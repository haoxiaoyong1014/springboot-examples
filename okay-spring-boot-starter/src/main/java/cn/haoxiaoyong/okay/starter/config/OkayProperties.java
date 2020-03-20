package cn.haoxiaoyong.okay.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author haoxiaoyong on 2020/3/20 上午 11:04
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@ConfigurationProperties(prefix = "okay.config")
public class OkayProperties {

    private String platform;

    private String channel;

    private Boolean enable;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "OkayProperties{" +
                "platform='" + platform + '\'' +
                ", channel='" + channel + '\'' +
                ", enable=" + enable +
                '}';
    }
}
