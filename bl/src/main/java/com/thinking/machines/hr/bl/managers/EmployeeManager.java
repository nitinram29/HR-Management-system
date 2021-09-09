package com.thinking.machines.hr.bl.managers;
import  com.thinking.machines.hr.bl.exceptions.*;
import  com.thinking.machines.hr.bl.interfaces.pojo.*;
import  com.thinking.machines.hr.bl.interfaces.managers.*;
import  com.thinking.machines.hr.bl.pojo.*;
import  com.thinking.machines.hr.dl.exceptions.*;
import  com.thinking.machines.hr.dl.interfaces.dto.*;
import  com.thinking.machines.hr.dl.interfaces.dao.*;
import  com.thinking.machines.hr.dl.dto.*;
import  com.thinking.machines.hr.dl.dao.*;
import  com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;

public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;

private Set<EmployeeInterface> employeesSet;
private static EmployeeManager employeeManager = null;

private EmployeeManager()  throws BLException
{
populateDataStructure();
}

private void populateDataStructure() throws BLException
{
this.employeeIdWiseEmployeesMap = new HashMap<>();
this.panNumberWiseEmployeesMap = new HashMap<>();
this.aadharCardNumberWiseEmployeesMap = new HashMap<>();
this.designationCodeWiseEmployeesMap = new HashMap<>();
this.employeesSet = new TreeSet<>();

try
{
Set<EmployeeDTOInterface> dlEmployees;

dlEmployees = new EmployeeDAO().getall();
EmployeeInterface employee;
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee = new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId().trim());
employee.setName(dlEmployee.getName());

designation = designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());

if(dlEmployee.getGender() == 'M') employee.setGender(GENDER.MALE);
else employee.setGender(GENDER.FEMALE);

employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase().trim(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets = this.designationCodeWiseEmployeesMap.get(dlEmployee.getDesignationCode());
if(ets==null)
{
ets = new TreeSet<>();
ets.add(employee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(employee);
}


}
}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//-------------------------------------------------------------------------------------------------------------
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager == null) employeeManager = new EmployeeManager();
return employeeManager;
}
//-------------------------------------------------------------------------------------------------------------
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException = new BLException();
String employeeId = employee.getEmployeeId();
String name = employee.getName();
DesignationInterface designation = employee.getDesignation();
int designationCode = 0;
Date dateOfBirth  = employee.getDateOfBirth();
char gender = employee.getGender();
boolean isIndian =  employee.getIsIndian();
BigDecimal basicSalary = employee.getBasicSalary();
String panNumber = employee.getPANNumber();
String aadharCardNumber = employee.getAadharCardNumber();
if(employeeId!=null)
{
employeeId = employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","Employee Id should be nil");
}
}
if(name == null) blException.addException("name","Name required");
else name = name.trim(); 
if(name!=null && name.length()==0) blException.addException("name","Name required");
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode = designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","invalid designation");
}

