package com.lexsoft.project.beer.database.repository.impl;

import org.springframework.stereotype.Repository;

@Repository
public class HealthcheckRepository extends GenericDAO<Object> {

    @Override
    public void checkConnection() {
        super.checkConnection();
    }

}
