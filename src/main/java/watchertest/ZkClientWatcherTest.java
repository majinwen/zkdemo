package watchertest;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.concurrent.TimeUnit;

/**
 * Created by majinwen on 2016/9/22.
 */
public class ZkClientWatcherTest {

    private static final Logger logger = Logger.getLogger(ZkClientWatcherTest.class);
    private ZkClient zk;

    private String nodeName = "/myApp";

    @Before
    public void initTest() {

        zk = new ZkClient("localhost:2181,localhost:2182,localhost:2183");
    }

    @After
    public void dispose() {
        zk.close();
        logger.info("zkclient closed!");
    }

    @Test
    public void testListener() throws InterruptedException {
        //监听指定节点的数据变化

        zk.subscribeDataChanges(nodeName, new IZkDataListener() {

            public void handleDataChange(String s, Object o) throws Exception {
                logger.info("node data changed!");
                logger.info("node=>" + s);
                logger.info("data=>" + o);
                logger.info("--------------");
            }


            public void handleDataDeleted(String s) throws Exception {
                logger.info("node data deleted!");
                logger.info("s=>" + s);
                logger.info("--------------");

            }
        });

        logger.info("ready!");

        //junit测试时，防止线程退出
        while (true) {
            TimeUnit.SECONDS.sleep(5);
        }
    }


    @Test
    public void testUpdateConfig() throws InterruptedException {
        if (!zk.exists(nodeName)) {
            zk.createPersistent(nodeName);
        }
        zk.writeData(nodeName, "1");
        zk.writeData(nodeName, "2");
        zk.delete(nodeName);
        zk.delete(nodeName);//删除一个不存在的node，并不会报错
    }

}
