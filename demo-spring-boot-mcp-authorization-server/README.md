# springdoc-openapi MCP Authorization Server

OAuth2 Authorization Server companion for the [demo-spring-boot-mcp](../demo-spring-boot-mcp) demo.

This server issues OAuth2 tokens that MCP clients use to authenticate with the MCP server endpoint.

## Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security-oauth2-authorization-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springaicommunity</groupId>
    <artifactId>mcp-authorization-server</artifactId>
</dependency>
```

- `spring-boot-starter-security-oauth2-authorization-server` — Spring Authorization Server
- `mcp-authorization-server` — MCP-specific authorization server integration from Spring AI Community

## Running

```sh
mvn spring-boot:run
```

The authorization server starts on port **9000**.

## Configuration

| Property | Value |
|----------|-------|
| Client ID | `mcp-demo-client` |
| Client Secret | `mcp-demo-secret` |
| Default user | `user` / `password` |
| Grant types | `authorization_code`, `client_credentials` |
| Access token TTL | 1 hour |

### Registered redirect URIs

- `http://localhost:8089/authorize/oauth2/code/authserver`
- `http://localhost:6274/oauth/callback` (MCP Inspector)
- `https://claude.ai/api/mcp/auth_callback` (Claude Desktop)

## Usage with demo-spring-boot-mcp

1. Start this authorization server first (`mvn spring-boot:run`)
2. Then start the MCP server (`cd ../demo-spring-boot-mcp && mvn spring-boot:run`)
3. MCP clients connecting to `http://localhost:8089/mcp` will be redirected to this server for authentication
