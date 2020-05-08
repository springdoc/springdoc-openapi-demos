package org.springdoc.demo.resource.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springdoc.demo.resource.model.Foo;

public interface IFooRepository extends PagingAndSortingRepository<Foo, Long> {
}
