package models.usercontroller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthData{
	private String username;

	private String password;


	public String getPassword(){
		return password;
	}

	public String getUsername(){
		return username;
	}
}
