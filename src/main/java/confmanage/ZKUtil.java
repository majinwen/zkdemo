package confmanage;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created by majinwen on 2016/9/22.
 */
public class ZKUtil {

    public static final String FTP_CONFIG_NODE_NAME = "/config/ftp";

    public static ZkClient getZkClient() {
        return new ZkClient("localhost:2181,localhost:2182,localhost:2183");
    }

}
