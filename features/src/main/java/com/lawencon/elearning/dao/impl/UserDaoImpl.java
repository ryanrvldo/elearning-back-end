package com.lawencon.elearning.dao.impl;

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
      "SELECT u.id, u.email, u.username, u.password, u.isActive, u.version, r.id AS roleId, u.role.code, u.role.name, u.userPhoto.id, u.userPhoto.version ",
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

  private User getAndSetupUser(String query, Object param) {
    Object[] objArr = createQuery(query, Object[].class)
        .setParameter(1, param)
        .getSingleResult();
    User user = new User();
    user.setId((String) objArr[0]);
    user.setEmail((String) objArr[1]);
    user.setUsername((String) objArr[2]);
    user.setPassword((String) objArr[3]);
    user.setIsActive((Boolean) objArr[4]);
    user.setVersion((Long) objArr[5]);

    Role role = new Role();
    role.setId((String) objArr[6]);
    role.setCode((String) objArr[7]);
    role.setName((String) objArr[8]);
    user.setRole(role);

    File file = new File();
    file.setId((String) objArr[9]);
    file.setVersion((Long) objArr[10]);
    user.setUserPhoto(file);
    return user;
  }

}
