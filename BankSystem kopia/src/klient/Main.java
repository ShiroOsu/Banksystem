package klient;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kund.Client;
import fileHandler.ObjectFileHandler;
import konto.Sparkonto;
import konto.Transaktionskonto;

@SuppressWarnings("serial")
public class Main extends JFrame {


	private JPanel panel;
	public static int WIDTH = 500, HEIGHT = 500;

	private JButton login, createNewClient, addClients, back;
	private JPasswordField password;
	private JTextField personNr, firstName, lastName, adress, zip, city, phoneNr, email, username, clientNr, kapital;
	private JLabel personLabel, firstNameLabel, lastNameLabel, adressLabel, zipLabel, cityLabel, phoneLabel, emailLabel,
	usernameLabel, passwordLabel, clientNrLabel, kapitalLabel;
	private JTextArea temp;
	private JList list;
	public static ArrayList<Client> clients = new ArrayList<>();
	private ObjectFileHandler objectHandler;
	private Client c;
	
	public static int CURRENT_CUSTOMER = -1;

	public Main(){
		super("Banksystem");
		panel = new JPanel();
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setLayout(null);
		setContentPane(panel);
		init();
		setVisible(true);

		

	}
	
	@SuppressWarnings("unchecked")
	public static void loadClients(){
		try{
			FileInputStream fis = new FileInputStream("clients.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			clients = (ArrayList<Client>) ois.readObject();
			ois.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for(int i = 0; i < clients.size(); i++){
			clients.get(i).setPassword(clients.get(i).getKundNr());
			System.out.println(clients.get(i).getKundNr());
		}
	}
	
	private void saveClients(){
		try{
			FileOutputStream fos = new FileOutputStream("clients.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(clients);
			oos.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void init(){
			loadClients();
			Client.loadKonto();
			username = new JTextField();
			usernameLabel = new JLabel("Username(First Name):");
			usernameLabel.setBounds(WIDTH / 2 - 120, HEIGHT / 2 - 125, 150, 20);
			username.setBounds(WIDTH / 2 - 50, HEIGHT / 2 - 100, 100, 20);
			panel.add(username);
			panel.add(usernameLabel);
			
			password = new JPasswordField();
			passwordLabel = new JLabel("Password(Costumer Number):");
			passwordLabel.setBounds(WIDTH / 2 - 120, HEIGHT / 2 - 75, 200, 20);
			password.setBounds(WIDTH / 2 - 50, HEIGHT / 2 - 50, 100, 20);
			panel.add(password);
			panel.add(passwordLabel);
				
			JButton register = new JButton("Register");
			register.setBounds(WIDTH / 2 - 50, HEIGHT / 2 + 20, 100, 20);
			panel.add(register);
			register.addActionListener(ae -> register());
			
			login = new JButton("Login");
			login.setBounds(WIDTH / 2 - 50, HEIGHT / 2 - 10, 100, 20);
			panel.add(login);
			login.addActionListener(ae -> bankLogin());
		
	}
	
	private void register(){
		resetPanel();
		firstName = new JTextField();
		firstName.setBounds(10, 20, 150, 20);
		firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setBounds(10, 0, 200, 20);
		panel.add(firstNameLabel);
		panel.add(firstName);

		lastName = new JTextField();
		lastName.setBounds(10, 60, 150, 20);
		lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setBounds(10, 40, 200, 20);
		panel.add(lastNameLabel);
		panel.add(lastName);

		adress = new JTextField();
		adress.setBounds(10, 100, 150, 20);
		adressLabel = new JLabel("Adress:");
		adressLabel.setBounds(10, 80, 200, 20);
		panel.add(adressLabel);
		panel.add(adress);

		phoneNr = new JTextField();
		phoneNr.setBounds(10, 140, 150, 20);
		phoneLabel = new JLabel("Phone number:");
		phoneLabel.setBounds(10, 120, 200, 20);
		panel.add(phoneLabel);
		panel.add(phoneNr);

		zip = new JTextField();
		zip.setBounds(10, 180, 150, 20);
		zipLabel = new JLabel("Postcode:");
		zipLabel.setBounds(10, 160, 200, 20);
		panel.add(zipLabel);
		panel.add(zip);

		city = new JTextField();
		city.setBounds(10, 220, 150, 20);
		cityLabel = new JLabel("City:");
		cityLabel.setBounds(10, 200, 200, 20);
		panel.add(cityLabel);
		panel.add(city);

		personNr = new JTextField();
		personNr.setBounds(10, 260, 150, 20);
		personLabel = new JLabel("Person Number:");
		personLabel.setBounds(10, 240, 200, 20);
		panel.add(personLabel);
		panel.add(personNr);

		email = new JTextField();
		email.setBounds(10, 300, 150, 20);
		emailLabel = new JLabel("Email:");
		emailLabel.setBounds(10, 280, 200, 20);
		panel.add(emailLabel);
		panel.add(email);

		createNewClient = new JButton("Create New Client");
		createNewClient.setBounds(10, 340, 150, 20);
		panel.add(createNewClient);
		createNewClient.addActionListener(ae -> newClient());

		addClients = new JButton("Add clients to list");
		addClients.setBounds(10, 380, 150, 20);
		panel.add(addClients);
		addClients.addActionListener(ae -> addClient());
		
		clientNr = new JTextField();
		clientNr.setBounds(WIDTH / 2, 20, 200, 20);
		clientNrLabel = new JLabel("Client Nr:");
		clientNrLabel.setBounds(WIDTH / 2, 0, 200, 20);
		panel.add(clientNrLabel);
		panel.add(clientNr);
		
		kapital = new JTextField();
		kapital.setBounds(WIDTH / 2, 60, 200, 20);
		kapitalLabel = new JLabel("Kapital:");
		kapitalLabel.setBounds(WIDTH / 2, 40, 200, 20);
		panel.add(kapitalLabel);
		panel.add(kapital);
		
		JButton addSparKonto = new JButton("Add Sparkonto");
		addSparKonto.setBounds(WIDTH / 2, 100, 200, 20);
		panel.add(addSparKonto);
		addSparKonto.addActionListener(ae -> addSparkonto());
		
		JButton addTransaktionsKonto = new JButton("Add Transaktionskonto");
		addTransaktionsKonto.setBounds(WIDTH / 2, 140, 200, 20);
		panel.add(addTransaktionsKonto);
		addTransaktionsKonto.addActionListener(ae -> addTransaktionskonto());
		
		back = new JButton("Return");
		back.setBounds(10, 420, 50, 20);
		panel.add(back);
		back.addActionListener(ae -> goBack());
	}
	
	private void goBack(){
		resetPanel();
		init();
	}
	
	private void goBackFromLogs(){
		resetPanel();
		welcome();
	}
	
	private void bankLogin(){
		for(int i = 0; i < clients.size(); i++){ 
			if(username.getText().equals(clients.get(i).getUsername()) 
			&& new String(password.getPassword()).equals(clients.get(i).getPassword())){
				CURRENT_CUSTOMER = i;
				welcome();	
				return;
			} else {
				System.err.println("Yahello, Incorrect password and/or username");
				usernameLabel.setForeground(Color.RED);
				passwordLabel.setForeground(Color.RED);
			}
		}
	}
	
	private void welcome() {
		resetPanel(); //Inloggad
		
		back = new JButton("Logout");
		back.setBounds(10, 420, 80, 20);
		panel.add(back);
		back.addActionListener(ae -> goBack());
		
		JButton showAcc = new JButton("Show Accounts");
		showAcc.setBounds(10, 80, 150, 20);
		panel.add(showAcc);
		showAcc.addActionListener(ae -> showAccounts());
	
		JButton showLogs = new JButton("Show Account Logs");
		showLogs.setBounds(10, 50, 150, 20);
		panel.add(showLogs);
		showLogs.addActionListener(ae -> showLog());
		
		JLabel welcome = new JLabel("welcome: " + clients.get(CURRENT_CUSTOMER).getFirstName() + " " + clients.get(CURRENT_CUSTOMER).getLastName());
		welcome.setBounds(10, 10, WIDTH, 20);
		panel.add(welcome);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void showLog(){
		resetPanel();
		String[] data = {"Sparkonto 1", "Sparkonto 2", "Sparkonto 3", "Transaktionskonto 1", "Transaktionskonto 2", "Transaktionskonto 3"};
		list = new JList();
		list.setBounds(0, 0, 150, 100);	
		panel.add(list);
		temp = new JTextArea();
		temp.setBounds(160, 0, WIDTH, HEIGHT);
		panel.add(temp);
		
		 list.setModel(new AbstractListModel() {

	            String[] strings = data;

	            @Override
	            public int getSize() {
	                return strings.length;
	            }

	            @Override
	            public Object getElementAt(int i) {
	                return strings[i];
	            }
	        });
		
		list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                list1ValueChanged(evt);
            }
        });
		
		back = new JButton("Back");
		back.setBounds(10, 420, 80, 20);
		panel.add(back);
		back.addActionListener(ae -> goBackFromLogs());
	}
	
	private void list1ValueChanged(javax.swing.event.ListSelectionEvent evt){
        String s = (String) list.getSelectedValue();
        if (s.equals("Sparkonto 1")) {
        	System.out.println(clients.get(CURRENT_CUSTOMER).getSparKonto(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(3)).getKontoNummer());
            temp.setText("Rent: "+clients.get(CURRENT_CUSTOMER).getSparKonto(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(0)).getRent()+"%"
            		+"\n"+"Current account balance: "
            		+clients.get(CURRENT_CUSTOMER).getSparKonto(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(0)).getCurrentAmount()+"Kr"
            		+"\n"+"Account Number: "+clients.get(CURRENT_CUSTOMER).getSparkontoNummer(0));
        }
        
        if (s.equals("Sparkonto 2")) {
            temp.setText("Rent: "+clients.get(CURRENT_CUSTOMER).getSparKonto(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(1)).getRent()+"%"
            		+"\n"+"Current account balance: "+clients.get(CURRENT_CUSTOMER).getSparKonto(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(1)).getCurrentAmount()+"Kr"
            		+"\n"+"Account Number: "+clients.get(CURRENT_CUSTOMER).getSparkontoNummer(1));
        }
        
        if (s.equals("Sparkonto 3")) {
            temp.setText("Rent: "+clients.get(CURRENT_CUSTOMER).getSparKonto(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(2)).getRent()+"%"
            		+"\n"+"Current account balance: "+clients.get(CURRENT_CUSTOMER).getSparKonto(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(2)).getCurrentAmount()+"Kr"
            		+"\n"+"Account Number: "+clients.get(CURRENT_CUSTOMER).getSparkontoNummer(2));
        }
        
        if (s.equals("Transaktionskonto 1")) {
            temp.setText("Current account balance: "+ clients.get(CURRENT_CUSTOMER).getTransaktionskonto(clients.get(CURRENT_CUSTOMER).getTransaktionsNummer(0)).getCurrentAmount()+"Kr"
            		+"\n"+"Account Number: "+clients.get(CURRENT_CUSTOMER).getTransaktionsNummer(0));
        }

        if (s.equals("Transaktionskonto 2")) {
            temp.setText("Current account balance: "+ clients.get(CURRENT_CUSTOMER).getTransaktionskonto(clients.get(CURRENT_CUSTOMER).getTransaktionsNummer(1)).getCurrentAmount()+"Kr"
            		+"\n"+"Account Number: "+clients.get(CURRENT_CUSTOMER).getTransaktionsNummer(1));
        }        
        
        if (s.equals("Transaktionskonto 3")) {
            temp.setText("Current account balance: "+ clients.get(CURRENT_CUSTOMER).getTransaktionskonto(clients.get(CURRENT_CUSTOMER).getTransaktionsNummer(2)).getCurrentAmount()+"Kr"
            		+"\n"+"Account Number: "+clients.get(CURRENT_CUSTOMER).getTransaktionsNummer(2));
        }
    }
	
	private void showAccounts(){
		JTextArea area = new JTextArea();
		area.setBounds(270, 70, 100, 100);
		area.setEditable(false);
		panel.add(area);
		area.setText(clients.get(CURRENT_CUSTOMER).getSparkontoNummer(0)+"\n"+clients.get(CURRENT_CUSTOMER).getSparkontoNummer(1)+"\n"+clients.get(CURRENT_CUSTOMER).getSparkontoNummer(2)+"");
	}
	
	private void checkAddInfo(){
		if(clientNr.getText().isEmpty()){
			clientNrLabel.setForeground(Color.RED);
		} else {
			clientNrLabel.setForeground(Color.BLACK);
		}
		
		if(kapital.getText().isEmpty()){
			kapitalLabel.setForeground(Color.RED);
		} else {
			kapitalLabel.setForeground(Color.BLACK);
		}
	}
	
	private void addTransaktionskonto(){
		checkAddInfo();
		if(!clientNr.getText().isEmpty() && !kapital.getText().isEmpty()){
			if(Integer.parseInt(clientNr.getText()) <= clients.size()){
				Client.addTransaktionkonto(Integer.parseInt(clientNr.getText()), Integer.parseInt(kapital.getText()));
				checkAddInfo();
			} else {
				System.err.println("Client does not exist");
			}
		}
	}
	
	private void addSparkonto(){
		checkAddInfo();
		if(!clientNr.getText().isEmpty() && !kapital.getText().isEmpty()){
			if(Integer.parseInt(clientNr.getText()) <= clients.size()){
				Client.addSparkonto(Integer.parseInt(clientNr.getText()), Integer.parseInt(kapital.getText()));
				checkAddInfo();
			} else {
				System.err.println("Client does not exist");
			}
		}
	}

	private void resetPanel(){
		panel.removeAll();
		panel.repaint();
	}


	public static long randomKontoNumber(int length){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++){
			sb.append((int)(Math.random() * 10) + "");
		}
		return Long.parseLong(sb.toString());
	}

	private void newClient(){
		if(firstName.getText().isEmpty() || lastName.getText().isEmpty() || personNr.getText().isEmpty() 
				|| adress.getText().isEmpty() || zip.getText().isEmpty() || city.getText().isEmpty()
				|| phoneNr.getText().isEmpty() || email.getText().isEmpty()){

			checkInfo();
			System.err.println("Yahello, Insufficient information to create client");

		} else if(firstName.getText().isEmpty() == false 
				&& lastName.getText().isEmpty() == false 
				&& personNr.getText().isEmpty() == false
				&& adress.getText().isEmpty() == false 
				&& zip.getText().isEmpty() == false
				&& city.getText().isEmpty() == false
				&& phoneNr.getText().isEmpty() == false 
				&& email.getText().isEmpty() == false) {
			
			c = new Client(firstName.getText(), lastName.getText(), 
					personNr.getText(), adress.getText(), zip.getText(), 
					city.getText(), phoneNr.getText(), email.getText());

			clients.add(c);
			clearInfo();
			clientAdded();
			
		}
	}
	
	private void clientAdded(){
		Color fg = Color.black;
		firstNameLabel.setForeground(fg);
		lastNameLabel.setForeground(fg);
		personLabel.setForeground(fg);
		personLabel.setForeground(fg);
		adressLabel.setForeground(fg);
		zipLabel.setForeground(fg);
		cityLabel.setForeground(fg);
		phoneLabel.setForeground(fg);
		emailLabel.setForeground(fg);
		
		firstNameLabel.setText("First Name:");
		lastNameLabel.setText("Last Name:");
		personLabel.setText("Person number:");
		adressLabel.setText("Adress:");
		zipLabel.setText("Post Code:");
		cityLabel.setText("City:");
		phoneLabel.setText("Phone number:");
		emailLabel.setText("Email:");
		
		System.out.println("Total number of clients added" + ": " + clients.size());
	}

	private void clearInfo(){
		firstName.setText("");
		lastName.setText("");
		personNr.setText("");
		adress.setText("");
		zip.setText("");
		city.setText("");
		phoneNr.setText("");
		email.setText("");
	}

	private void checkInfo(){
		if(firstName.getText().isEmpty()) { 
			firstNameLabel.setForeground(Color.RED);
			firstNameLabel.setText("First Name: Invalid Name");
		} else {
			firstNameLabel.setForeground(Color.BLACK);
			firstNameLabel.setText("First Name:");
		}
		if(lastName.getText().isEmpty()) {
			lastNameLabel.setForeground(Color.RED);
			lastNameLabel.setText("Last Name: Invalid Name");
		} else {
			lastNameLabel.setForeground(Color.BLACK);
			lastNameLabel.setText("Last Name:");
		}
		if(personNr.getText().isEmpty()) {
			personLabel.setForeground(Color.RED);
			personLabel.setText("Person number: Invalid Number");
		} else {
			personLabel.setForeground(Color.BLACK);
			personLabel.setText("Person Number:");
		}
		if(adress.getText().isEmpty()) {
			adressLabel.setForeground(Color.RED);
			adressLabel.setText("Adress: Invalid Adress");
		} else {
			adressLabel.setForeground(Color.BLACK);
			adressLabel.setText("Adress:");
		}
		if(zip.getText().isEmpty()) {
			zip.setForeground(Color.RED);
			zip.setText("Post Code: Invalid Code");
		} else {
			zipLabel.setForeground(Color.BLACK);
			zipLabel.setText("Post Code:");
		}
		if(city.getText().isEmpty()) {
			cityLabel.setForeground(Color.RED);
			cityLabel.setText("City: Invalid City");
		} else {
			cityLabel.setForeground(Color.BLACK);
			cityLabel.setText("City:");
		}
		if(phoneNr.getText().isEmpty()) {
			phoneLabel.setForeground(Color.RED);
			phoneLabel.setText("Phone Number: Invalid Number");
		} else {
			phoneLabel.setForeground(Color.BLACK);
			phoneLabel.setText("Phone Number:");
		}
		if(email.getText().isEmpty()) {
			emailLabel.setForeground(Color.RED);
			emailLabel.setText("Email: Invalid Email");
		} else {
			emailLabel.setForeground(Color.BLACK);
			emailLabel.setText("Email:");
		}
	}

	private void addClient(){
		for(int i = 0; i < clients.size(); i++){
			objectHandler = new ObjectFileHandler("kunder/k"+i);
			objectHandler.write(clients.get(i), "kunder/k"+i+"/info.ser");
			
			if(clients.get(i).getKundNr() == 0){
				clients.get(i).setKundNr((int)(Math.random()*1000));
			}
		}
		saveClients();
	}
	
	
	public static void main(String[] args){
		new Main();
	}
}
