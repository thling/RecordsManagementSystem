/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

/**
 *
 * @author sam
 */
public class Document {
    private String name;
    private String dateAdded;
    
    public Document(String n) {
        this.name = n;
        this.dateAdded = "Jul 19, 2012";
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDateAdded() {
        return this.dateAdded;
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public void setDateAdded(String d) {
        this.dateAdded = d;
    }
}
