package br.com.jb.api_gateway.mapper.custom;

import br.com.jb.api_gateway.data.vo.v2.PersonVOV2;
import br.com.jb.api_gateway.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVO(Person person) {
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setAddress(person.getAddress());
        vo.setBirthDate(new Date());
        return vo;
    }

    public Person convertVOToEntity(PersonVOV2 person) {
        Person vo = new Person();
        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setAddress(person.getAddress());
        vo.setGender(person.getGender());
        //vo.setBirthDate(new Date());
        return vo;
    }
}
