package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Contact;
import br.com.grupocesw.easyong.request.dtos.ContactRequestDto;
import br.com.grupocesw.easyong.response.dtos.ContactResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    List<ContactResponseDto> listToResponseDto(List<Contact> entities);

    default Set<ContactResponseDto> listToResponseDtoSet(Set<Contact> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<ContactResponseDto> listToResponseDto(Page<Contact> entities) {
        return entities.map(this::entityToResponseDto);
    }

    ContactResponseDto entityToResponseDto(Contact entity);

    @Mapping(target = "id", ignore = true)
    Contact requestDtoToEntity(ContactRequestDto dto);
}
