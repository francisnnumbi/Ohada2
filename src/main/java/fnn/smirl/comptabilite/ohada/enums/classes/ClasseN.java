package fnn.smirl.comptabilite.ohada.enums.classes;
import java.util.*;

public class ClasseN extends HashMap<Integer, String>
{
	protected  String label = "";
	public final String getLabel(){
		return label;
	}
	
	public String getAt(int index){
		Set<Integer> set = keySet();
		ArrayList<Integer> ali = new ArrayList<Integer>();
		Iterator<Integer> iter = set.iterator();
		while(iter.hasNext()){
			ali.add(iter.next());
		}
		Collections.sort(ali);
		int m = ali.get(index);
		return m + " : " + get(m);
	}
}
