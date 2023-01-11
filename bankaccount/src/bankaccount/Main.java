package bankaccount;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Main extends JFrame {

    public Main() {
        setTitle("Account App");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1));

        JButton createButton = new JButton("Create Account");
        JButton loginButton = new JButton("Log in");

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateAccountWindow createAccountWindow = new CreateAccountWindow();
                createAccountWindow.setVisible(true);
            }
        });


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	remove(createButton);
            	remove(loginButton);
            	
            	setTitle("Log in your account");
            	setLayout(new GridLayout(3,2));
            	
            	JLabel cardNumberLabel = new JLabel("Enter card number: ");
            	JTextField cardNumberField = new JTextField(20);
            	
            	JLabel cardPINLabel = new JLabel("Enter card pin:");
            	JTextField cardPINField = new JTextField(10);
            	
            	JButton loginButton2 = new JButton("Log in");
            	JButton returnButton = new JButton("Return");
            	
            	loginButton2.addActionListener(new ActionListener() {
            		public void actionPerformed(ActionEvent ae) {
            			long cardNumberL;
            			int cardPINL;
            			
            			try {
            				cardNumberL = Long.parseLong(cardNumberField.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
            			
            			try {
                			cardPINL = Integer.parseInt(cardPINField.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Error2", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
            			
            			Connection conn = null;
                        Statement stmt = null;
                        
                        String url = "jdbc:mysql://localhost:3306/";
                		String database = "bankaccount";
                		String driver = "com.mysql.cj.jdbc.Driver";
                		String username = "root";
                		String password = "";
                		
                        try {
                        	Class.forName(driver);
                        	
                        	conn = DriverManager.getConnection(url+database, username, password);
                        	stmt = conn.createStatement();
                        	String sql = "select cardholdername,balance from accounts where cardnumber=(?) and pin=(?)";
                        	PreparedStatement ps = conn.prepareStatement(sql);
                        	ps.setLong(1, cardNumberL);
                        	ps.setInt(2, cardPINL);
                        	ResultSet rs = ps.executeQuery();
                        	if(!rs.next()) {
                        		JOptionPane.showMessageDialog(null, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                        	} else {
                        		setTitle("Account");
                        		setLayout(new GridLayout(8,1));
                        		remove(returnButton);
                        		remove(cardNumberLabel);
                                remove(cardNumberField);
                                remove(cardPINLabel);
                                remove(cardPINField);
                                remove(loginButton2);
                        		remove(createButton);
                                remove(loginButton);
                                String nume = rs.getString(1);
                                String balanta = rs.getString(2);
                                JLabel welcomeLabel = new JLabel("Welcome " + nume);
                               // JLabel balanceLabel = new JLabel("Your current balance: " + balanta);
                                JButton balanceButton = new JButton("Watch banalnce");
                                JButton depositButton = new JButton("Deposit");
                                JButton withdrawButton = new JButton("Withdraw");
                                JButton transferButton = new JButton("Transfer money");
                                JButton loanButton = new JButton("Take a loan");
                                JButton disconnectButton = new JButton("Disconnect");
                                
                                balanceButton.addActionListener(new ActionListener() {
                            		public void actionPerformed(ActionEvent e) {
                            			remove(welcomeLabel);
                                		remove(balanceButton);
                                		remove(depositButton);
                                		remove(withdrawButton);
                                		remove(transferButton);
                                		remove(loanButton);
                                		remove(disconnectButton);
                                		
                                		JLabel balanceLabel = new JLabel("Your balance" + balanta);
                                		JButton returnButton = new JButton("Return");
                                		
                                		returnButton.addActionListener(new ActionListener() {
                                        	public void actionPerformed(ActionEvent e) {
                                        		remove(returnButton);
                                        		remove(balanceLabel);
                                        		
                                        		add(welcomeLabel);
                                                add(balanceButton);
                                                add(depositButton);
                                                add(withdrawButton);
                                                add(transferButton);
                                                add(loanButton);
                                                add(disconnectButton);
                                                repaint();
                                                revalidate();
                                                welcomeLabel.setVisible(true);
                                                balanceLabel.setVisible(true);
                                                balanceButton.setVisible(true);
                                                depositButton.setVisible(true);
                                                withdrawButton.setVisible(true);
                                                transferButton.setVisible(true);
                                                loanButton.setVisible(true);
                                                disconnectButton.setVisible(true);
                                        	}
                                		});
                                		
                                		//add(balanceText);
                                		add(balanceLabel);
                                		add(returnButton);
                                		repaint();
                                        revalidate();
                                        balanceLabel.setVisible(true);
                                        returnButton.setVisible(true);
                                		//balanceText.setVisible(true);
                            		}
                                });
                                
                            	depositButton.addActionListener(new ActionListener() {
                            		public void actionPerformed(ActionEvent ae) {
                            			JFrame frame2 = new JFrame();
                            			frame2.setSize(400,250);
                            			frame2.setVisible(true);
                            			frame2.setLayout(new GridLayout(2,2));
                            			frame2.setTitle("Deposit");
                            			
                            			JLabel depositLabel = new JLabel("Deposit amount");
                            			JTextField depositField = new JTextField();
                            			JButton depositButton2 = new JButton("Deposit");
                            			
                            			depositButton2.addActionListener(new ActionListener() {
                                    		public void actionPerformed(ActionEvent ae) {
                                    			int depositAmount;
                                    			try {
                                        			depositAmount = Integer.parseInt(depositField.getText());
                                                } catch (NumberFormatException ex) {
                                                    JOptionPane.showMessageDialog(null, "Error3", "Error", JOptionPane.ERROR_MESSAGE);
                                                    return;
                                                }
                                    			int newBalance = depositAmount + Integer.parseInt(balanta);
                                    			String query = "update accounts set balance=(?) where cardholdername=(?) and cardnumber=(?)";
                                    			Connection conn2 = null;
                                    			try {
                                    				Class.forName(driver);
                                    		
                                    				conn2 = DriverManager.getConnection(url+database, username, password);
                                    				PreparedStatement ps2 = conn2.prepareStatement(query);
                                    				ps2.setInt(1, newBalance);
                                    				ps2.setString(2, nume);
                                    				ps2.setLong(3, cardNumberL);
                                    				ps2.executeUpdate();
                                    				
                                    				JOptionPane.showMessageDialog(null, "Ammount deposited", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    			} catch (SQLException | ClassNotFoundException se) {
                                                	se.printStackTrace();
                                    			} finally {
                                    				
                                    			}
                                    		}
                            			});
                            			
                            			frame2.add(depositLabel);
                            			frame2.add(depositField);
                            			frame2.add(depositButton2);
                                        depositLabel.setVisible(true);
                                        depositField.setVisible(true);
                                        depositButton2.setVisible(true);
                            		}
                            	});
                                
                                add(welcomeLabel);
                                //add(balanceLabel);
                                add(balanceButton);
                                add(depositButton);
                                add(withdrawButton);
                                add(transferButton);
                                add(loanButton);
                                add(disconnectButton);
                                repaint();
                                revalidate();
                                welcomeLabel.setVisible(true);
                               // balanceLabel.setVisible(true);
                                balanceButton.setVisible(true);
                                depositButton.setVisible(true);
                                withdrawButton.setVisible(true);
                                transferButton.setVisible(true);
                                loanButton.setVisible(true);
                                disconnectButton.setVisible(true);
                        	}
                        } catch (SQLException | ClassNotFoundException se) {
                        	se.printStackTrace();
                        } finally {
                        	try {
                        		if(stmt == null) {
                        			stmt.close();
                        		}
                        	} catch (SQLException se2) {
                        	}
                        	try {
                        		if(conn != null) {
                        			conn.close();
                        		}
                        	} catch (SQLException se) {
                        		se.printStackTrace();
                        	}
                        }
            		}
            	});
            	
            	returnButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		remove(returnButton);
                		remove(cardNumberLabel);
                        remove(cardNumberField);
                        remove(cardPINLabel);
                        remove(cardPINField);
                        remove(loginButton2);
                		remove(createButton);
                        remove(loginButton);
                        repaint();
                        revalidate();
                        add(createButton);
                        add(loginButton);
                        createButton.setVisible(true);
                        loginButton.setVisible(true);
                        createButton.setEnabled(true);
                        loginButton.setEnabled(true);
                        setLayout(new GridLayout(2,1));
                        setTitle("Account App");
                	}
                });
            	
            	add(cardNumberLabel);
            	add(cardNumberField);
            	add(cardPINLabel);
            	add(cardPINField);
            	add(loginButton2);
            	add(returnButton);
            	cardNumberLabel.setVisible(true);
            	cardNumberField.setVisible(true);
            	cardPINLabel.setVisible(true);
            	cardPINField.setVisible(true);
            	loginButton2.setVisible(true);
            	returnButton.setVisible(true);
            	repaint();
                revalidate();
            } });
        add(createButton);
        add(loginButton);
    }

