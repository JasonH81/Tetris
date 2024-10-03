package jason.tetris;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jason.mycomponents.ScorePanel;
import jason.mycomponents.TitleLabel;

public class Tetris extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ScorePanel scorePanel = new ScorePanel(0, Color.CYAN);
	private BricksPanel bricksPanel = new BricksPanel(this);
	
	public Tetris() {
		initGUI();
		
		setTitle("Tetris");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void initGUI() {
		TitleLabel titleLabel = new TitleLabel("Tetris");
		add(titleLabel, BorderLayout.PAGE_START);
		
		// main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.CYAN);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel, BorderLayout.CENTER);
		
		// score panel
		mainPanel.add(scorePanel);
		
		// bricks panel
		mainPanel.add(bricksPanel);
	}
	
	public void addToScore(int points) {
		scorePanel.addToScore(points);
	}
	
	public void restart() {
		scorePanel.reset();
		bricksPanel.start();
	}

	public static void main(String[] args) {
		try {
			String classname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(classname);
		}
		catch (Exception e) {}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Tetris();
			}
		});
	}

}
