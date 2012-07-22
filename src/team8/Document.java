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
    private String type;
    private String provider;
    private String notes;
    
    public Document (String n, String d, String t, String p) {
        this.name = n;
        this.dateAdded = d;
        this.type = t;
        this.provider = p;
        this.notes = "";
    }
    
    public Document (String n, String d, String t, String p, String nt) {
        this.name = n;
        this.dateAdded = d;
        this.type = t;
        this.provider = p;
        this.notes = nt;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDateAdded() {
        return this.dateAdded;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getProvider() {
        return this.provider;
    }
    
    public String getNotes() {
        return this.notes;
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public void setDateAdded(String d) {
        this.dateAdded = d;
    }
    
    public void setType(String t) {
        this.type = t;
    }
    
    public void setProvider(String p) {
        this.provider = p;
    }
    
    public void setNotes(String nt) {
        this.notes = nt;
    }
}
