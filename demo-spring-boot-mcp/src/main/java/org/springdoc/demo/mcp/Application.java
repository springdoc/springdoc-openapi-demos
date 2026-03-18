package org.springdoc.demo.mcp;

import io.modelcontextprotocol.server.McpServerFeatures;
import org.springdoc.demo.mcp.tools.ConferencePrompts;
import org.springdoc.demo.mcp.tools.SessionTools;
import org.springdoc.demo.mcp.tools.SpeakersResource;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ToolCallbackProvider sessionToolCallbacks(SessionTools sessionTools) {
		ToolCallback[] callbacks = ToolCallbacks.from(sessionTools);
		return () -> callbacks;
	}

	@Bean
	public List<McpServerFeatures.SyncPromptSpecification> conferencePromptSpecs(ConferencePrompts prompts) {
		return prompts.listPrompts();
	}

	@Bean
	public List<McpServerFeatures.SyncResourceSpecification> speakerResourceSpecs(SpeakersResource speakersResource) {
		return speakersResource.listResources();
	}
}
