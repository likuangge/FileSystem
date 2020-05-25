package ring.central.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Redis公共工具类
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/26 00:19
 */
@Component
@Slf4j
public class RedisUtil {

    /**
     * RedisTemplate
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造redis工具类
     *
     * @param redisTemplate redis模板
     */
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 普通缓存放入
     *
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("错误信息:", e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("错误信息:", e);
            return false;
        }
    }
}
