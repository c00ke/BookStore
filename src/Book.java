
public class Book {
	private int bookID;
	private String bookTitle;
	private String bookAuthor;
	private int bookQTY;
	
	//Constructor
	public Book (int bookID, String bookTitle, String bookAuthor, int bookQTY) {
	this.setBookID(bookID); 
	this.setBookTitle(bookTitle);
	this.setBookAuthor(bookAuthor);
	this.setBookQTY(bookQTY);
	}
	
	public Book () {
	}
	
	//Getters and Setters
	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public int getBookQTY() {
		return bookQTY;
	}

	public void setBookQTY(int bookQTY) {
		this.bookQTY = bookQTY;
	}
	
	//ToString
	public String toString() {
		String output = "(" + bookID + ", '" + bookTitle + "', '" + bookAuthor + "', " + bookQTY +")";
		return output;
	}
	
}
