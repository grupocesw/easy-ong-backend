package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.request.dtos.*;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
import br.com.grupocesw.easyong.utils.PasswordVerificationUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = { Collectors.class, SocialCause.class, PasswordVerificationUtil.class })
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserResponseDto> listToResponseDto(List<User> entities);

    default Set<UserResponseDto> listToResponseDtoSet(Set<User> entities) {
        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    default Page<UserResponseDto> listToResponseDto(Page<User> entities) {
        return entities.map(this::entityToResponseDto);
    }

    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.birthday", target = "birthday")
    @Mapping(source = "person.gender", target = "gender")
    @Mapping(target = "picture", expression = "java(PictureMapper.INSTANCE.entityToResponseDto(entity.getPicture()))")
    @Mapping(target = "causes", expression = "java(SocialCauseMapper.INSTANCE.listToResponseDtoSet(entity.getCauses()))")
    @Mapping(target = "roles", expression = "java(RoleMapper.INSTANCE.listToResponseDtoSet(entity.getRoles()))")
    UserResponseDto entityToResponseDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "favoriteNgos", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "ngoSuggestions", ignore = true)
    @Mapping(source = "name", target = "person.name")
    @Mapping(source = "birthday", target = "person.birthday")
    @Mapping(source = "gender", target = "person.gender")
    @Mapping(target = "causes", expression = "java((dto.getCauseIds() != null) ? " +
            "dto.getCauseIds().stream()" +
            ".map(id -> SocialCause.builder().id(id).build())" +
            ".collect(Collectors.toSet()) : null)")
    User requestDtoToEntity(UserCreateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "favoriteNgos", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "ngoSuggestions", ignore = true)
    @Mapping(source = "name", target = "person.name")
    @Mapping(source = "birthday", target = "person.birthday")
    @Mapping(source = "gender", target = "person.gender")
    @Mapping(target = "causes", expression = "java((dto.getCauseIds() != null) ? " +
            "dto.getCauseIds().stream()" +
            ".map(id -> SocialCause.builder().id(id).build())" +
            ".collect(Collectors.toSet()) : null)")
    User requestDtoToEntity(UserUpdateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "favoriteNgos", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "ngoSuggestions", ignore = true)
    @Mapping(target = "causes", ignore = true)
    User requestDtoToEntity(UserPasswordRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "favoriteNgos", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "ngoSuggestions", ignore = true)
    @Mapping(target = "causes", ignore = true)
    User requestDtoToEntity(UserUsernameRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "favoriteNgos", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "ngoSuggestions", ignore = true)
    @Mapping(target = "causes", ignore = true)
    User requestDtoToEntity(LoginRequestDto dto);
}
