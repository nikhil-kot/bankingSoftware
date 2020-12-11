package Banking;

public class CheckingAccount extends Account {

    private int time;

    public CheckingAccount(int id, double apr) {
        super(id, apr);

    }

    @Override
    public boolean isCheckingAccount() {
        return true;
    }

    public void addInterestToBalance() {
        double aprPercent = apr/100;
        double aprPercentPerMonth = aprPercent/12;

        deposit(getAccountBalance() * aprPercentPerMonth);

    }

    public String getType(){
        return "Checking";
    }




}
