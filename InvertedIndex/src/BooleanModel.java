import java.util.*;

public class BooleanModel implements RetrievalModel
{
	HashMap<String, DictEntry> index = null;
	List <Document>documents = null;
	
	BooleanModel(HashMap<String, DictEntry> index ,List <Document>documents )
	{
		this.index = index;
		this.documents = documents ;		
	}
	
	@Override
	public void retrieveDocuments(String query)
	{
		List<String> queryTerms = Arrays.asList(query.split("\\s+"));
		for (String t : queryTerms)
		{
			if(!(index.containsKey(t)))
			{
				System.out.println("There is at least one term in the query is not in the index");
				return;
			}
		}
		Set <Integer> docs = searchInIndex(queryTerms);
		showResults(docs);
	}
	
	public Set<Integer> searchInIndex(List<String> queryTerms) 
	{
		Set<Integer> docs = new HashSet<Integer>();
		for (String term : queryTerms)
		{
			if(index.containsKey(term))
			{
				Posting p = index.get(term).pList;
			    for(int i=0;i<index.get(term).doc_freq;i++)
			    {
			    	docs.add(index.get(term).pList.docId);
			    	index.get(term).pList = index.get(term).pList.next;
			    }
			    index.get(term).pList=p;  
			}

		}
		return docs;
	}

	public void showResults(Set <Integer> docs)
	{
		for(int d :docs)
		{
			System.out.println("Document "+d);
		}
		
	}

	
	
}
