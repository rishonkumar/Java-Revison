package EncapsulationInheritance.code;

public class DemoEncap {

    static void main() {
        BankAccount ba = new BankAccount();
        ba.deposit(500);
        ba.withdraw(300);

        System.out.println(ba.getBalance());
    }
}

class BankAccount {
    private double balance;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    //getter / setter
    public double getBalance() {
        return balance;
    }

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

    public String getName() {
        return name;
    }

    private void setName() {
        //we can add validation in setters (use of encapuslation)
        this.name = name;
    }


}
