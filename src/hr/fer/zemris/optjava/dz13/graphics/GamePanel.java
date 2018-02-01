package hr.fer.zemris.optjava.dz13.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

public class GamePanel extends JPanel 
{
	private int rows;
	private int cols;
	
	public GamePanel(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		
		setSize(400,400);
		setLayout(new GridLayout(rows, cols));
		
	}
	public void initGUI()
	{
		for(int i=0;i<rows;++i)
		{
			for(int j=0;j<cols;++j)
			{
				JButton panel = new JButton();
				panel.setSize(4,4);
				add(panel);
			}
		}
	}
	public void update(char[][] matrix)
	{
		for(int i=0;i<rows;++i)
		{
			for(int j=0;j<cols;++j)
			{
				Component comp = getComponent(i*rows+j);
				System.out.printf("%c",matrix[i][j]);
				switch (matrix[i][j]) {
				case '1':
					comp.setBackground(Color.GREEN);
					break;
				case '0':
					comp.setBackground(Color.GRAY);
					break;
				default:
					comp.setBackground(Color.WHITE);
					break;
				}
				add(comp,i*rows+j);
			}
			System.out.printf("%n");
		}
		updateUI();
	}
}
