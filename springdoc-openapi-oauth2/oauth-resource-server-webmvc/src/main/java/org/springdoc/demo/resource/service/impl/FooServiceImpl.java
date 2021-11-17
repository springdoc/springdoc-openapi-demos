package org.springdoc.demo.resource.service.impl;

import java.util.Optional;

import org.springdoc.demo.resource.model.Foo;
import org.springdoc.demo.resource.repository.IFooRepository;
import org.springdoc.demo.resource.service.IFooService;

import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl implements IFooService {

    private IFooRepository fooRepository;

    public FooServiceImpl(IFooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @Override
    public Optional<Foo> findById(Long id) {
        return fooRepository.findById(id);
    }

    @Override
    public Foo save(Foo foo) {
        return fooRepository.save(foo);
    }

    @Override
    public Iterable<Foo> findAll() {
        return fooRepository.findAll();
    }
}
