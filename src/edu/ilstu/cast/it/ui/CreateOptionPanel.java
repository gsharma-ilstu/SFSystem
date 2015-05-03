package edu.ilstu.cast.it.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import edu.ilstu.cast.it.vo.SchedulerVO;

public class CreateOptionPanel {

	JPanel 	mainPanel,sectionListPanel,sectionListPanel1, coursePanel,coursePanel1, sectionPanel,sectionPanel1, professorPanel,professorPanel1,
			roomPanel,roomPanel1, sectionDaysPanel,sectionDaysPanel1, timePanel,timePanel1, 
			daysAndTimePanel, daysAndTimeLabelPanel,
			coursePreferenceLabelPanel, coursePreferencePanel,buttonPanel;
	JButton addSection,editSection, deleteSection, saveSection, professorQuery;
	JLabel courseNumLabel, sectionNumLabel, professorNameLabel, roomLabel,
			sectionDaysLabel, timeLabel, eveningClassLabel, labLabel,
			scheduleLabel,fromLabel, toLabel, daysAndTimeLabel, coursePreferenceLabel,timeList,prefList;
	JComboBox courseList, sectionNumList, professorList, roomList, startTime,
			endTime,sectionTypeList;
	JCheckBox monday, tuesday, wednesday, thursday, friday, eveningYes, labYes;
	// JTextField professorNameField;
	
	JScrollPane daysAndTimeAreaScroll,
			coursePreferenceAreaScroll;
	
	DefaultTableModel tableModel = new DefaultTableModel() ;
	JTable tableEdit = new JTable();
	JLabel statusLabel = new JLabel();
	SchedulerVO sVO = new SchedulerVO();
	
	List<String> allSections;
	List<String> allCourses;
	List<String> allProfessors;
	
	// sectoinNums holds the different sections to choose from for a given
	// section
	
	String[] dummy ={"A","B","C"};
	String[] sectionNums = { " ", "1","2","3", "4","5",  "6",  "7", "8",  "9",  "10",
			"11", "12", "13", "14","15", "16", "17", "18", "19",  "20", "21", "22","23","24","25" };

	String[] sectionTypes ={" ","Lab","Grad","UnderGrad"};
	String[] classRooms = { " ", "CVA 151", "OU 213E", "OU 213D", "OU 125",
			"OU 129", "OU 132", "OU 133", "STV 104", "STV 105", "STV 108", "STV 101",
			"STV 139A", "ONLINE" };
	String[] startTimes = { " ", "7:00", "7:15", "7:35", "8:00", "8:15", "8:35",
			"9:00", "9:15", "9:35", "10:00", "10:15", "10:35", "11:00",
			"11:15", "11:35", "12:00", "12:15", "12:35", "1:00", "1:15",
			"1:35", "2:00", "2:15", "2:35", "3:00", "3:15", "3:35", "4:00",
			"4:15", "4:35", "5:00", "5:15", "5:35", "6:00", "6:15", "6:35",
			"7:00", "7:15", "7:35", "8:00", "8:15", "8:35", "9:00", "9:15",
			"9:35", "10:00", "10:15", "10:35" };
	String[] endTimes = { " ", "7:00", "7:15", "7:35", "7:50", "8:00", "8:15",
			"8:35", "8:50", "9:00", "9:15", "9:35", "9:50", "10:00", "10:15",
			"10:35", "10:50", "11:00", "11:15", "11:35", "11:50", "12:00",
			"12:15", "12:35", "12:50", "1:00", "1:15", "1:35", "1:50", "2:00",
			"2:15", "2:35", "2:50", "3:00", "3:15", "3:35", "3:50", "4:00",
			"4:15", "4:35", "4:50", "5:00", "5:15", "5:35", "5:50", "6:00",
			"6:15", "6:35", "6:50", "7:00", "7:15", "7:35", "7:50", "8:00",
			"8:15", "8:35", "8:50", "9:00", "9:15", "9:35", "9:50", "10:00",
			"10:15", "10:35", "10:50" };

