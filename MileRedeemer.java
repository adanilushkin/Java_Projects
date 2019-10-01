import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;//used for sorting
import java.util.Comparator;//used for sorting
import java.io.File;
import java.io.*;


public class MileRedeemer{
  
  
  private int remainingMiles;
  private Destination[] destinationArray;
  
  
  //Constructor
  public MileRedeemer(){
    
    remainingMiles = 0;
    
  }
  
  
   /************************************************************************
   * readDestinations(): reads the file and saves destinations into an   *
   *                             array of destinations                                    *
   ************************************************************************/
  public void readDestinations(String fileName) throws IOException{
    
    File inFile = new File(fileName);
    Scanner readScan = new Scanner(inFile);
    
    //Attributes
    String destination;
    int miles;
    int cheapMiles;
    int upgradeMiles;
    int startMonth;
    int endMonth;
    
    ArrayList<Destination> destinationList = new ArrayList<Destination>();
    
    //Preparing to read file
    String inLine; //For reading the line
    String [] inLineParse = new String[4]; //will hold the split string
    Destination dest;
    
    //Reading file
    while(readScan.hasNext()){
      
      inLine = readScan.nextLine();
      inLineParse = inLine.split(";", 5);//Split up string
      String monthParse = inLineParse[4];
      String [] monthSplit = monthParse.split("-", -2);
      
      destination = inLineParse[0];
      miles = Integer.parseInt(inLineParse[1]); //converting string arg to int
      cheapMiles = Integer.parseInt((inLineParse[2]));
      upgradeMiles = Integer.parseInt((inLineParse[3]));
      startMonth = Integer.parseInt(monthSplit[0]);
      endMonth = Integer.parseInt(monthSplit[1]);
      
      dest = new Destination (destination, miles, cheapMiles, upgradeMiles,
                              startMonth, endMonth);
      
      destinationList.add(dest);
    }//End while
    
    //Array list to Array of Destination objects
    destinationArray = (Destination[]) destinationList.toArray(new
                                                                 Destination[destinationList.size()]);
    
    readScan.close();
  }
  
  
   /************************************************************************
   * getCityNames(): returns a String array filled with the destination *
   *                         names                                                            *
   ************************************************************************/
  public String[] getCityNames(){
    
    String [] destStringArray = new String[destinationArray.length];
    
    for(int i = 0; i < destStringArray.length; i++){
      //Pulls the names of each destination 
      destStringArray[i] = destinationArray[i].getDestination(); 
    }
    
    return destStringArray;
  }
  
  
  
   /************************************************************************
   * redeemMiles(): returns a String array of all the places one may    *
   *                       go using the miles the client has accumulated     *
   ************************************************************************/
  public String[] redeemMiles(int miles, int month){
    
    //Before we do anything, lets sort the array and initilize miles
    Arrays.sort(destinationArray);
    remainingMiles = miles;
    int startTrip, endTrip; //will be changed based on cheap file months for destinations
    
    ArrayList<Destination> canVisitList = new ArrayList<Destination>(); //hold places we can go to
    
    //Checks for cheap month miles first
    for(int i = 0; i < destinationArray.length; i++){
      
      startTrip = destinationArray[i].getStartMonth();
      endTrip = destinationArray[i].getEndMonth();
     
      if(month >= startTrip && month <= endTrip){
          if(remainingMiles >= destinationArray[i].getCheapMiles()){
          
          canVisitList.add(destinationArray[i]);
          
          remainingMiles -= destinationArray[i].getCheapMiles();
        }
      }
    }
    
    //Regular miles
    for(int i = 0; i < destinationArray.length; i++){
      if(canVisitList.contains(destinationArray[i]) == false){ //only check places we havent visited
        if(remainingMiles >= destinationArray[i].getMiles()){ //check if we can fly there
          canVisitList.add(destinationArray[i]); //if we can add to list
          remainingMiles -= destinationArray[i].getMiles();
        }
      }
    }
    
    String[] places = new String[canVisitList.size()];
    
    for(int i = 0; i < canVisitList.size(); i++){    
      places[i] = "A trip to " + (canVisitList.get(i)).getDestination() + " in Economy Class";
    }
    
    for(int i = 0; i < canVisitList.size(); i++){
      if( (canVisitList.get(i)).getUpgradeMiles() < remainingMiles){
        places[i] = "A trip to " + (canVisitList.get(i)).getDestination() + " in First Class";
        remainingMiles -= (canVisitList.get(i)).getUpgradeMiles();
      }
    }
    
    return places;
  }
  
  /************************************************************************
   * getMonthStrings(): returns an array of Months ie Jan, Feb,...       *
   * Used by the JSpinner and ListSelectionListener when the value is *
   * changed.                                                                                *
   ************************************************************************/
  protected String[] getMonthStrings() 
  {
    String[] months = new java.text.DateFormatSymbols().getShortMonths();
    
    int lastIndex = months.length - 1;
    
    if (months[lastIndex] == null || months[lastIndex].length() <= 0)  
    { 
      String[] monthStrings = new String[lastIndex];
      System.arraycopy(months, 0, monthStrings, 0, lastIndex);
      return monthStrings;
    }
    else 
    { 
      return months;
    }
  }
  

   /************************************************************************
   * getRemainingMiles(): returns remaining miles for the client         *
   ************************************************************************/
  public int getRemainingMiles(){
    
    return remainingMiles;
    
  }
  
  
  /************************************************************************
   * getDestObj(int index): returns the Destination object found at    *
   * the specified index in the destinationArray. Used to populate     *
   * fields in the bottom right JPanel when a destination is selected  *
   * from the list                                                                           *
   ************************************************************************/
  public Destination getDestObj(int index){
   
    return destinationArray[index];
  }
}