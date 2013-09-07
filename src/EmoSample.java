import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmoSample {

	@JsonProperty("local_time")
	private long localTime;

	@JsonProperty("connection_strength")
	private float connectionStrength;

	@JsonIgnore
	private int wirelessSignalStatus;
	@JsonIgnore
	private int[] contactQuality;

	@JsonProperty("meditation")
	private float meditation;
	@JsonProperty("engagement")
	private float engagement;
	@JsonProperty("happiness")
	private float happiness;
	@JsonProperty("excitement")
	private float excitement;

	@JsonProperty("wink_left")
	private boolean winkLeft;
	@JsonProperty("wink_right")
	private boolean winkRight;

	@JsonProperty("turn_x")
	private int turnX;
	@JsonProperty("turn_y")
	private int turnY;

	public EmoSample(long localTime, int wirelessSignalStatus, int[] contactQuality, float meditation,
			float engagement, float happiness, float excitement, boolean winkLeft, boolean winkRight, int turnX,
			int turnY) {
		this.localTime = localTime;
		this.wirelessSignalStatus = wirelessSignalStatus;
		this.contactQuality = contactQuality;
		this.meditation = meditation;
		this.engagement = engagement;
		this.happiness = happiness;
		this.excitement = excitement;
		this.winkLeft = winkLeft;
		this.winkRight = winkRight;
		this.turnX = turnX;
		this.turnY = turnY;
	}

	/**
	 * @return the localTime
	 */
	public long getLocalTime() {
		return localTime;
	}

	/**
	 * @param localTime
	 *            the localTime to set
	 */
	public void setLocalTime(long localTime) {
		this.localTime = localTime;
	}

	/**
	 * @return the connectionStrength
	 */
	public float getConnectionStrength() {
		float wirelessSignalStatus = (float) getWirelessSignalStatus() / (EmoState.EE_SignalStrength_t.values().length - 1);

		int[] contactQualities = getContactQuality();
		float contactQuality = 0;
		for (int i = 0; i < contactQualities.length; i++) {
			contactQuality += contactQualities[i];
		}
		contactQuality /= contactQualities.length * (EmoState.EE_EEG_ContactQuality_t.values().length - 1);

		return Math.min(wirelessSignalStatus, contactQuality);
	}

	/**
	 * @param connectionStrength
	 *            the connectionStrength to set
	 */
	public void setConnectionStrength(float connectionStrength) {
		this.connectionStrength = connectionStrength;
	}

	/**
	 * @return the wirelessSignalStatus
	 */
	public int getWirelessSignalStatus() {
		return wirelessSignalStatus;
	}

	/**
	 * @param wirelessSignalStatus
	 *            the wirelessSignalStatus to set
	 */
	public void setWirelessSignalStatus(int wirelessSignalStatus) {
		this.wirelessSignalStatus = wirelessSignalStatus;
	}

	/**
	 * @return the contactQuality
	 */
	public int[] getContactQuality() {
		return contactQuality;
	}

	/**
	 * @param contactQuality
	 *            the contactQuality to set
	 */
	public void setContactQuality(int[] contactQuality) {
		this.contactQuality = contactQuality;
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
