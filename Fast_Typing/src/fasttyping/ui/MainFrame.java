package fasttyping.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import fasttyping.util.UtilFunction;

public class MainFrame extends JFrame implements ActionListener, KeyListener {
	public MainFrame() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblWholeTime;
	private JLabel lblScore;
	private JLabel lblTimePerQuiz;
	private JPanel panelTextQuiz;
	private CardLayout cardLayoutTextQuiz;
	private JLabel lblQuiz;
	private JButton btnStartGame;
	private JTextPane txtpAnswer;

	private String[] data;
	private long wholeTimeDefault;
	private long timePerQuizDefault;
	private long wholeTime;
	private long timePerQuiz;
	private double score;
	private int textArrIndex = 0;
	private int textArrSize = 0;
	private int textIndex = 0;
	private int keyStroke = 0;
	private int keyStrokeWrong = 0;
	private boolean gameStarting = false;

	private Timer timer;
	private SimpleAttributeSet blackAttribute;
	private SimpleAttributeSet redAttributes;
	private DefaultStyledDocument document;

	private JMenuItem exitMenuItem;
	private JMenuItem stopMenuItem;
	private static int TEXT_SIZE = 28;
	private static final String LEFT_TIME_FORMAT = "Left Time %d sec";
	private static final String SCORE_POINT_FORMAT = "Score: %.2f Point";

	public void init() {
		initComponent();
		initlistener();
	}

	/**
	 * set time for quiz
	 * 
	 * @param wholeTime
	 *            whole time
	 * @param timePerQuiz
	 *            time per quiz
	 */
	public void setTime(long wholeTime, long timePerQuiz) {
		this.wholeTimeDefault = wholeTime;
		this.timePerQuizDefault = timePerQuiz;
	}

	/**
	 * set quiz data
	 * 
	 * @param data
	 *            quiz data
	 */
	public void setData(String[] data) {
		this.data = data;
		if (data != null) {
			textArrSize = data.length;
		}
	}

	/**
	 * init all components for this frame
	 */
	private void initComponent() {
		setTitle("Fast Typing");
		// Set size window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle(0, 0, 700, 480));
		setResizable(false);
		getContentPane().setLayout(null);

		// init menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		stopMenuItem = new JMenuItem("Stop");
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(stopMenuItem);
		fileMenu.add(exitMenuItem);
		setJMenuBar(menuBar);

		// init Logo
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(10, 10, 675, 80);
		getContentPane().add(panel);
		panel.setLayout(null);

		// init whole time label
		lblWholeTime = new JLabel(String.format(LEFT_TIME_FORMAT,
				wholeTimeDefault));
		lblWholeTime.setFont(new Font("Arial", Font.BOLD, TEXT_SIZE));
		lblWholeTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblWholeTime.setBounds(50, 110, 600, 40);
		getContentPane().add(lblWholeTime);

		// init score label
		lblScore = new JLabel(String.format(SCORE_POINT_FORMAT, score));
		lblScore.setBorder(new LineBorder(new Color(0, 206, 209), 2, true));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setFont(new Font("Arial", Font.BOLD, 18));
		lblScore.setBounds(100, 160, 500, 30);
		getContentPane().add(lblScore);

		// init time per quiz
		lblTimePerQuiz = new JLabel(timePerQuizDefault + "");
		lblTimePerQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimePerQuiz.setFont(new Font("Arial", Font.BOLD, 40));
		lblTimePerQuiz.setBounds(50, 210, 600, 30);
		getContentPane().add(lblTimePerQuiz);

		// init layout Quiz
		cardLayoutTextQuiz = new CardLayout(0, 0);
		panelTextQuiz = new JPanel();
		panelTextQuiz.setBounds(50, 260, 600, 60);
		getContentPane().add(panelTextQuiz);
		panelTextQuiz.setLayout(cardLayoutTextQuiz);

		// init start button
		btnStartGame = new JButton("Start New Game");
		btnStartGame.setFont(new Font("Arial", Font.BOLD, TEXT_SIZE));
		panelTextQuiz.add(btnStartGame, "Button");

		// init quiz
		lblQuiz = new JLabel("No data");
		lblQuiz.setDisplayedMnemonicIndex(textIndex);
		lblQuiz.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuiz.setFont(new Font("Arial", Font.BOLD, TEXT_SIZE));
		panelTextQuiz.add(lblQuiz, "Data");

		// set to show start button
		cardLayoutTextQuiz.show(panelTextQuiz, "Button");

		// init panel of answer
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(50, 340, 600, 60);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		// init document style for textpane
		document = new DefaultStyledDocument();
		blackAttribute = new SimpleAttributeSet();
		blackAttribute.addAttribute(StyleConstants.CharacterConstants.Bold,
				Boolean.TRUE);
		blackAttribute.addAttribute(
				StyleConstants.CharacterConstants.Foreground, Color.BLACK);
		blackAttribute.addAttribute(StyleConstants.CharacterConstants.FontSize,
				TEXT_SIZE);
		blackAttribute.addAttribute(
				StyleConstants.CharacterConstants.FontFamily, "Arial");
		redAttributes = new SimpleAttributeSet();
		redAttributes.addAttribute(StyleConstants.CharacterConstants.Bold,
				Boolean.TRUE);
		redAttributes.addAttribute(
				StyleConstants.CharacterConstants.Foreground, Color.RED);
		redAttributes.addAttribute(StyleConstants.CharacterConstants.FontSize,
				TEXT_SIZE);
		redAttributes.addAttribute(
				StyleConstants.CharacterConstants.FontFamily, "Arial");
		try {
			if (textArrSize == 0) {
				document.insertString(document.getLength(),
						"Data is not valid", redAttributes);

			} else {
				document.insertString(document.getLength(), "Click ",
						blackAttribute);
				document.insertString(document.getLength(), "Button",
						redAttributes);
				document.insertString(document.getLength(), " to Start Game",
						blackAttribute);
			}
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			document.setParagraphAttributes(0, document.getLength(), center,
					false);

		} catch (BadLocationException badLocationException) {
			System.err.println("Bad insert");
		}

		// init text pane for input answer
		txtpAnswer = new JTextPane(document);
		txtpAnswer.setBackground(Color.LIGHT_GRAY);
		txtpAnswer.setEditable(false);

		panel_2.add(txtpAnswer, BorderLayout.CENTER);
	}

