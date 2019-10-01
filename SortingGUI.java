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
 *  Purpose:  creates the gui and logic for our sorting  *
 *  App                                                                   *
 **********************************************************/
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.lang.Thread;

public class SortingGUI extends JPanel implements ActionListener{
  
  //panels
  private JPanel panel;
  private JPanel buttonPanel;
  private SortAnimationPanel animationArea;
  
  //swing components
  private JComboBox sortSelector;
  private JButton populateButton;
  private JButton sort;
  private String[] sorts = {"Heap Sort", "quicksort", "Shell sort"};
  
  //our array to be sorted
  private int[] sortArray = new int[400];
  
 /****************************************************************************
  * SortingGUI(): Constructor of the SortingGUI. It sets up two instances*
  * of the nested class SortingAnimationPanel and adds all the              *
  * jcomponents and listeners                                                             *
  ***************************************************************************/
  public SortingGUI(){
    
    panel = new JPanel();
    buttonPanel = new JPanel();
    
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
    animationArea = new SortAnimationPanel();
    panel.add(animationArea);
    panel.add(buttonPanel);//Done adding to panel
    
    
    populateButton = new JButton("Populate");
    populateButton.addActionListener(this);
    buttonPanel.add(populateButton);
    
    sort = new JButton("Sort");
    sort.setEnabled(false);
    sort.addActionListener(this);
    buttonPanel.add(sort);

    
    sortSelector = new JComboBox<String>(sorts);
    buttonPanel.add(sortSelector);
    
    add(panel);
  }
  
 /****************************************************************************
  * actionPreformed(ActionEvent e): written for the ActionListener int-  *
  * face. Listens for the populate and sort buttons.                               *
  ***************************************************************************/
  public void actionPerformed(ActionEvent e){
    
    if(e.getActionCommand().equals("Populate")){
      
      Random rand = new Random();
      
      for(int i = 0; i < sortArray.length; i++)
        sortArray[i] = rand.nextInt(300) + 1;
      
      repaint();
      
      sort.setEnabled(true);
    }
    
    if(e.getActionCommand().equals("Sort")){
      Thread animate = new Thread(animationArea);
      animate.start();
        
    }
  }
  
   /****************************************************************************
  * quickSort(): takes in the lower and upper bounds and sorts our array  *
  ***************************************************************************/
  //code from java2novice
  private void quickSort(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        
        int pivot = sortArray[lowerIndex+(higherIndex-lowerIndex)/2];
        
        while (i <= j) {
            
            while (sortArray[i] < pivot) {
                i++;
            }
            while (sortArray[j] > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
  
 /****************************************************************************
  * heapsort; buildMaxHeap; maxHeapify(): All methods are used for a   *
  * heap sort. Code from geeksforgeeks
  ***************************************************************************/
  private void heapSort(int[] array) {
    int length = array.length;
    
    buildMaxHeap(array, length);
    for(int i = length - 1; i > 0; i--) {
      exchangeNumbers(0, i);
      maxHeapify(array, 1, i);
    }
  }
  
  private void buildMaxHeap(int[] array, int heapSize) {
    if(array == null) {
      throw new NullPointerException("null");
    }
    if(array.length <=0 || heapSize <= 0) {
      throw new IllegalArgumentException("illegal");
    }
    if(heapSize > array.length) {
      heapSize = array.length;
    }
    
    for(int i = heapSize/2; i > 0; i--) {
      maxHeapify(array, i, heapSize);
    }
  }
  
  private void maxHeapify(int[] array, int index, int heapSize) {
    int l = index * 2;
    int r = l + 1;
    int largest;
    
    if(l <= heapSize && array[l - 1] > array[index - 1]) {
      largest = l;
    } else {
      largest = index;
    }
    
    if(r <= heapSize && array[r - 1] > array[largest - 1]) {
      largest = r;
    }
    
    if(largest != index) {
      exchangeNumbers(index - 1, largest -1);
      maxHeapify(array, largest, heapSize);
    }
  }
  
  /****************************************************************************
  * shellSort(int[] a): Uses the shellsort Method. Code from stackoverflow *
  ***************************************************************************/
  public void shellSort(int[] a){
   int j;
    for( int gap = a.length / 2; gap > 0; gap /= 2 )
    {
      for( int i = gap; i < a.length; i++ )
      {
         Integer tmp = a[ i ];
         for( j = i; j >= gap && tmp.compareTo( a[ j - gap ] ) < 0; j -= gap )
         {
           exchangeNumbers(j, j-gap);
         }
         a[ j ] = tmp;
      }
    }
  }
  
 /****************************************************************************
  * exchangeNumbers(int, int): This method is the swap call between all *
  * our sorting methods. This way we can call our thread sleep once and *
  * it will affect all of our sorts.                                                           *
  ***************************************************************************/
  private void exchangeNumbers(int i, int j) {
    try{
      int temp = sortArray[i];
      sortArray[i] = sortArray[j];
      sortArray[j] = temp;
      repaint();
      Thread.sleep(40);//in milliseconds
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      
    }
  }

  /****************************************************************************
  * SortAnimationPanel: This class creates a runnable object in which our*
  * animations will be done                                                                 *
  ***************************************************************************/
  private class SortAnimationPanel extends JPanel implements Runnable{
    
    
    public SortAnimationPanel(){
      this.setPreferredSize(new Dimension(400, 400));
    }
    
    public void run(){
      
      if(sortSelector.getSelectedItem().equals("quicksort"))
        quickSort(0, sortArray.length -1 );
      
      if(sortSelector.getSelectedItem().equals("Heap Sort"))
        heapSort(sortArray);
      
      if(sortSelector.getSelectedItem().equals("Shell sort"))
        shellSort(sortArray);
    }
    
 /****************************************************************************
  * paintComponent(Graphics g): Overwritten so we can call repaint      *
  ***************************************************************************/
    public void paintComponent(Graphics g)
    {
      g.setColor(Color.white);
      g.fillRect(0,0,400,400);
      if(sortArray != null)
      {
        
        for(int i = 0; i < sortArray.length; i++)
        {
          g.drawLine(i, 400, i, 400 - sortArray[i]);
          g.setColor(Color.blue);
        }//end for loop
      }
    }
    
  }//end nested class  
}