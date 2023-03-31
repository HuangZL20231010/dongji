package com.hzl.utils;

import com.hzl.dto.Poster;
import net.fastposter.client.FastposterClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class FastPoster {

    @Autowired
    FastposterClient client;

    /**
     * 制作海报
     * @param poster
     * @return localUrl
     */
    public String generatePost(Poster poster){

        // 2.准备海报参数
        Map<String, Object> params = new HashMap<>();
        params.put("sport", poster.getSport());
        params.put("day", poster.getDay());
        params.put("yearAndMonth", poster.getYearAndMonth());

        params.put("head1",poster.getHead1());
        params.put("target", poster.getTarget() );
        params.put("head2", poster.getHead2());
        params.put("time", poster.getTime());
        params.put("message", poster.getMessage());

        // 3.生成海报并保存
        String localUrl = client.buildPoster("2f5cdfcdaac9f081").params(params).build().save();

        return localUrl;
    }

}
