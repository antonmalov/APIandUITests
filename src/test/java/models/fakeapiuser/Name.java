package models.fakeapiuser;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Name{

	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("lastname")
	private String lastname;


}