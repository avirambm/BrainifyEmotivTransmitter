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

	/**
	 * @return the localTime
	 */
	public int getLocalTime() {
		return localTime;
	}

	/**
	 * @param localTime
	 *            the localTime to set
	 */
	public void setLocalTime(int localTime) {
		this.localTime = localTime;
	}

	/**
	 * @return the connectionStrength
	 */
	public float getConnectionStrength() {
		return connectionStrength;
	}

	/**
	 * @param connectionStrength
	 *            the connectionStrength to set
	 */
	public void setConnectionStrength(float connectionStrength) {
		this.connectionStrength = connectionStrength;
	}

	/**
	 * @return the meditation
	 */
	public float getMeditation() {
		return meditation;
	}

	/**
	 * @param meditation
	 *            the meditation to set
	 */
	public void setMeditation(float meditation) {
		this.meditation = meditation;
	}

	/**
	 * @return the engagement
	 */
	public float getEngagement() {
		return engagement;
	}

	/**
	 * @param engagement
	 *            the engagement to set
	 */
	public void setEngagement(float engagement) {
		this.engagement = engagement;
	}

	/**
	 * @return the happiness
	 */
	public float getHappiness() {
		return happiness;
	}

	/**
	 * @param happiness
	 *            the happiness to set
	 */
	public void setHappiness(float happiness) {
		this.happiness = happiness;
	}

	/**
	 * @return the excitement
	 */
	public float getExcitement() {
		return excitement;
	}

	/**
	 * @param excitement
	 *            the excitement to set
	 */
	public void setExcitement(float excitement) {
		this.excitement = excitement;
	}

	/**
	 * @return the winkLeft
	 */
	public boolean isWinkLeft() {
		return winkLeft;
	}

	/**
	 * @param winkLeft
	 *            the winkLeft to set
	 */
	public void setWinkLeft(boolean winkLeft) {
		this.winkLeft = winkLeft;
	}

	/**
	 * @return the winkRight
	 */
	public boolean isWinkRight() {
		return winkRight;
	}

	/**
	 * @param winkRight
	 *            the winkRight to set
	 */
	public void setWinkRight(boolean winkRight) {
		this.winkRight = winkRight;
	}

	/**
	 * @return the turnX
	 */
	public int getTurnX() {
		return turnX;
	}

	/**
	 * @param turnX
	 *            the turnX to set
	 */
	public void setTurnX(int turnX) {
		this.turnX = turnX;
	}

	/**
	 * @return the turnY
	 */
	public int getTurnY() {
		return turnY;
	}

	/**
	 * @param turnY
	 *            the turnY to set
	 */
	public void setTurnY(int turnY) {
		this.turnY = turnY;
	}

}
