package org.springdoc.demo.app4.coffee;

import javax.annotation.PostConstruct;

import reactor.core.publisher.Flux;

import org.springframework.stereotype.Component;

@Component
public class DataLoader {
    private final CoffeeRepository repo;

    public DataLoader(CoffeeRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    private void load() {
        repo.deleteAll().thenMany(
                Flux.just("Kaldi's Coffee", "Philz Coffee", "Blue Bottle Coffee")
                        .map(Coffee::new)
                        .flatMap(repo::save))
                .thenMany(repo.findAll())
                .subscribe(System.out::println);
    }
}
