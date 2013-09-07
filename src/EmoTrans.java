import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmoTrans implements Runnable {

	private int samplesToSend;
	private String serverAddress;
	private BlockingQueue<EmoSample> samplesQueue;
	private String userId;

	private EmoLogger logger = EmoLogger.getInstance();

	public EmoTrans(int samplesToSend, String serverAddress, BlockingQueue<EmoSample> samplesQueue, String userId) {
		this.samplesToSend = samplesToSend;
		this.serverAddress = serverAddress;
		this.samplesQueue = samplesQueue;
		this.userId = userId;
	}

	@Override
	public void run() {
		// catch exceptions which are not supposed to happen for cleaner code
		try {
			run2();
		} catch (IllegalStateException | InterruptedException | IOException e) {
			logger.transError(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void run2() throws InterruptedException, IllegalStateException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		while (true) {

			// create the post object
			ArrayList<EmoSample> emoSamples = new ArrayList<>();
			for (int i = 0; i < samplesToSend; i++) {
				emoSamples.add(samplesQueue.take());
			}
			EmoSamplesPost emoPost = new EmoSamplesPost(userId, emoSamples);

			// convert to json and send
			HttpPost post = new HttpPost(serverAddress);
			post.setEntity(new StringEntity(mapper.writeValueAsString(emoPost)));
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			HttpResponse response = null;
			try {
				logger.transInfo("Posting " + samplesToSend + " samples to " + serverAddress + "   bufferSize=" + samplesQueue.size());
				response = new DefaultHttpClient().execute(post);
			} catch (IOException e) {
				logger.transError(e.getMessage());
				e.printStackTrace();
				// continue
			}

			// log the response
			if (response != null) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					logger.transInfo(samplesToSend + " samples sent successfully");
				} else {
					StringBuilder responseContent = new StringBuilder();
					String line = "";
					while ((line = rd.readLine()) != null) {
						responseContent.append(line);
					}
					logger.transError("Failed to send samples. StatusCode: " + statusCode + "\n" + "Response: "
							+ responseContent.toString());
				}
			}

		}

	}

}
