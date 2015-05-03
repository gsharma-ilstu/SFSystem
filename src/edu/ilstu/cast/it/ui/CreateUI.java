package edu.ilstu.cast.it.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import edu.ilstu.cast.it.impl.CSVReader;
import edu.ilstu.cast.it.impl.CSVWriter;
import edu.ilstu.cast.it.ui.CreateOptionPanel.EditSectionListener;
import edu.ilstu.cast.it.util.ClockPane;
import edu.ilstu.cast.it.util.ColumnTableCellRenderer;
import edu.ilstu.cast.it.util.SetUpUtil;
import edu.ilstu.cast.it.util.StripedTableCellRenderer;
import edu.ilstu.cast.it.vo.SchedulerVO;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author at_gsharma
 *
 */
public class CreateUI {

	private static String columnNamesCSview[] = { "COURSE", "SECTION", "INSTRUCTOR", "DAYS", "TIME", "CLASSROOM" };
	private static String columnNamesCRviewMWF[] = { "MWF", "STV 104", "STV 105", "STV 108", "STV 139A", "OU 213E", "OU 213D" };
	private static String columnNamesCRviewTR[] = { "TR", "STV 104", "STV 105", "STV 108", "STV 139A", "OU 213E", "OU 213D" };
	private static String columnNamesCRviewOthers[] = { "", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
	private static String columnNamesIAview[] = { "INSTRUCTOR", "CLASSROOM", "DAYS", "TIME", "COURSE", "SECTION" };
	private static String columnNamesTTview[] = { "DAYS", "TIME", "INSTRUCTOR", "COURSE", "SECTION", "CLASSROOM" };
	private static String viewList[] = { "Course-Section", "Instructor-Assignment", "Time-Table", "Classroom" };
	private static String themeList[] = { "System", "HiFi", "Noire", "Aluminium", "Sea Glass", "Metal" };
	private static String action = "";
	private static String extension = ".csv";
	private static String filterText = "";
	private static String viewToRender = "";
	private static String theme = "";
	private SchedulerVO schedulerVO = new SchedulerVO();
	private SchedulerVO schedulerVOEditoption;
	private String loadedFileName, filePath;
	private File selectedPfile;
	private boolean firstSave = true;
	private boolean editEnabled = false;
	private boolean firstTime=false;
	

	private JFrame frame;
	private JButton btnCreateNew, btnOpenAnExisting, btnImport, btnSave, btnEditOptions, btnConflict, btnFilter, btnClearFilter,
			btnPreferences, btnCourses, btnProfessors, btnApplyChanges, btnClose;
	private JPanel pBanner, pButtons, pDataTable, pEditOptions, pContent, pStatus, pFilter, pModifySetup, pModifySetupButtons,
			pCommon, pConflictTable;
	private JTabbedPane tabbedPane, tabbedPane1;
	private JLabel lblStatus, lblLogo, lblisu, lblReggie, lblOptions, lblModifySetup, lblTheme, lblTimer, lblImport;
	private JTextField txFilter;
	private TableRowSorter<TableModel> sorter;
	private JComboBox<String> viewOptions, themeOptions;
	private JRadioButton rbYes, rbNo;
	private ButtonGroup group;
	// private Color lightGray = new Color (240, 240, 240);
	private Color lightGray = new Color(230, 230, 230);
	private Font font = new Font("Dialog", Font.BOLD, 12);

	private Font font1 = new Font("Dialog", Font.PLAIN, 12);
	private DefaultTableModel tableModel;
	private JTable table;
	CreateOptionPanel cop = new CreateOptionPanel();
	CSVReader csvReader;
	CSVWriter csvWriter;
	SetUpUtil setUpUtil = new SetUpUtil();
	
	private final static String FILE_DIR = "C:\\SFSystem\\Setup files";

	private JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);

