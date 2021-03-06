import java.util.concurrent.BlockingQueue;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class EmoReader implements Runnable {

	private String emotivIp;
	private short emotivPort;
	private BlockingQueue<EmoSample> samplesQueue;
	private int emotivUserId;

	private EmoLogger logger;

	// used for debugging
	private float excitementOffset = 0;
	private float happinessOffset = 0;
	private float engagementOffset = 0;
	private float meditationOffset = 0;
	private int gyroOffset = 0;

	public EmoReader(EmoLogger logger, String emotivIp, short emotivPort, BlockingQueue<EmoSample> samplesQueue,
			int emotivUserId) {
		this.emotivIp = emotivIp;
		this.emotivPort = emotivPort;
		this.samplesQueue = samplesQueue;
		this.emotivUserId = emotivUserId;
		this.logger = logger;
	}

	@Override
	public void run() {
		Pointer eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
		Pointer eState = Edk.INSTANCE.EE_EmoStateCreate();
		IntByReference userID = new IntByReference(emotivUserId);

		logger.readerInfo("Target IP of Emotiv: [" + emotivIp + "]");

		if (Edk.INSTANCE.EE_EngineRemoteConnect(emotivIp, emotivPort, "Emotiv Systems-5") != EdkErrorCode.EDK_OK
				.ToInt()) {
			logger.readerError("Cannot connect to Emotiv on [" + emotivIp + "]");
			return;
		}
		logger.readerInfo("Connected to Emotiv on [" + emotivIp + "]");

		while (true) {
			int state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);

			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) {

				int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);

				// Save the EmoState if it has been updated
				if (eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()) {

					Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);

					EmoSample emoSample = readSample(eState);
					logger.readerSample(emoSample);
					try {
						samplesQueue.put(emoSample);
					} catch (InterruptedException e) {
						logger.readerError(e.getMessage());
						e.printStackTrace();
						// continue
					}
				}

			} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				logger.readerError("Internal error in Emotiv Engine!");
				break;
			}
		}

		Edk.INSTANCE.EE_EngineDisconnect();
		logger.readerInfo("Disconnected!");
	}

	private EmoSample readSample(Pointer eState) {
		long localTime = System.currentTimeMillis();

		// 0..2
		int wirelessSignalStatus = EmoState.INSTANCE.ES_GetWirelessSignalStatus(eState);

		// 0..4
		int contactQuality[] = new int[EmoState.EE_InputChannels_t.values().length];
		for (int electroIdx = 0; electroIdx < contactQuality.length; electroIdx++) {
			contactQuality[electroIdx] = EmoState.INSTANCE.ES_GetContactQuality(eState, electroIdx);
		}

		// 0..1
		float excitement = EmoState.INSTANCE.ES_AffectivGetExcitementShortTermScore(eState);
		float engagement = EmoState.INSTANCE.ES_AffectivGetEngagementBoredomScore(eState);
		float meditation = EmoState.INSTANCE.ES_AffectivGetMeditationScore(eState);
		float frustration = EmoState.INSTANCE.ES_AffectivGetFrustrationScore(eState);
		float happiness = (frustration == 0) ? 0 : (1 - frustration);

		boolean winkLeft = false;
		boolean winkRight = false;
		int expressiveAction = EmoState.INSTANCE.ES_ExpressivGetLowerFaceAction(eState);
		if (expressiveAction == EmoState.EE_ExpressivAlgo_t.EXP_SMIRK_LEFT.ToInt()) {
			winkLeft = true;
		} else if (expressiveAction == EmoState.EE_ExpressivAlgo_t.EXP_SMIRK_RIGHT.ToInt()) {
			winkRight = true;
		}

		IntByReference turnX = new IntByReference(0);
		IntByReference turnY = new IntByReference(0);
		Edk.INSTANCE.EE_HeadsetGetGyroDelta(emotivUserId, turnX, turnY);

		// for debugging
		meditation = meditation + meditationOffset;
		engagement = engagement + engagementOffset;
		happiness = happiness + happinessOffset;
		excitement = excitement + excitementOffset;
		turnX.setValue(turnX.getValue() + gyroOffset);
		turnY.setValue(turnY.getValue() + gyroOffset);
		gyroOffset = 0;
		meditation = boundValue(meditation);
		engagement = boundValue(engagement);
		happiness = boundValue(happiness);
		excitement = boundValue(excitement);

		return new EmoSample(localTime, wirelessSignalStatus, contactQuality, meditation, engagement, happiness,
				excitement, winkLeft, winkRight, turnX.getValue(), turnY.getValue());
	}

	/**
	 * @param cognitive
	 * @return the param bounded between 0 to 1
	 */
	private float boundValue(float cognitive) {
		if (cognitive > 1 || cognitive < 0) {
			if (cognitive > 1) {
				cognitive = 1;
			} else {
				cognitive = 0;
			}
		}
		return cognitive;
	}

	public void setExcitementOffset(float onclickChange) {
		excitementOffset += onclickChange;
	}

	public void setHappinessOffset(float onclickChange) {
		happinessOffset += onclickChange;
	}

	public void setEngagementOffset(float onclickChange) {
		engagementOffset += onclickChange;
	}

	public void setMeditationOffset(float onclickChange) {
		meditationOffset += onclickChange;
	}

	public void setGyroOffset(float onclickChangeVolume) {
		gyroOffset += onclickChangeVolume;
	}

}