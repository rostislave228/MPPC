package Rostislav.DAO;

import java.util.List;

import Rostislav.Models.Book;
import Rostislav.Models.EBook;
import Rostislav.Models.Newspaper;
import Rostislav.Models.PrintedProduct;
import Rostislav.Models.User;
import Rostislav.Models.UserProduct;

public class DAOProxy implements IDAO {
	private final IDAO target;
    private final String userRole;

    public DAOProxy(IDAO target, String userRole) {
        this.target = target;
        this.userRole = userRole;
    }

	private void checkAdminAccess() {
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            throw new SecurityException("Access denied. Only ADMIN can perform this action.");
        }
    }

	@Override
	public List<PrintedProduct> readAllProducts() {
		return target.readAllProducts();
	}

	@Override
	public void deleteProduct(int Id) {
		this.checkAdminAccess();
		this.target.deleteProduct(Id);		
	}

	@Override
	public PrintedProduct getProductById(int id) {
		return target.getProductById(id);
	}

	@Override
	public void updateProduct(int Id, double Price, String Title) {
		this.checkAdminAccess();
		this.target.updateProduct(Id, Price, Title);		
	}

	@Override
	public void createPrintedProduct(PrintedProduct t) {
		this.checkAdminAccess();
		this.target.createPrintedProduct(t);
	}

	@Override
	public void createBook(Book c) {
		this.checkAdminAccess();
		this.target.createPrintedProduct(c);
	}

	@Override
	public void createEBook(EBook sc) {
		this.checkAdminAccess();
		this.target.createEBook(sc);
	}

	@Override
	public void createNewsPaper(Newspaper tr) {
		this.checkAdminAccess();
		this.target.createNewsPaper(tr);
	}

	@Override
	public void addListener(Listener l) {
		this.target.addListener(l);		
	}

	@Override
	public void removeListener(Listener l) {
		this.target.removeListener(l);
	}

	@Override
	public void notifyListeners(String eventType, Object entity) {
		this.target.notifyListeners(eventType, entity);
	}

	@Override
	public User getUser(String username, String password) {
		return this.target.getUser(username, password);
	}

	@Override
	public boolean authenticate(String username, String password) {
		return this.target.authenticate(username, password);
	}

	@Override
	public void register(User user) {
		this.target.register(user);
	}

	@Override
	public void addUser(User user) {
		this.checkAdminAccess();
		this.target.addUser(user);
	}

	@Override
	public List<UserProduct> getProductsByUserId(int id) {
		return this.target.getProductsByUserId(id);
	}
    
	
}
