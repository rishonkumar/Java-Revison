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
