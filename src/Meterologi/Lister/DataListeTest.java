/*
 * Skrevet av Mikael Jakhelln den 8 april 2011
 */

package Meterologi.Lister;

import java.awt.*;
	import java.util.*;
	import java.awt.event.*;
	import javax.swing.*;
	
public class DataListeTest extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
		private JTextArea utskrift;
		private JButton skrivut;
		private JButton leggtilny;
		private JTextField dagfelt;
		private JTextField månedfelt;
		private JTextField årfelt;
		private JTextField mintempfelt;
		private JTextField maxtempfelt;
		private JTextField nedbørfelt;
		
		private int dag;
		private int måned;
		private int år;
		private double min;
		private double max;
		private double ned;
		
		
		private DataListe dataliste;
		private Data nydata;

		public DataListeTest()
		{
			super("Datalistetest");
			Container c = getContentPane();
			c.setLayout(new FlowLayout());
			
			c.add(new JLabel("Dag"));
			dagfelt = new JTextField(2);
			c.add(dagfelt);
			c.add(new JLabel("Måned"));
			månedfelt = new JTextField(2);
			c.add(månedfelt);
			c.add(new JLabel("År"));
			årfelt = new JTextField(4);
			c.add(årfelt);

			c.add(new JLabel("Mimimumstemp"));
			mintempfelt = new JTextField(2);
			c.add(mintempfelt);
			c.add(new JLabel("Maksimumstemp"));
			maxtempfelt = new JTextField(2);
			c.add(maxtempfelt);
			c.add(new JLabel("Nedbør"));
			nedbørfelt= new JTextField(3);
			c.add(nedbørfelt);
			
			
			leggtilny = new JButton("Registrer Ny Data");
			leggtilny.addActionListener(this);
			c.add(leggtilny);
			
			skrivut = new JButton("skriv ut");
			skrivut.addActionListener(this);
			c.add(skrivut);

			utskrift = new JTextArea(40, 40);
			c.add(new JScrollPane(utskrift));

			setSize(500, 500);
			setVisible(true);
			
			dataliste = new DataListe2();
		}
		
		public void melding(String m)
		{
			JOptionPane.showMessageDialog(null, m);
		}
		
		public boolean getDatoVerdier()
		{
			dag = Integer.parseInt(dagfelt.getText());
			måned = Integer.parseInt(månedfelt.getText());
			år = Integer.parseInt(årfelt.getText());
			if(dag <= 0 || dag > 31)
			{	melding("ugyldig dag");
				return false; 
			}
			if(måned == 0 || måned >12 || måned < 1)
			{	melding("ugyldig måned");
				return false;
			}
			if(år < 1970)
			{	melding("ugyldig årstall");
				return false;
			}
			if(år > 3000)
			{	melding("Morsom eller.\nÅr "+år+" :P");
				return false;
			}
			//må lage test på registrering av datoer som ikke har vært ennå.
			return true;
		}
		
		public boolean getVærVerdier()
		{
			try{
			min = Integer.parseInt(mintempfelt.getText());
			}catch(Exception e){melding("ugyldig mintempverdi");return false;}
			try{
			max = Integer.parseInt(maxtempfelt.getText());
			}catch(Exception e){melding("ugyldig maxtempverdi");return false;}
			try{
			ned = Integer.parseInt(nedbørfelt.getText());
			}catch(Exception e){melding("ugyldig nedbørsverdi");return false;}
			
			if(min <0 )
			{melding("ugyldig nedbørsverdier"); return false;}
			if(max<min)
			{melding("maxnedbør er mindre en minnedbør!");return false;}
			if(max > 9999)
			{melding("ekstremnedbør");}
			
			return true;
		}

		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == skrivut)
			{
				if( dataliste.tomListe() )
					utskrift.setText("ingen data i systemet!");
				else
					utskrift.setText(dataliste.skrivUtListe() );
			}
			if(event.getSource() == leggtilny)
			{	
				try{
					//henter dato input
					if(!getDatoVerdier())
						return;
					//lagrer dato som calendar objekt
					Calendar dato = Calendar.getInstance(); 
					dato.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
					dato.set(år,måned-1,dag);/*-1 fordi Calendar.set() er teit*/
					Calendar nå = Calendar.getInstance();
					if(nå.before(dato))
					{
						melding("regisrtert dato har ikke intruffet ennå");
						return;
					}
					
					if(!getVærVerdier())
						return;
					
					//lager en ny node med dataen
					nydata = new Data(dato, min, max, ned);
					
					//prøver å sette den inn i lista.
					boolean dobbeltregistrering = dataliste.datoEksisterer(nydata);
						
					if(dobbeltregistrering)
					{melding("Det er allerede registrert data på denne datoen");}
					else{
						dataliste.nyData(nydata);
						melding("Data er lagt til");
					}
				}
				catch(Exception ex){melding("Feil ved innsetting av data!");};
			}
		}

		public static void main( String[] args )
		{
			DataListeTest vindu = new DataListeTest();
		    vindu.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		}
	}
