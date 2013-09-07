import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class EmoTrans implements Runnable {

	private int samplesToSend;
	private String serverAddress;
	private BlockingQueue<EmoSample> samplesQueue;

	public EmoTrans(int samplesToSend, String serverAddress, BlockingQueue<EmoSample> samplesQueue) {
		this.samplesToSend = samplesToSend;
		this.serverAddress = serverAddress;
		this.samplesQueue = samplesQueue;
	}

	@Override
	public void run() {
		run2();
	}

	private void run2() {
		while (true) {

			// creates the post
			ArrayList<EmoSample> emoSamples = new ArrayList<>();
			for (int i = 0; i < samplesToSend; i++) {
				emoSamples.add(samplesQueue.take());
			}
			EmoSamplesPost emoPost = new EmoSamplesPost();

		}

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(serverAddress);
		StringEntity input = new StringEntity("JSON HERE");
		post.setEntity(input);
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
		}
	}
}
