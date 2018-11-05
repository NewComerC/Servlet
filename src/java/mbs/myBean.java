/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mbs;

import java.io.Serializable;

/**
 * @author spring
 */
public class myBean extends Object implements Serializable {

    public static final String ISTATUS = "LogOut";

    private String status;
    private int id;
    private String name;

    public myBean() {
        status = ISTATUS;
        id=0;
        name = new String();
    }

    public String getStatus() {return status;}
    public void setStatus(String value) {status = value;}

    public int getId() {return id;}
    public void setId(int value) {id = value;}

    public String getName() {return name;}
    public void setName(String value) {name = value;}
    
    

}
