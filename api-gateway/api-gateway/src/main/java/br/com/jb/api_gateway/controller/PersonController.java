package br.com.jb.api_gateway.controller;

import br.com.jb.api_gateway.data.vo.v1.PersonVO;
import br.com.jb.api_gateway.service.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO findById(@PathVariable(value = "id") Long id ) {
        return service.findById(id);
    }

    @GetMapping
    public List<PersonVO> findAll() {return service.findAll();}

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) {
        return ResponseEntity.created(getCurrentUri(person)).body(service.create(person));
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonVO> update(@RequestBody PersonVO person) {
        return ResponseEntity.ok().body(service.update(person));

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    public static URI getCurrentUri(PersonVO personId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personId.getId())
                .toUri();
    }
}
