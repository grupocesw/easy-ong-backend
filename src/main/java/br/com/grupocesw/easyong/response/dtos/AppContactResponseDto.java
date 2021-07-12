package br.com.grupocesw.easyong.response.dtos;

import br.com.grupocesw.easyong.enums.AppReasonContact;

public class AppContactResponseDto {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String message;
    private AppReasonContact reason;
}
