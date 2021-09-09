package com.thinking.machines.hr.dl.interfaces.dao;
import  com.thinking.machines.hr.dl.exceptions.*;
import  com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public interface EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employeeDTO) throws DAOException;

public void update(EmployeeDTOInterface employeeDTO) throws DAOException;

public void delete(String employeeId) throws DAOException;

public Set<EmployeeDTOInterface> getall() throws DAOException;

public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException;

public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException;

public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException;

public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException;

public boolean employeeIdExists(String employeeId) throws DAOException;

public boolean panNumberExists(String panNumber) throws DAOException;

public boolean aadharNumberExists(String aadharNumber) throws DAOException;

public int getCount() throws DAOException;

public int getCountByDesignation(int designationCode) throws DAOException;

public boolean isDesignationAlloted(int designationCode) throws DAOException;

}