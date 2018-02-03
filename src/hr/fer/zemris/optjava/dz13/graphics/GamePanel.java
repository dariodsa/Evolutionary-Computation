package hr.fer.zemris.optjava.dz13.graphics;

import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;
import hr.fer.zemris.optjava.dz13.genetic.ant.AntPosition;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

public class GamePanel extends JPanel 
{
	private int rows;
	private int cols;
	private int current_move;
	private List<AntPosition> positions = new ArrayList<>();
	
	private char[][] matrix;
	public GamePanel(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.current_move = 0;
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
	private char temp;
	public void draw(char[][] matrix, List<AntPosition> bestOne)
	{
		this.matrix = matrix;
		temp = this.matrix[bestOne.get(current_move).getX()][bestOne.get(current_move).getY()];
		this.matrix[bestOne.get(current_move).getX()][bestOne.get(current_move).getY()]='3';
		update(matrix);
		this.positions = bestOne;
		System.out.println("SIZE " + positions.size());
	}
	public void move()
	{
		++current_move;
		System.out.println("SIZE " + positions.size() + " " +current_move+ " "+positions.get(current_move).getX()+","+positions.get(current_move).getY());
		if(current_move>=positions.size())
			return;
		if(temp=='1')
			temp = '2';
		this.matrix[positions.get(current_move-1).getX()][positions.get(current_move-1).getY()] = temp;
		temp = this.matrix[positions.get(current_move).getX()][positions.get(current_move).getY()];
		this.matrix[positions.get(current_move).getX()][positions.get(current_move).getY()]='3';
		update(matrix);
	}
	private void update(char[][] matrix)
	{
		for(int i=0;i<rows;++i)
		{
			for(int j=0;j<cols;++j)
			{
				Component comp = getComponent(i*rows+j);
				switch (matrix[i][j]) {
				case '1':
					comp.setBackground(Color.GREEN);
					break;
				case '2':
					comp.setBackground(Color.GRAY);
					break;
				case '3':
					comp.setBackground(Color.RED);
					break;
				default:
					comp.setBackground(Color.WHITE);
					break;
				}
				add(comp,i*rows+j);
			}
		}
		updateUI();
	}
}
