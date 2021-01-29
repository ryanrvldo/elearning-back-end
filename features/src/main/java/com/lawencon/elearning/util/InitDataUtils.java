package com.lawencon.elearning.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.UserService;

/**
 * Uncomment component annotation for init data to database
 *
 * @author Rian Rivaldo
 */
// @Component
public class InitDataUtils extends BaseServiceImpl implements CommandLineRunner {

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private FileDao fileDao;

  @Autowired
  private UserService userService;

  @Autowired
  private StudentDao studentDao;

  @Autowired
  private TeacherDao teacherDao;

  @Autowired
  private CourseCategoryDao courseCategoryDao;

  @Autowired
  private CourseTypeDao courseTypeDao;

  @Autowired
  private CourseDao courseDao;

  @Autowired
  private EncoderUtils encoderUtils;

  @Override
  public void run(String... args) throws Exception {
    initData();
  }

  private void initData() throws Exception {
    // initSuperAdmin();
    // initRoles();
    // initUser();
    // initCourseCategories();
    // initCourseTypes();
    // initCourses();
    initFile();
  }


  private void initFile() throws Exception {
    byte[] data = new byte[5];
    // file.setContentType(contentType);
    for (int i = 0; i < 5; i++) {
      File file = new File();
      file.setData(data);
      file.setContentType("txt");
      file.setName("tes_file" + i);
      file.setType(FileType.ASSIGNMENT);
      file.setSize(2);
      fileDao.create(file);
    }
  }

  private void initSuperAdmin() throws Exception {
    Role role = new Role();
    role.setCode("RL-001");
    role.setName("Super Admin");
    roleDao.create(role);

    User user = new User();
    user.setFirstName("Super Administrator");
    user.setUsername("superAdmin");
    user.setPassword(encoderUtils.getHashPassword("superAdmin"));
    user.setEmail("superadmin@lawerning.com");
    user.setRole(role);
    userDao.createUser(user);
  }

  private void initRoles() throws Exception {
    Role superAdminRole = roleDao.findByCode("RL-001");
    for (int i = 2; i <= 4; i++) {
      Role role = new Role();
      role.setCode("RL-00" + i);
      role.setName("Role-00" + i);
      role.setCreatedBy(superAdminRole.getId());
      roleDao.create(role);
    }
  }

  private void initUser() throws Exception {
    initAdmin();
    initStudent();
    initTeacher();
  }

  private void initAdmin() throws Exception {
    User user = new User();
    user.setFirstName("Administrator");
    user.setUsername("admin");
    user.setEmail("admin@lawerning.com");
    user.setPassword(encoderUtils.getHashPassword("admin"));

    User superAdminUser = userDao.findByUsername("superAdmin");
    user.setCreatedBy(superAdminUser.getId());

    Role role = roleDao.findByCode("RL-002");
    user.setRole(role);
    userDao.createUser(user);
  }

