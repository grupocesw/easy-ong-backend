package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.request.dtos.AddressRequestDto;
import br.com.grupocesw.easyong.response.dtos.AddressResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    List<AddressResponseDto> listToResponseDto(List<Address> entities);

    default Page<AddressResponseDto> listToResponseDto(Page<Address> entities) {
        return entities.map(this::entityToResponseDto);
    }

    @Mapping(target = "city", expression = "java(String.format(\"%s - %s\", entity.getCity().getName(), entity.getCity().getState().getAbbreviation()))")
    AddressResponseDto entityToResponseDto(Address entity);

    @Mapping(target = "id", ignore = true)
    Address requestDtoToEntity(AddressRequestDto dto);
}
