package org.springdoc.demo.mcp;

import org.junit.jupiter.api.Test;
import org.springdoc.demo.mcp.tools.SessionTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SessionToolsTest {

	@Autowired
	SessionTools sessionTools;

	@Test
	void testCountSessionsByDate() {
		Map<String, Long> sessionsByDate = sessionTools.countSessionsByDate();

		assertThat(sessionsByDate).isNotEmpty();
		assertThat(sessionsByDate.keySet()).allMatch(date -> date != null && !date.isEmpty());
		assertThat(sessionsByDate.values()).allMatch(count -> count > 0);

		assertThat(sessionsByDate.keySet()).contains("2026-04-14", "2026-04-15");
	}

	@Test
	void testFindAllSessions() {
		var allSessions = sessionTools.findAllSessions();

		assertThat(allSessions).isNotEmpty();
		assertThat(allSessions).hasSize(25);  // sessions from Spring I/O 2026

		var firstSession = allSessions.get(0);
		assertThat(firstSession.title()).isNotBlank();
		assertThat(firstSession.day()).isNotBlank();
		assertThat(firstSession.speakers()).isNotEmpty();
		assertThat(firstSession.location()).isNotBlank();
	}

	@Test
	void testSessionDataConsistency() {
		Map<String, Long> sessionsByDate = sessionTools.countSessionsByDate();
		var allSessions = sessionTools.findAllSessions();

		long expectedTotal = sessionsByDate.values().stream().mapToLong(Long::longValue).sum();
		assertThat(allSessions).hasSize((int) expectedTotal);

		for (String date : sessionsByDate.keySet()) {
			long sessionsForDate = allSessions.stream()
					.filter(session -> date.equals(session.day()))
					.count();
			assertThat(sessionsForDate).isEqualTo(sessionsByDate.get(date));
		}
	}
}
