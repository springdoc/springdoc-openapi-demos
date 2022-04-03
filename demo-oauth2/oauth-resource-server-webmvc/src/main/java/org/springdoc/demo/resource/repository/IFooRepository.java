package org.springdoc.demo.resource.repository;

import org.springdoc.demo.resource.model.Foo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFooRepository extends PagingAndSortingRepository<Foo, Long>, CrudRepository<Foo, Long> {
}
