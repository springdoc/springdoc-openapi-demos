package org.springdoc.demo.app1.sample1;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "items")
public class ItemController {

	@GetMapping("/items")
	public List<ItemDTO> showItems(@RequestParam("cusID") @Size(min = 4, max = 6) final String customerID,
			@Size(min = 4, max = 6) int toto) {
		return new ArrayList<ItemDTO>();
	}

	@PostMapping("/items")
	public ResponseEntity<URI> addItem(@Valid @RequestBody final ItemLightDTO itemDTO) {
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(UUID.randomUUID()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/items/demo")
	public List<ItemDTO> showItemsForObject(final ItemLightDTO itemDTO) {
		return new ArrayList<ItemDTO>();
	}

	@GetMapping(value = "/items/empty-params")
	@Operation(parameters = {
			@Parameter(name = "name", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class)) })
	public void get() {

	}
}