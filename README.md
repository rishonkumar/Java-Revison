# ☕ Java Interview Revision Repository

> A structured, interview-focused Java revision guide covering core concepts, OOP, functional programming, and multithreading — with runnable code examples and critical interview Q&A.

---

## 📚 Topics Covered

| # | Topic | Key Concepts | Notes |
|---|-------|-------------|-------|
| 1 | [Objects in Java](src/ObjectsInJava/README.md) | Object creation, Heap vs Stack, Call by Value, Shallow vs Deep Copy | Core foundation |
| 2 | [Static & Final Keywords](src/StaticFinalKeyWord/README.md) | `static` variables/blocks/methods, `final` variables/methods/classes, constants | Frequently asked |
| 3 | [Encapsulation & Inheritance](src/EncapsulationInheritance/README.md) | Data hiding, Getters/Setters, `extends`, method overriding, types of inheritance | OOP Pillar |
| 4 | [Comparator Interface](src/LambdasFunctionalInterfacesComparatorInterface_37/README.md) | `Comparator<T>`, custom sorting, anonymous class, lambda shorthand | Collections sorting |
| 5 | [Lambdas & Functional Interfaces](src/LambdasFunctionalInterface_38/README.md) | `@FunctionalInterface`, `Function`, `Consumer`, `Supplier`, `Predicate`, Method References, Composition | Java 8 must-know |
| 6 | [Multithreading in Java](src/MultithreadingInJava/README.md) | Process vs Thread, `Thread` class, `Runnable`, `synchronized`, thread lifecycle | Concurrency |

---

## 🎯 Interview Quick Reference

### OOP Pillars
| Pillar | One-liner |
|--------|-----------|
| **Encapsulation** | Hiding data with private fields + public getters/setters |
| **Inheritance** | Child class reuses parent class code via `extends` |
| **Polymorphism** | One interface, many implementations (overloading/overriding) |
| **Abstraction** | Hiding implementation, showing only what's needed |

### Java 8 Functional Interfaces Cheat Sheet
| Interface | Signature | Use case |
|-----------|-----------|----------|
| `Function<T,R>` | `R apply(T t)` | Transform input to output |
| `Consumer<T>` | `void accept(T t)` | Consume input, no return |
| `Supplier<T>` | `T get()` | Provide value, no input |
| `Predicate<T>` | `boolean test(T t)` | Test a condition |
| `BiFunction<T,U,R>` | `R apply(T t, U u)` | Two inputs, one output |

### Static vs Final — Key Rules
| Keyword | Applies to | Meaning |
|---------|-----------|---------|
| `static` | variable | Shared across all instances (class-level) |
| `static` | method | Called on class, not object; no `this` access |
| `static` | block | Runs once when class is loaded |
| `final` | variable | Value cannot be reassigned |
| `final` | method | Cannot be overridden |
| `final` | class | Cannot be subclassed (e.g., `String`) |

---

## 🗂️ Repository Structure

```
src/
├── ObjectsInJava/
│   ├── code/
│   │   ├── Demo.java          → Call by value demo
│   │   └── Demo2.java         → Shallow vs Deep copy
│   └── README.md
├── StaticFinalKeyWord/
│   ├── code/
│   │   ├── Demo.java          → Static variable + static block
│   │   ├── Demo3.java         → final variable + static final
│   │   └── Demo5.java         → String[] args usage
│   └── README.md
├── EncapsulationInheritance/
│   ├── code/
│   │   ├── DemoEncap.java     → BankAccount encapsulation
│   │   ├── InheriDemo.java    → Inheritance types (with diagram)
│   │   └── InheritanceExample.java → Simple inheritance demo
│   └── README.md
├── LambdasFunctionalInterfacesComparatorInterface_37/
│   ├── ComparatorExample.java → Custom sort by name/marks/rollNo
│   └── README.md
├── LambdasFunctionalInterface_38/
│   ├── Demo.java              → Custom @FunctionalInterface + lambda
│   ├── FunctionExample.java   → Function<T,R>
│   ├── ConsumerExample.java   → Consumer<T> + forEach
│   ├── SupplierExample.java   → Supplier<T>
│   ├── PredicateExample.java  → Predicate<T>
│   ├── FunctionalComposition.java → andThen() / compose()
│   ├── MethodReferenceExample.java → :: syntax
│   └── README.md
└── MultithreadingInJava/
    ├── Demo.java              → Process vs Thread concepts
    └── README.md
```

---

> 💡 **Tip:** Each topic folder has its own `README.md` with theory, code walkthroughs, and critical interview Q&A.