	/**
	 * This method creates the UI and makes calls to other classes depending on
	 * the action performed by the user
	 */
	public void CreateUIScreen() {

		
		csvReader = new CSVReader();

		frame = new JFrame();

		// initializing panels
		pBanner = new JPanel();
		pContent = new JPanel();
		pButtons = new JPanel();
		pStatus = new JPanel();
		pFilter = new JPanel();
		pModifySetup = new JPanel();
		pModifySetupButtons = new JPanel();
		pCommon = new JPanel();
		pDataTable = new JPanel();
		pEditOptions = new JPanel();
		pConflictTable = new JPanel();

		// initializing view as drop down
		viewOptions = new JComboBox<String>(viewList);
		viewOptions.setEnabled(false);

		themeOptions = new JComboBox<>(themeList);
		themeOptions.setSelectedIndex(0);
		themeOptions.setPrototypeDisplayValue("XXXXXXXXXXXXX");

		// initializing labels that contain banner images
		lblStatus = new JLabel();

		lblStatus.setFont(font);
		lblStatus.setForeground(Color.GREEN);
		lblStatus.setText(setUpUtil.initializeSetUp());
		
		txFilter = new JTextField(20);
		txFilter.setFont(font1);
		txFilter.setText("Enter criteria to Filter");
		txFilter.setEnabled(false);
		txFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txFilter.isEnabled())
					txFilter.setText("");
			}

		});

		lblModifySetup = new JLabel("Do you wish to modify the set up?");
		lblModifySetup.setFont(font1);

		lblTheme = new JLabel("Background Theme");
		lblTheme.setFont(font1);

		rbYes = new JRadioButton("Yes");
		rbNo = new JRadioButton("No");
		rbNo.setSelected(true);

		group = new ButtonGroup();
		group.add(rbYes);
		group.add(rbNo);

		rbNo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {

					schedulerVO = cop.saveToVO();
					if (schedulerVO.getSchedulerVOList().size() > 1) {
						btnEditOptions.setEnabled(true);
						viewOptions.setEnabled(true);
						btnConflict.setEnabled(true);
						btnSave.setEnabled(true);
						btnFilter.setEnabled(true);
						btnClearFilter.setEnabled(true);
					} else {
						btnEditOptions.setEnabled(false);
						viewOptions.setEnabled(false);
						btnConflict.setEnabled(false);
						btnSave.setEnabled(false);
						btnFilter.setEnabled(false);
						btnClearFilter.setEnabled(false);
					}
					frame.getContentPane().remove(pDataTable);
					rbYes.setSelected(false);
					btnCreateNew.setEnabled(true);
					btnOpenAnExisting.setEnabled(true);
					btnImport.setEnabled(true);
					frame.getContentPane().remove(pModifySetupButtons);
					//frame.getContentPane().add(pDataTable);
					SwingUtilities.updateComponentTreeUI(frame);

				}
			}
		});

		rbYes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == 1) {
					int returnValue = JOptionPane.showConfirmDialog(null, "Any unsaved changes in the schedule will be lost.\nDo you wish to continue?");
					if (returnValue == JOptionPane.YES_OPTION) {
					
						btnCreateNew.setEnabled(false);
						btnOpenAnExisting.setEnabled(false);
						btnImport.setEnabled(false);
						btnEditOptions.setEnabled(false);
						btnConflict.setEnabled(false);
						btnFilter.setEnabled(false);
						btnClearFilter.setEnabled(false);
						viewOptions.setEnabled(false);
						btnSave.setEnabled(false);
						rbNo.setSelected(false);

						frame.getContentPane().removeAll();
						frame.getContentPane().add(pContent, BorderLayout.NORTH);
						frame.getContentPane().add(pButtons, BorderLayout.SOUTH);
						pDataTable.revalidate();
						pDataTable.repaint();
						frame.getContentPane().add(pModifySetupButtons).setVisible(false);
						frame.getContentPane().add(pDataTable).setVisible(true);
						frame.getContentPane().remove(pEditOptions);
						editEnabled = false;

						frame.getContentPane().remove(pEditOptions);
						frame.getContentPane().remove(pDataTable);
						pModifySetupButtons.setEnabled(true);
						frame.getContentPane().add(pModifySetupButtons);
						SwingUtilities.updateComponentTreeUI(frame);
						pModifySetupButtons.repaint();
						pModifySetupButtons.setVisible(true);
					}
					if (returnValue == JOptionPane.NO_OPTION)
						{
						rbNo.setSelected(true);
						rbYes.setSelected(false);
						btnSave.setEnabled(true);
						frame.getContentPane().add(pDataTable);
						SwingUtilities.updateComponentTreeUI(frame);
						return;
						}
					if (returnValue == JOptionPane.CANCEL_OPTION)
					{
					rbNo.setSelected(true);
					rbYes.setSelected(false);
					btnSave.setEnabled(true);
					frame.getContentPane().add(pDataTable);
					SwingUtilities.updateComponentTreeUI(frame);
					return;
					}
				}
			}
		});

		lblLogo = new JLabel();
		lblLogo.setText("");
		lblLogo.setIcon(new ImageIcon(CreateUI.class.getClass().getResource("/logo.png")));
		// lblLogo.setIcon(new ImageIcon("/logo.png"));

		JPanel logoPanel = new JPanel();
		logoPanel.add(lblLogo);
		logoPanel.setBackground(Color.BLACK);

		lblReggie = new JLabel();
		lblReggie.setText("");
		lblReggie.setIcon(new ImageIcon(CreateUI.class.getClass().getResource("/reggie3.png")));
		// lblReggie.setIcon(new ImageIcon("/reggie3.png"));

		JPanel reggiePanel = new JPanel();
		reggiePanel.add(lblReggie);
		reggiePanel.setBackground(Color.BLACK);

		lblisu = new JLabel();
		lblisu.setText("");
		lblisu.setIcon(new ImageIcon(CreateUI.class.getClass().getResource("/wordmark1.png")));
		// lblisu.setIcon(new ImageIcon("/wordmark1.png"));
		JPanel isuPanel = new JPanel();
		isuPanel.add(lblisu);
		isuPanel.setBackground(Color.BLACK);

		// initializing label for view as drop down
		lblOptions = new JLabel();
		lblOptions.setText("View as      ");
		lblOptions.setFont(font1);
		lblOptions.setHorizontalAlignment(0);

		lblImport = new JLabel("Which of these would you like to import ?");
		lblImport.setFont(font1);

		lblTimer = new JLabel();
		lblTimer.setFont(font);
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblTimer.setText(DateFormat.getDateTimeInstance().format(new Date()));
			}

		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(0);
		timer.start();

		// initializing buttons

		btnPreferences = new JButton("Faculty Preferences");
		btnPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CSVReader reader = new CSVReader();
				try {
					fileLoader("setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (null != filePath && filePath.endsWith("references.csv")) {
					CSVReader csvReader = new CSVReader();
					csvReader.readCsvFile(filePath);
					lblStatus.setForeground(Color.GREEN);
					lblStatus.setText(filePath+" imported");
				}
			}
		});

		btnProfessors = new JButton("Professors");
		btnProfessors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					fileLoader("setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (null != filePath && filePath.endsWith("rofessors.csv")) {
						CSVReader csvReader = new CSVReader();
						csvReader.readCsvFile(filePath);
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText(filePath+" imported");
				
				}
			}
		});

		btnCourses = new JButton("Courses");
		btnCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					fileLoader("setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (null != filePath && filePath.endsWith("ourses.csv")) {
					CSVReader csvReader = new CSVReader();
					csvReader.readCsvFile(filePath);
					lblStatus.setForeground(Color.GREEN);
					lblStatus.setText(filePath+" imported");
					// allCourses = csvReader.getCourses();
				}
			}
		});

		btnApplyChanges = new JButton("Apply Changes");
		btnApplyChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					setUpUtil.copy(new FileInputStream(filePath), FILE_DIR + "\\" + loadedFileName);
					schedulerVOEditoption = csvReader.setUp();
					// schedulerVOEditoption.setSchedulerVOList(schedulerVO.getSchedulerVOList());
					lblStatus.setForeground(Color.GREEN);
					lblStatus.setText("Loaded " + loadedFileName);
					SwingUtilities.updateComponentTreeUI(frame);
				} catch (IOException ex) {
					ex.printStackTrace();
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Error Loading " + loadedFileName + " Error:" + ex.getMessage());
				}

			}

		});

		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				rbYes.setSelected(false);
				rbNo.setSelected(true);
				if (schedulerVO.getSchedulerVOList().size() > 1) {
					btnEditOptions.setEnabled(true);
					viewOptions.setEnabled(true);
					btnConflict.setEnabled(true);
					btnSave.setEnabled(true);
					btnFilter.setEnabled(true);
					btnClearFilter.setEnabled(true);
				} else {
					btnEditOptions.setEnabled(false);
					viewOptions.setEnabled(false);
					btnConflict.setEnabled(false);
					btnSave.setEnabled(false);
					btnFilter.setEnabled(false);
					btnClearFilter.setEnabled(false);
				}

				btnCreateNew.setEnabled(true);
				btnOpenAnExisting.setEnabled(true);
				btnImport.setEnabled(true);
				frame.getContentPane().remove(pModifySetupButtons);
				frame.getContentPane().remove(pDataTable);
				//frame.getContentPane().add(pDataTable);
				SwingUtilities.updateComponentTreeUI(frame);

			}

		});

		btnCreateNew = new JButton("New");
		btnCreateNew.setToolTipText("Create new blank schedule");
		btnOpenAnExisting = new JButton("Open");
		btnOpenAnExisting.setToolTipText("Open an existing schedule");
		btnImport = new JButton("Import");
		btnImport.setToolTipText("Import an existing schedule");
		btnSave = new JButton("Save");
		btnSave.setToolTipText("Save current schedule");
		btnSave.setVisible(true);
		btnSave.setEnabled(false);
		btnEditOptions = new JButton("Edit");
		btnEditOptions.setToolTipText("Click here to edit");
		btnEditOptions.setEnabled(false);

		btnConflict = new JButton("Conflict check");
		btnConflict.setToolTipText("Click here to check conflicts");
		btnConflict.setEnabled(false);

		btnFilter = new JButton("Filter");
		btnFilter.setToolTipText("Click here to filter data rows");
		btnFilter.setEnabled(false);
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!pDataTable.isVisible()) {
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("No Data to Filter");
				}
				filterText = txFilter.getText();
				if (filterText.length() == 0 || filterText.equalsIgnoreCase("Enter criteria to Filter")) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter(filterText));
				}
			}
		});

		btnClearFilter = new JButton("Remove Filter");
		btnClearFilter.setToolTipText("Click here to remove filter");
		btnClearFilter.setEnabled(false);
		btnClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txFilter.setText("");
				sorter.setRowFilter(null);

			}
		});

		// this action creates a new empty schedule
		btnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditOptions.setEnabled(true);
				btnConflict.setEnabled(true);
				btnFilter.setEnabled(true);
				btnClearFilter.setEnabled(true);
				viewOptions.setEnabled(true);
				btnSave.setEnabled(true);
				action = "New";
				
				/*frame.getContentPane().remove(pDataTable);
				frame.getContentPane().remove(pEditOptions);
				createDataTable(true);
				frame.getContentPane().add(pDataTable).setVisible(true);
				firstSave = true;*/
				
				frame.getContentPane().removeAll();
				frame.getContentPane().add(pContent, BorderLayout.NORTH);
				frame.getContentPane().add(pButtons, BorderLayout.SOUTH);
				frame.getContentPane().add(pModifySetupButtons).setVisible(false);
				frame.getContentPane().add(pDataTable).setVisible(true);
				createDataTable(true);
				frame.getContentPane().add(pDataTable).setVisible(true);
				firstSave = true;
				
			}
		});

		// this action opens an existing schedule
		btnOpenAnExisting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.getContentPane().removeAll();
				frame.getContentPane().add(pContent, BorderLayout.NORTH);
				frame.getContentPane().add(pButtons, BorderLayout.SOUTH);
				pDataTable.revalidate();
				pDataTable.repaint();
				frame.getContentPane().add(pModifySetupButtons).setVisible(false);
				frame.getContentPane().add(pDataTable).setVisible(true);
				frame.getContentPane().remove(pEditOptions);
				editEnabled = false;
				btnEditOptions.setEnabled(true);
				btnConflict.setEnabled(true);
				btnFilter.setEnabled(true);
				btnClearFilter.setEnabled(true);
				viewOptions.setEnabled(true);
				btnSave.setEnabled(true);
				action = "Open";
				frame.getContentPane().remove(pDataTable);
				frame.getContentPane().remove(pEditOptions);
				try {
					fileLoader(e.getActionCommand());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().add(pDataTable).setVisible(true);
				firstSave = true;

			}
		});

		// this action imports an existing schedule
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(pContent, BorderLayout.NORTH);
				frame.getContentPane().add(pButtons, BorderLayout.SOUTH);
				pDataTable.revalidate();
				pDataTable.repaint();
				frame.getContentPane().add(pModifySetupButtons).setVisible(false);
				frame.getContentPane().add(pDataTable).setVisible(true);
				frame.getContentPane().remove(pEditOptions);
				editEnabled = false;
				btnEditOptions.setEnabled(true);
				btnConflict.setEnabled(true);
				btnFilter.setEnabled(true);
				btnClearFilter.setEnabled(true);
				viewOptions.setEnabled(true);
				btnSave.setEnabled(true);
				action = "Import";
				frame.getContentPane().remove(pDataTable);
				frame.getContentPane().remove(pEditOptions);
				try {
					fileLoader(e.getActionCommand());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().add(pDataTable).setVisible(true);
				firstSave = true;
			}
		});

		// this action saves the file

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lblStatus.setText("Saving...");
				csvWriter = new CSVWriter();
				// CreateOptionPanel cOP = new CreateOptionPanel();
				schedulerVO = cop.saveToVO();
				Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_SECTION);
				Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_COURSE);

				String result;
				if (action.equalsIgnoreCase("Open") || !firstSave) {

					result = csvWriter.writeCsvFile(schedulerVO, filePath);
					// lblStatus.setForeground(Color.GREEN);
					lblStatus.setText("Save " + result);
					firstSave = false;

				} else if (action.equalsIgnoreCase("Import") || action.equalsIgnoreCase("New")) {

					if (!firstSave) {

						result = csvWriter.writeCsvFile(schedulerVO, filePath);
						// lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Save " + result);
						firstSave = false;

					} else {
						JFileChooser fc = new JFileChooser();
						int returnVal = fc.showSaveDialog(frame);

						if (returnVal == JFileChooser.APPROVE_OPTION) {

						//	String fileName = "";
							File file = fc.getSelectedFile();
							try {
								filePath = file.getCanonicalPath();
							} catch (IOException e1) {
								e1.printStackTrace();
							}

							// check file name ,if extension does not end with
							// .csv
							// , append the extension

							if (!filePath.endsWith(extension))
								filePath = filePath + extension;

							if (file.exists()) {
								returnVal = JOptionPane.showConfirmDialog(fc, "Replace existing file?");

								if (returnVal == JOptionPane.YES_OPTION) {
									// lblStatus.setForeground(Color.GREEN);
									result = csvWriter.writeCsvFile(schedulerVO, filePath);
									lblStatus.setText("Save " + result);
									firstSave = false;
								}
								if (returnVal == JOptionPane.CANCEL_OPTION)
									return;
								if (returnVal == JOptionPane.NO_OPTION)
									return;
							} else {

								result = csvWriter.writeCsvFile(schedulerVO, filePath);

								try {
									lblStatus.setForeground(Color.GREEN);
									lblStatus.setText(result + " ! New Scheduled saved at :" + file.getCanonicalPath().toString()
											);
									cop.resetFields();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								firstSave = false;
							}

						} else {
							lblStatus.setForeground(Color.RED);
							lblStatus.setText("Save command cancelled by user. \n");
						}
					}
				}
			}
		});

		btnEditOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblStatus.setForeground(Color.BLACK);
				lblStatus.setText("Edit Options");

