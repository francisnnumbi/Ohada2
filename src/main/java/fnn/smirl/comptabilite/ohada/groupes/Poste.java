package fnn.smirl.comptabilite.ohada.groupes;
import fnn.smirl.comptabilite.ohada.enums.*;
import java.util.*;
import fnn.smirl.comptabilite.ohada.plan.*;

public class Poste {
	public String reference = "";
	private Categorie categorie;
	private String designation;
	ArrayList<Compte> comptes;

	public Poste(String ref, Categorie categorie, String designation) {
		reference = ref;
		this.categorie = categorie;
		this.designation = designation;
		comptes = new ArrayList<Compte>();
	}

	public Poste(Categorie categorie, String designation) {
		this("", categorie, designation);
	}

	public Categorie categorie(){
		return categorie;
	}
	
	public String designation() {
		return designation;
	}

	public boolean ajoute(Compte cpt) {
		for (Compte c : comptes) {
			if (c.id() == cpt.id())return false;
		}
		cpt.categorie = categorie;
		cpt.posteReference = reference;
		return comptes.add(cpt);
	}

	public boolean efface(Compte cpt) {
		boolean did = false;
		Iterator<Compte> iter = comptes.iterator();
		while (iter.hasNext()) {
			Compte c = iter.next();
			if (c.id() == cpt.id()) {
				cpt.posteReference = "";
				iter.remove();
				did = true;
			}
		}
		return did;
	}

	public Compte get(int idx) {
		return comptes.get(idx);
	}

	public ArrayList<Compte> comptes() {
		return comptes;
	}

	public double somme() {
		double s = 0.0d;
		if (comptes.size() == 0) {
			s = 0.0d;
		} else {
			for (Compte c : comptes) {
				s += c.solde();
			}
		}
		return s;
	}

	public int size() {
		return comptes.size();
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return reference + " : " + designation;
	}


}
