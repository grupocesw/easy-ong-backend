package br.com.grupocesw.easyong.enums;

public enum GenderEnum {
	MALE(0), FEMALE(1), OTHER(2), UNINFORMED(3);

	private Integer code;

	private GenderEnum(Integer code) {
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
		
		throw new IllegalArgumentException("Invalid Gender code");
	}
}
