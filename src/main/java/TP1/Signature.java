package TP1;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface Signature {
	
	//Prévoyez un constructeur pour la classe manege
	//Pour traiter une demandes comme `
	//m = new Manege(nom,hauteur, vitesse);
	public void addManege(Manege m, String parc) ;
	
	public List<Manege> listeDuParc(String parc);
	
	//Prévoyez un constructeur pour la classe manege
	//Pour créer un objet bidon servant à la recherche comme suit:`
	//m = new Manege(nom);
	public Set<String> emplacementsManege(Manege m);	
	public Manege getManege(Manege m) ;
	
	public Map<Manege,Integer> frequence();

}
