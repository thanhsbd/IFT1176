/*
    IFT 1176 - TP2

    Auteur: Quoc Thanh Nguyen
    Matricule: 20353086

    Classe principale qui lance l'application graphique du TP2.
    Lit le fichier de donnees, remplit la Bdd et cree la fenetre principale.
	*/
package TP2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Tp2 {

    public static void main(String[] args) {
        Bdd bdd = new Bdd();
        lireFichier(bdd);
        new FenetrePrincipale(bdd);
    }

    public static void lireFichier(Bdd bdd) {
        FileReader fr = null;
        boolean existeFile = true;
        boolean finFichier = false;
        String nomFile;
        Manege m;
        String nom;
        double hauteur, vitesse;
        String[] valeurs;
        JFileChooser choixFichier;

        choixFichier = new JFileChooser();
        if (choixFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            nomFile = choixFichier.getSelectedFile().getName();
            File f = choixFichier.getCurrentDirectory();
            nomFile = f.getAbsolutePath() + File.separator + nomFile;
            try {
                fr = new FileReader(nomFile);
            } catch (java.io.FileNotFoundException e) {
                System.out.println("Probleme d'ouvrir le fichier " + nomFile);
                existeFile = false;
            }

            if (existeFile) {
                try {
                    BufferedReader entree = new BufferedReader(fr);
                    while (!finFichier) {
                        String ligne = entree.readLine();
                        if (ligne != null) {
                            valeurs = ligne.split(";");
                            nom = valeurs[0];
                            hauteur = Double.parseDouble(valeurs[1].trim());
                            vitesse = Double.parseDouble(valeurs[2].trim());
                            m = new Manege(nom, hauteur, vitesse);
                            bdd.addManege(m, valeurs[3].trim());
                        } else {
                            finFichier = true;
                        }
                    }
                    entree.close();
                } catch (IOException e) {
                    System.out.println("Problème lors de la lecture du fichier");
                }
            }
        }
    }
}

/* 	
	Fenetre principale pour voir une liste de parc et maneges
*/
class FenetrePrincipale extends JFrame {

    private Bdd bdd;
    private JComboBox<String> comboManeges;
    private JComboBox<String> comboParcs;

    public FenetrePrincipale(Bdd bdd) {
        this.bdd = bdd;

        setTitle("Tp2, choisir quoi afficher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem itemFrequence = new JMenuItem("Afficher la table de fréquence d'un manège");
        JMenuItem itemQuitter = new JMenuItem("Quitter");
        menu.add(itemFrequence);
        menu.add(itemQuitter);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Etiquette de l'auteur
        JLabel labelAuteurs = new JLabel("Quoc Thanh Nguyen - 20353086");
        add(labelAuteurs, BorderLayout.NORTH);

        // Contenu principale
        JPanel panelCentre = new JPanel(new GridLayout(2, 2, 5, 5));
        panelCentre.add(new JLabel("Les Maneges"));
        panelCentre.add(new JLabel("Les Parcs"));
        comboManeges = new JComboBox<String>(bdd.getNomsManeges());
        panelCentre.add(comboManeges);
        comboParcs = new JComboBox<String>(bdd.getNomsParcs());
        panelCentre.add(comboParcs);

        add(panelCentre, BorderLayout.CENTER);

        // Bouton Quitter pour fermer l'application
        JButton boutonQuitter = new JButton("Quitter");
        add(boutonQuitter, BorderLayout.SOUTH);

        setSize(500, 160);
        setVisible(true);

        
        // Affiche la table de frequence des maneges
        itemFrequence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { afficherFrequence(); }
        });

        // Quitter l'application
        itemQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });

        // Quitter l'application
        boutonQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });

        // Evenement pour la selection d'un manege
        comboManeges.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String nom = (String) e.getItem();
                    Manege m = bdd.getManege(new Manege(nom));
                    new FenetreManege(FenetrePrincipale.this, m, bdd.emplacementsManege(m));
                }
            }
        });

        // Evenement pour la selection d'un parc
        comboParcs.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String nom = (String) e.getItem();
                    java.util.List<Manege> liste = bdd.listeDuParc(nom);
                    new FenetreParc(FenetrePrincipale.this, nom, liste);
                }
            }
        });
    }

    // Affiche la table de frequence des maneges 
    private void afficherFrequence() {
        TreeMap<Manege, Integer> freq = bdd.frequence();
        String affichage = "Nb\tNom du manege\n";
        for (Map.Entry<Manege, Integer> entree : freq.entrySet()) {
            affichage += entree.getValue() + " : " + entree.getKey().getNom() + "\n";
        }
        JOptionPane.showMessageDialog(this, affichage,
                "Table de frequence des maneges", JOptionPane.INFORMATION_MESSAGE);
    }
}

/* 
	Cette fenetre montres les informations sur un manege et les parcs associes a celui-ci
*/
class FenetreManege extends JFrame {

