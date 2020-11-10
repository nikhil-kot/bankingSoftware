package Banking;

public class SavingsAccount extends Account {
    public SavingsAccount(int id, double apr) {
        super(id, apr);
    }

    @Override
    public boolean isSavingsAccount() {
        return true;
    }
}
