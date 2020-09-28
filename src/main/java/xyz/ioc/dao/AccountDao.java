package xyz.ioc.dao;

import java.util.List;
import java.util.Set;

import xyz.ioc.model.*;

public interface AccountDao {

	public long id();

	public long count();
	
	public Account get(long id);
	
	public Account findByUsername(String username);
	
	public List<Account> findAll();
	
	public List<Account> findAllOffset(int max, int offset);

	public Account save(Account account);

	public Account saveAdministrator(Account account);

	public boolean update(Account account);

	public boolean updateUuid(Account account);

	public boolean updatePassword(Account account);

	public Account findByUsernameAndUuid(String username, String uuid);
	
	public boolean delete(long id);
	
	public String getAccountPassword(String username);

	public boolean saveAccountRole(long accountId, long roleId);
	
	public boolean saveAccountPermission(long accountId, String permission);
	
	public boolean deleteAccountRoles(long accountId);
	
	public boolean deleteAccountPermissions(long accountId);

	public Set<String> getAccountRoles(long id);
	
	public Set<String> getAccountRoles(String username);

	public Set<String> getAccountPermissions(long id);

	public Set<String> getAccountPermissions(String username);

	public long countQuery(String query);

	public List<Account> search(String query, int offset);

	public boolean updateDisabled(Account account);

	public boolean suspend(Account account);

	public boolean renew(Account account);

}