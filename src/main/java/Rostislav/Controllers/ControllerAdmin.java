package Rostislav.Controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import Rostislav.DAO.*;
import Rostislav.Models.*;
import java.sql.SQLException;

@Controller
public class ControllerAdmin {
    public IDAO dao;
    private History history;
    public ControllerAdmin(@Qualifier("realDAO")IDAO dao2, History history) {
        this.dao = dao2;
        this.history = history;
    }

    //Main
    @GetMapping("/products")
    public String Products (Model model, @RequestParam(value = "message", required =
    		false) String message)throws SQLException{
    	
        model.addAttribute("products",dao.readAllProducts());
    	if (message != null && !message.isEmpty()) {
    		model.addAttribute("message", message);
    	}
    	
        return "index";
    }

    //Update
    @GetMapping("/{id}/updateData")
    public String updateProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", dao.getProductById(id));
        return "updateData";
    }
    @PostMapping("/{id}/updateData")
    public String updateProductPost(@ModelAttribute("product") PrintedProductForm product, @PathVariable("id") int id,
    		RedirectAttributes redirectAttributes) {
        dao.updateProduct(id,product.getPrice(),product.getTitle());
        redirectAttributes.addAttribute("message", "Printed Product successfully updated with ID: " + id);    
        return "redirect:/products";
    }


    //Delete
    @PostMapping("/productDelete/{id}")
    public String productDelete(@PathVariable("id") int id,
    		RedirectAttributes redirectAttributes) {
        dao.deleteProduct(id);
        redirectAttributes.addAttribute("message", "Printed Product successfully deleted with ID: " + id);
        return "redirect:/products";
    }
    
    //Create
    @GetMapping ("/createProducts")
    public String createProductsMenu(@ModelAttribute("prev") PrintedProductForm prev) {
    	PrintedProductForm previous = history.restoreState();

        if (previous != null) {
        	prev.setTitle(previous.getTitle());
        	prev.setPrice(previous.getPrice());
        }
        
        return "createProducts";
    }
    
    @GetMapping("/product/clarification")
    public String showClarificationForm(
        @RequestParam String title,
        @RequestParam Double price) {
    	
    	history.saveState(new PrintedProductForm(title,price));

        return "clarification";
    }

    @PostMapping("/submit")
    public String submitProduct(
        @RequestParam String productType,
        @RequestParam(value = "author", required = false) String author,
        @RequestParam(value = "date", required = false) String date,
        @RequestParam(value = "isEbook", required = false) Boolean isEbook,
        @RequestParam(value = "fileSize", required = false) Double fileSize,
        @RequestParam("action") String action,
        RedirectAttributes redirectAttributes) {
    	
    	PrintedProductForm form = history.restoreState();
    	if ("cancel".equals(action)) {
    		history.saveState(form);
	    	return "redirect:/createProducts";
    	}

        if (productType.equals("book") && Boolean.TRUE.equals(isEbook)) {
        	EBook.EBookBuilder eBookBuilder = new EBook.EBookBuilder();
			eBookBuilder.title(form.getTitle())
						.price(form.getPrice())
						.author(author).build();
			EBook ebook = eBookBuilder.fileSize(fileSize).build();
        	dao.createEBook(ebook);
        	redirectAttributes.addAttribute("message", "EBook successfully added with ID: " + ebook.getId());
        } else if (productType.equals("book")) {
        	Book book = new Book.BookBuilder()
        			.title(form.getTitle())
					.price(form.getPrice())
					.author(author)
					.build();
        	dao.createBook(book);
        	redirectAttributes.addAttribute("message", "Book successfully added with ID: " + book.getId());
        } else if (productType.equals("newspaper")) {
        	Newspaper newspaper = new Newspaper.NewspaperBuilder()
        			.title(form.getTitle())
					.price(form.getPrice())
					.date(date).build();
        	dao.createNewsPaper(newspaper);
        	redirectAttributes.addAttribute("message", "Newspaper successfully added with ID: " + newspaper.getId());
        } else {
        	PrintedProduct t  = new PrintedProduct.PrintedProductBuilder()
        			.title(form.getTitle())
					.price(form.getPrice())
    				.build();
        	dao.createPrintedProduct(t);
        	redirectAttributes.addAttribute("message", "Printed Product successfully added with ID: " + t.getId());
        }

        return "redirect:/products";
    }
    
}
