package com.lawencon.elearning;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.service.SubjectCategoryService;


/**
 * @author Rian Rivaldo
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lawencon")
public class ELearningApplication {

  public static void main(String[] args) {
    SpringApplication.run(ELearningApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(SubjectCategoryService td) {

    return args -> {
      // Teacher teach = new Teacher();
      // teach.setCode("TEACH01");
      // teach.setFirstName("Galih");
      // teach.setLastName("sandhika");
      // teach.setPhone("081280810442");
      // teach.setGender(Gender.MALE);
      // teach.setTitleDegree("S.Kom");
      //
      // User u = new User();
      // teach.setUser(u);
      // td.saveTeacher(teach, null);
      SubjectCategory sc = new SubjectCategory();
      sc.setId("b793f4b4-3092-43a7-b103-514b49cb5016");
      sc.setCode("S05");
      sc.setDescription("Pelajaran matematika");
      sc.setSubjectName("Matematika");
      sc.setVersion(2L);
      // td.updateSubject(sc, null);
      // td.updateIsActive(sc);
      td.deleteSubject("b793f4b4-3092-43a7-b103-514b49cb5016");
      // List<SubjectCategory> list = td.updateIsActive(data);
      // System.out.println(list.get(0).getCode() + " " + list.get(0).getSubjectName());
    };
  }

}
