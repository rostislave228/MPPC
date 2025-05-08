package Rostislav.Models;

public class EBookForm extends BookForm {
    private double fileSize;
    public EBookForm(int Id,String title, double price, String author, double fileSize)
    {
        super(Id,title, price, author);
        this.fileSize = fileSize;
    }
    public EBookForm(){}
    public double getFileSize() {
        return fileSize;
    }
    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
    @Override
    public String toString() {
        return super.toString() + ", File Size: " + fileSize + " MB";
    }
}

