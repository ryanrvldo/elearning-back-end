package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Experience;

/**
 * @author Rian Rivaldo
 */
public interface ExperienceDao {

  void create(Experience experience) throws Exception;

  Experience findById(String id) throws Exception;

  Experience findByCode(String code) throws Exception;

  List<Experience> findAllByTeacherId(String teacherId) throws Exception;

  void update(Experience experience) throws Exception;

}
