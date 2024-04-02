package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO =new AccountDAO();
    }

    //constructor for AccountService when AccountDAO provided

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO =accountDAO;
    }

    //register account
    public Account accountRegister(Account account) {
        return accountDAO.getRegister(account);
    }
    //login account
    public Account accountLogin(Account account) {
        return accountDAO.getLogin(account);
    }
    
}
