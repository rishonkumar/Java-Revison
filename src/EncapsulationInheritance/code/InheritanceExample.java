package EncapsulationInheritance.code;

public class InheritanceExample {

    static void main() {
        EngineeringStudent es = new EngineeringStudent();
        es.markAttendance();
        es.attendLab();

        StudentDemo sm = new StudentDemo();
//        sm.attendLab(); error
        sm.markAttendance();
    }
}
/*
Parent(SuperClass) -> CHild(subclass) Simple inheritance
 */

class StudentDemo {
    String name;
    int age;

    void markAttendance() {
        System.out.println("Attendance marked");
    }
    //it should not be private

}

class EngineeringStudent extends StudentDemo {

   void attendLab() {
       System.out.println("Lab marked");

   }
}


