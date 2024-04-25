package models.fakeapiuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NonNull
public class UserRoot{

	@Getter
	@JsonProperty("password")
	private String password;

	@Getter
	@JsonProperty("address")
	private Address address;

	@Getter
	@JsonProperty("phone")
	private String phone;

	@Getter
	@JsonProperty("__v")
	private int v;

	@Getter
	@JsonProperty("name")
	private Name name;

	@JsonProperty("id")
	private int id;

	@Getter
	@JsonProperty("email")
	private String email;

	@Getter
	@JsonProperty("username")
	private String username;

	public int getId(){
		return id;
	}

}