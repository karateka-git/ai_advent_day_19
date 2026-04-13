# ai_advent_day_18

Р Р°СЃС€РёСЂСЏРµРјС‹Р№ sandbox-РїСЂРѕРµРєС‚ РЅР° Kotlin РґР»СЏ Р·РЅР°РєРѕРјСЃС‚РІР° СЃ MCP Рё РїРѕРґРіРѕС‚РѕРІРєРё Р±Р°Р·С‹ РїРѕРґ Р±СѓРґСѓС‰РёРµ Р°РіРµРЅС‚РЅС‹Рµ СЃС†РµРЅР°СЂРёРё.

РЎРµР№С‡Р°СЃ РїСЂРѕРµРєС‚ РїСЂРѕС€С‘Р» РїСѓС‚СЊ РѕС‚ РјРёРЅРёРјР°Р»СЊРЅРѕРіРѕ `connect -> initialize -> tools/list` РґРѕ Р°РіРµРЅС‚РЅРѕР№ РјРѕРґРµР»Рё, РІ РєРѕС‚РѕСЂРѕР№:

- Р°РіРµРЅС‚ СЃР°Рј РїРѕРґРіРѕС‚Р°РІР»РёРІР°РµС‚ MCP-РІРѕР·РјРѕР¶РЅРѕСЃС‚Рё РЅР° СЃС‚Р°СЂС‚Рµ;
- РїРѕР»СЊР·РѕРІР°С‚РµР»СЊ РЅРµ СѓРїСЂР°РІР»СЏРµС‚ РїРѕРґРєР»СЋС‡РµРЅРёРµРј РІСЂСѓС‡РЅСѓСЋ;
- РґРѕСЃС‚СѓРїРЅС‹Рµ РєРѕРјР°РЅРґС‹ РїСѓР±Р»РёРєСѓСЋС‚СЃСЏ РїРѕРІРµСЂС… РЅР°Р№РґРµРЅРЅС‹С… MCP tools;
- РјР°СЂС€СЂСѓС‚РёР·Р°С†РёСЏ РєРѕРјР°РЅРґС‹ РЅР° РЅСѓР¶РЅС‹Р№ MCP tool Рё СЃРµСЂРІРµСЂ РїСЂРѕРёСЃС…РѕРґРёС‚ РІРЅСѓС‚СЂРё Р°РіРµРЅС‚Р°.

## Р Р°Р·РІРёС‚РёРµ РїСЂРѕРµРєС‚Р°

РќР° СЌС‚РѕРј СЌС‚Р°РїРµ РїСЂРѕРµРєС‚ СѓР¶Рµ СѓРјРµРµС‚:

- РїРѕРґРЅРёРјР°С‚СЊ Р»РѕРєР°Р»СЊРЅС‹Р№ MCP server РЅР° Kotlin/Ktor;
- РїРѕРґРєР»СЋС‡Р°С‚СЊ Рє РЅРµРјСѓ РєР»РёРµРЅС‚ С‡РµСЂРµР· Streamable HTTP;
- РІС‹РїРѕР»РЅСЏС‚СЊ discovery СЃРµСЂРІРµСЂРЅС‹С… РІРѕР·РјРѕР¶РЅРѕСЃС‚РµР№;
- РїСѓР±Р»РёРєРѕРІР°С‚СЊ РїСЂРёРєР»Р°РґРЅС‹Рµ РєРѕРјР°РЅРґС‹ РїРѕРІРµСЂС… MCP tools;
- РІС‹Р·С‹РІР°С‚СЊ tools `fetch_post` Рё `list_posts` РїРѕРІРµСЂС… `JSONPlaceholder`;
- РІС‹РїРѕР»РЅСЏС‚СЊ СЃС‚Р°СЂС‚РѕРІСѓСЋ РїРѕРґРіРѕС‚РѕРІРєСѓ Р°РіРµРЅС‚Р° РґРѕ РїРѕСЏРІР»РµРЅРёСЏ CLI prompt;
- СЃС‚СЂРѕРёС‚СЊ capability mapping С‡РµСЂРµР· РѕС‚РґРµР»СЊРЅС‹Рµ command definitions СЃ СЏРІРЅС‹Рј routing.

