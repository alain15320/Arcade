package persist;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import moteur.generique.IMeilleurScore;

public class MeilleurScore implements IMeilleurScore {

	private String nomJeu;
	private ArrayList<Score> top5;
	private IPersist persist;

	public MeilleurScore(String nom, String pers) {
		nomJeu = nom;
		top5 = null;
		if (pers.equals("FICHIER_TEXTE")) persist = new FichierTexte();
		else if (pers.equals("SERIALISATION")) persist = new Serialisation();
		top5 = persist.lireListe(nomJeu);
		if (top5==null) top5 = new ArrayList<Score>();
	}

	@Override
	public void ajouterTop5(int score) {
		if (top5.size()<5) {
			saisie(score);
		} else {
			Score min = top5.get(0);
			if (score>min.getScore()) {
				top5.remove(0);
				saisie(score);
			}
		}
		persist.ecrireListe(nomJeu, top5);
	}

	private void saisie(int score) {
		String nomJoueur = JOptionPane.showInputDialog(null,
				"Nouveau meilleur score !!! \nTapez votre nom :",
				"Meilleur score",
				JOptionPane.PLAIN_MESSAGE);
		top5.add(new Score(nomJoueur, score));
		Collections.sort(top5);
	}

	@Override
	public String afficherTop5() {
		String s ="";
		Score sc;
		for (int i=0; i<top5.size(); i++){
			sc = top5.get(i);
			s = s + sc.getNom()+" "+sc.getScore()+"\n";
		}
		return s;
	}

}
