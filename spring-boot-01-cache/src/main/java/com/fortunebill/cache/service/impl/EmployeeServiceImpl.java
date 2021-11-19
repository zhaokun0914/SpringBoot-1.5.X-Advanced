package com.fortunebill.cache.service.impl;

import com.fortunebill.cache.entities.Employee;
import com.fortunebill.cache.entities.EmployeeExample;
import com.fortunebill.cache.mapper.EmployeeMapper;
import com.fortunebill.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author Kevin
 * @date 2021年11月19日12:05
 */
@Service
@CacheConfig(cacheNames = {"emp"})// 抽取缓存的公共配置
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存，以后在想要相同的数据，直接从缓存中获取，不再调用方法
     *
     * CacheManager管理多个Cache组件，对缓存的真正CRUD操作是在具体的Cache组件中，每一个Cache组件有自己唯一一个名字
     * 几个属性：
     *   cacheNames/value：指定缓存的名字。名字可用于确定目标缓存（或多个缓存），匹配特定 bean 定义的限定符值或 bean 名称。
     *   key：缓存数据时使用的key。默认是使用方法参数的值，例如 key:value -> 1:返回的值
     *   keyGenerator：key的生成器，我们也可以自己指定key的生成器的组件name。PS:key 和 keyGenerator二选一
     *   cacheManager：指定从哪个缓存管理器中获取缓存。PS:cacheManager 和 cacheResolver二选一
     *   cacheResolver：缓存解析器
     *   condition：指定符合条件的情况下才缓存，还可以用SpEL表达式来进行判断，例如: "#id > 0"
     *   unless：指定符合条件的情况下不缓存
     *   sync：如果多个线程试图加载同一个键的值，则同步底层方法的调用。
     * 原理：
     *
     */
    @Override
    @Cacheable(/*cacheManager = "",*/
               /*cacheNames = {"emp"},*/
               /*key = "#root.methodName+'['+#id+']'",*/
               /*keyGenerator = "myKeyGenerator",*/
               /*condition = "#id>1",*/
               /*unless = "#id==2",*/
               sync = false)
    public Employee getEmployee(Integer id) {
        System.out.println("查询【" + id + "】号员工");
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    @CachePut(/*cacheNames = {"emp"},*/
              key = "#result.id")
    public Employee updateEmployee(Employee employee) {
        System.out.println("更新【" + employee.getId() + "】号员工");
        employeeMapper.updateByPrimaryKeySelective(employee);
        return employeeMapper.selectByPrimaryKey(employee.getId());
    }

    @Override
    @CacheEvict(/*cacheNames = {"emp"},*/
                /*key = "#id",*/
                allEntries = true)
    public String deleteEmployee(Integer id) {
        System.out.println("删除【" + id + "】号员工");
        int r = 1 / 0;
        return "success";
    }

    @Override
    @Caching(
            cacheable = {
                    @Cacheable(/*cacheNames = {"emp"},*/ key = "#lastName")
            },
            put = {
                    @CachePut(/*cacheNames = {"emp"},*/ key = "#result.id"),
                    @CachePut(/*cacheNames = {"emp"},*/ key = "#result.email")
            }
    )
    public Employee getEmployeeByLastName(String lastName) {
        System.out.println("查询【" + lastName + "】员工");
        EmployeeExample example = new EmployeeExample();
        example.createCriteria().andLastnameEqualTo(lastName);
        return employeeMapper.selectByExample(example).get(0);
    }

}
