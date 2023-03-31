package com.hzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzl.config.Exception.LabelException;
import com.hzl.dto.PersonalityLabelDTO;
import com.hzl.dto.Result;
import com.hzl.entity.PersonalityLabel;
import com.hzl.mapper.PersonalityLabelMapper;
import com.hzl.service.IPersonalityLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PersonalityLabelServiceImpl extends ServiceImpl<PersonalityLabelMapper, PersonalityLabel> implements IPersonalityLabelService {

    @Autowired
    PersonalityLabelMapper personalityLabelMapper;

    @Override
    public Result savePersonalityLabel(String openid, List<PersonalityLabelDTO> labels) {
        //处理数据，将数组中的数都拿出来
        PersonalityLabel personalityLabel = new PersonalityLabel();
        personalityLabel.setLabel8(null);
        System.out.println(personalityLabel.getLabel8());
        System.out.println(personalityLabel.getLabel9());
        try {
            int length=labels.size();
            if (length==0) throw new LabelException("一共有0个标签要上传",0);
            personalityLabel.setLabel1(labels.get(0).getName());
            if (length==1) throw new LabelException("一共有1个标签要上传",1);
            personalityLabel.setLabel2(labels.get(1).getName());
            if (length==2) throw new LabelException("一共有2个标签要上传",2);
            personalityLabel.setLabel3(labels.get(2).getName());
            if (length==3) throw new LabelException("一共有3个标签要上传",3);
            personalityLabel.setLabel4(labels.get(3).getName());
            if (length==4) throw new LabelException("一共有4个标签要上传",4);
            personalityLabel.setLabel5(labels.get(4).getName());
            if (length==5) throw new LabelException("一共有5个标签要上传",5);
            personalityLabel.setLabel6(labels.get(5).getName());
            if (length==6) throw new LabelException("一共有6个标签要上传",6);
            personalityLabel.setLabel7(labels.get(6).getName());
            if (length==7) throw new LabelException("一共有7个标签要上传",7);
            personalityLabel.setLabel8(labels.get(7).getName());
            if (length==8) throw new LabelException("一共有8个标签要上传",8);
            personalityLabel.setLabel9(labels.get(8).getName());
            if (length==9) throw new LabelException("一共有9个标签要上传",9);
        }catch (LabelException e){
            log.info(e.getMessage());

        }finally {
            //更新数据
            UpdateWrapper<PersonalityLabel> updateWrapper = new UpdateWrapper<>();;
            updateWrapper.eq("openid",openid);
            int isOk = personalityLabelMapper.update(personalityLabel, updateWrapper);
            if(isOk==0){
                log.error("个人标签信息存入失败");
                return Result.fail("个人标签信息存入失败");
            }
            return Result.ok();
        }




    }
}
