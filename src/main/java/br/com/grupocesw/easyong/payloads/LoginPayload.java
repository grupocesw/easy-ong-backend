package br.com.grupocesw.easyong.payloads;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginPayload {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