	public void createEditOption(JPanel panel,SchedulerVO schedulerVO,DefaultTableModel model,JTable table,JLabel label) throws IOException {

		tableModel = model;
		tableEdit = table;
		statusLabel = label;
		sVO = schedulerVO;
               //   System.out.println(schedulerVO.getPrefMap().toString());
		//System.out.println(tableModel.getRowCount()+"--"+tableModel.getColumnName(0));
		allCourses = new ArrayList<String>();
		allCourses = schedulerVO.getCourseList();

		Collections.sort(allCourses);
		
		allProfessors = schedulerVO.getProfessorList();

		Collections.sort(allProfessors);
		
		allSections = schedulerVO.getSectionList();

		Collections.sort(allSections);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,1));
		
		coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		coursePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		courseNumLabel = new JLabel("Course Number");
		courseList = new JComboBox();
		courseList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

	
		
		
		sectionListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sectionListPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		scheduleLabel = new JLabel("Section Type");
		sectionTypeList = new JComboBox<String>();
		sectionTypeList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	
		for (int i = 0; i < sectionTypes.length; i++) {
			sectionTypeList.addItem(sectionTypes[i]);
		}
		sectionListPanel.add(scheduleLabel);
		sectionListPanel1.add(sectionTypeList);

		// populates the courseList
		courseList.addItem(" ");
		for (int i = 0; i < allCourses.size(); i++) {
			courseList.addItem(allCourses.get(i));

		}

		coursePanel.add(courseNumLabel);
		coursePanel1.add(courseList);

		sectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sectionPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		sectionNumLabel = new JLabel("Section Number");
		sectionNumList = new JComboBox();
		sectionNumList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		// populates the sectionNumList
		for (int i = 0; i < sectionNums.length; i++) {
			sectionNumList.addItem(sectionNums[i]);

		}
		sectionPanel.add(sectionNumLabel);
		sectionPanel1.add(sectionNumList);

		// Creates the professor field
		professorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		professorPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		professorNameLabel = new JLabel("Professor Name");
		professorList = new JComboBox<String>();
		professorList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		professorList.addItem(" ");
		professorQuery = new JButton("Query");
		for (int i = 0; i < allProfessors.size(); i++) {
			professorList.addItem(allProfessors.get(i));
		}
		professorPanel.add(professorNameLabel);
		professorPanel1.add(professorList);
		//professorPanel.add(professorQuery);

		// creates the room field
		roomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		roomPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		roomLabel = new JLabel("Room");
		roomList = new JComboBox(classRooms);
		roomList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		roomPanel.add(roomLabel);
		roomPanel1.add(roomList);

		// creates the section days field
		sectionDaysPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sectionDaysPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		sectionDaysLabel = new JLabel("Section Days");
		sectionDaysPanel.add(sectionDaysLabel);
		monday = new JCheckBox(" M ");
		monday.setHorizontalTextPosition(SwingConstants.LEFT);
		sectionDaysPanel1.add(monday);
		tuesday = new JCheckBox(" T ");
		tuesday.setHorizontalTextPosition(SwingConstants.LEFT);
		sectionDaysPanel1.add(tuesday);
		wednesday = new JCheckBox(" W ");

		wednesday.setHorizontalTextPosition(SwingConstants.LEFT);
		sectionDaysPanel1.add(wednesday);
		thursday = new JCheckBox(" R ");
		thursday.setHorizontalTextPosition(SwingConstants.LEFT);
		sectionDaysPanel1.add(thursday);
		friday = new JCheckBox(" F ");
		friday.setHorizontalTextPosition(SwingConstants.LEFT);
		sectionDaysPanel1.add(friday);

		// creates the time field
		timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		timePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		timeLabel = new JLabel("Time");
		fromLabel = new JLabel("From");
		toLabel = new JLabel("  to  ");
		startTime = new JComboBox<String>(startTimes);
		startTime.setPrototypeDisplayValue("XXXXX");
		endTime = new JComboBox<String>(endTimes);
		endTime.setPrototypeDisplayValue("XXXXX");
		timePanel.add(timeLabel);
		timePanel1.add(fromLabel);
		timePanel1.add(startTime);
		timePanel1.add(toLabel);
		timePanel1.add(endTime);

		

		// adds all input fields to a panel
		
		
		

		// query preference panel
		daysAndTimeLabel = new JLabel("Days and Times");
		timeList = new JLabel("Select Professor to view day preferences");
		coursePreferenceLabel = new JLabel(
				"<html>Course Preference Rating <br>(1: Most Preferred, 5: Least Preferred)</html>");
		
		prefList = new JLabel("Select Professor to view course preferences");
		
		daysAndTimeLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		daysAndTimeLabelPanel.add(daysAndTimeLabel);
		daysAndTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		daysAndTimePanel.add(timeList);
		
		coursePreferenceLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		coursePreferenceLabelPanel.add(coursePreferenceLabel);
		coursePreferencePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		coursePreferencePanel.add(prefList);
		
		
		// Starting the Bottom Panel
		

		// bottomPanelLeft items
		addSection = new JButton("Add");
		addSection.setToolTipText("Adds a new row");
		editSection = new JButton("Edit");
		editSection.setToolTipText("Edits the selected row");
		deleteSection = new JButton("Delete");
		deleteSection.setToolTipText("Deletes the selected row");
		saveSection = new JButton("Save Changes");
		saveSection.setToolTipText("Saves all the changes");
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(addSection);
		buttonPanel.add(editSection);
		buttonPanel.add(deleteSection);
		buttonPanel.add(saveSection);
		
		Box boxes[] = new Box[ 5 ];
		boxes[0]=Box.createVerticalBox();
		
		boxes[0].add(coursePanel);
		boxes[0].add(sectionPanel);
		boxes[0].add(sectionListPanel);
		boxes[0].add(professorPanel);
		boxes[0].add(roomPanel);
		boxes[0].add(sectionDaysPanel);
		boxes[0].add(timePanel);
		boxes[0].add(daysAndTimeLabelPanel);
		
		boxes[1]=Box.createVerticalBox();
		boxes[1].add(coursePanel1);
		boxes[1].add(sectionPanel1);
		boxes[1].add(sectionListPanel1);
		boxes[1].add(professorPanel1);
		boxes[1].add(roomPanel1);
		boxes[1].add(sectionDaysPanel1);
		boxes[1].add(timePanel1);
		boxes[1].add(coursePreferenceLabelPanel);
	
		boxes[2] = Box.createHorizontalBox();
		boxes[2].add(buttonPanel);
		boxes[2].setBounds(0, 0, 100, 50);
		
		boxes[3]=Box.createVerticalBox();
		boxes[3].add(daysAndTimePanel);
		
		boxes[4]=Box.createVerticalBox();
		boxes[4].add(coursePreferencePanel);
		// mainPanel.add(leftPanel, BorderLayout.WEST);

		
		AddSectionListener addASection = new AddSectionListener();
		addSection.addActionListener(addASection);
		/*
		 * Using the JComboBox instead of a Jbutton to upload information into
		 * fields. Issues when sectionList is modified(add, edit, delete)
		 */
		EditSectionListener editASection = new EditSectionListener();
		editSection.addActionListener(editASection);

		SaveSectionListener saveASection = new SaveSectionListener();
		saveSection.addActionListener(saveASection);

		DeleteSectionListener deleteASection = new DeleteSectionListener();
		deleteSection.addActionListener(deleteASection);

		professorList.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	    	
	    	if(professorList.getSelectedIndex()!=0){
	    	 List<String> myList = new ArrayList<String>();
	    	 for ( String key : sVO.getPrefMap().keySet() ) {
	    		   // System.out.println( key );
	    		    if(key.toString().trim().equals(professorList.getSelectedItem().toString().trim()))
    				{
	    		    	myList=sVO.getPrefMap().get(key);
	    		    	 String evening="";
	    		    	 evening = getEvengPreferences(myList).equalsIgnoreCase("") ? "" : " [Evening]";
	    		    	 timeList.setForeground(Color.BLACK);
	    		    	 prefList.setForeground(Color.BLACK);
	    		    	 timeList.setText(getDayPreferences(myList) +evening);
	    		    	 prefList.setText(generateHTML(getCoursePreferences(myList)));
	    		    	 prefList.setHorizontalAlignment(0);
    					break;
    				}
	    		    else{
	    		    	timeList.setForeground(Color.RED);
	    		    	prefList.setForeground(Color.RED);
	    		    	timeList.setText("No Day preference");
	    	    		prefList.setText("No Course preference");
	    		    }
	    		}
	    	
	    	 }
	    	else{
	    		timeList.setForeground(Color.BLUE);
		    	prefList.setForeground(Color.BLUE);
	    		timeList.setText("Select Professor to view Day preference");
	    		prefList.setText("Select Professor to view Course preference");
	    	}
	    }
		});


		tableEdit.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	
		    	resetFields();
				
				rowSelector(tableEdit, tableModel, "Course-Section");
		    	
		    }
		});
		
		
		mainPanel.add(boxes[0],BorderLayout.WEST);
		mainPanel.add(boxes[1],BorderLayout.EAST);
		mainPanel.add(boxes[3]);
		mainPanel.add(boxes[4]);
		mainPanel.add(boxes[2],BorderLayout.SOUTH);
		panel.add(mainPanel);

	}

	
	private class AddSectionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String course ="";
			String section="";
			String instructor="";
			String days="";
			String time="";
			String classroom="";
		
			
			if(courseList.getSelectedIndex() !=0)
			{
				course = courseList.getSelectedItem().toString();
			}
			if(sectionNumList.getSelectedIndex() !=0)
			{
				section= sectionNumList.getSelectedItem().toString()+" ";
			}
			if(sectionTypeList.isEnabled())
			{
				section+= sectionTypeList.getSelectedItem().toString();
			}
			if(professorList.getSelectedIndex() !=0)
			{
				instructor= professorList.getSelectedItem().toString();
			}
			if(roomList.getSelectedIndex() !=0)
			{
				classroom= roomList.getSelectedItem().toString();
			}
			days = getSelectedDays();
			if(startTime.getSelectedIndex() !=0 && endTime.getSelectedIndex() !=0)
			{
				time= startTime.getSelectedItem().toString()+" - "+endTime.getSelectedItem().toString();
			}
			

			if(course.contentEquals(""))
			{
				statusLabel.setForeground(Color.RED);
				statusLabel.setText("You must select a course to add");
			}
			else{
			tableModel.addRow(new Object[] {course,section,instructor,days,time,classroom});
			tableModel.fireTableDataChanged();
			statusLabel.setForeground(Color.GREEN);
			statusLabel.setText("\""+course+"~"+section+"~"+instructor+"~"+days+"~"+time+"~"+classroom+"\" Added successfully");
			resetFields();
			}
		}
	}

	public String generateHTML(MultiMap multiMap){
		String html ="<html><div style='width:200px'>";
		
		String line1 = "";
		String line2 ="";
		String line3 ="";
		String line4="";
		String line5 ="";
		Set<String> keys = multiMap.keySet();
		for (String key : keys) {
			if(key.equalsIgnoreCase("1"))
				line1=key+"."+ multiMap.get(key)+"<br><br>";
			if(key.equalsIgnoreCase("2"))
				line2=key+"."+ multiMap.get(key)+"<br><br>";
			if(key.equalsIgnoreCase("3"))
				line3=key+"."+ multiMap.get(key)+"<br><br>";
			if(key.equalsIgnoreCase("4"))
				line4=key+"."+ multiMap.get(key)+"<br><br>";
			if(key.equalsIgnoreCase("5"))
				line5=key+"."+ multiMap.get(key)+"<br><br>";
			}
		if(line1.equalsIgnoreCase(""))
			line1="1.<br><br>";
		if(line2.equalsIgnoreCase(""))
			line2="2.<br><br>";
		if(line3.equalsIgnoreCase(""))
			line3="3.<br><br>";
		if(line4.equalsIgnoreCase(""))
			line4="4.<br><br>";
		if(line5.equalsIgnoreCase(""))
			line5="5.<br><br>";
		
		html+=line1+line2+line3+line4+line5;
		html+="</html>";
		
		return html;
	}
	
	public String generateHTML(List<String> list){
		String html ="<html> <body style='width:10px'>";
		for(int i=0;i<list.size();i++){
//			//html +="<ol><li  type=\"1\">"+list.get(i).toString()+"</li></ol>";
			html += (i+1)+". "+list.get(i).toString()+"<br>";
		}
		
		
		html+="</html>";
		
		return html;
	}
	
	public String getDayPreferences(List<String> list){
		
		return list.get(0).toString();
	}
	public String getEvengPreferences(List<String> list){
		
		if(list.get(1).contentEquals("Y") || list.get(1).contentEquals("y"))
			return list.get(1).toString();
		else
			return "";
	}
	public MultiMap getCoursePreferences(List<String> list){
		
		//Collections.sort(list);
		MultiMap multiMap = new MultiValueMap();
		String prefNum = "";
		for(int i=2;i<list.size();i++){
		//	System.out.println(list.get(i).toString());
			if(!list.get(i).toString().equals(""))
			{
				if(list.get(i).toString().equals("1")){
					prefNum ="1";
				}
				else if(list.get(i).toString().equals("2")){
					prefNum ="2";
				}
				else if(list.get(i).toString().equals("3")){
					prefNum ="3";
				}
				else if(list.get(i).toString().equals("4")){
					prefNum ="4";
				}
				else if(list.get(i).toString().equals("5")){
					prefNum ="5";
				}
				
				switch (i){
				case 2:multiMap.put(prefNum,"IT 115");break;
				case 3:multiMap.put(prefNum,"IT 140");break;
				case 4:multiMap.put(prefNum,"IT 150");break;
				case 5:multiMap.put(prefNum,"IT 164");break;
				case 6:multiMap.put(prefNum,"IT 165");break;
				case 7:multiMap.put(prefNum,"IT 168");break;
				case 8:multiMap.put(prefNum,"IT 178");break;
				case 9:multiMap.put(prefNum,"IT 179");break;
				case 10:multiMap.put(prefNum,"IT 191");break;
				case 11:multiMap.put(prefNum,"IT 214");break;
				case 12:multiMap.put(prefNum,"IT 225");break;
				case 13:multiMap.put(prefNum,"IT 226");break;
				case 14:multiMap.put(prefNum,"IT 254");break;
				case 15:multiMap.put(prefNum,"IT 261");break;
				case 16:multiMap.put(prefNum,"IT 262");break;
				case 17:multiMap.put(prefNum,"IT 272");break;
				case 18:multiMap.put(prefNum,"IT 275");break;
				case 19:multiMap.put(prefNum,"IT 279");break;
				case 20:multiMap.put(prefNum,"IT 326");break;
				case 21:multiMap.put(prefNum,"IT 327");break;
				case 22:multiMap.put(prefNum,"IT 328");break;
				case 23:multiMap.put(prefNum,"IT 330");break;
				case 24:multiMap.put(prefNum,"IT 332");break;
				case 25:multiMap.put(prefNum,"IT 340");break;
				case 26:multiMap.put(prefNum,"IT 341");break;
				case 27:multiMap.put(prefNum,"IT 350");break;
				case 28:multiMap.put(prefNum,"IT 351");break;
				case 29:multiMap.put(prefNum,"IT 353");break;
				case 30:multiMap.put(prefNum,"IT 354");break;
				case 31:multiMap.put(prefNum,"IT 356");break;
				case 32:multiMap.put(prefNum,"IT 357");break;
				case 33:multiMap.put(prefNum,"IT 375");break;
				case 34:multiMap.put(prefNum,"IT 363");break;
				case 35:multiMap.put(prefNum,"IT 367");break;
				case 36:multiMap.put(prefNum,"IT 372");break;
				case 37:multiMap.put(prefNum,"IT 373");break;
				case 38:multiMap.put(prefNum,"IT 375");break;
				case 39:multiMap.put(prefNum,"IT 376");break;
				case 40:multiMap.put(prefNum,"IT 377");break;
				case 41:multiMap.put(prefNum,"IT 378");break;
				case 42:multiMap.put(prefNum,"IT 379");break;
				case 43:multiMap.put(prefNum,"IT 380");break;
				case 44:multiMap.put(prefNum,"IT 381");break;
				case 45:multiMap.put(prefNum,"IT 382");break;
				case 46:multiMap.put(prefNum,"IT 383");break;
				case 47:multiMap.put(prefNum,"IT 384");break;
				case 48:multiMap.put(prefNum,"IT 388");break;
				case 49:multiMap.put(prefNum,"IT 390");break;
				case 50:multiMap.put(prefNum,"IT 391");break;
				case 51:multiMap.put(prefNum,"IT 432");break;
				case 52:multiMap.put(prefNum,"IT 450");break;
				case 53:multiMap.put(prefNum,"IT 463");break;
				case 54:multiMap.put(prefNum,"IT 467");break;
				case 55:multiMap.put(prefNum,"IT 468");break;
				case 56:multiMap.put(prefNum,"IT 477");break;
				case 57:multiMap.put(prefNum,"IT 478");break;
				case 58:multiMap.put(prefNum,"IT 495");break;
				case 59:multiMap.put(prefNum,"IT 496");break;
				case 60:multiMap.put(prefNum,"IT 497");break;
				default:
			}
			}
		}
		return multiMap;
	}
	
	public String getSelectedDays(){
		
		String days="";
		if(monday.isSelected())
		{
			days+="M";
		}
		if(tuesday.isSelected())
		{
			days+="T";
		}
		if(wednesday.isSelected())
		{
			days+="W";
		}
		if(thursday.isSelected())
		{
			days+="R";
		}
		if(friday.isSelected())
		{
			days+="F";
		}
		return days;
	}
	public void resetFields(){
	
		monday.setSelected(false);
    	tuesday.setSelected(false);
    	wednesday.setSelected(false);
    	thursday.setSelected(false);
    	friday.setSelected(false);
    	courseList.setSelectedIndex(0);
		sectionNumList.setSelectedIndex(0);
		sectionTypeList.setSelectedIndex(0);
		professorList.setSelectedIndex(0);
		startTime.setSelectedIndex(0);
		endTime.setSelectedIndex(0);
		roomList.setSelectedIndex(0);
		timeList.setForeground(Color.BLUE);
    	prefList.setForeground(Color.BLUE);
		timeList.setText("Select Professor to view Day preference");
		prefList.setText("Select Professor to view Course preference");
		
	}


	public class EditSectionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			if(tableEdit.getSelectedRow() == -1){
				if(tableModel.getRowCount() == 0){
					statusLabel.setForeground(Color.RED);
					statusLabel.setText("No rows to edit");
				}
				else{
					statusLabel.setForeground(Color.RED);
					statusLabel.setText("You must select a row to Edit");
				}
			}
			else{
				tableModel.setValueAt(courseList.getSelectedItem().toString(), tableEdit.getSelectedRow(), 0);
				tableModel.setValueAt(sectionNumList.getSelectedItem().toString() +" " +sectionTypeList.getSelectedItem().toString(), tableEdit.getSelectedRow(), 1);
				tableModel.setValueAt(professorList.getSelectedItem().toString(), tableEdit.getSelectedRow(), 2);
				tableModel.setValueAt(getSelectedDays(), tableEdit.getSelectedRow(), 3);
				tableModel.setValueAt(startTime.getSelectedItem().toString()+" - " +endTime.getSelectedItem().toString(), tableEdit.getSelectedRow(), 4);
				tableModel.setValueAt(roomList.getSelectedItem().toString(), tableEdit.getSelectedRow(), 5);
				tableModel.fireTableDataChanged();
				statusLabel.setForeground(Color.GREEN);
				statusLabel.setText("Edit successfull !");
				resetFields();
				
			}
			
			
			
		}
	}

	
	public class DeleteSectionListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			resetFields();
			if(tableEdit.getSelectedRow() == -1){
				if(tableModel.getRowCount() == 0){
					statusLabel.setForeground(Color.RED);
					statusLabel.setText("No rows to delete");
				}
				else{
					statusLabel.setForeground(Color.RED);
					statusLabel.setText("You must select a row to delete");
				}
			}
			else{
				tableModel.removeRow(tableEdit.getSelectedRow());
				tableModel.fireTableDataChanged();
				statusLabel.setForeground(Color.GREEN);
				statusLabel.setText("Delete successfull");
			}
			
			
		}
	}

	
	public class SaveSectionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			saveToVO();
			statusLabel.setForeground(Color.GREEN);
			statusLabel.setText("Changes saved");
			
		}
	}

	
	public SchedulerVO saveToVO(){
		

		ArrayList<SchedulerVO> dataList = new ArrayList<SchedulerVO>();
		DefaultTableModel dtm = (DefaultTableModel) tableEdit.getModel();
	    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
	    String [][] tableData = new String[nRow][nCol];
	    for (int i = 0 ; i < nRow ; i++)
	        {
	    	SchedulerVO dataVO = new SchedulerVO();
	    	for (int j = 0 ; j < nCol ; j++)
	            {
	    		tableData[i][j] = dtm.getValueAt(i,j).toString();
	        	
	        	switch(j){
	        	
	        		case 0:dataVO.setCourse(tableData[i][j]);break;
	        		case 1:dataVO.setSection(tableData[i][j]);;break;
	        		case 2:dataVO.setInstructor(tableData[i][j]);break;
	        		case 3:dataVO.setDays(tableData[i][j]);break;
	        		case 4:dataVO.setTime(tableData[i][j]);break;
	        		case 5:dataVO.setClassroom(tableData[i][j]);break;
	        	}
	        	
	        	
	            }	
	    	dataList.add(dataVO);
	    	
	        }
	    sVO.setSchedulerVOList(dataList);
	
		return sVO;
	}

	public void rowSelector(JTable table, DefaultTableModel model, String view) {

		int indexOfCourse = 0;
		int indexOfSection = 0;
		int indexOfInstructor = 0;
		int indexOfDays = 0;
		int indexOfTime = 0;
		int indexOfClassroom = 0;

		if (view.equalsIgnoreCase("Course-Section")) {
			indexOfCourse = 0;
			indexOfSection = 1;
			indexOfInstructor = 2;
			indexOfDays = 3;
			indexOfTime = 4;
			indexOfClassroom = 5;
		} else if (view.equalsIgnoreCase("Instructor-Assignment")) {
			indexOfCourse = 4;
			indexOfSection = 5;
			indexOfInstructor = 0;
			indexOfDays = 2;
			indexOfTime = 3;
			indexOfClassroom = 1;
		} else if (view.equalsIgnoreCase("Time-Table")) {
			indexOfCourse = 3;
			indexOfSection = 4;
			indexOfInstructor = 2;
			indexOfDays = 0;
			indexOfTime = 1;
			indexOfClassroom = 5;
		}

		courseList.setSelectedItem(model.getValueAt(table.getSelectedRow(), indexOfCourse));
		String sectionNumberType[] = model.getValueAt(table.getSelectedRow(), indexOfSection).toString().split(" ");
		sectionNumList.setSelectedItem(sectionNumberType[0]);
		sectionTypeList.setSelectedItem(sectionNumberType.length > 1 ? sectionNumberType[1] : " ");
		professorList
				.setSelectedItem(model.getValueAt(table.getSelectedRow(), indexOfInstructor).toString().equalsIgnoreCase("") ? ""
						: model.getValueAt(table.getSelectedRow(), indexOfInstructor).toString());
		String[] selectedDays = model.getValueAt(table.getSelectedRow(), indexOfDays).toString().split("");

		if (Arrays.asList(selectedDays).contains("M")) {
			monday.setSelected(true);
		}
		if (Arrays.asList(selectedDays).contains("T")) {
			tuesday.setSelected(true);
		}
		if (Arrays.asList(selectedDays).contains("W")) {
			wednesday.setSelected(true);
		}
		if (Arrays.asList(selectedDays).contains("R")) {
			thursday.setSelected(true);
		}
		if (Arrays.asList(selectedDays).contains("F")) {
			friday.setSelected(true);
		}

		String[] selectedTime = {};
		if (model.getValueAt(table.getSelectedRow(), indexOfTime).toString().equalsIgnoreCase("") || selectedTime.length != 0) {
			selectedTime = new String[0];
		} else {
			selectedTime = model.getValueAt(table.getSelectedRow(), indexOfTime).toString().split("-");
			startTime.setSelectedItem(selectedTime.length > 0 ? selectedTime[0].trim() : " ");
			endTime.setSelectedItem(selectedTime.length > 0 ? selectedTime[1].trim() : " ");
	 
		roomList.setSelectedItem((model.getValueAt(table.getSelectedRow(), indexOfClassroom).toString() != " ") ? model
				.getValueAt(table.getSelectedRow(), indexOfClassroom).toString() : " ");

	}
	}
}
