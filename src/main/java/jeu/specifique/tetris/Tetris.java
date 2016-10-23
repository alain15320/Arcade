package jeu.specifique.tetris;

import gui.generique.tetris.AireJeuTetris;
import jeu.generique.MonteurGenerique;
import moteur.specifique.tetris.MoteurTetris;

public class Tetris {

	public static void main(String[] args) {
		new MonteurGenerique("Tetris", new AireJeuTetris(), new MoteurTetris(), "FICHIER_TEXTE").construire();
	}
	
}
