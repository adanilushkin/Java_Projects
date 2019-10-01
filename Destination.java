


public class Destination implements Comparable{
  
  /*******************************************
   *                 Attributes                        *
   *******************************************/
  private String destination;
  private int miles;
  private int cheapMiles;
  private int upgradeMiles;
  private int startMonth;
  private int endMonth;
  //*******************************************
  
  
   /*******************************************
   *                 Constructor                      *
   *******************************************/
  public Destination(String des, int mile, int cmile, int upmile, int stmon, int enmon){
    
    setDestination(des);
    setMiles(mile);
    setCheapMiles(cmile);
    setUpgrade(upmile);
    setStartMonth(stmon);
    setEndMonth(enmon);
    
  }
 //******************************************** 
  
  
   /*******************************************
   *                 Setters                             *
   *******************************************/
  public void setDestination(String place){
    destination = place;
  }
  
  public void setMiles(int distance){
    miles = distance;
  }
  
  public void setCheapMiles(int cmiles){
    cheapMiles = cmiles;
  }
  
  public void setUpgrade(int umiles){
   upgradeMiles = umiles; 
  }
  
  public void setStartMonth(int sMonth){
    startMonth = sMonth;
  }
  
  public void setEndMonth(int eMonth){
    endMonth = eMonth;
  }
  //*******************************************
  
  
   /*******************************************
   *                 Getters                             *
   *******************************************/
  
  public String getDestination(){
    return destination; 
  }
  
  public int getMiles(){
    return miles;
  }
  
  public int getCheapMiles(){
    return cheapMiles;
  }
  
  public int getUpgradeMiles(){
    return upgradeMiles;
  }
  
  public int getStartMonth(){
    return startMonth; 
  }
  
  public int getEndMonth(){
    return endMonth;
  }
  
  //*******************************************
  
   /**************************************************************************
   * compareTo(): an override for compare to used by the sort method  *
   **************************************************************************/
  @Override
  public int compareTo(Object o){
    Destination i = (Destination) o;
    
    if(this.getMiles() > i.getMiles())
      return -1;
    else
      return 0;  
  }
}