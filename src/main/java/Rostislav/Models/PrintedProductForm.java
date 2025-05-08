package Rostislav.Models;

public class PrintedProductForm {
    private String title;
    private double price;
    private int Id;
    public PrintedProductForm(int Id,String title, double price) {
        this.Id=Id;
        this.title = title;
        this.price = price;
    }
    public PrintedProductForm(String title, double price) {
        this.title = title;
        this.price = price;
    }
    public PrintedProductForm(){}
    public PrintedProductForm(PrintedProductForm orderState) {
    	this.Id = orderState.Id;
        this.title = orderState.title;
        this.price = orderState.price;
	}
	public int getId(){return Id;}
    public void setId(int Id){this.Id=Id;}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Title: " + title + ", Price: " + price;
    }
}