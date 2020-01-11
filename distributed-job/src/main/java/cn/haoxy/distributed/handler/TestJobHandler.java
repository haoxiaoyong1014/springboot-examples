package cn.haoxy.distributed.handler;

import cn.haoxy.distributed.service.InService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by haoxiaoyong on 2019/12/26 下午 1:22
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */

@Component
public class TestJobHandler {

    @Autowired
    private InService inService;

    @XxlJob("jobHandler")
    public ReturnT<String> execute(String param) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }

        inService.xxl();
       return ReturnT.SUCCESS;
    }
}
