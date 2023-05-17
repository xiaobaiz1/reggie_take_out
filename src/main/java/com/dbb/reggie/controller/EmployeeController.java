package com.dbb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbb.reggie.common.R;
import com.dbb.reggie.pojo.Employee;
import com.dbb.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 实现登录功能
     * 1.密码md5加密
     * 2.根据id查询数据
     * 3.如果查询失败，则返回失败信息
     * 4.查询成功比对密码
     * 5.查看员工状态是否禁止
     * 6.登录成功存放到session域中
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1.密码md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据id查询数据
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        //getOne(queryWrapper) 获取唯一字段
        Employee emp = employeeService.getOne(queryWrapper);
        //3.查询失败返回失败信息
        if (emp == null) {
            return R.error("登录失败");
        }
        //4.查询成功比对密码
        if (!(emp.getPassword().equals(password))) {
            return R.error("密码错误");
        }
        //5.查看员工状态是否禁止
        if (emp.getStatus() == 0) {
            return R.error("账号锁定");
        }
        //6.登录成功存放到session域中
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //1.清理Session中的用户id
        request.getSession().removeAttribute("employee");
        //2.返回结果
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> add( @RequestBody Employee employee) {
        //1.设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //2.创建时间和跟新时间
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //3.设置创建人id和跟新人id
        //Long employeeId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(employeeId);
        //employee.setUpdateUser(employeeId);

        employeeService.save(employee);
        return R.success("保存成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page={}", page);
        //构造分页器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //判断name
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件(根据降序Desc)
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //根据page和条件查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 修改账号状态
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee){
       log.info("修改开始");
        //Long empById = (Long)request.getSession().getAttribute("employee");
        //设置修改时间
        //employee.setUpdateTime(LocalDateTime.now());
        //设置修改人
        //employee.setUpdateUser(empById);
        //修改状态
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id")Long id){
        log.info("查询employee");
        Employee emp = employeeService.getById(id);
        if(emp!=null){
            return R.success(emp);
        }
        return R.error("查询失败");
    }


}
