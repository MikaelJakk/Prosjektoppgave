package Meterologi;
////////////////////////////////////////
//Gui-vindu 3: VIS DATA/////////////////
//skrevet av Nam Le/////////////////////
////////////////////////////////////////

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class tab3 extends JFrame implements ActionListener
{
	JButton visData; //knapper bør init. uten for metode for at actionevent skal bli returnet
	JTextArea utskrift;
	JTextField innDato,innSted;

	public JPanel tab3() //utseende
	{
		JPanel p3 = new JPanel();

		innDato = new JTextField(15);
		innSted = new JTextField(15);
		visData = new JButton("Vis Data");
		utskrift = new JTextArea(25,45);
		p3.add(new JLabel("Skriv inn dato:"));
		p3.add(innDato);
		p3.add(new JLabel("Skriv inn sted:"));
		p3.add(innSted);
		p3.add(visData);
		p3.add(utskrift);

		return p3;
	}

	void visData() //utføres etter knappe RegNySted er trykket på
	{
		utskrift.setText("I oslo erre sol ja?");
	}

	public void actionPerformed(ActionEvent action)
	{
		if(action.getSource() == visData)
			visData();
	}


}