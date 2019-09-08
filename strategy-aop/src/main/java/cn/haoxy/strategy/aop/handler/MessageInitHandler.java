package cn.haoxy.strategy.aop.handler;

import cn.haoxy.strategy.aop.utils.MapCacheUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:15
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Component
@Slf4j
public class MessageInitHandler implements CommandLineRunner {

    /**
     * 文件名
     */
    private static String fileName = "test.json";

    /**
     * 包名
     */

    //private static String packageVo = "cn.haoxy.strategy.aop.strategys";

    /**
     * 初始化 Map
     */
    private Map<String, String> initMap = Maps.newHashMap();

    @Override
    public void run(String... args) throws Exception {

        JSONObject jsonObject = loadingJSONFile();
        log.info("json Data are as follows:{}", jsonObject);
        Gson gson = new Gson();
        initMap = gson.fromJson(jsonObject.toJSONString(), Map.class);
        MapCacheUtils.mapCaheInit = initMap;

        //加载指定包下所有的类
       /* log.info("start run initAllDataSourceType()..... ");
        initAllDataSourceType();
        log.info("mapCaheInit size: " + initMap.size());*/
    }

    private JSONObject loadingJSONFile() {

        log.info("开始加载resources/test.json");

        Enumeration<URL> resources;
        JSONObject jsonObject = new JSONObject();
        try {
            resources = getClassLoader().getResources(fileName);
        } catch (IOException e) {
            log.warn("getJsonResource fail {}", fileName, e);
            return jsonObject;
        }
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            try {
                String json = Resources.toString(url, Charsets.UTF_8);
                jsonObject.putAll(JSON.parseObject(json)); // 有多个的时候，后面的覆盖前面的
            } catch (IOException e) {
                log.warn("addJsonFile fail url:{}", url, e);
            }
        }
        return jsonObject;
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return MessageInitHandler.class.getClassLoader();
    }

}

/*    private void initAllDataSourceType() {

        getClasses(packageVo);
        MapCacheUtils.mapCaheClass = classzzMap;
        log.info("mapCaheClass size: " + classzzMap.size());
    }*/

/*    private void getClasses(String packageVo) {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        URL url = this.getClass().getClassLoader().getResource(packageVo.replace(".", "/"));
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            try {
                //获取包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                //以文件的形式扫描整个包下的文件,并添加到集合中
                findAndAddClassesInPackageByFile(packageVo, filePath, classes);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }*/

   /* private void findAndAddClassesInPackageByFile(String packageVo, String filePath, List<Class<?>> classes) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在就获取包下的所有文件,包括目录
        File[] dirFiles = dir.listFiles(file -> (file.isDirectory()) || (file.getName().endsWith(".class")));

        for (File file : dirFiles) {
            //如果是目录则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageVo + "." + file.getName(), file.getAbsolutePath(), classes);
            } else {
                String className = packageVo + "." + file.getName().replaceAll(".class", "");
                try {
                    Class<?> clazz = Class.forName(className);
                    //判断这个类上是否存在指定的注解
                    if (clazz.isAnnotationPresent(MessageType.class)) {
                        //如果存在,得到此注解的value值
                        MessageType messageType = clazz.getAnnotation(MessageType.class);
                        //放入容器
                        classzzMap.put(messageType.value(), clazz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/



