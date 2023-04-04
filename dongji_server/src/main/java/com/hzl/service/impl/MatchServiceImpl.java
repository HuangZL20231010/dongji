package com.hzl.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hzl.dto.MatchDTO;
import com.hzl.dto.Result;
import com.hzl.entity.*;
import com.hzl.mapper.GroupMapper;
import com.hzl.mapper.MatchMapper;
import com.hzl.mapper.PartnerMapper;
import com.hzl.mapper.UserMapper;
import com.hzl.service.IMatchService;
import com.hzl.utils.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.hzl.utils.RedisConstants.*;

@Slf4j
@Service
public class MatchServiceImpl implements IMatchService {

    @Autowired
    MatchMapper matchMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    PartnerMapper partnerMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    UserMapper userMapper;

    //测试通过
    @Override
    public Result getInterest(String openid) {

        ArrayList<String> list=new ArrayList<>();
        Interest interest = matchMapper.getInterest(openid);
        if(interest.getRunning()==1){
            list.add("跑步");
        }
        if(interest.getSkipping()==1){
            list.add("跳绳");
        }
        if(interest.getFootball()==1){
            list.add("足球");
        }
        if(interest.getBasketball()==1){
            list.add("篮球");
        }
        if(interest.getTennis()==1){
            list.add("网球");
        }
        if(interest.getTableTennis()==1){
            list.add("乒乓球");
        }
        if(interest.getYoga()==1){
            list.add("瑜伽");
        }
        if(interest.getBodybuilding()==1){
            list.add("健身");
        }
        if(interest.getSwimming()==1){
            list.add("游泳");
        }


        return Result.ok(list);
    }

    //测试通过
    @Override
    public Result getInvitee(String openid) {
        ArrayList<MatchDTO> list = new ArrayList<>();
        //从interst表中查出前50条数据，根据算法，算出匹配相似度
        Interest self = matchMapper.getInterest(openid);
        ArrayList<Interest> othersList = matchMapper.selectTop100(openid);

        //转成数组的形式
        try {
            int[] selfArray = convertToInterestArray(self);

            int[][] othersArray = convertToInterestArray(othersList);

            // 传回最匹配的前两个人
            int[] mostSimilarUsers = findMostSimilarUsers(selfArray, othersArray);
            System.out.println(2);
            //将这两人的信息放到缓存中MatchDTO.id:openid;

            for (int i = 0; i < mostSimilarUsers.length; i++) {
                System.out.println(33);
                Interest interest = othersList.get(mostSimilarUsers[i]);
                //获取头像和昵称
                MatchDTO matchDTO = matchMapper.getAvatarAndNickname(interest.getOpenid());//////////
                //设置虚拟id
                String uuid = IdUtil.simpleUUID();
                matchDTO.setInviteeId(uuid);
                //前端需要的一个字段 查看是否已经发过邀请
                QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("inviterId",openid).eq("inviteeId",interest.getOpenid());
                Match match = matchMapper.selectOne(queryWrapper);
                if (match!=null)matchDTO.setSuccess(true);
                else matchDTO.setSuccess(false);

                //放入缓存
                String key= RedisConstants.MATCH_INVITEE_KEY+uuid;
                String jsonStr = JSONUtil.toJsonStr(interest);
                redisTemplate.opsForValue().set(key,jsonStr, MATCH_INVITEE_TTL, TimeUnit.MINUTES);

                //存到要返回前端的数组中
                list.add(matchDTO);
            }



        } catch (IllegalAccessException e) {
            return Result.fail("匹配过程出错了");
        }


        return Result.ok(list);
    }

    //测试通过
    @Override
    public Result getInviter(String openid) {
        ArrayList<MatchDTO> list = new ArrayList<>();
        //从缓存中查询list

        //查到了，直接取第一条返回，并将第一条删除，更新缓存（考虑list还有没有元素）

        //没找到，去数据库中查数据 根据是否完成
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("inviteeId",openid).eq("status",0);
        List<Match> matches = matchMapper.selectList(queryWrapper);

        //没有查到任何邀请信息
        if(matches.size()==0)return Result.ok(list);

        //将数据放到list中
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            String inviterOpenid = match.getInviterId();
            MatchDTO matchDTO = matchMapper.getAvatarAndNickname(inviterOpenid);//获得头像和昵称
            String uuid = IdUtil.simpleUUID();
            matchDTO.setInviterId(uuid);

            //存到redis中
            String key= RedisConstants.MATCH_INVITER_KEY+uuid;
            String jsonStr = JSONUtil.toJsonStr(match);
            redisTemplate.opsForValue().set(key,jsonStr, MATCH_INVITER_TTL, TimeUnit.MINUTES);

            //存到要返回前端的数组中
            list.add(matchDTO);
        }


