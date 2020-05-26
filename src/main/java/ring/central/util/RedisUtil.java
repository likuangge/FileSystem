package ring.central.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
     * 设置分布式锁
     *
     * @param key 键
     * @param value 值
     * @param time 时间(秒) 过期时间
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time){
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("错误信息:", e);
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key 键
     * @param time 时间(秒)
     * @return 指定缓存失效时间是否成功
     */
    public boolean expire(String key, long time) {
        try {
            if(time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("错误信息:", e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if(key != null && key.length>0) {
            if(key.length == 1) {
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
}
