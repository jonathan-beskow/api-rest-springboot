package br.com.jb.api_gateway.controller;

import br.com.jb.api_gateway.data.vo.v1.PersonVO;
import br.com.jb.api_gateway.service.PersonServices;
import br.com.jb.api_gateway.util.MediaType;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/person")
@Tag(name = "People", description = "Endpoints de People")
public class PersonController {


    @Autowired
    private PersonServices service;

    @RequestMapping(value = "/{id}", produces = {br.com.jb.api_gateway.util.MediaType.APPLICATION_JSON, br.com.jb.api_gateway.util.MediaType.APPLICATION_XML, br.com.jb.api_gateway.util.MediaType.APPLICATION_YML})
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<PersonVO> findAll() {
        return service.findAll();
    }

    @PostMapping(value = "/create",
            produces = {br.com.jb.api_gateway.util.MediaType.APPLICATION_JSON, br.com.jb.api_gateway.util.MediaType.APPLICATION_XML,},
            consumes = {br.com.jb.api_gateway.util.MediaType.APPLICATION_JSON, br.com.jb.api_gateway.util.MediaType.APPLICATION_XML})
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) {
        return ResponseEntity.created(getCurrentUri(person)).body(service.create(person));
    }

    @PutMapping(value = "/update", produces = {br.com.jb.api_gateway.util.MediaType.APPLICATION_JSON, br.com.jb.api_gateway.util.MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<PersonVO> update(@RequestBody PersonVO person) {
        return ResponseEntity.ok().body(service.update(person));

    }

    @DeleteMapping(value = "/{id}", produces = {br.com.jb.api_gateway.util.MediaType.APPLICATION_JSON, br.com.jb.api_gateway.util.MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    public static URI getCurrentUri(PersonVO personId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personId.getKey())
                .toUri();
    }
}
