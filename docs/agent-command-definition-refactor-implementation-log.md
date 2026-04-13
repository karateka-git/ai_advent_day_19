# Р–СѓСЂРЅР°Р» СЂРµР°Р»РёР·Р°С†РёРё

Р¤Р°Р№Р» РґР»СЏ РєСЂР°С‚РєРѕР№ С„РёРєСЃР°С†РёРё С„Р°РєС‚РёС‡РµСЃРєРё РІС‹РїРѕР»РЅРµРЅРЅС‹С… РґРµР№СЃС‚РІРёР№ РїРѕ РўР— `agent-command-definition-refactor-spec.md`.

Р¤РѕСЂРјР°С‚ РёСЃРїРѕР»СЊР·РѕРІР°РЅРёСЏ:

- С‡С‚Рѕ Р±С‹Р»Рѕ С†РµР»СЊСЋ СЌС‚Р°РїР°;
- РєР°РєРёРµ С€Р°РіРё СЂРµР°Р»СЊРЅРѕ РІС‹РїРѕР»РЅРµРЅС‹;
- РєР°РєРёРµ СЂРµС€РµРЅРёСЏ РїСЂРёРЅСЏС‚С‹;
- С‡С‚Рѕ РїСЂРѕРІРµСЂРµРЅРѕ;
- РєР°РєРёРјРё РєРѕРјРјРёС‚Р°РјРё СЌС‚Рѕ Р·Р°С„РёРєСЃРёСЂРѕРІР°РЅРѕ;
- С‡С‚Рѕ РѕСЃС‚Р°С‘С‚СЃСЏ СЃР»РµРґСѓСЋС‰РёРј С€Р°РіРѕРј.

## Р­С‚Р°Рї 1. Р¤РѕСЂРјР°Р»РёР·Р°С†РёСЏ command-definition РјРѕРґРµР»Рё

РЎС‚Р°С‚СѓСЃ: Р·Р°РІРµСЂС€С‘РЅ

Р¦РµР»СЊ СЌС‚Р°РїР°:

- Р·Р°С„РёРєСЃРёСЂРѕРІР°С‚СЊ РѕС‚РґРµР»СЊРЅРѕРµ РўР— РЅР° refactor capability-mapping;
- РѕРїРёСЃР°С‚СЊ С†РµР»РµРІСѓСЋ РјРѕРґРµР»СЊ command definitions, resolver Рё С‚РёРїРёР·РёСЂРѕРІР°РЅРЅС‹С… РёРґРµРЅС‚РёС„РёРєР°С‚РѕСЂРѕРІ;
- РїРѕРґРіРѕС‚РѕРІРёС‚СЊ РѕС‚РґРµР»СЊРЅС‹Р№ unified implementation log РїРѕРґ СЌС‚РѕС‚ Р±Р»РѕРє РёР·РјРµРЅРµРЅРёР№.

Р’С‹РїРѕР»РЅРµРЅРЅС‹Рµ РґРµР№СЃС‚РІРёСЏ:

1. РЎРѕР·РґР°РЅРѕ РѕС‚РґРµР»СЊРЅРѕРµ РўР— [agent-command-definition-refactor-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/agent-command-definition-refactor-spec.md).
2. Р’ РўР— Р·Р°С„РёРєСЃРёСЂРѕРІР°РЅС‹:
   - РїСЂРѕР±Р»РµРјР° С‚РµРєСѓС‰РµРіРѕ `buildAvailableAgentCommands(...)`;
   - С†РµР»РµРІР°СЏ РјРѕРґРµР»СЊ command-definition СЃР»РѕСЏ;
   - С‚СЂРµР±РѕРІР°РЅРёСЏ Рє `agent/bootstrap/commands`;
   - РѕРіСЂР°РЅРёС‡РµРЅРёСЏ С‚РµРєСѓС‰РµРіРѕ РїСЂРѕС…РѕРґР°, РІРєР»СЋС‡Р°СЏ Р·Р°РїСЂРµС‚ РЅР° РїСЂР°РІРєСѓ `README`.
