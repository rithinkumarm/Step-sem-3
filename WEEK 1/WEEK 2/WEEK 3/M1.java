
class Employee1{
    public  String empName;
    double salary;
    static String companyName = "ABC Corp";
    static int employeeCount = 0;

    Employee1(String empName, double salary){
        this.empName = empName;
        this.salary = salary;
        employeeCount++;
    }

    void printEmployeeInfo() {
        System.out.println("Employee Name: " + empName);
        System.out.println("Salary: " + salary);
    }

    static void printCompanyInfo(){
        System.out.println("Company Name: " + companyName);
        System.out.println("Employee Count: " + employeeCount);
    }
}


public class M1 {
    public static void main(String[] args) {
        Employee1 emp1 = new Employee1("Alice", 50000.0);
        Employee1 emp2 = new Employee1("Bob", 60000.0);
        Employee1 emp3 = new Employee1("Charlie", 70000.0);

        emp1.printEmployeeInfo();
        emp2.printEmployeeInfo();
        emp3.printEmployeeInfo();
        Employee1.printCompanyInfo();
    }
}