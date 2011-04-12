package Meterologi;

////////////////////////////////////////
//Gui-vindu 1: Regsitrer et nytt sted///
//skrevet av Nam Le/////////////////////
////////////////////////////////////////

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class tab1 extends JFrame implements ActionListener
{
	JButton RegNySted; //knapper bør init. uten for metode for at actionevent skal bli returnet
	JTextArea utskrift;
	JTextField innFylke,innSted;

	public JPanel tab1() //utseende
	{
		JPanel p1 = new JPanel();

		innFylke = new JTextField(15);
		innSted = new JTextField(15);

		RegNySted = new JButton("Registrer nytt sted");
		RegNySted.addActionListener(this);

		utskrift = new JTextArea(20,50);

		p1.add(new JLabel("Skriv inn fylket:"));
	    p1.add(innFylke);
		p1.add(new JLabel("Skriv inn et nytt sted"));
		p1.add(innSted);
		p1.add(RegNySted);
		p1.add(utskrift);


		return p1;
	}

	void RegNySted() //utføres etter knappe RegNySted er trykket på
	{
		utskrift.setText("Stedet er registrert!");
	}

	public void actionPerformed(ActionEvent action)
	{
		if(action.getSource() == RegNySted)
			RegNySted();
	}


}