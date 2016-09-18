package jtservice.utility;

import java.nio.charset.Charset;

/**
 * Created by shouh on 2016/9/14.
 */
public class ZkCconfigConstant {
    /**
     * 系统配置zk服务上的根节点
     */
    public static final String ROOT_NODE = "/zkconfig";
    /**
     * zk节点数据默认值
     */
    public static final String DEFAULT_NODE_DATA = "-1";
    /**
     * 系统默认字符编码
     */
    public static final Charset CHARSET = Charset.forName("UTF-8");
}
