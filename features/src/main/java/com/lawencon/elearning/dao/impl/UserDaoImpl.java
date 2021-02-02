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

  @Override
  public void createUser(User user) throws Exception {
    save(user, null, null, false, false);
  }

  @Override
  public User findById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public User findByUsername(String username) throws Exception {
    String query = buildQueryOf(
        "SELECT u.id, u.email, u.username, u.password, u.isActive, r.id AS roleId, u.role.code, u.role.name, u.userPhoto.id ",
        "FROM User AS u INNER JOIN Role AS r ON r.id = u.role.id ",
        "LEFT JOIN File f ON f.id = u.userPhoto.id ",
        "WHERE u.username = ?1 ");
    Object[] objArr = createQuery(query, Object[].class).setParameter(1, username)
        .getSingleResult();
    User user = new User();
    user.setId((String) objArr[0]);
    user.setEmail((String) objArr[1]);
    user.setUsername((String) objArr[2]);
    user.setPassword((String) objArr[3]);
    user.setIsActive((Boolean) objArr[4]);

    Role role = new Role();
    role.setId((String) objArr[5]);
    role.setCode((String) objArr[6]);
    role.setName((String) objArr[7]);
    user.setRole(role);

    File file = new File();
    file.setId((String) objArr[8]);
    user.setUserPhoto(file);
    return user;
  }

  @Override
  public void updateUser(User user) throws Exception {
    save(user, null, null, false, false);
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

}
