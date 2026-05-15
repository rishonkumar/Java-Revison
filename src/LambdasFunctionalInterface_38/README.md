# Lambdas & Functional Interfaces

> Java 8's most impactful feature. Master `Function`, `Consumer`, `Supplier`, `Predicate`, method references, and functional composition.

---

## 📖 Theory

### What is a Lambda Expression?
A **lambda** is a concise way to represent an anonymous function — used wherever a **functional interface** is expected.

```java
// Before Java 8 — anonymous class
Runnable r = new Runnable() {
    public void run() { System.out.println("Hello"); }
};

// Java 8 Lambda
Runnable r = () -> System.out.println("Hello");
```

### The 4 Core Functional Interfaces

| Interface | Signature | Abstract Method | Use Case |
|-----------|-----------|-----------------|---------|
| `Function<T,R>` | `T → R` | `R apply(T t)` | Transform/map a value |
| `Consumer<T>` | `T → void` | `void accept(T t)` | Use value, no return |
| `Supplier<T>` | `() → T` | `T get()` | Provide/generate a value |
| `Predicate<T>` | `T → boolean` | `boolean test(T t)` | Test a condition |

---

## 🧪 Code Walkthroughs

### 1. Custom Functional Interface — `Demo.java`

```java
@FunctionalInterface
interface Calculate {
    int calculate(int a, int b);  // ONE abstract method
}

// Usage
print(4, 5, (a, b) -> a + b);   // lambda for addition
print(4, 5, (a, b) -> a * b);   // lambda for multiplication

static void print(int a, int b, Calculate c) {
    System.out.println(c.calculate(a, b));
}
```

---

### 2. Function<T,R> — `FunctionExample.java`

```java
Function<Integer, Integer> square = x -> x * x;
System.out.println(square.apply(5));  // 25

// BiFunction — two inputs
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
System.out.println(add.apply(3, 4));  // 7
```

---

### 3. Consumer<T> — `ConsumerExample.java`

```java
Consumer<Integer> print = x -> System.out.println("Value: " + x);
print.accept(6);  // Value: 6

// forEach uses Consumer internally
List<Integer> list = List.of(1, 2, 3, 4, 5);
list.forEach(x -> System.out.print(x * x + " "));  // 1 4 9 16 25

// Method reference shorthand
list.forEach(System.out::println);

// andThen — chain two consumers (both execute, one after another)
Consumer<String> log   = s -> System.out.println("[LOG] " + s);
Consumer<String> audit = s -> System.out.println("[AUDIT] " + s);
log.andThen(audit).accept("User login");
// [LOG] User login
// [AUDIT] User login

// BiConsumer — two inputs, no return
BiConsumer<String, Integer> printPair = (name, age) ->
    System.out.println(name + " is " + age + " years old");
printPair.accept("Rishon", 22);  // Rishon is 22 years old

// BiConsumer with Map.forEach
Map<String, Integer> scores = Map.of("Alice", 95, "Bob", 87);
scores.forEach((name, score) -> System.out.println(name + " → " + score));

// Consumer pipeline — chain print + uppercase
Consumer<String> printName  = System.out::println;
Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());
Consumer<String> pipeline    = printName.andThen(printUpper);
pipeline.accept("Rishon");
// Rishon
// RISHON
```

---

### 4. Supplier<T> — `SupplierExample.java`

```java
Supplier<Double> random = () -> Math.random();
System.out.println(random.get());  // some random double

// Common use: lazy initialization / factory methods
Supplier<List<String>> listFactory = ArrayList::new;
List<String> list = listFactory.get();  // creates new list
```

---

### 5. Predicate<T> — `PredicateExample.java`

