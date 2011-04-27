package Meterologi;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

<<<<<<< HEAD
import javax.swing.JFrame;

public class RunProgram extends Lista{
=======
public class RunProgram extends Lista{

	public static HovedVindu vindu = null;

	public static void main(String[] args)
	{
		vindu = new HovedVindu();
		lesLista();
/*
		vindu.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		        lagreLista();
>>>>>>> 94f4ce3b28e794838d57256dc093bdb998bbca52

	
	
		  public static void main(String args[]) {
		    JFrame frame = new JFrame("Window Listener");
		    HovedVindu vindu = new HovedVindu();
		    WindowListener listener = new WindowAdapter() {
		      public void windowClosing(WindowEvent w) {
		        System.exit(0);
<<<<<<< HEAD
		      }
		    };
		    frame.addWindowListener(listener);
		    frame.setSize(300, 300);
		    frame.show();
		  }
		}
	

=======
		    }
		});
		*/
	}
}
>>>>>>> 94f4ce3b28e794838d57256dc093bdb998bbca52
