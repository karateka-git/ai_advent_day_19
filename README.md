# ai_advent_day_18

Учебный MCP sandbox на Kotlin/Ktor с двумя контурами:

- `stateless` reference для обычных `tools/list` и `tools/call`;
- `stateful` push-контур для серверных уведомлений через SSE.

Проект показывает, как поверх одного CLI и одного agent/workflow-слоя поддерживать разные MCP runtime-подходы без дублирования всей верхней части приложения.

## Что умеет проект

- поднимать локальный `stateless` MCP server;
- поднимать отдельный `stateful` MCP server;
- подключать общий CLI-клиент к обоим контурам;
- вызывать обычные MCP tools для `JSONPlaceholder`;
- запускать фоновый push-сценарий `start_random_posts`;
- печатать `notifications/random_post` прямо в том же CLI.

## Текущая архитектура

Пользовательский поток остаётся общим:

`presentation -> workflow -> agent -> mcp`

Разделение начинается внутри MCP-слоя:

- `mcp/client/common` и `mcp/server/common`
  Общие модели, tool-call контракты и интеграция с `JSONPlaceholder`.
- `mcp/client/stateless` и `mcp/server/stateless`
  Базовый reference-контур на Streamable HTTP.
- `mcp/client/stateful` и `mcp/server/stateful`
  Контур с stateful session lifecycle и классическим SSE transport.

Ключевая идея:

- `tool posts` и `tool post <postId>` идут в `stateless` сервер;
- `tool start-random-posts [intervalMinutes]` идёт в `stateful` сервер;
- CLI, workflow и agent при этом остаются общими.

## Transport-подход

- `stateless` контур использует Streamable HTTP.
- `stateful` контур использует классический SSE transport:
  - клиент открывает SSE-сессию;
  - сервер держит session-aware runtime;
  - push-события приходят как `notifications/random_post`.

Это сделано намеренно: базовый reference не усложняется, а push-сценарий живёт рядом как отдельный runtime.

## Локальные endpoints

- `stateless`: `http://127.0.0.1:3000/mcp`
- `stateful`: `http://127.0.0.1:3001/mcp`

## CLI-команды

После подготовки агента доступны:

- `tool posts`
  Показать первые 10 публикаций из `JSONPlaceholder`.
- `tool post <postId>`
  Показать одну публикацию по идентификатору.
- `tool start-random-posts [intervalMinutes]`
  Включить push случайных публикаций в текущую клиентскую сессию.
  Если команда вызывается повторно, интервал обновляется.
- `help`
  Показать список доступных команд.
- `exit`
  Завершить клиентскую сессию.

Ограничения текущего этапа:

- на `stateful` сервере есть только один tool: `start_random_posts`;
- отдельного `stop_random_posts` пока нет;
- остановка push привязана к завершению клиентской сессии.

## Pipeline

Ниже путь пользовательской команды через общий CLI и два MCP-контура.

### 1. Подготовка агента

- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/App.kt)
  Запускает `PrepareAgentCommand` перед входом в интерактивный режим.
