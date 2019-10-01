/**********************************************************
 *                                                                           *
 *  CSCI 470/680-E   Assignment 6     Summer 2018    *
 *                                                                           *
 *  Developer: Alex Danilushkin                                  *
 *                  z1809166                                             *
 *                                                                           *
 *  Section:  2                                                          *
 *                                                                           *
 *  Due Date/Time:  8/7/2018                                    *
 *                                                                           *
 *  Purpose:  Shows us the speed of different sorting  *
 *  methods                                                            *
 **********************************************************/


import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.FlowLayout;

public class SortingApp extends JPanel{
  
  private SortingGUI leftSorter;
  private SortingGUI rightSorter;
  
  
  /************************************************************************
  * SortingApp(): Constructor of the SortingApp class. It creates two  *
  * sortingGUI objects and adds them side by side                            *
  ************************************************************************/
  public SortingApp(){
    
    leftSorter = new SortingGUI();
    rightSorter = new SortingGUI();
    
    add(leftSorter);
    add(rightSorter);
    
  }
  
  /************************************************************************
  * createAndShowGUI:  creates the JFrame Object then sets the pane *
  * as the SortingApp()                                                                   *
  ************************************************************************/
  private static void createAndShowGUI() {
    
    JFrame frame = new JFrame("Sorting Animation");
    
    SortingApp pane = new SortingApp();
    frame.setDefaultLookAndFeelDecorated(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    pane.setLayout(new FlowLayout(FlowLayout.LEADING));
    frame.setContentPane(pane);
    
    frame.setLocationRelativeTo(null);
    frame.pack();
    frame.setVisible(true);
    
  }
  
  public static void main(String[] args){
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
  
  
  
}