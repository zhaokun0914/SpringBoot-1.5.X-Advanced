package com.fortunebill.cache;

import com.fortunebill.cache.entities.Employee;
import com.fortunebill.cache.entities.EmployeeExample;
import com.fortunebill.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot01CacheApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> myRedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        EmployeeExample example = new EmployeeExample();
        List<Employee> employees = employeeMapper.selectByExample(example);
        System.out.println(employees);
    }

    /**
     * Redis常见的五大数据类型
     * String(字符串), List(列表), Set(集合), Hash(散列), ZSet(有序集合)
     * stringRedisTemplate.opsForValue()[字符串];
     * stringRedisTemplate.opsForList()[列表];
     * stringRedisTemplate.opsForSet()[集合];
     * stringRedisTemplate.opsForHash()[散列];
     * stringRedisTemplate.opsForZSet()[有序集合];
     *
     */
    @Test
    public void testRedis() {
        stringRedisTemplate.opsForValue().append("msg", "Hello");
        stringRedisTemplate.opsForValue().append("msg", " World");
        System.out.println(stringRedisTemplate.opsForValue().get("msg"));
    }

    @Test
    public void testRedisSaveObject() {
        Employee employee = employeeMapper.selectByPrimaryKey(8);
        myRedisTemplate.opsForValue().set("emp_01", employee);
//        System.out.println(myRedisTemplate.opsForValue().get("emp_01"));
    }

}
