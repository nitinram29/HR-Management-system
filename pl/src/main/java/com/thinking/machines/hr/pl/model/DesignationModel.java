package com.thinking.machines.hr.pl.model ;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*; // for layout
import com.itextpdf.layout.element.*; // for elements
import com.itextpdf.io.font.constants.*; // for font
import com.itextpdf.kernel.font.*; // to associat font with kernel
import com.itextpdf.io.image.*;
import com.itextpdf.layout.property.*; 
import com.itextpdf.layout.borders .*; 

public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations; // worng approch will change in future 
private DesignationManagerInterface designationManager;
private String[] columnTitle;

public DesignationModel()
{
this.populateDataStructure();
}
public void populateDataStructure()
{
this.columnTitle = new String[2];
this.columnTitle[0] = "S.No.";
this.columnTitle[1] = "Designation";

try
{
designationManager = DesignationManager.getDesignationManager();
}catch(BLException blE)
{
//???????????????????????????????????????????? yha kya kre iski kahani baad m dekhenge
}
Set<DesignationInterface> blDesignation = designationManager.getDesignations();
this.designations = new LinkedList<>(); 
for(DesignationInterface designation:blDesignation)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
 }
});
}
public int getRowCount()
{
return designations.size();
}

public int getColumnCount()
{
return this.columnTitle.length;
}

public String getColumnName(int columnIndex)
{
return this.columnTitle[columnIndex];
}//column name

public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class; // special treatment (normal is return Class.forName("java.lang.Integer"))
else return String.class;
}// column class

public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}// cell editable

public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex == 0) return rowIndex+1;
else return this.designations.get(rowIndex).getTitle();
}//get Value At

// application specific methods--------------------------------------------------------------------------------
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator = this.designations.iterator();
DesignationInterface d;
int index = 0;
while(iterator.hasNext())
{
d = iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException = new BLException();
blException.setGenericException("Invalid designation : " + designation.getTitle());
throw blException;
}

public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface> iterator = this.designations.iterator();
DesignationInterface d;
int index = 0;
while(iterator.hasNext())
{
d = iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException blException = new BLException();
blException.setGenericException("Invalid title : " + title);
throw blException;
}

public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
designations.remove(indexOfDesignation(designation)); // ye chaega qki indexOfDesignation methods m equals method code k bases pr operate kregi
designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}


public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator = designations.iterator();
int index = 0;
while(iterator.hasNext())
{
if(iterator.next().getCode() == code)
{
break;
}
index++;
}
if(index == designations.size()) // yha kbhi baat nhi aaegi qki is method ki 1st line hi exception dedegi agr code nhi hua to
{
BLException blException = new BLException();
blException.setGenericException("Invalid code : " + code);
throw blException;
}
designations.remove(index); // no need to sort,,,,qki kch delete hora h 
fireTableDataChanged();
}

public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index >= this.designations.size())
{
BLException blException = new BLException();
blException.setGenericException("invali index : " +index);
throw blException;
}
return this.designations.get(index);
}

//PDF
public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter = new PdfWriter(file);
PdfDocument pdfDocument = new PdfDocument(pdfWriter);
Document doc = new Document(pdfDocument);
Image logo = new Image(ImageDataFactory.create(this.getClass().getResource("/icons/icon.jpg")));
Paragraph logoPara = new Paragraph();
logoPara.add(logo);
Paragraph companyNameParagraph = new Paragraph();
companyNameParagraph.add("xyz company");
PdfFont companyNameFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNameParagraph.setFont(companyNameFont); 
companyNameParagraph.setFontSize(18);
Paragraph reportTitlePara = new Paragraph("List of Designation");
PdfFont reportTitleFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);
PdfFont columnTitleFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont pageNumberFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
Paragraph columnTitle1 = new Paragraph("S.no.");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(12);
Paragraph columnTitle2 = new Paragraph("Designation");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(12);
Paragraph pageNumberPara;
Paragraph dataPara;
float[] topTableColumnWidths = {1,5};
float[] dataTableColumnWidths = {1,5};
Table topTable = null;
Table dataTable = null;
Table pageNumberTable;
int sno,x,pageSize;
pageSize = 25; 
sno = 0;
x=0;
boolean newPage = true;
Cell cell;
int pageNumber = 0;
int numberOfPages = this.designations.size()/pageSize;
if((this.designations.size()%pageSize)!=0) numberOfPages++;
DesignationInterface designation;
while(x<this.designations.size())
{
if(newPage == true)
{
// create header
pageNumber++;
topTable = new Table(UnitValue.createPercentArray(topTableColumnWidths));
cell = new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell = new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNameParagraph);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
doc.add(topTable);
pageNumberPara = new Paragraph("Page : "+pageNumber+"/"+numberOfPages);
pageNumberPara.setFont(pageNumberFont);
pageNumberPara.setFontSize(8);
pageNumberTable = new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell = new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberPara);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
doc.add(pageNumberTable);
dataTable = new Table(UnitValue.createPercentArray(dataTableColumnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell = new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage = false;
}

designation = this.designations.get(x);
//add row to table;
sno++;
cell = new Cell();
dataPara = new Paragraph(String.valueOf(sno));
dataPara.setFont(dataFont);
dataPara.setFontSize(12);
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
cell = new Cell();
dataPara = new Paragraph(designation.getTitle());
dataPara.setFont(dataFont);
dataPara.setFontSize(12);
cell.add(dataPara);
dataTable.addCell(cell);
x++;
if(sno%pageSize == 0 || sno==this.designations.size())
{
// create footer
doc.add(dataTable);
doc.add(new Paragraph("Software by Nitin ram"));
if(x<this.designations.size())
{
//new page
doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage = true;
}
}
}// while ends
doc.close();
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}// exportToPDF

}//ends
