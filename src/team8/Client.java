/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

import java.util.HashMap;

/**
 *
 * @author sam
 */
public class Client {
    private String name;
    private HashMap<String, Document> documents;
    private String gender;
    private String age;
    private String dependents;
    private String income;
    private String marital;
    private String employment;
    
    /**
     * Constructor
     * 
     * @param n Name of the client
     */
    public Client(String n) {
        this.name = n;
        this.gender = "Other";
        this.age = "Other";
        this.dependents = "None";
        this.income = "Below 20K";
        this.marital = "Single";
        this.employment = "Student";
        
        this.documents = new HashMap<>();
        this.fillDocumentList();
    }
    
    public Client(String n, String g, String a, String d, String i, String m, String e) {
        this.name = n;
        this.gender = g;
        this.age = a;
        this.dependents = d;
        this.income = i;
        this.marital = m;
        this.employment = e;
        
        this.documents = new HashMap<>();
        this.fillDocumentList();
    }
    
    private void fillDocumentList() {
        this.documents.put("ABC Life Insurance", new Document("ABC Life Insurance"));
        this.documents.put("DEF Mutual Fund", new Document("DEF Mutual Fund"));
        this.documents.put("TD Waterhouse", new Document("TD Waterhouse"));
        this.documents.put("Sammy Segfunds", new Document("Sammy Segfunds"));
        this.documents.put("Ahaha Critical Illness", new Document("Ahaha Critical Illness"));
        this.documents.put("Momentum Bonds", new Document("Momentum Bonds"));
    }
    
    /**
     * Returns the name of the client
     * @return Name of client
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Updates the name of the client
     * @param n Name to update to
     */
    public void setName(String n) {
        this.name = n;
    }
    
    /**
     * Returns all documents that this client has
     * 
     * @return list of documents that the client has
     */
    public HashMap<String, Document> getDocumentList() {
        return this.documents;
    }
    
    /**
     * Add new document to the client
     * 
     * @param d Document to be added
     */
    public void addDocument(Document d) {
        this.documents.put(d.getName(), d);
    }
    
    /**
     * Returns document with name n
     * @param n Name of document
     * @return Document with name of parameter
     */
    public Document getDocumentByName(String n) {
        return this.documents.get(n);
    }
}
