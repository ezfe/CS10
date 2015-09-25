/**
 * ATMSavingsAccount.java
 *
 * Extends SavingsAccount and provides a withdraw limit and the corresponding fees
 *
 * @author Ezekiel Elin on September 25, 2015
 */

public class ATMSavingsAccount extends SavingsAccount {
    private int withdrawals = 0;
    private static final int MAX_WITHDRAWALS = 2;
    private static final double OVERDRAW_FEE = 1.5;

    public ATMSavingsAccount(double r) {
        super(r);
    }
    public ATMSavingsAccount(double r, double iB) {
        super(r, iB);
    }

    public void withdraw(int a) {
        withdrawals++; //Increment withdraws
        super.withdraw(a); //Call super to do the rest
    }

    public void transfer(BankAccount transferTo, double amt) {
        withdrawals++; //Increment withdraws
        super.transfer(transferTo, amt); //Call super to do the rest
    }

    public void addPeriodicInterest() {
        super.addPeriodicInterest();
        if (withdrawals > MAX_WITHDRAWALS) { //If we've exceeded the amount...
            double withdrawAmount = (withdrawals - MAX_WITHDRAWALS) * OVERDRAW_FEE; //Get the number of times exceeded, and multiply that by the overdraw fee
            withdraw(withdrawAmount); //Withdraw that amount
        }
        withdrawals = 0; //And set withdrawals back to 0 immediately
    }

    public String toString() {
        return "Balance: " + getBalance();
    }
}
