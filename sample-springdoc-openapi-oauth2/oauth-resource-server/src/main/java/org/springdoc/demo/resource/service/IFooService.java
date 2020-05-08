package org.springdoc.demo.resource.service;

import java.util.Optional;

import org.springdoc.demo.resource.model.Foo;


public interface IFooService {
    Optional<Foo> findById(Long id);

    Foo save(Foo foo);
    
    Iterable<Foo> findAll();

}
