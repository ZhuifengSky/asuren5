package cn.service.common.util;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pc-zw
 * @describe
 * @date 2018/5/21
 */
public class SqlSessionFactoryBuilder {
    Logger logger = LoggerFactory.getLogger(SqlSessionFactoryBuilder.class);
    private static String MASTER = "master";
    private static String SLAVE = "slave";
    private static String SHARDINGMASTER = "shardingMaster";
    private static String SHARDINGSLAVE = "shardingSlave";
    private static String SHARDING="sharding";
    private HashMap<String, SqlSessionFactoryBean> sqlSessionFactoryMap;


    /**
     * 只写的库
     *
     * @return
     */
    public  SqlSessionFactoryBean getMaster() {
        return sqlSessionFactoryMap.get(MASTER);
    }
    /**
     * 只写的库
     *
     * @return
     */
    public  SqlSessionFactoryBean getShardingMaster() {

        return sqlSessionFactoryMap.get(SHARDINGMASTER);
    }

    public  SqlSessionFactoryBean getSqlSessionFactory(String key) {
        return sqlSessionFactoryMap.get(key);
    }

    /**
     * 只读的库
     *
     * @return
     */


    public  SqlSessionFactoryBean getShardingSlave() {

        return sqlSessionFactoryMap.get(SHARDINGSLAVE);
    }
    public  SqlSessionFactoryBean getSlave() {
        return sqlSessionFactoryMap.get(SLAVE);
    }


    //读写分表
    public  SqlSessionFactoryBean getSharding() {

        return sqlSessionFactoryMap.get(SHARDING);
    }

    public Map<String, SqlSessionFactoryBean> getSqlSessionFactoryMap() {
        return sqlSessionFactoryMap;
    }

    public void setSqlSessionFactoryMap(HashMap<String, SqlSessionFactoryBean> sqlSessionFactoryMap) {
        this.sqlSessionFactoryMap = sqlSessionFactoryMap;
    }

    public void  init(){
        logger.info("ssssssaaaaaa");
        System.out.println("初始化数据连接Bean");
    }
}