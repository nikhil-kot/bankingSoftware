package Banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Set<Integer> getAllAccountIds() {
        return accounts.keySet();
    }

    public boolean closeAccount(Integer id){
        Account removedAccount = accounts.remove(id);

        if(removedAccount!=null){
            return true;
        } else {
            //account doesn't exist
            return false;
        }
    }
}
