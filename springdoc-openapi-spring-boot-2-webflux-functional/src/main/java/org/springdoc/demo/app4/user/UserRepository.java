package org.springdoc.demo.app4.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Users")
public interface UserRepository {

	public Mono<User> getUserById(@Parameter(in = ParameterIn.PATH, description = "The user Id") Long id);

	@Operation(description = "get all the users")
	public Flux<User> getAllUsers();

	@Operation(description = "get all the users by firstname")
	public Flux<User> getAllUsers(String firstname);

	public Mono<Void> saveUser(Mono<User> user);

	public Mono<User> putUser(@Parameter(in = ParameterIn.PATH) Long id, Mono<User> user);

	public Mono<String> deleteUser(@Parameter(in = ParameterIn.PATH) Long id);
}
