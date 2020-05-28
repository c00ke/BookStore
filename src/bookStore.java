import java.sql.*;
import java.util.Scanner;

/** Level 3, Task 7 - Compulsory Task 2
 * 11 May 2020
 * This is a program for a bookstore clerk - They will be able to add, change, update, delete or search books with this application
 * @author Estine Van der Berg
 */
public class bookStore {
	
	/**main method
	 * @param args not used
	 */
	public static void main (String[] args) {
		
		// Menu For User
		String menuChoice = "";
		while(!menuChoice.equals("0")) {
			Scanner scan = new Scanner(System.in);
			System.out.println("\nTiny Bookstore Menu: "
					+ "\n_________________________________________\n"
					+ "\n1. Enter Book"
					+ "\n2. Update Book Stock"
					+ "\n3. Delete Book"
					+ "\n4. Search Books"
					+ "\n0. Exit"
					+ "\n_________________________________________\n");
			System.out.println("Please make your choice here: ");
			menuChoice = scan.nextLine();
			
			// Call Create Book Function which will add a new book to the database
			if (menuChoice.equals("1")) {
				createBook();
			}
			
			// Get User Input and call update QTY Function to update 
			else if (menuChoice.equals("2")) {
				
				while (true) {
					Scanner scanBook = new Scanner(System.in);
					System.out.println("\nYou Have Chosen to Update a Book Already in the System"
							 + "\nTo Continue, view the books below and choose which one you would like to update:"
							 + "\n_________________________________________\n");
					print();
					System.out.println("\nPlease enter Book ID (0000): ");
					int bookIDChoice = scanBook.nextInt();
					scanBook.nextLine();
					System.out.println("\nPlease enter new Stock Amount: ");
					
					//Call newQTY Method that will update the qty in the DB to User's input
					int newQTY = scanBook.nextInt();
					updateQTY(newQTY, bookIDChoice);
					break;
				}
			}
			
			// Delete a book
			else if (menuChoice.equals("3")) {
				
				while (true) {
					Scanner scanBook = new Scanner(System.in);
					System.out.println("\nYou Have Chosen to delete a Book Already in the System"
							 + "\nTo Continue, view the books below and choose which one you would like to delete:"
							 + "\n_________________________________________\n");
					print();
					System.out.println("\nPlease enter Book ID (0000): ");
					int bookIDChoice = scanBook.nextInt();
					scanBook.nextLine();
					
					// Call deleteBook Method which will delete the book from the DB depending on user input
					deleteBook(bookIDChoice);
					break;
				}			
			}
			
			else if (menuChoice.equals("4")) {
				
				while (true) {
					Scanner scanBook = new Scanner(System.in);
					System.out.println("\nYou Have Chosen to Search for a book in the system"
							 + "\nTo Continue, choose whether you would like to search by :"
							 + "\n_________________________________________\n"
							 + "\n1. Book Author"
							 + "\n2. Book Title:"
							 + "\n_________________________________________\n");
					System.out.println("\nAuthor or Title (1 or 2): ");
					String choiceAuthTitle = scanBook.nextLine();
				
					if (choiceAuthTitle.equals("1")) {
						System.out.println("Please enter Author's name: ");
						String inputAuthor = scanBook.nextLine();
						System.out.println("\nResults for Search by Author Name: \n");
						searchAuthor(inputAuthor);
						break;
					}
				
					else if (choiceAuthTitle.equals("2")) {
						System.out.println("Please enter Title of book: ");
						String inputTitle = scanBook.nextLine();
						System.out.println("\nResults for Search by Title Name: \n");
						searchTitle(inputTitle);
						break;
					}
				}
			}
			
			else if (menuChoice.equals("0")) {
				System.out.println("Good Bye!");
				break;
			}
		}
	} // End of Main Method

/** Method will Delete Book Based on User's Choice
 * @param bookIDChoice This is the Book ID the User inputs 
 */
private static void deleteBook(int bookIDChoice) {
	
	try {
		//Establish connection to DB
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore_db?useSSL=false", 
				"otheruser", 
				"vytoyx");
		Statement statement = connection.createStatement();
		int rowsAffected;
		
		// Query that will delete book from where ID is based on User Input
		rowsAffected = statement.executeUpdate("DELETE FROM books WHERE id = " + bookIDChoice);
		System.out.println("Completed! " + rowsAffected + " Book Deleted");
		statement.close();
		connection.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

/**Method WIll Search for Books based on the Title Name 
 * All Books with similar title will display
 * @param inputTitle Is the Title name the user has input to search
 */
private static void searchTitle(String inputTitle) {
	
	try {
		//Establish connection to DB
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore_db?useSSL=false", 
				"otheruser", 
				"vytoyx");
		Statement statement = connection.createStatement();
		ResultSet results;
	
		// Select ANY row with words similar to what user has input
		results = statement.executeQuery("SELECT * FROM books WHERE title like '%" + inputTitle + "%'");
		while (results.next()) {
			System.out.println(
					results.getInt("id") + ", "
					+ results.getString("title") + ", "
					+ results.getString ("author") + ", "
					+ results.getInt("qty")
					);
			}
		
		results.close();
		connection.close();
		statement.close();
		  
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

/** This Method will Search for books based on the Author name
 * All Books with that Author Name will display
 * @param inputAuthor Is the Author name the user has input to Search
 */
private static void searchAuthor(String inputAuthor) {
	try {
		//Establish connection to DB
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore_db?useSSL=false", 
				"otheruser", 
				"vytoyx");
		Statement statement = connection.createStatement();
		ResultSet results;
		
		// Select ANY row with words similar to what user has input
		results = statement.executeQuery("SELECT * FROM books WHERE author like '%" + inputAuthor + "%'");
		while (results.next()) {
			System.out.println(
					results.getInt("id") + ", "
					+ results.getString("title") + ", "
					+ results.getString ("author") + ", "
					+ results.getInt("qty")
					);
			}
		
		results.close();
		statement.close();
		connection.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

/** This MEthod will update the Book's qty based on the User's choices
 * 
 * @param newQTY The user's new QTY input
 * @param bookIDChoice The user's Book ID Choice, to know which Book to change
 */
private static void updateQTY(int newQTY, int bookIDChoice) {
	
	try {
		//Establish Connection with DB
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore_db?useSSL=false", 
				"otheruser", 
				"vytoyx");
		Statement statement = connection.createStatement();
		int rowsAffected;
		
		// Update the books qty column based on the user's choice
		rowsAffected = statement.executeUpdate("UPDATE books SET qty = '" + newQTY + "' WHERE id = " + bookIDChoice);
		System.out.println("\nCompleted! " + rowsAffected + " Stock Updated");
		statement.close();
		connection.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

/**This method will call the printAllFromTable method with a connection in order
 * to print out all the contents in the books Table
 */
private static void print() {
	
	try {
		//Establish Connection with DB
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore_db?useSSL=false", 
				"otheruser", 
				"vytoyx");
		Statement statement = connection.createStatement();
		ResultSet results;

		// Select all rows in books and display them
		results = statement.executeQuery("SELECT * FROM books");
		printAllFromTable(statement);
		results.close();
		statement.close();
		connection.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

/** The enterBook method takes parameters from the createBook method
 * It will then write the information (New Book) to the Database
 * @param id - The ID of the book
 * @param title - The Title of the book
 * @param author - The Author of the book
 * @param qty - The Stock of the book
 */
private static void enterBook(int id, String title, String author, int qty) {
	
	Book book = new Book (id, title, author, qty);
	try {
		
		//Establish Connection with DB
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore_db?useSSL=false", 
				"otheruser", 
				"vytoyx");
		Statement statement = connection.createStatement();
		int rowsAffected;
		
		// Use book object created to String and create a query to insert into table
		rowsAffected = statement.executeUpdate("INSERT INTO books VALUES" + book.toString());
		System.out.println("\nCompleted! " + rowsAffected + " book added");
		statement.close();
		connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

/** This method get's the new book information from user 
 * and calls the enterBook() method which will add the information to the DB
 */
private static void createBook() {
	
	Scanner newBook = new Scanner(System.in);
	System.out.println("\nYou Have Chosen to Add a Book To the System"
			+ "\nTo Continue, fill in the Details of the book"
			+ "\n_________________________________________\n"
			+ "\nBook ID: ");
	int bookID = newBook.nextInt();
	newBook.nextLine();
	System.out.println("\nBook Title: ");
	String bookTitle = newBook.nextLine();
	System.out.println("\nBook Author: ");
	String bookAuthor = newBook.nextLine();
	System.out.println("\nBook QTY: ");
	int bookQTY = newBook.nextInt();
	newBook.hasNextLine();
	
	// Call enterBook Method which will add the input to the DB
	enterBook(bookID, bookTitle, bookAuthor, bookQTY);
	}

/** This method will print all data from the table in the DB
 * 
 * @param statement Is the Statement
 * @throws SQLException If SQL Exception occurs
 */
public static void printAllFromTable(Statement statement) throws SQLException {
	ResultSet results = statement.executeQuery("SELECT id, title, author, qty FROM books");
	while (results.next()) {
		System.out.println(
				results.getInt("id") + ", "
				+ results.getString("title") + ", "
				+ results.getString ("author") + ", "
				+ results.getInt("qty")
				);
		}
	}
}// End of Class
