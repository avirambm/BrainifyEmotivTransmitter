import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;


public class EmoSamplesPost {

	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("samples")
	private ArrayList<EmoSample> samples;
	
	public EmoSamplesPost() {
	}
	
	public EmoSamplesPost(String userId, ArrayList<EmoSample> samples) {
		this.userId = userId;
		this.samples = samples;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the samples
	 */
	public ArrayList<EmoSample> getSamples() {
		return samples;
	}

	/**
	 * @param samples the samples to set
	 */
	public void setSamples(ArrayList<EmoSample> samples) {
		this.samples = samples;
	}
	
}
