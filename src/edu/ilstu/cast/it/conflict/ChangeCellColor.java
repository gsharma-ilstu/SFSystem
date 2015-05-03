/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ilstu.cast.it.conflict;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jabates
 */
public class ChangeCellColor extends DefaultTableCellRenderer{

    int[]  color;
    public ChangeCellColor(int[] myColor)
    {
        setOpaque(true);
        color=new int[8];
        color[0]=myColor[0];
        for(int i=1; i<8; i++)
        {
            color[i]=myColor[i]+color[i-1];
        }
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable list, Object value, boolean isSelected, boolean cellHasFocus, int row, int c) {
        //setText(value.toString());
        JLabel l = (JLabel) super.getTableCellRendererComponent(list, value, isSelected, cellHasFocus, row, c);
        DefaultTableModel tableModel = (DefaultTableModel) list.getModel();
        if(row<color[0]-1)
        {
            l.setBackground(Color.BLUE);
            l.setForeground(Color.LIGHT_GRAY);
        }
        else if( row>= color[0] && row<color[1])
        {
             l.setBackground(Color.ORANGE);
            l.setForeground(Color.BLACK);
             
        }
        else if( row>= color[1] && row<color[2])
        {
            l.setBackground(Color.PINK);
             l.setForeground(Color.BLACK);
        }
        else if( row>= color[2] && row<color[3])
        {
            l.setBackground(Color.CYAN);
             l.setForeground(Color.BLACK);
        }
        else if( row>= color[3] && row<color[4])
        {
            l.setBackground(Color.magenta);
             l.setForeground(Color.BLACK);
        }
        else if( row>= color[4] && row<color[5])
        {
            l.setBackground(Color.GREEN);
             l.setForeground(Color.BLACK);
        }
        else if( row>= color[5] && row<color[6])
        {
            l.setBackground(Color.YELLOW);
             l.setForeground(Color.BLACK);
        }
        else if( row>= color[6] && row<color[7])
        {
            l.setBackground(Color.LIGHT_GRAY);
             l.setForeground(Color.BLACK);
        }
        return l;
    }
    
}
