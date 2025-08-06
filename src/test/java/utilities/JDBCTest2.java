package utilities;

import org.apache.hc.core5.http.io.SessionOutputBuffer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBCTest2 {
    public static void main(String[] args) throws SQLException {
        JDBCUtils.connectToDB(
                "jdbc:postgresql://localhost:5432/HR Department",
                "postgres",
                "admin");

        // CRUD
//        String queryToAddNewDepartment = "insert into departments (department_id, department_name, location_id) values (22, 'TestDepartment', 1800)";
//        String queryToDeleteDepartment = "delete from departments where department_name = 'TestDepartment'";
//        String queryToUpdateDepartment = "update departments set department_name= 'Information Technology' where department_name = 'IT'";
//        JDBCUtils.executeQueryUpdate(queryToUpdateDepartment);
//        List<Map<String, Object>> myData = JDBCUtils.executeQuery("select * from departments");
//        System.out.println(myData);
//        for(Map<String, Object> el : myData){
//            System.out.println(el);
//        }

        // print fname, lname of all emps with salary less than 5000
        System.out.println();

        for(Map<String, Object> el :  JDBCUtils.executeQuery("select first_name, last_name from employees where salary <= 5000"))
            System.out.println(el);

        System.out.println("----------------------");

        // Get first_name, salary, department_name of employees who has salary more than 10000

        List<Map<String, Object>> myData = JDBCUtils.executeQuery("select first_name, salary, department_name from employees " +
                "join departments " +
                "on employees.department_id = departments.department_id " +
                "where salary > 10000");
        for(Map<String, Object> el : myData)
            System.out.println(el);

        // close DB connection:
        JDBCUtils.closeDBConnection();
        System.out.println("************");
        // print every row which has fname longer than 6
        for(Map<String, Object> el : myData)
            if(el.get("first_name").toString().length() > 6) System.out.println(el);

        System.out.println("%%%%%%%%%%%%%%");
        // Print salary with first_name of employees who work in Executive
        for(Map<String, Object> el : myData)
            if(el.get("department_name").toString().equals("Executive"))
                System.out.println(el.get("first_name") + " " + el.get("salary"));

    }
}
