package Banking;

public class CheckingAccount extends Account {

    public CheckingAccount(int id, double apr) {
        super(id, apr);

    }

    @Override
    public boolean isCheckingAccount() {
        return true;
    }
}