if(designationManager.designationTitleExists(designation.getTitle())==false)
{
blException.addException("designation","invalid designation");
}

}
if(dateOfBirth == null)
{
blException.addException("dateOfBirth","Date of brith required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary cannot be (-)ve");
}
if(panNumber == null)
{
blException.addException("panNumber","Pan Number required");
}
else
{
panNumber = panNumber.trim();
if(panNumber.length() == 0) blException.addException("panNumber","Pan Number required");
}
if(aadharCardNumber == null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) blException.addException("aadharCardNumber","Aadhar card number required");
}
if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber))
{
blException.addException("panNumber","Pan Number (" + panNumber + ") exists");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber))
{
blException.addException("aadharCardNumber","Aadhar card number (" + aadharCardNumber + ") exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee = new EmployeeDTO();
dlEmployee.setName(employee.getName());
dlEmployee.setDesignationCode(employee.getDesignation().getCode());
dlEmployee.setDateOfBirth(employee.getDateOfBirth());
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(employee.getIsIndian());
dlEmployee.setBasicSalary(employee.getBasicSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
dlEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());

EmployeeInterface dsEmployee = new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(employee.getDesignation().getCode()));//============================//

dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
//************************************************************change***********************
Set<EmployeeInterface> ets = this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets = new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(dsEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(dsEmployee);
}
//************************************************************change***********************
this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(dsEmployee.getPANNumber().toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(dsEmployee.getAadharCardNumber().toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//-------------------------------------------------------------------------------------------------------------
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException = new BLException();
String employeeId = employee.getEmployeeId();
String name = employee.getName();
DesignationInterface designation = employee.getDesignation();
int designationCode = 0;
Date dateOfBirth  = employee.getDateOfBirth();
char gender = employee.getGender();
boolean isIndian =  employee.getIsIndian();
BigDecimal basicSalary = employee.getBasicSalary();
String panNumber = employee.getPANNumber();
String aadharCardNumber = employee.getAadharCardNumber();
if(employeeId==null)
{
blException.addException("employeeId","Employee Id required..........");
}
else
{
employeeId = employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","Employee Id required");
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Employee Id required..1");
throw blException;
}
}
}
if(name == null) blException.addException("name","Name required");
else name = name.trim(); 
if(name!=null && name.length()==0) blException.addException("name","Name required");
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode = designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","invalid designation");
}
}
if(dateOfBirth == null)
{
blException.addException("dateOfBirth","Date of brith required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary cannot be (-)ve");
}
//=---
if(panNumber == null)
{
blException.addException("panNumber","Pan Number required");
}
else
{
panNumber = panNumber.trim();
if(panNumber.length() == 0) blException.addException("panNumber","Pan Number required");
}
if(aadharCardNumber == null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) blException.addException("aadharCardNumber","Aadhar card number required");
}


