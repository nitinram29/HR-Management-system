import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationCode_TitleExistsTestCase
{
public static void main(String[] aa)
{
int Code = Integer.parseInt(aa[0]);
String title = aa[1];
try
{
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
boolean ans = designationDAO.codeExists(Code);
boolean ans1 = designationDAO.titleExists(title);

System.out.println("Code Exists : " + ans);
System.out.println("Title Exists : " + ans1);

}catch(DAOException daoexception)
 {
 System.out.println(daoexception.getMessage());
 }

}
}
 