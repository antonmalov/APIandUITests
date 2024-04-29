package models.usercontroller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullUser{

	@JsonProperty("pass")
	private String pass;

	@JsonProperty("games")
	private List<GamesItem> games;

	@JsonProperty("login")
	private String login;

	public String getPass(){
		return pass;
	}

	public List<GamesItem> getGames(){
		return games;
	}

	public String getLogin(){
		return login;
	}
}