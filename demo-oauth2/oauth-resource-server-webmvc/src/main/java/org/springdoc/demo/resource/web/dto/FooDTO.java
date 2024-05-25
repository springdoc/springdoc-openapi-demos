package org.springdoc.demo.resource.web.dto;

import java.util.UUID;

public class FooDTO {
	private UUID id;

	private String name;

	public FooDTO() {
		super();
	}

	public FooDTO(final UUID id, final String name) {
		super();

		this.id = id;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}