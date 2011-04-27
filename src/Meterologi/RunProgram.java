package Meterologi;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class RunProgram extends Lista{

	
	
		  public static void main(String args[]) {
		    JFrame frame = new JFrame("Window Listener");
		    HovedVindu vindu = new HovedVindu();
		    WindowListener listener = new WindowAdapter() {
		      public void windowClosing(WindowEvent w) {
		        System.exit(0);
		      }
		    };
		    frame.addWindowListener(listener);
		    frame.setSize(300, 300);
		    frame.show();
		  }
		}
	

