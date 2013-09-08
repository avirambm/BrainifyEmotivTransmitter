public class EmoLogger {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	private KeyEventDemo ui;
	
	public EmoLogger() {
	}
	
	public EmoLogger(KeyEventDemo ui) {
		this.ui = ui;
	}
	
	public void setUi(KeyEventDemo ui) {
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
		out(String
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
		out(String.format("%-5s %-9s %s", messageType, className, message));
	}

	private void out(String message) {
		ui.displayInfo(message);
	}

}
