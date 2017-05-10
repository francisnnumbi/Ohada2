package fnn.smirl.comptabilite.ohada.operations;
import java.util.*;
import fnn.smirl.comptabilite.ohada.plan.*;
import android.util.*;

public class Ecriture {

	public int id;
	public long date = 0l;
	public String libele = "";
	private HashSet<Entree> entrees;
	private HashSet<Integer> compteIds;

	public Ecriture(int id, long date, String libele) {
		this.id = id;
		this.date = date;
		this.libele = libele;
		entrees = new HashSet<Entree>();
		compteIds = new HashSet<Integer>();
	}

	public void met(int id, long date, String libele) {
		this.id = id;
		this.date = date;
		this.libele = libele;
	}

	public boolean ajoute(Compte cpt, double mnt, Entree.Cote cote) {
		Entree e = new Entree(id, date, cpt.id(), libele, mnt, cote);
		boolean bo = entrees.add(e);
		if (bo) {
			cpt.ajoute(e);
			compteIds.add(cpt.id());
		}
		return bo;
	}

//	private void compteEffaceEntree(Entree entree) {
//		try{
//			Iterator<Compte> iter = comptes.iterator();
//			while (iter.hasNext()) {
//				Compte e = iter.next();
//				e.efface(entree);
//			}
//		} catch(NullPointerException npe){
//			Log.e("Ecriture", npe.toString());
//		}
//	}

	public void efface(Entree entree) {
		Iterator<Entree> iter = entrees.iterator();
		while (iter.hasNext()) {
			Entree e = iter.next();
			if (e.equals(entree)) {
			//	compteEffaceEntree(entree);
				iter.remove();
			}
		}
	}

	public HashSet<Entree> entrees(Entree.Cote cote) {
		HashSet<Entree> set = new HashSet<Entree>();
		for (Entree e : entrees) {
			if (e.cote == cote)set.add(e);
		}
		return set;
	}

	public ArrayList<Entree> entrees() {
		ArrayList<Entree> ale = new ArrayList<Entree>();
		for (Entree e : entrees) {
			ale.add(e);
		}
		return ale;
	}

	public boolean equilibre() {
		return miniSomme(Entree.Cote.DEBIT) == miniSomme(Entree.Cote.CREDIT);
	}

	private double miniSomme(Entree.Cote cote) {
		double ms = 0;
		for (Entree e : entrees(cote)) {
			ms += e.montant;
		}
		return ms;
	}

}
