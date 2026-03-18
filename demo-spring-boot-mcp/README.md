# springdoc-openapi demo with Spring AI MCP Server

This demo showcases `springdoc-openapi-starter-webmvc-mcp`, which bridges **Spring AI MCP Server** and **springdoc-openapi** to automatically expose your REST endpoints as MCP tools — alongside manually defined MCP tools, prompts, and resources.

## What is springdoc-openapi-starter-webmvc-mcp?

`springdoc-openapi-starter-webmvc-mcp` automatically converts your Spring MVC REST endpoints into MCP (Model Context Protocol) tools. This means AI agents can discover and invoke your existing REST APIs without any additional code.

Key features:
- **Automatic REST-to-MCP tool conversion**: Every `@RestController` endpoint becomes an MCP tool that AI agents can call
- **`@McpIgnore` annotation**: Exclude specific endpoints from MCP tool exposure (e.g., PATCH operations, internal APIs)
- **Coexistence with manual MCP tools**: Works alongside manually defined `@Tool` methods, prompts, and resources
- **OpenAPI metadata reuse**: Leverages your existing OpenAPI annotations (`@Operation`, `@Parameter`, `@Schema`) to generate rich MCP tool descriptions

## Demo structure

This demo includes:

| Component | Description |
|-----------|-------------|
| **REST endpoints** (`/api/book/*`, `/users/*`) | Automatically exposed as MCP tools via springdoc-openapi-starter-webmvc-mcp |
| **Manual MCP tools** (`SessionTools`) | `@Tool`-annotated methods registered as MCP tool callbacks |
| **MCP prompts** (`ConferencePrompts`) | Pre-built prompt templates for conference session analysis |
| **MCP resources** (`SpeakersResource`) | Static speaker data accessible via `conference://speakers/keynote-speaker` |

## Dependencies

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-mcp</artifactId>
</dependency>
```

- `spring-ai-starter-mcp-server-webmvc` — Spring AI MCP server with WebMVC transport
- `springdoc-openapi-starter-webmvc-mcp` — Automatically exposes REST endpoints as MCP tools

## Building

### Pre-requisites

- JDK 17+
- Maven 3

```sh
mvn clean package
```

## Running

### Default mode (Streamable HTTP)

```sh
mvn spring-boot:run
```

The MCP server starts on port **8089** with the streamable HTTP endpoint at `/mcp`.

### SSE mode

```sh
mvn spring-boot:run -Psse
```

Exposes SSE transport at `/sse` with message endpoint at `/mcp/message`.

### STDIO mode

```sh
mvn spring-boot:run -Pstdout
```

Runs the MCP server in STDIO mode (no web server) for use with tools like Claude Desktop.

## Testing with the MCP Dashboard UI

After starting the application, open the built-in MCP Developer Dashboard in your browser:

```
http://localhost:8089/mcp-ui/index.html
```

The dashboard lets you:
- **Browse all MCP tools** — both auto-generated REST tools and manually defined `@Tool` methods
- **Execute tools** directly from the UI and inspect responses
- **View tool metadata** — HTTP method, path, input schema, safety classification
- **Test prompts and resources** registered with the MCP server

## How @McpIgnore works

The `@McpIgnore` annotation excludes specific REST endpoints from being exposed as MCP tools:

```java
@PatchMapping("/{id}")
@McpIgnore
public Book patchBook(@PathVariable("id") final String id, @RequestBody final Book book) {
    return book;
}
```

In this demo, the `PATCH /api/book/{id}` endpoint is excluded from MCP tool exposure while all other book endpoints remain available to AI agents.

## Running tests

```sh
mvn test
```

Tests verify:
- Application context loads successfully with both MCP and REST components
- Manual MCP tools return correct session data from Spring I/O 2026
- Session counts by date are consistent with the full session list
