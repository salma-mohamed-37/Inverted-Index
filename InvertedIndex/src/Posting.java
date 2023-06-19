
public class Posting
{
	public Posting next = null;
	int docId;
	int dtf = 1; // document term frequency
	
	Posting(int docId)
	{
		this.docId= docId;
	}
}
