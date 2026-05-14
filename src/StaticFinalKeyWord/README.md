# Static & Final Keywords

> Two of the most frequently asked Java interview topics. `static` is about class-level ownership; `final` is about immutability.

---

## 📖 Theory

### `static` Keyword
- Belongs to the **class**, not to any object
- All instances share the same static field
- Static members are loaded when the **class is loaded** by JVM (before any object is created)

```
JVM loads class → static block runs → static fields initialized
Object created  → instance fields initialized
```

### `final` Keyword
- `final` variable → **cannot be reassigned** after initialization
- `final` method → **cannot be overridden**
- `final` class → **cannot be subclassed** (e.g., `String`, `Integer`)

---

## 🧪 Code Walkthroughs

### 1. Static Variable + Static Block — `Demo.java`

```java
class Student {
    String name;
    int rollNumber;
    static String college;  // shared by ALL students

    static {
        college = "SRM";    // runs once when class loads
    }
}
```

> 🔑 **Key:** `college` is set once in a `static` block — not per object.  
> Access via `Student.college`, not via an instance.

---

### 2. Final Variable + Static Final — `Demo3.java`

```java
class Config {
    final double PI;            // instance final — set in constructor
    static final double E = 2.718;  // class-level constant (compile-time)

    Config() {
        this.PI = 3.14;         // ✅ OK — assigned exactly once
    }
}

// final local variable
final int x = 4;
x = 5;  // ❌ Compile error: cannot reassign final variable
```

---

### 3. String[] args — `Demo5.java`

```java
public static void main(String[] args) {
    // java Demo5 Rishon Rizon
    // args[0] = "Rishon", args[1] = "Rizon"
    for (int i = 0; i < args.length; i++) {
        System.out.println("Argument " + i + " = " + args[i]);
    }
}
```

---

## ❓ Critical Interview Questions

### Q1. What is the difference between `static` and `instance` variables?
| | Static Variable | Instance Variable |
|-|----------------|------------------|
| Belongs to | Class | Object |
| Shared? | ✅ Yes — all objects share same value | ❌ No — each object has its own |
| Memory | Method Area (Metaspace) | Heap |
| Access | `ClassName.variable` | `objectRef.variable` |

---

### Q2. Can a static method access instance variables?
> **No.** Static methods don't have access to `this`. They can only access static fields/methods.

```java
class Demo {
    int x = 5;
    static void print() {
        System.out.println(x); // ❌ Compile error — x is instance variable
    }
}
```

---

### Q3. What is a static block? When does it run?
> A `static { }` block runs **once**, when the class is first loaded by the JVM — before any constructor or instance creation.

**Use case:** Loading JDBC drivers, reading config, initializing constants.

```java
static {
    System.out.println("Class loaded!");  // runs before main()
}
```

---

### Q4. Can you override a static method?
> **No.** Static methods belong to the class, not the object. If a child class has the same static method, it's called **method hiding**, not overriding.

```java
class Parent {
    static void greet() { System.out.println("Parent"); }
}
class Child extends Parent {
    static void greet() { System.out.println("Child"); }  // hiding, not overriding
}
// Parent.greet() → "Parent"
// Child.greet()  → "Child"
// Parent p = new Child(); p.greet() → "Parent"  ← static binding!
```

---

### Q5. What is a `static final` variable?
> A **compile-time constant**. Belongs to the class, cannot be changed.

```java
static final double PI = 3.14159;  // constant — by convention ALL_CAPS
```
> 🔑 If it's a **primitive or String**, it becomes a **compile-time constant** and is inlined by the compiler.

---

### Q6. Can a `final` variable be uninitialized?
> Yes — if it's an **instance final** field, it must be initialized in the **constructor** (or inline). It cannot be left blank after that point.

```java
class Demo {
    final int x;          // blank final
    Demo(int x) { this.x = x; }  // ✅ initialized in constructor
}
```

---

### Q7. What is a `final` class? Give examples.
> A class that **cannot be extended**. Used for security and immutability.

Examples: `String`, `Integer`, `Double`, `Math`

```java
final class Immutable { ... }
class Child extends Immutable { }  // ❌ Compile error
```

---

## 📂 Files
| File | What it demonstrates |
|------|---------------------|
| `code/Demo.java` | Static variable, static block |
| `code/Demo3.java` | final instance variable, static final constant |
| `code/Demo5.java` | Command-line args with `String[] args` |
