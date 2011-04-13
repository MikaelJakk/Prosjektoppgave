/*
 * Skrevet av Thomas Nordengen,
 * Oppdatert: 12.4.2011
 * Denne klassen skal bygge gui, samt metoder og lytter for registrering av nyData
 * og legges til i Tab.java
 */
package Meterologi;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;
import Meterologi.Lister.*;
import Meterologi.*;


public class RegistrerSted implements ActionListener
{
	private JTextArea utskrift;

	private JComboBox fylkeboks;
	private JTextField stedfelt;
	private JButton skrivut;
	private JButton leggtilny;

	private String fylke;
	private String sted;
	private StedListe stedliste;
	private Sted nyttsted;


	private final String[] fylker = {"Akershus", "Aust-Agder", "Buskerud", "Finnmark",
										"Hedmark","Hordaland","Møre og Romsdal",
										"Nordland","Nord-Trøndelag","Oppland","Oslo","Rogaland",
										"Sogn og Fjordane","Sør-Trøndelag","Telemark",
										"Troms","Vest-Agder","Vestfold","Østfold"};

	public JPanel ByggPanel()
	{
		//GUI på parameter panelet
		JPanel panelet = new JPanel();
		panelet.setLayout(new FlowLayout());

		//panel på alt utenom utskriftsfelt
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(4,0));
		//droppned meny for valg av fylke
		JPanel stedPanel = new JPanel();
		stedPanel.add(new JLabel("Fylke"));
		fylkeboks = new JComboBox(fylker);
		fylkeboks.addActionListener(this);
		stedPanel.add(fylkeboks);
		//Textfelt for innskrivning av navn på Sted
		stedPanel.add(new JLabel("Sted"));
		stedfelt = new JTextField(15);
		stedfelt.addActionListener(this);
		stedPanel.add(stedfelt);
		toppanel.add(stedPanel);
		//knapper
			//leg til nytt sted
		JPanel knappepanel = new JPanel();
		leggtilny = new JButton("Legg til nytt sted");
		leggtilny.addActionListener(this);
		knappepanel.add(leggtilny);
			//skriv ut steder
		skrivut = new JButton("Skriv ut");
		skrivut.addActionListener(this);
		knappepanel.add(skrivut);
		toppanel.add(knappepanel);
		//legg til toppanelet
		panelet.add(toppanel);
		//utskriftvindu
		utskrift = new JTextArea(20,50);
		panelet.add(new JScrollPane(utskrift));
		panelet.setVisible(true);


		//Initsialiserer listen med Fylker og Steder
		stedliste = new StedListe();

		return panelet;
	}//slutt på byggPanel


	public void melding(String melding)
	{
		JOptionPane.showMessageDialog(null,melding, "OBS!", JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean getStedVerdier()
	{
		try
		{
			fylke = (String) fylkeboks.getSelectedItem();
			sted = stedfelt.getText();
		}
		catch(Exception e)
		{
			melding("Det opptod en feil ved val av verdier!");
			return false;
		}
		if(sted.length() == 0)
		{
			melding("Du må skrive inn sted");
			return false;
		}
		return true;
	}
	
	public void tømFelter()
	{
		stedfelt.setText("");
	}


	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == skrivut)
		{
			if(stedliste.tomListe())
				utskrift.setText("Ingen steder i systemet!");
			else
				utskrift.setText(stedliste.toString());
		}
		if(e.getSource() == leggtilny)
		{
			try
			{
				//sjekker at fylke og sted er valgt, og oppretter Sted-Objekt med de innskrevene verdiene
				if(stedfelt.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "fyll inn sted");
				return;
				}
				if(!getStedVerdier())
					return;
				nyttsted  = new Sted(sted, fylke);

				//setter Objektet inn i lista
				boolean dobbeltregistrering = stedliste.fylkeStedEksisterer(nyttsted);
				if(dobbeltregistrering)
				{
					melding("Dette stedet finnes allerede!");
				}
				else
				{
					stedliste.setInnFylke(nyttsted);
					melding("Nytt sted lagt inn i lista");
				}
				tømFelter();
			}
			catch(Exception ex)
			{
				melding("Det oppstod en feil ved registrering av data!");
			}
		}

	}//slutt på ActionPerformed

}//Slutt på RegistrerSted
