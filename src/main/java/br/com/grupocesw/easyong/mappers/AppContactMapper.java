package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.AppContact;
import br.com.grupocesw.easyong.request.dtos.AppContactRequestDto;
import br.com.grupocesw.easyong.response.dtos.AppContactResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AppContactMapper {
    AppContactMapper INSTANCE = Mappers.getMapper(AppContactMapper.class);

    List<AppContactResponseDto> listToResponseDto(List<AppContact> entities);

    default Set<AppContactResponseDto> listToResponseDtoSet(Set<AppContact> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<AppContactResponseDto> listToResponseDto(Page<AppContact> entities) {
        return entities.map(this::entityToResponseDto);
    }

    AppContactResponseDto entityToResponseDto(AppContact entity);

    @Mapping(target = "id", ignore = true)
    AppContact requestDtoToEntity(AppContactRequestDto dto);
}
