package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManagerInterface designationManager = null; 
private DesignationManager() throws BLException
{
populateDataStructures();
}
//____________________________________________________________________________________________________
private void populateDataStructures() throws BLException
{
this.codeWiseDesignationsMap = new HashMap<>();
this.titleWiseDesignationsMap = new HashMap<>();
this.designationsSet = new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations = new DesignationDAO().getall();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation : dlDesignations)
{
designation = new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(designation.getCode(),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoexception)
{
BLException blException = new BLException();
blException.setGenericException(daoexception.getMessage());
throw blException;
}
}
//____________________________________________________________________________________________________
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager == null) designationManager = new DesignationManager();
return designationManager;
}
//____________________________________________________________________________________________________
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException = new BLException();
if(designation == null)
{
blException.setGenericException("Designation required.");
throw blException;
}
int code = designation.getCode();
String title = designation.getTitle();
if(code != 0)
{
blException.addException("code","Code should be zero");
}
if(title == null)
{
blException.addException("title","Title required");
title = "";
}
else
{
title = title.trim();
if(title.length() == 0)
{
blException.addException("title","Title required");
}
}
if(title.length() > 0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation : "+ title +" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO = new DesignationDAO();
designationDAO.add(designationDTO);
code = designationDTO.getCode();
designation.setCode(code);
Designation dsDesignation = new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(code,dsDesignation);
title = title.toUpperCase();
this.titleWiseDesignationsMap.put(title,dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
//____________________________________________________________________________________________________
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException = new BLException();
if(designation == null)
{
blException.setGenericException("Designation required.");
throw blException;
}
int code = designation.getCode();
String title = designation.getTitle();
if(code <= 0)
{
blException.addException("code","Invalid Code.");
throw blException;
}
if(code > 0)
{
if(!(this.codeWiseDesignationsMap.containsKey(code)))
{
blException.addException("code","Designation : "+ code +" not exists");
throw blException;
}
}
if(title == null)
{
blException.addException("title","Title required");
title = "";
}
else
{
title = title.trim();
if(title.length() == 0)
{
blException.addException("title","Title required");
}
}
if(title.length() > 0)
{
DesignationInterface d = titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code)
{
blException.addException("title","Designation : "+ title +" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO = new DesignationDAO();
designationDAO.update(designationDTO);
DesignationInterface dsdesignation = codeWiseDesignationsMap.get(code);
//removing existing code elements
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(dsdesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsdesignation);
//updating title 
dsdesignation.setTitle(title);
//adding in ds
this.codeWiseDesignationsMap.put(code,dsdesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsdesignation);
this.designationsSet.add(dsdesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
//____________________________________________________________________________________________________
public void removeDesignation(int code) throws BLException
{
BLException blException = new BLException();
int Code = code;
if(code <= 0)
{
blException.addException("code","Invalid Code.");
}
if(!(this.codeWiseDesignationsMap.containsKey(code)))
{
blException.addException("code","Designation : "+ code +" not exists");
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDAOInterface designationDAO = new DesignationDAO();
designationDAO.delete(Code);
DesignationInterface dsDesignation = this.codeWiseDesignationsMap.get(code);
this.codeWiseDesignationsMap.remove(Code);
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);	
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//____________________________________________________________________________________________________
public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation = this.codeWiseDesignationsMap.get(code);
if(designation == null)
{
BLException blException = new BLException();
blException.addException("code","Designation : "+ code +" not exists");
throw blException;
}
DesignationInterface d = new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
//____________________________________________________________________________________________________
DesignationInterface getDSDesignationByCode(int code) throws BLException
{
DesignationInterface designation = this.codeWiseDesignationsMap.get(code);
if(designation == null)
{
BLException blException = new BLException();
blException.addException("code","Designation : "+ code +" not exists");
throw blException;
}
return designation;
}
//____________________________________________________________________________________________________
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation = this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation == null)
{
BLException blException = new BLException();
blException.addException("title","Designation : "+ title +" not exists");
throw blException;
}
DesignationInterface d = new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
//____________________________________________________________________________________________________
DesignationInterface getDSDesignationByTitle(String title) throws BLException
{
DesignationInterface designation = this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation == null)
{
BLException blException = new BLException();
blException.addException("title","Designation : "+ title +" not exists");
throw blException;
}
return designation;
}
//____________________________________________________________________________________________________
public int getDesignationCount()
{
return designationsSet.size();
}
//____________________________________________________________________________________________________
public boolean designationCodeExists(int code)
{
return this.codeWiseDesignationsMap.containsKey(code);
}
//____________________________________________________________________________________________________
public boolean designationTitleExists(String title)
{
return this.titleWiseDesignationsMap.containsKey(title.toUpperCase());
}
//____________________________________________________________________________________________________
public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations = new TreeSet<>();
designationsSet.forEach((v)->{
DesignationInterface d = new Designation();
d.setCode(v.getCode());
d.setTitle(v.getTitle());
designations.add(d);
});
return designations;
}
//____________________________________________________________________________________________________
}