package jtservice.zkRegister;

import jtservice.utility.ZkCconfigConstant;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by shouh on 2016/9/14.
 */
public class ActiveKeyValueStore  extends ConnectionWatcher {
    public static final Charset CHARSET = ZkCconfigConstant.CHARSET;

    public void write(String path, String value) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zk.setData(path, value.getBytes(CHARSET), -1);
        }
    }

    public void writeEphemeral(String path) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            zk.create(path, ZkCconfigConstant.DEFAULT_NODE_DATA.getBytes(ZkCconfigConstant.CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }
    }

    public String read(String path, Watcher watch) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, watch, null/**stat*/);
        return new String(data, 0, data.length, CHARSET);

    }

    public void writeByte(String path, byte[] data) throws KeeperException, InterruptedException {
        String t = new String(data, 0, data.length, CHARSET);
        logger.info(t + "abc");
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zk.setData(path, data, -1);
        }
    }

    public byte[] readByte(String path, Watcher watch) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, watch, null);
        return data;
    }

    public List<String> getChildren(final String path, Watcher watcher) throws KeeperException, InterruptedException {
        return zk.getChildren(path, watcher);
    }

    public void register(Watcher watcher) {
        zk.register(watcher);
    }

    public byte[] readByteWithoutWatcher(String path) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, false, null);
        return data;
    }

    public String readStringWithoutWatcher(String path) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, false, null);
        return new String(data, 0, data.length, CHARSET);
    }

    public void createPathWithOutACL(String zkpath) throws KeeperException, InterruptedException {
        zk.create(zkpath, ZkCconfigConstant.DEFAULT_NODE_DATA.getBytes(ZkCconfigConstant.CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void updatePathWithOutACL(String zkpath, String value) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(zkpath, false);
        if (stat == null) {
            zk.create(zkpath, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zk.setData(zkpath, value.getBytes(CHARSET), -1);
        }
    }

    /**
     * 按照path目录递归初始化节点，默认数据-1；
     *
     * @param zkpath
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void initZkPath(String zkpath) throws KeeperException, InterruptedException {
        if (zkpath != null && !"".equals(zkpath.trim())) {
            String[] pathArray = zkpath.split("/");
            if (pathArray != null && pathArray.length > 0) {
                String tmpPath = "";
                for (String p : pathArray) {
                    if (p != null && !"".equals(p.trim())) {
                        tmpPath += "/" + p;
                        Stat stat = zk.exists(tmpPath, false);
                        if (stat == null) {
                            this.createPathWithOutACL(tmpPath);
                        }
                    }

                }
            }
        }
    }
}
