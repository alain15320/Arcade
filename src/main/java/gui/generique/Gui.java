package gui.generique;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;

import moteur.generique.IGui;
import moteur.generique.IMeilleurScore;
import moteur.generique.IMoteur;

public class Gui implements IGui {

	private JFrame fenetre;
	private IPanneauJeu p_jeu;
	private IPanneauScore p_score;
	private IPanneauControle p_controle;
	private IMoteur moteur;
	private IMeilleurScore meilleur_score;

	public Gui(String nom) {
		fenetre = new JFrame(nom);
	}

	@Override
	public void terminerGui() {
		Container cp = fenetre.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(p_jeu.getPanneau(), BorderLayout.CENTER);
		Dimension d = new Dimension(p_jeu.getLargeur(), p_jeu.getHauteur());
		p_jeu.getPanneau().setPreferredSize(d);
		JPanel bas = new JPanel();
		bas.setLayout(new BorderLayout());
		p_score = new PanneauScore();
		bas.add(p_score.getPanneau(), BorderLayout.NORTH);
		p_controle = new PanneauControle(this);
		bas.add(p_controle.getPanneau(), BorderLayout.CENTER);
		cp.add(bas, BorderLayout.SOUTH);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setLocationRelativeTo(fenetre.getParent());
		fenetre.setVisible(true);
		((Observable)moteur).addObserver(p_score);
	}

	@Override
	public void setMeilleurScore(IMeilleurScore m) {
		meilleur_score = m;
	}

	@Override
	public void setMoteur(IMoteur m) {
		moteur = m;		
	}

	@Override
	public void resetPanneauControle() {
		p_controle.reset();
	}

	@Override
	public void repaintPanneauJeu() {
		p_jeu.getPanneau().repaint();
	}

	@Override
	public void focusPanneauJeu() {
		p_jeu.getPanneau().requestFocus();
	}

	@Override
	public void dessineGameover(Graphics g) {
		p_jeu.dessineGameover(g);
	}

	@Override
	public void setImageFondPanneauJeu(String s, Graphics g) {
		p_jeu.remplirImageFond(s, g);
	}

	@Override
	public boolean containsPanneauJeu(int x, int y) {
		return p_jeu.contains(x, y);
	}

	@Override
	public IMeilleurScore getMeilleurScore() {
		return meilleur_score;
	}

	@Override
	public IMoteur getMoteur() {
		return moteur;
	}

	public IPanneauJeu getPanneauJeu() {
		return p_jeu;
	}

	public IPanneauScore getPanneauScore() {
		return p_score;
	}

	public IPanneauControle getPanneauControle() {
		return p_controle;
	}

	public void setPanneauJeu(IPanneauJeu pj) {
		p_jeu = pj;
	}

}
