package com.study.web;

import io.lettuce.core.RedisClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class WebApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
	void contextLoads() {
	    redisTemplate.opsForValue().setIfAbsent("aaa", "aaaa", 10L, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("aaa"));
	}

    public static void main(String[] args) {
        String[] strings = split("abc,d\n \n.ghi", new String[]{"\n \n", "c"});
        System.out.println(Arrays.asList(strings));
        strings = split("abc---def::ghi::jkl:mno-", new String[]{"--", "ghi", ":", "-", "rst"});
        System.out.println(strings.length);
        System.out.println(Arrays.asList(strings));
    }

    public static String[] split(String str, String[] options) {
	    String token = "\n \n";
	    if (options.length == 0) {
	        return new String[]{str};
        }
        String option1 = options[0];
        boolean sameFlg = false;
	    for (String option : options) {
	        if (token.equals(option)) {
                sameFlg = true;
	        }
        }
        if (sameFlg) {
            if (!token.equals(option1)) {
                str = str.replaceAll(token, option1);
            } else {
                token = "\n";
            }
        }
	    for (String option : options) {
	        str = str.replaceAll(option, token);
        }
        return str.split(token);
    }
}
