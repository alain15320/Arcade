package persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serialisation implements IPersist {

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Score> lireListe(String nom) {
		File f = new File(PATH_SCORES+nom+".sco.txt");
		ArrayList<Score> res = new ArrayList<Score>();
		if (f.exists()) {
			try {
				FileInputStream fis = new FileInputStream(PATH_SCORES+nom+".sco.txt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				res = (ArrayList<Score>) ois.readObject();
				ois.close();
			} catch(Exception e) {
				System.out.println("read error : "+e.getMessage());
			}
		}
		return res;
	}

	@Override
	public void ecrireListe(String nom, ArrayList<Score> liste) {
		try {
			FileOutputStream fos = new FileOutputStream(PATH_SCORES+nom+".sco.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(liste);
			oos.close();
		} catch (Exception e) {
			System.out.println("write error : "+e);
		}
	}

}
