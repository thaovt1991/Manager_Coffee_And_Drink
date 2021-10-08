package sort.sortAccount;

import model.Account;

import java.util.Comparator;

public class SortDecentralizationZtoA implements Comparator<Account> {
    @Override
    public int compare(Account account1, Account account2) {
        return account2.getDecentralization().compareTo(account1.getDecentralization());
    }
}
