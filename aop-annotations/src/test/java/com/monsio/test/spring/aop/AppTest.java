package com.monsio.test.spring.aop;

import static org.junit.Assert.assertTrue;

import com.monsio.test.spring.aop.model.Personne;
import com.monsio.test.spring.aop.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {

        assertTrue(true);
    }

    private PersonService initPersonService() {

        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();

        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(genericApplicationContext);

        xmlReader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));

        genericApplicationContext.refresh();

        PersonService personService = (PersonService) genericApplicationContext.getBean("personService");

        return personService;
    }

    /**
     * BEFORE
     * */
    @Test
    public void personServiceBeforeShowTest() {

        PersonService personService = initPersonService();

        try {

            personService.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    public void personServiceAfterAddAndReturnTest() {

        PersonService personService = initPersonService();

        Personne personne = personService.add(new Personne("Jhon", "Doe"));

        log.info(personne.toString());
    }

    @Test
    public void personServiceAfterDeleteWithExceptionTest() {

        PersonService personService = initPersonService();

        try {

            personService.delete();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * AROUND
     * */
    @Test
    public void personServiceAroundUpdateTest() {

        PersonService personService = initPersonService();

        personService.update();
    }

    /**
     * AFTER
     * */
    @Test
    public void personServiceAfterGetTest() {

        PersonService personService = initPersonService();

        personService.get(0);
    }

    @Test
    public void personServiceBeforeAfterFindTest() {

        PersonService personService = initPersonService();

        personService.find("James");
    }

}
