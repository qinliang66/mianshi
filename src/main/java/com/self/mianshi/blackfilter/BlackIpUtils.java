package com.self.mianshi.blackfilter;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

@Slf4j
public class BlackIpUtils {

    private static BitMapBloomFilter bitMapBloomFilter;

    public static boolean isBlackIp(String ip) {
        return bitMapBloomFilter.contains(ip);
    }

    public static void rebuildBlackIpList(String config) {
        if (StringUtils.isBlank(config)){
            config ="{}";
        }

        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(config, Map.class);
        List<String> blacklist = (List<String>)map.get("blackIpList");
        if (CollectionUtil.isNotEmpty(blacklist)){
            BitMapBloomFilter bloomFilter = new BitMapBloomFilter(958506);
            for (String ip : blacklist) {
                bloomFilter.add(ip);
            }
            bitMapBloomFilter = bloomFilter;
        }else {
            bitMapBloomFilter = new BitMapBloomFilter(958506);
        }

    }
}
