package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.enums.ContactType;
import br.com.grupocesw.easyong.request.dtos.NgoCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.NgoUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = { Collectors.class, SocialCause.class, ContactType.class })
public interface NgoMapper {
    NgoMapper INSTANCE = Mappers.getMapper(NgoMapper.class);

    List<NgoResponseDto> listToResponseDto(List<Ngo> entities);

    default Set<NgoSlimResponseDto> listToSlimResponseDtoSet(Set<Ngo> entities) {
        return entities.stream()
                .map(this::entityToSlimResponseDto)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "favorited", expression = "java(entity.getFavorited())")
    default Page<NgoResponseDto> listToResponseDto(Page<Ngo> entities) {
        return entities.map(this::entityToResponseDto);
    }

    default Page<NgoSlimResponseDto> listToSlimResponseDto(Page<Ngo> entities) {
            return entities.map(this::entityToSlimResponseDto);
    }

    @Mapping(target = "picture", expression = "java(PictureMapper.INSTANCE.entityToResponseDto(entity.getPictures().stream().findFirst().orElse(null)))")
    NgoSlimResponseDto entityToSlimResponseDto(Ngo entity);

    @Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.entityToResponseDto(entity.getAddress()))")
    @Mapping(target = "causes", expression = "java(SocialCauseMapper.INSTANCE.listToResponseDtoSet(entity.getCauses()))")
    @Mapping(target = "contacts", expression = "java(ContactMapper.INSTANCE.listToResponseDtoSet(entity.getContacts()))")
    @Mapping(target = "moreInformations", expression = "java(NgoMoreInformationMapper.INSTANCE.listToResponseDtoSet(entity.getMoreInformations()))")
    @Mapping(target = "pictures", expression = "java(PictureMapper.INSTANCE.listToResponseDtoSet(entity.getPictures()))")
    NgoResponseDto entityToResponseDto(Ngo entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.requestDtoToEntity(dto.getAddress()))")
    @Mapping(target = "causes", expression = "java(" +
            "dto.getCauseIds().stream()" +
            ".map(id -> SocialCause.builder().id(id).build())" +
            ".collect(Collectors.toSet()))")
    Ngo requestDtoToEntity(NgoCreateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.requestDtoToEntity(dto.getAddress()))")
    @Mapping(target = "causes", expression = "java((dto.getCauseIds() != null) ? " +
            "dto.getCauseIds().stream()" +
            ".map(id -> SocialCause.builder().id(id).build())" +
            ".collect(Collectors.toSet()) : null)")
    Ngo requestDtoToEntity(NgoUpdateRequestDto dto);
}
