package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
import java.sql.*;
public class EmployeeDAO implements EmployeeDAOInterface
{

private static String DATA_FILE = "employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO == null) throw new DAOException("Employee is null");
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");

int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid designation code : "+designationCode);
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : " + designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
/*
this also rigth but why established connection is a single method....
DesignationDAOInterface designationDAO = new DesignationDAO();
if(!(designationDAO.codeExists(employeeDTO.getDesignationCode())))
{
throw new DAOException("Designation Code : " + employeeDTO.getDesignationCode() + " not exists.");
}
*/

java.util.Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth == null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("date of birth is null");
}
char gender = employeeDTO.getGender();
if(gender == ' ')
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Gender not set to M/F");
}
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("basic salary is null");
}
if(basicSalary.signum() == -1)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("basic salary is (-)ve");
}
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Pan number is null");
}
panNumber = panNumber.trim();
if(panNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Length of panNumber is zero");
}
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("aadhar Card Number is null");
}
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Length of aadhar Card Number is zero");
}
try
{
Boolean panNumberExists,aadharCardNumberExists;
preparedStatement = connection.prepareStatement("select gender from employee where pan_number = ?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
panNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select gender from employee where aadhar_card_number = ?");
preparedStatement.setString(1,aadharCardNumber);
resultSet = preparedStatement.executeQuery();
aadharCardNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Pan number ("+panNumber+") and aadhar card number("+aadharCardNumber+") exists.");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Pan number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("aadhar Card Number("+aadharCardNumber+") exists.");
}
preparedStatement = connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
 //IMP
java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet = preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeID = resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A" + (generatedEmployeeID + 1000000));
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//Add_ends

//_____________________________________________________________________________________________________________
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO == null) throw new DAOException("Employee is null");
String employeeId = employeeDTO.getEmployeeId();
if(employeeId == null) throw new DAOException("Employee ID is null");
employeeId = employeeId.trim();
if(employeeId.length() == 0) throw new DAOException("Length of employee ID is zero");
int actualEmployeeId;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1)) - 1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee Id.");
}
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid designation code : "+designationCode);
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : " + designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
/*
this also rigth but why established connection is a single method....
DesignationDAOInterface designationDAO = new DesignationDAO();
if(!(designationDAO.codeExists(employeeDTO.getDesignationCode())))
{
throw new DAOException("Designation Code : " + employeeDTO.getDesignationCode() + " not exists.");
}
*/

