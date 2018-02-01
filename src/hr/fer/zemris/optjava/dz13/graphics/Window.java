package hr.fer.zemris.optjava.dz13.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame 
{
	private int height;
	private int width;
	private int cols;
	private int rows;
	
	private JButton btnForward;
	private JButton btnHelp;
	private GamePanel gamePanel; 
	
	public Window(int height,int width,int rows, int cols)
	{
		super("Java DZ 13");
		
		this.height = height;
		this.width  = width;
		this.cols   = cols;
		this.rows   = rows;
		
		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}
	public void initGUI()
	{
		btnForward = new JButton("Next move");
		btnForward.setVisible(true);
		btnHelp    = new JButton("Help");
		btnHelp.addActionListener((e)->{btnHelpClick();});
		JPanel northPanel = new JPanel(new FlowLayout());
		northPanel.add(btnForward);
		northPanel.add(btnHelp);
		add(northPanel,BorderLayout.NORTH);
		
		gamePanel = new GamePanel(rows, cols);
		gamePanel.initGUI();
		add(gamePanel,BorderLayout.CENTER);
	}
	private void btnHelpClick() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Green , Gray , White , colors , help should go here ... ");
		
	}
	public GamePanel getGamePanel()
	{
		return this.gamePanel;
	}
}
