package confmanage;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created by majinwen on 2016/9/22.
 */
public class ConfigManager {
    private Config config;



    public void loadConfig() {
        //TODO...
        config = new Config(21, "192.168.1.1", "test", "123456");
    }


    /**
     * 模拟更新DB中的配置
     *
     * @param port
     * @param host
     * @param user
     * @param password
     */
    public void updateFtpConfigToDB(int port, String host, String user, String password) {
        if (config == null) {
            config = new Config();
        }
        config.setPort(port);
        config.setHost(host);
        config.setUser(user);
        config.setPassword(password);
    }


    /**
     * 将配置同步到ZK
     */
    public void syncFtpConfigToZk() {
        ZkClient zk = ZKUtil.getZkClient();
        if (!zk.exists(ZKUtil.FTP_CONFIG_NODE_NAME)) {
            zk.createPersistent(ZKUtil.FTP_CONFIG_NODE_NAME, true);
        }
        zk.writeData(ZKUtil.FTP_CONFIG_NODE_NAME, config);
        zk.close();
    }



}
