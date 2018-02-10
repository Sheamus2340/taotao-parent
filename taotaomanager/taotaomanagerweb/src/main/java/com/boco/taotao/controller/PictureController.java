package com.boco.taotao.controller;

import com.boco.taotao.service.PictureService;
import com.boco.taotao.vo.JsonUtils;
import com.boco.taotao.vo.PictureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片的controller
 * Created by Sheamus on 2018/1/21.
 */
@Controller
@RequestMapping("/pic")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    /**
     * 这里返回JSON字符串是为了解决前端上传插件的不兼容性问题
     * @param multipartFile
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String uploadPicture(@RequestParam(value = "uploadFile") MultipartFile multipartFile) {
        PictureResult pictureResult = pictureService.uploadPictrue(multipartFile);
        String json = JsonUtils.objectToJson(pictureResult);
        return json;
    }

}
