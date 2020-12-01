package com.lexsoft.project.beer.database.repository.impl;

import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.database.model.UserDb;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepository extends GenericDAO<UserDb> {

    public UserDb findUserByUsername(String username) {
        StringBuilder queryBuilder = new StringBuilder("SELECT u FROM UserDb u WHERE u.username = :username ");
        TypedQuery<UserDb> query = em.createQuery(queryBuilder.toString(), UserDb.class);
        query.setParameter("username",username);
        UserDb userDb = query.getSingleResult();
        return userDb;
    }


}
