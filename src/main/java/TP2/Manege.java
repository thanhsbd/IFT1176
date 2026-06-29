/*
    IFT 1176 - TP2

    Auteur: Quoc Thanh Nguyen
    Matricule: 20353086

    Classe representant un manege caracterise par un nom, une hauteur (pieds) et une vitesse (mph).
    Deux maneges sont consideres egaux s'ils ont le meme nom.
*/
package TP2;

public class Manege implements Comparable<Manege> {
    private String nom;
    private double hauteur, vitesse;

    /**
     * Cree un manege avec un nom, une hauteur et une vitesse.
     *
     * @param n le nom du manege
     * @param h la hauteur du manege en pieds
     * @param v la vitesse du manege en mph
     */
    public Manege(String n, double h, double v) {
        nom = n;
        hauteur = h;
        vitesse = v;
    }

    /**
     * Cree un manege avec uniquement un nom (hauteur et vitesse a 0).
     * Utile pour creer un objet bidon servant de cle de recherche.
     *
     * @param n le nom du manege
     */
    public Manege(String n) {
        // Delegue au constructeur principal avec des valeurs neutres
        this(n, 0, 0);
    }

    /**
     * Verifie l'egalite entre ce manege et un autre objet.
     * Deux maneges sont consideres egaux s'ils ont le meme nom.
     *
     * @param obj l'objet a comparer
     * @return true si obj est un Manege avec le meme nom, false sinon
     */
    public boolean equals(Object obj) {
        // Verifie que l'objet est bien une instance de Manege
        if (obj instanceof Manege) {
            Manege autre = (Manege) obj;
            // L'egalite est basee uniquement sur le nom
            return nom.equals(autre.nom);
        }
        return false;
    }

    /**
     * Retourne le code de hachage du manege, base uniquement sur son nom.
     * Coherent avec equals().
     *
     * @return le code de hachage de la chaine du nom
     */
    public int hashCode() {
        return nom.hashCode();
    }

    /**
     * Retourne une representation textuelle du manege.
     *
     * @return une chaine au format "Nom du manege: X | Hauteur: X | Vitesse: X"
     */
    public String toString() {
        return "Nom du manege: " + nom + " | Hauteur: " + hauteur + " | Vitesse: " + vitesse;
    }

    /**
     * Compare ce manege a un autre selon l'ordre alphabetique du nom.
     * Permet le tri naturel des maneges dans les collections ordonnees.
     *
     * @param autre le manege a comparer
     * @return un entier negatif, zero ou positif si ce manege est
     *         respectivement avant, egal ou apres l'autre dans l'ordre alphabetique
     */
    public int compareTo(Manege autre) {
        // Compare les nom avec le fonction compareTo de String
        return nom.compareTo(autre.nom);
    }

    /**
     * Retourne le nom du manege.
     *
     * @return le nom du manege
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la hauteur du manege en pieds.
     *
     * @return la hauteur en pieds
     */
    public double getHauteur() {
        return hauteur;
    }

    /**
     * Retourne la vitesse du manege en mph.
     *
     * @return la vitesse en mph
     */
    public double getVitesse() {
        return vitesse;
    }

}
