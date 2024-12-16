package br.com.jb.api_gateway.service;

import br.com.jb.api_gateway.controller.PersonController;
import br.com.jb.api_gateway.data.vo.v1.PersonVO;
import br.com.jb.api_gateway.exception.RequiredObjectIsNullException;
import br.com.jb.api_gateway.exception.ResourceNotFoundException;
import br.com.jb.api_gateway.mapper.PersonMapper;
import br.com.jb.api_gateway.model.Person;
import br.com.jb.api_gateway.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    public PersonVO findById(Long id) {
        logger.info("Buscando pessoa");

        // Busca a entidade do repositório
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this id"));

        // Usa o MapStruct para converter a entidade em VO
        var vo = mapper.toPersonVO(entity);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<PersonVO> findAll() {
        logger.info("Buscando todas as pessoas");

        // Busca todas as entidades do repositório e converte para VO
        return repository.findAll().stream()
                .map(person -> {
                    var vo = mapper.toPersonVO(person);
                    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    public PersonVO create(PersonVO personVO) {
        if (personVO == null) throw new RequiredObjectIsNullException();

        logger.info("Criando pessoa");

        // Converte VO para entidade, salva no banco e converte de volta para VO
        var entity = mapper.toPerson(personVO);
        var vo = mapper.toPersonVO(repository.saveAndFlush(entity));
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO personVO) {
        if (personVO == null) throw new RequiredObjectIsNullException();

        logger.info("Atualizando pessoa");

        // Verifica se a entidade existe e atualiza os campos
        var entity = repository.findById(personVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this id"));

        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setAddress(personVO.getAddress());
        entity.setGender(personVO.getGender());

        // Salva a entidade atualizada e converte para VO
        var vo = mapper.toPersonVO(repository.saveAndFlush(entity));
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deletando pessoa");

        // Verifica se o registro existe e o remove
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}
