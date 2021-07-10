package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Faq;
import br.com.grupocesw.easyong.request.dtos.FaqRequestDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FaqMapper {
    FaqMapper INSTANCE = Mappers.getMapper(FaqMapper.class);

    List<FaqResponseDto> listToResponseDto(List<Faq> entities);

    default Set<FaqResponseDto> listToResponseDtoSet(Set<Faq> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<FaqResponseDto> listToResponseDto(Page<Faq> entities) {
        return entities.map(this::entityToResponseDto);
    }

    FaqResponseDto entityToResponseDto(Faq entity);

    @Mapping(target = "id", ignore = true)
    Faq requestDtoToEntity(FaqRequestDto dto);
}
