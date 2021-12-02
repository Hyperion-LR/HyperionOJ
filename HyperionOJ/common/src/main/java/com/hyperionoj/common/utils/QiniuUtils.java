package com.hyperionoj.common.utils;

import com.alibaba.fastjson.JSON;
import com.hyperionoj.common.config.QiniuProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Component
@Slf4j
public class QiniuUtils {

    @Resource
    private QiniuProperties qiniuProperties;

    public boolean upload(MultipartFile file, String fileName) {

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getAccessSecretKey());
            String upToken = auth.uploadToken(qiniuProperties.getBucketName());
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            log.info("上传情况: {}", putRet.toString());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public String getUrl() {
        return qiniuProperties.getUrl();
    }

}
