package br.com.grupocesw.easyong.request.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}