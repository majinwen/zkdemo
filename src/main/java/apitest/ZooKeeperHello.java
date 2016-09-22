package apitest;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by majinwen on 2016/9/22.
 */
public class ZooKeeperHello {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper("192.168.108.157:2181", 300000, new DemoWatcher());//连接zk server
        String node = "/app1";
        Stat stat = zk.exists(node, false);//检测/app1是否存在
        if (stat == null) {
            //创建节点
            String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }
        //获取节点的值
        byte[] b = zk.getData(node, false, stat);
        System.out.println(new String(b));
        zk.close();
    }

    static class DemoWatcher implements Watcher {

        public void process(WatchedEvent event) {
            System.out.println("----------->");
            System.out.println("path:" + event.getPath());
            System.out.println("type:" + event.getType());
            System.out.println("stat:" + event.getState());
            System.out.println("<-----------");
        }
    }

    @Test
    public void testZkClient() {
        //ZkClient zkClient = new ZkClient("192.168.108.157:2181,192.168.108.157:2182,192.168.108.157:2183");
        ZkClient zkClient = new ZkClient("localhost:2181,localhost:2182,localhost:2183");
        String node = "/app2";
        if (!zkClient.exists(node)) {
            zkClient.createPersistent(node, "hello zk");
        }
        System.out.println(zkClient.readData(node));
    }

}
