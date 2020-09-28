package xyz.ioc.service;

import io.github.mcroteau.Parakeet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.ioc.common.Constants;
import xyz.ioc.dao.AccountDao;
import xyz.ioc.model.Account;

public class AuthService {

    private static final Logger log = Logger.getLogger(AuthService.class);

    @Autowired
    private static Parakeet parakeet;

    @Autowired
    public static AccountDao accountDao;

    public static boolean administrator(){
        return parakeet.hasRole(Constants.ROLE_ADMIN);
    }

    public static boolean authenticated(){
        return parakeet.isAuthenticated();
    }

    public static boolean hasPermission(String str){
        return parakeet.hasPermission(str);
    }

    public static Account getAuthenticatedAccount(){
        String user = parakeet.getUser();
        Account account = accountDao.findByUsername(user);
        return account;
    }
}

