package com.hzl.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hzl.dto.Result;
import com.hzl.entity.Photos;
import com.hzl.mapper.PhotosMapper;
import com.hzl.service.IPhotosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhotosServiceImpl implements IPhotosService {

    @Autowired
    PhotosMapper photosMapper;

    @Override
    public Result uploadPhoto(String openid, String photoUrl, int number) {

        UpdateWrapper<Photos> wrapper=new UpdateWrapper<>();
        wrapper.eq("openid",openid);
        Photos photo = new Photos();
        photo.setOpenid(openid);
        if(number==1){
            photo.setPhoto1url(photoUrl);
        }else if(number==2){
            photo.setPhoto2url(photoUrl);
        }

        int idOK = photosMapper.update(photo, wrapper);
        if(idOK<1){
            log.info("上传照片失败");
            return Result.fail("上传照片失败");
        }
        log.info("上传照片成功");

        return Result.ok();
    }
}
