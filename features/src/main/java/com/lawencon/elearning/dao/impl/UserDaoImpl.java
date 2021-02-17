package com.lawencon.elearning.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.User;

/**
 * @author Rian Rivaldo
 */
@Repository
public class UserDaoImpl extends CustomBaseDao<User> implements UserDao {

  private final String singleUserQuery = buildQueryOf(
      "SELECT u.id, u.firstName, u.lastName, u.email, u.username, u.password, u.isActive, ",
      "u.version, r.id AS roleId, u.role.code, u.role.name, u.userPhoto.id, u.userPhoto.version ",
      "FROM User AS u INNER JOIN u.role AS r ",
      "LEFT JOIN u.userPhoto ");

  @Override
  public void createUser(User user) throws Exception {
    save(user, null, null, false, false);
  }

  @Override
  public User findById(String id) throws Exception {
    return getAndSetupUser(singleUserQuery + "WHERE u.id = ?1", id);
  }

  @Override
  public User findByUsername(String username) throws Exception {
    return getAndSetupUser(singleUserQuery + "WHERE u.username = ?1 ", username);
  }

  @Override
  public void updateUser(User user) throws Exception {
    updateNativeSQL("UPDATE tb_m_users SET username = ?1, first_name = ?2, last_name = ?3",
        user.getId(),
        user.getUpdatedBy(),
        user.getUsername(),
        user.getFirstName(),
        user.getLastName());
  }

  @Override
  public void updateActivateStatus(String id, boolean status) throws Exception {
    createQuery("UPDATE User SET isActive = ?1 WHERE id = ?2 ", clazz)
        .setParameter(1, status)
        .setParameter(2, id)
        .executeUpdate();
  }

  @Override
  public void updateUserPhoto(User user) throws Exception {
    updateNativeSQL("UPDATE tb_m_users SET id_photo = ?1",
        user.getId(),
        user.getUpdatedBy(),
        user.getUserPhoto().getId());
  }

  @Override
  public String getUserRoleId(String userId) throws Exception {
    String query = buildQueryOf(
        "SELECT CASE WHEN tmt.id IS NULL THEN tms.id ELSE tmt.id END AS user_role_id ",
        "FROM tb_m_users tmu ",
        "LEFT JOIN tb_m_students tms ON tms.id_user = tmu.id ",
        "LEFT JOIN tb_m_teachers tmt ON tmt.id_user = tmu.id ",
        "WHERE tmu.id = ?1");
    Object objResult = createNativeQuery(query)
        .setParameter(1, userId)
        .getSingleResult();
    return (String) objResult;
  }

  @Override
  public void deleteById(String id) throws Exception {
    super.deleteById(id);
  }

  @Override
  public String getIdByEmail(String email) throws Exception {
    String query = buildQueryOf("SELECT id from tb_m_users WHERE email = ?1");
    Object objResult = createNativeQuery(query).setParameter(1, email).getSingleResult();
    return (String) objResult;
  }

  @Override
  public void updatePasswordUser(String userId, String newPassword, String updatedBy)
      throws Exception {
    String query = buildQueryOf("UPDATE tb_m_users SET user_password = ?1 , updated_at = now() , ",
        "updated_by = ?2, version = (version + 1) WHERE id = ?3").toString();

    createNativeQuery(query).setParameter(1, newPassword).setParameter(2, updatedBy)
        .setParameter(3, userId).executeUpdate();

  }

  private User getAndSetupUser(String query, Object param) {
    Object[] objArr = createQuery(query, Object[].class)
        .setParameter(1, param)
        .getSingleResult();
    User user = new User();
    user.setId((String) objArr[0]);
    user.setFirstName((String) objArr[1]);
    user.setLastName((String) objArr[2]);
    user.setEmail((String) objArr[3]);
    user.setUsername((String) objArr[4]);
    user.setPassword((String) objArr[5]);
    user.setIsActive((Boolean) objArr[6]);
    user.setVersion((Long) objArr[7]);

    Role role = new Role();
    role.setId((String) objArr[8]);
    role.setCode((String) objArr[9]);
    role.setName((String) objArr[10]);
    user.setRole(role);

    File file = new File();
    file.setId((String) objArr[11]);
    file.setVersion((Long) objArr[12]);
    user.setUserPhoto(file);
    return user;
  }

  @Override
  public List<String> getEmailUsersPerModule(String idModule) throws Exception {
    String query = buildQueryOf("SELECT tmu.email FROM tb_m_users tmu ",
        "INNER JOIN tb_m_students tms ON tmu.id = tms.id_user ",
        "INNER JOIN  student_course sc ON sc.id_student = tms.id ",
        "INNER JOIN tb_m_courses tmc ON tmc.id = sc.id_course ",
        "INNER JOIN tb_m_modules tmm ON tmc.id = tmm.id_course WHERE tmm.id = ?1").toString();

    List<?> listObj = createNativeQuery(query).setParameter(1, idModule).getResultList();

    List<String> resultList = new ArrayList<>();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      resultList.add((String) objArr[0]);
    });

    return resultList.size() > 0 ? resultList : null;
  }

}
