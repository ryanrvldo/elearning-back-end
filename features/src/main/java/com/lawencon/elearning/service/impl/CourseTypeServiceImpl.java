package com.lawencon.elearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeCreateRequestDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeResponseDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeUpdateRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.service.CourseTypeService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author : Galih Dika Permana
 */
@Service
public class CourseTypeServiceImpl extends BaseServiceImpl implements CourseTypeService {

  @Autowired
  private CourseTypeDao courseTypeDao;

  @Autowired
  private ValidationUtil validateUtil;

  @Override
  public List<CourseTypeResponseDTO> getListCourseType() throws Exception {
    List<CourseType> courseTypes = courseTypeDao.getListCourseType();

    if (courseTypes == null) {
      throw new DataIsNotExistsException("Data is not exist");
    }
    List<CourseTypeResponseDTO> responses = new ArrayList<CourseTypeResponseDTO>();

    for (CourseType ct : courseTypes) {
      CourseTypeResponseDTO response = new CourseTypeResponseDTO();
      response.setId(ct.getId());
      response.setCode(ct.getCode());
      response.setName(ct.getName());
      response.setVersion(ct.getVersion());
      responses.add(response);
    }

    return responses;
  }

  @Override
  public void insertCourseType(CourseTypeCreateRequestDTO courseTypeDTO) throws Exception {
    validateUtil.validate(courseTypeDTO);
    CourseType courseType = new CourseType();
    courseType.setCode(courseTypeDTO.getCode());
    courseType.setCreatedBy(courseTypeDTO.getCreatedBy());
    courseType.setName(courseTypeDTO.getName());
    courseTypeDao.insertCourseType(courseType, null);
  }

  @Override
  public void updateCourseType(CourseTypeUpdateRequestDTO courseTypeDTO) throws Exception {
    validateUtil.validate(courseTypeDTO);
    CourseType courseType = new CourseType();
    courseType.setId(courseTypeDTO.getId());
    courseType.setUpdatedBy(courseTypeDTO.getUpdatedBy());
    courseType.setCode(courseTypeDTO.getCode());
    courseType.setName(courseTypeDTO.getName());

    CourseType courseTypes = courseTypeDao.getTypeById(courseType.getId());
    if (courseTypes == null) {
      throw new DataIsNotExistsException("data is not exist");
    }
    setupUpdatedValue(courseType, () -> courseTypes);
    courseTypeDao.updateCourseType(courseType, null);
  }

  @Override
  public void deleteCourseType(String id) throws Exception {
    validateUtil.validateUUID(id);
    try {
      begin();
      courseTypeDao.deleteCourseType(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }


  @Override
  public void updateIsActive(UpdateIsActiveRequestDTO data) throws Exception {
    validateUtil.validate(data);
    try {
      begin();
      courseTypeDao.updateIsActive(data.getId(), data.getUpdatedBy(), data.getStatus());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }


}
