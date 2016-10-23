package gui.generique;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import moteur.generique.EtatMoteur;
import moteur.generique.IGui;
import moteur.generique.IMoteur;
import moteur.generique.ISon;

public class PanneauControle implements IPanneauControle {

	private IGui gui;
	private IMoteur moteur;
	private JButton demarrer_pause;
	private JButton arret;
	private JButton son_on_off;
	private JPanel panneau;
	private ImageIcon ipause = new ImageIcon(PanneauControle.class.getResource("/Images/pause.png"));
	private ImageIcon idemarrer = new ImageIcon(PanneauControle.class.getResource("/Images/start.png"));
	private ImageIcon iarret = new ImageIcon(PanneauControle.class.getResource("/Images/stop.png"));
	private ImageIcon ison_off = new ImageIcon(PanneauControle.class.getResource("/Images/audio-muted.png"));
	private ImageIcon ison_on = new ImageIcon(PanneauControle.class.getResource("/Images/audio-high.png"));

	private class ActivationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EtatMoteur etat = moteur.getEtat();
			switch (etat) {
			case INITIALISE:
			case GAMEOVER:
				demarrer_pause.setIcon(ipause);
				moteur.demarrerJeu();
				break;
			case EN_COURS:
				demarrer_pause.setIcon(idemarrer);
				moteur.setEtat(EtatMoteur.ARRETE);
				break;
			case ARRETE:
				demarrer_pause.setIcon(ipause);
				moteur.setEtat(EtatMoteur.EN_COURS);
				break;
			}
			arret.setEnabled(true);
			gui.focusPanneauJeu();
		}
	}
	
	private class ArretListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			moteur.setEtat(EtatMoteur.GAMEOVER);
			demarrer_pause.setIcon(idemarrer);
			demarrer_pause.setEnabled(true);
			arret.setEnabled(false);
			gui.repaintPanneauJeu();
		}
	}

	private class SonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ISon son = moteur.getSon();
			if (son.isOn()) {
				son.setOff();
				son_on_off.setIcon(ison_off);
			} else {
				son.setOn();
				son_on_off.setIcon(ison_on);				
			}
			gui.focusPanneauJeu();
		}
	}
	
	public PanneauControle(final IGui gui) {
		this.gui = gui;
		moteur = gui.getMoteur();
		panneau = new JPanel();
		panneau.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		demarrer_pause = new JButton(idemarrer);
		demarrer_pause.setEnabled(true);
		panneau.add(demarrer_pause);
		arret = new JButton(iarret);
		arret.setEnabled(false);
		panneau.add(arret);
		son_on_off = new JButton(ison_off);
		son_on_off.setEnabled(true);
		panneau.add(son_on_off);
		demarrer_pause.addActionListener(new ActivationListener());		
		arret.addActionListener(new ArretListener());
		son_on_off.addActionListener(new SonListener());
	}

	@Override
	public void reset() {
		demarrer_pause.setIcon(idemarrer);
		demarrer_pause.setEnabled(true);
		arret.setEnabled(false);
	}

	@Override
	public JPanel getPanneau() {
		return panneau;
	}

}
