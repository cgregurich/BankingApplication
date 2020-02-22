/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregurichfinalproject;

/**
 *
 * @author colin
 */


/*
enum of all months with corresponding values
Used to avoid having to validate user input of month on calculate interest window
And to make a cleaner looking UI
*/
public enum Month {
    January(1.0), February(2.0), March(3.0), April(4.0), May(5.0), June(6.0), July(7.0), August(8.0), 
    September(9.0), October(10.0), November(11.0), December(12.0);
    
    private double numVal;
    
    Month (double numVal){
        this.numVal = numVal;
    }
    
    public double getNumVal(){
        return numVal;
    }
    
}
