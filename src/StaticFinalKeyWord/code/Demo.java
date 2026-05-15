package StaticFinalKeyWord.code;

/*
 * ==========================================
 *     STATIC KEYWORD & MEMORY ALLOCATION
 * ==========================================
 * 
 *     HEAP MEMORY (Per Object)        METHOD AREA / METASPACE (Class Level)
 *  +---------------------------+    +----------------------------------+
 *  | s1 (Student)              |    | Student Class                    |
 *  | - name: "Aditya"          |    | - college: "SRM" (Shared)        |
 *  | - age: 28                 |    +----------------------------------+
 *  | - rollNumber: 101         |            ^
 *  +---------------------------+            | (All objects point here)
 *                                           |
 *  +---------------------------+            |
 *  | s2 (Student)              |            |
 *  | - name: "Rohit"           |------------+
 *  | - age: 28                 |
 *  | - rollNumber: 102         |
 *  +---------------------------+
 * 
 * NOTE: 
 * 1. Static variables belong to the CLASS, not the object.
 * 2. Static blocks run EXACTLY ONCE when the class is loaded by the JVM.
 */
public class Demo {

    static void main() {

        Student s1 = new Student("Aditya", 28, 101);
        Student s2 = new Student("Rohit", 28, 102);

        // Access static variable using Class Name (Recommended)
        System.out.println(s1.name + " , " + s1.age + " , " + s1.rollNumber + " , " + Student.college);
        System.out.println(s2.name + " , " + s2.age + " , " + s2.rollNumber + " , " + Student.college);

    }
}

class Student {
    String name;
    int age;
    int rollNumber;
    
    // Static variable (Shared across all objects)
    static String college; 

    Student(String name, int age, int rollNumber) {
        this.name = name;
        this.age = age;
        this.rollNumber = rollNumber;
    }

    // Static block: Runs once when the class is first loaded into memory
    static {
        System.out.println("Static block executed! (Class Loaded)");
        college = "SRM";
    }
}
