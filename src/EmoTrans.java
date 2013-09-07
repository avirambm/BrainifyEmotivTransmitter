import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmoTrans implements Runnable {

	private int samplesToSend;
	private String serverAddress;
	private BlockingQueue<EmoSample> samplesQueue;
	private String userId;

	public EmoTrans(int samplesToSend, String serverAddress, BlockingQueue<EmoSample> samplesQueue, String userId) {
		this.samplesToSend = samplesToSend;
		this.serverAddress = serverAddress;
		this.samplesQueue = samplesQueue;
		this.userId = userId;
	}

	@Override
	public void run() {
		// catching exceptions which are not supposed to happen
		try {
			run2();
		} catch (IllegalStateException | InterruptedException | IOException e) {
			// TODO: log severe error
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

			// convert to json and sent
			HttpPost post = new HttpPost(serverAddress);
			post.setEntity(new StringEntity(mapper.writeValueAsString(emoPost)));
			HttpResponse response = null;
			try {
				// TODO: log sending
				response = new DefaultHttpClient().execute(post);
			} catch (IOException e) {
				e.printStackTrace();
				// continue
			}

			// log the response
			if (response != null) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				int statusLine = response.getStatusLine().getStatusCode();
				if (statusLine == 200) {
					// TODO: log success
				} else {					
					StringBuilder responseContent = new StringBuilder();
					String line = "";
					while ((line = rd.readLine()) != null) {
						responseContent.append(line);
					}
					// TODO: log error
				}
			}

		}

	}
	
}
