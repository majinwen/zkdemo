package confmanage;

import org.junit.Test;

/**
 * Created by majinwen on 2016/9/22.
 */
public class ConfigTest {

    @Test
    public void testZkConfig() throws InterruptedException {

        ConfigManager cfgManager = new ConfigManager();
        ClientApp clientApp = new ClientApp();

        //模拟【配置管理中心】初始化时，从db加载配置初始参数
        cfgManager.loadConfig();
        //然后将配置同步到ZK
        cfgManager.syncFtpConfigToZk();

        //模拟客户端程序运行
        clientApp.run();

        //模拟配置修改
        cfgManager.updateFtpConfigToDB(23, "10.6.12.34", "newUser", "newPwd");
        cfgManager.syncFtpConfigToZk();

        //模拟客户端自动感知配置变化
        clientApp.run();

    }

}