java.util.Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth == null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("date of birth is null");
}
char gender = employeeDTO.getGender();
if(gender == ' ')
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Gender not set to M/F");
}
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("basic salary is null");
}
if(basicSalary.signum() == -1)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("basic salary is (-)ve");
}
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Pan number is null");
}
panNumber = panNumber.trim();
if(panNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Length of panNumber is zero");
}
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("aadhar Card Number is null");
}
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Length of aadhar Card Number is zero");
}
try
{
Boolean panNumberExists,aadharCardNumberExists;
preparedStatement = connection.prepareStatement("select gender from employee where pan_number = ? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
panNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select gender from employee where aadhar_card_number = ? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
aadharCardNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Pan number ("+panNumber+") and aadhar card number("+aadharCardNumber+") exists.");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("Pan number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
throw new DAOException("aadhar Card Number("+aadharCardNumber+") exists.");
}
preparedStatement = connection.prepareStatement("update employee set name=?,designation=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id = ?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
 //IMP
java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//update ends here
//_____________________________________________________________________________________________________________
public void delete(String employeeId) throws DAOException
{
if(employeeId == null) throw new DAOException("Employee ID is null");
employeeId = employeeId.trim();
if(employeeId.length() == 0) throw new DAOException("Length of employee ID is zero");
int actualEmployeeId;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1)) - 1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee Id.");
}
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;	
ResultSet resultSet;
try
{
preparedStatement = connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id." + employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//delete_ends
//_____________________________________________________________________________________________________________
public Set<EmployeeDTOInterface> getall() throws DAOException
{
Set<EmployeeDTOInterface> employees = new TreeSet<>();
try
{
Connection connection = DAOConnection.getConnection();
Statement statement = connection.createStatement();
ResultSet resultSet;
resultSet = statement.executeQuery("select * from employee");
EmployeeDTOInterface employee;
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String genderSql;
while(resultSet.next())
{
employee = new EmployeeDTO();
employee.setEmployeeId("A" + (1000000 + resultSet.getInt("employee_id")));
employee.setName(resultSet.getString("name").trim());
employee.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth = resultSet.getDate("date_of_birth");
utilDateOfBirth = new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employee.setDateOfBirth(sqlDateOfBirth);
genderSql = resultSet.getString("gender").trim();
if(genderSql.equalsIgnoreCase("M"))
{
employee.setGender(GENDER.MALE);
}
if(genderSql.equalsIgnoreCase("F"))
{
employee.setGender(GENDER.FEMALE);
}
employee.setIsIndian(resultSet.getBoolean("is_indian"));
employee.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employee.setPANNumber(resultSet.getString("pan_number").trim());
employee.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employee);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}//getAll_ends
//_____________________________________________________________________________________________________________
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface> employees = new TreeSet<>();
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code : " + designationCode );
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select * from employee where designation_code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
EmployeeDTOInterface employee;
String genderSql;
while(resultSet.next())
{
employee = new EmployeeDTO();
employee.setEmployeeId("A" + (1000000 + resultSet.getInt("employee_id")));
employee.setName(resultSet.getString("name").trim());
employee.setDesignationCode(resultSet.getInt("designation_code"));
employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
genderSql = resultSet.getString("gender").trim();
if(genderSql.equalsIgnoreCase("M"))
{
employee.setGender(GENDER.MALE);
}
if(genderSql.equalsIgnoreCase("F"))
{
employee.setGender(GENDER.FEMALE);
}
employee.setGender(GENDER.FEMALE);
employee.setIsIndian(resultSet.getBoolean("is_indian"));
employee.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employee.setPANNumber(resultSet.getString("pan_number").trim());
employee.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employee);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}//getByDesignationCode_ends
//-------------------------------------------------------------------------------------------------------------
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId.trim().length() == 0) throw new DAOException("length cant be 0");
int actualEmployeeId;
EmployeeDTOInterface employee = null;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1)) - 1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid Employee Id");
}
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from employee where employee_id = ?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id : " + employeeId);
}
employee = new EmployeeDTO();
String genderSql;
employee.setEmployeeId("A" + (1000000 + resultSet.getInt("employee_id")));
employee.setName(resultSet.getString("name").trim());
employee.setDesignationCode(resultSet.getInt("designation_code"));
employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
genderSql = resultSet.getString("gender").trim();
if(genderSql.equalsIgnoreCase("M"))
{
employee.setGender(GENDER.MALE);
}
if(genderSql.equalsIgnoreCase("F"))
{
employee.setGender(GENDER.FEMALE);
}
employee.setGender(GENDER.FEMALE);
employee.setIsIndian(resultSet.getBoolean("is_indian"));
employee.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employee.setPANNumber(resultSet.getString("pan_number").trim());
employee.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employee;
}//getByEmployeeId_ends
//________________________________________________________________________________________________________
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber.trim().length() == 0) throw new DAOException("length cant be 0");
EmployeeDTOInterface employee =null;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from employee where pan_number = ?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN Number: " + panNumber);
}
employee = new EmployeeDTO();
String genderSql;
employee.setEmployeeId("A" + (1000000 + resultSet.getInt("employee_id")));
employee.setName(resultSet.getString("name").trim());
employee.setDesignationCode(resultSet.getInt("designation_code"));
employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
genderSql = resultSet.getString("gender").trim();
if(genderSql.equalsIgnoreCase("M"))
{
employee.setGender(GENDER.MALE);
}
if(genderSql.equalsIgnoreCase("F"))
{
employee.setGender(GENDER.FEMALE);
}
employee.setGender(GENDER.FEMALE);
employee.setIsIndian(resultSet.getBoolean("is_indian"));
employee.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employee.setPANNumber(resultSet.getString("pan_number").trim());
employee.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employee;
}//getByPANnumber_ends
//_______________________________________________________________________________________________________
public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException
{
if(aadharNumber.trim().length() == 0) throw new DAOException("length cant be 0");
EmployeeDTOInterface employee = null;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from employee where aadhar_card_number = ?");
preparedStatement.setString(1,aadharNumber);
ResultSet resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar Card Number: " + aadharNumber);
}
employee = new EmployeeDTO();
String genderSql;
employee.setEmployeeId("A" + (1000000 + resultSet.getInt("employee_id")));
employee.setName(resultSet.getString("name").trim());
employee.setDesignationCode(resultSet.getInt("designation_code"));
employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
genderSql = resultSet.getString("gender").trim();
if(genderSql.equalsIgnoreCase("M"))
{
employee.setGender(GENDER.MALE);
}
if(genderSql.equalsIgnoreCase("F"))
{
employee.setGender(GENDER.FEMALE);
}
employee.setGender(GENDER.FEMALE);
employee.setIsIndian(resultSet.getBoolean("is_indian"));
employee.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employee.setPANNumber(resultSet.getString("pan_number").trim());
employee.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employee;
}//getByAadharNumber_ends
//_______________________________________________________________________________________
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId.trim().length() == 0) return false;
boolean employeeIdExists = false;
int actualEmployeeId = 0;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employeeId");
}
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select gender from employee where employee_id = ?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet = preparedStatement.executeQuery();
employeeIdExists = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeIdExists;
}//EmployeeIDExists_ends
//___________________________________________________________________________________________________________
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber == null) return false;
if(panNumber.trim().length() == 0) return false;
boolean panNumberExists = false;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select gender from employee where pan_number = ?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet = preparedStatement.executeQuery();
panNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return panNumberExists;
}//panNumberExists_ends
//_______________________________________________________________________________________________
public boolean aadharNumberExists(String aadharNumber) throws DAOException
{
if(aadharNumber == null) return false;
if(aadharNumber.trim().length() == 0) return false;
boolean aadharNumberExists = false;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select gender from employee where aadhar_card_number = ?");
preparedStatement.setString(1,aadharNumber);
ResultSet resultSet = preparedStatement.executeQuery();
aadharNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return aadharNumberExists;
}//aadharNumberExists_ends

public int getCount() throws DAOException
{
int count = 0;
try
{
Connection connection = DAOConnection.getConnection();
Statement statement = connection.createStatement();
ResultSet resultSet;
resultSet = statement.executeQuery("select count(*) as cun from employee");
resultSet.next();
count = resultSet.getInt("cnt");
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return count;
}//getCount_ends
//________________________________________________________________________________________________________
public int getCountByDesignation(int designationCode) throws DAOException
{
int count = 0;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as cnt from employee where designation_code = ?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
resultSet.next();
count = resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return count;
}//getByDesignationCode_ends
//________________________________________________________________________________________________________
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
boolean designationAlloted = false;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code : " + designationCode );
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select gender from employee where designation_code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
designationAlloted = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return designationAlloted;
}//isDesignationAlloted

}