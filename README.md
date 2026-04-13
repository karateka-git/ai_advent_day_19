# ai_advent_day_17

Расширяемый sandbox-проект на Kotlin для знакомства с MCP и подготовки базы под будущие агентные сценарии.

Сейчас проект прошёл путь от минимального `connect -> initialize -> tools/list` до агентной модели, в которой:

- агент сам подготавливает MCP-возможности на старте;
- пользователь не управляет подключением вручную;
- доступные команды публикуются поверх найденных MCP tools;
- маршрутизация команды на нужный MCP tool и сервер происходит внутри агента.

## Развитие проекта

На этом этапе проект уже умеет:

- поднимать локальный MCP server на Kotlin/Ktor;
- подключать к нему клиент через Streamable HTTP;
- выполнять discovery серверных возможностей;
- публиковать прикладные команды поверх MCP tools;
- вызывать tools `fetch_post` и `list_posts` поверх `JSONPlaceholder`;
- выполнять стартовую подготовку агента до появления CLI prompt;
- строить capability mapping через отдельные command definitions с явным routing.

Ключевые изменения относительно более раннего состояния проекта:

- пользовательский `connect` убран из основного CLI-сценария;
- `tool post` и `tool posts` теперь доступны через capability snapshot агента;
- `commandId` и `serverId` типизированы;
- вместо общего builder-а используется command-definition слой.

## Что внутри

- локальный MCP server на Kotlin/Ktor;
- HTTP endpoint `http://127.0.0.1:3000/mcp`;
- демонстрационные инструменты `ping` и `echo`;
- прикладные инструменты `fetch_post` и `list_posts`;
- интерактивный CLI-клиент;
- scripted smoke/e2e-проверка;
- direct launcher-запуск для Windows без Gradle progress UI.

## Текущая архитектура

Клиентская часть организована по цепочке:

`presentation -> workflow -> agent -> mcp`

Роли слоёв:

- `presentation` принимает пользовательский ввод и форматирует вывод.
- `workflow` переводит пользовательскую команду в прикладной сценарий.
- `agent` хранит capability snapshot, выбирает команду, сервер и MCP tool.
- `mcp` инкапсулирует работу с Kotlin MCP SDK и transport-уровнем.

## Pipeline

Ниже текущий путь от пользовательского ввода до вывода результата.

### 1. Старт приложения

- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/App.kt)
  Создаёт parser, workflow handler, formatter и запускает стартовую подготовку агента.
- [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
  Получает `PrepareAgentCommand` и делегирует подготовку агенту.
- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  Выполняет `AgentRequest.Prepare`, проходит по известным MCP-серверам и собирает snapshot возможностей.
- [DefaultMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/mcp/client/DefaultMcpClient.kt)
  Для каждого endpoint делает protocol initialization и `tools/list`.
- [AvailableAgentCommandResolver.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AvailableAgentCommandResolver.kt)
  Строит список доступных пользовательских команд из command definitions.
- [AgentCapabilityRegistry.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/AgentCapabilityRegistry.kt)
  Сохраняет актуальный capability snapshot агента.

Именно здесь сейчас происходит логическая проверка, что агент "подключился" и готов к работе: если snapshot не собран, пользовательские команды считаются недоступными.

### 2. Пользователь вводит команду

Пример:

```text
tool post 2
```

- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/App.kt)
  Читает строку из консоли и передаёт её в parser.
- [DefaultCliCommandParser.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/presentation/cli/DefaultCliCommandParser.kt)
  Разбирает CLI-ввод и превращает его в `ToolPostCommand(postId = 2)`.

### 3. Workflow переводит ввод в агентный сценарий

