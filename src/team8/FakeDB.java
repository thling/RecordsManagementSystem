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
public class FakeDB {
    private static HashMap<String, Client> clientList;
    private static FakeDB __instance = null;
    
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
    
    public static void initDb() {
        if (__instance == null) {
            __instance = new FakeDB();
        }
    }
    
    public static void addClient(String nm) {
        clientList.put(nm, new Client(nm));
    }
    
    public static HashMap<String, Document> getDocumentsByClient(String name) {
        Client c = clientList.get(name);
        
        if (c == null) {
            return null;
        }
        
        return c.getDocumentList();
    }
    
    public static Document getDocumentByClient(String clientName, String documentName) {
        Client c = clientList.get(clientName);
        
        if (c == null) {
            return null;
        }
        
        return c.getDocumentByName(documentName);
    }
    
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
