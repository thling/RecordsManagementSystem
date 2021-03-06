/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
/**
 *
 * @author sam
 */
public class FakeDB {
    private static HashMap<String, Client> clientList;
    private static FakeDB __instance = null;
    private static HashMap<String, String> dateMap = new HashMap<>();
    
    private FakeDB() {
        clientList = new HashMap<>();
        
        clientList.put("Emily Wang", new Client("Emily Wang", "Jul 27, 2009", "Female", "20 to 40", "20K to 40K", "Married"));
        clientList.put("Brad Pitt", new Client("Brad Pitt", "May 02, 2007", "Male", "40 to 60", "40K to 60K", "Widowed"));
        clientList.put("James Bond", new Client("James Bond", "Dec 12, 2011", "Male", "20 to 40", "60K to 80K", "Single"));
        clientList.put("Angelina Jolie", new Client("Angelina Jolie", "Jan 01, 2000", "Female", "40 to 60", "Above 80K", "Divorced"));
        clientList.put("Sam Ling", new Client("Sam Ling", "Nov 04, 2007", "Male", "20 to 40", "Below 20K", "Single"));
        clientList.put("Frank Wang", new Client("Frank Wang", "Jul 27, 2009", "Male", "20 to 40", "20K to 40K", "Married"));
        clientList.put("Marina Zhao", new Client("Marina Zhao", "Jul 20, 2012", "Female", "20 to 40", "20K to 40K", "Single"));
        clientList.put("Michael Vallis", new Client("Michael Vallis", "Oct 07, 2005", "Male", "20 to 40", "20K to 40K", "Single"));
        clientList.put("Tim Hortons", new Client("Tim Hortons", "Jul 23, 2002", "Male", "40 to 60", "20K to 40K", "Single"));
        clientList.put("Natalie Portman", new Client("Natalie Portman", "Jun 19, 2003", "Female", "20 to 40", "60K to 80K", "Married"));
        clientList.put("George Clooney", new Client("George Clooney", "Aug 08, 2011", "Male", "20 to 40", "Above 80K", "Single"));
    }
    
    /**
     * Init
     */
    public static void initDb() {
        if (__instance == null) {
            __instance = new FakeDB();
            
            dateMap = new HashMap<>();
            dateMap.put("Jan", "01");
            dateMap.put("Feb", "02");
            dateMap.put("Mar", "03");
            dateMap.put("Apr", "04");
            dateMap.put("May", "05");
            dateMap.put("Jun", "06");
            dateMap.put("Jul", "07");
            dateMap.put("Aug", "08");
            dateMap.put("Sep", "09");
            dateMap.put("Oct", "10");
            dateMap.put("Nov", "11");
            dateMap.put("Dec", "12");
        }
    }
    
    /**
     * Returns the first of clients
     * 
     * @return Client object in the first list
     */
    public static Client popClient() {
        Object[] keys = (clientList.keySet().toArray());
        
        return clientList.get((String)keys[0]);
    }
    
    public static Client[] getClientList() {
        Client[] c = clientList.values().toArray(new Client[0]);
        
        Arrays.sort(c, new Comparator(){ 
            @Override
            public int compare(Object a, Object b) {
                return ((Client)a).getName().compareTo(((Client)b).getName());
            }
        });
        
        return c;
    }
    
    public static Client getClient(String name) {
        return clientList.get(name);
    }
    
    /**
     * Adds a new client
     * @param nm the name to add
     */
    public static void addClient(String nm) {
        clientList.put(nm, new Client(nm));
    }
    
    /**
     * Adds a new client
     * @param c the client object to be added
     */
    public static void addClient(Client c) {
        clientList.put(c.getName(),c);
    }
    
    /**
     * Updates a client
     * @param name
     * @param c 
     */
    public static void updateClient(String name, Client c) {
        if (clientList.containsKey(name)) {
            Document[] d = getDocumentsByClient(name, "Name");
            for (int i = 0; i < d.length; i++) {
                c.addDocument(d[i]);
            }
            
            clientList.remove(name);
            addClient(c);
        }
    }
    
    /**
     * Returns sorted array
     * 
     * @param name name of client
     * @param orderBy either "Name" or "Date Added"
     * @return 
     */
    public static Document[] getDocumentsByClient(String name, String orderBy) {
        Client c = clientList.get(name);
        
        if (c == null) {
            return null;
        }
        
        if (!orderBy.equals("Name") && !orderBy.equals("Date Added")) {
            orderBy = "Name";
        }
        
        HashMap<String, Document> dlist = c.getDocumentList();
        Document[] documents = dlist.values().toArray(new Document[0]);
        
        final String order = orderBy;
        
        // Custom comparator object to sort with
        Arrays.sort(documents, new Comparator(){
            @Override
            public int compare(Object a, Object b) {
                Document d1 = (Document)a;
                Document d2 = (Document)b;
                
                if (order.equals("Name")) {
                    return d1.getName().compareToIgnoreCase(d2.getName());
                } else {                    
                    String d1Date = d1.getDateAdded();
                    String d2Date = d2.getDateAdded();
                    
                    int year = d2Date.substring(8, 12).compareTo(d1Date.substring(8, 12));
                    
                    if (year != 0) {
                        return year;
                    } else {
                        d1Date.replace(d1Date.substring(0,3), dateMap.get(d1Date.substring(0,3)));
                        d2Date.replace(d2Date.substring(0,3), dateMap.get(d2Date.substring(0,3)));
                        
                        int month = d2Date.substring(0, 3).compareTo(d1Date.substring(0, 3));
                        
                        if (month != 0) {
                            return month;
                        } else {
                            int day = d2Date.substring(4, 6).compareTo(d1Date.substring(4, 6));
                            
                            if (day != 0) {
                                return day;
                            }
                        }
                    }
                    
                    int res = d1Date.compareTo(d2Date);
                    if (res == 0) {
                        return d1.getName().compareToIgnoreCase(d2.getName());
                    } else {
                        return res;
                    }
                }
            }
        });
        
        return documents;
    }
    
    /**
     * Returns a document of a client
     * 
     * @param clientName name of client
     * @param documentName name of document
     * @return 
     */
    public static Document getDocumentByClient(String clientName, String documentName) {
        Client c = clientList.get(clientName);
        
        if (c == null) {
            return null;
        }
        
        return c.getDocumentByName(documentName);
    }
    
    /**
     * Adds new document to db
     * 
     * @param client the client that owns this document
     * @param doc the document to be added
     */
    public static void addDocument(String client, Document doc) {
        Client c = clientList.get(client);
        
        if (c == null ) {
            c = new Client(client);
            c.addDocument(doc);
            
            clientList.put(client, c);
        } else {
            c.addDocument(doc);
        }
    }
}
