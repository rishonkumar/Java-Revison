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
