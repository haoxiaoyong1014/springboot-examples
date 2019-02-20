package cn.haoxiaoyong.fastdfs.util;

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by haoxy on 2019/2/20.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class FiletoMuit {

    public static MultipartFile file2Muit(String pdfPath) throws IOException {
        File file = new File(pdfPath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        return multipartFile;
    }
}
