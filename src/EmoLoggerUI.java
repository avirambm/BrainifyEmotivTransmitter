import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EmoLoggerUI extends JFrame implements KeyListener, ActionListener, Runnable {
	private static final long serialVersionUID = 1L;

	private JTextArea samplesArea;
	private JTextArea messagesArea;
//	private JTextField typingArea;
	
	public static final String newline = System.getProperty("line.separator");

//	private EmoReader emoReader;

//	private EmoTrans emoTrans;

	@Override
	public void run() {
		/* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private void createAndShowGUI() {
		// Create and set up the window.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		this.addComponentsToPane();

		// Display the window.
		this.pack();
		this.setVisible(true);
	}

	private void addComponentsToPane() {

		JButton button = new JButton("Clear");
		button.addActionListener(this);
		
		samplesArea = new JTextArea();
		samplesArea.setEditable(false);
		samplesArea.addKeyListener(this);

		messagesArea = new JTextArea();
		messagesArea.setEditable(false);
		messagesArea.addKeyListener(this);
		
		JScrollPane scrollPaneSamples = new JScrollPane(samplesArea);
		scrollPaneSamples.setPreferredSize(new Dimension(1000, 400));
		
		JScrollPane scrollPaneMessages = new JScrollPane(messagesArea);
		scrollPaneMessages.setPreferredSize(new Dimension(1000, 200));

		getContentPane().add(scrollPaneSamples, BorderLayout.PAGE_START);
		getContentPane().add(scrollPaneMessages, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);
	}

	public EmoLoggerUI(String name, EmoReader emoReader, EmoTrans emoTrans) {
		super(name);
//		this.emoReader = emoReader;
//		this.emoTrans = emoTrans;
	}

	/** Handle the button click. */
	public void actionPerformed(ActionEvent e) {
		// Clear the text components.
		messagesArea.setText("");

		// Return the focus to the typing area.
//		typingArea.requestFocusInWindow();
	}

	/*
	 * We have to jump through some hoops to avoid trying to print non-printing
	 * characters such as Shift. (Not only do they not print, but if you put
	 * them in a String, the characters afterward won't show up in the text
	 * area.)
	 */
	void displayMessage(String message) {
		messagesArea.append(message + newline);
		messagesArea.setCaretPosition(messagesArea.getDocument().getLength());
	}
	
	void displaySample(String message) {
		samplesArea.append(message + newline);
		samplesArea.setCaretPosition(samplesArea.getDocument().getLength());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// for debugging
	@Override
	public void keyPressed(KeyEvent e) {
//		switch (e.getKeyChar()) {
//		case 'r':
//			emoReader.setExcitementOffset(EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case 'f':
//			emoReader.setExcitementOffset(-EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case 't':
//			emoReader.setHappinessOffset(EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case 'g':
//			emoReader.setHappinessOffset(-EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case 'y':
//			emoReader.setEngagementOffset(EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case 'h':
//			emoReader.setEngagementOffset(-EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case 'u':
//			emoReader.setMeditationOffset(EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case 'j':
//			emoReader.setMeditationOffset(-EmotivTransmitter.ONCLICK_CHANGE_COGNITIVE);
//			break;
//		case '0':
//			// reset
//			emoReader.setExcitementOffset(0);
//			emoReader.setHappinessOffset(0);
//			emoReader.setEngagementOffset(0);
//			emoReader.setMeditationOffset(0);
//			emoReader.setGyroOffset(0);
//			break;
//		case 'q':
//			emoReader.setGyroOffset(EmotivTransmitter.ONCLICK_CHANGE_VOLUME);
//			break;
//		case 'a':
//			emoReader.setGyroOffset(-EmotivTransmitter.ONCLICK_CHANGE_VOLUME);
//			break;
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}