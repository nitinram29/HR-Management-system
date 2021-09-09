import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationGetTestCase
{
public static void main(String[] aa)
{
try
{
DesignationDAOInterface designationDAO = new DesignationDAO();
System.out.println("Designtion count : " + designationDAO.getCount());
}
 catch(DAOException daoexception)
 {
 System.out.println(daoexception.getMessage());
 }

}
}
