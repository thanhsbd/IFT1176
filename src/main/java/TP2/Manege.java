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

    public Manege(String n, double h, double v) {
        nom = n;
        hauteur = h;
        vitesse = v;
    }

    public Manege(String n) {
        this(n, 0, 0);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Manege) {
            Manege autre = (Manege) obj;
            return nom.equals(autre.nom);
        }
        return false;
    }

    public int hashCode() {
        return nom.hashCode();
    }

    public String toString() {
        return "Nom du manege: " + nom + " | Hauteur: " + hauteur + " | Vitesse: " + vitesse;
    }

    public int compareTo(Manege autre) {
        return nom.compareTo(autre.nom);
    }

    public String getNom() {
        return nom;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getVitesse() {
        return vitesse;
    }

}
