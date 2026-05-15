# Comparator Interface & Lambda Introduction

> Learn how to sort custom objects using `Comparator<T>`, and see the evolution from verbose class implementations → anonymous classes → lambda expressions.

---

## 📖 Theory

### Why Comparator?
- `Collections.sort(list)` works for natural ordering (numbers, strings)
- For **custom objects** (Student by marks, by name, by roll no.) you need a `Comparator`

```java
// Comparator contract:
int compare(T o1, T o2)
// Returns: negative → o1 comes first
//          zero     → equal
//          positive → o2 comes first
```

### Evolution of Sorting Code

```
Step 1: Full class implements Comparator   (verbose, separate file)
Step 2: Anonymous class                    (inline but bulky)
Step 3: Lambda expression                  (clean, concise)
```

---

## 🧪 Code Walkthrough — `ComparatorExample.java`

### Step 1: Full Comparator Classes

```java
class SortByMarks implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return s1.marks - s2.marks;  // ascending by marks
    }
}
Collections.sort(list, new SortByMarks());
```

### Step 2: Anonymous Class

```java
Collections.sort(list, new Comparator<Student>() {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.marks - o2.marks;
    }
});
```

### Step 3: Lambda Expression ✅ (Preferred)

```java
Collections.sort(list, (s1, s2) -> s1.marks - s2.marks);
// OR using Comparator.comparingInt():
list.sort(Comparator.comparingInt(s -> s.marks));
```

---

## Lambda Expression Syntax

```java
// Single parameter
x -> x * x

// Multiple parameters
(a, b) -> a + b

// No parameters
() -> System.out.println("Hello")

// Multi-line body
(a, b) -> {
    int result = a + b;
    return result;
}
```

### Target Typing
> The compiler infers the lambda's type from the **context** (what functional interface is expected).  
> `(s1, s2) -> s1.marks - s2.marks` — compiler knows `s1`, `s2` are `Student` because `Comparator<Student>` is expected.

---

## ❓ Critical Interview Questions

### Q1. What is the difference between `Comparable` and `Comparator`?
| | `Comparable<T>` | `Comparator<T>` |
|-|-----------------|-----------------|
| Package | `java.lang` | `java.util` |
| Method | `compareTo(T o)` | `compare(T o1, T o2)` |
| Where defined | **Inside** the class to be sorted | **Outside** — separate class/lambda |
| Modifies class? | ✅ Yes | ❌ No |
| Multiple orderings | ❌ Only one natural order | ✅ Yes — multiple comparators possible |

```java
// Comparable — class defines its own natural order
class Student implements Comparable<Student> {
    public int compareTo(Student s) { return this.marks - s.marks; }
}
Collections.sort(list);  // uses compareTo

// Comparator — define multiple sort strategies externally
list.sort(Comparator.comparingInt((Student s) -> s.marks)
          .thenComparing(s -> s.name));  // chaining!
```

---

### Q2. What is a Functional Interface?
> An interface with **exactly one abstract method**. It can have:
> - Any number of `default` methods
> - Any number of `static` methods
> - Only **one abstract method**

```java
@FunctionalInterface
interface Calculate {
    int calculate(int a, int b);  // single abstract method
    // static/default methods allowed
}
```
> `Comparator<T>` is a functional interface — its one abstract method is `compare()`.

---

### Q3. Can you chain Comparators?

```java
// Sort by marks ascending, then by name alphabetically for ties
Comparator<Student> comp = Comparator.comparingInt((Student s) -> s.marks)
                                     .thenComparing(s -> s.name)
                                     .reversed();  // flip to descending
list.sort(comp);
```

---

### Q4. TRICKY: What does `s1.marks - s2.marks` return and when does it fail?
> - Positive → `s1` should come after `s2`
> - Negative → `s1` should come before `s2`
> - Zero → equal

**⚠️ Danger:** Integer subtraction can **overflow** for extreme values!
```java
// If s1.marks = Integer.MAX_VALUE, s2.marks = -1 → overflow → wrong result
// Safe alternative:
return Integer.compare(s1.marks, s2.marks);  // ✅ no overflow
```

---

### Q5. How do you sort in descending order?
```java
// Option 1: reverse the subtraction
(s1, s2) -> s2.marks - s1.marks

// Option 2: use reversed()
Comparator.comparingInt((Student s) -> s.marks).reversed()

// Option 3: Collections.reverseOrder() for natural ordering
Collections.sort(list, Collections.reverseOrder());
```

---

## 📂 Files
| File | What it demonstrates |
|------|---------------------|
| `ComparatorExample.java` | Full class → anonymous → lambda sorting evolution |


---

## 💻 Full Source Code

> Below is the complete, beautified source code for all examples in this topic.

### ComparatorExample.java

```java
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

```

