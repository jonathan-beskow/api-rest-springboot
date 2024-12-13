package br.com.jb.api_gateway.unittests.mockito.service;

import br.com.jb.api_gateway.data.vo.v1.PersonVO;
import br.com.jb.api_gateway.model.Person;
import br.com.jb.api_gateway.repositories.PersonRepository;
import br.com.jb.api_gateway.service.PersonServices;
import br.com.jb.api_gateway.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices services;

    @Mock
    PersonRepository repository;


    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void findById() {
        Person person = input.mockEntity();
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = services.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</person/1>;rel=\"self\"]"));
        assertEquals("Addres Test0", result.getAddress());
        assertEquals("First Name Test0", result.getFirstName());
        assertEquals("Last Name Test0", result.getLastName());
        assertEquals("Male", result.getGender());

    }

    @Test
    void findAll() {
    }

    @Test
    void create() {

        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = new Person();
        persisted.setId(1L);
        persisted.setAddress("Address Test1");
        persisted.setFirstName("First Name Test1");
        persisted.setLastName("Last Name Test1");
        persisted.setGender("Female");

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.saveAndFlush(entity)).thenReturn(persisted);

        var result = services.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</person/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void update() {

        Person entity = input.mockEntity(1);
        entity.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.saveAndFlush(entity)).thenReturn(entity);

        var result = services.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</person/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        services.delete(1L);
    }

}