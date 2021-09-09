import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;

//------------------------------------------MODEL----------------------------------------------
class DesigationModel extends AbstractTableModel
{
private Set<DesignationDTOInterface> designations;
private DesignationDAOInterface designationDAO;
private String title[];
DesigationModel()
{
populateDataStructure();
}
public void populateDataStructure()
{
title = new String[2];
title[0] = "Code";
title[1] = "Designation";
try
{
designationDAO = new DesignationDAO();
designations = designationDAO.getall();
}catch(DAOException e)
{
System.out.println(e.getMessage());
}
}

public int getRowCount()
{
try
{
return designationDAO.getCount();
}catch(DAOException e)
{
System.out.println(e.getMessage());
}
return 0;
}

public int getColumnCount()
{
return title.length;
}

public String getColumnName(int columnIndex)
{
return title[columnIndex];
}

public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}

public Class getColumnClass(int columnIndex)
{
Class c = null;
try
{
if(columnIndex==0)
{
c = Class.forName("java.lang.Integer");
}
if(columnIndex==1)
{
c = Class.forName("java.lang.String");
}
}catch(Exception e)
{
System.out.println(e.getMessage());
}
return c;
}

public Object getValueAt(int rowIndex,int columnIndex)
{
int x=0;
for(DesignationDTOInterface d:designations)
{
if(x==rowIndex)
{
if(columnIndex == 0)
{
return d.getCode();
}
if(columnIndex == 1)
{
return d.getTitle();
}
}
x++;
}
return 0;
}

}

//------------------------------------------VIEW----------------------------------------------
class UIDL extends JFrame
{
private JTable table;
private JScrollPane sbp;
private Container container;
private DesigationModel desigationModel;
UIDL()
{
super("All designation");
desigationModel = new DesigationModel();
table = new JTable(desigationModel);
table.getTableHeader().setReorderingAllowed(false);
table.getTableHeader().setResizingAllowed(false);
table.setRowSelectionAllowed(false);
Font font = new Font("Times New Roman",Font.PLAIN,24);
table.setFont(font);
table.setRowHeight(30);
sbp = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container = getContentPane();
container.setLayout(new BorderLayout());
container.add(sbp);
Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); // to get screen size
int w = 600;
int h = 600;
setSize(w,h);
int x = (d.width/2)-(w/2);
int y = (d.height/2)-(h/2);
setLocation(x,y);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);

}
}

class UIDLpsp
{
public static void main(String[] aa)
{
UIDL a = new UIDL();
}
}
