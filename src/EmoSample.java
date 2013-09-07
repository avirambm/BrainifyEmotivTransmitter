import com.fasterxml.jackson.annotation.JsonProperty;


public class EmoSample {

	@JsonProperty("local_time")
	int localTime;
	
	@JsonProperty("connection_strength")
	float connectionStrength;
	
	@JsonProperty("meditation")
	float meditation;
	@JsonProperty("engagement")
	float engagement;
	@JsonProperty("happiness")
	float happiness;
	@JsonProperty("excitement")
	float excitement;
	
	@JsonProperty("wink_left")
	boolean winkLeft;
	@JsonProperty("wink_right")
	boolean winkRight;
	
	@JsonProperty("turn_x")
	int turnX;
	@JsonProperty("turn_y")
	int turnY;
	
}