```java
Predicate<Integer> isEven = x -> x % 2 == 0;
System.out.println(isEven.test(4));   // true
System.out.println(isEven.test(7));   // false

// ── Combining predicates ───────────────────────────────────────────
Predicate<Integer> isPositive = x -> x > 0;
Predicate<Integer> isLarge    = x -> x > 100;

Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
Predicate<Integer> isOdd             = isEven.negate();
Predicate<Integer> isEvenOrLarge     = isEven.or(isLarge);

System.out.println(isEvenAndPositive.test(4));   // true
System.out.println(isEvenAndPositive.test(-4));  // false (not positive)
System.out.println(isOdd.test(3));               // true
System.out.println(isEvenOrLarge.test(101));     // true (large)
System.out.println(isEvenOrLarge.test(3));       // false

// ── Predicate chaining shorthand ──────────────────────────────────
Predicate<Integer> isGreater = x -> x > 100;
Predicate<Integer> isEven2   = x -> x % 2 == 0;

isGreater.and(isEven2).test(102)  // true  (>100 AND even)
isGreater.or(isEven2).test(102)   // true  (OR condition)
isEven2.negate().test(111)        // true  (odd)

// ── Predicate with Stream — filter ────────────────────────────────
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> evenNumbers = numbers.stream()
                                   .filter(isEven)
                                   .collect(Collectors.toList());
System.out.println("Even: " + evenNumbers);  // [2, 4, 6, 8, 10]

List<Integer> oddNumbers = numbers.stream()
                                  .filter(isEven.negate())
                                  .collect(Collectors.toList());
System.out.println("Odd: " + oddNumbers);    // [1, 3, 5, 7, 9]

// ── Real-world: custom class + predicate composition ─────────────
// class Student { int mark; int age; }
Predicate<Student> passed    = s -> s.mark >= 40;
Predicate<Student> isAdult   = s -> s.age  >= 18;
Predicate<Student> isEligible = passed.and(isAdult);

System.out.println(isEligible.test(new Student(50, 19)));  // true
System.out.println(isEligible.test(new Student(30, 20)));  // false (mark < 40)
```

> **Java 11+:** `Predicate.not(String::isEmpty)` is a clean static alternative to `.negate()`.

---

### 6. Functional Composition — `FunctionalComposition.java`

```java
Function<Integer, Integer> add2 = x -> x + 2;
Function<Integer, Integer> multiply3 = x -> x * 3;

// andThen: apply add2 FIRST, then multiply3
int result = add2.andThen(multiply3).apply(2);  // (2+2)*3 = 12

// compose: apply multiply3 FIRST, then add2
int result2 = add2.compose(multiply3).apply(2);  // (2*3)+2 = 8
```

> **Memory trick:** `andThen` = left-to-right (like reading). `compose` = right-to-left (like math f(g(x))).

---

### 7. Method References — `MethodReferenceExample.java`

```java
// Lambda form → Method Reference
x -> System.out.println(x)    →  System.out::println     // Instance method ref
x -> Math.abs(x)              →  Math::abs               // Static method ref
str -> str.isEmpty()          →  String::isEmpty         // Instance method (unbound)
() -> new ArrayList<>()       →  ArrayList::new          // Constructor ref
```

| Type | Example |
|------|---------|
| Static method | `Math::abs` |
| Instance method (object) | `System.out::println` |
| Instance method (type) | `String::toLowerCase` |
| Constructor | `ArrayList::new` |

---

## ❓ Critical Interview Questions

### Q1. What is a Functional Interface? Can it have more than one method?
> An interface with **exactly one abstract method** (SAM — Single Abstract Method interface).  
> It **can** have multiple `default` and `static` methods.  
> The `@FunctionalInterface` annotation is optional but recommended — it causes a compile-time error if you violate the SAM rule.

---

### Q2. What is the difference between `andThen` and `compose` in `Function`?

```java
Function<Integer,Integer> f = x -> x + 2;
Function<Integer,Integer> g = x -> x * 3;

f.andThen(g).apply(4)  // f first, then g: (4+2)*3 = 18
f.compose(g).apply(4)  // g first, then f: (4*3)+2 = 14
```

---

### Q3. Can a lambda expression throw a checked exception?
> Not directly if the functional interface's abstract method doesn't declare `throws`.

```java
// This won't compile with Function<String, String>
Function<String, String> f = s -> new String(s.getBytes("UTF-8"));  // ❌

// Fix: wrap in try-catch or create a custom functional interface
@FunctionalInterface
interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}
```

---

### Q4. What is the difference between `Predicate.and()`, `Predicate.or()`, and `Predicate.negate()`?

```java
Predicate<Integer> isEven = x -> x % 2 == 0;
Predicate<Integer> isPos  = x -> x > 0;

isEven.and(isPos).test(4)    // true  (AND)
isEven.or(isPos).test(3)     // true  (OR — 3 is positive)
isEven.negate().test(3)      // true  (NOT even = odd)
```

