package br.com.jb.api_gateway.service;

import br.com.jb.api_gateway.model.Person;
import br.com.jb.api_gateway.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    private Person person;

    public Optional<Person> findById(Long id) {
        logger.info("Buscando pessoa");
        return repository.findById(id);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person create(Person person) {
        return repository.saveAndFlush(person);
    }

    public Person update(Person person) {
        return repository.saveAndFlush(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
