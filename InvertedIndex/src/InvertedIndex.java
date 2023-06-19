import java.io.*;
import java.util.*;

public class InvertedIndex 
{
	HashMap<String, DictEntry> index = new HashMap<String, DictEntry> ();
	
	public void buildIndex(List<Document> documents) throws IOException
	{
		for(Document d: documents)
		{
			FileReader file = new FileReader(d.name);
			BufferedReader buffer = new BufferedReader(file);
			//array contains stop words
			List <String> stopWords = new ArrayList<String>(Arrays.asList("too","with","the","a","to","i","and","this","so","then","or","on","of","no","in","for","only","from","between","but","by","at","as"));
			String terms [] = null;
			String s; //to hold each line
			
			while ((s = buffer.readLine()) != null)
			{
				s=s.trim().replaceAll("[\\]\\[?:/,.;\"(\\)]", ""); //removing punctuation marks from the line
		    	terms = s.split("\\s+");
				for(int i=0;i<terms.length;i++)
				{
					d.length ++;
					terms[i]=terms[i].toLowerCase(); //convert all terms to lower case
					
					if(stopWords.contains(terms[i])) //if the term is stop word don't try to add it to the index
					{
						continue;
					}
					
					else
					{
						if(index.containsKey(terms[i])) //to check if the term is in the hashmap or need to create new dictentry
						{
							index.get(terms[i]).term_freq++; //increase the number of times the term mentioned
							
							if(!index.get(terms[i]).is_exist(d.id)) //check if it is the first time for the term to be mentioned in this document 
							{
								index.get(terms[i]).doc_freq++; //increase number of documents in which this term mentioned
								index.get(terms[i]).addNewPosting(d.id); //add new posting with the document Id 
							}
							// if is_exist returned true this means that it isn't the first time for the term to be mentioned in 
							// the document 
							//so all what is need is to increase dtf which happened in is_exist 
							
						}
						else
						{
							index.put(terms[i],new DictEntry()); //it is the first time for this term to be mentioned in the collection
							index.get(terms[i]).pList = new Posting(d.id); //create a new posting for the document 
							index.get(terms[i]).term_freq++; //increase the number of times the term mentioned
							index.get(terms[i]).doc_freq++;  //increase number of documents in which this term mentioned
						}

						}
					}
			}
			file.close();
			buffer.close();
		}
	}
		
		public void printIndex() 
		{
			System.out.println("term   doc. freq. term freq. posting list");
			for(String term : index.keySet())
			{
				System.out.print(term +" "+index.get(term).doc_freq+" "+index.get(term).term_freq+" ");
				Posting p = index.get(term).pList;
				for(int i=0;i<index.get(term).doc_freq;i++)
				{
				   System.out.print("doc"+index.get(term).pList.docId+"->"+index.get(term).pList.dtf+"times  ");
				   index.get(term).pList = index.get(term).pList.next;
				}
				index.get(term).pList=p;  
				System.out.println();
			}
			
		}
}
	
