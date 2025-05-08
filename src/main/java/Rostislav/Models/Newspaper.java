package Rostislav.Models;

public class Newspaper extends PrintedProduct {
    private final String date;
    
    Newspaper(NewspaperBuilder b) {
		super(b);
		this.date = b.date;	
	}

    public String getDate() { return date; }
    
    @Override
    public int getId(){ return super.getId(); }
    @Override
    public String getTitle() { return super.getTitle(); }
    @Override
    public double getPrice() { return super.getPrice(); }
    
    @Override
    public String toString() {
        return super.toString() + ", Date: " + date;
    }
    
    public static class NewspaperBuilder extends PrintedProductBuilder{
		protected String date;
		
		@Override
		public NewspaperBuilder id(int id2) {
			this.id=id2;
			return this;
		}
		@Override
		public NewspaperBuilder title(String title2) {
			this.title=title2;
			return this;
		}
		@Override
		public NewspaperBuilder price(double price2) {
			this.price=price2;
			return this;
		}
	    
		public NewspaperBuilder date(String date2) {
			this.date = date2;
			return this;
		}
		
		public Newspaper build(){
			return new Newspaper(this);
		}

	}   
}