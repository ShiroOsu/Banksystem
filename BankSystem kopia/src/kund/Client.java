package kund;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import fileHandler.ObjectFileHandler;
import klient.Main;
import konto.*;

public class Client implements Serializable {
	private static final long serialVersionUID = 3460909333890415541L;
	/**
	 * 
	 */
	private String personNr, firstName, lastName, adress, zip, city, phoneNr, email;
	private int kundNr;
	private long password;
	private String username;
	private static ArrayList<Sparkonto> sk = new ArrayList<>();
	private static ArrayList<Transaktionskonto> tk = new ArrayList<>();
	
	public Client(String firstName, String lastName, String personNr, String adress, String zip, String city, String phoneNr, String email){
		this.firstName = firstName;
		this.lastName = lastName;
		this.personNr = personNr;
		this.adress = adress;
		this.zip = zip;
		this.city = city;
		this.phoneNr = phoneNr;
		this.email = email;	
		
		setUsername(firstName);
	}
	
	public long getTransaktionsNummer(int index){
		return tk.get(index).getKontoNummer();
	}
	
	public long getSparkontoNummer(int index){
		return sk.get(index).getKontoNummer();
	}
	
	public Transaktionskonto getTransaktionskonto(long kontoNummer){
		for(int i = 0; i < tk.size(); i++){
			if(tk.get(i).getKontoNummer() == kontoNummer){
				return tk.get(i);
			}
		}
		return null; 
	}
	
	public Sparkonto getSparKonto(long kontoNummer){
		for(int i = 0; i < sk.size(); i++){
			if(sk.get(i).getKontoNummer() == kontoNummer){
				return sk.get(i);
			}
		}
		return null;
	}
	
	public void setKundNr(int kundNr){
		this.kundNr = kundNr;
	}
	
	public int getKundNr(){
		return kundNr;
	}
	

	@SuppressWarnings("unchecked")
	public static void loadKonto(){
		try{
			FileInputStream fis = new FileInputStream("sparkonton.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			sk = (ArrayList<Sparkonto>) ois.readObject();
			ois.close();
			
			FileInputStream fis0 = new FileInputStream("transaktionskonton.ser");
			ObjectInputStream ois0 = new ObjectInputStream(fis0);
			tk = (ArrayList<Transaktionskonto>) ois0.readObject();
			ois0.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void saveKonto(){
		try{
			FileOutputStream fos = new FileOutputStream("sparkonton.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(sk);
			oos.close();
			
			FileOutputStream fos0 = new FileOutputStream("transaktionskonton.ser");
			ObjectOutputStream oos0 = new ObjectOutputStream(fos0);
			oos0.writeObject(tk);
			oos0.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void addSparkonto(int nummer, int pengar){
		ObjectFileHandler obh = new ObjectFileHandler("kunder/k"+nummer+"/Sparkonto");
		double rent = 0.2; //Standard ränta, kan ändras
			for(int i = 0; i < 1; i++){
				try {
					if(Files.list(Paths.get("kunder/k"+nummer+"/Sparkonto")).count() < 3){
						long kontoNumber = Main.randomKontoNumber(9);
						sk.add(new Sparkonto(kontoNumber, pengar, rent));
						obh.write(sk, "kunder/k"+nummer+"/Sparkonto"+"/"+kontoNumber+".ser");
					} else {
						System.err.println("Client: "+nummer+" has max amount of Sparkonton");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		saveKonto();
	}
	
	public static void addTransaktionkonto(int nummer, int pengar){
		ObjectFileHandler obh = new ObjectFileHandler("kunder/k"+nummer+"/Transaktionskonto");
			for(int i = 0; i < 1; i++){
				try {
					if(Files.list(Paths.get("kunder/k"+nummer+"/Transaktionskonto")).count() < 3){
						long kontoNumber = Main.randomKontoNumber(9);
						tk.add(new Transaktionskonto(kontoNumber, pengar));
						obh.write(tk, "kunder/k"+nummer+"/Transaktionskonto"+"/"+kontoNumber+".ser");
					} else {
						System.err.println("Client: "+nummer+" has max amount of Transaktionskonton");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			saveKonto();
	}	
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password +"";
	}
	
	public void setPassword(long password){
		this.password = password;
	}

	public String getPersonNr() {
		return personNr;
	}

	public void setPersonNr(String personNr) {
		this.personNr = personNr;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getCity(){
		return city;
	}
	
	public void setCity(String city){
		this.city = city;
	}

	public String getPhoneNr() {
		return phoneNr;
	}

	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
