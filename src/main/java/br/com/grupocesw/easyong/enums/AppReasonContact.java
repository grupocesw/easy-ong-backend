package br.com.grupocesw.easyong.enums;

public enum AppReasonContact {
	SUGGESTION(0),
	COMPLAINT(1),
	PRAISE(2),
	INDICATE_NGO(3),
	OTHER(4);

	private Integer code;

	private AppReasonContact(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static AppReasonContact fromString(Integer code) {
		for(AppReasonContact value : AppReasonContact.values()) {
			if(value.getCode().equals(code)) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid reason code");
	}
}
