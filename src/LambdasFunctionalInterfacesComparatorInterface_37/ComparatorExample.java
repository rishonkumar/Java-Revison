package LambdasFunctionalInterfacesComparatorInterface_37;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * ==========================================
 *        COMPARATOR & LAMBDA EVOLUTION
 * ==========================================
 * 
 * We use Comparator whenever we want to sort objects by multiple/different 
 * fields (e.g., sort by name, then by rollNo, then by marks).
 * 
 * EVOLUTION OF THE CODE:
 * 
 * 1. SEPARATE CLASS (Verbose)
 *    class SortByMarks implements Comparator<Student> { ... }
 *             |
 *             v
 * 2. ANONYMOUS INNER CLASS (Less verbose, still clunky)
 *    new Comparator<Student>() { public int compare(...) { ... } }
 *             |
 *             v
 * 3. LAMBDA EXPRESSION (Modern Java 8+)
 *    (s1, s2) -> s1.marks - s2.marks
 * 
 * Why does Lambda work here?
 * Because `Comparator` is a @FunctionalInterface (it has exactly ONE abstract method: `compare`).
 * Target Typing: Java infers `s1` and `s2` are `Student` based on the List type.
 */
public class ComparatorExample {

    static void main() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Rishon", 22, 111));
        list.add(new Student("Praveen", 13, 451));
        list.add(new Student("Hari", 26, 161));
        list.add(new Student("Rizon", 42, 112));

        // ── 1. The Old Way: Separate Classes ──────────────────────────────
        Comparator<Student> c1 = new SortByMarks();
        Comparator<Student> c2 = new SortByName();
        Comparator<Student> c3 = new SortByRollNo();

        Collections.sort(list, c1);
        System.out.println("--- Sorted by Marks (Class) ---");
        for (Student s : list) {
            System.out.println(s.name + ", " + s.marks + " ," + s.rollNo);
        }

        // ── 2. The Intermediate Way: Anonymous Inner Class ─────────────────
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.marks - o2.marks;
            }
        });

        // ── 3. The Modern Way: Lambda Expression ───────────────────────────
        // We just need to define the behavior, not a whole class!
        Collections.sort(list, (s1, s2) -> s1.marks - s2.marks);

        System.out.println("\n--- Sorted by Marks (Lambda) ---");
        for (Student s : list) {
            System.out.println(s.name + ", " + s.marks + " ," + s.rollNo);
        }
        
        /*
         * LAMBDA SYNTAX RULES:
         * Multiple parameters: (a, b) -> a + b
         * Single parameter:    x -> x * x
         * No parameter:        () -> System.out.println("Hello")
         * Multi-line body:     (a, b) -> { int sum = a + b; return sum; }
         */
    }
}

// Verbose custom classes implementations...
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
        return s1.marks - s2.marks; // Note: Vulnerable to integer overflow, use Integer.compare in production
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
