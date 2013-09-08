import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EmotivTransmitter {

	public static final String USAGE = "Usage: BUFFER_SIZE SAMPLES_TO_SEND EMOTIV_IP EMOTIV_PORT SERVER_ADDRESS";

	public static final int SPOTIFY_USER_ID = 0;
	public static final int EMOTIV_USER_ID = 0;

	public static int BUFFER_SIZE;
	public static int SAMPLES_TO_SEND;
	public static String EMOTIV_IP; // usually: 127.0.0.1
	public static short EMOTIV_PORT; // usually: 3008 or 1726
	public static String SERVER_ADDRESS;

	public static EmoReader emoReader;
	public static EmoTrans emoTrans;
	
	public static final float ONCLICK_CHANGE_COGNITIVE = 0.01f;
	public static final float ONCLICK_CHANGE_VOLUME = 6000;

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println(USAGE);
			return;
		}
		BUFFER_SIZE = Integer.parseInt(args[0]);
		SAMPLES_TO_SEND = Integer.parseInt(args[1]);
		EMOTIV_IP = args[2];
		EMOTIV_PORT = Short.parseShort(args[3]);
		SERVER_ADDRESS = args[4];

		EmoLogger logger = new EmoLogger();
		
		BlockingQueue<EmoSample> samplesQueue = new ArrayBlockingQueue<>(BUFFER_SIZE);
		
		emoReader = new EmoReader(logger, EMOTIV_IP, EMOTIV_PORT, samplesQueue, EMOTIV_USER_ID);
		emoTrans = new EmoTrans(logger, SAMPLES_TO_SEND, SERVER_ADDRESS, samplesQueue, SPOTIFY_USER_ID);
		
		EmoLoggerUI ui = new EmoLoggerUI("BrainifyEmotivTransmitter", emoReader, emoTrans);
		logger.setUi(ui);
		
		new Thread(ui).start();
		
		new Thread(emoReader).start();
		new Thread(emoTrans).start();
	}

}
