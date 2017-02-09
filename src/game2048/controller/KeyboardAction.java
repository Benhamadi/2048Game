package game2048.controller;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;


import javax.swing.AbstractAction;

import game2048.ClientWriter_Reader;
import game2048.model.Field;
import game2048.view.FieldFrame;

public class KeyboardAction  extends AbstractAction  {

    
    private FieldFrame frame;
     
    private Field model;
    private ClientWriter_Reader w;
    private ClientWriter_Reader r;
    
    
    private String direction;
 
    public KeyboardAction(FieldFrame frame, Field model ,String direction ,Socket connection) {
    	 try {
			
			w =new ClientWriter_Reader(connection.getOutputStream());
			r= new ClientWriter_Reader(connection.getInputStream());
		}  catch (IOException e) {
			e.printStackTrace();
		}
        this.frame = frame;
        this.model = model;
        this.direction=direction;
    }
    public void setModel() throws IOException 
    {
    	int i=-1;
    	for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
            	model.setGrid(x, y, r.ReadMessage());
            	
            }
        }
    	
    	model.setScore(r.readValue());
    }
     
    @Override
    public void actionPerformed(ActionEvent event) {
    	  switch (direction) {
    	  
    	  
    	  
    	  case "up":    
              w.WriteMessage(KeyEvent.VK_UP);
              w.send();
       
              try {
				setModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              
          break;
          

          case "down":  
              w.WriteMessage(KeyEvent.VK_DOWN);
              w.send();
              
              try {
				setModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              
          break;
          
          
          case "left":      
              w.WriteMessage(KeyEvent.VK_LEFT);
              w.send();
              
              try {
				setModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
          break;
          
          
          case "right": 
                  
              w.WriteMessage(KeyEvent.VK_RIGHT);
              w.send();
              
              try {
				setModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
          break;
          case "start":  
             
        	  w.WriteMessage(1);
        	  w.send();
        	  
        	  try {
				setModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      
          break;
        }
    	  
    	    frame.repaintGridPanel();
    }
 
}
