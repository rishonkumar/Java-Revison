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
