package pyramid_scheme;

/**
 * A basic representation of a person and their money.
 * @author pmele
 * @version 3/29/2019
 */
public class Person {
    
    private String name;
    private double balance;
    
    /**
     * Default Person constructor.
     */
    public Person() {
        name = "Anonymous";
        balance = 0.0;
    }
    
    /**
     * Name-only Person constructor.
     * 
     * @param n The Person's name
     */
    public Person(String n) {
        name = n;
        balance = 0.0;
    }
    
    /**
     * Normal Person constructor.
     * 
     * @param n The Person's name
     * @param bal The Person's account balance
     */
    public Person(String n, double bal) {
        name = n;
        balance = bal;
    }
    
    /**
     * Returns the Person's account balance.
     * 
     * @return balance
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Adds or subtracts an amount from the Person's balance.
     * 
     * @param amount The amount to adjust by (Can be negative).
     */
    public void adjustBalance(double amount) {
        balance += amount;
    }
    
    /**
     * Returns the Person's name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Compares this Person to another object for equality, based on name.
     * 
     * @param o Another Object to compare this to
     * @return a boolean, true if this and o are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) {
            return false;
        }
        Person p = (Person)o;
        return this.name.equals(p.getName());
    }
    
    /**
     * 
     * @return a String describing this Person.
     */
    @Override
    public String toString() {
        return name + " (Bal: $" + balance + ")";
    }
}
