import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.math.*;
public class EmployeePANExistsTestCase
{
public static void main(String[] aa)
{
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
System.out.println(employeeDAO.panNumberExists("123qwe"));

EmployeeDAOInterface employeeDAO1 = new EmployeeDAO();
System.out.println(employeeDAO1.panNumberExists("123qwed"));


}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}