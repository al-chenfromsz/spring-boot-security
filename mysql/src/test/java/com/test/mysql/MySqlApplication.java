package com.test.mysql;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import com.test.mysql.entity.Department;
import com.test.mysql.entity.Role;
import com.test.mysql.entity.User;
import com.test.mysql.repository.DepartmentRepository;
import com.test.mysql.repository.RoleRepository;
import com.test.mysql.repository.UserRepository;

@SpringBootApplication
public class MySqlApplication implements CommandLineRunner{
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DepartmentRepository departmentRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(MySqlApplication.class, args);
    }

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

    public void insertUserRoles(){
        User user = userRepository.findByName("user");
        Assert.notNull(user,null);

        List<Role> roles = roleRepository.findAll();
        Assert.notNull(roles,null);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
        insertUserRoles();
    }
}
