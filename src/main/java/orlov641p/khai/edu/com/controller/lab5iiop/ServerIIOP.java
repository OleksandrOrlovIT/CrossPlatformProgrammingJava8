package orlov641p.khai.edu.com.controller.lab5iiop;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ServerIIOP {
    public static void main(String[] args) {
        try {
            ServerImpl serverImpl = new ServerImpl();

            Context initialNamingContext = new InitialContext();
            initialNamingContext.rebind("ServerIIOP", serverImpl);

            System.out.println("ServerIIOP is Ready...");
        } catch (Exception e) {
            System.err.println("ServerIIOP exception: " + e);
            e.printStackTrace();
        }
    }
}
