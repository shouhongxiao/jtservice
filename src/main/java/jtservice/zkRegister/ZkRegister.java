package jtservice.zkRegister;

import jtservice.utility.ZkConfigParser;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * Created by shouh on 2016/9/14.
 */
public class ZkRegister extends RegistryService  {
    protected ActiveKeyValueStore store;

    private String getHostAddr() throws UnknownHostException {
        String host = System.getenv("host");
        if(host==null)
        {
            host=  InetAddress.getLocalHost().getHostAddress().toString();
        }
        return host;
    }

    @Override
    protected void init() {
        this.store = new ActiveKeyValueStore();
        try {
            this.store.connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int port = Integer.parseInt(ZkConfigParser.getProperty("port"));

        String host = null;
        try {
            host =getHostAddr() + ":" + "" + port;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Set<String> sets = getKeys();
        for (String key : sets) {

            try {
                this.store.initZkPath(key+ host);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (key.lastIndexOf("/") == -1)
                key += "/";
            try {
                this.store.writeEphemeral(key+host);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
