package game2048.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
 
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game2048.controller.KeyboardAction;
import game2048.model.Field;
 
public class ControlPanel {
     
    private static final Insets regularInsets   = 
            new Insets(10, 10, 0, 10);
     
    private FieldFrame frame;
     
    private Field model;
     
    private JPanel panel;
 
    public ControlPanel(FieldFrame frame, Field model) {
        this.frame = frame;
        this.model = model;
        createPartControl();
    }
     
    private void createPartControl() {
      //  StartGameActionListener listener =          new StartGameActionListener(frame, model);
    	 KeyboardAction listener =          new KeyboardAction(frame, model,"start",frame.getSocket());  
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
 
        int gridy = 0;
         
        JButton startGameButton = new JButton("Start");
        startGameButton.addActionListener(listener);
        addComponent(panel, startGameButton, 0, gridy++, 1, 1, 
                regularInsets, GridBagConstraints.LINE_START, 
                GridBagConstraints.HORIZONTAL);
    }
 
    private void addComponent(Container container, Component component,
            int gridx, int gridy, int gridwidth, int gridheight, 
            Insets insets, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
                gridwidth, gridheight, 1.0D, 1.0D, anchor, fill, 
                insets, 0, 0);
        container.add(component, gbc);
        
    }
     
    public JPanel getPanel() {
        return panel;
    }
}