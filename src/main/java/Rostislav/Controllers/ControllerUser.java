package Rostislav.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Rostislav.DAO.IDAO;
import Rostislav.Models.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class ControllerUser {
	public IDAO dao;

    @Autowired
    public ControllerUser(@Qualifier("proxyDAO")IDAO dao2) {
    	this.dao = dao2;
    }
	@GetMapping("/user_page")
    public String viewUserProducts(HttpSession session, Model model) {
		User user = (User) session.getAttribute("current_user");
        if (user == null) {
            return "redirect:/login";
        }
                
        List<UserProduct> products = dao.getProductsByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("products", products);

        return "user";
    }
	
	@PostMapping("/product/delete/{id}")
	public String productDelete(@PathVariable("id") int id,
    		RedirectAttributes redirectAttributes) {
        dao.deleteProduct(id);
        redirectAttributes.addAttribute("message", "Printed Product successfully deleted with ID: " + id);
        return "redirect:/user_page";
    }
    
    @GetMapping("/product/update/{id}")
    public String updateProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", dao.getProductById(id));
        return "updateData_u";
    }
    
    @GetMapping("/product/create")
    public String createProductPage() {
        return "product_create";
    }
    
    @PostMapping("/product/create")
    public String createProduct(@RequestParam String title, @RequestParam double price, @RequestParam int userId) {
    	dao.createPrintedProduct(new PrintedProduct.PrintedProductBuilder().build());
        return "redirect:/user_page";
    }

    @PostMapping("/product/update/{id}")
    public String updateProduct(@PathVariable int id, @RequestParam String title, @RequestParam double price) {
    	dao.updateProduct(id, price, title);
        return "redirect:/user_page";
    }
}