3. Р’ РўР— РІС‹РґРµР»РµРЅС‹ 4 РїРѕСЃР»РµРґРѕРІР°С‚РµР»СЊРЅС‹С… СЌС‚Р°РїР°:
   - С„РѕСЂРјР°Р»РёР·Р°С†РёСЏ;
   - РІРЅРµРґСЂРµРЅРёРµ command definitions;
   - С‚РёРїРёР·Р°С†РёСЏ РёРґРµРЅС‚РёС„РёРєР°С‚РѕСЂРѕРІ Рё routing;
   - РёРЅС‚РµРіСЂР°С†РёСЏ Рё РїСЂРѕРІРµСЂРєР°.
4. РЎРѕР·РґР°РЅ РѕС‚РґРµР»СЊРЅС‹Р№ unified implementation log РґР»СЏ СЌС‚РѕРіРѕ РўР—.

РџСЂРёРЅСЏС‚С‹Рµ СЂРµС€РµРЅРёСЏ:

- РІРµСЃС‚Рё СЌС‚РѕС‚ СЂРµС„Р°РєС‚РѕСЂРёРЅРі РѕС‚РґРµР»СЊРЅС‹Рј РўР—, Р° РЅРµ СЃРјРµС€РёРІР°С‚СЊ РµРіРѕ СЃ РїСЂРµРґС‹РґСѓС‰РёРј capability-bootstrap Р¶СѓСЂРЅР°Р»РѕРј;
- РЅРµ С‚СЂРѕРіР°С‚СЊ `README` РЅР° СЌС‚РѕРј РїСЂРѕС…РѕРґРµ;
- СЃС‡РёС‚Р°С‚СЊ РєРѕРјР°РЅРґРЅС‹Рµ definitions РѕСЃРЅРѕРІРЅРѕР№ С‚РѕС‡РєРѕР№ СЂРѕСЃС‚Р° РґР»СЏ capability-mapping.

РџСЂРѕРІРµСЂРєР°:

- РІ `docs` РїСЂРёСЃСѓС‚СЃС‚РІСѓСЋС‚:
  - [agent-command-definition-refactor-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/agent-command-definition-refactor-spec.md)
  - [agent-command-definition-refactor-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/agent-command-definition-refactor-implementation-log.md)

РљРѕРјРјРёС‚С‹ СЌС‚Р°РїР°:

- С‚РµРєСѓС‰РёР№ РєРѕРјРјРёС‚ СЌС‚Р°РїР° вЂ” СЃРѕР·РґР°РЅРёРµ РўР— Рё unified implementation log.

РЎР»РµРґСѓСЋС‰РёР№ С€Р°Рі:

- РїРµСЂРµР№С‚Рё Рє `Р­С‚Р°РїСѓ 2` Рё Р·Р°РјРµРЅРёС‚СЊ С‚РµРєСѓС‰РёР№ `buildAvailableAgentCommands(...)` РЅР° explicit command definitions Рё resolver.

## Р­С‚Р°Рї 2. Р’РЅРµРґСЂРµРЅРёРµ command-definition СЃР»РѕСЏ

РЎС‚Р°С‚СѓСЃ: Р·Р°РІРµСЂС€С‘РЅ

Р¦РµР»СЊ СЌС‚Р°РїР°:

- РІС‹РЅРµСЃС‚Рё РѕРїРёСЃР°РЅРёРµ РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРёС… РєРѕРјР°РЅРґ РёР· РѕР±С‰РµРіРѕ builder-Р° РІ РѕС‚РґРµР»СЊРЅС‹Рµ command definitions;
- Р·Р°РјРµРЅРёС‚СЊ top-level С„СѓРЅРєС†РёСЋ РїРѕСЃС‚СЂРѕРµРЅРёСЏ РєРѕРјР°РЅРґ РЅР° explicit resolver.

Р’С‹РїРѕР»РЅРµРЅРЅС‹Рµ РґРµР№СЃС‚РІРёСЏ:

