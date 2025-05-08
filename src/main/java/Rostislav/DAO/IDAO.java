package Rostislav.DAO;

import java.util.List;

import Rostislav.Models.*;

public interface IDAO {
    public List<PrintedProduct> readAllProducts();
    public void deleteProduct(int Id);
    public PrintedProduct getProductById(int id);
    public void updateProduct(int Id, double Price, String Title);
    public void createPrintedProduct(PrintedProduct t);
    public void createBook(Book c);
    public void createEBook(EBook sc);
    public void createNewsPaper(Newspaper tr);
    public void addListener(Listener l);
    public void removeListener(Listener l);
    public void notifyListeners(String eventType, Object entity);
	public User getUser(String username, String password);
	public boolean authenticate(String username, String password);
	public void register(User user);
	public void addUser(User user);
	public List<UserProduct> getProductsByUserId(int id);
}