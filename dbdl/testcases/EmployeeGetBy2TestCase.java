import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.math.*;
public class EmployeeGetBy2TestCase
{
public static void main(String[] aa)
{
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
System.out.println(employeeDAO.getCount());

System.out.println(employeeDAO.getCountByDesignation(1));


}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}