1. РЈРґР°Р»С‘РЅ СЃС‚Р°СЂС‹Р№ [AgentCommandCatalog.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/AgentCommandCatalog.kt) СЃ top-level builder-РѕРј.
2. Р’РІРµРґС‘РЅ РЅРѕРІС‹Р№ РїР°РєРµС‚ [agent/bootstrap/commands](</C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands>) СЃРѕ СЃР»РµРґСѓСЋС‰РёРјРё СЂРѕР»СЏРјРё:
   - [AgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AgentCommandDefinition.kt) вЂ” Р±Р°Р·РѕРІС‹Р№ РєРѕРЅС‚СЂР°РєС‚ command definition;
   - [ToolPostAgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/ToolPostAgentCommandDefinition.kt) вЂ” definition РєРѕРјР°РЅРґС‹ `tool post <postId>`;
   - [ToolPostsAgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/ToolPostsAgentCommandDefinition.kt) вЂ” definition РєРѕРјР°РЅРґС‹ `tool posts`;
   - [AvailableAgentCommandResolver.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AvailableAgentCommandResolver.kt) вЂ” resolver РґРѕСЃС‚СѓРїРЅС‹С… РєРѕРјР°РЅРґ;
   - [SupportedAgentCommandDefinitions.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/SupportedAgentCommandDefinitions.kt) вЂ” С‚РѕС‡РєР° СЃР±РѕСЂРєРё РїРѕРґРґРµСЂР¶РёРІР°РµРјС‹С… definitions.
3. Р”РѕР±Р°РІР»РµРЅ РѕР±С‰РёР№ support-РєР»Р°СЃСЃ [CommandDefinitionSupport.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/CommandDefinitionSupport.kt), РєРѕС‚РѕСЂС‹Р№ РїРѕРєР° СЃРѕС…СЂР°РЅСЏРµС‚ С‚РµРєСѓС‰РµРµ РїСЂРѕСЃС‚РѕРµ РїСЂР°РІРёР»Рѕ resolve РїРѕ РЅР°Р»РёС‡РёСЋ РЅСѓР¶РЅРѕРіРѕ tool РЅР° СЃРµСЂРІРµСЂРµ.
4. [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt) РїРµСЂРµРІРµРґС‘РЅ СЃ builder-Р° РЅР° `AvailableAgentCommandResolver`.
5. Р”РѕР±Р°РІР»РµРЅ Р»РѕРєР°Р»СЊРЅС‹Р№ unit-С‚РµСЃС‚ [AvailableAgentCommandResolverTest.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/test/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AvailableAgentCommandResolverTest.kt), РєРѕС‚РѕСЂС‹Р№ РїСЂРѕРІРµСЂСЏРµС‚:
   - РїРѕСЃС‚СЂРѕРµРЅРёРµ РґРѕСЃС‚СѓРїРЅС‹С… РєРѕРјР°РЅРґ РёР· РЅР°Р±РѕСЂР° definitions;
   - РїСЂРѕРїСѓСЃРє РєРѕРјР°РЅРґ, РєРѕС‚РѕСЂС‹Рµ РЅРµ РјРѕРіСѓС‚ СЂР°Р·СЂРµС€РёС‚СЊСЃСЏ.
6. Р’С‹РїРѕР»РЅРµРЅ РїСЂРѕРіРѕРЅ `.\gradlew.bat test`.

РџСЂРёРЅСЏС‚С‹Рµ СЂРµС€РµРЅРёСЏ:

- СЃРЅР°С‡Р°Р»Р° СЂР°Р·РґРµР»РёС‚СЊ РѕС‚РІРµС‚СЃС‚РІРµРЅРЅРѕСЃС‚Рё РјРµР¶РґСѓ definition Рё resolver, РЅРµ РјРµРЅСЏСЏ РµС‰С‘ С„РѕСЂРјР°С‚ РёРґРµРЅС‚РёС„РёРєР°С‚РѕСЂРѕРІ;
- СЃРѕС…СЂР°РЅРёС‚СЊ С‚РµРєСѓС‰РµРµ С„Р°РєС‚РёС‡РµСЃРєРѕРµ РїРѕРІРµРґРµРЅРёРµ РІС‹Р±РѕСЂР° СЃРµСЂРІРµСЂР° РЅР° СЌС‚РѕРј СЌС‚Р°РїРµ, С‡С‚РѕР±С‹ СЃР»РµРґСѓСЋС‰РёР№ СЌС‚Р°Рї Р±С‹Р» РїРѕСЃРІСЏС‰С‘РЅ РёРјРµРЅРЅРѕ С‚РёРїРёР·Р°С†РёРё Рё routing policy;
- РІС‹РЅРµСЃС‚Рё definitions РІ РѕС‚РґРµР»СЊРЅС‹Р№ РїР°РєРµС‚ `commands`, С‡С‚РѕР±С‹ РЅРѕРІР°СЏ РјРѕРґРµР»СЊ Р±С‹Р»Р° РІРёРґРЅР° СЃС‚СЂСѓРєС‚СѓСЂРЅРѕ, Р° РЅРµ С‚РѕР»СЊРєРѕ РїРѕ РёРјРµРЅР°Рј РєР»Р°СЃСЃРѕРІ.

