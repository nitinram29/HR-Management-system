import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
public class DesignationGettAllTestCase
{
public static void main(String[] aa)
{
try
{

Set<DesignationDTOInterface> designations;
DesignationDAOInterface designationDAO = new DesignationDAO();
designations = designationDAO.getall();

designations.forEach((d)->{
System.out.println("Code is : " + d.getCode() + " , title is : " + d.getTitle() + "\n");
});

}
 catch(DAOException daoexception)
 {
 System.out.println(daoexception.getMessage());
 }

}
}
