package gui.generique;

import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import moteur.generique.IMoteur;

public class PanneauScore implements IPanneauScore {

	private JPanel panneau;
	private JTextField score;
	private JTextField niveau;
	
	public PanneauScore() {
		panneau = new JPanel();
		JLabel labscore = new JLabel("Score");
		panneau.add(labscore);
		score = new JTextField("0", 5);
		score.setEditable(false);
		panneau.add(score);
		JLabel labniv = new JLabel("Niveau");
		panneau.add(labniv);
		niveau = new JTextField("1", 5);
		niveau.setEditable(false);
		panneau.add(niveau);
	}

	@Override
	public void update(Observable o, Object arg) {
		IMoteur moteur = (IMoteur)o;
		score.setText(""+moteur.getScore());
		niveau.setText(""+moteur.getNiveau());
	}

	@Override
	public JPanel getPanneau() {
		return panneau;
	}

}
