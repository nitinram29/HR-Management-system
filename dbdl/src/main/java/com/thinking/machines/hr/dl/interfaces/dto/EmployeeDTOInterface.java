package com.thinking.machines.hr.dl.interfaces.dto;
import com.thinking.machines.enums.*;
import java.math.*; // to use BigDecimal
import java.util.*;
public interface EmployeeDTOInterface extends Comparable<EmployeeDTOInterface>,java.io.Serializable
{

public void setEmployeeId(String employeeId);
public String getEmployeeId();

public void setName(String name);
public String getName();

public void setDesignationCode(int designationId);
public int getDesignationCode();

public void setDateOfBirth(Date dateOfBirth);
public Date getDateOfBirth();

public void setGender(GENDER gender);
public char getGender();

public void setIsIndian(boolean isIndian);
public boolean getIsIndian();

public void setBasicSalary(BigDecimal basicSalary);
public BigDecimal getBasicSalary();

public void setPANNumber(String PanNumber);
public String getPANNumber();

public void setAadharCardNumber(String AadherCardNumber);
public String getAadharCardNumber();
}
