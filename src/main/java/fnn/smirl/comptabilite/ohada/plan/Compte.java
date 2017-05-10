package fnn.smirl.comptabilite.ohada.plan;
import fnn.smirl.comptabilite.ohada.enums.*;
import java.util.*;
import fnn.smirl.comptabilite.ohada.operations.*;

public class Compte {
	public String reference = "", posteReference = "";
	public Categorie categorie = Categorie.ACTIF;
	private Classe classe;
	private String sCptTitre = "", divTitre = "";
	private int cptId = 0, sCptId = 0, divId = 0;
	protected double solde= 0.0d, soldeInit = 0d;
	protected ArrayList<Transaction> trans =
	new ArrayList<Transaction>();

	public Compte(Classe classe, int cptId, int sCptId, int divId, String sCptTitre, String divTitre) {
		this.classe = classe;
		this.cptId = cptId;
		this.sCptId = sCptId;
		this.sCptTitre = sCptTitre;
		this.divId = divId;
		this.divTitre = divTitre;
	}

	public String cptTitre() {
		String _titre = "";
		int tmp = Integer.parseInt(classe.getId() + "" + cptId);
		switch (classe.getId()) {
			case 1:
				_titre = Classe.classe1.get(tmp);
				break;
			case 2:
				_titre = Classe.classe2.get(tmp);
				break;
			case 3:
				_titre = Classe.classe3.get(tmp);
				break;
			case 4:
				_titre = Classe.classe4.get(tmp);
				break;
			case 5:
				_titre = Classe.classe5.get(tmp);
				break;
			case 6:
				_titre = Classe.classe6.get(tmp);
				break;
			case 7:
				_titre = Classe.classe7.get(tmp);
				break;
			case 8:
				_titre = Classe.classe8.get(tmp);
				break;
			case 9:
				_titre = Classe.classe9.get(tmp);
				break;
		}
		return _titre;
	}
	
	public ArrayList<Transaction> transactions(){
		return trans;
	}
	
	public ArrayList<Integer> transactionsIds(){
		ArrayList<Integer> ali = new ArrayList<Integer>();
		for (Transaction t : trans) {
			ali.add(t.getId());
		}
		return ali;
	}

	public void metSCptTitre(String sCptTitre) {
		this.sCptTitre = sCptTitre;
	}

	public String sCptTitre() {
		return sCptTitre;
	}

	public void metDivTitre(String divTitre) {
		this.divTitre = divTitre;
	}

	public String divTitre() {
		return divTitre;
	}

	public void metCptId(int cptId) {
		this.cptId = cptId;
	}

	public int cptId() {
		return cptId;
	}

	public void metSCptId(int sCptId) {
		this.sCptId = sCptId;
	}

	public int sCptId() {
		return sCptId;
	}

	public void metDivId(int divId) {
		this.divId = divId;
	}

	public int divId() {
		return divId;
	}

	public String titre() {
		return String.format("%s %s %s", cptTitre(), sCptTitre, divTitre);
	}
	
	
	public Classe classe() {
		return classe;
	}

	public void ajoute(Entree entree) {
		int i = entree.id;
		boolean b = false;
		for (Transaction t : trans) {
			if (t.getId() == i) {
				b = t.ajoute(entree);
			}
		}
		if (b == false) {
			Transaction t = new Transaction(i);
			t.ajoute(entree);
			trans.add(t);
		}
		consolidate();
	}
	
	public void efface(Entree entree){
		Iterator<Transaction> iter = trans.iterator();
		while (iter.hasNext()) {
		Transaction t =iter.next();
		t.efface(entree);
		if(t.size() == 0)iter.remove();
		}
	}

	public int id() {
		return Integer.parseInt(classe.getId() + "" +
		cptId + "" + sCptId + "" + divId);
	}
	
	public void met(Classe classe, int cptId, int sCptId, int divId, String sCptTitre, String divTitre) {
		this.classe = classe;
		this.cptId = cptId;
		this.sCptId = sCptId;
		this.sCptTitre = sCptTitre;
		this.divId = divId;
		this.divTitre = divTitre;
		
	}

	public void metCompte(Compte compte) {
		this.reference = compte.reference;
		this.classe = compte.classe;
		this.cptId = compte.cptId;
		this.sCptId = compte.sCptId;
		this.sCptTitre = compte.sCptTitre;
		this.divId = compte.divId;
		this.divTitre = compte.divTitre;
		this.solde = compte.solde;
		this.soldeInit = compte.soldeInit;
		trans.addAll(compte.trans);
//		for(Transaction t : compte.trans){
//			trans.add(t);
//		}
	}

	public Compte compte() {
		return this;
	}
	@Override
	public String toString() {
		// TODO: Implement this method
		return "[" + id() + " : " + titre() + " = " + solde + "]";
	}

	public double solde() {
		consolidate();
		if(categorie == Categorie.ACTIF || categorie == Categorie.CHARGE){
			return solde;
		}
		return Math.abs(solde);
	}
	
//	public double soldeAbs() {
//		return Math.abs(solde());
//	}
//
	public double soldeInit() {
		return soldeInit;
	}

	public void metSoldeInitial(double amnt) {
		soldeInit = amnt;
	}

	private void consolidate() {
		double tmp = 0;
		if (trans.size() < 1) {
			tmp = soldeInit;
		} else {
			trans.get(0).metSoldeInitial(soldeInit);
			tmp = trans.get(0).soldeFinal();
			for (int i = 1; i < trans.size(); i++) {
				trans.get(i).metSoldeInitial(tmp);
				tmp = trans.get(i).soldeFinal();
			}
		}
		solde = tmp;
	}

	}
