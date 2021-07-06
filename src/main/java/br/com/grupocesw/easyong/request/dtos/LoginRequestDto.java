package br.com.grupocesw.easyong.request.dtos;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @ApiModelProperty(notes="Username is email", name="Username", required=true, value="user@easyong.com.br")
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
