import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.math.*;
import java.util.*;
public class EmployeeIdExistsTestCase
{
public static void main(String[] aa)
{
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
System.out.println(employeeDAO.employeeIdExists(aa[0])); 
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}
