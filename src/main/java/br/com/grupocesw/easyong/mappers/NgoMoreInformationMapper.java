package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.NgoMoreInformation;
import br.com.grupocesw.easyong.request.dtos.NgoMoreInformationRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoMoreInformationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NgoMoreInformationMapper {
    NgoMoreInformationMapper INSTANCE = Mappers.getMapper(NgoMoreInformationMapper.class);

    List<NgoMoreInformationResponseDto> listToResponseDto(List<NgoMoreInformation> entities);

    default Set<NgoMoreInformationResponseDto> listToResponseDtoSet(Set<NgoMoreInformation> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<NgoMoreInformationResponseDto> listToResponseDto(Page<NgoMoreInformation> entities) {
        return entities.map(this::entityToResponseDto);
    }

    NgoMoreInformationResponseDto entityToResponseDto(NgoMoreInformation entity);

    @Mapping(target = "id", ignore = true)
    NgoMoreInformation requestDtoToEntity(NgoMoreInformationRequestDto dto);
}
