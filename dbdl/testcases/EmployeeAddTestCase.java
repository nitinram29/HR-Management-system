import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.math.*;
public class EmployeeAddTestCase
{
public static void main(String[] aa)
{
try
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();
employeeDTO.setName(aa[0]);
employeeDTO.setDesignationCode(Integer.parseInt(aa[1]));

SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yy");
employeeDTO.setDateOfBirth(sdf.parse(aa[2]));
char fGender = aa[3].charAt(0);
if(fGender == 'M')
employeeDTO.setGender(GENDER.MALE);
else
employeeDTO.setGender(GENDER.FEMALE);

employeeDTO.setIsIndian(Boolean.parseBoolean(aa[4]));

BigDecimal bigDecimal = new BigDecimal(aa[5]);
employeeDTO.setBasicSalary(bigDecimal);

employeeDTO.setPANNumber(aa[6]);
employeeDTO.setAadharCardNumber(aa[7]);
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
employeeDAO.add(employeeDTO); 
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
catch(ParseException pe)
{
System.out.println(pe.getMessage());
}

}
}