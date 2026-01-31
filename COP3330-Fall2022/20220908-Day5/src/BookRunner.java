
public class BookRunner {

	public static void main(String[] args) {
		Book[] library = new Book[5];
		
		library[0] = new Book("Eye of the World", "Robert Jordan", 800);
		library[1] = new Book("Thrawn", 350);
		library[2] = new Book("Star Wars thrawn Ascendancy", "Zahn", 350, 250);
		
		for(int i=0; i<library.length; i++) {
			System.out.print("Book #" + (i+1) + ": ");
		
			if(library[i] == null) {
				System.out.println("Book has not been created.");
			}
			else {
				System.out.println(library[i].getTitle() + " by " + library[i].getAuthor());
			}
		}

		/*for(int i=0; i<library.length; i++) {
			library[i] = null;
		}*/
			
			for(Book b : library) {
				System.out.print("Book: ");
			
				if(b == null)
					System.out.println("Book has not been created.");
				else {
					System.out.println(b.getTitle() + " by " + b.getAuthor());
				}
		}
	}

}