- [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
  Делегирует подготовку агенту.
- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  Обходит известные MCP-серверы и собирает capability snapshot.
- [RoutingMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/RoutingMcpClient.kt)
  Подключает `stateless` и `stateful` контуры через разные client runtime.

Результат:

- CLI знает, какие команды реально доступны;
- если один из серверов недоступен, команды этого контура скрываются и появляется понятное предупреждение.

### 2. Обычный stateless сценарий

Пример:

```text
tool post 2
```

Путь:

1. [DefaultCliCommandParser.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/presentation/cli/DefaultCliCommandParser.kt)
   Разбирает ввод в `ToolPostCommand`.
2. [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
   Превращает его в `AgentRequest.CallAvailableCommand`.
3. [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
   Находит command definition и понимает, что команда идёт в `stateless`.
4. [RoutingMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/RoutingMcpClient.kt)
   Отправляет вызов в [StatelessMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/stateless/StatelessMcpClient.kt).
5. `StatelessMcpClient` вызывает tool на [StatelessMcpServerApp.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateless/StatelessMcpServerApp.kt).
6. Общий server-side tool из `mcp/server/common/toolcall/tools/...` возвращает обычный результат.

### 3. Stateful push сценарий

Пример:

```text
tool start-random-posts 1
```

Путь:

1. [DefaultCliCommandParser.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/presentation/cli/DefaultCliCommandParser.kt)
   Разбирает ввод в `ToolStartRandomPostsCommand`.
2. [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
   Делегирует вызов агенту.
3. [ToolStartRandomPostsAgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/ToolStartRandomPostsAgentCommandDefinition.kt)
   Закрепляет маршрут в `stateful` контур.
4. [RoutingMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/RoutingMcpClient.kt)
   Передаёт вызов в [StatefulMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/stateful/StatefulMcpClient.kt).
5. `StatefulMcpClient` держит долгоживущую SSE-сессию и вызывает tool на [StatefulMcpServerApp.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateful/StatefulMcpServerApp.kt).
6. [StartRandomPostsTool.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateful/toolcall/tools/startrandomposts/StartRandomPostsTool.kt)
   Регистрирует или обновляет подписку текущей сессии.
7. [RandomPostTickerService.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateful/background/RandomPostTickerService.kt)
   Периодически получает случайный пост и шлёт `notifications/random_post`.
8. [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/App.kt)
   Слушает push-уведомления и печатает `[push] ...` в тот же CLI.

### 4. Ключевое отличие двух путей

- `stateless` путь заканчивается сразу после ответа на `tools/call`.
- `stateful` путь после успешного `tools/call` продолжает жить в рамках клиентской сессии.
- В `stateful` сценарии важен не только результат команды, но и последующие server-to-client notifications.

## Сборка

Базовая сборка:

```powershell
.\gradlew.bat build
```

Сборка direct launcher-артефактов:

```powershell
.\gradlew.bat installClientDist installServerDist installStatefulServerDist
```

После этого будут доступны:

- `build\install\mcp-client\bin\mcp-client.bat`
- `build\install\mcp-server\bin\mcp-server.bat`
- `build\install\mcp-stateful-server\bin\mcp-stateful-server.bat`

## Предпочтительный запуск

Для ручной проверки в этом проекте предпочитается scripted/direct launcher workflow.

Подготовить проект к ручной проверке:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1
```

Этот сценарий:

- собирает проект;
- поднимает `stateless` сервер;
- поднимает `stateful` сервер;
- открывает клиентский CLI.

Запуск уже собранного проекта без нового build:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -SkipBuild
```

Headless-вариант для воспроизводимой проверки:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -Headless
```

Сквозная e2e-проверка:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\check-e2e.ps1
```

## Ручной запуск по отдельности

`stateless` сервер:

```powershell
.\build\install\mcp-server\bin\mcp-server.bat
```

`stateful` сервер:

```powershell
.\build\install\mcp-stateful-server\bin\mcp-stateful-server.bat
```

Клиент:

```powershell
.\build\install\mcp-client\bin\mcp-client.bat
```

Технические Gradle entrypoint'ы тоже есть, но они вторичны:

```powershell
.\gradlew.bat runServer
.\gradlew.bat runStatefulServer
.\gradlew.bat runClient
```

## Быстрый сценарий проверки push

1. Подними проект через:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1
```

2. В окне клиента выполни:

```text
help
tool posts
tool post 1
tool start-random-posts 1
```

3. Ожидаемое поведение:

- `tool posts` и `tool post 1` работают через `stateless` контур;
- `tool start-random-posts 1` активирует push для текущей сессии;
- дальше примерно раз в минуту в том же CLI появляются строки вида:

```text
[push] Случайная публикация #...
```

4. Остановка:

```text
exit
```

## Важные файлы

- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/App.kt)
  Общий CLI entrypoint.
- [McpProjectConfig.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/config/McpProjectConfig.kt)
  Локальные endpoints и конфиг серверов.
- [RoutingMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/RoutingMcpClient.kt)
  Маршрутизация между `stateless` и `stateful`.
- [StatelessMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/stateless/StatelessMcpClient.kt)
  Клиент для базового reference-контура.
- [StatefulMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/stateful/StatefulMcpClient.kt)
  Клиент с долгоживущей SSE-сессией.
- [StatelessMcpServerApp.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateless/StatelessMcpServerApp.kt)
  Entry point `stateless` сервера.
- [StatefulMcpServerApp.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateful/StatefulMcpServerApp.kt)
  Entry point `stateful` сервера.
- [RandomPostTickerService.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateful/background/RandomPostTickerService.kt)
  Фоновый ticker для push случайных публикаций.
- [StartRandomPostsTool.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/server/stateful/toolcall/tools/startrandomposts/StartRandomPostsTool.kt)
  Единственный tool `stateful` сервера.

## Документация

- [MemoryBank/README.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/MemoryBank/README.md)
  Локальная карта памяти проекта.
- [MemoryBank/agent-preflight.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/MemoryBank/agent-preflight.md)
  Preflight-правила перед новыми крупными блоками.
- [docs/technical-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/technical-spec.md)
  Базовое ТЗ reference-части.
- [docs/stateful-random-post-push-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/stateful-random-post-push-spec.md)
  ТЗ для stateful push-контура.
- [docs/stateful-random-post-push-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/stateful-random-post-push-implementation-log.md)
  Журнал реализации stateful push-сценария.
