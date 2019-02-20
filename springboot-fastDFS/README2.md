### 使用FastDFSClient上传文件

**引入依赖**

```xml
<dependency>
    <groupId>com.github.tobato</groupId>
    <artifactId>fastdfs-client</artifactId>
    <version>1.26.5</version>
</dependency>
```
此依赖仅支持springboot2x版本以上,如果你使用的是 springboot1x版本请使用以下依赖

```xml
<dependency>
    <groupId>com.github.tobato</groupId>
    <artifactId>fastdfs-client</artifactId>
    <version>1.25.4-RELEASE</version>
</dependency>

```

**FastDFSClient 工具类:**

```java
package cn.haoxiaoyong.fastdfs.util;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by haoxy on 2019/1/7.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component
public class FastDFSClient {


	@Autowired
	private FastFileStorageClient storageClient;

//	@Autowired
//	private AppConfig appConfig; // 项目参数配置

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @return 文件访问地址
	 * @throws IOException
	 */
	public String uploadFile(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
				FilenameUtils.getExtension(file.getOriginalFilename()), null);

		return storePath.getGroup() + "/" + storePath.getPath();
	}

	public String uploadFile(File file) throws IOException {
		StorePath storePath = storageClient.uploadFile(new FileInputStream(file), FileUtils.sizeOf(file),
				FilenameUtils.getExtension(file.getName()), null);

		return storePath.getGroup() + "/" + storePath.getPath();
	}
	
	public String uploadFile2(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
				FilenameUtils.getExtension(file.getOriginalFilename()), null);

		return storePath.getGroup() + "/" + storePath.getPath();
	}
	
	public String uploadQRCode(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
				"png", null);
		
		return storePath.getGroup() + "/" + storePath.getPath();
	}
	
	public String uploadFace(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
				"png", null);
		
		return storePath.getGroup() + "/" + storePath.getPath();
	}
	
	public String uploadBase64(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
				"png", null);
		
		return storePath.getGroup() + "/" + storePath.getPath();
	}
	
	/**
	 * 将一段字符串生成一个文件上传
	 * 
	 * @param content
	 *            文件内容
	 * @param fileExtension
	 * @return
	 */
	public String uploadFile(String content, String fileExtension) {
		byte[] buff = content.getBytes(Charset.forName("UTF-8"));
		ByteArrayInputStream stream = new ByteArrayInputStream(buff);
		StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
		return storePath.getGroup() + "/" + storePath.getPath();
	}

	// 封装图片完整URL地址
//	private String getResAccessUrl(StorePath storePath) {
//		String fileUrl = AppConstants.HTTP_PRODOCOL + appConfig.getResHost() + ":" + appConfig.getFdfsStoragePort()
//				+ "/" + storePath.getFullPath();
//		return fileUrl;
//	}

	/**
	 * 删除文件
	 * 
	 * @param fileUrl
	 *            文件访问地址
	 * @return
	 */
	public void deleteFile(String fileUrl) {
		if (StringUtils.isEmpty(fileUrl)) {
			return;
		}
		try {
			StorePath storePath = praseFromUrl(fileUrl);
			storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
		} catch (FdfsUnsupportStorePathException e) {
			e.getMessage();
		}
	}

	public static StorePath praseFromUrl(String filePath) {
		Validate.notNull(filePath, "解析文件路径不能为空", new Object[0]);
		int groupStartPos = getGroupStartPos(filePath);
		String groupAndPath = filePath.substring(groupStartPos);
		int pos = groupAndPath.indexOf("/");
		if (pos > 0 && pos != groupAndPath.length() - 1) {
			String group = groupAndPath.substring(0, pos);
			String path = groupAndPath.substring(pos + 1);
			return new StorePath(group, path);
		} else {
			throw new FdfsUnsupportStorePathException("解析文件路径错误,有效的路径样式为(group/path) 而当前解析路径为".concat(filePath));
		}
	}

	private static int getGroupStartPos(String filePath) {
		int pos = filePath.indexOf("group");
		if (pos == -1) {
			throw new FdfsUnsupportStorePathException("解析文件路径错误,被解析路径url没有group,当前解析路径为".concat(filePath));
		} else {
			return pos;
		}
	}
}

```

**测试使用方式:**

```java
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
```

**测试上传图片结果**

原始图片: 

<img src="http://www.haoxiaoyong.cn/group1/M00/00/00/rBAuwFxs1ACAaxYsAAGy54qn7gE929.png"/>

缩略图: 

<img src="http://www.haoxiaoyong.cn/group1/M00/00/00/rBAuwFxs1ACAaxYsAAGy54qn7gE929_150x150.png"/>


