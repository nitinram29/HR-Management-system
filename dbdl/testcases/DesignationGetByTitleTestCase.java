import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationGetByTitleTestCase
{
public static void main(String[] aa)
{
String Title = aa[0];
try
{
DesignationDTOInterface designationDTO;
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
designationDTO = designationDAO.getByTitle(Title);
System.out.println("Designation Title is ; " + designationDTO.getTitle() + " , cod is ; " + designationDTO.getCode());
}catch(DAOException daoexception)
 {
 System.out.println(daoexception.getMessage());
 }

}
}
