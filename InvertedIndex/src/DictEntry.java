
public class DictEntry
{
	int doc_freq = 0; // number of documents that contain the term
	int term_freq = 0; //number of times the term is mentioned in the collection
	Posting pList = null;
	
	boolean is_exist (int doc_Id)
	{
		Posting p = pList;
		while(p != null)
		{
			if(p.docId == doc_Id)
			{
				p.dtf++;
				return true;
			}
			else
			{
				p= p.next;
			}
		}
		return false;
	}
	
	void addNewPosting(int doc_Id)
	{
		Posting p = pList;
		while(p.next != null)
		{
			p = p.next;
		}
		p.next = new Posting(doc_Id);
	}
}