class CreateAccountWindow extends JFrame {

    public CreateAccountWindow() {
        setTitle("Create Account");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3,2));

        JLabel nameLabel = new JLabel("Card holder name:");
        JTextField nameField = new JTextField(40);
        
        JLabel balanceLabel = new JLabel("Insert balance:");
        JTextField balanceField = new JTextField(15);
        
        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardName = nameField.getText();
                String balanceString = balanceField.getText();
                int cardBalance;
                try {
                    cardBalance = Integer.parseInt(balanceString);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid balance entered", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (cardBalance < 50) {
                    JOptionPane.showMessageDialog(null, "Balance must be at least 50 to create the account", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Connection conn = null;
                Statement stmt = null;
                
                String url = "jdbc:mysql://localhost:3306/";
        		String database = "bankaccount";
        		String driver = "com.mysql.cj.jdbc.Driver";
        		String username = "root";
        		String password = "";
                
                try {
                	Class.forName(driver);
                	
                	conn = DriverManager.getConnection(url+database, username, password);
                	stmt = conn.createStatement();
                	
                	Random rand = new Random();
                    long min = 1000000000000000L;
                    long max = 9999999999999999L;
                    
                    long number = rand.nextLong();
                    number = number < 0 ? -number : number;
                    number = (min + (long)(number % (max - min)));
                    long cardNumber = number;
                	
                    int cardPIN = rand.nextInt((9999 - 1000) + 1) + 1000;
                    
                    int cardCSV = rand.nextInt((999 - 100) + 1) + 100;
                    
                    int lastTransaction = 0;
                    
                	PreparedStatement ps = conn.prepareStatement("insert into accounts values (?,?,?,?,?,?)");
                	
                	ps.setString(1, cardName);
                	ps.setLong(2, cardNumber);
                	ps.setInt(3, cardCSV);
                	ps.setInt(4, cardPIN);
                	ps.setInt(5, cardBalance);
                	ps.setInt(6,  lastTransaction);
                	
                	ps.executeUpdate();
                	
                	try (FileWriter fw = new FileWriter("account_details.txt", false);
                            BufferedWriter bw = new BufferedWriter(fw)) {

                           bw.write("Card holder name: " + cardName);
                           bw.newLine();
                           bw.write("Card number: " + cardNumber);
                           bw.newLine();
                           bw.write("Card pin: " + cardPIN);
                           bw.newLine();
                           bw.write("Card csv: " + cardCSV);
                           bw.newLine();
                           JOptionPane.showMessageDialog(null, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                       } catch (IOException ex) {
                           JOptionPane.showMessageDialog(null, "Error writing to file", "Error", JOptionPane.ERROR_MESSAGE);
                       }
                   
                	
                } catch (SQLException | ClassNotFoundException se) {
                	se.printStackTrace();
                } finally {
                	try {
                		if(stmt == null) {
                			stmt.close();
                		}
                	} catch (SQLException se2) {
                	}
                	try {
                		if(conn != null) {
                			conn.close();
                		}
                	} catch (SQLException se) {
                		se.printStackTrace();
                	}
                }
            }
        });

        add(nameLabel);
        add(nameField);
        add(balanceLabel);
        add(balanceField);
        add(createButton);
    }
}

public static void main(String[] args) throws Exception{
    Main main = new Main();
    main.setVisible(true);
	}
}