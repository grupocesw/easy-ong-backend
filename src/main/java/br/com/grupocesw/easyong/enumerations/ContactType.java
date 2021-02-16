package br.com.grupocesw.easyong.enumerations;

public enum ContactType {
	EMAIL(0), PHONE(1), WEB_SITE(2), REDE_SOCIAL(3), WHATSAPP(4);
	
	private Integer code;

	private ContactType(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static Gender fromString(Integer code) {
		for(Gender value : Gender.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid Contact Type code");
	}
}
