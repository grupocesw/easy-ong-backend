package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.enums.ContactType;
import br.com.grupocesw.easyong.response.dtos.SimpleContactResponseDto;
import br.com.grupocesw.easyong.utils.FormatterUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {
        City.class, ContactType.class,
        Collectors.class, FormatterUtil.class })
public interface SimpleContactMapper {
    SimpleContactMapper INSTANCE = Mappers.getMapper(SimpleContactMapper.class);

    @Mapping(target = "website", expression = "java(entity.getContacts().stream()" +
            ".filter(contact -> contact.getType().equals(ContactType.WEB_SITE))" +
            ".findFirst().isPresent() ? entity.getContacts().stream()" +
            ".filter(contact -> contact.getType().equals(ContactType.WEB_SITE))" +
            ".findFirst().get().getContent() : \"\")")
    @Mapping(target = "phones", expression = "java(entity.getContacts().stream()" +
            ".filter(contact -> contact.getType().equals(ContactType.PHONE))" +
            ".map(contact -> contact.getContent()).collect(Collectors.toList()))")
    SimpleContactResponseDto entityToResponseDto(Ngo entity);

}
