package cn.itsource.dao;

import cn.itsource.domain.Account;
import cn.itsource.domain.StudentMessages;

import java.util.List;

public interface AccountDao {
    public void insertAccount(Account account);
    public void updateByPrimaryKey(Account account);
    public void deleteByPrimaryKey(Integer id);
    public Account getAccountByLogin(Account account);
    public Account getAccountByNum(Account account);
    public Account getAccountById(Integer id);
    public List<Account> queryAll();
}
