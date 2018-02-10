package com.boco.taotao.service;

import com.boco.taotao.vo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Sheamus on 2018/1/21.
 */
public interface PictureService {
    PictureResult uploadPictrue(MultipartFile multipartFile);
}
