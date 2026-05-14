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
