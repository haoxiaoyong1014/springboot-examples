package cn.haoxiaoyong.fastdfs;

import cn.haoxiaoyong.fastdfs.util.FastDFSClient;
import cn.haoxiaoyong.fastdfs.util.FiletoMuit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by haoxy on 2019/2/20.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
public class TestFastDFSClient {

    @Autowired
    private FastDFSClient fastDFSClient;


    private String httpUrl = "http://www.haoxiaoyong.cn/";

    //上传File类型
    @Test
    public void uploadFileTest() throws IOException {
        String url = fastDFSClient.uploadFile(new File("/Users/haoxiaoyong/Desktop/server.xml"));
        System.out.println(url); // 打印地址: group1/M00/00/00/rBAuwFxsuv2ATek5AAAdV0CoZsM414.xml
        //然后拼接上域名:
        System.out.println(httpUrl + url);

    }

    //上传MultipartFile类型
    @Test
    public void uploadMulTest() throws IOException {
        //将文件转换成MultipartFile类型
        MultipartFile file = FiletoMuit.file2Muit("/Users/haoxiaoyong/Desktop/server.xml");
        String url = fastDFSClient.uploadFile(file);
        System.out.println(url);// group1/M00/00/00/rBAuwFxsvOOAT9DFAAAdV0CoZsM460.xml
    }


    //上传图片同时生成缩略图
    @Test
    public void uploadImgTest() throws IOException {

        MultipartFile file = FiletoMuit.file2Muit("/Users/haoxiaoyong/Desktop/meinv.png");
        String url = fastDFSClient.uploadFace(file);
        //拼接上域名,大图片
        System.out.println(httpUrl + url);
        //在FastDFS上传的时候,会自动生成一个缩略图
        String[] fileNameList = url.split("\\.");
        String fileName = fileNameList[0];
        String ext = fileNameList[1];
        String picSmallUrl = fileName + "_150x150." + ext;//缩略图地址
        //拼接上域名-缩略图地址
        System.out.println(httpUrl + picSmallUrl);
    }

}
