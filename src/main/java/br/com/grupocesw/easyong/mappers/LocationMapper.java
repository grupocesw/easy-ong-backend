package br.com.grupocesw.easyong.mappers;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.response.dtos.LocationResponseDto;
import br.com.grupocesw.easyong.utils.FormatterUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = { City.class, FormatterUtil.class })
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(target = "address", expression = "java(String.format(\"%s,  %s - %s, %s, %s\", entity.getStreet(), " +
            "entity.getNumber(), entity.getDistrict(), entity.getCity(), " +
            "!entity.getZipCode().isEmpty() ?" +
            "FormatterUtil.formatString(entity.getZipCode(), \"#####-###\") : \"\" ))")
    LocationResponseDto entityToResponseDto(Address entity);
}
