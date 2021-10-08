package sort.sortAccount;

import model.Account;

import java.util.Comparator;

public class SortUserNameAtoZ implements Comparator<Account> {
    @Override
    public int compare(Account account1 , Account account2){
        return account1.getUserName().compareTo(account2.getUserName());
    }
}