РљР»СЋС‡РµРІС‹Рµ РёР·РјРµРЅРµРЅРёСЏ РѕС‚РЅРѕСЃРёС‚РµР»СЊРЅРѕ Р±РѕР»РµРµ СЂР°РЅРЅРµРіРѕ СЃРѕСЃС‚РѕСЏРЅРёСЏ РїСЂРѕРµРєС‚Р°:

- РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРёР№ `connect` СѓР±СЂР°РЅ РёР· РѕСЃРЅРѕРІРЅРѕРіРѕ CLI-СЃС†РµРЅР°СЂРёСЏ;
- `tool post` Рё `tool posts` С‚РµРїРµСЂСЊ РґРѕСЃС‚СѓРїРЅС‹ С‡РµСЂРµР· capability snapshot Р°РіРµРЅС‚Р°;
- `commandId` Рё `serverId` С‚РёРїРёР·РёСЂРѕРІР°РЅС‹;
- РІРјРµСЃС‚Рѕ РѕР±С‰РµРіРѕ builder-Р° РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ command-definition СЃР»РѕР№.

## Р§С‚Рѕ РІРЅСѓС‚СЂРё

- Р»РѕРєР°Р»СЊРЅС‹Р№ MCP server РЅР° Kotlin/Ktor;
- HTTP endpoint `http://127.0.0.1:3000/mcp`;
- РґРµРјРѕРЅСЃС‚СЂР°С†РёРѕРЅРЅС‹Рµ РёРЅСЃС‚СЂСѓРјРµРЅС‚С‹ `ping` Рё `echo`;
- РїСЂРёРєР»Р°РґРЅС‹Рµ РёРЅСЃС‚СЂСѓРјРµРЅС‚С‹ `fetch_post` Рё `list_posts`;
- РёРЅС‚РµСЂР°РєС‚РёРІРЅС‹Р№ CLI-РєР»РёРµРЅС‚;
- scripted smoke/e2e-РїСЂРѕРІРµСЂРєР°;
- direct launcher-Р·Р°РїСѓСЃРє РґР»СЏ Windows Р±РµР· Gradle progress UI.

## РўРµРєСѓС‰Р°СЏ Р°СЂС…РёС‚РµРєС‚СѓСЂР°

РљР»РёРµРЅС‚СЃРєР°СЏ С‡Р°СЃС‚СЊ РѕСЂРіР°РЅРёР·РѕРІР°РЅР° РїРѕ С†РµРїРѕС‡РєРµ:

`presentation -> workflow -> agent -> mcp`

Р РѕР»Рё СЃР»РѕС‘РІ:

- `presentation` РїСЂРёРЅРёРјР°РµС‚ РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРёР№ РІРІРѕРґ Рё С„РѕСЂРјР°С‚РёСЂСѓРµС‚ РІС‹РІРѕРґ.
- `workflow` РїРµСЂРµРІРѕРґРёС‚ РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєСѓСЋ РєРѕРјР°РЅРґСѓ РІ РїСЂРёРєР»Р°РґРЅРѕР№ СЃС†РµРЅР°СЂРёР№.
- `agent` С…СЂР°РЅРёС‚ capability snapshot, РІС‹Р±РёСЂР°РµС‚ РєРѕРјР°РЅРґСѓ, СЃРµСЂРІРµСЂ Рё MCP tool.
- `mcp` РёРЅРєР°РїСЃСѓР»РёСЂСѓРµС‚ СЂР°Р±РѕС‚Сѓ СЃ Kotlin MCP SDK Рё transport-СѓСЂРѕРІРЅРµРј.

## Pipeline

РќРёР¶Рµ С‚РµРєСѓС‰РёР№ РїСѓС‚СЊ РѕС‚ РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРѕРіРѕ РІРІРѕРґР° РґРѕ РІС‹РІРѕРґР° СЂРµР·СѓР»СЊС‚Р°С‚Р°.

### 1. РЎС‚Р°СЂС‚ РїСЂРёР»РѕР¶РµРЅРёСЏ

- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/App.kt)
  РЎРѕР·РґР°С‘С‚ parser, workflow handler, formatter Рё Р·Р°РїСѓСЃРєР°РµС‚ СЃС‚Р°СЂС‚РѕРІСѓСЋ РїРѕРґРіРѕС‚РѕРІРєСѓ Р°РіРµРЅС‚Р°.
