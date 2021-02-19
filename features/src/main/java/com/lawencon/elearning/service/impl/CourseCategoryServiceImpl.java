package com.lawencon.elearning.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryCreateRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryResponseDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryUpdateRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.CourseCategoryService;
import com.lawencon.elearning.service.UserService;
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

  @Autowired
  private UserService userService;

  @Override
  public List<CourseCategoryResponseDTO> getListCourseCategory() throws Exception {
    List<CourseCategory> courseCategories = courseCategoryDao.getListCourseCategory();
    if (courseCategories == null) {
      return Collections.emptyList();
    }

    List<CourseCategoryResponseDTO> courseCategoryResponse = new ArrayList<>();
    for (CourseCategory courseCategory : courseCategories) {
      CourseCategoryResponseDTO response = new CourseCategoryResponseDTO();
      response.setId(courseCategory.getId());
      response.setCode(courseCategory.getCode());
      response.setName(courseCategory.getName());
      response.setVersion(courseCategory.getVersion());
      response.setActive(courseCategory.getIsActive());
      courseCategoryResponse.add(response);
    }

    courseCategoryResponse.sort(Comparator.comparing(val -> val.getCode()));
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
    User user = userService.getById(courseCategoryDTO.getUpdateBy());
    if (!user.getRole().getCode().equals(Roles.ADMIN.getCode()) && user.getIsActive() == false) {
      throw new IllegalAccessException("only admin can update data !");
    }
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
    validateUtil.validateUUID(id);
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
  public void updateIsActive(UpdateIsActiveRequestDTO data) throws Exception {
    validateUtil.validate(data);
    try {
      begin();
      courseCategoryDao.updateIsActive(data.getId(), data.getUpdatedBy(), data.getStatus());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

}
