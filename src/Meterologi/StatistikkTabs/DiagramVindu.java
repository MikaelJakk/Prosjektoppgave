/*
 * Skrevet at Thomas Nordengen 15.Mai 2011
 */

package Meterologi.StatistikkTabs;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiagramVindu extends JFrame
{
	private Diagram diagram;
	private JButton refreshknapp;
	private double g = 10, n = 30;

	public DiagramVindu()
	{
		super("GjennomsnittsTemp for Sted + Valgt år!");
		diagram = new Diagram(g,n);
		refreshknapp = new JButton("Refresh");
		refreshknapp.addActionListener(new Knappelytter());
		Container c = getContentPane();
		c.add(diagram, BorderLayout.CENTER);
		c.add(refreshknapp, BorderLayout.PAGE_END);
	}

	private class Knappelytter implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			if(e.getSource() == refreshknapp)
			{
				diagram.refresh();
			}

		}
	}
}

