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
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/dispatcher-servlet.xml")
public class PersonListControllerTest {

    static List<Person> personList;

    static {
        Person person_1 = new Person();
        person_1.setId(1L);
        person_1.setFirstName("Kaan");
        person_1.setLastName("Aktas");
        Person person_2 = new Person();
        person_2.setId(2L);
        person_2.setFirstName("Thomas");
        person_2.setLastName("Brown");
        personList = Arrays.asList(person_1, person_2);
    }

    private final static String personListModelAttribute = "persons";
    private MockMvc mockMvc;

    @InjectMocks
    private PersonListController personListController;

    @Mock
    private PersonService personServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(personListController)
                .build();
    }

    @Test
    public void TestFindAllPersons() throws Exception {
        when(personServiceMock.findAll()).thenReturn(personList);

        mockMvc.perform(get("/listPersons"))
                .andExpect(status().isOk())
                .andExpect(view().name("personList"))
                .andExpect(forwardedUrl("personList"))
                .andExpect(model().attribute(personListModelAttribute, hasSize(2)))
                .andExpect(model().attribute(personListModelAttribute, hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("firstName", is("Kaan")),
                                hasProperty("lastName", is("Aktas"))
                        )
                )))
                .andExpect(model().attribute(personListModelAttribute, hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("firstName", is("Thomas")),
                                hasProperty("lastName", is("Brown"))
                        )
                )));

        verify(personServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(personServiceMock);
    }

    @Test
    public void TestFindAllPersonsNull() throws Exception {
        when(personServiceMock.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/listPersons"))
                .andExpect(status().isOk())
                .andExpect(view().name("personList"))
                .andExpect(model().attribute(personListModelAttribute, emptyList()));

        verify(personServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(personServiceMock);
    }
}
