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
