package Rostislav.Models;

public class EBook extends Book {
    private final double fileSize;
    
    EBook(EBookBuilder b) {
		super(b);
		this.fileSize = b.fileSize;	
	}
    
    public double getFileSize() { return fileSize; }
    
    @Override
    public int getId(){ return super.getId(); }
    @Override
    public String getTitle() { return super.getTitle(); }
    @Override
    public double getPrice() { return super.getPrice(); }
    @Override
    public String getAuthor() { return super.getAuthor(); }

    @Override
    public String toString() {
        return super.toString() + ", File Size: " + fileSize + " MB";
    }
    
    public static class EBookBuilder extends BookBuilder{
    	protected double fileSize;
	    
    	@Override
    	public EBookBuilder author(String author2) {
			this.author = author2;
			return this;
		}
    	
    	public EBookBuilder fileSize(double fileSize2) {
			this.fileSize = fileSize2;
			return this;
		}
		
		public EBook build(){
			return new EBook(this);
		}
	}
}

