package jeu.generique;

import gui.generique.Gui;
import gui.generique.IAireJeu;
import gui.generique.PanneauJeu;
import moteur.generique.IMoteurSpecifique;
import moteur.generique.Moteur;
import persist.MeilleurScore;
import utils.son.Son;

public class MonteurGenerique extends MonteurAbstrait {

	private String nom;
	private IAireJeu aire;
	private IMoteurSpecifique ims;
	private String pers;
	private Gui gui;
	private Moteur moteur;

	public MonteurGenerique(String n, IAireJeu a, IMoteurSpecifique i, String p) {
		nom = n;
		aire = a;
		ims = i;
		pers = p;
	}

	@Override
	public void construireGui() {
		gui = new Gui(nom);
		PanneauJeu pjeu = new PanneauJeu(gui);
		gui.setPanneauJeu(pjeu);
		pjeu.setAireJeu(aire);
		aire.setGui(gui);
	}

	@Override
	public void construireMoteur() {
		moteur = new Moteur();
		moteur.setGui(gui);
		gui.setMoteur(moteur);
		moteur.setMoteurSpecifique(ims);
		ims.setMoteur(moteur);
		aire.setMoteur(moteur);
	}

	@Override
	public void ajouterSon() {
		Son son = new Son();
		son.initialiser();
		moteur.setSon(son);
	}

	@Override
	public void ajouterPersistance() {
		MeilleurScore ms = new MeilleurScore(nom, pers);
		gui.setMeilleurScore(ms);
	}

	@Override
	public void terminerConstruire() {
		gui.terminerGui();
	}

}