РџСЂРѕРІРµСЂРєР°:

- `DefaultAgent` СЃС‚СЂРѕРёС‚ `availableCommands` С‡РµСЂРµР· `AvailableAgentCommandResolver`;
- top-level builder РІ СЃС‚Р°СЂРѕРј РІРёРґРµ СѓРґР°Р»С‘РЅ;
- `.\gradlew.bat test` Р·Р°РІРµСЂС€Р°РµС‚СЃСЏ СѓСЃРїРµС€РЅРѕ.

РљРѕРјРјРёС‚С‹ СЌС‚Р°РїР°:

- С‚РµРєСѓС‰РёР№ РєРѕРјРјРёС‚ СЌС‚Р°РїР° вЂ” РІРЅРµРґСЂРµРЅРёРµ command-definition СЃР»РѕСЏ Рё resolver.

РЎР»РµРґСѓСЋС‰РёР№ С€Р°Рі:

- РїРµСЂРµР№С‚Рё Рє `Р­С‚Р°РїСѓ 3` Рё Р·Р°РјРµРЅРёС‚СЊ СЃС‚СЂРѕРєРѕРІС‹Рµ `commandId` Рё `serverId` РЅР° С‚РёРїРёР·РёСЂРѕРІР°РЅРЅС‹Рµ РёРґРµРЅС‚РёС„РёРєР°С‚РѕСЂС‹ СЃ СЏРІРЅС‹Рј routing Сѓ command definitions.

## Р­С‚Р°Рї 3. РўРёРїРёР·Р°С†РёСЏ `commandId` Рё `serverId` Рё СЏРІРЅС‹Р№ routing

РЎС‚Р°С‚СѓСЃ: Р·Р°РІРµСЂС€С‘РЅ

Р¦РµР»СЊ СЌС‚Р°РїР°:

- СѓР±СЂР°С‚СЊ raw string `commandId` Рё `serverId` РёР· capability-РїРѕС‚РѕРєР°;
- СЃРґРµР»Р°С‚СЊ РїСЂР°РІРёР»Рѕ РІС‹Р±РѕСЂР° СЃРµСЂРІРµСЂР° СЏРІРЅРѕР№ С‡Р°СЃС‚СЊСЋ command definition.

Р’С‹РїРѕР»РЅРµРЅРЅС‹Рµ РґРµР№СЃС‚РІРёСЏ:

1. Р”РѕР±Р°РІР»РµРЅС‹ РЅРѕРІС‹Рµ С‚РёРїРёР·РёСЂРѕРІР°РЅРЅС‹Рµ РјРѕРґРµР»Рё:
   - [AgentCommandId.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/models/AgentCommandId.kt)
   - [McpServerId.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/models/McpServerId.kt)
2. Р’РІРµРґРµРЅР° РјРѕРґРµР»СЊ routing policy:
   - [CommandRouting.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/models/CommandRouting.kt)
3. Capability-РјРѕРґРµР»Рё РїРµСЂРµРІРµРґРµРЅС‹ РЅР° С‚РёРїРёР·РёСЂРѕРІР°РЅРЅС‹Рµ РёРґРµРЅС‚РёС„РёРєР°С‚РѕСЂС‹:
   - `AvailableAgentCommand.commandId`
   - `AvailableAgentCommand.serverId`
   - `KnownMcpServer.serverId`
   - `PreparedMcpServer.serverId`
