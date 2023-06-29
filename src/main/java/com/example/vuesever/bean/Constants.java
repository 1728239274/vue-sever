package com.example.vuesever.bean;

//常量类
public class Constants {

    public static final byte[] tokenKey = "sdafsadfdgsafdgs".getBytes();
    public static final String redisLoginKey = "login:";

    public static String qiNiuAccessKey = "Vz_KB0upYSvWmMQQ2w46S-tp5eqeBbSsZYf09AsW";
    public static String qiNiuSecretKey = "rbWe3LhCvAmJ_JaOvrGGxguSwJfKQWRuEQIf8nBo";
    public static String qiNiuBucket = "ht-itqiang";

    //未登录
    public static final int USER_NO_LOGIN_ERROR = 9000;
    //其他错误
    public static final int LOGIN_ERROR = 10000;
    //用户登录验证码错误
    public static final int LOGIN_CODE_ERROR = 9999;
    //用户名或密码错误
    public static final int LOGIN_USER_ERROR = 9998;
    //用户被锁定
    public static final int LOGIN_USER_LOCKED_ERROR = 9993;

    public static final int LOGIN_TOKEN_ERROR = 9992;
    public static final int LOGIN_OVERDUE_ERROR = 9991;
    //注册邮箱验证码失效分钟
    public static final int REGISTER_EMAIL_CODE_TIME = 5;
    //用户注册邮箱验证码错误
    public static final int REGISTER_CODE_ERROR = 9997;
    //注册用户名已存在
    public static final int REGISTER_USERNAME_HAS = 9996;
    //旧密码错误
    public static final int OLD_PASSWORD_ERROR =  9995;
    //修改失败
    public static final int UPDATE_USER_ERROR =  9995;
    //修改失败
    public static final int DELETE_USER_ERROR =  9994;
    //头像上传失败
    public static final int UPLOAD_USER_AVATAR_ERR =  9990;

    //酷猪前台
    //收藏验证错误
    public static final int KZ_COLLECTION_HAS_ERROR = 8001;
    //收藏添加失败
    public static final int KZ_COLLECTION_ADD_ERROR = 8002;
    //收藏删除失败
    public static final int KZ_COLLECTION_REMOVE_ERROR = 8003;


    public static final String qiNiuCDN = "http://rw6mddjhq.hn-bkt.clouddn.com/";
}
