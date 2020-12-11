package Banking;

public class CdAccount extends Account {
    private int time = 0;
    private boolean allowWithdrawal = false;


    public CdAccount(int id, double apr, double balance) {
        super(id, apr, balance);
    }

    @Override
    public boolean isCdAccount() {
        return true;
    }

    public void addInterestToBalance() {
        double aprPercent = apr/100;
        double aprPercentPerMonth = (aprPercent/12);


        for(int i=1; i <= 4; i++) {
            deposit(getAccountBalance() * aprPercentPerMonth);
        }

    }
    public void passMonths(int months){
        time += months;
        if (time >= 12){
            this.allowWithdrawal=true;
        }
    }

    public boolean isWithDrawalAllowed(){
        return this.allowWithdrawal;
    }

    public String getType(){
        return "Cd";
    }
}
