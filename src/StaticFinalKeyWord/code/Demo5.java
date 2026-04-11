package StaticFinalKeyWord.code;

//USe of string[] args
public class Demo5 {

    static void main(String[] args) {
        System.out.println("Nymber of arguments are " + args.length);

        for(int i = 0 ; i < args.length ; i++) {
            System.out.println("Argument " + i + " =" + args[i]);
            // javac demo5.java
            //if u run java Demo5 Rishon Rizon then you
            // will get the output but if u run without giving
            // any input it will show 0 argument
        }
    }
}
