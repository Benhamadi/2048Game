package game2048;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import game2048.model.Field;
import game2048.view.FieldFrame;
 
public class Client2048 implements Runnable {
 
    @Override
    public void run() {
    	System.out.println("Rentrer le nombre client .");
    	Scanner in = new Scanner(System.in);
    	String str = in.nextLine();
    	int clients =Integer.valueOf(str);
    	for(int i=0 ;i< clients ;i++)
    	{
    		new FieldFrame(new Field() );
    	}
    	 
    	 in.close();
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Client2048());
    }
 
}
