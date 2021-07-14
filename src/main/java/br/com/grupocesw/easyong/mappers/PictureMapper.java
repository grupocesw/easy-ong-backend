package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.request.dtos.PictureRequestDto;
import br.com.grupocesw.easyong.response.dtos.PictureResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PictureMapper {
    PictureMapper INSTANCE = Mappers.getMapper(PictureMapper.class);

    List<PictureResponseDto> listToResponseDto(List<Picture> entities);

    default Set<PictureResponseDto> listToResponseDtoSet(Set<Picture> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<PictureResponseDto> listToResponseDto(Page<Picture> entities) {
        return entities.map(this::entityToResponseDto);
    }

    PictureResponseDto entityToResponseDto(Picture entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "ngos", ignore = true)
    @Mapping(target = "notification", ignore = true)
    Picture requestDtoToEntity(PictureRequestDto dto);
}
