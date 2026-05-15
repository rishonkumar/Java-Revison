# Encapsulation & Inheritance

> Two foundational OOP pillars. Encapsulation = data hiding. Inheritance = code reuse through parent-child relationships.

---

## 📖 Theory

### Encapsulation
- Make fields **`private`** so they cannot be directly accessed
- Provide controlled access through **getters/setters**
- Setters can include **validation logic** — this is the core benefit

```
Outside world         BankAccount
─────────────         ────────────────────────────
ba.deposit(500) ────→ public void deposit(int amt) {
                          balance += amt;  // controlled
                      }
ba.balance      ────✗  private double balance; // BLOCKED
```

### Inheritance
- Child class (`extends`) inherits all **non-private** fields and methods from parent
- Promotes **code reuse**
- Java supports **single inheritance** for classes (one parent only)
- **Multiple inheritance** via interfaces only

#### Types of Inheritance

```
Simple:          A → B

Multi-level:     A → B → C (B extends A, C extends B)

Hierarchical:        A
                    / \
                   B   C

Multiple:       NOT supported via classes (diamond problem)
                    A
                   / \
                  B   C
                   \ /
                    D  ← ambiguous! B.method() or C.method()?
```

---

## 🧪 Code Walkthroughs

### 1. Encapsulation — `DemoEncap.java`

```java
class BankAccount {
    private double balance;  // hidden from outside

    public void deposit(int amount) {
        balance += amount;   // controlled mutation
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public double getBalance() { return balance; }  // read-only access
}
```
**No direct access to `balance` from outside the class — that's encapsulation.**

---

### 2. Simple Inheritance — `InheritanceExample.java`

```java
class StudentDemo {
    void markAttendance() { ... }  // available to all students
}

class EngineeringStudent extends StudentDemo {
    void attendLab() { ... }       // only for engineering students
}

// EngineeringStudent inherits markAttendance() from StudentDemo
EngineeringStudent es = new EngineeringStudent();
es.markAttendance();  // ✅ inherited
es.attendLab();       // ✅ own method

StudentDemo sm = new StudentDemo();
sm.attendLab();       // ❌ compile error — parent doesn't know about child method
```

---

### 3. Types of Inheritance — `InheriDemo.java`
```java
// Multi-level: Student → EngineeringStudent → CSEEngineeringStudent
class CSEEngineeringStudent extends EngineeringStudent {
    void attendCSELab() { ... }
}
```

---

## ❓ Critical Interview Questions

### Q1. What is Encapsulation and why is it important?
> **Encapsulation** is the bundling of data (fields) and the methods that operate on that data into a single unit (class), while **restricting direct access** to the fields.

**Benefits:**
- Prevents invalid state (e.g., negative balance)
- Allows validation in setters
- Hides implementation details (you can change internals without breaking callers)
- Better maintainability

---

### Q2. What is the difference between method overloading and method overriding?
| | Overloading | Overriding |
|-|-------------|------------|
| Where | Same class | Child class overrides parent method |
| Signature | **Different** parameters | **Same** signature |
| Return type | Can differ | Must be same (or covariant) |
| Binding | Compile-time (static) | Runtime (dynamic) |
| `@Override` | Not used | Recommended |

```java
// Overloading
void print(int x) {}
void print(String s) {}

// Overriding
class Parent { void greet() { println("Parent"); } }
class Child extends Parent {
    @Override void greet() { println("Child"); }
}
```

---

### Q3. Why doesn't Java support multiple inheritance with classes?
> **Diamond Problem:** If class D extends B and C, both of which extend A, and A has a method that B and C override differently — which version does D inherit? This creates ambiguity.

```
      A.method()
     /          \
B.method()   C.method()
     \          /
         D  ← which method()?
```
> Java solves this with **interfaces** (using `default` methods with explicit override when needed).

---

### Q4. What is `super` keyword?
- Refers to the **parent class**
- `super.method()` → calls parent's version of overridden method
- `super(args)` → calls parent constructor (must be first line in child constructor)

```java
class Animal {
    Animal(String name) { System.out.println("Animal: " + name); }
}
class Dog extends Animal {
    Dog() {
        super("Dog");  // calls Animal constructor
        System.out.println("Dog created");
    }
}
```

