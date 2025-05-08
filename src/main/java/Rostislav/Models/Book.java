package Rostislav.Models;

public class Book extends PrintedProduct {
    private final String author;
    
    Book(BookBuilder b) {
		super(b);
		this.author = b.author;	
	}

    public String getAuthor() { return author; }

    @Override
    public int getId(){ return super.getId(); }
    @Override
    public String getTitle() { return super.getTitle(); }
    @Override
    public double getPrice() { return super.getPrice(); }
    
    
    @Override
    public String toString() {
        return super.toString() + ", Author: " + author;
    }
    
    public static class BookBuilder extends PrintedProductBuilder{
		protected String author;
	    
		@Override
		public BookBuilder id(int id2) {
			this.id=id2;
			return this;
		}
		@Override
		public BookBuilder title(String title2) {
			this.title=title2;
			return this;
		}
		@Override
		public BookBuilder price(double price2) {
			this.price=price2;
			return this;
		}
		
		public BookBuilder author(String author2) {
			this.author = author2;
			return this;
		}
		
		public Book build(){
			return new Book(this);
		}
		

	}
}