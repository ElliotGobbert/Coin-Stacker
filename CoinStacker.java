import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.event.*;
import java.util.ArrayList;
public class CoinStacker {
  private JFrame jFrame = new JFrame("COIN STACKER: by Biz L.L.C.");

  private JLabel errorLabel = new JLabel("<html> You can't add a coin of this size here! </html>");

  private int startingXPosition = 50;
  private int startingYPosition = 300;

  private int spaceBetweenCoins = 15;//when iterating the currentYPosition = currentYPosition + spaceBetweenCoins;
  
  public static final double penny = 0.01;
  public static final double nickel = 0.05;
  public static final double dime = 0.1;
  public static final double quarter = 0.25;
  
  public final ImageIcon pennyImage = new ImageIcon("penny.png");
  public final ImageIcon nickelImage = new ImageIcon("nickel.png");
  public final ImageIcon dimeImage = new ImageIcon("dime.png");
  public final ImageIcon quarterImage = new ImageIcon("quarter.png");
  
  private double minVal = 0.75;
  private double maxVal = 2.75;
  private int coinLim;
  private double moneyGoal;
  private boolean hard;

  // private double moneyGoal = (double) Math.round(((Math.random() * (maxVal-minVal)) + minVal) * 100) / 100;

  //double moneyGoal = 0.99;
  
  
  
  private double moneyCurrent = 0.0;

  Border border = BorderFactory.createLineBorder(Color.BLACK);
  Border redBorder = BorderFactory.createLineBorder(Color.RED);

  private JLabel currentLabel;
  private JLabel goalLabel;
  private JLabel coinLabel;
  private JLabel coinCurrent;

  //different screens for game (main), final screen after beating game (endScreen), and the instructions
  private JPanel main = new JPanel();
  private JPanel endScreen = new JPanel();
  private JPanel instructions = new JPanel();

  

  //stack of coins to add up to random value ($0.75 - $5.00)
  Stack<Double> coinStack = new Stack<Double>();
  
  ArrayList<JLabel> imageStack = new ArrayList<JLabel>();
  public CoinStacker() {
    showInstructions();
  }

