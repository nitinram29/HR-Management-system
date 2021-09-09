import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class EmployeeupdateTestCase
{
public static void main(String[] aa)
{
try
{

EmployeeDTOInterface employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A100000001");
employeeDTO.setDesignationCode(3);
employeeDTO.setName("aaqaqaqaq");


SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
employeeDTO.setDateOfBirth(sdf.parse("12/09/2012"));


employeeDTO.setGender(GENDER.MALE);

//employeeDTO.setIsIndian(Boolean.parseBoolean(false));
employeeDTO.setIsIndian(false);
BigDecimal bigDecimal = new BigDecimal(1233123123);
employeeDTO.setBasicSalary(bigDecimal);
employeeDTO.setPANNumber("qweasd");
employeeDTO.setAadharCardNumber("123qweqwe");

EmployeeDAOInterface employeeDAO = new EmployeeDAO();
employeeDAO.update(employeeDTO);

}
 catch(DAOException daoexception)
 {
 System.out.println(daoexception.getMessage());
 }
 catch(ParseException pexception)
 {
 System.out.println(pexception.getMessage());
 }

}
}
