package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchFieldButton;
private JLabel searchErrorLabel;
private JTable designationTable;
private JScrollPane scrollPane;
private DesignationModel designationModel; 
private Container container;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,DELETE,EDIT,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon cancelIcon;
private ImageIcon logoIcon;
public int x; // for experiment
public DesignationUI()
{
initComponents(); //for object creation
setAppearance(); // to set appearance of objects
setListeners(); // to add listeners to objects
setViewMode();
designationPanel.setViewMode();
}

public void initComponents()
{
logoIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/logo.jpg"));
setIconImage(logoIcon.getImage());
designationModel = new DesignationModel();
titleLabel = new JLabel("Designations");
searchLabel = new JLabel("Search");
searchTextField = new JTextField();
cancelIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/cancel.jpg"));
clearSearchFieldButton = new JButton(cancelIcon);
searchErrorLabel = new JLabel("");
designationTable  = new JTable(designationModel);
scrollPane = new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container = getContentPane();
designationPanel = new DesignationPanel();
x = 0; //for experiment
}

private void setAppearance()
{
Font titleFont = new Font("Verdana",Font.BOLD,18);
Font captionFont = new Font("Verdana",Font.BOLD,17);
Font dataFont = new Font("Verdana",Font.PLAIN,12);
Font tableDataFont = new Font("Verdana",Font.PLAIN,15);
Font columnHeaderFont = new Font("Verdana",Font.BOLD,16);
Font searchErrorFont = new Font("Verdana",Font.BOLD,10);
//assigning fonts
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red); // colou of lable
designationTable.setFont(tableDataFont);
designationTable.setRowHeight(25);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(70);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(380);
JTableHeader header = designationTable.getTableHeader();
header.setFont(columnHeaderFont);
designationTable.setRowSelectionAllowed(true); //allow to selected a row or more
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
 //setting layout
container.setLayout(null);
int lm,tm; // left-margin and top-margin
lm = 0;
tm = 0;
titleLabel.setBounds(lm+180,tm+10,200,40); // (left-margin,top-margin,width,height) 
searchLabel.setBounds(lm+10+8,tm+10+40+20,100,35);
searchTextField.setBounds(lm+10+100+5,tm+10+40+20,320,30);
clearSearchFieldButton.setBounds(lm+10+100+5+320,tm+10+40+20,30,29);
scrollPane.setBounds(lm+10,tm+10+40+10+34+20,466,300);
designationPanel.setBounds(lm+10,tm+10+40+10+34+10+300+25,466,153);
searchErrorLabel.setBounds(lm+10+200+165,tm+10+47,100,15);
// adding all
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchFieldButton);
container.add(scrollPane);
container.add(designationPanel);
container.add(searchErrorLabel);

int w=500,h=633;
setSize(w,h);
Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
}

public void setListeners()
{
searchTextField.getDocument().addDocumentListener(this);

clearSearchFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent av)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);

addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent we)
{
x=1;
setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
}
});

}

public void searchDesignation()
{
searchErrorLabel.setText("");
int index;
String title = searchTextField.getText().trim();
if(title.length() == 0) return;
try
{
index = designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
designationTable.setRowSelectionInterval(index,index); // to select the interval of row (i.e. from x to y)
Rectangle rectangle = designationTable.getCellRect(index,0,true);
designationTable.scrollRectToVisible(rectangle);
}
 // from DesignationListener interface
public void changedUpdate(DocumentEvent de)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent de)
{
searchDesignation();
}

 // from SelectionListener interface
public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex = designationTable.getSelectedRow();
try
{
DesignationInterface designation = designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}

private void setViewMode()
{
this.mode = MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}

