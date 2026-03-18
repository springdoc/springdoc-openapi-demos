package org.springdoc.demo.mcp.tools;

import tools.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SpeakersResource {

	private static final Logger log = LoggerFactory.getLogger(SpeakersResource.class);
	private final ObjectMapper objectMapper;

	public SpeakersResource(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public List<McpServerFeatures.SyncResourceSpecification> listResources() {
		var speakerResource = new McpSchema.Resource(
				"conference://speakers/keynote-speaker",
				"Biographical information and expertise details for the keynote speaker - use when users ask about speaker background information",
				"application/json",
				"conference://speakers/keynote-speaker",
				null
		);

		return List.of(
				new McpServerFeatures.SyncResourceSpecification(
						speakerResource,
						(exchange, request) -> new McpSchema.ReadResourceResult(
								List.of(new McpSchema.TextResourceContents(
										"conference://speakers/keynote-speaker",
										"application/json",
										getKeynoteSpeakerData()
								))
						)
				)
		);
	}

	private String getKeynoteSpeakerData() {
		try {
			Map<String, Object> speakerInfo = Map.of(
					"name", "Alice Martin",
					"title", "Senior Developer Advocate",
					"bio", "A seasoned developer advocate with over 15 years of experience in software development, passionate about helping developers learn and build amazing applications with Spring Boot, Spring Framework, and the broader Java ecosystem.",
					"expertise", List.of(
							"Spring Boot & Spring Framework",
							"Java Development",
							"Cloud Native Applications",
							"Developer Education",
							"Spring AI & AI Integration",
							"Model Context Protocol (MCP)"
					),
					"speaking", Map.of(
							"topics", List.of(
									"Spring Boot & Spring Framework",
									"Java Development Best Practices",
									"Spring AI and AI Integration",
									"Modern Web Development",
									"Developer Productivity"
							)
					),
					"conferenceSessions", List.of(
							Map.of(
									"title", "API Versioning in Spring",
									"day", "2025-09-17",
									"time", "09:00",
									"room", "Room 301"
							),
							Map.of(
									"title", "Roundtable with the Spring Cloud Team",
									"day", "2025-09-15",
									"time", "13:30",
									"room", "Experts Lounge, Table 6"
							)
					)
			);

			return objectMapper.writeValueAsString(speakerInfo);
		}
		catch (Exception e) {
			log.error("Error generating speaker data", e);
			return "{}";
		}
	}
}
