package LambdasFunctionalInterfacesComparatorInterface_37;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//We yse comparator whenver we want to sort based on anything not specfic to marks also
// we can sort by name or rollno or marks in that case we use comparator
public class ComparatorExample {

    static void main() {
        List<Student>list = new ArrayList<>();
        list.add(new Student("Rishon",22,111));
        list.add(new Student("Praveen",13,451));
        list.add(new Student("Hari",26,161));
        list.add(new Student("Rizon",42,112));

        Comparator<Student>c1 = new SortByMarks();
        Comparator<Student>c2 = new SortByName();
        Comparator<Student>c3 = new SortByRollNo();


        Collections.sort(list, c1);

        for(Student s : list) {
            System.out.println(s.name + ", " + s.marks + " ," + s.rollNo);
        }

        Collections.sort(list, c2);
        Collections.sort(list, c3);

        //Instead of writng all this classes we can use anonymms class
        //instead of creating class this is done simple way
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.marks - o2.marks;
            }
        });

        //even in anonyms we are still creating class it looks complicated
        //I jsut need to tell a method how to do something
        // we can use fucntion interface which can be implemtent thright lamda expression
        //Function interface means
        //    Only one abstrach method => inthis case interface Comparator<T> {int compare (To1, To2) -> absrtrach method}
        //   static method
        // default method

        // this functional interface can be written in lamda expression
        // here list if of stupe student so obvious the paramter will be of type student
        Collections.sort(list, (s1,s2) -> s1.marks - s2.marks);

        for(Student s : list) {
            System.out.println(s.name + ", " + s.marks + " ," + s.rollNo);
        }
        //Ways to write Lamda expresioon declare

        // multiple paramater (a,b) -> (a+b)
        // Single paramter x -> x*x
        //no paramter () -> print("helo")

        //multiline
        /*
        (a,b) -> {

        int s = a+b
        reutrn s

        }
         */

        //From the current context it assume what type s1 and s2 are thsi is also know as TARGET TYPING
        //Lamda exp is implemted for functional interface and it was it can have only one abstract method and it maps to it


    }
}
class SortByName implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);
    }
}

class SortByRollNo implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return s1.rollNo - s2.rollNo;
    }

}

class SortByMarks implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return s1.marks - s2.marks;
    }
}

class Student {
    String name;
    int rollNo;
    int marks;

    public Student(String name, int rollNo, int marks) {
        this.marks = marks;
        this.rollNo = rollNo;
        this.name = name;
    }
}
