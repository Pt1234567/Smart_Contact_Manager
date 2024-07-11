package com.smartContactManager.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.smartContactManager.helpers.AppConstants;
import com.smartContactManager.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

   private Cloudinary cloudinary;

   public ImageServiceImpl(Cloudinary cloudinary){
      this.cloudinary=cloudinary;
   }

    @Override
    public String uploadImage(MultipartFile picture,String fileName) {
        //code likna hai jo image ko upload kar rha ho

        //hamlog image ko cloudinary pe save karenge
        

        try {
            byte[] data=new byte[picture.getInputStream().available()];

            picture.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                  "public_id",fileName
            ));

            return this.getUrlFromPublicId(fileName);

      } catch (IOException e) {
            return null;
      }
    }

@Override
public String getUrlFromPublicId(String publicId) {
      return cloudinary
       .url()
       .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_CROP)
       ).generate(publicId);
}

}