4. `AgentRequest.CallAvailableCommand` Рё `AgentCapabilityRegistry.availableCommand(...)` РїРµСЂРµРІРµРґРµРЅС‹ РЅР° `AgentCommandId`.
5. `ToolBasedAgentCommandDefinition` С‚РµРїРµСЂСЊ С‚СЂРµР±СѓРµС‚ `routing` Рё СЂР°Р·СЂРµС€Р°РµС‚ РєРѕРјР°РЅРґСѓ С‡РµСЂРµР· РЅРµРіРѕ, Р° РЅРµ С‡РµСЂРµР· РЅРµСЏРІРЅС‹Р№ РїРѕРёСЃРє РїРµСЂРІРѕРіРѕ РїРѕРґС…РѕРґСЏС‰РµРіРѕ СЃРµСЂРІРµСЂР°.
6. `ToolPostAgentCommandDefinition` Рё `ToolPostsAgentCommandDefinition` Р·Р°РєСЂРµРїР»РµРЅС‹ Р·Р° `CommandRouting.FixedServer(McpServerId.LOCAL_MCP_SERVER)`.
7. [McpProjectConfig.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/config/McpProjectConfig.kt) С‚РµРїРµСЂСЊ СЃРѕР·РґР°С‘С‚ known servers СЃ `McpServerId.LOCAL_MCP_SERVER`.
8. [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt) РїРµСЂРµРІРµРґС‘РЅ РЅР° `AgentCommandId.TOOL_POST` Рё `AgentCommandId.TOOL_POSTS`.
9. [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt) РѕР±РЅРѕРІР»С‘РЅ РїРѕРґ С‚РёРїРёР·РёСЂРѕРІР°РЅРЅС‹Рµ id Рё С„РѕСЂРјРёСЂСѓРµС‚ РїСЂРёРєР»Р°РґРЅС‹Рµ error messages С‡РµСЂРµР· СЃС‚Р°Р±РёР»СЊРЅРѕРµ СЃС‚СЂРѕРєРѕРІРѕРµ РїСЂРµРґСЃС‚Р°РІР»РµРЅРёРµ `AgentCommandId`.
10. РћР±РЅРѕРІР»РµРЅС‹ unit-С‚РµСЃС‚С‹ РїРѕРґ РЅРѕРІС‹Рµ С‚РёРїС‹ Рё РЅРѕРІРѕРµ routing-РїРѕРІРµРґРµРЅРёРµ.
11. Р’С‹РїРѕР»РЅРµРЅ РїСЂРѕРіРѕРЅ `.\gradlew.bat test`.

РџСЂРёРЅСЏС‚С‹Рµ СЂРµС€РµРЅРёСЏ:

- РёСЃРїРѕР»СЊР·РѕРІР°С‚СЊ `enum class AgentCommandId` РґР»СЏ С„РёРєСЃРёСЂРѕРІР°РЅРЅРѕРіРѕ РЅР°Р±РѕСЂР° РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРёС… РєРѕРјР°РЅРґ;
- РёСЃРїРѕР»СЊР·РѕРІР°С‚СЊ `@JvmInline value class McpServerId` РґР»СЏ typed server id Р±РµР· Р¶С‘СЃС‚РєРѕРіРѕ РѕРіСЂР°РЅРёС‡РµРЅРёСЏ С‚РѕР»СЊРєРѕ enum-Р·РЅР°С‡РµРЅРёСЏРјРё;
- РЅР° С‚РµРєСѓС‰РµРј РїСЂРѕС…РѕРґРµ Р·Р°РєСЂРµРїРёС‚СЊ РєРѕРјР°РЅРґС‹ Р·Р° `FixedServer`, С‡С‚РѕР±С‹ РїРµСЂРµРЅРѕСЃ РєРѕРјР°РЅРґС‹ РЅР° РґСЂСѓРіРѕР№ СЃРµСЂРІРµСЂ РјРµРЅСЏР»СЃСЏ РІ РѕРґРЅРѕРј РјРµСЃС‚Рµ вЂ” РІРЅСѓС‚СЂРё РµС‘ definition.

