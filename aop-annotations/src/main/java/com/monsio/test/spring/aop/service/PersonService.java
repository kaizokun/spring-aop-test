package com.monsio.test.spring.aop.service;

import com.monsio.test.spring.aop.model.Personne;

public interface PersonService {

    void show();

    void delete() throws Exception;

    Personne add(Personne personne);

    void update();

    void get(int id);

    Personne find(String name);
}