---

### Q5. TRICKY: What is the difference between `Consumer.andThen` vs `Function.andThen`?
| | `Function.andThen(f)` | `Consumer.andThen(c)` |
|-|----------------------|----------------------|
| Chains | Two functions producing output | Two consumers (both just consume) |
| Result | New Function | New Consumer |

```java
Consumer<String> upper = s -> System.out.println(s.toUpperCase());
Consumer<String> lower = s -> System.out.println(s.toLowerCase());
upper.andThen(lower).accept("Hello");
// prints: HELLO
//         hello
```

---

### Q6. Can all lambda expressions be replaced by method references?
> **No.** Method references only work when:
> - The lambda does nothing except call a single existing method
> - The parameters map directly to the method arguments

```java
x -> x * x            // ❌ No method reference (custom logic)
x -> Math.abs(x)      // ✅ → Math::abs
(a, b) -> a + b       // ❌ No method reference (operator)
```

---

### Q7. What are primitive functional interfaces and why do they exist?
> To avoid **autoboxing overhead** when working with primitives.

```java
// Instead of Function<Integer, Integer> (boxes int → Integer)
IntFunction<Integer> f = x -> x * x;     // int → R
ToIntFunction<String> len = String::length;  // T → int
IntUnaryOperator abs = Math::abs;            // int → int
IntBinaryOperator add = (a, b) -> a + b;     // int, int → int
```

---

### Q8. TRICKY: How does `Consumer.andThen()` behave if the second consumer throws an exception?
> The first consumer runs to completion. If the second throws an unchecked exception, it propagates — the first consumer's side-effects are **already committed** (not rolled back).

```java
Consumer<String> log   = s -> System.out.println("[LOG] " + s);
Consumer<String> risky = s -> { if (s == null) throw new NullPointerException(); };
log.andThen(risky).accept(null);
// [LOG] null  ← first ran
// NullPointerException ← second throws
```

---

### Q9. How would you use `Predicate` with a custom class to filter eligible candidates?

```java
class Student { int mark; int age; }

Predicate<Student> passed    = s -> s.mark >= 40;
Predicate<Student> isAdult   = s -> s.age  >= 18;
Predicate<Student> isEligible = passed.and(isAdult);

List<Student> students = List.of(
    new Student(50, 19),   // eligible
    new Student(35, 20),   // failed (mark < 40)
    new Student(60, 17)    // underage
);

students.stream()
        .filter(isEligible)
        .forEach(s -> System.out.println("Eligible: mark=" + s.mark + " age=" + s.age));
// Eligible: mark=50 age=19
```

> **Pattern:** Build fine-grained predicates for each rule → compose them with `and()` / `or()`. This is far more readable and testable than a giant `if` statement.

---

## 📂 Files
| File | What it demonstrates |
|------|---------------------|
| `Demo.java` | Custom `@FunctionalInterface` + lambda basics |
| `FunctionExample.java` | `Function<T,R>` — apply(), BiFunction |
| `ConsumerExample.java` | `Consumer<T>` — accept(), forEach(), andThen() pipeline, BiConsumer with Map |
| `SupplierExample.java` | `Supplier<T>` — get(), lazy init, factory |
| `PredicateExample.java` | `Predicate<T>` — test(), and/or/negate, Stream.filter, Student eligibility |
| `FunctionalComposition.java` | `andThen()` vs `compose()` |
| `MethodReferenceExample.java` | All 4 types of method references |


---

## 💻 Full Source Code

> Below is the complete, beautified source code for all examples in this topic.

### ConsumerExample.java

