package pro;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Knjizica {
	private ArrayList<String> noveReci;
	private Recnik recnik;
	private HashMap<String, Integer> ponavljanjeReci;

	public Knjizica() {
		noveReci = new ArrayList<String>();
		ponavljanjeReci = new HashMap<String, Integer>();
		recnik = new Recnik();
		recnik.popuniRecnik();
	}

	public void ukloniZnake() {

		try {
			BufferedReader br = new BufferedReader(new FileReader("src\\knjiga"));
			FileWriter fw = new FileWriter("novaKnjiga");

			String linija = br.readLine();

			while (linija != null) {
				linija = linija.toLowerCase();
				for (int i = 0; i < linija.length(); i++) {
					char ch = linija.charAt(i);
					if (!(ch >= 'a' && ch <= 'z')) {
						linija = linija.replace(ch, ' ');
					}
				}
				fw.write(linija + "\n");
				linija = br.readLine();

			}
			fw.flush();
			br.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> pronadjiNoveReci() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("novaKnjiga"));
			String linija = br.readLine();
			while (linija != null) {
				String[] reci = linija.split(" ");
				for (int i = 0; i < reci.length; i++) {
					if (!reci[i].isEmpty() && !recnik.nadjiRec(reci[i]) && !noveReci.contains(reci[i])) {
						noveReci.add(reci[i]);
						recnik.dodajNovuRec(reci[i]);
					}
				}
				linija = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return noveReci;
	}

	public HashMap<String, Integer> brojPojavljivanja() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("novaKnjiga"));
			String linija = br.readLine();
			while (linija != null) {
				String[] reci = linija.split(" ");
				for (int i = 0; i < reci.length; i++) {
					if (!reci[i].isEmpty() && recnik.nadjiRec(reci[i]) == true) {
						if (ponavljanjeReci.containsKey(reci[i]) == false) {

							ponavljanjeReci.put(reci[i], 1);
						} else {

							int brojac = ponavljanjeReci.get(reci[i]);
							ponavljanjeReci.put(reci[i], brojac + 1);
						}
					}
				}
				linija = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ponavljanjeReci;
	}

	public ArrayList<String> pronadjiNajcesceReci(int brojReci) {

		ArrayList<String> reci = new ArrayList<String>();
		for (String rec : ponavljanjeReci.keySet()) {
			reci.add(rec);
		}

		for (int i = 0; i < reci.size() - 1; i++) {
			for (int j = i + 1; j < reci.size(); j++) {
				String recI = reci.get(i);
				String recJ = reci.get(j);
				int brojI = ponavljanjeReci.get(recI);
				int brojJ = ponavljanjeReci.get(recJ);
				if (brojI < brojJ) {
					reci.set(i, recJ);
					reci.set(j, recI);
				}
			}
		}

		if (brojReci > reci.size()) {
			brojReci = reci.size();
		}
		ArrayList<String> rezultat = new ArrayList<String>();
		for (int i = 0; i < brojReci; i++) {
			rezultat.add(reci.get(i));
		}
		return rezultat;
	}

	public void upisUFajl(String s) {
		ArrayList<String> sveReciUKnjizi = new ArrayList<String>();
		sveReciUKnjizi.addAll(noveReci);
		sveReciUKnjizi.addAll(recnik.getRecnik().keySet());
		Collections.sort(sveReciUKnjizi);

		try {
			FileWriter fw = new FileWriter("sortiraneReci");

			for (String rec : sveReciUKnjizi) {
				rec = rec.toLowerCase();
				fw.write(rec + "\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Recnik getRecnik() {
		return recnik;
	}
}