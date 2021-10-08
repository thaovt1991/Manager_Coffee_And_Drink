package sort.sortAccount;

import model.Account;

import java.util.Comparator;

public class SortByIdAtoZ implements Comparator<Account> {
    @Override
    public int compare(Account account1 ,Account account2){
        return account1.getOwnerId().compareTo(account2.getOwnerId());
    }
}
