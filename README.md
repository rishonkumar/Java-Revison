# ☕ Java Interview Revision

> Structured, interview-focused Java revision — core concepts, OOP, functional programming, and multithreading.  
> Each topic has its own README with theory, code walkthroughs, and interview Q&A.

---

## 🗺️ Topics

| # | Topic | Key Concepts |
|---|-------|-------------|
| 1 | [Objects in Java](src/ObjectsInJava/README.md) | Object creation, Heap vs Stack, Call by Value, Shallow vs Deep Copy |
| 2 | [Static & Final Keywords](src/StaticFinalKeyWord/README.md) | `static` variables/blocks/methods, `final` variables/methods/classes |
| 3 | [Encapsulation & Inheritance](src/EncapsulationInheritance/README.md) | Data hiding, Getters/Setters, `extends`, overriding, types of inheritance |
| 4 | [Comparator Interface](src/LambdasFunctionalInterfacesComparatorInterface_37/README.md) | `Comparator<T>`, anonymous class → lambda evolution |
| 5 | [Lambdas & Functional Interfaces](src/LambdasFunctionalInterface_38/README.md) | `Function`, `Consumer`, `Supplier`, `Predicate`, Method References, Composition |
| 6 | [Multithreading](src/MultithreadingInJava_47/README.md) | Thread lifecycle, `synchronized`, race conditions, deadlock, ExecutorService |

---

## 🎯 Interview Prep

### → [INTERVIEW_QUESTIONS.md](INTERVIEW_QUESTIONS.md)
All critical Q&A consolidated in one file — grouped by topic, edge cases highlighted with ⚡.

---

## 🗂️ Project Structure

```
Java-Revison/
├── INTERVIEW_QUESTIONS.md          ← All interview Q&A in one place
├── README.md                       ← This file (navigation)
└── src/
    ├── ObjectsInJava/
    │   ├── code/
    │   │   ├── Demo.java           → Call by value demo
    │   │   └── Demo2.java          → Shallow vs Deep copy
    │   └── README.md
    ├── StaticFinalKeyWord/
    │   ├── code/
    │   │   ├── Demo.java           → Static variable + static block
    │   │   ├── Demo3.java          → final variable + static final
    │   │   └── Demo5.java          → String[] args usage
    │   └── README.md
    ├── EncapsulationInheritance/
    │   ├── code/
    │   │   ├── DemoEncap.java      → BankAccount encapsulation
    │   │   ├── InheriDemo.java     → Inheritance types
    │   │   └── InheritanceExample.java → Simple inheritance demo
    │   └── README.md
    ├── LambdasFunctionalInterfacesComparatorInterface_37/
    │   ├── ComparatorExample.java  → Custom sort by name/marks/rollNo
    │   └── README.md
    ├── LambdasFunctionalInterface_38/
    │   ├── Demo.java               → Custom @FunctionalInterface
    │   ├── FunctionExample.java    → Function<T,R>
    │   ├── ConsumerExample.java    → Consumer<T> + andThen pipeline
    │   ├── SupplierExample.java    → Supplier<T>
    │   ├── PredicateExample.java   → Predicate<T> + Stream.filter
    │   ├── FunctionalComposition.java → andThen() / compose()
    │   ├── MethodReferenceExample.java → :: syntax
    │   └── README.md
    └── MultithreadingInJava_47/
        ├── Demo.java               → Thread concepts
        └── README.md
```

---

> 💡 **Tip:** Each topic `README.md` has theory + code walkthroughs. Use `INTERVIEW_QUESTIONS.md` for rapid pre-interview review.
