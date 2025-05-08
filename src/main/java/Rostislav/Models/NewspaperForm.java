package Rostislav.Models;

public class NewspaperForm extends PrintedProductForm {
    private String date;
    public NewspaperForm(int Id,String title, double price, String date) {
        super(Id,title, price);
        this.date = date;
    }
    public NewspaperForm(){}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return super.toString() + ", Date: " + date;
    }
}