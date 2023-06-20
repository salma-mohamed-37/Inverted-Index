
import java.util.*;

import java.util.Map.Entry;

public class RankedModel implements RetrievalModel
{
	HashMap<String, DictEntry> index = null;
	List <Document>documents = null;
	
	RankedModel(HashMap<String, DictEntry> index ,List <Document>documents )
	{
		this.index = index;
		this.documents = documents ;		
	}
	
	public void retrieveDocuments(String query)
	{
		query = query.toLowerCase();
		List<String> queryTerms = Arrays.asList(query.split("\\s+"));
		for (String t : queryTerms)
		{
			if(!(index.containsKey(t)))
			{
				System.out.println("There is at least one term in the query is not in the index");
				return;
			}
		}
		HashMap<String, Double> queryIndex = parseQuery(query);
		HashMap<String, Double[]> documentsIndex = calculateTfIdf(queryTerms);
		List<Entry<Integer, Double>> cosine = calculateCosineSimilarity (documentsIndex, queryIndex);
		showResults(cosine);
	}

	public HashMap<String, Double> parseQuery(String query)
	{
		HashMap<String, Double> queryIndex = new HashMap<String, Double> ();
		String terms []= null;
		terms = query.split("\\s+");
		for(int i=0;i<terms.length;i++)
		{
			if(queryIndex.containsKey(terms[i])) // if the term exists increase frequency
			{
				double count = queryIndex.get(terms[i]);
				queryIndex.remove(terms[i]);
				queryIndex.put(terms[i], count+1.0);
			}
			else //if the term doesn't exist add it with frequency 1
			{ 
				queryIndex.put(terms[i], 1.0);
			}
		}
		return queryIndex;
	}
	
	public HashMap<String,Double[]> calculateTfIdf (List<String> queryTerms)
	{
		HashMap<String , Double[]> tfIdfValues = new HashMap<String , Double[]>();
        
		for(String t : queryTerms)
		{
			Double[] empty = new Double[11];
	        Arrays.fill(empty, 0.0);
			tfIdfValues.put(t, empty);
			Posting p = index.get(t).pList;
			for (Document d : documents)
			{
				while(index.get(t).pList != null)
				{
					if(index.get(t).pList.docId == d.id) //the term in the document
					{
						double tf = index.get(t).pList.dtf / d.length;
						double idf = Math.log(10.0/index.get(t).doc_freq);
						tfIdfValues.get(t)[d.id] = tf*idf;
						break;
						
					}
					else
					{
						index.get(t).pList =index.get(t).pList.next;
					}
				}
				index.get(t).pList = p;

			}
		}
		return tfIdfValues ;
	}
	
	//it takes the index of the query and the index of a document
		public List<Entry<Integer, Double>> calculateCosineSimilarity (HashMap <String, Double[]> dVector,HashMap <String, Double> qVector)
		{
			HashMap<Integer , Double> cosine = new HashMap<Integer,Double>();
			for (int i=1 ; i<=10 ;i++)
			{
				double dotProduct = 0.0;
			    double d = 0.0;
			    double q = 0.0;

			    for (String term : qVector.keySet())
		        {
			        dotProduct += dVector.get(term)[i] * qVector.get(term) ;
			        d += Math.pow(dVector.get(term)[i], 2);
			        q += Math.pow(qVector.get(term), 2);
			    }

			    
			    if(d ==0)
			    {
			    	cosine.put(i,0.0);
			    }
			    else
			    {
			    	double result = dotProduct / (Math.sqrt(d) * Math.sqrt(q));
			    	cosine.put(i,result);
			    }
			    
			    
			}
			
			//the logic for sorting the cosine similarity descending
			List<Map.Entry<Integer,Double>> c = new ArrayList<>(cosine.entrySet()); //to put the cosine values to sort it

	        // override the compare function
	        Collections.sort(c, new Comparator<Map.Entry<Integer, Double>>()
	        {
	            public int compare(Map.Entry< Integer,Double> c1, Map.Entry<Integer,Double> c2)
	            {
	                return Double.compare(c2.getValue(), c1.getValue());
	            }
	        });
	       
	        return c;    

		}
		
		public void showResults(List<Entry<Integer, Double>> cosine)
		{
			for (Map.Entry<Integer, Double> entry : cosine) 
			{
	            System.out.println("Document "+entry.getKey() + ": " + entry.getValue());
	        }
			
		}
	
}

