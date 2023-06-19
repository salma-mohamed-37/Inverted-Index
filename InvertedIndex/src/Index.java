
import java.io.*;
import java.util.*;

public class Index
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
	public void booleanQuery(String query)
	{
		query = query.toLowerCase();
		if(index.containsKey(query))
		{
			System.out.println("The term : "+ query);
			System.out.println("Number of documents that contain the term: "+index.get(query).doc_freq+" documents");
		    System.out.println("Number of times the term is mentioned in the collection: "+index.get(query).term_freq +" times");
		    Posting p = index.get(query).pList;
		    for(int i=0;i<index.get(query).doc_freq;i++)
		    {
		    	System.out.println("The term mentioned "+index.get(query).pList.dtf+" times in document "+index.get(query).pList.docId);
		    	index.get(query).pList = index.get(query).pList.next;
		    }
		    index.get(query).pList=p;  
		}
		else
		{
			System.out.println("The term "+query+" wasn't mentioned in the collection");
		}
		 
	}
	
	public void rankedQuery(String query)
	{
		List<String> queryTerms = Arrays.asList(query.split("\\s+"));
		HashMap<Integer, Double> cosine = new HashMap<Integer, Double>() ; //contain the cosine values for the documents
	}
	/*
	public oldRanked ()
	{
		HashMap<String, DictEntry> index = new HashMap<String, DictEntry> ();
		List <Document>documents = null;
		
		RankedModel(HashMap<String, DictEntry> index ,List <Document>documents )
		{
			this.index = index;
			this.documents = documents ;		
		}
		
		public void calculateTfIdf (List<String> queryTerms)
		{
			for(String t : queryTerms)
			{
				Posting p = index.get(t).pList;
				for (Document d : documents)
				{
					while(index.get(t).pList != null)
					{
						if(index.get(t).pList.docId == d.id) //the term in the document
						{
							double tf = index.get(t).pList.dtf / d.length;
							double idf = Math.log(10/index.get(t).doc_freq);
							System.out.println("For term "+t+" Tf-idf for document "+d.id+" is "+tf*idf);
							break;
						}
						else
						{
							index.get(t).pList =index.get(t).pList.next;
						}
					}
					if(index.get(t).pList == null)
					{
						index.get(t).pList = p;
						System.out.println("This terms isn't found in document "+d.id);
					}
					else
						index.get(t).pList = p;
					
				}
				System.out.println();
			}
		}
		
		//it takes the index of the query and the index of a document
			public double calculateCosineSimilarity (HashMap <String, Double> dVector,HashMap <String, Double> qVector)
			{
				double dotProduct = 0.0;
			    double d = 0.0;
			    double q = 0.0;

			    for (String term : qVector.keySet())
		         {
			        dotProduct += dVector.get(term) * qVector.get(term) ;
			        d += Math.pow(dVector.get(term), 2);
			        q += Math.pow(qVector.get(term), 2);
			    }

			    double result = dotProduct / (Math.sqrt(d) * Math.sqrt(q));
			    return result; //the cosine similarity between query and a document
			}
		
	}*/
}
