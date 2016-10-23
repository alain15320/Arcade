package jeu.specifique.pacman;

import gui.generique.pacman.AireJeuPacMan;
import jeu.generique.MonteurGenerique;
import moteur.specifique.pacman.MoteurPacMan;

public class PacMan {

	public static void main(String[] args) {
		new MonteurGenerique("PacMan", new AireJeuPacMan(), new MoteurPacMan(), "SERIALISATION").construire();
	}

}
