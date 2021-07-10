package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Country;
import br.com.grupocesw.easyong.request.dtos.CountryRequestDto;
import br.com.grupocesw.easyong.response.dtos.CountryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    List<CountryResponseDto> listToResponseDto(List<Country> entities);

    default Set<CountryResponseDto> listToResponseDtoSet(Set<Country> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<CountryResponseDto> listToResponseDto(Page<Country> entities) {
        return entities.map(this::entityToResponseDto);
    }

    CountryResponseDto entityToResponseDto(Country entity);

    @Mapping(target = "id", ignore = true)
    Country requestDtoToEntity(CountryRequestDto dto);
}
