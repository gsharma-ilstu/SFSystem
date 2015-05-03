package edu.ilstu.cast.it.util;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

public class ClockPane {

    private JLabel clock;

    public String getClockPane() {
        
    	clock = new JLabel();
        clock.setHorizontalAlignment(JLabel.CENTER);
       // clock.setFont(UIManager.getFont("Dialog").deriveFont(Font.BOLD, 10));
        tickTock();
        
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tickTock();
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
        return clock.getText();
        
    }
    public void tickTock() {
        clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
    }
    
}