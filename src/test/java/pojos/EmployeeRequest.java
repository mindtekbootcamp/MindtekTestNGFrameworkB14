package pojos;

import utilities.HrApiUtils;

public class EmployeeRequest {

    private String first_name;
    private String last_name;
    private String email;
    private String hire_date;
    private Integer job_id;
    private Double salary;
    private Integer department_id;

    public void setDefaultValues(){
        first_name="John";
        last_name="Doe";
        email="john.doe@gmail.com";
        hire_date="2025-08-05";
        job_id=4;
        salary=1234.00;
        department_id = HrApiUtils.existingDeptId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHire_date() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }
}
