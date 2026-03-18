package org.springdoc.demo.mcp.tools;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SessionTools {

	private static final Logger log = LoggerFactory.getLogger(SessionTools.class);
	private Conference conference;
	private final ObjectMapper objectMapper;

	public SessionTools(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Tool(name = "conference-sessions-by-date", description = "Returns the count of sessions by date")
	public Map<String, Long> countSessionsByDate() {
		return conference.sessions().stream()
				.collect(Collectors.groupingBy(
						Session::day,
						Collectors.counting()
				));
	}

	@Tool(name = "conference-sessions", description = "Returns all sessions for the conference")
	public List<Session> findAllSessions() {
		return conference.sessions();
	}

	@PostConstruct
	public void init() {
		log.info("Loading Sessions from JSON file 'sessions.json'");
		try (InputStream inputStream = getClass().getResourceAsStream("/data/sessions.json")) {
			JsonNode root = objectMapper.readTree(inputStream);
			JsonNode confNode = root.get("conference");
			String name = confNode.get("name").asText();
			int year = confNode.get("year").asInt();
			String[] dates = objectMapper.treeToValue(confNode.get("dates"), String[].class);
			String location = confNode.get("location").asText();
			List<Session> sessions = objectMapper.readerForListOf(Session.class).readValue(root.get("sessions"));
			this.conference = new Conference(name, year, dates, location, sessions);
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to read JSON data", e);
		}
	}
}