	/**
	 * binding listener
	 */
	private void initlistener() {
		btnStartGame.addActionListener(this);
		txtpAnswer.addKeyListener(this);
		stopMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStartGame) {
			if (!gameStarting) {
				startGame();
			}
		} else if (e.getSource() == stopMenuItem) {
			if (gameStarting) {
				stopGame();
			}
		} else if (e.getSource() == exitMenuItem) {
			if (gameStarting) {
				stopGame();
			}
			System.exit(0);
		}
	}

	/**
	 * Starting game
	 */
	private synchronized void startGame() {
		// set to default value
		wholeTime = wholeTimeDefault;
		timePerQuiz = timePerQuizDefault;
		textArrIndex = 0;
		score = 0;
		keyStroke = 0;
		keyStrokeWrong = 0;
		textIndex = 0;

		// create timer to invoke every 1 sec
		timer = new Timer((int) (1000), new ActionListener() {

			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				if (keyStroke > 0) {
					score = UtilFunction.calWPM(keyStroke, keyStrokeWrong,
							(wholeTimeDefault - wholeTime) * 1000);
				}
				// decease time
				wholeTime--;
				timePerQuiz--;
				// time per quiz is up
				if (timePerQuiz <= 0) {
					changeQuiz();
				}
				// whole time is up
				if (wholeTime <= 0) {
					stopGame();
				}
				UpdateUI();
			}

		});

		// focusing to textpane
		txtpAnswer.requestFocus();
		timer.start();
		changeQuiz();
		gameStarting = true;
	}

	/**
	 * Stopping game
	 */
	private synchronized void stopGame() {
		gameStarting = false;
		// create new thread to stop timer
		new Thread(new Runnable() {
			@Override
			public void run() {
				timer.stop();
			}
		}).start();

		// Back to start game button
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				cardLayoutTextQuiz.show(panelTextQuiz, "Button");

			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// unimplemented
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// unimplemented
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		if (!gameStarting) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT
				|| e.getKeyCode() == KeyEvent.VK_ENTER
				|| e.getKeyCode() == KeyEvent.VK_CAPS_LOCK
				|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			return;
		}

		// get data
		byte[] text = data[textArrIndex].getBytes();
		boolean correct = true;
		// increase key stroke
		keyStroke++;
		// validate input key
		if (text[textIndex] != e.getKeyChar()) {
			correct = false;
			// increase wrong key stroke
			keyStrokeWrong++;
		}
		// update UI
		updateInputText(e.getKeyChar(), correct);
		// increase index of inputing character
		textIndex++;
		// validate end of character of quiz
		if (textIndex == data[textArrIndex].length()) {
			changeQuiz();
		}
	}

	/**
	 * Change quiz
	 */
	private void changeQuiz() {
		textArrIndex++;
		timePerQuiz = timePerQuizDefault;
		textIndex = 0;
		if (textArrIndex >= textArrSize) {
			stopGame();
			return;
		}
		document = new DefaultStyledDocument();
		UpdateUI();
	}

	/**
	 * Add stroked key to answer text pane
	 * 
	 * @param keyChar
	 *            stroked key
	 * @param correct
	 *            correctness
	 */
	private void updateInputText(char keyChar, boolean correct) {
		// calculate word per minute
		score = UtilFunction.calWPM(keyStroke, keyStrokeWrong,
				(wholeTimeDefault - wholeTime) * 1000);
		try {
			if (correct) {
				document.insertString(document.getLength(), "" + keyChar,
						blackAttribute);
			} else {
				document.insertString(document.getLength(), "" + keyChar,
						redAttributes);
			}
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			document.setParagraphAttributes(0, document.getLength(), center,
					false);
		} catch (BadLocationException badLocationException) {
			System.err.println("Bad insert");
		}
		UpdateUI();
	}

	/**
	 * Update all user interface
	 */
	private void UpdateUI() {
		if (!gameStarting) {
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// bringing whole time label
				if (wholeTime < 5) {
					lblWholeTime.setForeground(Color.RED);
				} else if (wholeTime < 20) {
					if (wholeTime % 2 == 0) {
						lblWholeTime.setForeground(Color.ORANGE);
					} else {
						lblWholeTime.setForeground(Color.RED);
					}
				}
				lblWholeTime.setText(String.format(LEFT_TIME_FORMAT, wholeTime));
				lblTimePerQuiz.setText(timePerQuiz + "");
				lblScore.setText(String.format(SCORE_POINT_FORMAT, score));
				txtpAnswer.setDocument(document);
				if (textArrIndex < textArrSize) {
					lblQuiz.setText(data[textArrIndex]);
					// set cursor
					lblQuiz.setDisplayedMnemonicIndex(textIndex);
				}
				cardLayoutTextQuiz.show(panelTextQuiz, "Data");
			}
		});

	}
}
