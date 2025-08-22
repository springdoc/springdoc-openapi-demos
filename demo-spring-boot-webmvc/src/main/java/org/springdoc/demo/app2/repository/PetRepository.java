/*
 *
 *  * Copyright 2019-2020 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.springdoc.demo.app2.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springdoc.demo.app2.model.Pet;
import org.springdoc.demo.app2.model.Tag;

import org.springframework.stereotype.Repository;

@Repository
public class PetRepository extends HashMapRepository<Pet, Long> {

	private Long sequenceId = 1L;

	public PetRepository() {
		super(Pet.class);
	}

	@Override
	<S extends Pet> Long getEntityId(S pet) {
		return pet.getId();
	}

	@Override
	public <S extends Pet> S save(S pet) {
		if (pet.getId() != null && pet.getId() > sequenceId) {
			sequenceId = pet.getId() + 1;
		}
		if (pet.getId() == null) {
			pet.setId(sequenceId);
			sequenceId += 1;
		}
		return super.save(pet);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> iterable) {

	}

	public List<Pet> findPetsByStatus(List<Pet.StatusEnum> statusList) {
		return entities.values().stream()
				.filter(entity -> entity.getStatus() != null)
				.filter(entity -> statusList.contains(entity.getStatus()))
				.collect(Collectors.toList());
	}

	public List<Pet> findPetsByTags(List<String> tags) {
		return entities.values().stream()
				.filter(entity -> entity.getTags() != null)
				.filter(entity -> entity.getTags().stream()
						.map(Tag::getName)
						.anyMatch(tags::contains)
				)
				.collect(Collectors.toList());
	}
}
