package moteur.generique;

public interface ISon {
	void initialiser();
	boolean isOn();
	void setOn();
	void setOff();
	void jouerCollision();
	void jouerFin();
}
