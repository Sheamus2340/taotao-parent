package com.boco.taotao.service.impl;

import com.boco.taotao.service.PictureService;
import com.boco.taotao.util.FtpUtil;
import com.boco.taotao.util.IDUtils;
import com.boco.taotao.vo.PictureResult;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 图片上传和下载的service
 * Created by Sheamus on 2018/1/21.
 */
@Service
public class PictureServiceImpl implements PictureService {
    private Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    @Value(value="#{propertyConfigure['ftp.host']}")
    private String host;
    @Value(value="#{propertyConfigure['ftp.port']}")
    private Integer port;
    @Value(value="#{propertyConfigure['ftp.username']}")
    private String username;
    @Value(value="#{propertyConfigure['ftp.password']}")
    private String password;
    @Value(value="#{propertyConfigure['ftp.basePth']}")
    private String basePath;
    @Value(value="#{propertyConfigure['ftp.imageBaseUrl']}")
    private String imageBaseUrl;

    @Override
    public PictureResult uploadPictrue(MultipartFile multipartFile) {
        PictureResult pictureResult = new PictureResult();
        if(multipartFile == null) {
            logger.error("前端给的参数有问题");
            pictureResult.setError(1);
            pictureResult.setMessage("前端给的参数有问题");
            return pictureResult;
        }
        //FTP服务保存的图片的名字要重命名
        //获取原来图片的名字
        String originalFilename = multipartFile.getOriginalFilename();
        //根据策略生成FTP的文件名
        String filename = IDUtils.genImageName();
        //拼接图片的后缀
        filename = filename + originalFilename.substring(originalFilename.lastIndexOf("."));
        //创建图片FTP保存的路径，格式为当前时间的日期“/yyyy/MM/dd”
        String filePath = new DateTime().toString("/yyyy/MM/dd");

        //获取上传的图片流
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("前端给的参数有问题");
            pictureResult.setError(1);
            pictureResult.setMessage("前端给的参数有问题");
            return pictureResult;
        }
        boolean uploadFile = true;
        try {
            uploadFile = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, filename, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("配置有问题");
            pictureResult.setError(1);
            pictureResult.setMessage("配置有问题");
            return pictureResult;
        }

        //判断是否上传成功
        if(uploadFile) {
            pictureResult.setError(0);
            pictureResult.setUrl(imageBaseUrl + filePath + "/" + filename);
        } else {
            logger.error("服务器端有问题");
            pictureResult.setError(1);
            pictureResult.setMessage("服务器端有问题");
        }
        return pictureResult;
    }
}
