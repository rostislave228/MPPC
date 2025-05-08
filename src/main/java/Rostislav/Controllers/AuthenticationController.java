package Rostislav.Controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Rostislav.DAO.DAOFactory;
import Rostislav.DAO.IDAO;
import Rostislav.DAO.TypeDAO;
import Rostislav.Models.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthenticationController {
	public IDAO dao;

    @Autowired
    public AuthenticationController() {
    	try {
            dao = DAOFactory.getDAOInstance(TypeDAO.MySQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    @GetMapping("/")
	public String startPage() {
		return "start";
	}
    
    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        System.out.println("signup");
    	model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam String username, @RequestParam String password,
    		HttpSession session, Model model) {
    	  	
    	if (dao.getUser(username, password) != null) {
            model.addAttribute("error", "Username already exists");
            return "redirect:/signup";
        }
        
    	User user = new User(username,password);
        dao.register(user);
        session.setAttribute("usered", true);
        session.setAttribute("current_user", user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
    	model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
    		HttpSession session, Model model) {
    	User user = dao.getUser(username, password);
    	if (username.equals("admin") && password.equals("adminpass")) {
            session.setAttribute("admined", true);
			return "redirect:/products";
        } else if (dao.authenticate(username, password)) {
        	session.setAttribute("usered", true);
            session.setAttribute("current_user", user);
            return "redirect:/user_page";
        } else {
        	model.addAttribute("error", "Invalid username or password");
            return "redirect:/login?error=true";
        }
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}