package xyz.ioc.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

import io.github.mcroteau.Parakeet;
import xyz.ioc.model.Account;
import xyz.ioc.dao.AccountDao;
import xyz.ioc.service.AuthService;

@Controller
public class AuthController {

	private static final Logger log = Logger.getLogger(AuthController.class);

    private Gson gson = new Gson();

	@Autowired
	private Parakeet parakeet;

	@Autowired
	private AccountDao accountDao;


	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public String authenticate(ModelMap model,	
							   HttpServletRequest request, 
							   final RedirectAttributes redirect,
							   @RequestParam(value="uri", required = false ) String uri,
							   @ModelAttribute("signon") Account account){

		try{


			if(!parakeet.login(account.getUsername(), account.getPassword())){
				request.getSession().setAttribute("message", "Wrong username and password");
				return "redirect:/";
			}

			Account sessionAccount = accountDao.findByUsername(account.getUsername());

			request.getSession().setAttribute("account", sessionAccount);
			request.getSession().setAttribute("imageUri", sessionAccount.getImageUri());

			request.getSession().removeAttribute("message");

			if(uri != null &&
					!uri.equals("")) {
				return "redirect:/resource?uri=" + uri;
			} else {
				return "redirect:/";
			}


		} catch ( Exception e ) { 
			e.printStackTrace();
		}

		request.getSession().setAttribute("message", "Wrong username and password");
		log.info("redirect to base");
		return "redirect:/";
	
	}	

	@RequestMapping(value="/authenticate_mobile", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody String authenticateMobile(ModelMap model,	
							   HttpServletRequest request, 
							   final RedirectAttributes redirect,
							   @RequestParam(value="uri", required = false) String uri,
							   @ModelAttribute("signon") Account account){


		Map<String, Object> response = new HashMap<String, Object>();
	
		try{

			String payload = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
	    	account = gson.fromJson(payload, Account.class);


			if(!parakeet.login(account.getUsername(), account.getPassword())){
				response.put("authenticated", false);
				return gson.toJson(response);
			}

			Account authenticatedAccount = AuthService.getAuthenticatedAccount();
			response.put("profile", authenticatedAccount);

		} catch ( Exception e ) { 
			response.put("authenticated", false);
			e.printStackTrace();
		} 
		
		return gson.toJson(response);
	}	


	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String signout(ModelMap model,	
							   HttpServletRequest request, 
							   final RedirectAttributes redirect){

		parakeet.logout();

		model.addAttribute("message", "Successfully signed out");
		request.getSession().setAttribute("account", "");
		request.getSession().setAttribute("imageUri", "");

		return "redirect:/";
	}

	@RequestMapping(value="/unauthorized", method=RequestMethod.GET)
	public String unauthorized(ModelMap model, @RequestParam(value="uri", required = false ) String uri){
		return "authentication/unauthorized";
	}
}