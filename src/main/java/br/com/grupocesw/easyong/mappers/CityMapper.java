package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.request.dtos.CityRequestDto;
import br.com.grupocesw.easyong.response.dtos.CityResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    List<CityResponseDto> listToResponseDto(List<City> entities);

    default Set<CityResponseDto> listToResponseDtoSet(Set<City> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<CityResponseDto> listToResponseDto(Page<City> entities) {
        return entities.map(this::entityToResponseDto);
    }

    @Mapping(target = "state", expression = "java(String.format(\"%s - %s\", entity.getState().getName(), entity.getState().getAbbreviation()))")
    CityResponseDto entityToResponseDto(City entity);

    @Mapping(target = "id", ignore = true)
    City requestDtoToEntity(CityRequestDto dto);
}
