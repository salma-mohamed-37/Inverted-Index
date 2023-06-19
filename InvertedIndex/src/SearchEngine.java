import java.io.IOException;
import java.util.Scanner;

public class SearchEngine {

	public static void main(String[] args) throws IOException
	{
		System.out.println("Welcome to search engine");
		Collection c = new Collection();
		InvertedIndex in = new InvertedIndex();
		in.buildIndex(c.documents);
		RetrievalModel rm = null ;
		String query;
		int anotherQuery = 0 ;
		int modelNum = 0 ;
		Scanner input = new Scanner(System.in);
		do
		{
			System.out.print("Enter your query: ");
			query = input.nextLine();
			do
			{
				System.out.println("Enter the number of the model you want or enter 0 to enter another query or to exit the program");
				System.out.println("1- Boolean Model");
				System.out.println("2- Ranked Model");
				System.out.print("Your choice: ");
				modelNum = input.nextInt();
				
				switch (modelNum)
				{
					case 1:
					{
						rm = new BooleanModel(in.index , c.documents);
						rm.retrieveDocuments(query);
						break;
					}
					case 2:
					{
						rm = new RankedModel(in.index , c.documents);
						rm.retrieveDocuments(query);
						break;
					}
				}
			}while (modelNum != 0);
			
			System.out.print("Do you want to enter another query enter 1 for yes , 0 for no : ");
			anotherQuery = input.nextInt();		
			input.nextLine(); // consume the newline character
		} while (anotherQuery != 0);
		input.close();
	}

}
