package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.enums.ContactType;
import br.com.grupocesw.easyong.response.dtos.NgoSlimV2ResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoV2ResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = { Collectors.class, SocialCause.class, ContactType.class })
public interface NgoV2Mapper {
    NgoV2Mapper INSTANCE = Mappers.getMapper(NgoV2Mapper.class);

    default Set<NgoSlimV2ResponseDto> listToSlimResponseDtoSet(Set<Ngo> entities) {
        return entities.stream()
                .map(this::entityToSlimResponseDto)
                .collect(Collectors.toSet());
    }

    default Set<NgoV2ResponseDto> listToResponseDtoSet(Set<Ngo> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<NgoSlimV2ResponseDto> listToSlimResponseDto(Page<Ngo> entities) {
            return entities.map(this::entityToSlimResponseDto);
    }

    default Page<NgoV2ResponseDto> listToResponseDto(Page<Ngo> entities) {
        return entities.map(this::entityToResponseDto);
    }

    @Mapping(target = "title", expression = "java(entity.getName())")
    @Mapping(target = "website", expression = "java(entity.getContacts()" +
            ".stream()" +
            ".filter(contact -> contact.getType().equals(ContactType.WEB_SITE))" +
            ".findFirst().isPresent() ? entity.getContacts()" +
            ".stream()" +
            ".filter(contact -> contact.getType().equals(ContactType.WEB_SITE))" +
            ".findFirst().get().getContent() : \"\")")
    @Mapping(target = "url", expression = "java(entity.getPictures().stream().findFirst().isPresent() ?" +
            "entity.getPictures().stream().findFirst().get().getUrl() : \"\")")
    NgoSlimV2ResponseDto entityToSlimResponseDto(Ngo entity);

    @Mapping(target = "contact", expression = "java(SimpleContactMapper.INSTANCE.entityToResponseDto(entity))")
    @Mapping(target = "location", expression = "java(LocationMapper.INSTANCE.entityToResponseDto(entity.getAddress()))")
    @Mapping(target = "picture", expression = "java(PictureMapper.INSTANCE.entityToResponseDto(entity.getPictures().stream().findFirst().orElse(null)))")
    NgoV2ResponseDto entityToResponseDto(Ngo entity);

}
