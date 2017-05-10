package fnn.smirl.comptabilite.ohada.enums;
import fnn.smirl.comptabilite.ohada.enums.classes.*;
import java.util.*;

public enum Classe
{
	Un(1, "Ressources durables"),
	Deux(2, "Actif Immobilisé"),
	Trois(3, "Stocks"),
	Quatre(4, "Tiers"),
	Cinq(5, "Trésorerie"),
	Six(6, "Charges des Activités Ordinaires"),
	Sept(7, "Produits des Activités Ordinaires"),
	Huit(8, "Autres Charges et Autres Produits"),
	Neuf(9, "Engagements Hors Bilan et Comptabilité Analytique de Gestion");
	private int id;
	private String titre;
	private Classe(int id, String titre){
		this.id = id;
		this.titre = titre;
	}

	public int getId(){
		return id;
	}

	public String getTitre(){
		return titre;
	}
	
	public static Classe classeDu(int id){
		for(Classe c : values()){
			if(c.id == id) return c;
		}
		return null;
	}
	
	
	
	public static Classe1 classe1 = new Classe1();
	public static Classe2 classe2 = new Classe2();
	public static Classe3 classe3 = new Classe3();
	public static Classe4 classe4 = new Classe4();
	public static Classe5 classe5 = new Classe5();
	public static Classe6 classe6 = new Classe6();
	public static Classe7 classe7 = new Classe7();
	public static Classe8 classe8 = new Classe8();
	public static Classe9 classe9 = new Classe9();
	
	
}
