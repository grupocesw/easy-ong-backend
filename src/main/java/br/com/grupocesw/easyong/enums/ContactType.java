package br.com.grupocesw.easyong.enums;

public enum ContactType {
	EMAIL(0),
	FACEBOOK(1),
	INSTAGRAM(2),
	LINKEDIN(3),
	PHONE(4),
	TELEGRAM(5),
	TIK_TOK(6),
	TWITTER(7),
	WEB_SITE(8),
	WHATSAPP(9),
	YOUTUBE(10);
	
	private Integer code;

	ContactType(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	public static boolean exists(Integer code) {
		for(Gender value : Gender.values()) {
			if(value.getCode().equals(code)) {
				return true;
			}
		}
		
		throw new IllegalArgumentException("Invalid contact type code");
	}

	public static Gender fromString(Integer code) {
		for(Gender value : Gender.values()) {
			if(value.getCode().equals(code)) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid contact type code");
	}
}