```java
package LambdasFunctionalInterface_38;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * ==========================================
 *        CONSUMER INTERFACE & CHAINING
 * ==========================================
 * 
 * Consumer<T> — T → void
 * Abstract method: void accept(T t)
 *
 * BiConsumer<T,U> — (T, U) → void
 * Abstract method: void accept(T t, U u)
 *
 * CONSUMER CHAINING (.andThen):
 * 
 *     Input "Hello"
 *          |
 *          v
 *   +-------------+
 *   | Consumer 1  | (e.g., Print original)
 *   +-------------+
 *          | (side-effect happens)
 *          v
 *   +-------------+
 *   | Consumer 2  | (e.g., Print Uppercase)
 *   +-------------+
 *          | (side-effect happens)
 *          v
 *       (void)
 * 
 * Key use: forEach loops, logging, validation, side effects
 */
public class ConsumerExample {

    public static void main(String[] args) {

        // ── Basic Consumer ─────────────────────────────────────────────────
        Consumer<Integer> print = x -> System.out.println("Value: " + x);
        print.accept(6);  // Value: 6

        Consumer<String> shout = s -> System.out.println(s.toUpperCase() + "!");
        shout.accept("hello");  // HELLO!

        // ── forEach — internally uses Consumer ────────────────────────────
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        // Old way:
        System.out.print("Old loop: ");
        for (Integer i : list) { System.out.print(i + " "); }
        System.out.println();

        // Lambda way:
        System.out.print("Lambda loop: ");
        list.forEach(x -> System.out.print(x * x + " "));  // 1 4 9 16 25
        System.out.println();

        // Method reference way:
        list.forEach(System.out::println);

        // ── Consumer andThen — chain two consumers ────────────────────────
        Consumer<String> log    = s -> System.out.println("[LOG] " + s);
        Consumer<String> audit  = s -> System.out.println("[AUDIT] " + s);

        Consumer<String> logAndAudit = log.andThen(audit);
        logAndAudit.accept("User login");
        // [LOG] User login
        // [AUDIT] User login

        // ── BiConsumer — two inputs, no return ────────────────────────────
        BiConsumer<String, Integer> printPair = (name, age) ->
            System.out.println(name + " is " + age + " years old");
        printPair.accept("Rishon", 22);  // Rishon is 22 years old

        // BiConsumer with Map.forEach
        Map<String, Integer> scores = Map.of("Alice", 95, "Bob", 87, "Charlie", 92);
        scores.forEach((name, score) ->
            System.out.println(name + " → " + score));

        // ── Consumer chaining ─────────────────────────────────────────────
        Consumer<String> printName = System.out::println;
        Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());

        Consumer<String> pipeline = printName.andThen(printUpperCase);
        pipeline.accept("Rishon");
    }
}

```

### Demo.java

```java
package LambdasFunctionalInterface_38;

public class Demo {

    static void main() {

//        Calculate c = new Addition();
//        print(4,5,c);

        //Instead of writing like this we can do lamda expression
        print(4,5,(a,b) -> a+b);

//        Calculate c = (a,b) -> a + b;
//        print(4,5,c);
    }

    static void print(int a, int b, Calculate c) {
        System.out.println(c.calulate(a,b));
    }



}

@FunctionalInterface
interface Calculate {
    int calulate(int a, int b);
}


//class Addition implements Calculate {
//
//    @Override
//    public int calulate(int a, int b) {
//        return a+b;
//    }
//}

/*
Lamdab expression
4 core Funcitonal interface

    Function
    Consumer
    Supplier
    Predicate

    Function Interface -> Take i/p -> o/p
    interfae Function(T,R>) {
        R apply(T t)
    }

    Consumer
    It wil take T but it will not give any output
    T -> void
    Public interface Consumer<T> {
        void accept(T t)
    }

    Suplier -> opposite of COnsumer
    Supplier<T> no input but gives some output

    pulic interface Suplier<T> {
            T.get();
    }

    Predicate Takes input and returns boolean

    public interface Predicate<T> {

            boolean test(T t)

    }


    Primitive Functional Interface

        (T -> R)

    IntFunction )int -> R) R Apply()

    Long function (long -> R)

    Double Function


    ToIntFunction<T>
    ToDoubleFunction<T>
    ToLongFunction<T>

    Primitive Consumer Faimly

    IntCousmer ->(int -> Void) .. same for double and long

    ObJIntCousmer (T,int) -> void


    Primitive Supplier
        void -> T

       IntSupplier (void -> int) Same for long and double


      Primitive perdicate

        (T -> booleans)

       IntPredicate (int -> boolean) same for long and double


      Primitive operator family
        (int -> int)

        IntUnaryOperator same for long and double

       IntBinary operator (int,int) -> int





*/
```

### FunctionExample.java

