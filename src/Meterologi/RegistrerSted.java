/*
 * Skrevet av Thomas Nordengen, og Mikael Jakhelln April-Mai 2011
 * Denne klassen skal bygge gui, samt metoder og lytter for registrering av nyData
 * og legges til i Tab.java
 */
package Meterologi;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Meterologi.Lister.*;


public class RegistrerSted extends Lista implements ActionListener
{
	private JTextArea utskrift;

	private JComboBox fylkeboks;
	private JTextField stedfelt;
	private JButton skrivut;
	private JButton leggtilny;
	private JButton slett;
	
	private String fylke;
	private String sted;
	private Sted nyttsted;
	String[] stedArray = new String[0];
	
	private final String[] fylker = {"Akershus", "Aust-Agder", "Buskerud", "Finnmark",
										"Hedmark","Hordaland","Møre og Romsdal",
										"Nordland","Nord-Trøndelag","Oppland","Oslo","Rogaland",
										"Sogn og Fjordane","Sør-Trøndelag","Telemark",
										"Troms","Vest-Agder","Vestfold","Østfold", "Svalbard"};
	

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
			//legg til nytt sted
		JPanel knappepanel = new JPanel();
		leggtilny = new JButton("Legg til nytt sted");
		leggtilny.addActionListener(this);
		knappepanel.add(leggtilny);
			//skriv ut steder
		skrivut = new JButton("Vis registrerte steder");
		skrivut.addActionListener(this);
		knappepanel.add(skrivut);
			//slett innskrevet sted
		slett = new JButton("Slett");
		slett.addActionListener(this);
		knappepanel.add(slett);
		toppanel.add(knappepanel);
		//legg til toppanelet
		panelet.add(toppanel);
		//utskriftvindu
		utskrift = new JTextArea(20,50);
		panelet.add(new JScrollPane(utskrift));
		
		panelet.setVisible(true);
		utskrift.setEditable(false);

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
			sted = stedfelt.getText().trim();//trim så det ikke er whitespace på slutten, pga lagringsmåten
			
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
	
	public void skrivUt()
	{
		if(stedliste.tomListe())
			utskrift.setText("Ingen steder i systemet!");
		else
		{utskrift.setText(stedliste.toString());}
	}


	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == fylkeboks )
		{
			if(stedliste.tomListe())
				utskrift.setText("Ingen steder i systemet!");
			else
			{
				utskrift.setText(stedliste.toString());
			
			}
		}
		
		if(e.getSource() == skrivut)
		{
			skrivUt();
		}
		if(e.getSource() == leggtilny)
		{
			try
			{
				//sjekker at fylke og sted er valgt, og oppretter Sted-Objekt med de innskrevene verdiene
				if(stedfelt.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Du har ikke skrevet inn et riktig sted");
				return;
				}
				if(stedfelt.getText().length() != 0)
				{
					int valg = JOptionPane.showConfirmDialog(null, 
							"Vil du registrere "+stedfelt.getText()+"?", "Registrere Sted?",
                            JOptionPane.YES_NO_OPTION);
					if( valg == JOptionPane.NO_OPTION || valg == JOptionPane.CLOSED_OPTION) 
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
					stedliste.settInnFylke(nyttsted);
					lagreLista();
					melding("Nytt sted lagt inn i lista");
				}
				tømFelter();
				lesLista();
			}
			catch(Exception ex)
			{
				melding("Det oppstod en feil ved registrering av data!");
			}
			skrivUt();	
		}//slutt på ActionListener for LeggTilNy
		if(e.getSource() == slett)
		{
				//Sjekker at fylke og sted er valgt
			try{
				if(!getStedVerdier())
					return;
				int valg = JOptionPane.showConfirmDialog(null, "Sikker på at du vil slette: " + fylke +", "+sted+"?",
						"Slette sted?", JOptionPane.YES_NO_OPTION);
				if(valg == JOptionPane.NO_OPTION || valg == JOptionPane.CLOSED_OPTION)
				return;
				
				//Bruke metode som sletter nåværende node i lisa
				stedliste.slettStedNode(fylke,sted);
				//skal slette mappen
				if(!stedliste.slettFil(datamappe+"/"+fylke+"."+sted))
				{
					melding("klarte ikke slette mappen");
					return;
				}
				lagreLista();
				melding("Stedet er slettet!");
				System.out.println("Slettet sted: "+fylke+", "+sted);
			}
			catch(Exception ex){System.out.println("Feil: Ubehandlet unntak ved sletting av sted "+ex);}
			skrivUt();
		}//end of slettValgtSted ActionListener
	
	}//slutt på ActionPerformed

}//Slutt på RegistrerSted

//Github nede?
