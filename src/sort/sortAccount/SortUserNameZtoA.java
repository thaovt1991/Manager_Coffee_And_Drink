package sort.sortAccount;

import model.Account;

import java.util.Comparator;

public class SortUserNameZtoA implements Comparator<Account> {
    @Override
    public int compare(Account account1 , Account account2){
        return account2.getUserName().compareTo(account1.getUserName());
    }
}
