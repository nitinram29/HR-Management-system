import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationDeleteTestCase
{
public static void main(String[] aa)
{
try
{

DesignationDAOInterface designationDAO = new DesignationDAO();
designationDAO.delete(Integer.parseInt(aa[0]));
System.out.println("deleted");
}
 catch(DAOException daoexception)
 {
 System.out.println(daoexception.getMessage());
 }

}
}