  private void initStudent() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 20; i++) {
      User user = new User();
      user.setFirstName("Student");
      user.setLastName("00" + i);
      user.setUsername("student-00" + i);
      user.setEmail(String.format("student-00%d@gmail.com", i));
      user.setPassword(encoderUtils.getHashPassword("student"));
      user.setCreatedBy(adminUser.getId());

      Role role = roleDao.findByCode("RL-004");
      user.setRole(role);
      userDao.createUser(user);

      Student student = new Student();
      student.setCode("ST-00" + i);
      student.setPhone("08120000" + i);
      if (i % 2 == 0) {
        student.setGender(Gender.MALE);
      } else {
        student.setGender(Gender.FEMALE);
      }
      student.setUser(user);
      student.setCreatedBy(adminUser.getId());
      studentDao.insertStudent(student, null);
    }
  }

  private void initTeacher() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    Role role = roleDao.findByCode("RL-003");
    for (int i = 1; i <= 10; i++) {
      User user = new User();
      user.setFirstName("Teacher");
      user.setLastName("00" + i);
      user.setUsername("teacher-00" + i);
      user.setPassword(encoderUtils.getHashPassword("teacher"));
      user.setEmail(String.format("teacher-00%d@gmail.com", i));
      user.setRole(role);
      user.setCreatedAt(LocalDateTime.now());
      user.setCreatedBy(adminUser.getId());
      userDao.createUser(user);

      Gender gender;
      if (i % 2 == 0) {
        gender = Gender.MALE;
      } else {
        gender = Gender.FEMALE;
      }
      Teacher teacher = new Teacher();
      teacher.setCode("TC-00" + i);
      teacher.setPhone("08990000" + i);
      teacher.setGender(gender);
      teacher.setTitleDegree("S.AMPLE");
      teacher.setCreatedAt(LocalDateTime.now());
      teacher.setCreatedBy(adminUser.getId());
      teacher.setUser(user);
      teacherDao.saveTeacher(teacher, null);
    }
  }

  private void initCourseCategories() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      CourseCategory courseCategory = new CourseCategory();
      courseCategory.setCode("CC-00" + i);
      courseCategory.setName("CourseCategory-00" + i);
      courseCategory.setCreatedAt(LocalDateTime.now());
      courseCategory.setCreatedBy(adminUser.getId());
      courseCategoryDao.insertCourseCategory(courseCategory, null);
    }
  }

  private void initCourseTypes() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      CourseType courseType = new CourseType();
      courseType.setCode("CT-00" + i);
      courseType.setName("CourseType-00" + i);
      courseType.setCreatedAt(LocalDateTime.now());
      courseType.setCreatedBy(adminUser.getId());
      courseTypeDao.insertCourseType(courseType, null);
    }
  }

  private void initCourses() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    Random random = new Random();
    List<CourseCategory> courseCategories = courseCategoryDao.getListCourseCategory();
    List<CourseType> courseTypes = courseTypeDao.getListCourseType();
    List<Teacher> teacherList = teacherDao.getAllTeachers();
    for (int i = 1; i <= 20; i++) {
      Course course = new Course();
      course.setCode("CRS-00" + i);
      course.setDescription("lorem ipsum bla bla for " + course.getCode());
      LocalDateTime dateTimeNow = LocalDateTime.now();
      course.setPeriodStart(dateTimeNow.plusDays(i));
      course.setPeriodEnd(course.getPeriodStart().plusDays(30));
      course.setCapacity(random.nextInt(50));
      course.setStatus(CourseStatus.values()[random.nextInt(3)]);

      int constraintIndex = i - 1;
      if (i > 10) {
        constraintIndex -= 10;
      }
      course.setCategory(courseCategories.get(constraintIndex));
      course.setCourseType(courseTypes.get(constraintIndex));
      course.setTeacher(teacherList.get(constraintIndex));
      course.setCreatedAt(LocalDateTime.now());
      course.setCreatedBy(adminUser.getId());
      courseDao.insertCourse(course, null);
    }
  }

  // private void initStudentCourse() throws Exception {
  // List<Student> studentList = studentDao.findAll();
  // int totalStudent = studentList.size();
  // List<Course> courses = courseDao.getListCourse();
  // int totalCourse = courses.size();
  // for (int i = 0; i < totalStudent / 2; i++) {
  // Student student = studentList.get(i);
  // for (int j = 0; j < totalCourse; j++) {
  //
  // }
  // studentDao.updateStudentProfile(student, () -> student.getCourses().add(courses.get(0)));
  // }
  // }

  // private void initSuperAdmin() throws Exception {
  // Role role = new Role();
  // role.setCode("RL-001");
  // role.setName("Super Admin");
  // roleService.create(role);
  //
  // User user = new User();
  // user.setFirstName("Super Administrator");
  // user.setUsername("superAdmin");
  // user.setPassword("superAdmin");
  // user.setEmail("superadmin@lawerning.com");
  // user.setRole(role);
  // userService.addUser(user);
  // }
  //
  // private void initRoles() throws Exception {
  // Role superAdminRole = roleService.findByCode("RL-001");
  // for (int i = 2; i <= 4; i++) {
  // Role role = new Role();
  // role.setCode("RL-00" + i);
  // role.setName("Role-00" + i);
  // role.setCreatedBy(superAdminRole.getId());
  // roleService.create(role);
  // }
  // }
  //
  // private void initUser() throws Exception {
  // initAdmin();
  // initStudent();
  // initTeacher();
  // }
  //
  // private void initAdmin() throws Exception {
  // User user = new User();
  // user.setFirstName("Administrator");
  // user.setUsername("admin");
  // user.setEmail("admin@lawerning.com");
  // user.setPassword("admin");
  //
  // User superAdminUser = userService.getByUsername("superAdmin");
  // user.setCreatedBy(superAdminUser.getId());
  //
  // Role role = roleService.findByCode("RL-002");
  // user.setRole(role);
  // userService.addUser(user);
  // }
  //
  // private void initStudent() throws Exception {
  // User adminUser = userService.getByUsername("admin");
  // for (int i = 1; i <= 20; i++) {
  // RegisterStudentDTO std = new RegisterStudentDTO();
  // std.setFirstName("Student");
  // std.setLastName("00" + i);
  // std.setUsername("student-00" + i);
  // std.setEmail(String.format("student-00%d@gmail.com", i));
  // std.setPassword("student");
  // std.setCreatedBy(adminUser.getId());
  // Role role = roleService.findByCode("RL-004");
  // std.setRoleId(role.getId());
  // std.setRoleVersion(role.getVersion());
  // std.setCode("ST-00" + i);
  // std.setPhone("08120000" + i);
  // if (i % 2 == 0) {
  // std.setGender("MALE");
  // } else {
  // std.setGender("FEMALE");
  // }
  // studentService.insertStudent(std);
  // }
  // }
  //
  // private void initTeacher() throws Exception {
  // User adminUser = userService.getByUsername("admin");
  // Role role = roleService.findByCode("RL-003");
  // for (int i = 1; i <= 10; i++) {
  // Gender gender;
  // if (i % 2 == 0) {
  // gender = Gender.MALE;
  // } else {
  // gender = Gender.FEMALE;
  // }
  // TeacherRequestDTO requestDTO = new TeacherRequestDTO(
  // "TC-00" + i,
  // "Teacher",
  // "00" + i,
  // "08990000" + i,
  // gender,
  // "teacher-00" + i,
  // "teacher",
  // String.format("teacher-00%d@gmail.com", i),
  // role.getId(),
  // 0L,
  // adminUser.getId(),
  // "S.AMPLE"
  // );
  // teacherService.saveTeacher(requestDTO);
  // }
  // }
  //
  // private void initCourseCategories() throws Exception {
  // User adminUser = userService.getByUsername("admin");
  // for (int i = 1; i <= 10; i++) {
  // CourseCategory courseCategory = new CourseCategory();
  // courseCategory.setCode("CC-00" + i);
  // courseCategory.setName("CourseCategory-00" + i);
  // courseCategory.setCreatedAt(LocalDateTime.now());
  // courseCategory.setCreatedBy(adminUser.getId());
  // courseCategoryService.insertCourseCategory(courseCategory);
  // }
  // }
  //
  // private void initCourseTypes() throws Exception {
  // User adminUser = userService.getByUsername("admin");
  // for (int i = 1; i <= 10; i++) {
  // CourseType courseType = new CourseType();
  // courseType.setCode("CT-00" + i);
  // courseType.setName("CourseType-00" + i);
  // courseType.setCreatedAt(LocalDateTime.now());
  // courseType.setCreatedBy(adminUser.getId());
  // courseTypeService.insertCourseType(courseType);
  // }
  // }
  // >>>>>>> Stashed changes
  //
  // private void initCourses() throws Exception {
  // User adminUser = userService.getByUsername("admin");
  // Random random = new Random();
  // List<CourseCategory> courseCategories = courseCategoryService.getListCourseCategory();
  // List<CourseType> courseTypes = courseTypeService.getListCourseType();
  // List<Teacher> teacherList = teacherService.getAllTeachers();
  // for (int i = 1; i <= 20; i++) {
  // Course course = new Course();
  // course.setCode("CRS-00" + i);
  // course.setDescription("lorem ipsum bla bla for " + course.getCode());
  // LocalDateTime dateTimeNow = LocalDateTime.now();
  // course.setPeriodStart(dateTimeNow.plusDays(i));
  // course.setPeriodEnd(course.getPeriodStart().plusDays(30));
  // course.setCapacity(random.nextInt(50));
  // course.setStatus(CourseStatus.values()[random.nextInt(3)]);
  //
  // int constraintIndex = i - 1;
  // if (i > 10) {
  // constraintIndex -= 10;
  // }
  // course.setCategory(courseCategories.get(constraintIndex));
  // course.setCourseType(courseTypes.get(constraintIndex));
  // course.setTeacher(teacherList.get(constraintIndex));
  // course.setCreatedAt(LocalDateTime.now());
  // course.setCreatedBy(adminUser.getId());
  // courseService.insertCourse(course);
  // }
  // }

}
