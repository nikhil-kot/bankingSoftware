import java.util.Map;

public abstract class Account {
    private static Double balance;
    protected Integer id;
    protected Double apr;

    public Account(Integer id, Double apr){
        this.id = id;
        this.apr = apr;
        this.balance = 0.00;

    }
    public Account(Integer id, Double apr, Double balance){
        this.id = id;
        this.apr = apr;
        this.balance = balance;
    }

    public Integer getID(){
        return id;
    }

    public Double getApr(){
        return apr;
    }
    public static CheckingAccount checkingAccount(int id, double apr){
        return new CheckingAccount(id, apr);
    }

    public static SavingsAccount savingsAccount(int id, double apr){
        return new SavingsAccount(id, apr);
    }

    public static CdAccount cdAccount(int id, double apr, double balance ){
        return new CdAccount(id, apr, balance);
    }


    public Double getAccountBalance() {
        return balance;
    }

    public Double deposit(Double deposit_amount){
        balance = balance + deposit_amount;
        return balance;

    }

    public Double withdraw(Double withdraw_amount){
        if(withdraw_amount > balance){
            return balance = 0.00;
        } else {
            balance = balance - withdraw_amount;
            return balance;
        }
    }
}