- [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
  РџРѕР»СѓС‡Р°РµС‚ `PrepareAgentCommand` Рё РґРµР»РµРіРёСЂСѓРµС‚ РїРѕРґРіРѕС‚РѕРІРєСѓ Р°РіРµРЅС‚Сѓ.
- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  Р’С‹РїРѕР»РЅСЏРµС‚ `AgentRequest.Prepare`, РїСЂРѕС…РѕРґРёС‚ РїРѕ РёР·РІРµСЃС‚РЅС‹Рј MCP-СЃРµСЂРІРµСЂР°Рј Рё СЃРѕР±РёСЂР°РµС‚ snapshot РІРѕР·РјРѕР¶РЅРѕСЃС‚РµР№.
- [DefaultMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/DefaultMcpClient.kt)
  Р”Р»СЏ РєР°Р¶РґРѕРіРѕ endpoint РґРµР»Р°РµС‚ protocol initialization Рё `tools/list`.
- [AvailableAgentCommandResolver.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/AvailableAgentCommandResolver.kt)
  РЎС‚СЂРѕРёС‚ СЃРїРёСЃРѕРє РґРѕСЃС‚СѓРїРЅС‹С… РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРёС… РєРѕРјР°РЅРґ РёР· command definitions.
- [AgentCapabilityRegistry.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/AgentCapabilityRegistry.kt)
  РЎРѕС…СЂР°РЅСЏРµС‚ Р°РєС‚СѓР°Р»СЊРЅС‹Р№ capability snapshot Р°РіРµРЅС‚Р°.

РРјРµРЅРЅРѕ Р·РґРµСЃСЊ СЃРµР№С‡Р°СЃ РїСЂРѕРёСЃС…РѕРґРёС‚ Р»РѕРіРёС‡РµСЃРєР°СЏ РїСЂРѕРІРµСЂРєР°, С‡С‚Рѕ Р°РіРµРЅС‚ "РїРѕРґРєР»СЋС‡РёР»СЃСЏ" Рё РіРѕС‚РѕРІ Рє СЂР°Р±РѕС‚Рµ: РµСЃР»Рё snapshot РЅРµ СЃРѕР±СЂР°РЅ, РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРёРµ РєРѕРјР°РЅРґС‹ СЃС‡РёС‚Р°СЋС‚СЃСЏ РЅРµРґРѕСЃС‚СѓРїРЅС‹РјРё.

### 2. РџРѕР»СЊР·РѕРІР°С‚РµР»СЊ РІРІРѕРґРёС‚ РєРѕРјР°РЅРґСѓ

РџСЂРёРјРµСЂ:

```text
tool post 2
```

- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/App.kt)
  Р§РёС‚Р°РµС‚ СЃС‚СЂРѕРєСѓ РёР· РєРѕРЅСЃРѕР»Рё Рё РїРµСЂРµРґР°С‘С‚ РµС‘ РІ parser.
- [DefaultCliCommandParser.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/presentation/cli/DefaultCliCommandParser.kt)
  Р Р°Р·Р±РёСЂР°РµС‚ CLI-РІРІРѕРґ Рё РїСЂРµРІСЂР°С‰Р°РµС‚ РµРіРѕ РІ `ToolPostCommand(postId = 2)`.

### 3. Workflow РїРµСЂРµРІРѕРґРёС‚ РІРІРѕРґ РІ Р°РіРµРЅС‚РЅС‹Р№ СЃС†РµРЅР°СЂРёР№

