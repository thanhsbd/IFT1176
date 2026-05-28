package TP1;

public class Manege {
    private String nom;
    private int hauteur, vitesse;

    public Manege(String n, int h, int v) {
        nom = n;
        hauteur = h;
        vitesse = v;
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

}
