package br.com.grupocesw.easyong.enums;

public enum ContactTypeEmun {
	EMAIL(0), FACEBOOK(1), INSTAGRAM(2), LINKEDIN(3), PHONE(4), TELEGRAM(5), TIK_TOK(6), TWITTER(7), WEB_SITE(8), WHATSAPP(9),YOUTUBE(10);
	
	private Integer code;

	private ContactTypeEmun(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static GenderEnum fromString(Integer code) {
		for(GenderEnum value : GenderEnum.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid contact type code");
	}
}
