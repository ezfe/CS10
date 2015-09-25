/**
 * AccountTest.java
 *
 * Modification of an example in Cay Horstmann's "Computing Concepts with
 * Java 2 Essentials".
 *
 * Tests the various bank account classes.
 *
 * @author Scot Drysdale on 4/23/00.  Modified 1/8/12.
 */
public class AccountTest {
  public static void main(String[] args) {
    SavingsAccount momsSavings = new SavingsAccount(0.5);
    TimeDepositAccount collegeFund = new TimeDepositAccount(1.0, 10000.00, 3, 0.5);
    CheckingAccount harrysChecking = new CheckingAccount();

    ATMSavingsAccount harrysATMSavings = new ATMSavingsAccount(0.5); // Add new testing for ATMSavings account with 0.5% interest
    harrysATMSavings.deposit(5000.00); //Deposit $5000 into harrys ATM savings account

    momsSavings.deposit(9980.00);

    momsSavings.transfer(harrysChecking, 2000);

    harrysChecking.withdraw(200);
    harrysChecking.withdraw(300);
    harrysChecking.withdraw(80);
    harrysChecking.withdraw(400);

    harrysATMSavings.withdraw(20); //First allowed withdrawal
    harrysATMSavings.withdraw(20); //Second allowed withdrawal
    harrysATMSavings.withdraw(20); //Third, $1.50 fee
    harrysATMSavings.transfer(momsSavings, 20); //Fourth, $1.50 fee (this is a transfer but still counts as a withdraw)

    endOfMonth(momsSavings);
    endOfMonth(collegeFund);
    endOfMonth(harrysChecking);

    endOfMonth(harrysATMSavings); //End of the month, calc interest and fees

    collegeFund.transfer(harrysChecking, 980);

    System.out.println("Mom's savings. " + momsSavings);
    // (9980 - 2000 + 20) * .5 % interest = 8040
    System.out.println("The college fund. " + collegeFund);
    // (10000 * 1% interest) * 0.5% penalty - 980 = 9069.50
    System.out.println("Harry's checking. " + harrysChecking);
    // 2000 - 200 - 300 - 80 - 400 - 2 trans. fees + 980 = 1999

    //Print out harry's atm savings account to check the balance
    System.out.println("Harry's ATM Savings. " + harrysATMSavings);
    // (5000 - 20 - 20 - 20 - 20) * 1.005 (0.5% interest) - 3 ($1.50 * 2 in fees) = 4,941.6
  }

  // Handles end-of-month operations. Overloaded method, because
  // checking account does different things than savings account.

  /**
   * Handle end of month interest for a savings account
   * @param savings the savings account to handle
   */
  public static void endOfMonth(SavingsAccount savings) {
    savings.addPeriodicInterest();
  }

  /**
   * Handles end of month fee deduction for a checking account
   * @param checking the checking account to handle
   */
  public static void endOfMonth(CheckingAccount checking) {
    checking.deductFees();
  }
}
