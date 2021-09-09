
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.math.*;
public class EmployeeGetByEmployeeTeseCase
{
public static void main(String[] aa)
{
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
EmployeeDTOInterface employeeDTO = employeeDAO.getByEmployeeId(aa[0]);
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
System.out.println(employeeDTO.getDateOfBirth());
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadharCardNumber());
System.out.println("______________________________________");

employeeDTO = employeeDAO.getByEmployeeId("5");
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
System.out.println(employeeDTO.getDateOfBirth());
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadharCardNumber());


}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}