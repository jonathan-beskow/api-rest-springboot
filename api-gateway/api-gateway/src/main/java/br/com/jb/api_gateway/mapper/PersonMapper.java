package br.com.jb.api_gateway.mapper;

import br.com.jb.api_gateway.data.vo.v1.PersonVO;
import br.com.jb.api_gateway.model.Person;


@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    // Mapeia de Entity para VO
    PersonVO toPersonVO(Person person);

    // Mapeia de VO para Entity
    Person toPerson(PersonVO personVO);
}
