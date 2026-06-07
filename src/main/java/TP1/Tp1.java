/*
    IFT 1176 - TP1

    Auteur: Quoc Thanh Nguyen
    Matricule: 20353086

    Classe principale qui contient la methode main.
    Elle cree une instance de Bdd, lit les donnees des maneges depuis un fichier texte et teste les services definis dans l'interface Signature.
*/
package TP1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Tp1 {

	public static void main(String[] args) {
		Signature donnees = new Bdd();
		Manege unManege;
		String nom, parc, affichage = "";
		double hauteur, vitesse;
		
		unManege = new Manege("Kingda Ka",437, 121);
		
		donnees.addManege(unManege, "Great Adventure");
		
		lireFichier(donnees);
		parc = "Great Adventure";
		List<Manege> l = donnees.listeDuParc(parc);
		for(Manege m : l) {
			affichage += m.toString() + "\n";
		}
		
		JOptionPane.showConfirmDialog(null, affichage, "Les manèges de " + parc, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		
		unManege = donnees.getManege(new Manege("Condor"));
		if (unManege != null)
			System.out.println(unManege);
		System.out.println("La table de fréquence des manèges");
		System.out.println(donnees.frequence());
		
		System.out.println("La Bdd:");
		System.out.println(donnees);
	}
	
	public static void lireFichier(Signature donnees) {
		FileReader fr = null;
		boolean existeFile = true;
		boolean finFichier = false;
		String nomFile;
		Manege m;
		String nom, parc;
		double hauteur, vitesse;
		String[] valeurs;
		JFileChooser choixFichier;
		
		choixFichier = new JFileChooser();
		if(choixFichier.showOpenDialog(null)== JFileChooser.APPROVE_OPTION)
		{	nomFile = choixFichier.getSelectedFile().getName();
			File f = choixFichier.getCurrentDirectory();
			nomFile = f.getAbsolutePath()+File.separator + nomFile;
			try {
			
				fr = new FileReader(nomFile);
			} catch (java.io.FileNotFoundException e) {
				System.out.println("Probleme d'ouvrir le fichier " + nomFile);
		   		existeFile = false;
			}

			if (existeFile) {
				try{

		  			BufferedReader entree = new BufferedReader (fr);

		  			while (!finFichier) {
			  			String ligne = entree.readLine();
			  			if (ligne != null){
			  				valeurs = ligne.split(";");
			  				nom = valeurs[0];
			  				hauteur = Double.parseDouble(valeurs[1].trim());
			  				vitesse = Double.parseDouble(valeurs[2].trim());
			  				m = new Manege(nom,hauteur, vitesse);
			  				donnees.addManege(m, valeurs[3].trim());
			  			}
			  			else
			  				finFichier = true;
		  				}
		  			entree.close();
				}catch(IOException e){
					System.out.println("Problème lors de la lecture du fichier");
				}
			}
		}
	}

}
