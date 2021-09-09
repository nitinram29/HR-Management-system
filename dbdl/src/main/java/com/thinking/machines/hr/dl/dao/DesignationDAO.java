package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{
private String DATA_FILE = "designation.data";

public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO == null) throw new DAOException("Designation is  null");
String title = designationDTO.getTitle();
if(title == null) throw new DAOException("Designation is  null");
title = title.trim();
if(title.length() == 0) throw new DAOException("length of designation is 0");
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select code from designation where title = ?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : " + title + " exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("insert into designation (title) values (?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet = preparedStatement.getGeneratedKeys();
resultSet.next();
int code = resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
designationDTO.setCode(code);
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}//try-catch_ends
}//Add_ends

public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO == null) throw new DAOException("Designation is  null");
int Code = designationDTO.getCode();
if(Code <= 0) throw new DAOException("invald code : " + Code);
String title = designationDTO.getTitle();
if(title == null) throw new DAOException("Designation is  null");
title = title.trim();
if(title.length() == 0) throw new DAOException("length of designation is 0");
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,Code);
resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : " + Code);
}
preparedStatement.close();
preparedStatement = connection.prepareStatement("select code from designation where title = ?",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
resultSet = preparedStatement.executeQuery();
if(resultSet.next())
{
if(resultSet.getInt(1) != Code)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : " + title + " exists.");
}
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("update designation set title = ? where code ?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,Code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//update_ends

public void delete(int code) throws DAOException
{
int Code = code;
if(Code <= 0) throw new DAOException("invald code : " + Code);
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select * from designation where code = ?");
preparedStatement.setInt(1,Code);
resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false) 
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : " + Code);
}
String designation = resultSet.getString("title").trim();	
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select gender from employee where designation_code = ?");
preparedStatement.setInt(1,Code);
resultSet = preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Cannot delete designation : " + designation + " as it has alloted to employee(s)");
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("delete from designation where code = ?");
preparedStatement.setInt(1,Code);
preparedStatement.executeUpdate();
connection.close();
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//delete_ends

public Set<DesignationDTOInterface> getall() throws DAOException
{
Set<DesignationDTOInterface> designations = new TreeSet<>();
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery("select * from designation");
DesignationDTOInterface designationDTO;
while(resultSet.next())
{
designationDTO = new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());	
designations.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
return designations;
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//set_ends

public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code <= 0) throw new DAOException("Invalid code : " + code);
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select * from designation where code = ?");
preparedStatement.setInt(1,code);
resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid code : " + code);
}
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(resultSet.getString("title").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//getByCode_ends

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title == null || title.trim().length() == 0) throw new DAOException("invalid title : " + title);
title = title.trim();
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select * from designation where title = ?");
preparedStatement.setString(1,title);
resultSet = preparedStatement.executeQuery();
if(resultSet.next() == false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : " + title + " does not exist.");
}
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(title);
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//getByTitle_ends

public boolean codeExists(int code) throws DAOException
{
if(code <= 0) return false;
try
{
Boolean exists;
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,code);
resultSet = preparedStatement.executeQuery();
exists = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return exists;
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//codeExists_ends

public boolean titleExists(String title) throws DAOException
{
if(title == null || title.trim().length() == 0) return false;
title = title.trim();
try
{
Boolean exists;
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select title from designation where title = ?");
preparedStatement.setString(1,title);
resultSet = preparedStatement.executeQuery();
exists = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return exists;
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//titleExists_ends

public int getCount() throws DAOException
{
try
{
Connection connection = DAOConnection.getConnection();
Statement statement;
ResultSet resultSet;
statement = connection.createStatement();
resultSet = statement.executeQuery("select count(*) as cnt from designation");
resultSet.next();
int count = resultSet.getInt("count");
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException SQLexception)
{
throw new DAOException(SQLexception.getMessage());
}
}//getCount_ends
}//dao_ends