    public FenetreManege(FenetrePrincipale parentFenetre, Manege manege, Set<String> emplacements) {
        // Cacher la fenetre principale pendant l'affichage du manege
        parentFenetre.setVisible(false);

        setTitle(manege.getNom());
        setLayout(new BorderLayout());

        // Informations sur le manege
        JLabel labelManege = new JLabel("Les informations sur le manege  " + manege.getNom() + ", " + manege.getHauteur() + "' , " + manege.getVitesse() + " mph");
        add(labelManege, BorderLayout.NORTH);

        // Liste des parcs
        JPanel panelCentre = new JPanel(new GridLayout(1, 2, 5, 5));

        JLabel labelPresent = new JLabel("Présent dans les parcs suivants :");
        String[] parcsArray = emplacements.toArray(new String[0]);
        JList<String> listeParcs = new JList<String>(parcsArray);
        JScrollPane scrollPane = new JScrollPane(listeParcs);

        panelCentre.add(labelPresent);
        panelCentre.add(scrollPane);
        add(panelCentre, BorderLayout.CENTER);

        // Fermer la fenetre et rendre la fenetre principale visible
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                parentFenetre.setVisible(true);
                dispose();
            }
        });

        setSize(600, 300);
        Point p = parentFenetre.getLocation();
        p.translate(100, 100);
        setLocation(p);
        setVisible(true);
    }
}

/* 
	Cette fenetre visualise une liste de maneges dans un parc avec un navigateur par bouton
*/
class FenetreParc extends JFrame {

    private int indexCourant = 0;
    private java.util.List<Manege> maneges;

    private JLabel labelNavigation;
    private JLabel labelNomValeur;
    private JLabel labelVitesseValeur;
    private JLabel labelHauteurValeur;
    private JButton boutonPrecedent;
    private JButton boutonSuivant;

    public FenetreParc(FenetrePrincipale parentFenetre, String nomParc, java.util.List<Manege> maneges) {
        this.maneges = maneges;

        // Cacher la fenetre principale pendant la navigation dans le parc
        parentFenetre.setVisible(false);

        setTitle(nomParc);
        setLayout(new BorderLayout());

        // Titre identifiant le parc 
        JLabel labelParc = new JLabel("Les maneges du parc " + nomParc, JLabel.CENTER);
        labelParc.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        add(labelParc, BorderLayout.NORTH);

        // Information sur le manege
        JPanel panelCentre = new JPanel(new GridLayout(2, 3, 5, 5));
        panelCentre.setBackground(Color.LIGHT_GRAY);
        panelCentre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCentre.add(new JLabel("Le nom du manege"));
        panelCentre.add(new JLabel("Vitesse en mph"));
        panelCentre.add(new JLabel("Hauteur en pieds"));
        labelNomValeur = new JLabel("-");
        panelCentre.add(labelNomValeur);
        labelVitesseValeur = new JLabel("-");
        panelCentre.add(labelVitesseValeur);
        labelHauteurValeur = new JLabel("-");
        panelCentre.add(labelHauteurValeur);

        add(panelCentre, BorderLayout.CENTER);

        // Navigations et boutons
        JPanel panelSud = new JPanel(new FlowLayout());

        boutonPrecedent = new JButton("Précédent");
        boutonSuivant = new JButton("Suivant");
        boutonPrecedent.setEnabled(false);
        boutonSuivant.setEnabled(false);

        labelNavigation = new JLabel("0 de " + maneges.size());

        panelSud.add(boutonPrecedent);
        panelSud.add(labelNavigation);
        panelSud.add(boutonSuivant);
        add(panelSud, BorderLayout.SOUTH);

        // Afficher le premier manege s'il y en a au moins un
        if (!maneges.isEmpty()) {
            afficherManege(0);
            if (maneges.size() > 1)
                boutonSuivant.setEnabled(true);
        }

        // Evenements des boutons de navigation
        boutonSuivant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                indexCourant++;
                afficherManege(indexCourant);
                boutonPrecedent.setEnabled(true);
                boutonSuivant.setEnabled(indexCourant < maneges.size() - 1);
            }
        });
        boutonPrecedent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                indexCourant--;
                afficherManege(indexCourant);
                boutonSuivant.setEnabled(true);
                boutonPrecedent.setEnabled(indexCourant > 0);
            }
        });

        // Quitter et rendre la fenetre principale visible
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                parentFenetre.setVisible(true);
                dispose();
            }
        });

        setSize(600, 200);
        Point p = parentFenetre.getLocation();
        p.translate(100, 100);
        setLocation(p);
        setVisible(true);
    }

    // Afficher le manege
    private void afficherManege(int index) {
        Manege m = maneges.get(index);
        labelNomValeur.setText(m.getNom());
        labelVitesseValeur.setText(String.valueOf(m.getVitesse()));
        labelHauteurValeur.setText(String.valueOf(m.getHauteur()));
        labelNavigation.setText((index + 1) + " de " + maneges.size());
    }
}

