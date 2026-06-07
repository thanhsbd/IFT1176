/*
    IFT 1176 - TP1

    Auteur: Quoc Thanh Nguyen
    Matricule: 20353086

    Ce programme permet de gerer une banque de donnees de manèges et des parcs d'attractions ou ils sont situes.
*/
package TP1;

import java.util.*;

public class Bdd implements Signature {

    private Map<String, ArrayList<Manege>> manegesParOrdreAjout = new LinkedHashMap<String, ArrayList<Manege>>();
    private Map<Manege, TreeSet<String>> manegesParNom = new TreeMap<Manege, TreeSet<String>>();

    public void addManege(Manege m, String parc) {
        if (!manegesParOrdreAjout.containsKey(parc)) {
            manegesParOrdreAjout.put(parc, new ArrayList<Manege>());
        }
        manegesParOrdreAjout.get(parc).add(m);

        if (!manegesParNom.containsKey(m)) {
            manegesParNom.put(m, new TreeSet<String>());
        }
        manegesParNom.get(m).add(parc);
    }   

    public List<Manege> listeDuParc(String parc) {
        if (!manegesParOrdreAjout.containsKey(parc)) {
            return Collections.unmodifiableList(new ArrayList<Manege>());
        }
        return Collections.unmodifiableList(manegesParOrdreAjout.get(parc));
    }

    public Set<String> emplacementsManege(Manege m) {
        if (!manegesParNom.containsKey(m)) {
            return Set.of();
        }
        return Collections.unmodifiableSet(manegesParNom.get(m));
    }

    public Manege getManege(Manege m) {
        for (Manege manege : manegesParNom.keySet()) {
            if (manege.equals(m)) {
                return manege;
            }
        }
        for (ArrayList<Manege> liste : manegesParOrdreAjout.values()) {
            for (Manege manege : liste) {
                if (manege.equals(m)) {
                    return manege;
                }
            }
        }
        return null;
    }

    public Map<Manege, Integer> frequence() {
        Map<Manege, Integer> resultat = new HashMap<Manege, Integer>();
        for (Manege manege : manegesParNom.keySet()) {
            resultat.put(manege, manegesParNom.get(manege).size());
        }
        return resultat;
    }

    public String toString() {
        String resultat = "";
        for (String parc : manegesParOrdreAjout.keySet()) {
            resultat += parc + ":\n";
            for (Manege manege : manegesParOrdreAjout.get(parc)) {
                resultat += "  " + manege + "\n";
            }
        }
        return resultat;
    }
}
