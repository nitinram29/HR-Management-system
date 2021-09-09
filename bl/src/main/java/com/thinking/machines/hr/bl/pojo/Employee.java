package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;

public class Employee implements EmployeeInterface
{
private String employeeId;
private String name;
private DesignationInterface designation;
private Date dateOfBirth;
private char gender;
private boolean isIndian;
private BigDecimal basicSalary;
private String panNumber;
private String aadharCardNumber;

public Employee()
{
this.employeeId = "";
this.name = "";
this.designation = null;
this.dateOfBirth = null;
this.gender = ' ';
this.isIndian = false;
this.basicSalary = null;
this.panNumber = "";
this.aadharCardNumber = "";

}

public void setEmployeeId(String employeeId)
{
this.employeeId = employeeId;
}
public String getEmployeeId()
{
return this.employeeId;
}

public void setName(String name)
{
this.name =name;
}
public String getName()
{
return this.name;
}

public void setDesignation(DesignationInterface designation)
{
this.designation = designation;
}
public DesignationInterface getDesignation()
{
return this.designation;
}

public void setDateOfBirth(Date dateOfBirth)
{
this.dateOfBirth = dateOfBirth;
}
public Date getDateOfBirth()
{
return this.dateOfBirth;
}

public void setGender(GENDER gender)
{
if(gender == GENDER.MALE)
{
this.gender = 'M';
}else
{
this.gender = 'F';
}
}
public char getGender()
{
return this.gender;
}

public void setIsIndian(boolean isIndian)
{
this.isIndian = isIndian;
}
public boolean getIsIndian()
{
return this.isIndian;
}

public void setBasicSalary(BigDecimal basicSalary)
{
this.basicSalary = basicSalary;
}
public BigDecimal getBasicSalary()
{
return this.basicSalary;
}

public void setPANNumber(String panNumber)
{
this.panNumber = panNumber;
}
public String getPANNumber()
{
return this.panNumber;
}

public void setAadharCardNumber(String aadharCardNumber)
{
this.aadharCardNumber = aadharCardNumber;
}

public String getAadharCardNumber()
{
return this.aadharCardNumber;
}

public boolean equals(Object other)
{
if(!(other instanceof EmployeeInterface)) return false;
EmployeeInterface employee = (Employee)other;
return this.employeeId.equalsIgnoreCase(employee.getEmployeeId());
}

public int compareTo(EmployeeInterface employee)
{
return this.employeeId.compareToIgnoreCase(employee.getEmployeeId());
}

public int hashCode()
{
return this.employeeId.toUpperCase().hashCode();
}
}
