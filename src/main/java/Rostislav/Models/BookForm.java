package Rostislav.Models;

public class BookForm extends PrintedProductForm {
    private String author;
    public BookForm(int Id, String title, double price, String author ) {
        super(Id,title, price);
        this.author = author;
    }
    public BookForm(){
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    @Override
    public String toString() {
        return super.toString() + ", Author: " + author;
    }
}