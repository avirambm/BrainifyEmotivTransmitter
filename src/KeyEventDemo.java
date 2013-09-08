/*
 * KeyEventDemo
 */

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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class KeyEventDemo extends JFrame implements KeyListener, ActionListener, Runnable {
	private static final long serialVersionUID = 1L;

	JTextArea displayArea;
	JTextField typingArea;
	static final String newline = System.getProperty("line.separator");

	private EmoReader emoReader;
	private EmoTrans emoTrans;

	@Override
	public void run() {
		/* Use an appropriate Look and Feel */
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
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

		typingArea = new JTextField(20);
		typingArea.addKeyListener(this);

		// Uncomment this if you wish to turn off focus
		// traversal. The focus subsystem consumes
		// focus traversal keys, such as Tab and Shift Tab.
		// If you uncomment the following line of code, this
		// disables focus traversal and the Tab events will
		// become available to the key event listener.
		// typingArea.setFocusTraversalKeysEnabled(false);

		displayArea = new JTextArea();
		displayArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane.setPreferredSize(new Dimension(375, 125));

		getContentPane().add(typingArea, BorderLayout.PAGE_START);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);
	}

	public KeyEventDemo(String name, EmoReader emoReader, EmoTrans emoTrans) {
		super(name);
		this.emoReader = emoReader;
		this.emoTrans = emoTrans;
	}

	/** Handle the button click. */
	public void actionPerformed(ActionEvent e) {
		// Clear the text components.
		displayArea.setText("");
		typingArea.setText("");

		// Return the focus to the typing area.
		typingArea.requestFocusInWindow();
	}

	/*
	 * We have to jump through some hoops to avoid trying to print non-printing
	 * characters such as Shift. (Not only do they not print, but if you put
	 * them in a String, the characters afterward won't show up in the text
	 * area.)
	 */
	void displayInfo(String keyStatus) {
		displayArea.append(keyStatus + newline);
		displayArea.setCaretPosition(displayArea.getDocument().getLength());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'r':
			// energy, happiness, focus, calm
			emoReader.setExcitementOffset(EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case 'f':
			emoReader.setExcitementOffset(-EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case 't':
			emoReader.setHappinessOffset(EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case 'g':
			emoReader.setHappinessOffset(-EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case 'y':
			emoReader.setEngagementOffset(EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case 'h':
			emoReader.setEngagementOffset(-EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case 'u':
			emoReader.setMeditationOffset(EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case 'j':
			emoReader.setMeditationOffset(-EmotivTransmitter.ONCLICK_CHANGE);
			break;
		case '0':
			// reset
			emoReader.setExcitementOffset(0);
			emoReader.setHappinessOffset(0);
			emoReader.setEngagementOffset(0);
			emoReader.setMeditationOffset(0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}