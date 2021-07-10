package br.com.grupocesw.easyong.request.dtos;

import br.com.grupocesw.easyong.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
    private String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
	
    private Gender gender;

	private Set<Long> causeIds;

}