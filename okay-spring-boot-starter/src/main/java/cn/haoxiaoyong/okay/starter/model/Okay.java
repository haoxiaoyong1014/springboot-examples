package cn.haoxiaoyong.okay.starter.model;

/**
 * @author haoxiaoyong on 2020/3/20 下午 1:27
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
public class Okay {

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
        return "Okay{" +
                "platform='" + platform + '\'' +
                ", channel='" + channel + '\'' +
                ", enable=" + enable +
                '}';
    }
}
