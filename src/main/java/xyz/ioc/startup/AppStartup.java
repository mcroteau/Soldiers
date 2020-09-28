package xyz.ioc.startup;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import xyz.ioc.common.Constants;
import xyz.ioc.common.Utilities;
import xyz.ioc.dao.*;
import xyz.ioc.model.*;


public class AppStartup implements ApplicationListener<ContextRefreshedEvent>{

	private static final Logger log = Logger.getLogger(AppStartup.class);

	@Autowired
	public RoleDao roleDao;

	@Autowired
	public AccountDao accountDao;

	@Autowired
	public Utilities utilities;

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshEvent) {
		createApplicationRoles();
		createApplicationAdministrator();
		createApplicationGuestShelter();
	}


	private void createApplicationRoles(){
		Role adminRole = roleDao.find(Constants.ROLE_ADMIN);
		Role accountRole = roleDao.find(Constants.ROLE_ACCOUNT);

		if(adminRole == null){
			adminRole = new Role();
			adminRole.setName(Constants.ROLE_ADMIN);
			roleDao.save(adminRole);
		}

		if(accountRole == null){
			accountRole = new Role();
			accountRole.setName(Constants.ROLE_ACCOUNT);
			roleDao.save(accountRole);
		}

		log.info("Roles : " + roleDao.count());
	}

	
	private void createApplicationAdministrator(){
		
		try{
			Account existing = accountDao.findByUsername(Constants.ADMIN_USERNAME);
			String password = utilities.hash(Constants.PASSWORD);

			if(existing == null){
				Account admin = new Account();
				admin.setName("Administrator");
				admin.setUsername(Constants.ADMIN_USERNAME);
				admin.setPassword(password);
				admin.setImageUri(Constants.FRESCO);
				accountDao.saveAdministrator(admin);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("Accounts : " + accountDao.count());
	}

	private void createApplicationGuestShelter() {
	}
	
}