import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class employeeDeleteTestCase
{
public static void main(String[] aa)
{

try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
employeeDAO.delete("A100000003");
System.out.println("deleted");
}catch(DAOException dao)
{
System.out.println(dao.getMessage());
}
}
}
