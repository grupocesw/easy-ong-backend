package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.enums.ContactType;
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


    default Set<NgoV2ResponseDto> listToResponseDtoSet(Set<Ngo> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<NgoV2ResponseDto> listToResponseDto(Page<Ngo> entities) {
        return entities.map(this::entityToResponseDto);
    }

    @Mapping(target = "contact", expression = "java(SimpleContactMapper.INSTANCE.entityToResponseDto(entity))")
    @Mapping(target = "location", expression = "java(LocationMapper.INSTANCE.entityToResponseDto(entity.getAddress()))")
    @Mapping(target = "picture", expression = "java(PictureMapper.INSTANCE.entityToResponseDto(entity.getPictures().stream().findFirst().orElse(null)))")
    NgoV2ResponseDto entityToResponseDto(Ngo entity);

}
