package moteur.generique;

import java.awt.Graphics;

public interface IMoteur {
	void demarrerJeu();
	void terminerJeu();
	void notification();
	void setMoteurSpecifique(IMoteurSpecifique spe);
	IMoteurSpecifique getMoteurSpecifique();
	void setGui(IGui g);
	IGui getGui();
	void dessine(Graphics g);
	void toucheHaut();
	void toucheBas();
	void toucheDroite();
	void toucheGauche();
	int getParamInt(int p);
	String getParamString(int p);
	EtatMoteur getEtat();
	void setEtat(EtatMoteur e);
	int getScore();
	int getNiveau();
	ISon getSon();
	void setScore(int s);
	void setNiveau(int n);
	void setSon(ISon s);
}
