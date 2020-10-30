import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Integer, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public Account getAccount(Integer id) {
        return accounts.get(id);
    }


    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void makeDeposit(Integer accountId, Double amount) {
        getAccount(accountId).deposit(amount);
    }

    public void makeWithdrawal(Integer accountId, Double amount) {
        getAccount(accountId).withdraw(amount);
    }

    public Double getAccountBalance(Integer accountId) {
        return getAccount(accountId).getAccountBalance();
    }

    public void addAccount(Account account) {
        accounts.put(account.getID(), account);
    }

    public boolean doesAccountExist(Integer id) {
        return accounts.get(id) != null;
    }
}
