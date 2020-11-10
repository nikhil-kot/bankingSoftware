package Banking;

public class CdAccount extends Account {
    public CdAccount(int id, double apr, double balance) {
        super(id, apr, balance);
    }

    @Override
    public boolean isCdAccount() {
        return true;
    }
}
