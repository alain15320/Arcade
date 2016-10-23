package moteur.generique;

import java.awt.Graphics;

public interface IMoteurSpecifique {
	void initialiserJeu();
	void genererJeu();
	void mettreAJourJeu();
	void traiterCollision();
	long getPeriodeMaj();
	void dessine(Graphics g);
	void toucheHaut();
	void toucheBas();
	void toucheDroite();
	void toucheGauche();
	int getParamInt(int p);
	String getParamString(int p);
	void setMoteur(IMoteur m);
}
