package Banking;

import java.util.Calendar;
import java.util.TimeZone;

public class SavingsAccount extends Account {
    public SavingsAccount(int id, double apr) {
        super(id, apr);
    }

    boolean allowWithdrawal=true;


    @Override
    public boolean isSavingsAccount() {
        return true;
    }

    public void addInterestToBalance() {
        double aprPercent = apr/100;
        double aprPercentPerMonth = aprPercent/12;

        deposit(getAccountBalance() * aprPercentPerMonth);

    }

    public Double withdraw(Double withdraw_amount) {
        if(isWithDrawalAllowed()){
            this.allowWithdrawal=false;
            return super.withdraw(withdraw_amount);
        } else {
            return getAccountBalance();
        }
    }

    public void passMonths(int months){
        this.allowWithdrawal=true;
    }

    public boolean isWithDrawalAllowed(){
        return this.allowWithdrawal;
    }
}
