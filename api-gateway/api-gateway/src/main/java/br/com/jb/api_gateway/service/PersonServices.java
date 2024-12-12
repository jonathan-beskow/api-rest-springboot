package br.com.jb.api_gateway.service;

import br.com.jb.api_gateway.data.vo.v1.PersonVO;
import br.com.jb.api_gateway.data.vo.v2.PersonVOV2;
import br.com.jb.api_gateway.exception.ResourceNotFoundException;
import br.com.jb.api_gateway.mapper.DozerMapper;
import br.com.jb.api_gateway.mapper.custom.PersonMapper;
import br.com.jb.api_gateway.model.Person;
import br.com.jb.api_gateway.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());
    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    public PersonVO findById(Long id) {
        logger.info("Buscando pessoa");

        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No Records found for this id"));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public List<PersonVO> findAll() {
        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        var entity = DozerMapper.parseObject(person, Person.class);
        return DozerMapper.parseObject(repository.saveAndFlush(entity), PersonVO.class);
    }

    public PersonVO update(PersonVO person) {
        var entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException("No Records found for this id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return DozerMapper.parseObject(repository.saveAndFlush(entity), PersonVO.class);
    }

    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No records found for this id");
        }

    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        var entity = mapper.convertVOToEntity(person);
        return mapper.convertEntityToVO(repository.saveAndFlush(entity));
    }
}
