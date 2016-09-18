package jtservice;

import jtservice.JettyServer.JettyServer;
import jtservice.zkRegister.ZkRegister;

import java.io.IOException;

/**
 *
 *
 */
public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ZkRegister zkRegister = new ZkRegister();
        zkRegister.build();
        JettyServer jettyJersey = new JettyServer();
        try {
            jettyJersey.startAndJoin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