- [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
  Для `ToolPostCommand` создаёт `AgentRequest.CallAvailableCommand` с `AgentCommandId.TOOL_POST` и аргументами команды.

### 4. Агент выбирает команду и маршрут

- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  Ищет команду в `AgentCapabilityRegistry`.
- [ToolPostAgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/ToolPostAgentCommandDefinition.kt)
  Описывает команду `tool post <postId>`.
- [CommandRouting.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/models/CommandRouting.kt)
  Определяет правило маршрутизации команды на сервер.

Если capability snapshot пустой или команда не найдена, агент возвращает прикладную ошибку. То есть проверка доступности команды теперь находится в агенте, а не в `App`.

### 5. MCP client вызывает tool

- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  Превращает доступную пользовательскую команду в `AgentRequest.CallTool`.
- [DefaultMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/mcp/client/DefaultMcpClient.kt)
  Создаёт SDK client, вызывает `tools/call`, маппит ответ в проектную модель и закрывает соединение.

### 6. Ответ идёт обратно к пользователю

- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  Возвращает `AgentResponse.ToolCallSuccess` или `AgentResponse.Failure`.
- [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
  Преобразует агентный ответ в `ToolCallResult`.
- [DefaultCliOutputFormatter.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/presentation/cli/DefaultCliOutputFormatter.kt)
  Форматирует результат в текст для консоли.
- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/App.kt)
  Печатает итоговый вывод пользователю.

## Транспорт

Проект использует Streamable HTTP transport. На текущем этапе сервер опубликован через stateless-вариант, потому что он проще для минимального reference-сценария и не требует отдельного server-side session lifecycle.

## Быстрый старт

Сборка проекта:

```powershell
.\gradlew.bat build
```

Сборка direct launcher-артефактов:

```powershell
.\gradlew.bat installClientDist installServerDist
```

После этого будут доступны:

- `build\install\mcp-client\bin\mcp-client.bat`
- `build\install\mcp-server\bin\mcp-server.bat`

Ручной запуск сервера:

```powershell
.\build\install\mcp-server\bin\mcp-server.bat
```

Ручной запуск клиента:

```powershell
.\build\install\mcp-client\bin\mcp-client.bat
```

## Команды проекта

Технические Gradle entrypoint'ы:

```powershell
.\gradlew.bat runServer
.\gradlew.bat runClient
```

Scripted-запуск клиента для smoke/e2e:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\invoke-client-commands.ps1 -Commands help,"tool posts",exit
```

Подготовка ручной проверки одним запуском:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1
```

Для этого репозитория пользовательская фраза `собери проект` по умолчанию трактуется именно как этот workflow.

Запуск уже собранных артефактов без новой сборки:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -SkipBuild
```

Для этого репозитория пользовательская фраза `запусти проект` по умолчанию трактуется именно как этот вариант.

Сквозная end-to-end проверка:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\check-e2e.ps1
```

Headless-вариант ручной проверки:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -Headless
```

## Пользовательские команды CLI

После подготовки агента пользователю доступны:

- `tool posts` — показать первые 10 публикаций из `JSONPlaceholder`;
- `tool post <postId>` — показать одну публикацию по идентификатору;
- `help` — показать список доступных команд;
- `exit` — завершить сессию клиента.

## Документация

- проектный `MemoryBank`: [MemoryBank/README.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/MemoryBank/README.md)
- preflight для новых сессий: [MemoryBank/agent-preflight.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/MemoryBank/agent-preflight.md)
- ТЗ по MCP reference-части: [docs/technical-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/technical-spec.md)
- журнал реализации MCP reference-части: [docs/mcp-reference-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/mcp-reference-implementation-log.md)
- ТЗ по агентной архитектуре: [docs/agent-architecture-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/agent-architecture-spec.md)
- журнал реализации агентной архитектуры: [docs/agent-architecture-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/agent-architecture-implementation-log.md)
- ТЗ по интеграции MCP tool: [docs/mcp-tool-integration-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/mcp-tool-integration-spec.md)
- журнал реализации интеграции MCP tool: [docs/mcp-tool-integration-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/mcp-tool-integration-implementation-log.md)
- контракт первого MCP tool: [docs/mcp-tool-integration-contract.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/mcp-tool-integration-contract.md)
- ТЗ по agent capability bootstrap: [docs/mcp-agent-capability-bootstrap-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/mcp-agent-capability-bootstrap-spec.md)
- capability model агента: [docs/mcp-agent-capability-model.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/mcp-agent-capability-model.md)
- журнал реализации agent capability bootstrap: [docs/mcp-agent-capability-bootstrap-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/mcp-agent-capability-bootstrap-implementation-log.md)
- ТЗ по refactor command definitions: [docs/agent-command-definition-refactor-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/agent-command-definition-refactor-spec.md)
- журнал реализации refactor command definitions: [docs/agent-command-definition-refactor-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/agent-command-definition-refactor-implementation-log.md)