private void setAddMode()
{
this.mode = MODE.ADD;
searchTextField.setEnabled(false);
clearSearchFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setEditMode()
{
this.mode = MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setDeleteMode()
{
this.mode = MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setExportToPDFMode()
{
this.mode = MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

// inner class DesignationPanel
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
private ImageIcon addIcon;
private ImageIcon deleteIcon;
private ImageIcon saveIcon;
private ImageIcon cancelIcon;
private ImageIcon updateIcon;
private ImageIcon editIcon;
private ImageIcon exportToPDFIcon;

DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(140,140,140)));
initComponent();
setAppearance();
addListeners();
this.designation = null;
}

public void setDesignation(DesignationInterface designation)
{
this.designation = designation;
this.titleLabel.setText(designation.getTitle());
}

public void clearDesignation()
{
this.designation = null;
titleLabel.setText("");
}

private void initComponent()
{
//icons---------
addIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/add.jpg"));
deleteIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/delete.jpg"));
saveIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/sava.jpg"));
cancelIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/cancel.jpg"));
editIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/edit.png"));
exportToPDFIcon = new ImageIcon(DesignationUI.this.getClass().getResource("/icons/exportToPDF.jpg"));
//buttons and lable
titleCaptionLabel = new JLabel("Designation");
titleLabel = new JLabel("");
titleTextField = new JTextField();
clearTitleTextFieldButton = new JButton(cancelIcon);
buttonsPanel = new JPanel();
addButton = new JButton(addIcon);
editButton = new JButton(editIcon);
cancelButton = new JButton(cancelIcon);
deleteButton = new JButton(deleteIcon);
exportToPDFButton = new JButton(exportToPDFIcon);
}

private void setAppearance()
{
Font captionFont = new Font("Verdana",Font.BOLD,16);
Font dataFont = new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm=0,tm=0;
//466
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+10+110+10+20,tm+20,260,30);
titleTextField.setBounds(lm+10+110+10+20,tm+20,260,30);
clearTitleTextFieldButton.setBounds(lm+10+110+10+260+10+20,tm+20,29,29);

buttonsPanel.setBounds(33,tm+20+30+15,400,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(140,140,140)));
addButton.setBounds(10+15,12,50,50);
editButton.setBounds(10+15+50+25,12,50,50);
cancelButton.setBounds(10+15+50+25+50+25,12,50,50);
deleteButton.setBounds(10+15+50+25+50+25+50+25,12,50,50);
exportToPDFButton.setBounds(10+15+50+25+50+25+50+25+50+25,12,50,50);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
add(clearTitleTextFieldButton);
add(buttonsPanel);
}

private void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode == MODE.VIEW)
{
setAddMode();
}
else
{
// logic to add designation and save it
if(addDesignation())
{
setViewMode(); // help to switch to view mode after adding designation
}
}
}
});

this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode == MODE.VIEW)
{
setEditMode();
}
else
{
if(updateDesignation())
{
setViewMode(); // help to switch to view mode after editing designation
}
}
}
});

this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode(); 
setViewMode(); // help to switch to view mode after deleting designation
}
});

this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});

this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jfc = new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption = jfc.showSaveDialog(DesignationUI.this);
if(selectedOption == JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile = jfc.getSelectedFile();
String pdfFile = selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile += "pdf";
else if(pdfFile.endsWith(".") == false) pdfFile += ".pdf";
File file = new File(pdfFile);
File parent = new File(file.getParent());
if(parent.exists() == false || parent.isDirectory() == false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path : " + file.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to : " +file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
}
}catch(Exception e)
{
System.out.println(e);
}
}//if ends

}
});

}// inner addListener

private boolean addDesignation()
{
this.titleTextField.requestFocus();
String title  = this.titleTextField.getText().trim();
if(title.length()<=0)
{
JOptionPane.showMessageDialog(this,"Enter designation to add");
return false;
}
DesignationInterface d = new Designation();
d.setTitle(title);
int index= 0;
try
{
designationModel.add(d);
try
{
index = designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//nothing
}
designationTable.setRowSelectionInterval(index,index); // to select the interval of row (i.e. from x to y)
Rectangle rectangle = designationTable.getCellRect(index,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
return false;
}
return true;
}//addDesignation ends

private boolean updateDesignation()
{
this.titleTextField.requestFocus();
String title  = this.titleTextField.getText().trim();
if(title.length()<=0)
{
JOptionPane.showMessageDialog(this,"Select designation to update");
return false;
}
DesignationInterface d = new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int index= 0;
try
{
index = designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//nothing
}
designationTable.setRowSelectionInterval(index,index); // to select the interval of row (i.e. from x to y)
Rectangle rectangle = designationTable.getCellRect(index,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
return false;
}
return true;
}// updateDesignation ends

private void deleteDesignation()
{
try
{
String d1 = designation.getTitle();
int optionSelected = JOptionPane.showConfirmDialog(this,"Delete " + d1 + " ?","Confirmation Box",JOptionPane.YES_NO_OPTION);
if(optionSelected == JOptionPane.NO_OPTION) return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,"Designation : " + d1 + " deleted");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
}
void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
this.titleLabel.setVisible(true);
addButton.setEnabled(true);
cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
else
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
}

void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.clearTitleTextFieldButton.setVisible(true);
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
addButton.setIcon(saveIcon);
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

void setEditMode()
{
if(designationTable.getSelectedRow()<0||designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.clearTitleTextFieldButton.setVisible(true);
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setIcon(saveIcon);
}

void setDeleteMode()
{
if(designationTable.getSelectedRow()<0||designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
addButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setEnabled(false);
deleteDesignation();
}

void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setEnabled(false);
}
}//inner ends

}//god ends