  private void showInstructions() { 
    jFrame.getContentPane().removeAll();
    jFrame.getContentPane().add(instructions);
    main.removeAll();
    JLabel title = new JLabel("<html> COINSTACKER</html>");
    title.setBorder(border);
    title.setPreferredSize(new Dimension(100, 50));
    
    title.setHorizontalAlignment(JLabel.CENTER);
    title.setVerticalAlignment(JLabel.CENTER);
    title.setBounds(150, 20, 200, 50);
    title.setFont(new Font("Serif", Font.PLAIN, 25));

    JLabel credits = new JLabel("<html> By: Devin Y. and Elliot G.</html>");
    credits.setPreferredSize(new Dimension(100, 50));
    
    credits.setHorizontalAlignment(JLabel.CENTER);
    credits.setVerticalAlignment(JLabel.CENTER);
    credits.setBounds(365, 20, 100, 50);
    credits.setFont(new Font("Serif", Font.PLAIN, 14));

    JButton easyStart = new JButton("<html> EASY MODE </html>");
    easyStart.setPreferredSize(new Dimension(30, 30)); 
    easyStart.setMargin(new Insets(0, 0, 0, 0));
    easyStart.setBounds(165,330,75,45);
    easyStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hard = false;
        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(main);
        createAndShowGUI();
      }
    });
    JButton hardStart = new JButton("<html> HARD MODE </html>");
    hardStart.setPreferredSize(new Dimension(30, 30)); 
    hardStart.setMargin(new Insets(0, 0, 0, 0));
    hardStart.setBounds(255,330,75,45);
    hardStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hard = true;
        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(main);
        createAndShowGUI();
      }
    });
    
    JLabel instructionsLabel = new JLabel("<html> Instructions: <br> - Stack various coins of various sizes and values to reach a specific random value!  <br>- Coins MUST be stacked in decreasing coin size, meaning a larger coin could not be placed above a smaller one <br> - EASY MODE: Any coin combination <br> - HARD MODE: Specific numbers of coins required </html>");
    instructionsLabel.setPreferredSize(new Dimension(100, 50));
    instructionsLabel.setBorder(border);
    instructionsLabel.setHorizontalAlignment(JLabel.CENTER);
    instructionsLabel.setVerticalAlignment(JLabel.CENTER);
    instructionsLabel.setBounds(20, 75, 275, 250);
    instructionsLabel.setFont(new Font("Serif", Font.PLAIN, 14));

    JLabel coinStackImg = new JLabel(new ImageIcon("coinStackImg.png"));
    coinStackImg.setPreferredSize(new Dimension(100, 50));
    coinStackImg.setBorder(border);
    coinStackImg.setHorizontalAlignment(JLabel.CENTER);
    coinStackImg.setVerticalAlignment(JLabel.CENTER);
    coinStackImg.setBounds(300, 75, 186, 250);
    
    instructions.setSize(new Dimension(500, 400));
    instructions.setLayout(null);
    instructions.add(coinStackImg);
    instructions.add(instructionsLabel);
    instructions.add(easyStart);
    instructions.add(hardStart);
    instructions.add(title);
    instructions.add(credits);
    instructions.setVisible(true);
    
    jFrame.setSize(500,400);
    jFrame.add(instructions, BorderLayout.CENTER);
    jFrame.setVisible(true);
  }

  
  private void createAndShowGUI() {
    jFrame.add(main, BorderLayout.CENTER);
    jFrame.setSize(500,400);
    main.setLayout(null);
    main.setSize(new Dimension(500, 400));

    randomize();
    
    //Adding the labels for currentMoney and goalMoney to the screen
    currentLabel = new JLabel("<html> Current: <br>$" + String.format("%.2f", moneyCurrent) + "</html>");
    currentLabel.setBorder(border);
    currentLabel.setPreferredSize(new Dimension(100, 50));
    currentLabel.setHorizontalAlignment(JLabel.CENTER);
    currentLabel.setVerticalAlignment(JLabel.CENTER);
    currentLabel.setBounds(25, 0, 100, 50);

  
    
    goalLabel = new JLabel("<html> Goal: <br>$" + String.format("%.2f", moneyGoal)+ "</html>");
    goalLabel.setBorder(border);
    goalLabel.setPreferredSize(new Dimension(100, 50));
    goalLabel.setHorizontalAlignment(JLabel.CENTER);
    goalLabel.setVerticalAlignment(JLabel.CENTER);
    goalLabel.setBounds(150, 0, 100, 50);

    

    errorLabel.setBorder(redBorder);
    errorLabel.setPreferredSize(new Dimension(100, 50));
    errorLabel.setHorizontalAlignment(JLabel.CENTER);
    errorLabel.setVerticalAlignment(JLabel.CENTER);
    errorLabel.setBounds(170, 300, 120, 50);
    errorLabel.setBackground(new Color(255, 71, 76));
    errorLabel.setOpaque(true);
    errorLabel.setVisible(false);

    JButton addPenny = new JButton("Penny");
    addPenny.setPreferredSize(new Dimension(30, 30)); 
    addPenny.setMargin(new Insets(0, 0, 0, 0));
    addPenny.setActionCommand("Penny");
    addPenny.setBounds(425,30,75,75);

    JButton addDime = new JButton("Dime");
    addDime.setPreferredSize(new Dimension(30, 30)); 
    addDime.setMargin(new Insets(0, 0, 0, 0));
    addDime.setActionCommand("Dime");
    addDime.setBounds(425,115,75,75);

    JButton addNickel = new JButton("Nickel");
    addNickel.setPreferredSize(new Dimension(30, 30)); 
    addNickel.setMargin(new Insets(0, 0, 0, 0));
    addNickel.setActionCommand("Nickel");
    addNickel.setBounds(425,200,75,75);

    JButton addQuarter = new JButton("Quarter");
    addQuarter.setPreferredSize(new Dimension(30, 30)); 
    addQuarter.setMargin(new Insets(0, 0, 0, 0));
    addQuarter.setActionCommand("Quarter");
    addQuarter.setBounds(425,285,75,75);

    JButton undo = new JButton("Undo");
    undo.setPreferredSize(new Dimension(30, 30));
    undo.setMargin(new Insets(0, 0, 0, 0));
    undo.setActionCommand("Undo");
    undo.setBounds(300,150,100,100);

    //Adding everything to the main game panel
    if(hard) {
      coinLabel = new JLabel("<html> Coin Target: <br>" + coinLim + "</html>");
      coinLabel.setBorder(border);
      coinLabel.setPreferredSize(new Dimension(100, 50));
      coinLabel.setHorizontalAlignment(JLabel.CENTER);
      coinLabel.setVerticalAlignment(JLabel.CENTER);
      coinLabel.setBounds(275, 0, 100, 50);

      coinCurrent = new JLabel("<html> # of Coins: <br>" + (coinStack.size()) + "</html>");
      coinCurrent.setBorder(border);
      coinCurrent.setPreferredSize(new Dimension(100, 50));
      coinCurrent.setHorizontalAlignment(JLabel.CENTER);
      coinCurrent.setVerticalAlignment(JLabel.CENTER);
      coinCurrent.setBounds(275, 65, 100, 50);

      main.add(coinLabel);
      main.add(coinCurrent);
    }
    main.add(errorLabel);
    main.add(goalLabel);
    main.add(currentLabel);
    main.add(addPenny);
    main.add(addNickel);
    main.add(addDime);
    main.add(addQuarter);
    main.add(undo);
    main.setVisible(true);

    main.setBackground(Color.WHITE);
    jFrame.add(main, BorderLayout.CENTER);
    jFrame.setVisible(true);
    
    //addPenny
    addPenny.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(coinStack.isEmpty() || !(coinStack.peek() == dime)) {
          errorLabel.setVisible(false);
          coinStack.push(penny);
          
          JLabel temp = new JLabel(pennyImage);
          imageStack.add(temp);
          main.add(temp);
          moneyCurrent = moneyCurrent + penny;

          placeCoins();
        } else {
          errorLabel.setVisible(true);
        }   
      }
    }); 

    //addDime
    addDime.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        errorLabel.setVisible(false);
        coinStack.push(dime);
        JLabel temp = new JLabel(dimeImage);
        imageStack.add(temp);
        main.add(temp);
        moneyCurrent = moneyCurrent + dime;
        placeCoins();
      }  
    }); 

    //addNickel
    addNickel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(coinStack.isEmpty() || coinStack.peek() == quarter || coinStack.peek() == nickel) {
          errorLabel.setVisible(false);
          coinStack.push(nickel);
          JLabel temp = new JLabel(nickelImage);
          imageStack.add(temp);
          main.add(temp);
          moneyCurrent = moneyCurrent + nickel;
          placeCoins();
        } else {
          errorLabel.setVisible(true);
        }
      }
    }); 

    //addQuarter
    addQuarter.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          if(coinStack.isEmpty() || coinStack.peek() == quarter) {
            coinStack.push(quarter);
            JLabel temp = new JLabel(quarterImage);
            imageStack.add(temp);
            main.add(temp);
            moneyCurrent = moneyCurrent + quarter;
            placeCoins();
          } else {
            errorLabel.setVisible(true);
          }              
      }
    }); 

    //undo
    undo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        errorLabel.setVisible(false);
        if(!coinStack.isEmpty()) { 
          imageStack.get(imageStack.size() - 1).setVisible(false);
          imageStack.remove(imageStack.size() - 1);
          moneyCurrent = moneyCurrent - coinStack.pop();
          placeCoins();
        }
              
      }
    }); 
    
  }


  

  private void placeCoins() {
    //set all coins at proper locations, update the current money label at top
    checkGameEnd();
    currentLabel.setText("<html> Current: <br>$" + String.format("%.2f", Math.abs(moneyCurrent)) + "</html>");
    goalLabel.setText("<html> Goal: <br>$" + String.format("%.2f", Math.abs(moneyGoal)) + "</html>");
    if(hard) {
      coinCurrent.setText("<html> # of Coins: <br>" + (coinStack.size()+1) + "</html>");
    }
    int i = startingYPosition;
    //Also needed to change this to for() loop
    for (int j = 0; j < imageStack.size(); j++) {
      JLabel img = imageStack.get(j);
      //Each of the coins, due to their sizes, needs a different amount of space in betwen them and other coins, to not look goofy, so making if() to fix
      if (coinStack.search(quarter) != -1 && ((coinStack.search(quarter) == j - 1) || (coinStack.search(quarter) == j))) {
        
          spaceBetweenCoins = 10;
        
      } else if (!(coinStack.search(nickel) == -1) && ((coinStack.search(nickel) == j - 1) || (coinStack.search(nickel) == j))) {
          spaceBetweenCoins = 5;
        
        } else if (!(coinStack.search(penny) == -1) && ((coinStack.search(penny) == j - 1) || (coinStack.search(penny) == j))) {
          spaceBetweenCoins = 5;
        
      } else if (!(coinStack.search(dime) == -1) && ((coinStack.search(dime) == j - 1) || (coinStack.search(dime) == j))) {
          spaceBetweenCoins = 3;
      }
      img.setBounds(startingXPosition, i, 50, 50);
      i = i - spaceBetweenCoins;
      main.add(img);
      main.setVisible(true);
    }
    
  }
  