        //取list的第一条返回
        return Result.ok(list.subList(0,1));
    }


    //测试通过
    @Override
    public Result sendInvite(MatchDTO matchDTO, String openid) {
        //从缓存中获得被邀请人的openid
        String key= MATCH_INVITEE_KEY+matchDTO.getInviteeId();
        String json = redisTemplate.opsForValue().get(key);
        //判断是否存在
        if(StrUtil.isBlank(json))return Result.fail("发送超时，请重新匹配");

        Interest interest = JSONUtil.toBean(json, Interest.class);
        String inviteeOpenid = interest.getOpenid();


        //先查询match是否有数据了，如果有数据了，就提示说已经发送过邀请了
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("inviterId",openid).eq("inviteeId",inviteeOpenid);
        Match one = matchMapper.selectOne(queryWrapper);
        if(BeanUtil.isNotEmpty(one)){
            return Result.fail("已经发送过邀请了");
        }


        System.out.println("interest："+interest);
        //写入数据库中 日期、状态
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Match match = new Match();
        match.setInviterId(openid);
        match.setInviteeId(inviteeOpenid);
        match.setStatus(0);//设置状态为 等待
        match.setSendDate(timestamp);

        int ok = matchMapper.insert(match);
        if(ok<1){
            log.error("匹配信息保存失败 失败");
        }


        return Result.ok("发送成功");

    }


    //测试通过
    @Override
    public Result acceptInvite(MatchDTO matchDTO, String openid) {
        //从缓存中获得邀请人的openid
        String key= RedisConstants.MATCH_INVITER_KEY+matchDTO.getInviterId();
        String json = redisTemplate.opsForValue().get(key);

        //判断是否存在
        if(StrUtil.isBlank(json))return Result.fail("匹配失败，请刷新界面");

        Match match = JSONUtil.toBean(json, Match.class);
        String inviterOpenid = match.getInviterId();

        //更改数据是否完成，状态，user表中的isMatch
        //将status改成1
        UpdateWrapper<Match> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("inviterId",inviterOpenid);
        updateWrapper.eq("inviteeId",openid);
        match.setStatus(1);
        int ok = matchMapper.update(match, updateWrapper);/////////////


        //matchMapper.updateOtherMatchInvalid(openid,inviterOpenid);
        // 将相关的匹配信息的status改成3 失效
        UpdateWrapper<Match> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.ne("status",1).and(i->i.eq("inviterId",inviterOpenid).or().eq("inviteeId",openid)
                .or().eq("inviterId",openid).or().eq("inviteeId",inviterOpenid));
        Match match2 = new Match();
        match2.setStatus(3);
        int updateOtherMatchInvalid = matchMapper.update(match2, updateWrapper2);/////////////
        log.info("让"+updateOtherMatchInvalid+"条match数据失效");

        //user表中的is_match改成1
        matchMapper.updateUserIsMatched(openid,inviterOpenid);/////////////

        //添加partner、group表信息
        //添加group，返回groupid
        Group group = new Group();
        group.setIntegral(0);
        group.setBeginDay(new Timestamp(System.currentTimeMillis()));
        group.setPosterNum(0);
        groupMapper.insert(group);/////////////
        Integer groupId = group.getGroupId();
        log.debug("groupId:"+groupId);

        //添加partner
        Partner partner1 = new Partner();
        partner1.setOpenid(openid);
        partner1.setPartnerId(inviterOpenid);
        partner1.setGroupId(groupId);
        partnerMapper.insert(partner1);/////////////

        Partner partner2 = new Partner();
        partner2.setOpenid(inviterOpenid);
        partner2.setPartnerId(openid);
        partner2.setGroupId(groupId);
        partnerMapper.insert(partner2);/////////////

        return Result.ok();
    }

    //测试通过
    @Override
    public Result refuseInvite(MatchDTO matchDTO, String openid) {
        //从缓存中获得邀请人的openid
        String key= RedisConstants.MATCH_INVITER_KEY+matchDTO.getInviterId();
        String json = redisTemplate.opsForValue().get(key);

        //判断是否存在
        if(StrUtil.isBlank(json))return Result.fail("拒绝失败，请刷新界面");

        Match match = JSONUtil.toBean(json, Match.class);
        String inviterOpenid = match.getInviterId();

        //更改数据 的状态status
        UpdateWrapper<Match> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("inviterId",inviterOpenid);
        updateWrapper.eq("inviteeId",openid);
        match.setStatus(2);//代表失败
        int ok = matchMapper.update(match, updateWrapper);
        if(ok<1){
            log.error("拒绝邀请 失败");
        }

        //让前端重新获取 收到的邀请

        return Result.ok("拒绝成功，请重新刷新界面");
    }

    @Override
    public Result getIsMatched(String openid) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("openid", openid));
        if(user==null)return Result.fail("用户不存在");
        Integer is_match = user.getIs_match();
        if(is_match==1)return Result.ok(true);
        else return Result.ok(false);
    }


    // 计算两个数组的相似度
    public static int calculateSimilarity(int[] arr1, int[] arr2) {
        int similarity = 0;

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] == arr2[i]) {
                similarity++;
            }
        }

        return similarity;
    }

    // 找到与给定用户相似度最高的两个人
    public static int[] findMostSimilarUsers(int[] userInterests, int[][] otherUsers) {
        int[][] similarities = new int[otherUsers.length][2];

        // 计算每个人与给定用户的相似度
        for (int i = 0; i < otherUsers.length; i++) {
            int similarity = calculateSimilarity(userInterests, otherUsers[i]);
            similarities[i][0] = i;
            similarities[i][1] = similarity;
        }

        // 按相似度从高到低对人进行排序
        Arrays.sort(similarities, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return b[1] - a[1];
            }
        });

        // 返回前两个人的索引
        int[] mostSimilarUsers = new int[2];
        mostSimilarUsers[0] = similarities[0][0];
        mostSimilarUsers[1] = similarities[1][0];

        return mostSimilarUsers;
    }

