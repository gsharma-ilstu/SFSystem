/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ilstu.cast.it.conflict;


import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author josh
 */
public class ConflictTableModel extends DefaultTableModel{
   
    ArrayList<Integer> rowList;
    
    public ConflictTableModel(String[] str, int value)
    {
        super(str, value);
        rowList=new ArrayList<>();
    }
    
    public void addRow( Object[] rowData, int value){
        super.addRow(rowData);
        rowList.add(value);
    }
    
    public int getRow(int row){
       return rowList.get(row);
    }
}
