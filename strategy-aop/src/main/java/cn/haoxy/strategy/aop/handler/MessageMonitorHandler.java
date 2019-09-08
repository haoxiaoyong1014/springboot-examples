package cn.haoxy.strategy.aop.handler;

import cn.haoxy.strategy.aop.pojo.AnalysisMessage;
import cn.haoxy.strategy.aop.pojo.AnalysisRole;
import cn.haoxy.strategy.aop.pojo.AnalysisUser;
import cn.haoxy.strategy.aop.service.AnalysisMessageService;
import cn.haoxy.strategy.aop.service.AnalysisRoleService;
import cn.haoxy.strategy.aop.service.MessageStrategyService;
import cn.haoxy.strategy.aop.strategys.StrategyBase;
import cn.haoxy.strategy.aop.utils.MapCacheUtils;
import cn.haoxy.strategy.aop.utils.ReturnUtils;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:16
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Aspect
@Component
public class MessageMonitorHandler {

    private Logger logger = LoggerFactory.getLogger(MessageMonitorHandler.class);

    @Autowired
    private AnalysisMessageService messageService;

    @Autowired
    private MessageStrategyService messageStrategyService;


    @Pointcut("@annotation(cn.haoxy.strategy.aop.annotation.MessageLog)")
    public void checkMessageHandler() {

    }

    @Around("checkMessageHandler()")
    public void doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("start run doAround.....");
        Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
        //返回客户端结果
        ReturnUtils.infoReturn(getHttpServletResponse(),obj);
        //判断调用是否成功
        //......省略
        //如果调用成功
        processOutPutObj(proceedingJoinPoint);
    }

    private void processOutPutObj(ProceedingJoinPoint proceedingJoinPoint) {

        Object[] args = proceedingJoinPoint.getArgs();
        //得到HttpServletRequest
        HttpServletRequest request = getHttpServletRequest();
        //得到请求url
        String url = request.getServletPath();
        //根据url从MapCacheUtils.mapCaheInit中取出操作title,
        // 这里是从test.json文件中读取的，当然也可以配置在数据库中
        String operatorLog = MapCacheUtils.mapCaheInit.get(url);
        //根据url取出对应的策略类,这里的url也就是和策略类上@Component注解的value值
        StrategyBase messageChild = messageStrategyService.run(url);
        //拿到策略类执行相应的策略方法
        String content = messageChild.run(args);
        AnalysisMessage analysisMessage = new AnalysisMessage();
        analysisMessage.setId(1L);
        analysisMessage.setTitle(operatorLog);
        analysisMessage.setContent(content);
        //在这里模拟存库
        messageService.insert(analysisMessage);

        logger.info("  end  run doAround....." + content);

    }


    /**
     * 获取 HttpServletRequest
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取 HttpServletResponse
     */
    private HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getResponse();
    }

}
