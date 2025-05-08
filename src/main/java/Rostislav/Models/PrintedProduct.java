package Rostislav.Models;

public class PrintedProduct {
	private final int id;
	private final String title;
    private final double price;
    
    public int getId(){ return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return "Title: " + title + ", Price: " + price;
    }
    
    PrintedProduct(PrintedProductBuilder b) {
		this.id=b.id;
		this.title=b.title;
		this.price=b.price;
	}
	
	public static class PrintedProductBuilder{
		protected int id;
		protected String title;
		protected double price;
	    
	    
		public PrintedProductBuilder id(int id2) {
			this.id=id2;
			return this;
		}
		public PrintedProductBuilder title(String title2) {
			this.title=title2;
			return this;
		}
		public PrintedProductBuilder price(double price2) {
			this.price=price2;
			return this;
		}

		public PrintedProduct build(){
			return new PrintedProduct(this);
		}

	}
}