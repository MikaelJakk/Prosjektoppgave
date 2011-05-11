package Meterologi;
////////////////////////////////////////
//Gui-vindu 4: STATISTIKK///////////////
//skrevet av Nam Le/////////////////////
////////////////////////////////////////

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class Statistikk extends JFrame implements ActionListener
{
	JButton RegNySted; //knapper bør init. uten for metode for at actionevent skal bli returnet
	JTextArea utskrift,utskrift2,utskrift3,utskrift4;
	JTextField innFylke,innSted;

	public JPanel ByggPanel() //utseende
	{
		JPanel p4 = new JPanel();
		JPanel s1 = new JPanel();
		JPanel s2 = new JPanel();
		JPanel s3 = new JPanel();
		JPanel s4 = new JPanel();

		utskrift = new JTextArea(25,45);
		utskrift2 = new JTextArea(25,45);
		utskrift3 = new JTextArea(25,45);
		utskrift4 = new JTextArea(25,45);

		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Statistikk for alle steder", s1);
		pane.addTab("Rankingliste", s2);
		pane.addTab("Statistikk over år", s3);
		pane.addTab("Statistikk over alle registrerte steder",s4);

		RegNySted = new JButton("trykk meg");
		RegNySted.addActionListener(this);



		s1.add(utskrift);
		s2.add(utskrift2);
		s3.add(utskrift3);
		s4.add(utskrift4);
		p4.add(pane);
		p4.add(RegNySted);
		return p4;
	}

	void RegNySted() //utføres etter knappe RegNySted er trykket på
	{
		utskrift.setText("Registerert");
		utskrift2.setText("Registerert");
		utskrift3.setText("Registerert");
		utskrift4.setText("Registerert");
	}

	public void actionPerformed(ActionEvent action)
	{
		if(action.getSource() == RegNySted)
			RegNySted();
	}


}