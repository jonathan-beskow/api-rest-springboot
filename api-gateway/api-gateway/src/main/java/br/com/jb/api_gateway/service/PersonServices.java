package br.com.jb.api_gateway.service;

import br.com.jb.api_gateway.controller.PersonController;
import br.com.jb.api_gateway.data.vo.v1.PersonVO;
import br.com.jb.api_gateway.exception.ResourceNotFoundException;
import br.com.jb.api_gateway.mapper.DozerMapper;
import br.com.jb.api_gateway.model.Person;
import br.com.jb.api_gateway.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());


    @Autowired
    private PersonRepository repository;

    public PersonVO findById(Long id) {
        logger.info("Buscando pessoa");

        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No Records found for this id"));
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<PersonVO> findAll() {
        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons.stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonVO create(PersonVO person) {
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.saveAndFlush(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {
        var entity = repository.findById(person.getKey()).orElseThrow(()-> new ResourceNotFoundException("No Records found for this id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo = DozerMapper.parseObject(repository.saveAndFlush(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No records found for this id");
        }

    }

}
