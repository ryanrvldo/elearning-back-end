package com.lawencon.elearning.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convert list of Object / Object[] to list model
 * <p>
 * <ul>
 * <li>Java 8+ required</li>
 * <li>Pure java utils and reflections</li>
 * <li>Support for super class of model</li>
 * <li>Support 1 nest for variable type model</li>
 * </ul>
 * 
 * @author Galih Dika
 */
public class HibernateUtils {

  /**
   * Return list model
   * <p>
   * <b>Example 1 :</b><br/>
   * 
   * String sql = "select username, email from Users";
   * <p>
   * List<<code>?</code>> listUser = em.createQuery(sql).getResultList();
   * <p>
   * List<<code>Users</code>> listResult = HibernateUtils.bMapperList(listUser, Users.class,
   * "username", "email");
   * 
   * <p>
   * <b>Example 2 :</b><br/>
   * 
   * String sql = "select username, email, r.id, r.name from Users as u LEFT JOIN u.role as r";
   * <p>
   * List<<code>?</code>> listUser = em.createQuery(sql).getResultList();
   * <p>
   * List<<code>Users</code>> listResult = HibernateUtils.bMapperList(listUser, Users.class,
   * "username", "email", "role.id", "role.name");
   * 
   * @param listMapping result list of hibernate query
   * @param clazz expected return model type
   * @param columns must be same as the variable name in the model class, if column selected is a
   *        foreign key then column parameter must be separated by dot (.) see example 2.
   */
  public static <T> List<T> bMapperList(List<?> listMapping, Class<T> clazz, String... columns)
      throws Exception {
    Map<String, Object> mapObject = new HashMap<>();

    List<T> listResult = new ArrayList<>();

    listMapping.forEach(val -> {

      try {
        T newClass = clazz.getDeclaredConstructor().newInstance();
        Method[] methodArr = clazz.getDeclaredMethods();
        List<Method> methodList = new ArrayList<>(Arrays.asList(methodArr));
        getSuperMethod(clazz, methodList);

        if (val instanceof Object[]) {
          if (((Object[]) val).length != columns.length)
            throw new RuntimeException(
                "The length of columns parameter and select not match : select -> "
                    + ((Object[]) val).length + " and columns parameter -> " + columns.length);

          Object[] valDb = (Object[]) val;
          for (int i = 0; i < valDb.length; i++) {
            Object value = valDb[i];
            String mapping = columns[i];

            invokeMethod(newClass, methodList, value, mapping.toLowerCase(), mapObject);
          }
        } else if (val instanceof Object) {
          if (columns.length != 1)
            throw new RuntimeException(
                "The columns parameter must be one because you only select one column");

          invokeMethod(newClass, methodList, val, columns[0].toLowerCase(), mapObject);
        }

        listResult.add(newClass);
        mapObject.clear();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    return listResult;
  }

  private static <T> void getSuperMethod(Class<T> clazz, List<Method> listMethod) {
    Class<? super T> superClazz = clazz.getSuperclass();
    while (superClazz != null) {
      Method[] methodSuper = superClazz.getDeclaredMethods();
      listMethod.addAll(Arrays.asList(methodSuper));
      superClazz = superClazz.getSuperclass();
    }
  }

  private static <T> void invokeMethod(T newClass, List<Method> listMethod, Object value,
      String mapping, Map<String, Object> mapObject) throws Exception {

    for (int j = 0; j < listMethod.size(); j++) {

      Method m = listMethod.get(j);

      if (m.getName().startsWith("set")) {

        Parameter p = m.getParameters()[0];

        if (mapping.equalsIgnoreCase(p.getName())
            || mapping.contains(".") && mapping.startsWith(p.getName())) {

          Class<?> classVariable = p.getType();

          if (classVariable.getPackageName().equals(newClass.getClass().getPackageName())) {
            Object objVariable = classVariable.getDeclaredConstructor().newInstance();
            Object objMap = mapObject.get(p.getName());

            if (objMap == null) {
              mapObject.put(p.getName(), objVariable);
              objMap = mapObject.get(p.getName());
            }

            Method[] methods = classVariable.getDeclaredMethods();

            if (mapping.contains(".")) {
              String[] mapSplit = mapping.split("\\.");

              for (Method method : methods) {
                Class<?>[] param = method.getParameterTypes();
                if (method.getName().equalsIgnoreCase("set" + mapSplit[1])) {
                  setValueToSetter(method, objMap, param[0], value);
                  break;
                }
              }

              m.invoke(newClass, objMap);
            }
          } else {
            Class<?>[] param = m.getParameterTypes();
            setValueToSetter(m, newClass, param[0], value);
          }

          break;
        }
      }
    }
  }

  private static <T> void setValueToSetter(Method m, T newClass, Class<?> clazz, Object value)
      throws Exception {
    if (value != null) {
      if (clazz.equals(java.time.LocalDate.class) && value instanceof java.sql.Date) {
        m.invoke(newClass, value != null ? ((java.sql.Date) value).toLocalDate() : null);
      } else if (clazz.equals(java.time.LocalDateTime.class)
          && value instanceof java.sql.Timestamp) {
        m.invoke(newClass, value != null ? ((java.sql.Timestamp) value).toLocalDateTime() : null);
      } else if (clazz.equals(java.time.LocalTime.class) && value instanceof java.sql.Time) {
        m.invoke(newClass, value != null ? ((java.sql.Time) value).toLocalTime() : null);
      } else if (clazz.equals(java.util.Date.class) && value instanceof java.sql.Date) {
        m.invoke(newClass,
            value != null ? new java.util.Date(((java.sql.Date) value).getTime()) : null);
      } else if (clazz.equals(java.util.Date.class) && value instanceof java.sql.Timestamp) {
        m.invoke(newClass,
            value != null ? new java.util.Date(((java.sql.Timestamp) value).getTime()) : null);
      } else if (clazz.equals(BigDecimal.class)) {
        m.invoke(newClass, value != null ? new BigDecimal(value.toString()) : null);
      } else if (clazz.equals(Long.class)) {
        m.invoke(newClass, value != null ? Long.valueOf(value.toString()) : null);
      } else {
        m.invoke(newClass, value != null ? value : null);
      }
    } else {
      m.invoke(newClass, value != null ? value : null);
    }
  }
}
