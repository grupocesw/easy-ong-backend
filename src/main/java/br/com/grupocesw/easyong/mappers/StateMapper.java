package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.State;
import br.com.grupocesw.easyong.request.dtos.StateRequestDto;
import br.com.grupocesw.easyong.response.dtos.StateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

    List<StateResponseDto> listToResponseDto(List<State> entities);

    default Set<StateResponseDto> listToResponseDtoSet(Set<State> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<StateResponseDto> listToResponseDto(Page<State> entities) {
        return entities.map(this::entityToResponseDto);
    }

    @Mapping(target = "country", expression = "java(String.format(\"%s - %s\", entity.getCountry().getName(), entity.getCountry().getAbbreviation()))")
    StateResponseDto entityToResponseDto(State entity);

    @Mapping(target = "id", ignore = true)
    State requestDtoToEntity(StateRequestDto dto);
}
