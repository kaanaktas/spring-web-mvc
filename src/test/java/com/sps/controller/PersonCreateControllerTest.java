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

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
    public void TestPostCreatePerson() throws Exception {
        Person person_1 = new Person();
        person_1.setId(1L);
        person_1.setFirstName("Kaan");
        person_1.setLastName("Aktas");

        doNothing().when(personServiceMock).create(person_1);

        mockMvc.perform(post("/createPerson")
                .param("id", "1")
                .param("firstName", "Kaan")
                .param("lastName", "Aktas")
                .sessionAttr("person", new ArrayList<>(0)))
                .andExpect(status().isFound());
    }


}
