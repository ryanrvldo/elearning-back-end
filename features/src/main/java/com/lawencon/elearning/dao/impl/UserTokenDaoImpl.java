package com.lawencon.elearning.dao.impl;

import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.UserTokenDao;
import com.lawencon.elearning.model.UserToken;

/**
 * @author Rian Rivaldo
 */
@Repository
public class UserTokenDaoImpl extends CustomBaseDao<UserToken> implements UserTokenDao {

  @Override
  public void save(UserToken userToken) throws Exception {
    save(userToken, null, null);
  }

  @Override
  public UserToken findByToken(String token) throws Exception {
    String query = buildQueryOf(
        "FROM UserToken AS ut INNER JOIN FETCH ut.user AS u ",
        "INNER JOIN FETCH u.role AS r ",
        "WHERE ut.token = ?1"
    );
    return createQuery(query, clazz)
        .setParameter(1, token)
        .getSingleResult();
  }

}
