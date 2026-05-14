package EncapsulationInheritance.code;

/*
 * Inheritance Types in Java
 *
 * 1. Simple (Single)   : A → B
 * 2. Multi-level       : A → B → C
 * 3. Hierarchical      : A → B, A → C (one parent, multiple children)
 * 4. Multiple          : NOT supported via classes (diamond problem)
 *                        Achieved via INTERFACES
 *
 * Diamond Problem:
 *       A
 *      / \
 *     B   C       B and C both override A.method()
 *      \ /
 *       D         ← which method() does D inherit? AMBIGUOUS!
 *
 * Java's fix: multiple inheritance ONLY via interfaces
 *             If two interfaces have same default method → D must override it
 */
public class InheriDemo {

    public static void main(String[] args) {

        // Simple Inheritance
        EngineeringStudent es = new EngineeringStudent();
        es.markAttendance();  // inherited from StudentBase
        es.attendLab();       // own method

        // Multi-level
        CSEEngineeringStudent cse = new CSEEngineeringStudent();
        cse.markAttendance(); // from StudentBase (grandparent)
        cse.attendLab();      // from EngineeringStudent (parent)
        cse.attendCSELab();   // own method

        // Polymorphism — parent reference, child object
        StudentBase ref = new EngineeringStudent();
        ref.markAttendance(); // ✅ works — method from EngineeringStudent (overridden)
        // ref.attendLab();   // ❌ compile error — StudentBase doesn't know attendLab

        // ── Multiple Inheritance via Interface ────────────────────────────
        AndroidDeveloper dev = new AndroidDeveloper();
        dev.writeCode();     // from Programmer
        dev.useMobile();     // from MobileUser
        dev.develop();       // own method
    }
}

// ── Simple ────────────────────────────────────────────────────────────
class StudentBase {
    String name;
    int age;

    void markAttendance() {
        System.out.println(this.getClass().getSimpleName() + " attendance marked");
    }
}

// ── Simple & also parent for Multi-level ──────────────────────────────
class EngineeringStudent1 extends StudentBase {
    void attendLab() {
        System.out.println("Lab attended");
    }

    @Override
    void markAttendance() {
        System.out.println("Engineering student attendance (with biometric)");
    }
}

// ── Multi-level: StudentBase → EngineeringStudent → CSEEngineeringStudent ──
class CSEEngineeringStudent extends EngineeringStudent {
    void attendCSELab() {
        System.out.println("CSE Lab attended");
    }
}

// ── Hierarchical: StudentBase → MedicalStudent (sibling of EngineeringStudent) ──
class MedicalStudent extends StudentBase {
    void attendClinic() {
        System.out.println("Clinic attended");
    }
}

// ── Multiple Inheritance via Interfaces ───────────────────────────────
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
