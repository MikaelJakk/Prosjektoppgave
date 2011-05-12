package Meterologi.StatistikkTabs;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class RankingListe {

	private JTextArea utskrift;
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		utskrift = new JTextArea(25,50);
		
		panel.add(utskrift);
		
		
		
		return panel;
	}
}
