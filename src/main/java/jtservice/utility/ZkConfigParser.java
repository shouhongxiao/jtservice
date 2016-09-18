package jtservice.utility;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by shouh on 2016/9/14.
 */
public class ZkConfigParser {

    private static String appInstanceConfig;
    private static Logger logger = Logger.getLogger(ZkConfigParser.class);
    private static Properties config = new Properties();
    static {
        String propPath =  System.getProperty("app.instance.config");
        if(propPath == null){
            propPath = appInstanceConfig;
        }
        if(propPath == null){
            logger.error("未设置虚拟机启动参数'app.instance.config'!");
            throw new RuntimeException("未设置虚拟机启动参数'app.instance.config'!");
        }else{
            try {
                InputStream in = new FileInputStream(propPath+ File.separator+"config.properties");
                config.load(in);
                in.close();
            } catch (FileNotFoundException e) {
                logger.error("路劲"+propPath+"下未找到配置文件zkconfig.properties！",e);
            } catch (IOException e) {
                logger.error("读取配置文件zkconfig.properties失败！路劲"+propPath,e);
            }
        }
    }
    public static String getProperty(String key){
        return config.getProperty(key);
    }
    public static String getAppInstanceConfig() {
        return appInstanceConfig;
    }
    public static void setAppInstanceConfig(String appInstanceConfig) {
        ZkConfigParser.appInstanceConfig = appInstanceConfig;
    }
    public static String getAppConfigPaths(){
        String value = config.getProperty("app_config_paths");
        if(value == null || "".equals(value.trim()) || value.indexOf("app.instance.config") > 0){
            value = System.getProperty("app.instance.config");
        }
        if(value == null){
            logger.warn("zkconfig.properties配置文件缺少参数app_config_paths，或者参数配置不正确！");
        }
        return value;
    }
    public static Properties parserFile(String filePath){
        Properties prop = new Properties();
        try {
            InputStream in = new FileInputStream(filePath);
            prop.load(in);
            in.close();
            return prop;
        }catch (Exception e) {
            logger.error("读取配置文件失败！filePath="+filePath,e);
            return null;
        }
    }

    public static Properties parserData(byte[] data){
        Properties prop = new Properties();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(data, 0, data.length);
            InputStream in = new DataInputStream(input);
            prop.load(in);
            in.close();
            return prop;
        }catch (Exception e) {
            logger.error("数据读取失败！",e);
            return null;
        }
    }
    public static synchronized void  updateProperties(String profilepath, String keyname,String keyvalue) {
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream(profilepath);
            props.load(in);
            in.close();
            OutputStream fos = new FileOutputStream(profilepath);
            props.setProperty(keyname, keyvalue);
            props.store(fos, null);
            fos.close();

        } catch (Exception e) {
            logger.error("属性文件更新错误",e);
        }
    }
}