//				schedulerVOEditoption = schedulerVO;
				schedulerVOEditoption = csvReader.setUp();
				schedulerVOEditoption.setSchedulerVOList(schedulerVO.getSchedulerVOList());

				if (editEnabled) {

					frame.getContentPane().removeAll();
					frame.getContentPane().add(pContent, BorderLayout.NORTH);
					frame.getContentPane().add(pButtons, BorderLayout.SOUTH);
					pDataTable.revalidate();
					pDataTable.repaint();
					frame.getContentPane().add(pModifySetupButtons).setVisible(false);
					frame.getContentPane().add(pDataTable).setVisible(true);
					frame.getContentPane().remove(pEditOptions);
					editEnabled = false;
				} else {
					pEditOptions.removeAll();
					try {
						cop.createEditOption(pEditOptions, schedulerVOEditoption, tableModel, table, lblStatus);
						pEditOptions.setVisible(true);
						frame.getContentPane().add(new JScrollPane(pEditOptions), BorderLayout.EAST);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					editEnabled = true;
				}
				SwingUtilities.updateComponentTreeUI(frame);
			}

		});

		btnConflict.addActionListener(new ActionListener() {
			private JDialog mydialog;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (editEnabled)
					schedulerVOEditoption = cop.saveToVO();
				else
					schedulerVOEditoption = schedulerVO;
			
				if(!firstTime){
				if(schedulerVOEditoption.getSchedulerVOList().size()>1)
				{
					
					ConflictUIT cfUi = ConflictUIT.getInstance(schedulerVOEditoption, table, viewOptions.getSelectedItem().toString());
					cfUi.setVisible(true);
				}
				else{
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Not enough rows");
				}
				}
			}
		});

		// this action will change the view as per the option selected in drop
		// down
		viewOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				String selectedView = (String) combo.getSelectedItem();

				if (selectedView.equals("Course-Section")) {
					btnEditOptions.setEnabled(true);
					Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_SECTION);
					Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_COURSE);
					lblStatus.setText("Course-Section");
					renderViews(columnNamesCSview);
					SwingUtilities.updateComponentTreeUI(frame);

				} else if (selectedView.equals("Time-Table")) {
					btnEditOptions.setEnabled(true);
					lblStatus.setText("Time Table view!");
					Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_TIME);
					Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_DAYS);
					renderViews(columnNamesTTview);
					SwingUtilities.updateComponentTreeUI(frame);

				} else if (selectedView.equals("Classroom")) {
					btnEditOptions.setEnabled(false);
					lblStatus.setText("Classroom view!");
					Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_TIME);
					Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_DAYS);

					tabbedPane = new JTabbedPane();
					tabbedPane1 = new JTabbedPane();

					tabbedPane1.addTab("OU 125", new JScrollPane(renderCRViews(columnNamesCRviewOthers, "OU 125")));
					tabbedPane1.addTab("OU 129", new JScrollPane(renderCRViews(columnNamesCRviewOthers, "OU 129")));
					tabbedPane1.addTab("OU 132", new JScrollPane(renderCRViews(columnNamesCRviewOthers, "OU 132")));
					tabbedPane1.addTab("OU 133", new JScrollPane(renderCRViews(columnNamesCRviewOthers, "OU 133")));
					tabbedPane1.addTab("CVA 151", new JScrollPane(renderCRViews(columnNamesCRviewOthers, "CVA 151")));
					tabbedPane1.addTab("STV 101", new JScrollPane(renderCRViews(columnNamesCRviewOthers, "STV 101")));

					tabbedPane.addTab("MWF", new JScrollPane(renderCRViews(columnNamesCRviewMWF, "")));
					tabbedPane.addTab("TR", new JScrollPane(renderCRViews(columnNamesCRviewTR, "")));

					ChangeListener changeListener = new ChangeListener() {
						public void stateChanged(ChangeEvent changeEvent) {
							tabbedPane = (JTabbedPane) changeEvent.getSource();
							int index = tabbedPane.getSelectedIndex();
							if (tabbedPane.getTitleAt(index).equalsIgnoreCase("MWF")) {
								tabbedPane.getComponentAt(0);
							} else if (tabbedPane.getTitleAt(index).equalsIgnoreCase("TR")) {
								tabbedPane.getComponentAt(1);
							} else if (tabbedPane.getTitleAt(index).equalsIgnoreCase("Other Class Rooms")) {
								tabbedPane.getComponentAt(2);
							}

						}
					};
					tabbedPane.addChangeListener(changeListener);
					ChangeListener changeListener1 = new ChangeListener() {
						public void stateChanged(ChangeEvent changeEvent) {
							tabbedPane1 = (JTabbedPane) changeEvent.getSource();
							int index = tabbedPane1.getSelectedIndex();
							if (tabbedPane1.getTitleAt(index).equalsIgnoreCase("OU 125")) {
								tabbedPane1.getComponentAt(0);
							} else if (tabbedPane1.getTitleAt(index).equalsIgnoreCase("OU 129")) {
								tabbedPane1.getComponentAt(1);
							}

							else if (tabbedPane1.getTitleAt(index).equalsIgnoreCase("OU 132")) {
								tabbedPane1.getComponentAt(2);
							} else if (tabbedPane1.getTitleAt(index).equalsIgnoreCase("OU 133")) {
								tabbedPane1.getComponentAt(3);
							} else if (tabbedPane1.getTitleAt(index).equalsIgnoreCase("CVA 151")) {
								tabbedPane1.getComponentAt(4);
							} else if (tabbedPane1.getTitleAt(index).equalsIgnoreCase("STV 101")) {
								tabbedPane1.getComponentAt(5);
							}
						}
					};

					tabbedPane1.addChangeListener(changeListener1);

					pDataTable.removeAll();
					tabbedPane.addTab("Other Class Rooms", tabbedPane1);
					pDataTable.add(tabbedPane);
					SwingUtilities.updateComponentTreeUI(frame);
				} else if (selectedView.equals("Instructor-Assignment")) {
					btnEditOptions.setEnabled(true);
					lblStatus.setText("Instructor-Assignment view!");
					Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_INSTRUCTOR);
					renderViews(columnNamesIAview);
					SwingUtilities.updateComponentTreeUI(frame);
				}

			}
		});

		pBanner.setLayout(new BoxLayout(pBanner, BoxLayout.X_AXIS));
		pBanner.setLayout(new GridLayout(1, 3));
		pBanner.setBackground(Color.GRAY);

		pButtons.setBorder(BorderFactory.createTitledBorder("OPTIONS"));
		pButtons.setLayout(new GridLayout(1, 8));
		pButtons.setBackground(Color.GRAY);

		pContent.setLayout(new BoxLayout(pContent, BoxLayout.Y_AXIS));
		pContent.setBackground(Color.GRAY);

		pStatus.setBorder(BorderFactory.createTitledBorder("STATUS"));
		pStatus.setLayout(new FlowLayout());
		pStatus.setBackground(Color.GRAY);

		pFilter.setBorder(BorderFactory.createTitledBorder("FILTER"));
		pFilter.setLayout(new FlowLayout(FlowLayout.CENTER));
		pFilter.setBackground(Color.GRAY);

		pModifySetup.setBorder(BorderFactory.createTitledBorder("SET UP"));
		pModifySetup.setLayout(new FlowLayout(FlowLayout.CENTER));
		pModifySetup.setBackground(Color.GRAY);

		pModifySetupButtons.setBorder(BorderFactory.createTitledBorder("SET UP OPTIONS"));
		pModifySetupButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		pModifySetupButtons.setBackground(Color.GRAY);

		pCommon.setBorder(BorderFactory.createTitledBorder(""));
		pCommon.setLayout(new GridLayout(1, 2));
		pCommon.setBackground(Color.GRAY);

		pBanner.add(logoPanel);
		pBanner.add(reggiePanel);
		pBanner.add(isuPanel);

		pStatus.add(lblStatus);
		pFilter.add(txFilter);
		pFilter.add(btnFilter);
		pFilter.add(btnClearFilter);

		pModifySetup.add(lblModifySetup);
		pModifySetup.add(rbYes);
		pModifySetup.add(rbNo);
		pModifySetup.add(lblTimer);

		Box boxes[] = new Box[3];
		boxes[0] = Box.createHorizontalBox();

		boxes[1] = Box.createHorizontalBox();

		boxes[2] = Box.createHorizontalBox();

		boxes[0].add(btnCourses);
		boxes[0].add(btnProfessors);
		boxes[0].add(btnPreferences);
		boxes[1].add(themeOptions);
		boxes[2].add(btnApplyChanges);
		boxes[2].add(btnClose);

		JPanel imports = new JPanel();
		imports.setLayout(new FlowLayout());
		imports.add(lblImport, BorderLayout.NORTH);
		imports.add(boxes[0], BorderLayout.CENTER);
		imports.setBackground(Color.GRAY);

		JPanel themes = new JPanel();
		themes.setLayout(new FlowLayout());
		themes.add(lblTheme, BorderLayout.NORTH);
		themes.add(boxes[1], BorderLayout.CENTER);
		themes.setBackground(Color.GRAY);

		JPanel applyChng = new JPanel();
		applyChng.setLayout(new FlowLayout());
		applyChng.add(boxes[2]);
		applyChng.setBackground(Color.GRAY);

		pModifySetupButtons.add(imports, BorderLayout.NORTH);
		pModifySetupButtons.add(themes, BorderLayout.CENTER);
		pModifySetupButtons.add(applyChng, BorderLayout.SOUTH);

		pCommon.add(pFilter);
		pCommon.add(pModifySetup);

		pButtons.add(btnCreateNew);
		pButtons.add(btnOpenAnExisting);
		pButtons.add(btnImport);
		pButtons.add(btnSave);
		pButtons.add(btnEditOptions);
		pButtons.add(btnConflict);
		pButtons.add(lblOptions);
		pButtons.add(viewOptions);

		pContent.add(pBanner, BorderLayout.NORTH);
		pContent.add(pStatus, BorderLayout.CENTER);
		pContent.add(pCommon, BorderLayout.SOUTH);
		// pContent.add(pModifySetup, BorderLayout.SOUTH);

		themeOptions.addActionListener(new ActionListener() {
			// "System","HiFi","Noire","Aluminium","Sea Glass","Metal"};
			@Override
			public void actionPerformed(ActionEvent e) {
				if (themeOptions.getSelectedItem().equals("System")) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Background Theme set to \"System\"");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (themeOptions.getSelectedItem().equals("HiFi")) {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Background Theme set to \"HiFi\"");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (themeOptions.getSelectedItem().equals("Noire")) {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Background Theme set to \"Noire\"");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (themeOptions.getSelectedItem().equals("Aluminium")) {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Background Theme set to \"Aluminium\"");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (themeOptions.getSelectedItem().equals("Sea Glass")) {
					try {
						UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Background Theme set to \"Sea Glass\"");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (themeOptions.getSelectedItem().equals("Metal")) {
					try {
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Background Theme set to \"Metal\"");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (themeOptions.getSelectedItem().equals("Acryl")) {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
						lblStatus.setForeground(Color.GREEN);
						lblStatus.setText("Background Theme set to \"Acryl\"");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		frame.getContentPane().add(pContent, BorderLayout.NORTH);
		frame.getContentPane().add(pButtons, BorderLayout.SOUTH);
		frame.getContentPane().add(pDataTable).setVisible(false);
		frame.getContentPane().add(pModifySetupButtons).setVisible(false);
		frame.getContentPane().add(pEditOptions).setVisible(false);
		SwingUtilities.updateComponentTreeUI(frame);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setResizable(true);
		frame.pack();
		frame.setLocation(25, 25);
		frame.setTitle("Schedule Facilitating System");
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		         int answer = JOptionPane.showConfirmDialog(frame, "Are you Sure?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		         if (answer == JOptionPane.YES_OPTION) 
                             System.exit(0);
		         if(answer == JOptionPane.NO_OPTION)
		        	 return;
		    }
		});
		
	}

	/**
	 * This method loads/opens a .csv file particularly when open/import is
	 * clicked
	 * 
	 * @param actionPerformed
	 * @throws InterruptedException
	 */
	public void fileLoader(String actionPerformed) throws InterruptedException {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return extension;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				final String name = f.getName();
				return name.endsWith(extension);
			}
		});

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			loadedFileName = chooser.getSelectedFile().getName();
			selectedPfile = chooser.getSelectedFile();
			try {
				filePath = selectedPfile.getCanonicalPath();
				if(!(filePath.contains("chedule") || filePath.contains("ourse") || filePath.contains("references") || filePath.contains("rofessor") || filePath.contains("pring") || filePath.contains("all"))){
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Cannot load file");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (actionPerformed.equalsIgnoreCase("Setup")) {
				if(!(filePath.contains("chedule") || filePath.contains("ourse") || filePath.contains("references") || filePath.contains("rofessor"))){
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Cannot load file");
				}else{
				schedulerVOEditoption = csvReader.readCsvFile(filePath);
				}
				} else {
				createDataTable(false);
			}
		} else {
			lblStatus.setForeground(Color.RED);
			if (actionPerformed.equalsIgnoreCase("Open"))
				lblStatus.setText("Open Command cancelled by user. \n");

			else if (actionPerformed.equalsIgnoreCase("Import"))
				lblStatus.setText("Import Command cancelled by user. \n");

			if (firstSave) {
				btnEditOptions.setEnabled(false);
				btnFilter.setEnabled(false);
				btnClearFilter.setEnabled(false);
				viewOptions.setEnabled(false);
				btnSave.setEnabled(false);
			}
		}

	}

	/**
	 * This method creates data table depending on the action performed by user
	 * 
	 * @param isNew
	 */
	public void createDataTable(boolean isNew) {
		pDataTable.removeAll();
		ArrayList<SchedulerVO> sVOList = new ArrayList<SchedulerVO>();
		schedulerVO.setSchedulerVOList(sVOList);
		tableModel = new DefaultTableModel(columnNamesCSview, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};

		table = new JTable(tableModel);
		JScrollPane tableContainer = new JScrollPane(table);
		tableModel.fireTableDataChanged();

		StripedTableCellRenderer.installInTable(table, lightGray, Color.BLACK, null, null);

		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		table.setRowHeight(25);
		if (isNew) {
			txFilter.setText("Enter criteria to Filter");
			txFilter.setEnabled(false);
			lblStatus.setForeground(Color.BLACK);
			lblStatus.setText("Creating new schedule");
			for (int i = 0; i < 1; i++) {
				Object rows[] = { "", "", "", "", "", "" };
				tableModel.addRow(rows);
			}

			pDataTable.setLayout(new BoxLayout(pDataTable, BoxLayout.Y_AXIS));
			pDataTable.setBorder(BorderFactory.createTitledBorder(""));
			pDataTable.setLayout(new BorderLayout());
			pDataTable.add(tableContainer, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(frame);
			pDataTable.repaint();
			pDataTable.setVisible(true);
			lblStatus.setForeground(Color.GREEN);
			lblStatus.setText("New schedule created");
		} else {
			txFilter.setEnabled(true);

			txFilter.setText("Enter criteria to Filter");
			table.setDefaultRenderer(Object.class, new ColumnTableCellRenderer());
			lblStatus.setForeground(Color.BLACK);
			lblStatus.setText("Opening schedule " + filePath);
			if(!(filePath.contains("chedule"))){
				lblStatus.setForeground(Color.RED);
				lblStatus.setText("Cannot load file");
			}else{
			schedulerVO = csvReader.readCsvFile(filePath);
			for (int i = 0; i < schedulerVO.getSchedulerVOList().size(); i++) {
				Object rows[] = { schedulerVO.getSchedulerVOList().get(i).getCourse(),
						schedulerVO.getSchedulerVOList().get(i).getSection(),
						schedulerVO.getSchedulerVOList().get(i).getInstructor(),
						schedulerVO.getSchedulerVOList().get(i).getDays(), schedulerVO.getSchedulerVOList().get(i).getTime(),
						schedulerVO.getSchedulerVOList().get(i).getClassroom() };

				tableModel.addRow(rows);
			}

			lblStatus.setForeground(Color.GREEN);
			lblStatus.setText("Loaded schedule : " + loadedFileName + " from location " + filePath);
			pDataTable.setLayout(new BoxLayout(pDataTable, BoxLayout.Y_AXIS));
			pDataTable.setBorder(BorderFactory.createTitledBorder(""));
			pDataTable.setLayout(new BorderLayout());
			pDataTable.add(tableContainer, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(frame);
			pDataTable.repaint();
			pDataTable.setVisible(true);
			}
		}

	}

	/**
	 * This method creates conflict table performing a check on the currently
	 * loaded schedule
	 * 
	 * @param event
	 */
	/*public void createConflictTable(ActionEvent event) {
		if (!action.equalsIgnoreCase("New")) {

			final JScrollPane tableContainer;

			pEditOptions.removeAll();

			tableModel = new DefaultTableModel(columnNamesCSview, 0) {
				public boolean isCellEditable(int row, int column) {
					return false;// This causes all cells to be not editable
				}
			};

			table = new JTable(tableModel);
			table.setRowHeight(20);
			tableContainer = new JScrollPane(table);
			StripedTableCellRenderer.installInTable(table, lightGray, Color.BLACK, null, null);
			table.setDefaultRenderer(Object.class, new ColumnTableCellRenderer());

			Collections.sort(schedulerVO.getSchedulerVOList(), SchedulerVO.COMPARE_BY_CLASSROOM);
			for (int j = 0; j < schedulerVO.getSchedulerVOList().size(); j++) {

				Object rows[] = { schedulerVO.getSchedulerVOList().get(j).getClassroom(),
						schedulerVO.getSchedulerVOList().get(j).getDays(), schedulerVO.getSchedulerVOList().get(j).getTime(),
						schedulerVO.getSchedulerVOList().get(j).getCourse(),
						schedulerVO.getSchedulerVOList().get(j).getSection(),
						schedulerVO.getSchedulerVOList().get(j).getInstructor() };

				tableModel.addRow(rows);

			}

			frame.remove(pEditOptions);
			tableModel.fireTableDataChanged();

			JButton btnNewWindow = new JButton("Open in new window");
			btnNewWindow.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					pEditOptions.setVisible(false);
					JDialog mydialog = new JDialog();
					mydialog.setSize(new Dimension(800, 600));
					mydialog.setTitle("CONFLICTS");
					mydialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					mydialog.setModal(true);

					// mydialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
					ConflictUIT cfUi = new ConflictUIT(schedulerVO);
					mydialog.add(cfUi);
					mydialog.setVisible(true);

				}
			});

			lblStatus.setText("Conflicts in Schedule : " + loadedFileName);
			pEditOptions.setLayout(new BoxLayout(pEditOptions, BoxLayout.Y_AXIS));
			pEditOptions.setBorder(BorderFactory.createTitledBorder("CONFLICTS"));
			pEditOptions.add(btnNewWindow, BorderLayout.PAGE_START);
			pEditOptions.add(tableContainer, BorderLayout.PAGE_END);
			SwingUtilities.updateComponentTreeUI(frame);
			pEditOptions.repaint();

			if (pEditOptions.isVisible())
				pEditOptions.setVisible(false);
			else {
				pEditOptions.setVisible(true);
				frame.getContentPane().add(pEditOptions, BorderLayout.EAST);
			}

		} else {
			lblStatus.setForeground(Color.RED);
			lblStatus.setText("Please save the schedule to check conflicts");
		}
	}*/

	/*
	 * public void createEditOptions(ActionEvent event) { if
	 * (!action.equalsIgnoreCase("New")) {
	 * 
	 * final JScrollPane tableContainer;
	 * 
	 * pEditOptions.removeAll();
	 * 
	 * tableModel = new DefaultTableModel(columnNamesCSview, 0) { public boolean
	 * isCellEditable(int row, int column) { return false;// This causes all
	 * cells to be not editable } };
	 * 
	 * table = new JTable(tableModel); table.setRowHeight(20); tableContainer =
	 * new JScrollPane(table); StripedTableCellRenderer.installInTable(table,
	 * lightGray, Color.BLACK, null, null);
	 * table.setDefaultRenderer(Object.class, new ColumnTableCellRenderer());
	 * 
	 * Collections.sort(schedulerVO.getSchedulerVOList(),
	 * SchedulerVO.COMPARE_BY_CLASSROOM); for (int j = 0; j <
	 * schedulerVO.getSchedulerVOList().size(); j++) {
	 * 
	 * Object rows[] = { schedulerVO.getSchedulerVOList().get(j).getClassroom(),
	 * schedulerVO.getSchedulerVOList().get(j).getDays(),
	 * schedulerVO.getSchedulerVOList().get(j).getTime(),
	 * schedulerVO.getSchedulerVOList().get(j).getCourse(),
	 * schedulerVO.getSchedulerVOList().get(j).getSection(),
	 * schedulerVO.getSchedulerVOList().get(j).getInstructor() };
	 * 
	 * tableModel.addRow(rows);
	 * 
	 * }
	 * 
	 * frame.remove(pEditOptions); tableModel.fireTableDataChanged();
	 * 
	 * JButton btnNewWindow = new JButton("Open in new window");
	 * btnNewWindow.addActionListener(new ActionListener() { private JDialog
	 * mydialog;
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 * pEditOptions.setVisible(false); mydialog = new JDialog();
	 * mydialog.setSize(new Dimension(800, 600));
	 * mydialog.setTitle("CONFLICTS");
	 * mydialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
	 * mydialog.add(tableContainer); mydialog.setVisible(true);
	 * 
	 * } });
	 * 
	 * lblStatus.setText("Conflicts in Schedule : " + loadedFileName);
	 * pEditOptions.setLayout(new BoxLayout(pEditOptions, BoxLayout.Y_AXIS));
	 * pEditOptions.setBorder(BorderFactory.createTitledBorder("CONFLICTS"));
	 * pEditOptions.add(btnNewWindow, BorderLayout.PAGE_START);
	 * pEditOptions.add(tableContainer, BorderLayout.PAGE_END);
	 * SwingUtilities.updateComponentTreeUI(frame); pEditOptions.repaint();
	 * 
	 * if (pEditOptions.isVisible()) pEditOptions.setVisible(false); else {
	 * pEditOptions.setVisible(true); frame.getContentPane().add(pEditOptions,
	 * BorderLayout.EAST); }
	 * 
	 * } else { // lblStatus.setForeground(Color.RED);
	 * lblStatus.setText("Please save the schedule to check conflicts"); } }
	 */
	/**
	 * This method is used to render different views - Ex.
	 * Course-section,Time-Table & Instructor-Assignment
	 * 
	 * @param view
	 *            is the column Header
	 */
	public void renderViews(String[] view) {
		JScrollPane tableContainer;
		pDataTable.removeAll();
		tableModel = new DefaultTableModel(view, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		table = new JTable(tableModel);
		tableContainer = new JScrollPane(table);

		if (editEnabled)
			schedulerVOEditoption = cop.saveToVO();
		else
			schedulerVOEditoption = schedulerVO;
		StripedTableCellRenderer.installInTable(table, lightGray, Color.BLACK, null, null);
		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new ColumnTableCellRenderer());
		for (int j = 0; j < schedulerVOEditoption.getSchedulerVOList().size(); j++) {

			if (view.equals(columnNamesIAview)) {
				if (editEnabled)
					cop.resetFields();
				viewToRender = "Instructor-Assignment";
				Object rows[] = { schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getDays(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getCourse(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getSection() };
				tableModel.addRow(rows);
			} else if (view.equals(columnNamesCSview)) {
				if (editEnabled)
					cop.resetFields();
				viewToRender = "Course-Section";
				Object[] builder = { schedulerVOEditoption.getSchedulerVOList().get(j).getCourse(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getDays(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom() };
				tableModel.addRow(builder);
			} else if (view.equals(columnNamesTTview)) {
				if (editEnabled)
					cop.resetFields();
				viewToRender = "Time-Table";
				Object rows[] = { schedulerVOEditoption.getSchedulerVOList().get(j).getDays(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getCourse(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
						schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom() };
				tableModel.addRow(rows);
			}

		}

		frame.remove(pDataTable);
		tableModel.fireTableDataChanged();
		txFilter.setEnabled(true);
		txFilter.setText("Enter criteria to Filter");
		lblStatus.setForeground(Color.GREEN);
		lblStatus.setText("Loaded schedule : " + loadedFileName + " with "
				+ (schedulerVOEditoption.getSchedulerVOList().size() - 1) + " rows");

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (editEnabled) {
					cop.resetFields();
					cop.rowSelector(table, tableModel, viewToRender);
				}
			}
		});

		pDataTable.setLayout(new BoxLayout(pDataTable, BoxLayout.Y_AXIS));
		pDataTable.setBorder(BorderFactory.createTitledBorder(""));
		pDataTable.setLayout(new BorderLayout());
		pDataTable.add(tableContainer, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(frame);
		pDataTable.repaint();
		pDataTable.setVisible(true);
		frame.getContentPane().add(pDataTable);

	}

	/*
	 * public void checkAvailableFields(SchedulerVO schedulerVO, List
	 * coursesList){ List<String> availableCourses = new ArrayList<String>();
	 * for(int a=0;a<schedulerVO.getSchedulerVOList().size();a++){
	 * if(!"CourseList"
	 * .contains(schedulerVO.getSchedulerVOList().get(a).getCourse())){
	 * availableCourses.add("CourseList Value"); } } }
	 */

	/**
	 * This method is used to render different types of classroom views.
	 * 
	 * @param1 view is the type of column header for tabs ex.
	 *         columnNamesCRviewMWF/columnNamesCRviewTR/columnNamesCRviewOthers
	 * @param2 tabName is the name of the tab ex. MWF/TR/Other class rooms
	 * @returns a formatted JTable to be rendered in respective tabs.
	 */
	public JTable renderCRViews(String[] view, String tabName) {
		JScrollPane tableContainer;

		tableModel = new DefaultTableModel(view, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table = new JTable(tableModel);
		tableContainer = new JScrollPane(table);

		if (editEnabled)
			schedulerVOEditoption = cop.saveToVO();
		else
			schedulerVOEditoption = schedulerVO;

		StripedTableCellRenderer.installInTable(table, lightGray, Color.BLACK, null, null);
		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);

//		table.setRowHeight(115);
		table.setDefaultRenderer(Object.class, new ColumnTableCellRenderer());
		txFilter.setEnabled(true);
		txFilter.setText("Enter criteria to Filter");
		hideEditOptionsPanel();

		Collections.sort(schedulerVOEditoption.getSchedulerVOList(), schedulerVOEditoption.COMPARE_BY_TIME);
		if (view.equals(columnNamesCRviewMWF)) {
                    setClassroomView("MWF", columnNamesCRviewMWF);
                }
                else if(view.equals(columnNamesCRviewTR))
                {
                    setClassroomView("TR", columnNamesCRviewTR);
                }else if (view.equals(columnNamesCRviewOthers)) {
                    for (int j = 0; j < schedulerVOEditoption.getSchedulerVOList().size(); j++) {
				if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("M")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()), "", "", "", "" };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("W")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							"",
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()), "", "" };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("F")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							"",
							"",
							"",
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()) };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("MW")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()), "", "" };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("WF")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							"",
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()) };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("MWF")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()) };

					tableModel.addRow(rows);
				}

			}
                }
                
                            /*
                for (int j = 0; j < schedulerVOEditoption.getSchedulerVOList().size(); j++) {
			
				/*if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("MW")
						|| schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("WF")
						|| schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("M")
						|| schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("W")
						|| schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("MWF")) {
					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 104")) {

						// System.out.println(getRowByValue(tableModel,
						// schedulerVOEditoption.getSchedulerVOList().get(j).getTime()));

						Object rows[] = {
								schedulerVO.getSchedulerVOList().get(j).getTime(),
								generateHTML(schedulerVO.getSchedulerVOList().get(j).getInstructor(), schedulerVO
										.getSchedulerVOList().get(j).getCourse()
										+ "-" + schedulerVO.getSchedulerVOList().get(j).getSection(), schedulerVO
										.getSchedulerVOList().get(j).getDays()), "", "", "", "", "" };

						tableModel.addRow(rows);

					}

					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 105")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "", "", "", "" };

						tableModel.addRow(rows);
					}

					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 108")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "", "", "" };

						tableModel.addRow(rows);
					}
					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 139A")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "", "" };

						tableModel.addRow(rows);
					}

					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("OU 213E")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "" };

						tableModel.addRow(rows);
					}
					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("OU 213D")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								"",
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()) };

						tableModel.addRow(rows);
					}
				}

			} else if (view.equals(columnNamesCRviewTR)) {

				if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("TR")
						|| schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("T")
						|| schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("R")) {

					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 104")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "", "", "", "", "" };

						tableModel.addRow(rows);
					}

					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 105")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "", "", "", "" };

						tableModel.addRow(rows);
					}

					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 108")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "", "", "" };

						tableModel.addRow(rows);
					}
					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("STV 139A")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "", "" };

						tableModel.addRow(rows);
					}

					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("OU 213E")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()), "" };

						tableModel.addRow(rows);
					}
					if (schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals("OU 213D")) {
						Object rows[] = {
								schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
								"",
								"",
								"",
								"",
								"",
								generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getCourse() + "-"
												+ schedulerVOEditoption.getSchedulerVOList().get(j).getSection(),
										schedulerVOEditoption.getSchedulerVOList().get(j).getDays()) };

						tableModel.addRow(rows);
					}
				}

			} else if (view.equals(columnNamesCRviewOthers)) {

				if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("M")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()), "", "", "", "" };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("W")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							"",
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()), "", "" };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("F")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							"",
							"",
							"",
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()) };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("MW")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()), "", "" };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("WF")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							"",
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()) };

					tableModel.addRow(rows);
				} else if (schedulerVOEditoption.getSchedulerVOList().get(j).getDays().equals("MWF")
						&& schedulerVOEditoption.getSchedulerVOList().get(j).getClassroom().equals(tabName)) {
					Object rows[] = {
							schedulerVOEditoption.getSchedulerVOList().get(j).getTime(),
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()),
							"",
							generateHTML(schedulerVOEditoption.getSchedulerVOList().get(j).getInstructor(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getCourse()
									+ "-" + schedulerVOEditoption.getSchedulerVOList().get(j).getSection(), schedulerVOEditoption
									.getSchedulerVOList().get(j).getDays()) };

					tableModel.addRow(rows);
				}

			}

		}*/

		tableModel.fireTableDataChanged();
		SwingUtilities.updateComponentTreeUI(frame);
                updateRowHeights(table);
		return table;
	}

    private void setClassroomView(String days, String[] columns) {
        ArrayList<LinkedList<SchedulerVO>> viewList=schedulerVOEditoption.getClassroomList(days);
        for(int i=0; i< viewList.size(); i++)
        {
            LinkedList<SchedulerVO> currentRow=viewList.get(i);
            String rows[]=getClassRoomObject(currentRow, columns);
            Boolean inRooms=false;
            for(int j=1; j<rows.length; j++)
            {
                
                if(!rows[j].equals(""))
                {
                    inRooms=true;
                    rows[j]="<html>"+rows[j]+"<html>";
                }
            }
            if(inRooms)
            {
                tableModel.addRow(rows);
            }
        }
    }

        private String[] getClassRoomObject(LinkedList<SchedulerVO> sched, String[] columns)
        {
            String[] objStr=new String[columns.length];
             if(sched.size()>0)
             {
                 objStr[0]=sched.get(0).getTime();
                 
             }
             for(int i=1; i<objStr.length; i++)
                 {
                     objStr[i]="";
                 }
            SchedulerVO current=null;
            ListIterator<SchedulerVO> it = sched.listIterator();
            while (it.hasNext())
            {
                current=it.next();
                int counter=1;
                while(counter<objStr.length && !current.getClassroom().equalsIgnoreCase(columns[counter]))
                {
                    counter++;
                    
                }
             //   System.out.println("Class"+current.getClassroom());
                if(counter<objStr.length && current.getClassroom().equalsIgnoreCase(columns[counter]))
                {
                    /*
                    if(objStr[counter].contains("</html>"))
                    {
                        System.out.println("Detecting Html");
                        objStr[counter].replace("</html>", "<br>");
                        System.out.println(objStr);
                    }*/
                    objStr[counter]+=generateHTML1( current.getInstructor(), current.getCourse()
                    									+ "-" + current.getSection(), current.getDays());
                    
                  
                  }
            }
            
            return objStr;
        }
        
        
	public void hideEditOptionsPanel() {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pContent, BorderLayout.NORTH);
		frame.getContentPane().add(pButtons, BorderLayout.SOUTH);
		pDataTable.revalidate();
		pDataTable.repaint();
		frame.getContentPane().add(pModifySetupButtons).setVisible(false);
		frame.getContentPane().add(pDataTable).setVisible(true);
		frame.getContentPane().remove(pEditOptions);
	}

	/**
	 * This method returns a HTML formatted string used in Classroom view
	 * 
	 * @param line1
	 * @param line2
	 * @param line3
	 * @return
	 */
	public String generateHTML(String line1, String line2, String line3) {
		String htmlString = "";

		htmlString = "<html>" + line1 + "<br>" + line2 + "<br>" + line3 + "</html>";

		return htmlString;
	}
        
        
        /**
	 * This method returns a HTML formatted string used in Classroom view
	 * 
	 * @param line1
	 * @param line2
	 * @param line3
	 * @return
	 */
	public String generateHTML1(String line1, String line2, String line3) {
		String htmlString = "";

		htmlString =  "<br>"+line1 + "<br>" + line2 + "<br>" + line3+"<br>";

		return htmlString;
	}
	/*
	 * public String [][] getTableData (JTable table) { DefaultTableModel dtm =
	 * (DefaultTableModel) table.getModel(); int nRow = dtm.getRowCount(), nCol
	 * = dtm.getColumnCount(); String [][] tableData = new String[nRow][nCol];
	 * for (int i = 0 ; i < nRow ; i++) for (int j = 0 ; j < nCol ; j++)
	 * tableData[i][j] = dtm.getValueAt(i,j).toString(); return tableData; }
	 */

        private void updateRowHeights(JTable jTable)
    {
        try
        {
            for (int row = 0; row < jTable.getRowCount(); row++)
            {
                int rowHeight = jTable.getRowHeight();

                for (int column = 0; column < jTable.getColumnCount(); column++)
                {
                    Component comp = jTable.prepareRenderer(table.getCellRenderer(row, column), row, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }

                jTable.setRowHeight(row, rowHeight);
            }
            
        }
        catch(ClassCastException e) {}
}
	
}
