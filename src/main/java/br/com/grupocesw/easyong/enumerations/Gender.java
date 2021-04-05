package br.com.grupocesw.easyong.enumerations;

public enum Gender {
	MALE(0), FEMALE(1), OTHER(2), UNINFORMED(3);

	private Integer code;

	private Gender(Integer code) {
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
		
		throw new IllegalArgumentException("Invalid Gender code");
	}
}
