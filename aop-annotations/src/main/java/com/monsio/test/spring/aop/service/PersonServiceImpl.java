package com.monsio.test.spring.aop.service;

import com.monsio.test.spring.aop.model.Personne;
import org.springframework.stereotype.Component;

@Component("personService")
public class PersonServiceImpl implements PersonService {



    @Override
    public void show() {
        sleep(200);
    }

    @Override
    public void delete() throws Exception {

        sleep(210);

        throw new Exception("Suppression impossible");
    }

    @Override
    public Personne add(final Personne personne) {

        sleep(220);

        return personne;
    }

    @Override
    public void update() {

        sleep(230);
    }

    @Override
    public void get(int id) {

        sleep(240);
    }

    @Override
    public Personne find(String name) {

        sleep(250);

        return new Personne(name, "Doe");
    }

    private void sleep(int ms){

        try {

            Thread.sleep(ms);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}