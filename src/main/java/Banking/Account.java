package Banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.lang.Math.floor;


public abstract class Account {
    protected Double apr;
    protected Double balance;
    protected Integer id;
    DecimalFormat df = new DecimalFormat("0.00");




    public Account(Integer id, Double apr) {
        this.id = id;
        this.apr = apr;
        this.balance = 0.00;

    }

    public Account(Integer id, Double apr, Double balance) {
        this.id = id;
        this.apr = apr;
        this.balance = balance;

    }

    public static CheckingAccount checkingAccount(int id, double apr) {
        return new CheckingAccount(id, apr);
    }

    public static SavingsAccount savingsAccount(int id, double apr) {
        return new SavingsAccount(id, apr);
    }

    public static CdAccount cdAccount(int id, double apr, double balance) {
        return new CdAccount(id, apr, balance);
    }


    public Integer getID() {
        return id;
    }

    public Double getApr() {
        return Double.parseDouble(df.format(apr));
    }


    public Double getAccountBalance() {
        return Double.parseDouble(df.format(balance));

    }

    public Double deposit(Double deposit_amount) {
        balance = balance + deposit_amount;
        return balance;

    }

    public Double withdraw(Double withdraw_amount) {
        if (withdraw_amount >= balance) {
            balance = 0.00;
            return balance;
        } else {
            balance = balance - withdraw_amount;
            return balance;
        }
    }

    public void deduct(Double fee) {
        if( balance < fee){
            balance = 0.0;
        } else {
            balance = balance - fee;
        }
    }
    public void passMonths(int months){
    }

    public boolean isWithDrawalAllowed(){
        return true;
    }

    public boolean isCheckingAccount() {
        return false;
    }

    public boolean isSavingsAccount() {
        return false;
    }

    public boolean isCdAccount() {
        return false;
    }

    public abstract void addInterestToBalance();

    public abstract String getType();

}


