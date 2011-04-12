package Meterologi;

import java.awt.event.WindowEvent;

public class RunProgram extends Lista{

	public static HovedVindu vindu = null;

	public static void main(String[] args)
	{
		vindu = new HovedVindu();
		

		vindu.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		        //lesLista();

		        System.exit(0);
		    }
		});
		
	}
}
