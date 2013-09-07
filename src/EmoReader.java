import java.util.concurrent.BlockingQueue;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class EmoReader implements Runnable {

	String emotivIp;
	short emotivPort;
	BlockingQueue<EmoSample> samplesQueue;
	int emotivUserId;

	public EmoReader(String emotivIp, short emotivPort, BlockingQueue<EmoSample> samplesQueue, int emotivUserId) {
		this.emotivIp = emotivIp;
		this.emotivPort = emotivPort;
		this.samplesQueue = samplesQueue;
		this.emotivUserId = emotivUserId;
	}

	@Override
	public void run() {
		Pointer eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
		Pointer eState = Edk.INSTANCE.EE_EmoStateCreate();
		IntByReference userID = new IntByReference(emotivUserId);

		System.out.println("Target IP of Emotiv: [" + emotivIp + "]");

		if (Edk.INSTANCE.EE_EngineRemoteConnect(emotivIp, emotivPort, "Emotiv Systems-5") != EdkErrorCode.EDK_OK
				.ToInt()) {
			System.out.println("Cannot connect to EmoComposer on [" + emotivIp + "]");
			return;
		}
		System.out.println("Connected to EmoComposer on [" + emotivIp + "]");

		while (true) {
			int state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);

			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) {

				int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);

				// Save the EmoState if it has been updated
				if (eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()) {

					Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);

					try {
						samplesQueue.put(readSample(eState));
					} catch (InterruptedException e) {
						e.printStackTrace();
						// continue
					}
				}

			} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine!");
				break;
			}
		}

		Edk.INSTANCE.EE_EngineDisconnect();
		System.out.println("Disconnected!");
		// TODO: log severe error
		System.exit(1);
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
		int expressiveAction = EmoState.INSTANCE.ES_CognitivGetCurrentAction(eState);
		if (expressiveAction == EmoState.EE_ExpressivAlgo_t.EXP_SMIRK_LEFT.ToInt()) {
			winkLeft = true;
		} else if (expressiveAction == EmoState.EE_ExpressivAlgo_t.EXP_SMIRK_RIGHT.ToInt()) {
			winkRight = true;
		}

		IntByReference turnX = new IntByReference(0);
		IntByReference turnY = new IntByReference(0);
		Edk.INSTANCE.EE_HeadsetGetGyroDelta(emotivUserId, turnX, turnY);

		return new EmoSample(localTime, wirelessSignalStatus, contactQuality, meditation, engagement, happiness,
				excitement, winkLeft, winkRight, turnX.getValue(), turnY.getValue());
	}

}