```java
package LambdasFunctionalInterface_38;

import java.util.function.Function;
import java.util.function.BiFunction;

/*
 * Function<T,R> — T → R
 * Abstract method: R apply(T t)
 *
 * BiFunction<T,U,R> — (T,U) → R
 *
 * Composition:
 *   andThen(f) → apply this, THEN f
 *   compose(f) → apply f FIRST, then this
 */
public class FunctionExample {

    public static void main(String[] args) {

        // ── Basic Function ─────────────────────────────────────────────────
        Function<Integer, Integer> square = x -> x * x;
        System.out.println(square.apply(5));  // 25

        Function<String, Integer> length = String::length;
        System.out.println(length.apply("Hello"));  // 5

        Function<String, String> toUpper = String::toUpperCase;
        System.out.println(toUpper.apply("hello"));  // HELLO

        // ── BiFunction — two inputs ────────────────────────────────────────
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(3, 4));  // 7

        BiFunction<String, String, String> fullName = (f, l) -> f + " " + l;
        System.out.println(fullName.apply("Rishon", "Kumar"));  // Rishon Kumar

        // ── Function composition ───────────────────────────────────────────
        Function<Integer, Integer> doubleIt  = x -> x * 2;
        Function<Integer, String>  toDisplay = x -> "Score: " + x;

        // andThen: double first, then convert to string
        Function<Integer, String> pipeline = doubleIt.andThen(toDisplay);
        System.out.println(pipeline.apply(10));  // Score: 20

        // ── Returning Function from Function ───────────────────────────────
        // Higher-order function: a function that returns another function
        Function<Integer, Function<Integer, Integer>> multiplier = x -> (y -> x * y);
        Function<Integer, Integer> triple = multiplier.apply(3);
        System.out.println(triple.apply(5));   // 15
        System.out.println(triple.apply(10));  // 30
    }
}

```

### FunctionalComposition.java

```java
package LambdasFunctionalInterface_38;

import java.util.function.Function;

/*
 * Functional Composition
 *
 * Math: f(x) = x + 2,  g(x) = x * 3
 *
 * andThen : f.andThen(g) → apply f FIRST, then g   → g(f(x))
 * compose : f.compose(g) → apply g FIRST, then f   → f(g(x))
 *
 * Memory trick: andThen = left-to-right (like reading a sentence)
 *               compose = right-to-left (like math function notation)
 */
public class FunctionalComposition {

    public static void main(String[] args) {

        Function<Integer, Integer> add2      = x -> x + 2;
        Function<Integer, Integer> multiply3 = x -> x * 3;

        // ── Direct composition ─────────────────────────────────────────────
        // (2 + 2) * 3 = 12
        int manual = multiply3.apply(add2.apply(2));
        System.out.println("Manual compose: " + manual);  // 12

        // ── andThen: add2 first, THEN multiply3 ───────────────────────────
        // (2 + 2) * 3 = 12
        int andThenResult = add2.andThen(multiply3).apply(2);
        System.out.println("andThen (add first, multiply second): " + andThenResult);  // 12

        // ── compose: multiply3 first, THEN add2 ───────────────────────────
        // (2 * 3) + 2 = 8
        int composeResult = add2.compose(multiply3).apply(2);
        System.out.println("compose (multiply first, add second): " + composeResult);  // 8

        // ── Chaining multiple andThen ──────────────────────────────────────
        Function<Integer, String> pipeline =
            add2.andThen(multiply3)
                .andThen(x -> x - 1)
                .andThen(x -> "Result: " + x);

        System.out.println(pipeline.apply(2));  // Result: 11  → (2+2)*3 - 1 = 11

        // ── Real-world use case: data transformation pipeline ──────────────
        Function<String, String> trim    = String::trim;
        Function<String, String> toLower = String::toLowerCase;
        Function<String, String> addHash = s -> "#" + s;

        Function<String, String> normalizeTag = trim.andThen(toLower).andThen(addHash);
        System.out.println(normalizeTag.apply("  Java  "));  // #java
        System.out.println(normalizeTag.apply(" SPRING "));  // #spring
    }
}

```

### MethodReferenceExample.java

