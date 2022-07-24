package game;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8188848270448527548L;
	public static int BOARD_SIZE = 3;

	public static enum GameStatus {
		Incomplete, Xwin, Zwin, Tie
	}

	private JButton[][] jButtons = new JButton[BOARD_SIZE][BOARD_SIZE];
	boolean crossTurn = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe tic = new TicTacToe();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TicTacToe() {
		super.setTitle("Tic-Tac-Toe");
		super.setSize(500, 500);
		GridLayout gridLayout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(gridLayout);
		Font font = new Font("Comic Sans", 1, 150);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton jButton = new JButton("");
				jButtons[row][col] = jButton;
				jButton.setFont(font);
				jButton.addActionListener(this);
				super.add(jButton);
			}
		}
		super.setResizable(false);
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		makeMove(clickedButton);
		GameStatus gameStatus = this.getGameStatus();
		if (gameStatus == GameStatus.Incomplete) {
			return;
		}
		declarewinner(gameStatus);
		int choice = JOptionPane.showConfirmDialog(this, "Do you want to restart the Game");
		if (choice == JOptionPane.YES_OPTION) {
			for (int row = 0; row < BOARD_SIZE; row++) {
				for (int col = 0; col < BOARD_SIZE; col++) {
					jButtons[row][col].setText("");
				}
			}
			crossTurn = true;
		} else {
			super.dispose();
		}
	}

	private void makeMove(JButton clickedButton) {
		String btnText = clickedButton.getText();
		if (btnText.length() > 0) {
			JOptionPane.showMessageDialog(this, "Invalid Move");
		} else {
			if (crossTurn) {
				clickedButton.setText("X");
			} else {
				clickedButton.setText("0");
			}
			crossTurn = !crossTurn;
		}
	}

	private GameStatus getGameStatus() {
		String text1 = "", text2 = "";
		int row = 0, col = 0;

		// check row
		row = 0;
		while (row < BOARD_SIZE) {
			col = 0;
			while (col < BOARD_SIZE - 1) {
				text1 = jButtons[row][col].getText();
				text2 = jButtons[row][col + 1].getText();
				if (!text1.equals(text2) || text1.length() == 0) {
					break;
				}
				col++;
			}

			if (col == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.Xwin;
				} else {
					return GameStatus.Zwin;
				}
			}
			row++;
		}

		// check column
		col = 0;
		while (col < BOARD_SIZE) {
			row = 0;
			while (row < BOARD_SIZE - 1) {
				text1 = jButtons[row][col].getText();
				text2 = jButtons[row + 1][col].getText();
				if (!text1.equals(text2) || text1.length() == 0) {
					break;
				}
				row++;
			}

			if (row == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.Xwin;
				} else {
					return GameStatus.Zwin;
				}
			}
			col++;
		}

		// check diagonal1
		row = 0;
		col = 0;
		while (row < BOARD_SIZE - 1) {
			text1 = jButtons[row][col].getText();
			text2 = jButtons[row + 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0) {
				break;
			}
			col++;
			row++;
		}

		if (row == BOARD_SIZE - 1) {
			if (text1.equals("X")) {
				return GameStatus.Xwin;
			} else {
				return GameStatus.Zwin;
			}
		}

		// check diagonal2
		row = BOARD_SIZE - 1;
		col = 0;
		while (row > 0) {
			text1 = jButtons[row][col].getText();
			text2 = jButtons[row - 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0) {
				break;
			}
			col++;
			row--;
		}

		if (row == 0) {
			if (text1.equals("X")) {
				return GameStatus.Xwin;
			} else {
				return GameStatus.Zwin;
			}
		}

		String txt = "";
		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE; col++) {
				txt = jButtons[row][col].getText();
				if (txt.length() == 0) {
					return GameStatus.Incomplete;
				}
			}
		}
		return GameStatus.Tie;
	}

	private void declarewinner(GameStatus gameStatus) {
		if (gameStatus == GameStatus.Xwin) {
			JOptionPane.showMessageDialog(this, "X Wins");
		} else if (gameStatus == GameStatus.Zwin) {
			JOptionPane.showMessageDialog(this, "Y Wins");
		} else {
			JOptionPane.showMessageDialog(this, "It is a Tie");
		}
	}
}
