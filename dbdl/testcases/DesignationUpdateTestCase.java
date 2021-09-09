import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationUpdateTestCase
{
public static void main(String[] aa)
{
try
{

DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(4);
designationDTO.setTitle("a");

DesignationDAOInterface designationDAO = new DesignationDAO();
designationDAO.update(designationDTO);

}
 catch(DAOException daoexception)
 {
 System.out.println(daoexception.getMessage());
 }

}
}
