package com.smartContactManager.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String uploadImage(MultipartFile picture,String fileName);
    public String getUrlFromPublicId(String publicId);

}
