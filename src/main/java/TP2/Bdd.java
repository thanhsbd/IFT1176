/*
    IFT 1176 - TP2

    Auteur: Quoc Thanh Nguyen
    Matricule: 20353086

    Ce programme permet de gerer une banque de donnees de maneges et des parcs d'attractions ou ils sont situes.
*/
package TP2;

import java.util.*;

public class Bdd {

    private Map<String, ArrayList<Manege>> manegesParOrdreAjout = new LinkedHashMap<String, ArrayList<Manege>>();
    private Map<Manege, TreeSet<String>> manegesParNom = new TreeMap<Manege, TreeSet<String>>();

    /**
     * Ajoute un manege a un parc dans les deux structures de donnees internes.
     * Si le parc ou le manege n'existe pas encore, il est cree automatiquement.
     *
     * @param m    le manege a ajouter
     * @param parc le nom du parc auquel associer le manege
     */
    public void addManege(Manege m, String parc) {
        // Cree la liste du parc si c'est la premiere fois qu'on le rencontre
        if (!manegesParOrdreAjout.containsKey(parc)) {
            manegesParOrdreAjout.put(parc, new ArrayList<Manege>());
        }
        // Ajoute le manege a la liste du parc en preservant l'ordre d'insertion
        manegesParOrdreAjout.get(parc).add(m);

        // Cree l'entree du manege s'il n'a pas encore ete vu
        if (!manegesParNom.containsKey(m)) {
            manegesParNom.put(m, new TreeSet<String>());
        }
        // Associe ce parc au manege 
        manegesParNom.get(m).add(parc);
    }   

    /**
     * Retourne la liste des maneges appartenant a un parc, dans l'ordre d'ajout.
     * Retourne une liste vide si le parc est inconnu.
     *
     * @param parc le nom du parc recherche
     * @return une liste non modifiable des maneges du parc
     */
    public List<Manege> listeDuParc(String parc) {
        // Le parc est inconnu: retourne une liste vide non modifiable plutot que null
        if (!manegesParOrdreAjout.containsKey(parc)) {
            return Collections.unmodifiableList(new ArrayList<Manege>());
        }
        // Retourne la liste en lecture seule pour proteger la structure interne
        return Collections.unmodifiableList(manegesParOrdreAjout.get(parc));
    }

    /**
     * Retourne l'ensemble des parcs ou se trouve un manege donne, tries alphabetiquement.
     * Retourne un ensemble vide si le manege est inconnu.
     *
     * @param m le manege dont on cherche les emplacements
     * @return un ensemble non modifiable de noms de parcs
     */
    public Set<String> emplacementsManege(Manege m) {
        // Le manege est inconnu: retourne un ensemble vide immutable plutot que null
        if (!manegesParNom.containsKey(m)) {
            return Set.of();
        }
        // Retourne l'ensemble des parcs en lecture seule pour proteger la structure interne
        return Collections.unmodifiableSet(manegesParNom.get(m));
    }

    /**
     * Recherche et retourne l'objet Manege complet correspondant a un manege donne (par egalite de nom).
     * Utile pour retrouver les details d'un manege a partir d'un objet bidon ne contenant que le nom.
     *
     * @param m le manege servant de cle de recherche (seul le nom est utilise)
     * @return l'objet Manege trouve, ou null si aucun manege correspondant n'existe
     */
    public Manege getManege(Manege m) {
        // Premiere passe: cherche parmi les cles de manegesParNom (un seul objet par manege unique)
        for (Manege manege : manegesParNom.keySet()) {
            if (manege.equals(m)) {
                return manege;
            }
        }
        // Deuxieme passe : parcourt toutes les listes de parcs
        for (ArrayList<Manege> liste : manegesParOrdreAjout.values()) {
            for (Manege manege : liste) {
                if (manege.equals(m)) {
                    return manege;
                }
            }
        }
        // Aucun manege correspondant trouve
        return null;
    }

    /**
     * Calcule la frequence de chaque manege, c'est-a-dire le nombre de parcs distincts
     * dans lesquels il apparait.
     *
     * @return une TreeMap triee par nom de manege, associant chaque manege a son nombre d'emplacements
     */
    public TreeMap<Manege, Integer> frequence() {
        TreeMap<Manege, Integer> resultat = new TreeMap<Manege, Integer>();
        // La taille du TreeSet de parcs donne directement le nombre d'emplacements du manege
        for (Manege manege : manegesParNom.keySet()) {
            resultat.put(manege, manegesParNom.get(manege).size());
        }
        return resultat;
    }

    /**
     * Retourne une representation textuelle de la base de donnees.
     * Affiche chaque parc suivi de ses maneges, dans l'ordre d'ajout.
     *
     * @return une chaine de caracteres listant tous les parcs et leurs maneges
     */
    public String toString() {
        String resultat = "";
        // Parcourir les parcs dans l'ordre d'insertion 
        for (String parc : manegesParOrdreAjout.keySet()) {
            resultat += parc + ":\n";
            // Indente chaque manege du parc avec deux espaces
            for (Manege manege : manegesParOrdreAjout.get(parc)) {
                resultat += "  " + manege + "\n";
            }
        }
        return resultat;
    }

    /**
     * Retourne les noms de tous les parcs enregistres, dans l'ordre d'ajout.
     *
     * @return un tableau de chaines contenant les noms des parcs
     */
    public String[] getNomsParcs() {
        return manegesParOrdreAjout.keySet().toArray(new String[0]);
    }

    /**
     * Retourne les noms de tous les maneges enregistres, tries alphabetiquement.
     *
     * @return un tableau de chaines contenant les noms des maneges
     */
    public String[] getNomsManeges() {
        String[] noms = new String[manegesParNom.size()];
        int i = 0;
        for (Manege m : manegesParNom.keySet()) {
            noms[i++] = m.getNom();
        }
        return noms;
    }
}
