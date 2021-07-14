package br.com.grupocesw.easyong.enums;

public enum NotificationType {
	INFORMATION(0),
	ERROR(1),
	WARNNING(2);

	private Integer code;

	private NotificationType(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static NotificationType fromString(Integer code) {
		for(NotificationType value : NotificationType.values()) {
			if(value.getCode().equals(code)) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid notification type code");
	}
}
