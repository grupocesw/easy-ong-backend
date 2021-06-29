package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.request.dtos.RoleRequestDto;
import br.com.grupocesw.easyong.response.dtos.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    List<RoleResponseDto> listToResponseDto(List<Role> entities);

    default Set<RoleResponseDto> listToResponseDtoSet(Set<Role> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    RoleResponseDto entityToResponseDto(Role entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role requestDtoToEntity(RoleRequestDto dto);
}
