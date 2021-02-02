package com.lawencon.elearning.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.dao.ExperienceDao;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.dao.ForumDao;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.dao.ScheduleDao;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.dao.SubjectCategoryDao;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Forum;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;

/**
 * Uncomment component annotation for init data to database
 *
 * @author Rian Rivaldo
 */
@Component
public class InitDataUtils extends BaseServiceImpl implements CommandLineRunner {

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private UserDao userDao;

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
  private FileDao fileDao;

  @Autowired
  private SubjectCategoryDao subjectCategoryDao;

  @Autowired
  private ExperienceDao experienceDao;

  @Autowired
  private ScheduleDao scheduleDao;

  @Autowired
  private AttendanceDao attendanceDao;

  @Autowired
  private ModuleDao moduleDao;

  @Autowired
  private ForumDao forumDao;

  @Autowired
  private EncoderUtils encoderUtils;

  @Override
  public void run(String... args) throws Exception {
    initData();
  }

  private void initData() throws Exception {
    initSuperAdmin();
    initRoles();
    initUser();
    initCourseCategories();
    initCourseTypes();
    initCourses();
    initStudentCourse();
    initFile();
    initSubjectCategory();
    initExperience();
    initSchedule();
    initModule();
    initAttendance();
    initForum();
    initModuleFile();
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
    begin();
    userDao.createUser(user);
    commit();
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
    begin();
    userDao.createUser(user);
    commit();
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
      begin();
      userDao.createUser(user);
      commit();

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
      begin();
      studentDao.insertStudent(student, null);
      commit();
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
      begin();
      userDao.createUser(user);
      commit();

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
      begin();
      teacherDao.saveTeacher(teacher, null);
      commit();
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

  private void initStudentCourse() throws Exception {
    List<Student> studentList = studentDao.findAll();
    List<Course> courses = courseDao.getListCourse();
    int totalCourse = courses.size();
    Random random = new Random();
    for (Student student : studentList) {
      for (int j = 0; j < totalCourse; j++) {
        studentDao.updateStudentProfile(student,
            () -> student.getCourses().add(courses.get(random.nextInt(totalCourse - 1))));
      }
    }
  }

  private void initFile() throws Exception {
    byte[] data = new byte[5];
    for (int i = 0; i < 5; i++) {
      File file = new File();
      file.setData(data);
      file.setContentType(MediaType.TEXT_PLAIN_VALUE);
      file.setName("tes_file" + i);
      file.setType(FileType.ASSIGNMENT);
      file.setSize(5);
      fileDao.create(file);
    }
  }

  private void initSubjectCategory() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      SubjectCategory subjectCategory = new SubjectCategory();
      subjectCategory.setCode("CT-00" + i);
      subjectCategory.setSubjectName("Subject Category-0" + i);
      subjectCategory.setDescription("lorem ipsum bla bla " + subjectCategory.getCode());
      subjectCategory.setCreatedAt(LocalDateTime.now());
      subjectCategory.setCreatedBy(adminUser.getId());
      subjectCategoryDao.addSubject(subjectCategory, null);
    }
  }

  private void initExperience() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      Experience experience = new Experience();
      experience.setTitle("Experience-00" + i);
      experience.setCreatedBy(adminUser.getId());
      experience.setCreatedAt(LocalDateTime.now());
      experience.setDescription("lorem ipsum blabla " + experience.getTitle());
      experience.setStartDate(LocalDate.now().plusDays(i));
      experience.setEndDate(LocalDate.now().plusDays(i+25));
      List<Teacher> teacher = teacherDao.getAllTeachers();
      experience.setTeacher(teacher.get(i - 1));
      experienceDao.create(experience);
    }
  }

  private void initSchedule() throws Exception{
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      Schedule schedule = new Schedule();
      schedule.setCreatedAt(LocalDateTime.now());
      schedule.setCreatedBy(adminUser.getId());
      schedule.setCode("Schedule-00"+i);
      schedule.setDate(LocalDate.now());
      schedule.setStartTime(LocalTime.now());
      schedule.setEndTime(LocalTime.now().plusHours(i+2));
      List<Teacher> teacher = teacherDao.getAllTeachers();
      schedule.setTeacher(teacher.get(i - 1));
      begin();
      scheduleDao.saveSchedule(schedule, null);
      commit();
    }
  }

  private void initAttendance() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      Attendance attendance = new Attendance();
      attendance.setCreatedAt(LocalDateTime.now());
      attendance.setCreatedBy(adminUser.getId());
      attendance.setTrxNumber("Attendance-00" + i);
      attendance.setTrxDate(LocalDate.now());
      attendance.setIsVerified(false);
      List<Module> module = moduleDao.getListModule();
      attendance.setModule(module.get(i - 1));
      List<Student> student = studentDao.findAll();
      attendance.setStudent(student.get(i - 1));
      attendanceDao.createAttendance(attendance, null);
    }
  }

  private void initForum() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      Forum forum = new Forum();
      forum.setCreatedAt(LocalDateTime.now());
      forum.setCreatedBy(adminUser.getId());
      forum.setTrxDate(LocalDate.now());
      forum.setTrxNumber("Forum-00" + i);
      forum.setContent("loremipsum bla bla bla" + forum.getTrxNumber());
      List<Module> module = moduleDao.getListModule();
      forum.setModule(module.get(i - 1));
      if (i % 2 == 0) {
        User user = userDao.findByUsername("student-00" + i);
        forum.setUser(user);
      } else {
        User user = userDao.findByUsername("teacher-00" + i);
        forum.setUser(user);
      }
      forumDao.saveForum(forum, null);
    }
  }

  private void initModule() throws Exception {
    User adminUser = userDao.findByUsername("admin");
    for (int i = 1; i <= 10; i++) {
      Module module = new Module();
      module.setCode("Module-00"+i);
      module.setTitle("Module Title-00" +i);
      module.setDescription("Lorem Ipsum bla bla bla bla" + module.getTitle());
      List<Schedule> schedule = scheduleDao.getAllSchedules();
      module.setSchedule(schedule.get(i - 1));
      List<Course> course = courseDao.getListCourse();
      module.setCourse(course.get(i - 1));
      List<SubjectCategory> subject = subjectCategoryDao.getAllSubject();
      module.setSubject(subject.get(i - 1));
      module.setCreatedAt(LocalDateTime.now());
      module.setCreatedBy(adminUser.getId());
      // List<File> file = fileDao.getAllFile();
      // module.setFiles();
      begin();
      moduleDao.insertModule(module, null);
      commit();
    }
  }

  private void initModuleFile() throws Exception {
    List<Module> moduleList = moduleDao.getListModule();
    List<File> file = fileDao.getAllFile();
    int totalFile = file.size();
    Random random = new Random();
    for (Module module : moduleList) {
      for (int i = 0; i < totalFile; i++) {
        moduleDao.updateModule(module,
            () -> module.getFiles().add(file.get(random.nextInt(totalFile - 1))));
      }
    }
  }

}