РџСЂРѕРІРµСЂРєР°:

- РІ agent/workflow capability-РїРѕС‚РѕРєРµ Р±РѕР»СЊС€Рµ РЅРµС‚ raw string `commandId`;
- definitions РёСЃРїРѕР»СЊР·СѓСЋС‚ СЏРІРЅС‹Р№ `routing`;
- `.\gradlew.bat test` Р·Р°РІРµСЂС€Р°РµС‚СЃСЏ СѓСЃРїРµС€РЅРѕ.

РљРѕРјРјРёС‚С‹ СЌС‚Р°РїР°:

- С‚РµРєСѓС‰РёР№ РєРѕРјРјРёС‚ СЌС‚Р°РїР° вЂ” С‚РёРїРёР·Р°С†РёСЏ id Рё РІРЅРµРґСЂРµРЅРёРµ СЏРІРЅРѕРіРѕ routing РґР»СЏ command definitions.

РЎР»РµРґСѓСЋС‰РёР№ С€Р°Рі:

- РїРµСЂРµР№С‚Рё Рє `Р­С‚Р°РїСѓ 4` Рё РґРѕР±РёС‚СЊ РёРЅС‚РµРіСЂР°С†РёСЋ, Р»РѕРєР°Р»СЊРЅС‹Рµ С‚РµСЃС‚С‹ Рё С„РёРЅР°Р»СЊРЅСѓСЋ С„РёРєСЃР°С†РёСЋ Р¶СѓСЂРЅР°Р»Р° СЌС‚РѕРіРѕ РўР—.

## Р­С‚Р°Рї 4. РРЅС‚РµРіСЂР°С†РёСЏ, С‚РµСЃС‚С‹ Рё СЃРёРЅС…СЂРѕРЅРёР·Р°С†РёСЏ Р¶СѓСЂРЅР°Р»РѕРІ

РЎС‚Р°С‚СѓСЃ: Р·Р°РІРµСЂС€С‘РЅ

Р¦РµР»СЊ СЌС‚Р°РїР°:

- РїРѕРґС‚РІРµСЂРґРёС‚СЊ, С‡С‚Рѕ РЅРѕРІР°СЏ РјРѕРґРµР»СЊ command definitions СЂР°Р±РѕС‚Р°РµС‚ end-to-end РІРЅСѓС‚СЂРё С‚РµРєСѓС‰РµР№ Р°СЂС…РёС‚РµРєС‚СѓСЂС‹;
- Р·Р°РєСЂС‹С‚СЊ refactor РїСЂРѕРІРµСЂРєР°РјРё Рё РёС‚РѕРіРѕРІРѕР№ С„РёРєСЃР°С†РёРµР№ РІ unified log.

Р’С‹РїРѕР»РЅРµРЅРЅС‹Рµ РґРµР№СЃС‚РІРёСЏ:

1. РЈСЃРёР»РµРЅ unit-РЅР°Р±РѕСЂ РґР»СЏ resolver-Р°:
   - [AvailableAgentCommandResolverTest.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/test/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AvailableAgentCommandResolverTest.kt) С‚РµРїРµСЂСЊ СЏРІРЅРѕ РїСЂРѕРІРµСЂСЏРµС‚, С‡С‚Рѕ `FixedServer` РЅРµ РґРµР»Р°РµС‚ РЅРµСЏРІРЅС‹Р№ fallback РЅР° РґСЂСѓРіРѕР№ СЃРµСЂРІРµСЂ, РґР°Р¶Рµ РµСЃР»Рё С‚РѕС‚ РїСѓР±Р»РёРєСѓРµС‚ РЅСѓР¶РЅС‹Р№ tool.
2. Р’С‹РїРѕР»РЅРµРЅ РїСЂРѕРіРѕРЅ `.\gradlew.bat test` РїРѕСЃР»Рµ Р·Р°РІРµСЂС€РµРЅРёСЏ РІСЃРµС… refactor-РїСЂР°РІРѕРє.
3. Р’С‹РїРѕР»РЅРµРЅР° project-level headless РїСЂРѕРІРµСЂРєР°:
   - `powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -Headless`
