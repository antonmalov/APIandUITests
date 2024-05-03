package models.usercontroller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Requirements{

	@JsonProperty("videoCard")
	private String videoCard;

	@JsonProperty("hardDrive")
	private int hardDrive;

	@JsonProperty("osName")
	private String osName;

	@JsonProperty("ramGb")
	private int ramGb;

}