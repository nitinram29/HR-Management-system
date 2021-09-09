package com.thinking.machines.hr.pl;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.pl.ui.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

class Main1 extends JFrame implements WindowListener
{
private JButton designation;
private JButton employee;
private JLabel lable;
private JPanel buttonPanel;
private boolean flagBool;
private Container container;
private DesignationUI designationUI;
private int flagAlter;
Main1()
{
initComponents();
setAppearance();
setListeners();
}

public void initComponents()
{
designationUI = new DesignationUI();
designation = new JButton("Designation");
employee = new JButton("Employee");
lable = new JLabel("HR Application");
buttonPanel = new JPanel();
flagBool = true;
flagAlter = 0;
container = getContentPane();
}

public void setAppearance()
{
buttonPanel.setLayout(null);
buttonPanel.setBounds(100,200,100,100);
lable.setBounds(110,0,90,20);
designation.setBounds(40,5+20+5,110,30);
employee.setBounds(40+110+10,5+20+5,110,30);
container.add(lable);
buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(100,140,140)));

buttonPanel.add(designation);
buttonPanel.add(employee);
container.add(buttonPanel);

setSize(310,105);
setLocation(100,100);
setVisible(true);
}

public void setListeners()
{
designation.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(designationUI.x == 1)
	flagBool = true;

if(flagBool == true)
{
designationUI.setVisible(true); // isko DesignationUI k constructor m na deke hm uske andr 
                		// ki methods ki visibility control kr skte h 
Main1.this.flagBool = false;
}
}
});

employee.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
System.out.println("hello employee");
}
});
}

public void windowActivated(WindowEvent arg0) 
{  
//empty
}  

public void windowOpened(WindowEvent arg0) 
{  
//empty
}  

public void windowClosed(WindowEvent arg0)
{
//empty
}  

public void windowClosing(WindowEvent arg0)
{
Main1.this.flagBool = true;
dispose();  
}  

public void windowDeactivated(WindowEvent arg0)
{  
//empty
}  

public void windowDeiconified(WindowEvent arg0)
{  
//empty
}  

public void windowIconified(WindowEvent arg0)
{  
//empty
}  
}//Main1 ends

class Main
{
public static void main(String agrs[])
{
Main1 main = new Main1();
}
}
// employee ka pl bnana h