```java
package LambdasFunctionalInterface_38;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * Method References — shorthand for lambdas that ONLY call an existing method
 *
 * Syntax: ClassName::methodName  OR  instance::methodName
 *
 * 4 Types:
 *   1. Static method         : ClassName::staticMethod      (Math::abs)
 *   2. Instance method (obj) : instance::method             (System.out::println)
 *   3. Instance method (type): ClassName::instanceMethod    (String::toLowerCase)
 *   4. Constructor           : ClassName::new               (ArrayList::new)
 *
 * When can you use method reference?
 *   ONLY when the lambda does NOTHING except call a single existing method
 *   and the arguments map directly to that method's parameters.
 */
public class MethodReferenceExample {

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, -4, -5));
        List<String> names = List.of("Alice", "Bob", "Charlie", "Dave");

        // ── Type 1: Static Method Reference ───────────────────────────────
        // Lambda:           x -> Math.abs(x)
        // Method reference: Math::abs
        numbers.stream()
               .map(Math::abs)          // same as x -> Math.abs(x)
               .forEach(System.out::println);

        // Lambda:           x -> Integer.parseInt(x)
        // Method reference: Integer::parseInt
        List<String> numStrings = List.of("1", "2", "3");
        numStrings.stream()
                  .map(Integer::parseInt)   // same as s -> Integer.parseInt(s)
                  .forEach(System.out::println);

        // ── Type 2: Instance Method Reference (specific object) ───────────
        // Lambda:           x -> System.out.println(x)
        // Method reference: System.out::println
        names.forEach(System.out::println);  // System.out is a specific instance

        // ── Type 3: Instance Method Reference (arbitrary instance) ────────
        // Lambda:           s -> s.toLowerCase()
        // Method reference: String::toLowerCase
        names.stream()
             .map(String::toLowerCase)    // same as s -> s.toLowerCase()
             .forEach(System.out::println);

        // Lambda:           s -> s.isEmpty()
        // Method reference: String::isEmpty
        Predicate<String> isEmpty = String::isEmpty;
        System.out.println(isEmpty.test(""));       // true
        System.out.println(isEmpty.test("hello"));  // false

        // ── Type 4: Constructor Reference ─────────────────────────────────
        // Lambda:           () -> new ArrayList<String>()
        // Method reference: ArrayList::new
        Supplier<List<String>> listMaker = ArrayList::new;
        List<String> newList = listMaker.get();  // creates new ArrayList

        // ── When you CANNOT use method reference ──────────────────────────
        // x -> x * x          ❌ custom logic — no direct method to reference
        // (a, b) -> a + b     ❌ operator — not a method call
        // x -> x > 0 ? x : 0 ❌ conditional — not a single method call

        // ── Chaining with stream pipeline ─────────────────────────────────
        long count = names.stream()
                          .map(String::toLowerCase)     // method ref
                          .filter(s -> s.startsWith("a")) // lambda (no method for this)
                          .count();
        System.out.println("Names starting with 'a': " + count);  // 1 (alice)
    }
}

```

### PredicateExample.java

