import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class EmotivTransmitter {
	
	public static final String USAGE = "USAGE: BUFFER_SIZE SAMPLES_TO_SEND EMOTIV_IP EMOTIV_PORT SERVER_ADDRESS";
	
	public static final String SPOTIFY_USER_ID = "0";
	
	public static int BUFFER_SIZE;
	public static int SAMPLES_TO_SEND;
	public static String EMOTIV_IP;
	public static short EMOTIV_PORT;
	public static String SERVER_ADDRESS;
	
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
		
		BlockingQueue<EmoSample> samplesQueue = new ArrayBlockingQueue<>(BUFFER_SIZE);
		
		EmoReader emoReader = new EmoReader(EMOTIV_IP, EMOTIV_PORT, samplesQueue);
		EmoTrans emoTrans = new EmoTrans(SAMPLES_TO_SEND, SERVER_ADDRESS, samplesQueue, SPOTIFY_USER_ID);
		
		new Thread(emoReader).start();
		new Thread(emoTrans).start();
	}
	
}