//THIS DOES NOT ALWAYS WORK, SOMETIMES PRODUCES HIGHER THAN MINIMUM COMBO (think about it next class)  
  private int findMinCoins() {
    double tempMoney = moneyGoal;
    int coins = 0;
    while(!(tempMoney == 0)) {
      if(tempMoney >= quarter) {
        tempMoney = (double)Math.round((tempMoney - quarter) * 100) / 100;
        coins++;
      } else if(tempMoney >= dime) {
        tempMoney = (double)Math.round((tempMoney - dime) * 100) / 100;
        coins++;
      } else if(tempMoney >= nickel) {
        tempMoney = (double)Math.round((tempMoney - nickel) * 100) / 100;
        coins++;
      } else if(tempMoney >= penny) {
        tempMoney = (double)Math.round((tempMoney - penny) * 100) / 100;
        coins++;
      }
    }
    return coins;
  }
  
  private void randomize() {
    moneyGoal = (Math.random() * (maxVal-minVal)) + minVal;
    int min = findMinCoins();
    coinLim = (int)((Math.random()*(moneyGoal*10)) + min);
  }


  private void outputError(String messageToOutput) { //Broadcasts a message of our choosing for a couple seconds(usually an error)
    errorLabel.setText("<html>" + messageToOutput + "</html>");
    errorLabel.setVisible(true);
    errorLabel.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
      errorLabel.setVisible(false);
			e.printStackTrace();
		}

		/*
    try {
      Thread.sleep(1000);
    } catch (Exception e) {} finally {
      System.out.println("end");
      errorLabel.setVisible(false);
    }*/
    // errorLabel.setVisible(true);
  }
  
  private void checkGameEnd() {
    endScreen.removeAll();
    boolean won = false;
    if(hard) {
      won = String.format("%.2f", Math.abs(moneyCurrent)).equals(String.format("%.2f", Math.abs(moneyGoal))) && coinStack.size()+1 == coinLim;
    } else if (!hard){
      won = String.format("%.2f", Math.abs(moneyCurrent)).equals(String.format("%.2f", Math.abs(moneyGoal)));
    }
    if(won) {
      jFrame.getContentPane().removeAll();
      jFrame.getContentPane().add(endScreen);
      
      JLabel title = new JLabel("<html> GAME OVER</html>");
      title.setBorder(border);
      title.setPreferredSize(new Dimension(100, 50));
    
      title.setHorizontalAlignment(JLabel.CENTER);
      title.setVerticalAlignment(JLabel.CENTER);
      title.setBounds(150, 20, 200, 50);
      title.setFont(new Font("Serif", Font.PLAIN, 25));

      JLabel score = new JLabel("<html> Minimum Coins: " + findMinCoins() + "<br>You Used: "+ (coinStack.size() + 1) + "</html>"); //coinStack.size + 1 because the stack thing is goofy, check tmrw
      score.setPreferredSize(new Dimension(100, 50));
    
      score.setHorizontalAlignment(JLabel.CENTER);
      score.setVerticalAlignment(JLabel.CENTER);
      score.setBounds(75, 75, 325, 215);
      score.setFont(new Font("Serif", Font.PLAIN, 18));

      
      JButton returnToMenu = new JButton("<html>RETURN TO MENU</html>");
      returnToMenu.setPreferredSize(new Dimension(30, 30)); 
      returnToMenu.setMargin(new Insets(0, 0, 0, 0));
      returnToMenu.setBounds(255,330,75,45);
      returnToMenu.addActionListener(new ActionListener() {
        //DOESNT WORK, NEEDS TO MAKE MAIN SCREEN BLANK/RESET
        public void actionPerformed(ActionEvent e) {
          coinStack = new Stack();
          imageStack = new ArrayList<JLabel>();
          moneyCurrent = 0.0;
          placeCoins();
          jFrame.getContentPane().removeAll();
          instructions.removeAll();
          
          showInstructions();
          
        }
      }); 


      JButton restartGame = new JButton("RESTART");
      restartGame.setPreferredSize(new Dimension(30, 30)); 
      restartGame.setMargin(new Insets(0, 0, 0, 0));
      restartGame.setBounds(165,330,75,45);
      restartGame.addActionListener(new ActionListener() {
        //DOESNT WORK, NEEDS TO MAKE MAIN SCREEN BLANK/RESET
        public void actionPerformed(ActionEvent e) {
          coinStack = new Stack();
          imageStack = new ArrayList<JLabel>();
          moneyCurrent = 0.0;
          placeCoins();
          jFrame.getContentPane().removeAll();
          main.removeAll();
          jFrame.getContentPane().add(main);
          createAndShowGUI();
        }
      }); 

      endScreen.setSize(new Dimension(500, 400));
      endScreen.setLayout(null);
      endScreen.add(title);
      endScreen.add(score);
      endScreen.setVisible(true);
      endScreen.add(restartGame);
      endScreen.add(returnToMenu);
      
      jFrame.setSize(500,400);
      jFrame.add(endScreen, BorderLayout.CENTER);
      jFrame.setVisible(true);
      
      
    }
  }
}

