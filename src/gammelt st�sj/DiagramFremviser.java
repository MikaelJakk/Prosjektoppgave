/*
 * Skrevet at Thomas Nordengen 15.Mai 2011
 */


import javax.swing.JFrame;
import java.awt.EventQueue;


public class DiagramFremviser
{
	public static void main (String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				DiagramVindu diagram = new DiagramVindu();
				diagram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				diagram.setVisible(true);
			}
		});
	}
}