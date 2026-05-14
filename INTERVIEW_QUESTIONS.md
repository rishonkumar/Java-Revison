# 🎯 Java Interview Questions — Master Sheet

> All critical interview questions from every topic covered, in one place. Sorted by topic. Edge cases & tricky questions highlighted with ⚡.

---

## 📌 Quick Navigation

| Topic | Questions |
|-------|-----------|
| [Objects in Java](#-objects-in-java) | Pass by value, ==, String pool, Singleton, Shallow/Deep copy |
| [Static & Final](#-static--final-keywords) | static vs instance, method hiding, blank final, static block |
| [Encapsulation & Inheritance](#-encapsulation--inheritance) | Overloading vs Overriding, Diamond problem, dynamic dispatch |
| [Comparator & Lambda Intro](#-comparator--lambda-intro) | Comparable vs Comparator, chaining, overflow trap |
| [Lambdas & Functional Interfaces](#-lambdas--functional-interfaces) | SAM, andThen vs compose, Consumer pipeline, Predicate POJO |
| [Multithreading](#-multithreading) | start() vs run(), race condition, deadlock, volatile, ExecutorService |

---

## 🧩 Objects in Java

### Q1. Is Java pass-by-value or pass-by-reference?
> **Always pass-by-value.** For objects, the *reference (address)* is passed by value. You can mutate object state through the reference, but you cannot reassign the reference itself in the caller.

```java
void change(MyObj r) {
    r.x = 99;            // ✅ mutates the original object (caller sees this)
    r = new MyObj(0);    // ❌ does NOT affect caller's variable — new local ref
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
> **Rule:** `==` for primitives and reference equality. `.equals()` for logical content equality.

---

### Q3. What happens in memory for `String s = "hello"` vs `new String("hello")`?
| | `"hello"` (literal) | `new String("hello")` |
|-|---------------------|----------------------|
| Location | **String Pool** (Heap) | Regular Heap |
| Reused? | ✅ Yes (interned) | ❌ No, always new object |
| `==` with same literal | `true` | `false` |

---

### Q4. What is the `this` keyword?
- Refers to the **current object instance**
- Distinguishes field from parameter with same name
- `this(args)` calls another constructor in the same class (constructor chaining)

---

### Q5. Can a constructor be private?
> Yes — used in the **Singleton pattern**.

```java
class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }
}
```

---

### Q6. What is the difference between shallow copy and deep copy?
| | Shallow Copy | Deep Copy |
|-|-------------|-----------|
| New object? | ✅ Yes | ✅ Yes |
| Nested objects | ❌ Shared (same reference) | ✅ Cloned recursively |
| Example | `r3 = r1` / `Object.clone()` | Copy constructor / manual clone |

---

### ⚡ Q7. TRICKY: What does `Object.clone()` do by default — shallow or deep?
> **Shallow.** `Object.clone()` creates a new object but copies field values as-is. For primitive fields this is fine, but for object fields it copies the **reference** — both objects point to the same nested object.

```java
class Person implements Cloneable {
    String name;
    Address address;  // reference type!

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();  // address field still points to SAME Address object
    }
}
// Fix: override clone() to also clone Address
```

---

## 🧩 Static & Final Keywords

### Q1. What is the difference between `static` and instance variables?
| | Static Variable | Instance Variable |
|-|----------------|------------------|
| Belongs to | Class | Object |
| Shared? | ✅ All objects share same value | ❌ Each object has its own |
| Memory | Method Area (Metaspace) | Heap |
| Access | `ClassName.variable` | `objectRef.variable` |

---

### Q2. Can a static method access instance variables?
> **No.** Static methods have no `this`. They can only access static fields/methods.

```java
class Demo {
    int x = 5;
    static void print() {
        System.out.println(x); // ❌ Compile error
    }
}
```

---

### Q3. What is a static block? When does it run?
> Runs **once**, when the class is first loaded by JVM — before any constructor or object creation.

**Use cases:** Loading JDBC drivers, reading config, initializing lookup maps.

```java
static {
    System.out.println("Class loaded!");  // runs before main()
}
```

---

### Q4. Can you override a static method?
> **No.** It's called **method hiding**, not overriding. Resolved at compile-time (static binding).

```java
class Parent { static void greet() { System.out.println("Parent"); } }
class Child extends Parent { static void greet() { System.out.println("Child"); } }

Parent p = new Child();
p.greet();  // "Parent" ← static binding — uses reference type, NOT actual object type
```

---

### Q5. What is a `static final` variable?
> A **compile-time constant**. Belongs to the class, cannot be changed.

```java
static final double PI = 3.14159;  // convention: ALL_CAPS
```
> If it's a **primitive or String**, the compiler inlines the value everywhere it's used.

---

### Q6. Can a `final` variable be uninitialized?
> Yes — a **blank final** instance field can be assigned in the constructor (exactly once).

```java
class Demo {
    final int x;
    Demo(int x) { this.x = x; }  // ✅ assigned in constructor
}
```

---

### ⚡ Q7. TRICKY: What is the order of execution when a class is loaded?
```
1. Static fields & static blocks (in order of appearance)
2. Instance fields (in order of appearance) — per object creation
3. Constructor body
```

```java
class Demo {
    static int a = 10;           // step 1
    static { System.out.println("Static block: " + a); }  // step 1

    int b = 20;                  // step 2 (per object)
    Demo() { System.out.println("Constructor: " + b); }   // step 3
}
```

---

## 🧩 Encapsulation & Inheritance

### Q1. What is Encapsulation and why is it important?
> Bundling data (fields) and methods into a single unit while **restricting direct access** to fields.

**Benefits:** Prevents invalid state, allows validation in setters, hides implementation details.

---

### Q2. What is the difference between method overloading and overriding?
| | Overloading | Overriding |
|-|-------------|------------|
| Where | Same class | Child class overrides parent method |
| Signature | **Different** parameters | **Same** signature |
| Binding | Compile-time (static) | Runtime (dynamic) |
| `@Override` | Not used | Recommended |

---

### Q3. Why doesn't Java support multiple inheritance with classes?
> **Diamond Problem** — ambiguous method resolution when two parents override the same method.

```
      A.method()
     /          \
B.method()   C.method()
     \          /
         D  ← which method()?
```
> Java solves this with **interfaces** (explicit override required when default methods conflict).

---

### Q4. What is the `super` keyword?
- `super.method()` → calls parent's version of overridden method
- `super(args)` → calls parent constructor (must be **first line** in child constructor)

---

### Q5. Can a constructor be inherited?
> **No.** Constructors are not inherited. But a child constructor implicitly calls `super()` (no-arg) unless you explicitly call `super(args)`.

---

### Q6. Access modifiers in inheritance?
| Modifier | Same Class | Same Package | Subclass | World |
|----------|-----------|--------------|---------|-------|
| `private` | ✅ | ❌ | ❌ | ❌ |
| `default` | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

---

### ⚡ Q7. TRICKY: What is the output of this code?

```java
class A {
    int x = 10;
    void show() { System.out.println("A: " + x); }
}
class B extends A {
    int x = 20;
    void show() { System.out.println("B: " + x); }
}
A obj = new B();
obj.show();          // "B: 20" → runtime dynamic dispatch (method)
System.out.println(obj.x);  // 10 → fields are NOT polymorphic! (compile-time)
```
> **Key insight:** Method calls → **runtime** (dynamic dispatch). Field access → **compile-time** (reference type wins).

---

## 🧩 Comparator & Lambda Intro

### Q1. What is the difference between `Comparable` and `Comparator`?
| | `Comparable<T>` | `Comparator<T>` |
|-|-----------------|-----------------|
| Package | `java.lang` | `java.util` |
| Method | `compareTo(T o)` | `compare(T o1, T o2)` |
| Where defined | **Inside** the class | **Outside** — separate class/lambda |
| Multiple orderings | ❌ One natural order | ✅ Yes |

---

### Q2. What is a Functional Interface?
> An interface with **exactly one abstract method** (SAM — Single Abstract Method). Can have unlimited `default` and `static` methods.

```java
@FunctionalInterface
interface Calculate {
    int calculate(int a, int b);  // only one abstract method allowed
}
```

---

### Q3. Can you chain Comparators?

```java
Comparator<Student> comp = Comparator.comparingInt((Student s) -> s.marks)
                                     .thenComparing(s -> s.name)
                                     .reversed();
list.sort(comp);
```

---

### ⚡ Q4. TRICKY: When does `s1.marks - s2.marks` fail as a comparator?
> **Integer overflow!** If `s1.marks = Integer.MAX_VALUE` and `s2.marks = -1`, subtraction overflows.

```java
// ❌ Dangerous
return s1.marks - s2.marks;

// ✅ Safe
return Integer.compare(s1.marks, s2.marks);
```

---

## 🧩 Lambdas & Functional Interfaces

### Q1. What is a Functional Interface? Can it have more than one method?
> Exactly **one abstract method** (SAM). Can have multiple `default` and `static` methods. `@FunctionalInterface` annotation causes compile-time error if you violate SAM.

---

### Q2. What is the difference between `andThen` and `compose` in `Function`?

```java
Function<Integer,Integer> f = x -> x + 2;
Function<Integer,Integer> g = x -> x * 3;

f.andThen(g).apply(4)  // f FIRST, then g: (4+2)*3 = 18  ← left to right
f.compose(g).apply(4)  // g FIRST, then f: (4*3)+2 = 14  ← right to left (math f∘g)
```

---

### Q3. Can a lambda expression throw a checked exception?
> Not directly if the functional interface's abstract method doesn't declare `throws`.

```java
// ❌ Won't compile — Function doesn't declare throws IOException
Function<String, String> f = s -> new String(s.getBytes("UTF-8"));

// ✅ Fix: wrap in try-catch OR use a custom functional interface
@FunctionalInterface
interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}
```

---

### Q4. What is the difference between `Consumer.andThen` vs `Function.andThen`?
| | `Function.andThen(f)` | `Consumer.andThen(c)` |
|-|----------------------|----------------------|
| Chains | Two functions (produces output) | Two consumers (side-effects only) |
| Result type | New `Function` | New `Consumer` |

```java
Consumer<String> upper = s -> System.out.println(s.toUpperCase());
Consumer<String> lower = s -> System.out.println(s.toLowerCase());
upper.andThen(lower).accept("Hello");
// HELLO
// hello
```

---

### Q5. Can all lambda expressions be replaced by method references?
> **No.** Method references only work when the lambda does nothing but call a **single existing method** and the parameters map directly.

```java
x -> x * x            // ❌ No method reference (custom logic)
x -> Math.abs(x)      // ✅ → Math::abs
(a, b) -> a + b       // ❌ No method reference (operator)
```

---

### Q6. What are primitive functional interfaces and why do they exist?
> To avoid **autoboxing overhead** (`int` → `Integer` conversion has cost).

```java
IntFunction<Integer> f     = x -> x * x;      // int → R
ToIntFunction<String> len  = String::length;   // T → int
IntUnaryOperator abs       = Math::abs;        // int → int
IntBinaryOperator add      = (a, b) -> a + b;  // int, int → int
```

---

### ⚡ Q7. TRICKY: `Consumer.andThen()` — what if the second consumer throws?
> The **first consumer already ran** — its side-effects are committed. The exception from the second consumer propagates up.

```java
Consumer<String> log   = s -> System.out.println("[LOG] " + s);
Consumer<String> risky = s -> { if (s == null) throw new NullPointerException(); };
log.andThen(risky).accept(null);
// [LOG] null   ← first ran
// NullPointerException ← second throws
```

---

### ⚡ Q8. TRICKY: How do you use Predicate with a custom class?

```java
class Student { int mark; int age; }

Predicate<Student> passed    = s -> s.mark >= 40;
Predicate<Student> isAdult   = s -> s.age  >= 18;
Predicate<Student> isEligible = passed.and(isAdult);

students.stream()
        .filter(isEligible)
        .forEach(s -> System.out.println("Eligible: mark=" + s.mark));
```
> **Pattern:** Build fine-grained predicates → compose with `and()` / `or()`. Far more testable than one big `if`.

---

## 🧩 Multithreading

### Q1. What is the difference between `start()` and `run()`?

```java
Thread t = new Thread(() -> System.out.println("Hello"));
t.run();   // ❌ Runs on CURRENT thread — just a regular method call
t.start(); // ✅ Creates NEW thread and calls run() on that thread
```

---

### Q2. What is a race condition? How do you fix it?
> Multiple threads access shared mutable data simultaneously — result depends on execution order.

**Fixes:**
1. `synchronized` keyword
2. `ReentrantLock` (from `java.util.concurrent.locks`)
3. `AtomicInteger` / `AtomicLong`
4. Thread-safe collections (`ConcurrentHashMap`, `CopyOnWriteArrayList`)

---

### Q3. `synchronized` method vs `synchronized` block?

```java
// Method — locks entire object
synchronized void increment() { count++; }

// Block — locks only critical section (prefer this)
void increment() {
    synchronized(this) { count++; }
}
```
> **Prefer blocks** — less contention, better throughput.

---

### Q4. What is a deadlock?
> Two threads each hold a lock the other needs → both wait forever.

```
Thread 1: holds Lock A, wants Lock B
Thread 2: holds Lock B, wants Lock A  → DEADLOCK
```

**Prevention:** Always acquire locks in the **same order**. Use `tryLock()` with timeout.

---

### Q5. What is the difference between `wait()` and `sleep()`?
| | `wait()` | `sleep()` |
|-|---------|-----------| 
| Defined in | `Object` | `Thread` |
| Releases lock? | ✅ Yes | ❌ No |
| Wakes up when | `notify()` / `notifyAll()` | Timer expires |
| Must be in `synchronized`? | ✅ Yes | ❌ No |

---

### Q6. What is a daemon thread?
> Runs in the background — JVM exits when only daemon threads remain.

```java
Thread t = new Thread(() -> { /* background task */ });
t.setDaemon(true);  // must be set BEFORE start()
t.start();
```
Examples: GC thread, file watchers, timer threads.

---

### Q7. What is the `volatile` keyword?
> Ensures a variable is always **read from main memory**, not a thread's local cache. Solves **visibility**, not **atomicity**.

```java
volatile boolean running = true;
// Thread 1: while (running) { ... }
// Thread 2: running = false;  // Thread 1 sees this change immediately with volatile
```

---

### ⚡ Q8. TRICKY: What happens if you call `start()` twice on the same Thread?

```java
Thread t = new Thread(() -> System.out.println("Hello"));
t.start();
t.start();  // ❌ IllegalThreadStateException!
```
> Once TERMINATED, a Thread **cannot be restarted**. Create a new Thread instance.

---

### ⚡ Q9. TRICKY: `volatile` vs `synchronized` vs `AtomicInteger`?

| | `volatile` | `synchronized` | `AtomicInteger` |
|-|------------|----------------|-----------------|
| Visibility | ✅ Yes | ✅ Yes | ✅ Yes |
| Atomicity | ❌ No | ✅ Yes | ✅ Yes (CAS) |
| Mutual Exclusion | ❌ No | ✅ Yes | ❌ No |
| Performance | Fastest | Slowest (blocks) | Fast (lock-free CAS) |

> Use `volatile` for simple flags. Use `AtomicInteger` for counters. Use `synchronized` when you need mutual exclusion across multiple statements.

---

### Q10. What is `ExecutorService` and why use it over raw threads?

```java
ExecutorService pool = Executors.newFixedThreadPool(5);  // reuse 5 threads
pool.submit(() -> System.out.println("Task 1"));
pool.submit(() -> System.out.println("Task 2"));
pool.shutdown();   // waits for all tasks to complete
// pool.shutdownNow()  // interrupts running tasks immediately
```

| Pool Type | Description |
|-----------|-------------|
| `newFixedThreadPool(n)` | Fixed n threads |
| `newCachedThreadPool()` | Grows as needed, reuses idle |
| `newSingleThreadExecutor()` | One thread, sequential |
| `newScheduledThreadPool(n)` | Delayed / periodic tasks |

---

## ⚡ Bonus Edge Case Questions

### B1. What is the output?
```java
public class Test {
    static int x = 10;
    public static void main(String[] args) {
        int x = 20;
        System.out.println(x);        // 20  (local variable shadows static)
        System.out.println(Test.x);   // 10  (explicit class reference)
    }
}
```

---

### B2. Can you use `this` in a static method?
> **No.** `this` refers to the current object instance. Static methods have no instance.

---

### B3. What is the difference between `String`, `StringBuilder`, and `StringBuffer`?
| | `String` | `StringBuilder` | `StringBuffer` |
|-|----------|-----------------|----------------|
| Mutable? | ❌ Immutable | ✅ Mutable | ✅ Mutable |
| Thread-safe? | ✅ Yes | ❌ No | ✅ Yes (synchronized) |
| Performance | Slowest for concat | Fastest | Slower than StringBuilder |

> Use `StringBuilder` in single-threaded code. `StringBuffer` if shared across threads.

---

### B4. What is autoboxing and unboxing?
```java
Integer a = 5;    // autoboxing:   int → Integer (JVM calls Integer.valueOf(5))
int b = a;        // unboxing:     Integer → int (JVM calls a.intValue())

// ⚠️ Danger:
Integer x = null;
int y = x;  // ❌ NullPointerException during unboxing!
```

---

### B5. `==` on Integer objects — the cache trap
```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b);  // true  ← cached (Integer pool: -128 to 127)

Integer c = 128;
Integer d = 128;
System.out.println(c == d);  // false ← outside cache range, different objects
```
> Always use `.equals()` to compare `Integer` objects!

---

*Last updated: May 2026 | Topics: Objects, Static/Final, Encapsulation/Inheritance, Comparator, Lambdas/FI, Multithreading*