//上面的代码包含两个方法：
//
//        calculateSimilarity方法计算两个给定数组的相似度。它使用一个循环来比较两个数组中相同下标的元素是否相等，并返回相同元素的数量。
//        findMostSimilarUser方法找到与给定用户兴趣数组最相似的其他人。它使用一个循环来比较给定用户兴趣数组与每个其他人的兴趣数组，并记录相似度最高的人的索引。
//        你可以在你的代码中使用这两个方法来找到与给定用户最相似的人。以下是一个示例：

    public static int[] convertToInterestArray(Interest self) throws IllegalAccessException {
        List<Integer> interests = new ArrayList<>();

        // 使用反射获取类的所有字段
        Field[] fields = self.getClass().getDeclaredFields();

        // 遍历所有字段，如果字段值为1则添加1，否则添加0
        for (Field field : fields) {
            if (field.getType() == Long.class || field.getType() == long.class) {
                field.setAccessible(true);
                long value = field.getLong(self);
                if (value == 1) {
                    interests.add(1);
                } else {
                    interests.add(0);
                }
            }
        }

        // 将列表转换为数组
        int[] interestArray = new int[interests.size()];
        for (int i = 0; i < interests.size(); i++) {
            interestArray[i] = interests.get(i);
        }

        return interestArray;
    }


    public static int[][] convertToInterestArray(List<Interest> interests) throws IllegalAccessException {
        List<int[]> interestArrays = new ArrayList<>();

        // 遍历所有Interest对象
        for (Interest interest : interests) {
            List<Integer> interestList = new ArrayList<>();

            // 使用反射获取类的所有字段
            Field[] fields = interest.getClass().getDeclaredFields();

            // 遍历所有字段，如果字段值为1则添加1，否则添加0
            for (Field field : fields) {
                if (field.getType() == long.class) {
                    field.setAccessible(true);
                    long value = field.getLong(interest);
                    if (value == 1) {
                        interestList.add(1);
                    } else {
                        interestList.add(0);
                    }
                }
            }

            // 将列表转换为数组
            int[] interestArray = new int[interestList.size()];
            for (int i = 0; i < interestList.size(); i++) {
                interestArray[i] = interestList.get(i);
            }

            // 将数组添加到interestArrays列表中
            interestArrays.add(interestArray);
        }

        // 将interestArrays列表转换为二维数组
        int[][] interestArray = new int[interestArrays.size()][];
        for (int i = 0; i < interestArrays.size(); i++) {
            interestArray[i] = interestArrays.get(i);
        }

        return interestArray;
    }


}
