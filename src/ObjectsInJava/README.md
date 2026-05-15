# Objects in Java

> Core topic: How Java stores objects, passes data, and how memory is managed — Heap vs Stack, Call by Value, Shallow vs Deep Copy.

---

## 📖 Theory

### What is an Object?
An object is an **instance of a class**. When you write `new ClassName()`, Java:
1. Allocates memory on the **Heap**
2. Calls the constructor
3. Returns a **reference** (pointer) stored on the **Stack**

```
Stack          Heap
------         --------
r1 ─────────→ [Random: x=4, y=5]
r3 ─────────↗  (same object!)
r2 ─────────→ [Random: x=4, y=5]  ← different object (deep copy)
```

### Heap vs Stack
| | Stack | Heap |
|-|-------|------|
| Stores | Primitives, references, method calls | Objects |
| Lifetime | Until method returns | Until GC collects |
| Speed | Faster | Slower |
| Size | Limited | Large |

---

## 🧪 Code Walkthroughs

### 1. Call by Value — `Demo.java`
Java is **strictly call-by-value** — even for objects, the *reference* is copied.

```java
// Primitives: changes inside method do NOT affect caller
static void addTen(int x, int y) {
    x = x + 10;  // modifies local copy only
    y = y + 10;
}
```
**Output:** `4 3` → `4 3` (unchanged — copy of value passed)

---

### 2. Shallow Copy vs Deep Copy — `Demo2.java`

```java
Random r1 = new Random(4, 5);
Random r2 = new Random(r1);  // Deep copy  → NEW object, same values
Random r3 = r1;              // Shallow copy → same reference!

addTen(r1);  // modifies r1's x and y in Heap
// r3 also sees the change! r2 does NOT.
```

| Copy Type | New Object? | Independent? |
|-----------|-------------|--------------|
| Shallow (`r3 = r1`) | ❌ No | ❌ No — changes affect both |
| Deep (`new Random(r1)`) | ✅ Yes | ✅ Yes — independent |

---

## ❓ Critical Interview Questions

### Q1. Is Java pass-by-value or pass-by-reference?
> **Always pass-by-value.** For objects, the *reference (address)* is passed by value. You can mutate object state through the reference, but you cannot reassign the reference itself in the caller.

```java
void change(Random r) {
    r.x = 99;           // ✅ mutates the original object (visible to caller)
    r = new Random(0,0); // ❌ does NOT affect caller's variable
}
```

---

### Q2. What is the difference between `==` and `.equals()` for objects?
```java
String a = new String("hello");
String b = new String("hello");

a == b       // false → compares references (different heap addresses)
a.equals(b)  // true  → compares content (overridden in String)
```
> **Rule:** Use `==` for primitives and reference equality. Use `.equals()` for logical/content equality.

---

### Q3. What happens in memory when you do `String s = "hello"` vs `new String("hello")`?
| | `"hello"` (literal) | `new String("hello")` |
|-|---------------------|----------------------|
| Location | **String Pool** (Heap) | Regular Heap |
| Reused? | ✅ Yes (interned) | ❌ No, always new object |
| `==` with same literal | `true` | `false` |

---

### Q4. What is the `this` keyword?
- Refers to the **current object instance**
- Used to distinguish between field and parameter with same name
- Can be used to call another constructor: `this(args)`

---

### Q5. What is a constructor? Can it be private?
- Special method called when object is created
- No return type (not even `void`)
- **Yes**, it can be private → used in **Singleton pattern**

```java
class Singleton {
    private static Singleton instance;
    private Singleton() {}  // private constructor
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }
}
```

---

### Q6. What is the difference between a shallow copy and a deep copy?
| | Shallow Copy | Deep Copy |
|-|-------------|-----------|
| New object? | ✅ Yes | ✅ Yes |
| Nested objects | ❌ Shared | ✅ Cloned recursively |
| Example | `r3 = r1` (just reference) / `Object.clone()` (default) | Copy constructor / manual copy |

---

## 📂 Files
| File | What it demonstrates |
|------|---------------------|
| `code/Demo.java` | Call by value with primitives |
| `code/Demo2.java` | Shallow copy vs Deep copy with objects |


---

## 💻 Full Source Code

> Below is the complete, beautified source code for all examples in this topic.

### code/Demo.java

```java
package ObjectsInJava.code;

/*
 * ==========================================
 *        CALL BY VALUE IN JAVA
 * ==========================================
 * 
 *  main() Stack Frame         addTen() Stack Frame
 *  +--------------+           +--------------+
 *  | x = 4        | --copy--> | x = 4 -> 14  | (local copy modified)
 *  | y = 3        | --copy--> | y = 3 -> 13  | (local copy modified)
 *  +--------------+           +--------------+
 * 
 * CONCLUSION: 
 * Modifying primitive parameters inside a method 
 * does NOT affect the original variables in the caller.
 */
public class Demo {

    static void main() {
        int x = 4;
        int y = 3;

        System.out.println("Before: " + x + " " + y);

        addTen(x, y);

        // Values remain 4 and 3 because only a copy was passed
        System.out.println("After: " + x + " " + y);
    }

    static void addTen(int x, int y) {
        x = x + 10;
        y = y + 10;
    }
}

```

### code/Demo2.java

```java
package ObjectsInJava.code;

/*
 * ==========================================
 *     SHALLOW COPY VS DEEP COPY
 * ==========================================
 * 
 *      STACK                   HEAP
 *  +-----------+           +------------------+
 *  | r1 (Ref)  | --------> | Random(x=4, y=5) | <--- Original Object
 *  +-----------+    +----> +------------------+
 *                   |      
 *  +-----------+    |      +------------------+
 *  | r3 (Ref)  | ---+      | Random(x=4, y=5) | <--- DEEP COPY (New Object)
 *  +-----------+           +------------------+
 *  (Shallow copy)                   ^
 *                                   |
 *  +-----------+                    |
 *  | r2 (Ref)  | -------------------+
 *  +-----------+
 * 
 * NOTE: Java is ALWAYS Pass-By-Value. When passing objects to methods, 
 * the REFERENCE is passed by value (copied).
 */
public class Demo2 {

    static void main() {
        Random r1 = new Random(4,5);
        Random r2 = new Random(r1); // Deep copy (new object in heap)
        Random r3 = r1;             // Shallow copy (same heap reference)

        System.out.println("Original r1: " + r1.x + " , " + r1.y);
        System.out.println("Deep Cpy r2: " + r2.x + " , " + r2.y);
        System.out.println("Shallow  r3: " + r3.x + " , " + r3.y);

        // This modifies the object that r1 points to in the Heap
        addTen(r1);

        System.out.println("\n--- After addTen(r1) ---");
        System.out.println("Original r1: " + r1.x + " , " + r1.y); // Modified!
        System.out.println("Deep Cpy r2: " + r2.x + " , " + r2.y); // Unchanged!
        System.out.println("Shallow  r3: " + r3.x + " , " + r3.y); // Modified! (Points to same obj)
    }

    static void addTen(Random r) {
        // 'r' is a copy of the reference 'r1', but they point to the same object
        r.x = r.x + 10;
        r.y = r.y + 10;
    }
}

class Random {
    int x;
    int y;

    // Normal Constructor
    Random(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Copy Constructor (For Deep Copy)
    Random(Random r) {
        this.x = r.x;
        this.y = r.y;
    }
}

```

