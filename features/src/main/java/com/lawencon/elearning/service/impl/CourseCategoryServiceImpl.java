package com.lawencon.elearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryCreateRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryResponseDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryUpdateRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.service.CourseCategoryService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author : Galih Dika Permana
 */
@Service
public class CourseCategoryServiceImpl extends BaseServiceImpl implements CourseCategoryService {

  @Autowired
  private CourseCategoryDao courseCategoryDao;

  @Autowired
  private ValidationUtil validateUtil;

  @Override
  public List<CourseCategoryResponseDTO> getListCourseCategory() throws Exception {
    List<CourseCategory> courseCategories = courseCategoryDao.getListCourseCategory();
    if (courseCategories == null) {
      throw new DataIsNotExistsException("Data is not exist");
    }

    List<CourseCategoryResponseDTO> courseCategoryResponse = new ArrayList<>();
    for (CourseCategory courseCategory : courseCategories) {
      CourseCategoryResponseDTO response = new CourseCategoryResponseDTO();
      response.setId(courseCategory.getId());
      response.setCode(courseCategory.getCode());
      response.setName(courseCategory.getName());
      response.setVersion(courseCategory.getVersion());
      courseCategoryResponse.add(response);
    }

    return courseCategoryResponse;
  }

  @Override
  public void insertCourseCategory(CourseCategoryCreateRequestDTO courseCategoryDTO)
      throws Exception {
    validateUtil.validate(courseCategoryDTO);
    CourseCategory courseCategory = new CourseCategory();
    courseCategory.setCode(courseCategoryDTO.getCode());
    courseCategory.setCreatedBy(courseCategoryDTO.getCreatedBy());
    courseCategory.setName(courseCategoryDTO.getName());
    courseCategoryDao.insertCourseCategory(courseCategory, null);
  }

  @Override
  public void updateCourseCategory(CourseCategoryUpdateRequestDTO courseCategoryDTO)
      throws Exception {
    validateUtil.validate(courseCategoryDTO);
    CourseCategory courseCategory = new CourseCategory();
    courseCategory.setId(courseCategoryDTO.getId());
    courseCategory.setCode(courseCategoryDTO.getCode());
    courseCategory.setUpdatedBy(courseCategoryDTO.getUpdateBy());
    courseCategory.setName(courseCategoryDTO.getName());
    CourseCategory courseCategories = courseCategoryDao.getCategoryById(courseCategory.getId());
    if (courseCategories == null) {
      throw new DataIsNotExistsException("data is not exist");
    }
    setupUpdatedValue(courseCategory, () -> courseCategories);
    courseCategoryDao.updateCourseCategory(courseCategory, null);
  }

  @Override
  public void deleteCourseCategory(String id)
      throws Exception {
    validateNullId(id, "id");
    try {
      begin();
      courseCategoryDao.deleteCourseCategory(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public void setIsActiveTrue(DeleteMasterRequestDTO data) throws Exception {
    try {
      begin();
      courseCategoryDao.setIsActiveTrue(data.getId(), data.getUpdatedBy());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public void setIsActiveFalse(DeleteMasterRequestDTO data) throws Exception {
    try {
      begin();
      courseCategoryDao.setIsActiveFalse(data.getId(), data.getUpdatedBy());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
