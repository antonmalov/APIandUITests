package models.usercontroller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Info{
	private String message;
	private String status;

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}
