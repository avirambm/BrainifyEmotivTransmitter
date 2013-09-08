public class EmoLogger {

	private EmoLoggerUI ui;
	
	public EmoLogger() {
	}
	
	public EmoLogger(EmoLoggerUI ui) {
		this.ui = ui;
	}
	
	public void setUi(EmoLoggerUI ui) {
		this.ui = ui;
	}
	
	public void transInfo(String message) {
		info(EmoTrans.class.getName(), message);
	}

	public void transError(String message) {
		error(EmoTrans.class.getName(), message);
	}

	public void readerInfo(String message) {
		info(EmoReader.class.getName(), message);
	}

	public void readerError(String message) {
		error(EmoReader.class.getName(), message);
	}

	public void readerSample(EmoSample emoSample) {
		ui.displaySample(String
				.format("Sample: time=%10d connection=%.2f meditation=%.2f engagement=%.2f happiness=%.2f excitement=%.2f winkLeft=%-5b winkRight=%-5b turnX=%-5d turnY=%-5d",
						emoSample.getLocalTime(), emoSample.getConnectionStrength(), emoSample.getMeditation(),
						emoSample.getEngagement(), emoSample.getHappiness(), emoSample.getExcitement(),
						emoSample.isWinkLeft(), emoSample.isWinkRight(), emoSample.getTurnX(), emoSample.getTurnY()));
	}

	public void info(String className, String message) {
		outFormat("INFO", className, message);
	}

	public void error(String className, String message) {
		outFormat("ERROR", className, message);
	}

	public void outFormat(String messageType, String className, String message) {
		ui.displayMessage(String.format("%d  %-5s %-9s %s", System.currentTimeMillis(), messageType, className, message));
	}

//	private void out(String message) {
//		ui.displayMessage(message);
//	}

}