```java
package LambdasFunctionalInterface_38;

import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;

/*
 * ==========================================
 *        PREDICATE INTERFACE & CHAINING
 * ==========================================
 * 
 * Predicate<T> — T → boolean
 * Abstract method: boolean test(T t)
 *
 * PREDICATE COMPOSITION:
 * 
 *               [ Object ]
 *                   |
 *     +-------------+-------------+
 *     |                           |
 * [ Predicate A ]           [ Predicate B ]
 *     |                           |
 *     +----->  [ .and() ]  <------+  = True only if BOTH are true
 *     +----->  [ .or()  ]  <------+  = True if AT LEAST ONE is true
 *     +-----> [ .negate() ]          = Flips the boolean result
 * 
 * Key Use: Stream.filter(), input validation, conditional logic.
 */
public class PredicateExample {

    public static void main(String[] args) {

        // ── Basic predicate ────────────────────────────────────────────────
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4));   // true
        System.out.println("Is 7 even? " + isEven.test(7));   // false

        // ── Combining predicates ───────────────────────────────────────────
        Predicate<Integer> isPositive = x -> x > 0;
        Predicate<Integer> isLarge    = x -> x > 100;

        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        Predicate<Integer> isOdd             = isEven.negate();
        Predicate<Integer> isEvenOrLarge     = isEven.or(isLarge);

        System.out.println("4 is Even & Positive: " + isEvenAndPositive.test(4));   // true
        System.out.println("-4 is Even & Positive: " + isEvenAndPositive.test(-4));  // false
        System.out.println("3 is Odd: " + isOdd.test(3));               // true
        System.out.println("101 is Even or Large: " + isEvenOrLarge.test(101));     // true (large)
        System.out.println("3 is Even or Large: " + isEvenOrLarge.test(3));       // false

        // ── Predicate with Stream — filter ────────────────────────────────
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> evenNumbers = numbers.stream()
                                           .filter(isEven)          // pass predicate
                                           .collect(Collectors.toList());
        System.out.println("Even Stream: " + evenNumbers);  // [2, 4, 6, 8, 10]

        List<Integer> oddNumbers = numbers.stream()
                                          .filter(isEven.negate())
                                          .collect(Collectors.toList());
        System.out.println("Odd Stream: " + oddNumbers);   // [1, 3, 5, 7, 9]

        // ── Predicate<String> — common use case ───────────────────────────
        Predicate<String> isNullOrEmpty = s -> (s == null || s.isEmpty());
        Predicate<String> isNotEmpty    = isNullOrEmpty.negate();

        System.out.println("Is 'hello' not empty? " + isNotEmpty.test("hello"));  // true
        System.out.println("Is '' not empty? " + isNotEmpty.test(""));        // false

        // ── Real-world Object Predicate Chaining ───────────────────────────
        Predicate<Student> passed = s -> s.mark >= 40;
        Predicate<Student> isAdult = s -> s.age >= 18;

        Predicate<Student> isEligible = passed.and(isAdult);

        Student testStudent = new Student(50, 19);
        System.out.println("Is student eligible? " + isEligible.test(testStudent)); // true
    }
}

// Model class for object predicates
class Student {
    int age;
    int mark;

    public Student(int mark, int age) {
        this.age = age;
        this.mark = mark;
    }
}

```

### SupplierExample.java

```java
package LambdasFunctionalInterface_38;

import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

/*
 * Supplier<T> — () → T  (opposite of Consumer)
 * Abstract method: T get()
 *
 * No input, produces output.
 * Key uses:
 *   - Lazy initialization (don't compute until needed)
 *   - Factory methods
 *   - Random value generation
 *   - Deferred computation
 */
public class SupplierExample {

    public static void main(String[] args) {

        // ── Basic Supplier ─────────────────────────────────────────────────
        Supplier<Double> random = () -> Math.random();
        System.out.println(random.get());  // some random double (0.0 to 1.0)
        System.out.println(random.get());  // different value each call

        Supplier<String> greeting = () -> "Hello, Rishon!";
        System.out.println(greeting.get());

        // ── Supplier for lazy initialization ──────────────────────────────
        // The list is only created when .get() is called
        Supplier<List<String>> listFactory = ArrayList::new;  // constructor reference
        List<String> names1 = listFactory.get();  // creates new list
        List<String> names2 = listFactory.get();  // creates ANOTHER new list (independent)

        names1.add("Alice");
        System.out.println("names1: " + names1);  // [Alice]
        System.out.println("names2: " + names2);  // []  ← independent!

        // ── Supplier as a factory ──────────────────────────────────────────
        Supplier<StudentDemo> studentFactory = () -> new StudentDemo("Default", 0);
        StudentDemo s = studentFactory.get();
        System.out.println(s.name);  // Default

        // ── Lazy evaluation with Supplier ──────────────────────────────────
        // Without supplier: expensive() is ALWAYS called
        // printIfTrue(true, expensiveComputation());  // called even if condition is false!

        // With supplier: only called if actually needed
        printIfTrue(true, () -> "Expensive Result");  // ✅ lazy — computed only when needed
        printIfTrue(false, () -> "Never computed");   // ✅ this lambda body never runs
    }

    // Only call supplier.get() if condition is true (lazy evaluation)
    static void printIfTrue(boolean condition, Supplier<String> valueSupplier) {
        if (condition) {
            System.out.println(valueSupplier.get());  // computed on demand
        }
    }
}

class StudentDemo {
    String name;
    int age;
    StudentDemo(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

```

