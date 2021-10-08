package sort.sortAccount;

import model.Account;

import java.util.Comparator;

public class SortDecentralizationAtoZ implements Comparator<Account> {
    @Override
    public  int compare(Account account1, Account account2){
        return account1.getDecentralization().compareTo(account2.getDecentralization());
    }
}
