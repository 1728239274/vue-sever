package com.example.vuesever.utils;


import com.example.vuesever.bean.Constants;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


/**
 * 七牛云工具类
 */
public class QiNiuUtils {


    //上传文件
    public static void upload2QiNiu(byte[] bytes, String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(Constants.qiNiuAccessKey, Constants.qiNiuSecretKey);
        String upToken = auth.uploadToken(Constants.qiNiuBucket);

        try {
            Response response = uploadManager.put(bytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    //删除文件
    public static void deleteFileFromQiNiu(String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        String key = fileName;
        Auth auth = Auth.create(Constants.qiNiuAccessKey, Constants.qiNiuSecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(Constants.qiNiuBucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
