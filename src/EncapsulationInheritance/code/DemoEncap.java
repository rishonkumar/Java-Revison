package EncapsulationInheritance.code;

/*
 * ==========================================
 *        ENCAPSULATION IN JAVA
 * ==========================================
 * 
 *       [ Outside World / Main ]
 *                 |
 *                 v
 *  +-----------------------------+
 *  |       STUDENT OBJECT        |
 *  |                             |
 *  |   public void setAge(age)   | <--- Gatekeeper (Validation Logic)
 *  |   { if (age > 0) ... }      |
 *  |              |              |
 *  |              v              |
 *  |      [ private age ]        | <--- Hidden Data (Cannot be accessed directly)
 *  +-----------------------------+
 * 
 * Encapsulation = Data Hiding (private fields) + Controlled Access (public getters/setters)
 * Key benefit: Setters can add VALIDATION — preventing the object from entering an invalid state.
 */
public class DemoEncap {

    public static void main(String[] args) {

        // ── Basic BankAccount Encapsulation ───────────────────────────────
        BankAccount ba = new BankAccount();
        ba.deposit(500);
        ba.withdraw(300);
        System.out.println("Balance: " + ba.getBalance());  // 200.0

        // ba.balance = 9999;  // ❌ Compile error — private field, no direct access

        // ── Withdrawal guard: balance cannot go negative ──────────────────
        boolean result = ba.withdraw(1000);
        System.out.println("Withdraw 1000 succeeded: " + result);  // false
        System.out.println("Balance still: " + ba.getBalance());    // 200.0

        // ── Student with validated setter ─────────────────────────────────
        Student s = new Student("Rishon", 1, 21, "SRM");
        System.out.println("Student Name: " + s.getName());

        // s.setAge(-5);  // prints validation error — won't set negative age
    }
}

class BankAccount {
    // PRIVATE — outside world cannot directly touch this
    private double balance;  

    public void deposit(int amount) {
        // Validation inside — caller can't bypass this
        if (amount > 0) {          
            balance += amount;
        }
    }

    // Returns true if successful, false if insufficient balance
    public boolean withdraw(int amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        System.out.println("Insufficient balance or invalid amount.");
        return false;
    }

    // Getter — read-only access to balance
    public double getBalance() {
        return balance;
    }
    // No setter for balance — you MUST go through deposit/withdraw to alter state
}

class Student {
    private String name;
    private int rollNo;
    private int age;
    private String college;

    Student(String name, int rollNo, int age, String college) {
        this.name = name;
        this.rollNo = rollNo;
        this.age = age;
        this.college = college;
    }

    public String getName() { return name; }

    // Setter with validation — this is THE key benefit of encapsulation
    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else {
            System.out.println("Invalid name — not updated.");
        }
    }

    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age — not updated.");
        }
    }

    public int getAge() { return age; }
}
