import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.math.*;
import java.util.*;

public class EmployeeGetAllTestCase
{
public static void main(String[] aa)
{
try
{

Set<EmployeeDTOInterface> employees;
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
employees = employeeDAO.getall();

employees.forEach((d)->{
System.out.println("id is : " + d.getEmployeeId() + "\n");
System.out.println("Designation is : " + d.getDesignationCode() + "\n");
System.out.println("name is : " + d.getName() + "\n");
System.out.println("date of birth is : " + d.getDateOfBirth() + "\n");
System.out.println("gender is : " + d.getGender() + "\n");
System.out.println("isIndian is : " + d.getIsIndian() + "\n");
System.out.println("basic salary is : " + d.getBasicSalary() + "\n");
System.out.println("PAN number is : " + d.getPANNumber() + "\n");
System.out.println("Aadhar number is : " + d.getAadharCardNumber() + "\n");
System.out.println("---------------------------------------------------------------------------------------");
});



}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}

}
}