/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author sam
 */
public class Client {
    private String name;
    private HashMap<String, Document> documents;
    private String since;
    private String gender;
    private String age;
    private String dependents;
    private String income;
    private String marital;
    private String employment;
    
    public Client() {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date = new Date();
        
        this.since = dateFormat.format(date);
        this.documents = new HashMap<>();
    }
    
    /**
     * Constructor
     * 
     * @param n Name of the client
     */
    public Client(String n) {
        this.name = n;
        this.since = "Jun 20, 2000";
        this.gender = "Other";
        this.age = "Other";
        this.dependents = "No";
        this.income = "Below 20K";
        this.marital = "Single";
        this.employment = "Unemployed";
        
        this.documents = new HashMap<>();
        this.fillDocumentList();
    }
    
    /**
     * Initialize a client with all attributes
     * 
     * @param n name
     * @param s since
     * @param g gender
     * @param a age
     * @param d dependents
     * @param i annual income
     * @param m marital status
     * @param e employment
     * @param fill whether to fill this client with some preset data or not
     */
    public Client(String n, String s, String g, String a, String d, String i, String m, String e, boolean fill) {
        this.name = n;
        this.since = s;
        this.gender = g;
        this.age = a;
        this.dependents = d;
        this.income = i;
        this.marital = m;
        this.employment = e;
        
        this.documents = new HashMap<>();
        if (fill) {
            this.fillDocumentList();
        }
    }
    
    /**
     * Initialize using the variables
     * @param n name
     * @param s since
     * @param g gender
     * @param a age
     * @param i income
     * @param m marital status
     */
    public Client(String n, String s, String g, String a, String i, String m) {
        this.name = n;
        this.since = s;
        this.gender = g;
        this.age = a;
        this.income = i;
        this.marital = m;
        this.dependents = "No";
        this.employment = "Unemployed";
        
        this.documents = new HashMap<>();
        this.fillDocumentList();
    }
    
    private void fillDocumentList() {
        this.documents.put("ABC Life Insurance", 
                new Document("ABC Life Insurance", "Jun 19, 2011", "Insurance", "ABC Corp."));
        
        this.documents.put("DEF Mutual Fund", 
                new Document("DEF Mutual Fund", "Nov 01, 2010", "Investment", "DEF Group"));
        
        this.documents.put("TD Waterhouse",
                new Document("TD Fund", "Aug 13, 2012", "Investment", "TD Waterhouse"));
        
        this.documents.put("Sammy Segfunds",
                new Document("Sammy Segfunds", "Feb 14, 2011", "Investment", "Sammy Beast Corp."));
        
        this.documents.put("Canadian Critical Illness", 
                new Document("Canadian Critical Illness", "Apr 30, 2008", "Insurance", "CPP Foundation"));
        
        this.documents.put("Momentum Bonds",
                new Document("Momentum Bonds", "Oct 23, 2008", "Investment", "Momentum Canada, Inc."));
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public String getGender() {
        return this.gender;
    }
    
    public String getSince() {
        return this.since;
    }
    
    public String getAge() {
        return this.age;
    }
    
    public String getMarital() {
        return this.marital;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public void setSince(String since) {
        this.since = since;
    }
    
    public String getDependents() {
        return this.dependents;
    }
    
    public String getIncome() {
        return this.income;
    }
    
    public String getEmployment() {
        return this.employment;
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
