package gui.generique;

import java.util.Observer;

import javax.swing.JPanel;

public interface IPanneauScore extends Observer {
	JPanel getPanneau();
}