- [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
  Р”Р»СЏ `ToolPostCommand` СЃРѕР·РґР°С‘С‚ `AgentRequest.CallAvailableCommand` СЃ `AgentCommandId.TOOL_POST` Рё Р°СЂРіСѓРјРµРЅС‚Р°РјРё РєРѕРјР°РЅРґС‹.

### 4. РђРіРµРЅС‚ РІС‹Р±РёСЂР°РµС‚ РєРѕРјР°РЅРґСѓ Рё РјР°СЂС€СЂСѓС‚

- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  РС‰РµС‚ РєРѕРјР°РЅРґСѓ РІ `AgentCapabilityRegistry`.
- [ToolPostAgentCommandDefinition.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/ToolPostAgentCommandDefinition.kt)
  РћРїРёСЃС‹РІР°РµС‚ РєРѕРјР°РЅРґСѓ `tool post <postId>`.
- [CommandRouting.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/bootstrap/commands/models/CommandRouting.kt)
  РћРїСЂРµРґРµР»СЏРµС‚ РїСЂР°РІРёР»Рѕ РјР°СЂС€СЂСѓС‚РёР·Р°С†РёРё РєРѕРјР°РЅРґС‹ РЅР° СЃРµСЂРІРµСЂ.

Р•СЃР»Рё capability snapshot РїСѓСЃС‚РѕР№ РёР»Рё РєРѕРјР°РЅРґР° РЅРµ РЅР°Р№РґРµРЅР°, Р°РіРµРЅС‚ РІРѕР·РІСЂР°С‰Р°РµС‚ РїСЂРёРєР»Р°РґРЅСѓСЋ РѕС€РёР±РєСѓ. РўРѕ РµСЃС‚СЊ РїСЂРѕРІРµСЂРєР° РґРѕСЃС‚СѓРїРЅРѕСЃС‚Рё РєРѕРјР°РЅРґС‹ С‚РµРїРµСЂСЊ РЅР°С…РѕРґРёС‚СЃСЏ РІ Р°РіРµРЅС‚Рµ, Р° РЅРµ РІ `App`.

### 5. MCP client РІС‹Р·С‹РІР°РµС‚ tool

- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  РџСЂРµРІСЂР°С‰Р°РµС‚ РґРѕСЃС‚СѓРїРЅСѓСЋ РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєСѓСЋ РєРѕРјР°РЅРґСѓ РІ `AgentRequest.CallTool`.
- [DefaultMcpClient.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/mcp/client/DefaultMcpClient.kt)
  РЎРѕР·РґР°С‘С‚ SDK client, РІС‹Р·С‹РІР°РµС‚ `tools/call`, РјР°РїРїРёС‚ РѕС‚РІРµС‚ РІ РїСЂРѕРµРєС‚РЅСѓСЋ РјРѕРґРµР»СЊ Рё Р·Р°РєСЂС‹РІР°РµС‚ СЃРѕРµРґРёРЅРµРЅРёРµ.

### 6. РћС‚РІРµС‚ РёРґС‘С‚ РѕР±СЂР°С‚РЅРѕ Рє РїРѕР»СЊР·РѕРІР°С‚РµР»СЋ

- [DefaultAgent.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/agent/DefaultAgent.kt)
  Р’РѕР·РІСЂР°С‰Р°РµС‚ `AgentResponse.ToolCallSuccess` РёР»Рё `AgentResponse.Failure`.
- [DefaultWorkflowCommandHandler.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/workflow/service/DefaultWorkflowCommandHandler.kt)
  РџСЂРµРѕР±СЂР°Р·СѓРµС‚ Р°РіРµРЅС‚РЅС‹Р№ РѕС‚РІРµС‚ РІ `ToolCallResult`.
- [DefaultCliOutputFormatter.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/presentation/cli/DefaultCliOutputFormatter.kt)
  Р¤РѕСЂРјР°С‚РёСЂСѓРµС‚ СЂРµР·СѓР»СЊС‚Р°С‚ РІ С‚РµРєСЃС‚ РґР»СЏ РєРѕРЅСЃРѕР»Рё.
- [App.kt](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/src/main/kotlin/ru/compadre/mcp/App.kt)
  РџРµС‡Р°С‚Р°РµС‚ РёС‚РѕРіРѕРІС‹Р№ РІС‹РІРѕРґ РїРѕР»СЊР·РѕРІР°С‚РµР»СЋ.

## РўСЂР°РЅСЃРїРѕСЂС‚

РџСЂРѕРµРєС‚ РёСЃРїРѕР»СЊР·СѓРµС‚ Streamable HTTP transport. РќР° С‚РµРєСѓС‰РµРј СЌС‚Р°РїРµ СЃРµСЂРІРµСЂ РѕРїСѓР±Р»РёРєРѕРІР°РЅ С‡РµСЂРµР· stateless-РІР°СЂРёР°РЅС‚, РїРѕС‚РѕРјСѓ С‡С‚Рѕ РѕРЅ РїСЂРѕС‰Рµ РґР»СЏ РјРёРЅРёРјР°Р»СЊРЅРѕРіРѕ reference-СЃС†РµРЅР°СЂРёСЏ Рё РЅРµ С‚СЂРµР±СѓРµС‚ РѕС‚РґРµР»СЊРЅРѕРіРѕ server-side session lifecycle.

## Р‘С‹СЃС‚СЂС‹Р№ СЃС‚Р°СЂС‚

РЎР±РѕСЂРєР° РїСЂРѕРµРєС‚Р°:

```powershell
.\gradlew.bat build
```

РЎР±РѕСЂРєР° direct launcher-Р°СЂС‚РµС„Р°РєС‚РѕРІ:

```powershell
.\gradlew.bat installClientDist installServerDist
```

РџРѕСЃР»Рµ СЌС‚РѕРіРѕ Р±СѓРґСѓС‚ РґРѕСЃС‚СѓРїРЅС‹:

- `build\install\mcp-client\bin\mcp-client.bat`
- `build\install\mcp-server\bin\mcp-server.bat`

Р СѓС‡РЅРѕР№ Р·Р°РїСѓСЃРє СЃРµСЂРІРµСЂР°:

```powershell
.\build\install\mcp-server\bin\mcp-server.bat
```

Р СѓС‡РЅРѕР№ Р·Р°РїСѓСЃРє РєР»РёРµРЅС‚Р°:

```powershell
.\build\install\mcp-client\bin\mcp-client.bat
```

## РљРѕРјР°РЅРґС‹ РїСЂРѕРµРєС‚Р°

РўРµС…РЅРёС‡РµСЃРєРёРµ Gradle entrypoint'С‹:

```powershell
.\gradlew.bat runServer
.\gradlew.bat runClient
```

Scripted-Р·Р°РїСѓСЃРє РєР»РёРµРЅС‚Р° РґР»СЏ smoke/e2e:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\invoke-client-commands.ps1 -Commands help,"tool posts",exit
```

РџРѕРґРіРѕС‚РѕРІРєР° СЂСѓС‡РЅРѕР№ РїСЂРѕРІРµСЂРєРё РѕРґРЅРёРј Р·Р°РїСѓСЃРєРѕРј:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1
```

Р”Р»СЏ СЌС‚РѕРіРѕ СЂРµРїРѕР·РёС‚РѕСЂРёСЏ РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєР°СЏ С„СЂР°Р·Р° `СЃРѕР±РµСЂРё РїСЂРѕРµРєС‚` РїРѕ СѓРјРѕР»С‡Р°РЅРёСЋ С‚СЂР°РєС‚СѓРµС‚СЃСЏ РёРјРµРЅРЅРѕ РєР°Рє СЌС‚РѕС‚ workflow.

Р—Р°РїСѓСЃРє СѓР¶Рµ СЃРѕР±СЂР°РЅРЅС‹С… Р°СЂС‚РµС„Р°РєС‚РѕРІ Р±РµР· РЅРѕРІРѕР№ СЃР±РѕСЂРєРё:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -SkipBuild
```

Р”Р»СЏ СЌС‚РѕРіРѕ СЂРµРїРѕР·РёС‚РѕСЂРёСЏ РїРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєР°СЏ С„СЂР°Р·Р° `Р·Р°РїСѓСЃС‚Рё РїСЂРѕРµРєС‚` РїРѕ СѓРјРѕР»С‡Р°РЅРёСЋ С‚СЂР°РєС‚СѓРµС‚СЃСЏ РёРјРµРЅРЅРѕ РєР°Рє СЌС‚РѕС‚ РІР°СЂРёР°РЅС‚.

РЎРєРІРѕР·РЅР°СЏ end-to-end РїСЂРѕРІРµСЂРєР°:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\check-e2e.ps1
```

Headless-РІР°СЂРёР°РЅС‚ СЂСѓС‡РЅРѕР№ РїСЂРѕРІРµСЂРєРё:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-manual-check.ps1 -Headless
```

## РџРѕР»СЊР·РѕРІР°С‚РµР»СЊСЃРєРёРµ РєРѕРјР°РЅРґС‹ CLI

РџРѕСЃР»Рµ РїРѕРґРіРѕС‚РѕРІРєРё Р°РіРµРЅС‚Р° РїРѕР»СЊР·РѕРІР°С‚РµР»СЋ РґРѕСЃС‚СѓРїРЅС‹:

- `tool posts` вЂ” РїРѕРєР°Р·Р°С‚СЊ РїРµСЂРІС‹Рµ 10 РїСѓР±Р»РёРєР°С†РёР№ РёР· `JSONPlaceholder`;
- `tool post <postId>` вЂ” РїРѕРєР°Р·Р°С‚СЊ РѕРґРЅСѓ РїСѓР±Р»РёРєР°С†РёСЋ РїРѕ РёРґРµРЅС‚РёС„РёРєР°С‚РѕСЂСѓ;
- `help` вЂ” РїРѕРєР°Р·Р°С‚СЊ СЃРїРёСЃРѕРє РґРѕСЃС‚СѓРїРЅС‹С… РєРѕРјР°РЅРґ;
- `exit` вЂ” Р·Р°РІРµСЂС€РёС‚СЊ СЃРµСЃСЃРёСЋ РєР»РёРµРЅС‚Р°.

## Р”РѕРєСѓРјРµРЅС‚Р°С†РёСЏ

- РїСЂРѕРµРєС‚РЅС‹Р№ `MemoryBank`: [MemoryBank/README.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/MemoryBank/README.md)
- preflight РґР»СЏ РЅРѕРІС‹С… СЃРµСЃСЃРёР№: [MemoryBank/agent-preflight.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/MemoryBank/agent-preflight.md)
- РўР— РїРѕ MCP reference-С‡Р°СЃС‚Рё: [docs/technical-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/technical-spec.md)
- Р¶СѓСЂРЅР°Р» СЂРµР°Р»РёР·Р°С†РёРё MCP reference-С‡Р°СЃС‚Рё: [docs/mcp-reference-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/mcp-reference-implementation-log.md)
- РўР— РїРѕ Р°РіРµРЅС‚РЅРѕР№ Р°СЂС…РёС‚РµРєС‚СѓСЂРµ: [docs/agent-architecture-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/agent-architecture-spec.md)
- Р¶СѓСЂРЅР°Р» СЂРµР°Р»РёР·Р°С†РёРё Р°РіРµРЅС‚РЅРѕР№ Р°СЂС…РёС‚РµРєС‚СѓСЂС‹: [docs/agent-architecture-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/agent-architecture-implementation-log.md)
- РўР— РїРѕ РёРЅС‚РµРіСЂР°С†РёРё MCP tool: [docs/mcp-tool-integration-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/mcp-tool-integration-spec.md)
- Р¶СѓСЂРЅР°Р» СЂРµР°Р»РёР·Р°С†РёРё РёРЅС‚РµРіСЂР°С†РёРё MCP tool: [docs/mcp-tool-integration-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/mcp-tool-integration-implementation-log.md)
- РєРѕРЅС‚СЂР°РєС‚ РїРµСЂРІРѕРіРѕ MCP tool: [docs/mcp-tool-integration-contract.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/mcp-tool-integration-contract.md)
- РўР— РїРѕ agent capability bootstrap: [docs/mcp-agent-capability-bootstrap-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/mcp-agent-capability-bootstrap-spec.md)
- capability model Р°РіРµРЅС‚Р°: [docs/mcp-agent-capability-model.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/mcp-agent-capability-model.md)
- Р¶СѓСЂРЅР°Р» СЂРµР°Р»РёР·Р°С†РёРё agent capability bootstrap: [docs/mcp-agent-capability-bootstrap-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/mcp-agent-capability-bootstrap-implementation-log.md)
- РўР— РїРѕ refactor command definitions: [docs/agent-command-definition-refactor-spec.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/agent-command-definition-refactor-spec.md)
- Р¶СѓСЂРЅР°Р» СЂРµР°Р»РёР·Р°С†РёРё refactor command definitions: [docs/agent-command-definition-refactor-implementation-log.md](/C:/Users/compadre/Downloads/Projects/AiAdvent/day_18/docs/agent-command-definition-refactor-implementation-log.md)
