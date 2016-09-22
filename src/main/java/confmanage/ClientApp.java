package confmanage;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by majinwen on 2016/9/22.
 */
public class ClientApp {

    Config config;


    private Config getFtpConfig() {
        if (config == null) {
            //首次获取时，连接zk取得配置，并监听配置变化
            ZkClient zk = ZKUtil.getZkClient();
            config = (Config) zk.readData(ZKUtil.FTP_CONFIG_NODE_NAME);
            System.out.println("config => " + config);

            zk.subscribeDataChanges(ZKUtil.FTP_CONFIG_NODE_NAME, new IZkDataListener() {


                public void handleDataChange(String s, Object o) throws Exception {
                    System.out.println("config is changed !");
                    System.out.println("node:" + s);
                    System.out.println("o:" + o.toString());
                    config = (Config) o;//重新加载Config
                }


                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("config is deleted !");
                    System.out.println("node:" + s);
                    config = null;
                }
            });
        }
        return config;

    }

    /**
     * 模拟程序运行
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {

        getFtpConfig();

        upload();

        download();
    }


    //循环获取数据库
    public void upload() throws InterruptedException {
        System.out.println("正在上传文件...");
        System.out.println(config);
        TimeUnit.SECONDS.sleep(10);
        System.out.println("文件上传完成...");
    }


    public void download() throws InterruptedException {
        System.out.println("正在下载文件...");
        System.out.println(config);
        TimeUnit.SECONDS.sleep(10);
        System.out.println("文件下载完成...");
    }

}
