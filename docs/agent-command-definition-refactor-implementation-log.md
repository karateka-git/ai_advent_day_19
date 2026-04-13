# Журнал реализации

Файл для краткой фиксации фактически выполненных действий по ТЗ `agent-command-definition-refactor-spec.md`.

Формат использования:

- что было целью этапа;
- какие шаги реально выполнены;
- какие решения приняты;
- что проверено;
- какими коммитами это зафиксировано;
- что остаётся следующим шагом.

## Этап 1. Формализация command-definition модели

Статус: завершён

Цель этапа:

- зафиксировать отдельное ТЗ на refactor capability-mapping;
- описать целевую модель command definitions, resolver и типизированных идентификаторов;
- подготовить отдельный unified implementation log под этот блок изменений.

Выполненные действия:

1. Создано отдельное ТЗ [agent-command-definition-refactor-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/agent-command-definition-refactor-spec.md).
2. В ТЗ зафиксированы:
   - проблема текущего `buildAvailableAgentCommands(...)`;
   - целевая модель command-definition слоя;
   - требования к `agent/bootstrap/commands`;
   - ограничения текущего прохода, включая запрет на правку `README`.
3. В ТЗ выделены 4 последовательных этапа:
   - формализация;
   - внедрение command definitions;
   - типизация идентификаторов и routing;
   - интеграция и проверка.
4. Создан отдельный unified implementation log для этого ТЗ.

Принятые решения:

- вести этот рефакторинг отдельным ТЗ, а не смешивать его с предыдущим capability-bootstrap журналом;
- не трогать `README` на этом проходе;
- считать командные definitions основной точкой роста для capability-mapping.

Проверка:

- в `docs` присутствуют:
  - [agent-command-definition-refactor-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/agent-command-definition-refactor-spec.md)
  - [agent-command-definition-refactor-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/docs/agent-command-definition-refactor-implementation-log.md)

Коммиты этапа:

- текущий коммит этапа — создание ТЗ и unified implementation log.

Следующий шаг:

- перейти к `Этапу 2` и заменить текущий `buildAvailableAgentCommands(...)` на explicit command definitions и resolver.

## Этап 2. Внедрение command-definition слоя

Статус: завершён

Цель этапа:

- вынести описание пользовательских команд из общего builder-а в отдельные command definitions;
- заменить top-level функцию построения команд на explicit resolver.

Выполненные действия:

1. Удалён старый [AgentCommandCatalog.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/AgentCommandCatalog.kt) с top-level builder-ом.
2. Введён новый пакет [agent/bootstrap/commands](</C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands>) со следующими ролями:
   - [AgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AgentCommandDefinition.kt) — базовый контракт command definition;
   - [ToolPostAgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/ToolPostAgentCommandDefinition.kt) — definition команды `tool post <postId>`;
   - [ToolPostsAgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/ToolPostsAgentCommandDefinition.kt) — definition команды `tool posts`;
   - [AvailableAgentCommandResolver.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AvailableAgentCommandResolver.kt) — resolver доступных команд;
   - [SupportedAgentCommandDefinitions.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/SupportedAgentCommandDefinitions.kt) — точка сборки поддерживаемых definitions.
3. Добавлен общий support-класс [CommandDefinitionSupport.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/CommandDefinitionSupport.kt), который пока сохраняет текущее простое правило resolve по наличию нужного tool на сервере.
4. [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt) переведён с builder-а на `AvailableAgentCommandResolver`.
5. Добавлен локальный unit-тест [AvailableAgentCommandResolverTest.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_17/src/test/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AvailableAgentCommandResolverTest.kt), который проверяет:
   - построение доступных команд из набора definitions;
   - пропуск команд, которые не могут разрешиться.
6. Выполнен прогон `.\gradlew.bat test`.

Принятые решения:

- сначала разделить ответственности между definition и resolver, не меняя ещё формат идентификаторов;
- сохранить текущее фактическое поведение выбора сервера на этом этапе, чтобы следующий этап был посвящён именно типизации и routing policy;
- вынести definitions в отдельный пакет `commands`, чтобы новая модель была видна структурно, а не только по именам классов.

Проверка:

- `DefaultAgent` строит `availableCommands` через `AvailableAgentCommandResolver`;
- top-level builder в старом виде удалён;
- `.\gradlew.bat test` завершается успешно.

Коммиты этапа:

- текущий коммит этапа — внедрение command-definition слоя и resolver.

Следующий шаг:

- перейти к `Этапу 3` и заменить строковые `commandId` и `serverId` на типизированные идентификаторы с явным routing у command definitions.
