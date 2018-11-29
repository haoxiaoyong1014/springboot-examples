package cn.haoxy.redis.example.bean;

import org.springframework.stereotype.Component;

/**
 * Created by haoxy on 2018/11/29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component
public class RedisConnector {

    private String hostName;

    private Integer port;

    private String password;

    private Integer dbIndex;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(Integer dbIndex) {
        this.dbIndex = dbIndex;
    }

    @Override
    public String toString() {
        return "RedisConnector{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                ", dbIndex=" + dbIndex +
                '}';
    }
}
