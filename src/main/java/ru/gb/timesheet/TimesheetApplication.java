package ru.gb.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.timesheet.model.*;
import ru.gb.timesheet.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication {

  /**
   * spring-data - ...
   * spring-data-jdbc - зависимость, которая предоставляет удобные преднастроенные инструемнты
   * для работы c реляционными БД
   * spring-data-jpa - зависимость, которая предоставляет удобные преднастроенные инструемнты
   * для работы с JPA
   * spring-data-jpa
   * /
   * /
   * jpa   <------------- hibernate (ecliselink ...)
   * <p>
   * spring-data-mongo - завимость, которая предоставляет инструменты для работы с mongo
   * spring-data-aws
   */

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(TimesheetApplication.class, args);

    // 1. пользователь регистрируется на сайте и вводит свой пароль
    // 1.1 сервер считает хеш от пароля и сохраняет его в бд = hashInDatabase
    // 2. пользователь логинится на сайт и вводит свой пароль
    // 2.1 сервер считает хеш от пароля и сравнивает его с хешом в БД
    // hashFunc(password) -> hashInDatabase
    // hash -> password !!!
    // username1, password => hash(username1 + _ + password) == hash1
    // username2, password => hash(username2 + _ + password) == hash2

    // hashFunc(rawPassword) == hashInDatabase


    RoleRepository roleRepository =ctx.getBean(RoleRepository.class);
    Role adminRole = new Role();
//    adminRole.setId(1L);
    adminRole.setName("admin");
    roleRepository.save(adminRole);

    Role userRole = new Role();
//    userRole.setId(2L);
    userRole.setName("user");
    roleRepository.save(userRole);

    Role anonRole = new Role();
//    anonRole.setId(3L);
    anonRole.setName("rest");
    roleRepository.save(anonRole);

    UserRepository userRepository = ctx.getBean(UserRepository.class);

    User admin = new User();
    admin.setLogin("admin");
    admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin

    User user = new User();
    user.setLogin("user");
    user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user

    User anonymous = new User();
    anonymous.setLogin("anon");
    anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon

    admin = userRepository.save(admin);
    user = userRepository.save(user);
    anonymous = userRepository.save(anonymous);




    UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);

    UserRole adminAdminRole = new UserRole(); //роль админа
    adminAdminRole.setUserId(admin.getId());
    adminAdminRole.setRoleId(1L);
    userRoleRepository.save(adminAdminRole);

    UserRole adminUserRole = new UserRole(); //роль юзера
    adminUserRole.setUserId(user.getId());
    adminUserRole.setRoleId(2L);
    userRoleRepository.save(adminUserRole);

    UserRole userRestRole = new UserRole();  //роль рест
    userRestRole.setUserId(anonymous.getId());
    userRestRole.setRoleId(3L);
    userRoleRepository.save(userRestRole);

//		JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
//		jdbcTemplate.execute("delete from project");

    ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);

    for (int i = 1; i <= 5; i++) {
      Project project = new Project();
      project.setName("Project #" + i);
      projectRepo.save(project);
    }

    TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

    LocalDate createdAt = LocalDate.now();
    for (int i = 1; i <= 10; i++) {
      createdAt = createdAt.plusDays(1);

      Timesheet timesheet = new Timesheet();
      timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
      timesheet.setCreatedAt(createdAt);
      timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

      timesheetRepo.save(timesheet);
    }
  }

}
