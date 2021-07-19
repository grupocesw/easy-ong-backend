package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.request.dtos.SocialCauseRequestDto;
import br.com.grupocesw.easyong.response.dtos.SocialCauseResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = { Collections.class })
public interface SocialCauseMapper {
    SocialCauseMapper INSTANCE = Mappers.getMapper(SocialCauseMapper.class);

    List<SocialCauseResponseDto> listToResponseDto(List<SocialCause> entities);

    default Set<SocialCauseResponseDto> listToResponseDtoSet(Set<SocialCause> entities) {
        return (entities != null) ? entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet()) : Collections.emptySet();
    }

    default Page<SocialCauseResponseDto> listToResponseDto(Page<SocialCause> entities) {
        return entities.map(this::entityToResponseDto);
    }

    SocialCauseResponseDto entityToResponseDto(SocialCause entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "ngos", ignore = true)
    SocialCause requestDtoToEntity(SocialCauseRequestDto dto);
}
