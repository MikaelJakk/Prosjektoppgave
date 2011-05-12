package Meterologi;

////////////////////////////////////////
///////HovedVindu-gui skrevet av Nam Le og  Mikael Jakhelln
////// tab1.java "Registrer et nytt sted"
////// tab2.java "Regsitrer ny data"
////// tab3.java "Vis Data"
////// tab4.java "Statistikk"
////////////////////////////////////////

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class HovedVindu extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	RegistrerSted regsted = new RegistrerSted(); //tab 1 = "Registrer et nytt sted"
	RegistrerData regdata = new RegistrerData(); //tab 2 = "Regsitrer ny data"
	VisData visdata = new VisData(); //tab 3 = "Vis Data"
	Statistikk stat = new Statistikk(); //tab 4 = "Statistikk"

	public HovedVindu()
	{

		JFrame frame = new JFrame("Hovedvindu");

		JTabbedPane tabs = new JTabbedPane(); // tabs

		JPanel p1 = regsted.ByggPanel();
		JPanel p2 = regdata.ByggPanel();
		JPanel p3 = visdata.ByggPanel();
		JPanel p4 = stat.ByggPanel();

		tabs.addTab("Registrer et nytt sted",null,p1,"Registrering av nye steder");
		tabs.addTab("Registrer ny Data",null,p2,"Registrering av ny meterologisk data");
	    tabs.addTab("Data For Sted",null,p3,"Visning av data for ett bestemt sted");
	    tabs.addTab("Data for Alt",null,p4,"Visning av data for alle registrerte steder");
	    
	    //oppdaterer comboboxer i RegistrerData
	    tabs.addChangeListener(new ChangeListener(){
	    								public void stateChanged(ChangeEvent evt){
	    									try{
	    									regdata.oppdater();
	    									visdata.oppdater();
	    									stat.oppdater();
	    									}catch(Exception ex){System.out.println("Feil: ved " +
	    											"oppdatering av comboboxer"+ex);}
	    									
	    								}
	    							});


		setLayout(new FlowLayout());
	    frame.setPreferredSize( new java.awt.Dimension(800,600) );
		frame.add(tabs);
		frame.setSize(800,600);
		frame.setVisible(true);
	}
}

