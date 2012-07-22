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
        
        clientList.put("Emily Wang", new Client("Emily Wang"));
        clientList.put("Brad Brain", new Client("Brad Brain"));
        clientList.put("Bloody Marry", new Client("Bloody Marry"));
        clientList.put("Catherine Crazikid", new Client("Catherine Crazikid"));
        clientList.put("Samuel Beasto", new Client("Samuel Beasto"));
        clientList.put("Tee Bone", new Client("Tee Bone"));
        clientList.put("Crash Sait", new Client("Crash Sait"));
        clientList.put("Brownie Ploany", new Client("Brownie Ploany"));
        clientList.put("Tim Hortons", new Client("Tim Hortons"));
        clientList.put("Betty Barbie", new Client("Betty Barbie"));
        clientList.put("Henry Oh", new Client("Henry Oh"));
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
    
    /**
     * Adds a new client
     * @param nm the name to add
     */
    public static void addClient(String nm) {
        clientList.put(nm, new Client(nm));
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
