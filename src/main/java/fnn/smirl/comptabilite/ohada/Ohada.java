package fnn.smirl.comptabilite.ohada;
import java.util.*;
import fnn.smirl.comptabilite.ohada.plan.*;
import fnn.smirl.comptabilite.gui.*;
import fnn.smirl.comptabilite.ohada.operations.*;
import fnn.smirl.comptabilite.ohada.groupes.*;
import fnn.smirl.comptabilite.ohada.enums.*;

public class Ohada {
	public ArrayList<Compte> comptes = new ArrayList<Compte>();
	public ArrayList<Ecriture> ecritures = new ArrayList<Ecriture>();
	public transient Actif actif = new Actif();
	public transient Passif passif = new Passif();
	public transient Charge charge = new Charge();
	public transient Produit produit = new Produit();

	public Ohada() {
		refresh();

	}

public void refresh(){
	actif.clear();
	passif.clear();
	actif.ajoute(initActifPostes());
	passif.ajoute(initPassifPostes());


	loadCompteToPoste();
}

	private void loadCompteToPoste() {
		// TODO: Implement this method
		for (Compte c : comptes) {
			if (c.categorie == Categorie.ACTIF) {
				for (Poste p : actif.postes()) {
					if (p.reference.equalsIgnoreCase(c.posteReference)) {
						p.ajoute(c);
					}
				}
			}
			//
			if (c.categorie == Categorie.PASSIF) {
				for (Poste p : passif.postes()) {
					if (p.reference.equalsIgnoreCase(c.posteReference)) {
						p.ajoute(c);
					}
				}
			}
			//
			if (c.categorie == Categorie.CHARGE) {
				for (Poste p : charge.postes()) {
					if (p.reference.equalsIgnoreCase(c.posteReference)) {
						p.ajoute(c);
					}
				}
			}
			//
			if (c.categorie == Categorie.PRODUIT) {
				for (Poste p : produit.postes()) {
					if (p.reference.equalsIgnoreCase(c.posteReference)) {
						p.ajoute(c);
					}
				}
			}
			//
		}
	}

	public ArrayList<Integer> compteIds() {
		ArrayList<Integer> ali = new ArrayList<Integer>();
		for (Compte c : comptes) {
			ali.add(c.id());
		}
		return ali;
	}

	private ArrayList<Integer> availableEcrituresIds() {
		ArrayList<Integer> ali = new ArrayList<Integer>();
		for (Ecriture e : ecritures) {
			ali.add(e.id);
		}
		return ali;
	}

	public int nextAvailableEcritureId() {
		int i = 0;
		do{
			i++;
		}while(availableEcrituresIds().contains(i));
		return i;
	}

	public String compteTitre(int id) {
		String t = "";
		for (Compte c : comptes) {
			if (c.id() == id) t = c.titre();
		}
		return t;
	}

	public Compte compte(int id) {
		Compte t = null;
		for (Compte c : comptes) {
			if (c.id() == id) t = c;
		}
		return t;
	}

	public void ajouterCompte() {
		comptes.add(MainActivity.compteActif);
	}

	public void effacerCompte(int precId) {
		Iterator<Compte> iter = comptes.iterator();
		while (iter.hasNext()) {
			if (iter.next().id() == precId) iter.remove();
		}
	}

	private ArrayList<Poste> initActifPostes() {
		ArrayList<Poste> pos = new ArrayList<Poste>();
		pos.add(new Poste("AA", Categorie.ACTIF, "CHARGES IMMOBILISEES"));
		pos.add(new Poste("AD", Categorie.ACTIF, "IMMOBILISATIONS INCORPORELLES"));
		pos.add(new Poste("AI", Categorie.ACTIF, "IMMOBILISATIONS CORPORELLES"));
		pos.add(new Poste("AP", Categorie.ACTIF, "AVANCES ET ACOMPTES VERSES SUR IMMOBILISATIONS"));
		pos.add(new Poste("AQ", Categorie.ACTIF, "IMMOBILISATIONS FINANCIERES"));
		pos.add(new Poste("BA", Categorie.ACTIF, "ACTIF CIRCULANT H.A.O."));
		pos.add(new Poste("BB", Categorie.ACTIF, "STOCKS"));
		pos.add(new Poste("BG", Categorie.ACTIF, "CREANCES ET EMPLOIS ASSIMILES"));
		pos.add(new Poste("00", Categorie.ACTIF, "TRESORERIE-ACTIF"));

		return pos;
	}

	private ArrayList<Poste> initPassifPostes() {
		ArrayList<Poste> pos = new ArrayList<Poste>();
		pos.add(new Poste("CA", Categorie.PASSIF, "CAPITAL"));
		pos.add(new Poste("CC", Categorie.PASSIF, "PRIMES ET RESERVES"));
		pos.add(new Poste("CI", Categorie.PASSIF, "RESULTAT NET DE L'EXERCICE"));
		pos.add(new Poste("CK", Categorie.PASSIF, "AUTRES CAPITAUX PROPRES"));
		pos.add(new Poste("CP", Categorie.PASSIF, "DETTES FINANCIERES ET RESSOURCES ASSIMILEES"));
		pos.add(new Poste("00", Categorie.PASSIF, "PASSIF CIRCULANT"));
		pos.add(new Poste("00", Categorie.PASSIF, "TRESORERIE-PASSIF"));

		return pos;
	}
}
