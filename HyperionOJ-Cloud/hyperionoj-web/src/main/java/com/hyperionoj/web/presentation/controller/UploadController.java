package com.hyperionoj.web.presentation.controller;

import com.hyperionoj.web.infrastructure.utils.QiniuUtils;
import com.hyperionoj.web.presentation.vo.ErrorCode;
import com.hyperionoj.web.presentation.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Hyperion
 * @date 2021/12/18
 */
@RequestMapping("/upload")
@RestController
public class UploadController {

    @Resource
    private QiniuUtils qiniuUtils;

    @PostMapping("/avatar")
    public Result uploadImg(@RequestParam("image") MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(multipartFile, fileName);
        if (upload) {
            return Result.success(qiniuUtils.getUrl() + '/' + fileName);
        }
        return Result.fail(ErrorCode.SYSTEM_ERROR);
    }

}
