package moteur.generique;

import java.awt.Graphics;
import java.util.Random;

public abstract class ObjetJeu {
	protected IMoteurSpecifique mots;
	protected Random rand;
	protected int x;
	protected int y;
	public ObjetJeu() {
		rand = new Random();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}	
	public void setX(int v) {
		x = v;
	}
	public void setY(int v) {
		y = v;
	}
	public void setMoteurSpecifique(IMoteurSpecifique m) {
		mots = m;
	}
	public abstract void generer();
	public abstract void mettreAJour();	
	public abstract void dessiner(Graphics g);
}
