/**********************************************************
 *                                                                           *
 *  CSCI 470/680-E   Assignment 5     Summer 2018    *
 *                                                                           *
 *  Developer: Alex Danilushkin                                  *
 *                  z1809166                                             *
 *                                                                           *
 *  Section:  2                                                          *
 *                                                                           *
 *  Due Date/Time:  7/30/2018                                   *
 *                                                                           *
 *  Purpose:  Creates and runs a GUI for the               *
 * MileRedeemer class.                                             *
 **********************************************************/
import java.io.File;
import java.io.IOException;
//Java util, decoration, and other
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import java.awt.Graphics;
//Swing Componenets
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;//Used for JList selection
import javax.swing.SpinnerListModel;
import javax.swing.JSpinner;
//Frames, and layout managers
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;//for GridBagLayout
//Listeners and events
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MileApp extends JPanel implements ListSelectionListener , ActionListener{
  
  //Private instance variables to be used throughout the class
  private JTextField normalField, saverField, upCostField, datesField;
  private JList destinationList;
  private MileRedeemer redeemer;
  private JSpinner spinner;
  private JTextField inputMilesField;
  private JTextArea ticketArea;
  private JTextField remainingField;
  
  
  /************************************************************************ 
  * MileApp(): The constructor for this class. It holds everything that *
  * will be placed on the JFrame in the createAndShowGUI method.  *
  * Uses to main JPanels, leftPanel and rightPanel, to hold the swing*
  * components. This method also edits the swing components, adds*
  * background colors, and adds borders                                        *
  ************************************************************************/
  public MileApp() throws IOException{
    
    //Create Panels
    JPanel leftPanel = new JPanel();
    JPanel leftTopPanel = new JPanel();
    JPanel leftBottomPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    
    //Set layouts for panels
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftBottomPanel.setLayout(new GridLayout(4,2));
    leftBottomPanel.setBackground(new Color( 20, 220, 40));
    leftPanel.add(leftTopPanel);
    leftPanel.add(leftBottomPanel);
    rightPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    
    //Prepare to read doc and prompt for file
    redeemer = new MileRedeemer();
    JOptionPane enterFile = new JOptionPane();
    Scanner fileScan = new Scanner(enterFile.showInputDialog("Please enter text file"));
    
    //*********************ADDING TO LEFT PANEL******************************

    //Read file and create JList
    String inStringFile = fileScan.next();
    redeemer.readDestinations(inStringFile);
    destinationList = new JList<String>(redeemer.getCityNames());
    destinationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    destinationList.setPreferredSize(new Dimension(220, 180));
    destinationList.addListSelectionListener(this);
    leftTopPanel.add(destinationList);
    
    //Set Labels and text areas for leftPanel
    JLabel normalMiles, saverMiles, upCost, saverDates;
    normalMiles = new JLabel("Normal Miles");
    saverMiles = new JLabel("Supersaver Miles");
    upCost = new JLabel("Upgrade Cost");
    saverDates = new JLabel("Supersaver Dates");
    normalField = new JTextField(7);
    saverField = new JTextField(7);
    upCostField = new JTextField(7);
    datesField = new JTextField(7);
    normalField.setEditable(false);
    saverField.setEditable(false);
    upCostField.setEditable(false);
    datesField.setEditable(false);
    normalField.setBackground(Color.white); //done for asthetics
    saverField.setBackground(Color.white);
    upCostField.setBackground(Color.white);
    datesField.setBackground(Color.white);
    
    //Adding labels and text areas to LeftBottomPanel
    leftBottomPanel.add(normalMiles);
    leftBottomPanel.add(normalField);
    leftBottomPanel.add(saverMiles);
    leftBottomPanel.add(saverField);
    leftBottomPanel.add(upCost);
    leftBottomPanel.add(upCostField);
    leftBottomPanel.add(saverDates);
    leftBottomPanel.add(datesField);

    //***********************ADDING TO RIGHT PANEL***************************
    
    //Input mile components
    JLabel inputMiles = new JLabel("Enter your Miles");
    c.weightx = 1.0;
    rightPanel.add(inputMiles, c);
    inputMilesField = new JTextField(10);
    c.gridwidth = GridBagConstraints.REMAINDER;
    rightPanel.add(inputMilesField, c);
    
    //Departing Month
    JLabel selectMonth = new JLabel("Select the month of departure");
    c.gridwidth = 20;
    rightPanel.add(selectMonth, c);
    String[] monthStrings = redeemer.getMonthStrings(); //get month names
    SpinnerListModel monthModel = new SpinnerListModel(monthStrings);
    spinner = new JSpinner(monthModel);
    c.gridwidth = GridBagConstraints.REMAINDER;
    rightPanel.add(spinner, c);
    
    //Adding redeem button and ticket text area
    JButton redeemButton = new JButton("Redeem");
    redeemButton.addActionListener(this);
    c.gridwidth = GridBagConstraints.REMAINDER;
    rightPanel.add(redeemButton, c);
    ticketArea = new JTextArea(10, 35);
    ticketArea.setEditable(false);
    rightPanel.add(ticketArea, c);
    
    //Remaining mile components
    JLabel remainingMiles = new JLabel("Remaining Miles");
    c.gridwidth = 20;
    c.weighty = 1.0;
    c.ipady = 5;
    rightPanel.add(remainingMiles, c);
    remainingField = new JTextField(10);
    remainingField.setEditable(false);
    remainingField.setBackground(Color.white);
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weighty = 1.0;
    c.insets = new Insets(10,0,9,0);//This helps push the border for later
                                               //so that its the same size as the left panel border
    rightPanel.add(remainingField, c);
    
    //********************ADDING COLORS AND BORDERS************************
    
    Border blackline = BorderFactory.createLineBorder(Color.black);
    TitledBorder title = BorderFactory.createTitledBorder(blackline, "Destinations");
    title.setTitleJustification(TitledBorder.CENTER);
    leftPanel.setBorder(title);
    TitledBorder title2 = BorderFactory.createTitledBorder(blackline, "Redeem Miles");
    title2.setTitleJustification(TitledBorder.CENTER);
    rightPanel.setBorder(title2);
    
    leftPanel.setBackground(new Color( 20, 220, 40));
    rightPanel.setBackground(new Color(50, 120, 220));
    
    //Add JPanels to JFrame
    add(leftPanel);
    add(rightPanel);
  }
  
  
  /************************************************************************
  * valueChanged(ListSelectionEvent e): Method is used when the     *
  * ListSelectionListener is used. It then calls this method to decide   *
  * to what to do with the ListSelectionEvent. This method in            *
  * particular will only update textfields when the event value           *
  * is adjusted.                                                                              *
  ************************************************************************/
  public void valueChanged(ListSelectionEvent e){
    
    if(e.getValueIsAdjusting()){
      Destination displayedDest = redeemer.getDestObj( destinationList.getSelectedIndex());
      normalField.setText( Integer.toString(displayedDest.getMiles()));
      saverField.setText(Integer.toString(displayedDest.getCheapMiles()));
      upCostField.setText(Integer.toString(displayedDest.getUpgradeMiles()));
      
      //Dates
      //month - 1 to fix array out of bounds
      String[] allMonths = redeemer.getMonthStrings();
      datesField.setText(allMonths[displayedDest.getStartMonth() -1] + "-" +
                        allMonths[displayedDest.getEndMonth() - 1]);
    }
    
  }
  
  
  /************************************************************************
   * actionPreformed(): This method is used when the ActionListener *
   * "hears" an event. It's only tied to one button, the "redeem"        *
   *  button, and shows possible tickets for the client                       *
   ************************************************************************/
  public void actionPerformed(ActionEvent e){
    ticketArea.setText("");
    try{
      String[] allMonths = redeemer.getMonthStrings();
      int monthIndex = 1; //1 incase month not found
      for(int i = 0; i < allMonths.length; i++){
        if(allMonths[i].equals(spinner.getValue()))
          monthIndex = i - 1;// -1 to account for array size
      }
      //get places we can travel to
      String[] tickets = redeemer.redeemMiles(Integer.parseInt(inputMilesField.getText()),
                                              monthIndex);
      
      //if you dont have enough miles
      if(tickets.length == 0)
        ticketArea.append("You do not have enough miles");
      
      //Show tickets
      for(String ticket : tickets)
        ticketArea.append(ticket + "\n");
      
      //Show remaining miles
      remainingField.setText(Integer.toString(redeemer.getRemainingMiles()));
    }
    catch(NumberFormatException exception){
      ticketArea.append("Please input a numeric value");
    }
  }
  
  
  /************************************************************************
   * createAndShowGUI(): This method instanciates a JFrame and a   *
   * MileApp object. The MileApp object is then the pane set to the  *
   * JFrame.
   ************************************************************************/
  private static void createAndShowGUI() throws IOException{
    
    JFrame frame = new JFrame("Mile Redemption App");
    frame.setDefaultLookAndFeelDecorated(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    MileApp pane = new MileApp();
    
    pane.setOpaque(true);
    pane.setLayout(new FlowLayout(FlowLayout.LEADING));
    frame.setContentPane(pane);
    
    frame.setLocationRelativeTo(null);
    frame.pack();
    frame.setVisible(true);
    
  }
  
  /************************************************************************
   * main(String[] args): Main method. Only purpose is to call the     *
   * createAndShowGUI method and make a new runnable from it. if *
   * the document is not found in the called method, it comes back  *
   * to main and displays "file not found" message on the counsel.    *
   ************************************************************************/
  public static void main(String[] args) throws IOException{
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try{
        createAndShowGUI(); 
        }
        catch(IOException e){
          System.out.println("File not found");
        }
      }
    });
  }
  
}