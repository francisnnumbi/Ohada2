package fnn.smirl.comptabilite.ohada.enums.classes;

import java.util.*;

public final class ClassesRef 
{
	private ArrayList<ClasseN> classeDef;
	public ClassesRef(){
	classeDef = new ArrayList<ClasseN>();
	classeDef.add(new Classe1());
	classeDef.add(new Classe2());
	classeDef.add(new Classe3());
	classeDef.add(new Classe4());
	classeDef.add(new Classe5());
	classeDef.add(new Classe6());
	classeDef.add(new Classe7());
	classeDef.add(new Classe8());
	classeDef.add(new Classe9());
	}
	
	public ArrayList<ClasseN> classeDef(){
		return classeDef;
	}
}
