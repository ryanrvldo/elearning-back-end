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
 * @author Ryan Rivaldo
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

        if (mapping.equalsIgnoreCase(p.getName()) || mapping.contains(".")
            && p.getName().equalsIgnoreCase(mapping.substring(0, mapping.indexOf(".")))) {

          Class<?> classVariable = p.getType();
          if (classVariable.getPackage().getName()
              .equals(newClass.getClass().getPackage().getName()) && !classVariable.isEnum()) {
            getRelatedModel(newClass, mapping, classVariable, mapObject, p, m, value);
          } else {
            Class<?>[] param = m.getParameterTypes();
            setValueToSetter(m, newClass, param[0], value);
          }
          break;
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static <T> void getRelatedModel(T newClass, String mapping, Class<?> classVariable,
      Map<String, Object> mapObject, Parameter p, Method m, Object value) throws Exception {

    Class<?> relatedClazz = classVariable;
    while (true) {
      Object objVariable = relatedClazz.getDeclaredConstructor().newInstance();
      Object objMap = mapObject.get(p.getName());

      if (objMap == null) {
              mapObject.put(p.getName(), objVariable);
              objMap = mapObject.get(p.getName());
            }

            setValueToSetter(m, newClass, relatedClazz, objMap);

            if (mapping.contains(".")) {
              String[] mapSplit = mapping.split("\\.");

              Method[] methodArr = relatedClazz.getDeclaredMethods();
              List<Method> methodList = new ArrayList<>(Arrays.asList(methodArr));
              getSuperMethod(relatedClazz, methodList);

              boolean isDeepModel = false;

              for (int i = 1; i < mapSplit.length; i++) {

                for (Method method : methodList) {

                  Class<?>[] param = method.getParameterTypes();

                  if (method.getName().equalsIgnoreCase("set" + mapSplit[i])) {

                    if (param[0].getPackage().getName().equals(
                        newClass.getClass().getPackage().getName()) && !relatedClazz.isEnum()) {
                      isDeepModel = true;
                      relatedClazz = param[0];
                      p = method.getParameters()[0];
                      m = method;
                      newClass = (T) objMap;

                      break;
                    } else {
                      setValueToSetter(method, objMap, param[0], value);
                    }
                  }
                }
              }

              if (isDeepModel)
                continue;
          }

          break;
      }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static <T> void setValueToSetter(Method m, T newClass, Class<?> clazz, Object value)
      throws Exception {
    if (value != null) {
      if (clazz.equals(java.time.LocalDate.class) && value instanceof java.sql.Date) {
        m.invoke(newClass, ((java.sql.Date) value).toLocalDate());
      } else if (clazz.equals(java.time.LocalDateTime.class)
          && value instanceof java.sql.Timestamp) {
        m.invoke(newClass, ((java.sql.Timestamp) value).toLocalDateTime());
      } else if (clazz.equals(java.time.LocalTime.class) && value instanceof java.sql.Time) {
        m.invoke(newClass, ((java.sql.Time) value).toLocalTime());
      } else if (clazz.equals(java.util.Date.class) && value instanceof java.sql.Date) {
        m.invoke(newClass, new java.util.Date(((java.sql.Date) value).getTime()));
      } else if (clazz.equals(java.util.Date.class) && value instanceof java.sql.Timestamp) {
        m.invoke(newClass, new java.util.Date(((java.sql.Timestamp) value).getTime()));
      } else if (clazz.equals(BigDecimal.class)) {
        m.invoke(newClass, new BigDecimal(value.toString()));
      } else if (clazz.equals(Long.class)) {
        m.invoke(newClass, Long.valueOf(value.toString()));
      } else if (clazz.isEnum()) {
        m.invoke(newClass, Enum.valueOf((Class) clazz, value.toString()));
      } else {
        m.invoke(newClass, value);
      }
      }
  }

  /**
   * Return list map, example :
   * <p>
   * String sql = "select employeeName, employeeCode from Employee";
   * <p>
   * List<<code>?</code>> employees = em.createQuery(sql, Object[].class).getResultList();;
   * <p>
   * return HibernateUtils.bMapperListMap(employees, employeeName, employeeCode);
   * <p>
   */
  public static List<Map<String, Object>> bMapperListMap(List<?> listMapping, String... columns)
      throws Exception {
    if (listMapping.isEmpty())
      return new ArrayList<>();

    List<Map<String, Object>> listMap = new ArrayList<>();
    listMapping.forEach(val -> {
      Map<String, Object> map = new HashMap<>();
      if (val instanceof Object[]) {
        Object[] valObj = (Object[]) val;
        if (valObj.length != columns.length)
          throw new RuntimeException(
              "The length of columns parameter and select not match : select -> "
                  + ((Object[]) val).length + " and columns parameter -> " + columns.length);

        for (int i = 0; i < valObj.length; i++) {
          map.put(columns[i], valObj[i]);
        }
      } else {
        if (columns.length != 1)
          throw new RuntimeException(
              "The columns parameter must be one because you only select one column");

        map.put(columns[0], val);
      }

      listMap.add(map);
    });

    return listMap;
  }

  /**
   * Return list class non model, example :
   * <p>
   * String sql = "select employeeName, employeeCode from Employee";
   * <p>
   * List<<code>?</code>> employees = em.createQuery(sql, String.class).getResultList();
   * <p>
   * return HibernateUtils.bMapperListMap(employees, employeeName, employeeCode);
   * <p>
   * r
   */
  @SuppressWarnings({"unchecked"})
  public static <T> List<T> bMapperNonModelList(List<?> listMapping, Class<T> clazz)
      throws Exception {
    List<T> listResult = new ArrayList<>();
    listMapping.forEach(val -> {
      try {
        if (val instanceof Object[]) {
          T[] objArr = (T[]) val;
          for (T t : objArr) {
            listResult.add(t);
          }
        } else {
          listResult.add((T) val);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    return listResult;
  }
}

