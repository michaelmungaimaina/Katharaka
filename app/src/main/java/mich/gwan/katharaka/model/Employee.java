package mich.gwan.katharaka.model;

public class Employee {

    //Employee attributes
    private String emp_id;
    private String emp_phone;
    private String emp_email;
    private String emp_name;
    private String emp_password;
   // private String emp_station;
   // private String emp_usertype;

    //Employee accessors and modifiers
    public String getEmp_id() { return emp_id; }
    public void setEmp_id(String emp_id) { this.emp_id = emp_id; }

    public String getEmp_phone() { return  emp_phone; }
    public void setEmp_phone(String emp_phone) { this.emp_phone = emp_phone; }

    public String getEmp_email() { return  emp_email; }
    public void setEmp_email(String emp_email) { this.emp_email = emp_email; }

    public  String getEmp_name() { return  emp_name; }
    public void setEmp_name(String emp_name) {this.emp_name = emp_name; }

    public String getEmp_password() { return emp_password; }
    public void setEmp_password(String emp_password) { this.emp_password = emp_password; }

    //public String getEmp_station() { return emp_station; }
    //public void setEmp_station(String empStation) { this.emp_station = empStation; }

    //public String getEmp_usertype() { return emp_usertype; }
    //public void setEmp_usertype(String emp_usertype) { this.emp_usertype = emp_usertype; }
}
