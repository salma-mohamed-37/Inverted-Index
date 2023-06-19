
import java.util.ArrayList;
import java.util.List;

public class Collection
{
	List <Document>documents = new ArrayList<Document>();
	String filesnames [] = {"software engineering.txt", "software engineering2.txt","operating systems.txt","operating systems2.txt","cloud computing.txt","cloud computing2.txt","data structures.txt","data structures2.txt", "google assistant.txt" , "google assistant2.txt"};
	
	Collection ()
	{
		int id = 1;
		for(int i=0;i<filesnames.length;i++)
		{
			Document d = new Document(filesnames[i],id);
			documents.add(d);
			id++;
		}
	}
	
	void printDocuments()
	{
		for(Document d : documents)
		{
			System.out.println(d.id+" "+d.name);
		}
	}
}
