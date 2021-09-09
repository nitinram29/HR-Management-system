
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.math.*;
public class EmployeeGetByDesignationAllotedTestCase
{
public static void main(String[] aa)
{
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
boolean ans = employeeDAO.isDesignationAlloted(2);
System.out.println(ans);
ans = employeeDAO.isDesignationAlloted(1);
System.out.println(ans);
//ans = employeeDAO.isDesignationAlloted();
//System.out.println(ans);

}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}