package com.hzl.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.OSSClient;
import com.hzl.service.IOssService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
public class OssServiceImpl implements IOssService{

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.dir.prefix}")
    private String dirPrefix;

    //1、创建oss客户端连接
    @Autowired
    private OSSClient ossClient;


    /**
     * 用户头像上传
     * @param file：头像
     * @return 图片Url
     */
    @Override
    public String upload(MultipartFile file) {

        String url = null;
        try {
            //2、获取文件输入流
            InputStream inputStream = file.getInputStream();
            //3、获取原始文件名
            String originFileName = file.getOriginalFilename();
            //4、防止文件重名
//            String uuidFileName =RandomUti.randomUUID().toString(true) + originFileName;
            String uuidFileName = IdUtil.simpleUUID() + originFileName;
            //4.1、精确到日
            String dateTime = DateTime.now().toString("yyyy-MM-dd");
            //5、拼接文件名
            String realFileName = dateTime + uuidFileName ;
            //拼接dir根目录
            String dirFileName = dirPrefix + realFileName;

            //6、创建oss请求，传入三个参数
            ossClient.putObject(bucketName,dirFileName, inputStream);

            //7、拼接图片url路径，方便后续入库
            url = "https://" + bucketName+ "." + endpoint + "/" + dirFileName;

        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

}
