package Meterologi.StatistikkTabs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import Meterologi.Lista;

public class RankingListe extends Lista implements ActionListener {

	private JTextArea utskrift;
	
	private JRadioButton enradioknapp;
	private JRadioButton toradioknapp;
	private ButtonGroup enknappegruppe;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		utskrift = new JTextArea(25,50);
		
		enradioknapp = new JRadioButton("løk");
		enradioknapp.addActionListener(this);
		toradioknapp = new JRadioButton("ost");
		toradioknapp.addActionListener(this);
		
		enknappegruppe = new ButtonGroup();
		enknappegruppe.add(enradioknapp);
		enknappegruppe.add(toradioknapp);
		
		JPanel utvalg = new JPanel();
		utvalg.add(enradioknapp);
		utvalg.add(toradioknapp);
		
		panel.add(utvalg);
		
		panel.add(utskrift);
		
		
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == enradioknapp)
		{//gjør dette
		}
		
		if(e.getSource() == toradioknapp)
		{
			//gjør noe annet
		}
		
	}
}
