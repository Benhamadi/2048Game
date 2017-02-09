package game2048.view;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import game2048.controller.KeyboardAction;

import game2048.model.Field;

public class FieldFrame {
     
    private ControlPanel controlPanel;
     
    private Field model;
     
    private GridPanel gridPanel;
    private Socket socket;
     
    private JFrame frame;
    JTextField Scorefield = null;
    JPanel sidePanel = new JPanel();

     
    public FieldFrame(Field model ) {
    	 try {
    		 
 			this.socket= new  Socket("127.0.0.1",12016);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        this.model = model;
        
        createPartControl();
       
    }
 
    private void createPartControl() {
    
      
        gridPanel = new GridPanel(model);

        controlPanel = new ControlPanel(this, model);
         
        frame = new JFrame();
        frame.setTitle("Game of 2048");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
            
                exitProcedure();
            }
        });
         
        setKeyBindings();
 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(gridPanel);   
        mainPanel.add(createSidePanel());
 
        frame.add(mainPanel);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
    }
 
    private JPanel createSidePanel() {
        sidePanel.setLayout(new BoxLayout(sidePanel, 
                BoxLayout.PAGE_AXIS));
        sidePanel.add(Box.createVerticalStrut(30));
        sidePanel.add(controlPanel.getPanel());
         Scorefield=new JTextField("Score: " + model.getScore());
       if(Scorefield!=null){  sidePanel.add(Scorefield);}
        return sidePanel;
    }
     
    private void setKeyBindings() {
        InputMap inputMap = 
                gridPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
         
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up arrow");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down arrow");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left arrow");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right arrow");
         
        inputMap = gridPanel.getInputMap(JPanel.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up arrow");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down arrow");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left arrow");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right arrow");
 
     
        
        gridPanel.getActionMap().put("up arrow",  new KeyboardAction(this, model,"up",socket));
        gridPanel.getActionMap().put("down arrow", new KeyboardAction(this, model,"down", socket));
        gridPanel.getActionMap().put("left arrow",  new KeyboardAction(this, model,"left",socket));
        gridPanel.getActionMap().put("right arrow", new KeyboardAction(this, model,"right",socket));
        
        
    }
     
    public Socket getSocket() {
		return socket;
	}

	public void exitProcedure() {
        frame.dispose();
        System.exit(0);
    }
     
    public void repaintGridPanel() {
    	
    	gridPanel.repaint();
    	Scorefield.setText("Score: " + model.getScore());
    	sidePanel.repaint();
    	
    }
 
}
