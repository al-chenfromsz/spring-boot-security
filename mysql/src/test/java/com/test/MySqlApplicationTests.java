package com.test;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.test.entity.Department;
import com.test.entity.Role;
import com.test.entity.User;
import com.test.repository.DepartmentRepository;
import com.test.repository.RoleRepository;
import com.test.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySqlApplicationTests {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DepartmentRepository departmentRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Before
    public void initData(){
        userRepository.deleteAll();
        roleRepository.deleteAll();
        departmentRepository.deleteAll();

        Department department = new Department();
        department.setName("开发部");
        departmentRepository.save(department);
        Assert.notNull(department.getId(),null);

        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        Assert.notNull(role.getId(),null);

        User user = new User();
        user.setName("user");
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        user.setPassword(bpe.encode("user"));
        user.setCreatedate(new Date());
        user.setDepartment(department);
        userRepository.save(user);
        Assert.notNull(user.getId(),null);
    }

    @Test
    public void insertUserRoles(){
        User user = userRepository.findByName("user");
        Assert.notNull(user,null);

        List<Role> roles = roleRepository.findAll();
        Assert.notNull(roles,null);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