if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface e = panNumberWiseEmployeesMap.get(panNumber);
if(e!=null && e.getEmployeeId().equalsIgnoreCase(employeeId))
{
blException.addException("panNumber","Pan Number (" + panNumber + ") exists with other employee");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface e = aadharCardNumberWiseEmployeesMap.get(aadharCardNumber);
if(e!=null && e.getEmployeeId().equalsIgnoreCase(employeeId))
{
blException.addException("aadharCardNumber","Aadhar Card Number (" + aadharCardNumber + ") exists with other employee");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee;

dsEmployee = employeeIdWiseEmployeesMap.get(employeeId);
String oldPANNumber = dsEmployee.getPANNumber();
String oldAadharCardNumber = dsEmployee.getAadharCardNumber();
int oldDesignationCode = dsEmployee.getDesignation().getCode();
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee = new EmployeeDTO();

dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(employee.getName());
dlEmployee.setDesignationCode(employee.getDesignation().getCode());
dlEmployee.setDateOfBirth(employee.getDateOfBirth());
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(employee.getIsIndian());
dlEmployee.setBasicSalary(employee.getBasicSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
dlEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employeeDAO.update(dlEmployee);

dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(employee.getDesignation().getCode()));//============================//
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());

this.employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
this.panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
this.employeesSet.remove(dsEmployee); 

this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(dsEmployee.getPANNumber().toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(dsEmployee.getAadharCardNumber().toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
//************************************************************change***********************
Set<EmployeeInterface> ets = this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(dsEmployee);

if(ets==null)
{
ets = new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(dsEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(dsEmployee);
}
//************************************************************change***********************

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//-------------------------------------------------------------------------------------------------------------
public void removeEmployee(String employeeId) throws BLException
{
if(employeeId==null)
{
BLException blException = new BLException();
blException.addException("employeeId","Employee Id required..........");
throw blException;
}
else
{
employeeId = employeeId.trim();
if(employeeId.length()==0)
{
BLException blException = new BLException();
blException.addException("employeeId.toUpperCase()","Employee Id required......1");
throw blException;
}
//while(employeeId.length()<11) employeeId+=" ";
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
BLException blException = new BLException();
blException.addException("employeeId","Employee Id required...2 : " + employeeId);
throw blException;
}

}

try
{
EmployeeInterface dsEmployee;
dsEmployee = employeeIdWiseEmployeesMap.get(employeeId);

EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
employeeDAO.delete(employeeId);

this.employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
this.panNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
this.employeesSet.remove(dsEmployee); 
//*************************************************change*****************************************************
Set<EmployeeInterface> ets = this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
//*************************************************change*****************************************************

}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//------------------------------------------------------------------------------------------------------------
public EmployeeInterface getByEmployeeId(String employeeId) throws BLException
{
//employeeId = employeeId.trim();
EmployeeInterface dsEmployee = this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(dsEmployee == null)
{
BLException blException = new BLException();
blException.addException("employeeId","Employee : "+ employeeId +" not exists....");
throw blException;
}

EmployeeInterface employee = new Employee();
employee.setEmployeeId(employeeId);
employee.setName(dsEmployee.getName());
DesignationInterface designation = new Designation();
designation.setCode(dsEmployee.getDesignation().getCode());
designation.setTitle(dsEmployee.getDesignation().getTitle());
employee.setDesignation(designation);//============================//
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return dsEmployee;
}//------------------------------------------------------------------------------------------------------------
public EmployeeInterface getByPANNumber(String panNumber) throws BLException
{
panNumber = panNumber.trim();
EmployeeInterface employee = this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(employee == null)
{
BLException blException = new BLException();
blException.addException("panNumber","PAN Number : "+ panNumber +" not exists....");
throw blException;
}
EmployeeInterface dsEmployee = new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());

DesignationInterface designation = new Designation();
designation.setCode(dsEmployee.getDesignation().getCode());
designation.setTitle(dsEmployee.getDesignation().getTitle());
dsEmployee.setDesignation(designation);//============================//
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dsEmployee;
}
//------------------------------------------------------------------------------------------------------------
public EmployeeInterface getByAadharNumber(String aadharNumber) throws BLException
{
aadharNumber = aadharNumber.trim();
EmployeeInterface employee = this.aadharCardNumberWiseEmployeesMap.get(aadharNumber.toUpperCase());
if(employee == null)
{
BLException blException = new BLException();
blException.addException("employeeId","Employee : "+ aadharNumber +" not exists....");
throw blException;
}
String name = employee.getName();
EmployeeInterface dsEmployee = new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(name);
DesignationInterface designation = new Designation();
designation.setCode(dsEmployee.getDesignation().getCode());
designation.setTitle(dsEmployee.getDesignation().getTitle());
dsEmployee.setDesignation(designation);//============================//
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dsEmployee;
}
//------------------------------------------------------------------------------------------------------------
public int getEmployeeCount()
{
return employeesSet.size();
}
//------------------------------------------------------------------------------------------------------------
public boolean employeeIdExists(String employeeId)
{
return employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}
//------------------------------------------------------------------------------------------------------------
public boolean employeePANNumberExists(String panNumber)
{
return panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
//------------------------------------------------------------------------------------------------------------
public boolean EmployeeAadharCardNumberExists(String aadharNumber)
{
return aadharCardNumberWiseEmployeesMap.containsKey(aadharNumber.toUpperCase());
}
//-----------------------------------------------------------------------------------------------------------
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees = new TreeSet<>();
EmployeeInterface employee;
DesignationInterface designation; 
for(EmployeeInterface dsEmployee: employeesSet)
{
employee = new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
designation = new Designation();
designation.setCode(dsEmployee.getDesignation().getCode());
designation.setTitle(dsEmployee.getDesignation().getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
//------------------------------------------------------------------------------------------------------------
public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException
{
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false)
{
BLException blException = new BLException();
blException.setGenericException("Invalid designation code : "+designationCode);
throw blException;
}
Set<EmployeeInterface> employees = new TreeSet<>();
Set<EmployeeInterface> ets =  this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
return employees;
}

EmployeeInterface employee;
DesignationInterface designation; 
for(EmployeeInterface dsEmployee: ets)
{
employee = new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
designation = new Designation();
designation.setCode(dsEmployee.getDesignation().getCode());
designation.setTitle(dsEmployee.getDesignation().getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}

//------------------------------------------------------------------------------------------------------------
public int getCountByDesignationCode(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.get(designationCode).size();
}
//------------------------------------------------------------------------------------------------------------
public boolean isDesignationAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
//------------------------------------------------------------------------------------------------------------
}