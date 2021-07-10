package br.com.grupocesw.easyong.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class ApiResponse {
    @NotEmpty
    private boolean success;

    @NotEmpty
    private final String message;

}