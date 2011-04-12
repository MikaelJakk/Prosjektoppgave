package Meterologi;
////////////////////////////////////////
//Gui-vindu 2: Regsitrer Ny data////////
//skrevet av Nam Le/////////////////////
////////////////////////////////////////

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class tab2 extends JFrame implements ActionListener
{
	JButton RegNyData; //knapper bør init. uten for metode for at actionevent skal bli returnet
	JTextArea utskrift;
	JTextField fylke2,min2,max2,dato2,nedbør2,sted2;

	public JPanel tab2() //utseende
	{
		JPanel p2 = new JPanel();

		fylke2 = new JTextField(15);
		min2   = new JTextField(15);
		max2   = new JTextField(15);
		dato2  = new JTextField(15);
		nedbør2= new JTextField(15);
		sted2  = new JTextField(15);

		RegNyData = new JButton("Registrer ny Data");
		RegNyData.addActionListener(this);

		utskrift = new JTextArea(25,45);

		p2.add(new JLabel("Skriv fylke :"));
		p2.add(fylke2);
		p2.add(new JLabel("min temp. :"));
		p2.add(min2);
		p2.add(new JLabel("Skriv sted :"));
		p2.add(sted2);
		p2.add(new JLabel("max temp. :"));
		p2.add(max2);
		p2.add(new JLabel("Skriv dato :"));
		p2.add(dato2);
		p2.add(new JLabel("nedbør mm :"));
		p2.add(nedbør2);

		p2.add(utskrift);
		p2.add(RegNyData);

		return p2;
	}

	void RegNyData() //utføres etter knappe RegNySted er trykket på
	{
		utskrift.setText("nytt data er registrert!");
	}

	public void actionPerformed(ActionEvent action)
	{
		if(action.getSource() == RegNyData)
			RegNyData();
	}


}