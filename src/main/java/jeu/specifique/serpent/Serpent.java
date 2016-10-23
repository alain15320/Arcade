package jeu.specifique.serpent;

import gui.generique.serpent.AireJeuSerpent;
import jeu.generique.MonteurGenerique;
import moteur.specifique.serpent.MoteurSerpent;

public class Serpent {

	public static void main(String[] args) {
		new MonteurGenerique("Serpent", new AireJeuSerpent(), new MoteurSerpent(), "SERIALISATION").construire();
	}

}
