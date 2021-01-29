package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.Experience;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface ExperienceDao {

  void create(Experience experience) throws Exception;

  Experience findById(String id) throws Exception;

  Experience findByCode(String code) throws Exception;

  List<Experience> findAllByTeacherId(String teacherId) throws Exception;

  void update(Experience experience) throws Exception;

  void delete(String id) throws Exception;

}
