/*
 * Skrevet av Nam Le og Mikael Jakhelln 10.April 2011
 */

package Meterologi;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.awt.*;
import Meterologi.StatistikkTabs.*;
public class Statistikk extends JFrame implements ActionListener
{
	MånedligeRekorder månedrec = new MånedligeRekorder();
	DatoEkstremer datorekorder = new DatoEkstremer();
	SnittTemp  snitttemp = new SnittTemp();
	ÅrligeEkstremer årligeks = new ÅrligeEkstremer();
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panelet = new JPanel();
		panelet.setLayout(new FlowLayout());

		JPanel p1 = månedrec.ByggPanel();
		JPanel p2 = datorekorder.ByggPanel();
		JPanel p3 = snitttemp.ByggPanel();
		JPanel p4 = årligeks.ByggPanel();

		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Dato Ekstremer", p2);
		pane.addTab("Årlige Ekstremer", p4);
		pane.addTab("Månedlige Rekorder", p1);
		pane.addTab("Snitts Temperatur",p3);
		
		//Oppdaterer utskrift i statistikkvinduene ved bytte av tabs
		pane.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent evt){
				try{
				oppdater();
				}catch(Exception ex){System.out.println("Feil: ved " +
						"oppdatering av comboboxer"+ex);}
				
			}
		});
		
		panelet.add(pane);
		return panelet;
	}
	
	public void oppdater()
	{
		månedrec.getMinTempRekorder();
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}
}