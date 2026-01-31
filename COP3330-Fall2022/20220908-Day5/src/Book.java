
public class Book {
	/********************
	 * Instance Variables
	 ********************/
	private String title;
	private String author;
	private int pages;
	private int currentPage;
	
	/********************
	 * Constructor
	 ********************/
	public Book(String title, int pages) {
		this.title = title;
		this.author = "Unknown";
		this.pages = pages;
		this.currentPage = 0;
	}
	
	public Book(String title, String author, int pages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.currentPage = 0;
	}
	
	public Book(String title, String author, int pages, int currentPages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.currentPage = 0;
	}
	/*******************
	 * Instance Methods
	 *******************/
	public void read(int pages) {
		this.currentPage += pages;
		if(this.finished()) {
			this.currentPage = this.pages;
		}
	}
	
	public void goToPage(int value) {
		this.currentPage = value;
	}
	
	public int pagesUntilEnd() {
		return this.pages - this.currentPage;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public int getPage() {
		return this.currentPage;
	}
	
	public boolean finished() {
		return false;
	}
	
}