4. Unified implementation log РґРѕРїРѕР»РЅРµРЅ РёС‚РѕРіР°РјРё РІСЃРµС… СЌС‚Р°РїРѕРІ Рё Р·Р°РєСЂС‹С‚ Р±РµР· РґРѕРїРѕР»РЅРёС‚РµР»СЊРЅС‹С… РёР·РјРµРЅРµРЅРёР№ РІ `README`, РєР°Рє Рё С‚СЂРµР±РѕРІР°Р»РѕСЃСЊ РІ СЂР°РјРєР°С… СЌС‚РѕРіРѕ РўР—.

РџСЂРёРЅСЏС‚С‹Рµ СЂРµС€РµРЅРёСЏ:

- СЃС‡РёС‚Р°С‚СЊ РѕС‚СЃСѓС‚СЃС‚РІРёРµ fallback РЅР° РґСЂСѓРіРѕР№ СЃРµСЂРІРµСЂ РєРѕСЂСЂРµРєС‚РЅС‹Рј С‚РµРєСѓС‰РёРј РїРѕРІРµРґРµРЅРёРµРј РґР»СЏ `FixedServer`, РїРѕС‚РѕРјСѓ С‡С‚Рѕ РёРјРµРЅРЅРѕ СЌС‚Рѕ РґРµР»Р°РµС‚ РјР°СЂС€СЂСѓС‚РёР·Р°С†РёСЋ РєРѕРјР°РЅРґС‹ СЏРІРЅРѕР№ Рё СѓРїСЂР°РІР»СЏРµРјРѕР№;
- РЅРµ СЂР°СЃС€РёСЂСЏС‚СЊ СЌС‚РѕС‚ РїСЂРѕС…РѕРґ РґРѕ РЅРѕРІС‹С… UX/README-РїСЂР°РІРѕРє, С‡С‚РѕР±С‹ РѕСЃС‚Р°РІРёС‚СЊ refactor СЃС„РѕРєСѓСЃРёСЂРѕРІР°РЅРЅС‹Рј С‚РѕР»СЊРєРѕ РЅР° capability-mapping.

РџСЂРѕРІРµСЂРєР°:

- `.\gradlew.bat test` Р·Р°РІРµСЂС€Р°РµС‚СЃСЏ СѓСЃРїРµС€РЅРѕ;
- `powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -Headless` РїСЂРѕС…РѕРґРёС‚ СѓСЃРїРµС€РЅРѕ;
- command definitions, typed ids Рё fixed routing СЂР°Р±РѕС‚Р°СЋС‚ РІ РѕР±С‰РµР№ С†РµРїРѕС‡РєРµ РїСЂРёР»РѕР¶РµРЅРёСЏ Р±РµР· СЂРµРіСЂРµСЃСЃРёРё С‚РµРєСѓС‰РµРіРѕ CLI-РєРѕРЅС‚СЂР°РєС‚Р°.

РљРѕРјРјРёС‚С‹ СЌС‚Р°РїР°:

- С‚РµРєСѓС‰РёР№ РєРѕРјРјРёС‚ СЌС‚Р°РїР° вЂ” С„РёРЅР°Р»СЊРЅР°СЏ РїСЂРѕРІРµСЂРєР° Рё Р·Р°РєСЂС‹С‚РёРµ unified implementation log.

РЎР»РµРґСѓСЋС‰РёР№ С€Р°Рі:

- РѕС‚РґРµР»СЊРЅС‹Рј РїРѕСЃР»РµРґСѓСЋС‰РёРј Р±Р»РѕРєРѕРј РјРѕР¶РЅРѕ РґРѕСЂР°Р±Р°С‚С‹РІР°С‚СЊ UX, README Рё РґР°Р»СЊРЅРµР№С€СѓСЋ СЌРІРѕР»СЋС†РёСЋ routing policy, РЅРµ СЃРјРµС€РёРІР°СЏ СЌС‚Рѕ СЃ СѓР¶Рµ Р·Р°РІРµСЂС€С‘РЅРЅС‹Рј refactor-РѕРј command definitions.
