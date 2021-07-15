package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Notification;
import br.com.grupocesw.easyong.request.dtos.NotificationRequestDto;
import br.com.grupocesw.easyong.response.dtos.NotificationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    List<NotificationResponseDto> listToResponseDto(List<Notification> entities);

    default Set<NotificationResponseDto> listToResponseDtoSet(Set<Notification> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<NotificationResponseDto> listToResponseDto(Page<Notification> entities) {
        return entities.map(this::entityToResponseDto);
    }

    NotificationResponseDto entityToResponseDto(Notification entity);

    @Mapping(target = "id", ignore = true)
    Notification requestDtoToEntity(NotificationRequestDto dto);
}
