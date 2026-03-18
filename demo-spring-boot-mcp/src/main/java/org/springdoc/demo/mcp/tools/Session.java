package org.springdoc.demo.mcp.tools;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Session(
		String day,
		String time,
		String duration,
		String title,
		String description,
		String type,
		String[] speakers,
		@JsonProperty("session_code") String sessionCode,
		String location
) {
}
