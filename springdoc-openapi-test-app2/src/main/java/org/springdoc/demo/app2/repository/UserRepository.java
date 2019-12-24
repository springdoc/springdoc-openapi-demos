package org.springdoc.demo.app2.repository;

import org.springdoc.demo.app2.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends HashMapRepository<User, String> {

    public UserRepository() {
        super(User.class);
    }

    @Override
    <S extends User> String getEntityId(S user) {
        return user.getUsername();
    }
}
