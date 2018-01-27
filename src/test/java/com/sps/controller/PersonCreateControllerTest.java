package com.sps.controller;


import com.sps.entity.Person;
import com.sps.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/dispatcher-servlet.xml")
public class PersonCreateControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PersonCreateController personCreateController;

    @Mock
    private PersonService personServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(personCreateController)
                .build();
    }

    @Test
    public void TestGetCreatePerson() throws Exception {
        mockMvc.perform(get("/createPerson"))
                .andExpect(status().isOk())
                .andExpect(view().name("personCreate"))
                .andExpect(forwardedUrl("personCreate"))
                .andExpect(model().attribute("person", instanceOf(Person.class)));
    }

    @Test
    public void TestFailedCreatePerson() throws Exception {
        mockMvc.perform(get("/createPersonFailed"))
                .andExpect(status().isOk())
                .andExpect(view().name("personCreate"))
                .andExpect(forwardedUrl("personCreate"));
    }

    @Test
    public void TestPostCreatePersonSuccess() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("Kaan");
        person.setLastName("Aktas");

        mockMvc.perform(post("/createPerson")
                .sessionAttr("person", person))
                .andExpect(status().isFound())
                .andExpect(model().attributeExists())
                .andExpect(redirectedUrl("/mvc/listPersons"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attribute("message", "Person created. Person id :1"));
    }

    @Test
    public void TestPostCreatePersonFailed() throws Exception {

        mockMvc.perform(post("/createPerson")
                .sessionAttr("person", new ArrayList<>()))
                .andExpect(status().isFound())
                .andExpect(model().attributeExists())
                .andExpect(redirectedUrl("/mvc/createPersonFailed"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attribute("message", "Person create failed"));
    }
}
