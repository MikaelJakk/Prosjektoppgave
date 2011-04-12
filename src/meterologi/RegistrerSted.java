package Meterologi;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;

import Meterologi.Lister.*;
import Meterologi.*;

public class RegistrerSted extends Lista implements ActionListener{
	
	StedListe stedliste;
	private String sted;
	private String fylke;
	private JTextField fylkefelt;
	private JTextField stedfelt;
	private JButton registrerknapp;
	private JButton skrivutknapp;
	private JTextArea utskrift;

	public JPanel ByggPanel()
	{
		JPanel panelet = new JPanel();
		panelet.setLayout(new FlowLayout());
		
		//oppretter et panel for inputfelter og knapper
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(2,0));
		
		JPanel stedpanel = new JPanel();
		stedpanel.setLayout(new FlowLayout());
		fylkefelt = new JTextField(20);
		stedpanel.add(new JLabel("Fylke:"));
		stedpanel.add(fylkefelt);
		stedfelt = new JTextField(20);
		stedpanel.add(new JLabel("Sted:"));
		stedpanel.add(stedfelt);
		toppanel.add(stedpanel);
		
		JPanel knappepanel = new JPanel();
		knappepanel.setLayout(new FlowLayout());
		registrerknapp = new JButton("Registrer nytt sted");
		registrerknapp.addActionListener(this);
		knappepanel.add(registrerknapp);
		skrivutknapp = new JButton("Skriv ut liste");
		skrivutknapp.addActionListener(this);
		knappepanel.add(skrivutknapp);
		toppanel.add(knappepanel);
		
		panelet.add(toppanel);
		
		utskrift = new JTextArea(25,60);
		panelet.add(utskrift);
		
		
		return panelet;
	}
	
	public boolean getInput()
	{
		try{
		sted = stedfelt.getText();
		fylke = fylkefelt.getText();
		}catch(Exception e){melding("problem ved innlesing av sted og fylke");return false;}
		if(sted.length() == 0 || fylke.length() == 0)
		{
			melding("Feltene er ikke riktig utfylt");
			return false;
		}
		return true;
	}
	
	public void melding(String m)
	{
		JOptionPane.showMessageDialog(null,m, "OBS!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == registrerknapp)
		{
			if(!getInput())
				return;
			//stedliste.nyttSted(fylke, sted);
			utskrift.setText("la til nytt sted");
		}
		else if(e.getSource() == skrivutknapp)
		{
			utskrift.setText("Fylke\tSted\n");
			utskrift.append("skal kalle opp stedliste.skrivutliste");
		}
		
	}

}
