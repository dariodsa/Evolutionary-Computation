package hr.fer.zemris.optjava.dz13.graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Window extends JFrame 
{
	private int height;
	private int width;
	private int cols;
	private int rows;
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
		setLayout(new GridLayout(rows, cols));
	}
	public void initGUI()
	{
		for(int i=0;i<rows;++i)
		{
			for(int j=0;j<cols;++j)
			{
				JButton button = new JButton("But");
				button.setSize(7, 7);
				add(button);
			}
		}
	}
}
