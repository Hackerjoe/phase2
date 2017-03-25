package Airbnb;

import java.util.Scanner;

class Page {
    
    public Application app;
    public Scanner scanner;
    
    public Page(Application app) {
        this.app = app;
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
    }
    
    public void show() {
        
    }
    
    public void println(String message) {
        System.out.println(message);
    }
    
    public void print(String message) {
        System.out.print(message);
    }
    
    public String getInput() {
//        while(true) {
//            String response = scanner.next();
//            if(response != null && !response.isEmpty());
//               return response;
//        }
        return scanner.next();
    }

}
