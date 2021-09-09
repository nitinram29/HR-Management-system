package com.thinking.machines.hr.bl.interfaces.managers;
import  com.thinking.machines.hr.bl.exceptions.*;
import  com.thinking.machines.hr.bl.interfaces.pojo.*;
import java.util.*;

public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee) throws BLException;
public void updateEmployee(EmployeeInterface employee) throws BLException;
public void removeEmployee(String employeeId) throws BLException;
public EmployeeInterface getByEmployeeId(String employeeId) throws BLException;
public EmployeeInterface getByPANNumber(String panNumber) throws BLException;
public EmployeeInterface getByAadharNumber(String aadharNumber) throws BLException;
public int getEmployeeCount();
public boolean employeeIdExists(String employeeId);
public boolean employeePANNumberExists(String panNumber);
public boolean EmployeeAadharCardNumberExists(String aadharNumber);
public Set<EmployeeInterface> getEmployees();
public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException;
public int getCountByDesignationCode(int designationCode) throws BLException;
public boolean isDesignationAlloted(int designationCode) throws BLException;
}