---

### Q5. Can a constructor be inherited?
> **No.** Constructors are not inherited. But a child constructor implicitly calls `super()` (parent's no-arg constructor) unless you explicitly call `super(args)`.

---

### Q6. What is the difference between `private`, `protected`, and `public` in inheritance?
| Modifier | Same Class | Same Package | Subclass | World |
|----------|-----------|--------------|---------|-------|
| `private` | ✅ | ❌ | ❌ | ❌ |
| `default` (no modifier) | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

---

### Q7. What is the Liskov Substitution Principle (LSP)?
> A subclass should be substitutable for its superclass without breaking the program.

```java
// LSP respected:
StudentDemo s = new EngineeringStudent();  // ✅ works — EngineeringStudent IS-A StudentDemo
s.markAttendance();  // ✅ works fine
```

---

### Q8. TRICKY: What is the output of this code?

```java
class A {
    int x = 10;
    void show() { System.out.println("A: " + x); }
}
class B extends A {
    int x = 20;
    void show() { System.out.println("B: " + x); }
}
public class Test {
    public static void main(String[] args) {
        A obj = new B();
        obj.show();         // "B: 20" → dynamic dispatch (runtime)
        System.out.println(obj.x);  // 10 → fields are NOT polymorphic!
    }
}
```
> **Key insight:** Method calls are resolved at **runtime** (dynamic dispatch), but **field access** is resolved at **compile-time** (based on reference type).

---

## 📂 Files
| File | What it demonstrates |
|------|---------------------|
| `code/DemoEncap.java` | BankAccount encapsulation + Student getters/setters |
| `code/InheriDemo.java` | Inheritance types + multiple inheritance limitation |
| `code/InheritanceExample.java` | Simple inheritance: StudentDemo → EngineeringStudent |


---

## 💻 Full Source Code

> Below is the complete, beautified source code for all examples in this topic.

### code/DemoEncap.java

```java
package EncapsulationInheritance.code;

/*
 * ==========================================
 *        ENCAPSULATION IN JAVA
 * ==========================================
 * 
 *       [ Outside World / Main ]
 *                 |
 *                 v
 *  +-----------------------------+
 *  |       STUDENT OBJECT        |
 *  |                             |
 *  |   public void setAge(age)   | <--- Gatekeeper (Validation Logic)
 *  |   { if (age > 0) ... }      |
 *  |              |              |
 *  |              v              |
 *  |      [ private age ]        | <--- Hidden Data (Cannot be accessed directly)
 *  +-----------------------------+
 * 
 * Encapsulation = Data Hiding (private fields) + Controlled Access (public getters/setters)
 * Key benefit: Setters can add VALIDATION — preventing the object from entering an invalid state.
 */
public class DemoEncap {

    public static void main(String[] args) {

        // ── Basic BankAccount Encapsulation ───────────────────────────────
        BankAccount ba = new BankAccount();
        ba.deposit(500);
        ba.withdraw(300);
        System.out.println("Balance: " + ba.getBalance());  // 200.0

        // ba.balance = 9999;  // ❌ Compile error — private field, no direct access

        // ── Withdrawal guard: balance cannot go negative ──────────────────
        boolean result = ba.withdraw(1000);
        System.out.println("Withdraw 1000 succeeded: " + result);  // false
        System.out.println("Balance still: " + ba.getBalance());    // 200.0

        // ── Student with validated setter ─────────────────────────────────
        Student s = new Student("Rishon", 1, 21, "SRM");
        System.out.println("Student Name: " + s.getName());

        // s.setAge(-5);  // prints validation error — won't set negative age
    }
}

class BankAccount {
    // PRIVATE — outside world cannot directly touch this
    private double balance;  

    public void deposit(int amount) {
        // Validation inside — caller can't bypass this
        if (amount > 0) {          
            balance += amount;
        }
    }

    // Returns true if successful, false if insufficient balance
    public boolean withdraw(int amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        System.out.println("Insufficient balance or invalid amount.");
        return false;
    }

    // Getter — read-only access to balance
    public double getBalance() {
        return balance;
    }
    // No setter for balance — you MUST go through deposit/withdraw to alter state
}

class Student {
    private String name;
    private int rollNo;
    private int age;
    private String college;

    Student(String name, int rollNo, int age, String college) {
        this.name = name;
        this.rollNo = rollNo;
        this.age = age;
        this.college = college;
    }

    public String getName() { return name; }

    // Setter with validation — this is THE key benefit of encapsulation
    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            System.out.println("Invalid name — not updated.");
        }
    }

    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age — not updated.");
        }
    }

    public int getAge() { return age; }
}

```

### code/InheriDemo.java

```java
package EncapsulationInheritance.code;

/*
 * ==========================================
 *        INHERITANCE TYPES IN JAVA
 * ==========================================
 *
 * 1. Simple (Single)   : A → B
 * 2. Multi-level       : A → B → C
 * 3. Hierarchical      : A → B, A → C (one parent, multiple children)
 * 4. Multiple          : NOT supported via classes (diamond problem)
 *                        Achieved via INTERFACES
 *
 * THE DIAMOND PROBLEM:
 * 
 *         [ A ] (method X)
 *         /   \
 *       /       \
 *    [ B ]     [ C ] (both override method X)
 *       \       /
 *         \   /
 *         [ D ]  <-- Which method X does D inherit? AMBIGUOUS!
 * 
 * Java's fix: Multiple inheritance is ONLY allowed via interfaces.
 *             If two interfaces have the same default method → D MUST override it to resolve ambiguity.
 */
public class InheriDemo {

    public static void main(String[] args) {

        // ── 1. Simple Inheritance ─────────────────────────────────────────
        EngineeringStudent1 es = new EngineeringStudent1();
        es.markAttendance();  // Inherited/Overridden from StudentBase
        es.attendLab();       // Own method

        // ── 2. Multi-level Inheritance ────────────────────────────────────
        CSEEngineeringStudent cse = new CSEEngineeringStudent();
        cse.markAttendance(); // From StudentBase (grandparent)
        cse.attendLab();      // From EngineeringStudent1 (parent)
        cse.attendCSELab();   // Own method

        // ── Dynamic Method Dispatch (Polymorphism) ────────────────────────
        // Parent reference holding a child object
        StudentBase ref = new EngineeringStudent1();
        ref.markAttendance(); // ✅ Runtime uses the Child's overridden method!
        // ref.attendLab();   // ❌ Compile error — StudentBase reference doesn't know about attendLab()

        // ── 3. Multiple Inheritance via Interface ─────────────────────────
        AndroidDeveloper dev = new AndroidDeveloper();
        dev.writeCode();     // From Programmer interface
        dev.useMobile();     // From MobileUser interface
        dev.develop();       // Own method
    }
}

// ── Simple Base Class ──────────────────────────────────────────────────
class StudentBase {
    String name;
    int age;

    void markAttendance() {
        System.out.println(this.getClass().getSimpleName() + " attendance marked");
    }
}

// ── Simple Inheritance & parent for Multi-level ────────────────────────
class EngineeringStudent1 extends StudentBase {
    void attendLab() {
        System.out.println("Lab attended");
    }

    @Override
    void markAttendance() {
        System.out.println("Engineering student attendance (with biometric)");
    }
}

// ── Multi-level: StudentBase → EngineeringStudent1 → CSEEngineeringStudent 
class CSEEngineeringStudent extends EngineeringStudent1 {
    void attendCSELab() {
        System.out.println("CSE Lab attended");
    }
}

// ── Hierarchical: StudentBase → MedicalStudent (sibling of EngineeringStudent) 
class MedicalStudent extends StudentBase {
    void attendClinic() {
        System.out.println("Clinic attended");
    }
}

// ── Multiple Inheritance via Interfaces ────────────────────────────────
interface Programmer {
    default void writeCode() { System.out.println("Writing code..."); }
}

interface MobileUser {
    default void useMobile() { System.out.println("Using mobile..."); }
}

// AndroidDeveloper IS-A Programmer AND IS-A MobileUser — no diamond problem!
class AndroidDeveloper implements Programmer, MobileUser {
    void develop() { System.out.println("Developing Android app"); }
}

```

### code/InheritanceExample.java

```java
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



```

