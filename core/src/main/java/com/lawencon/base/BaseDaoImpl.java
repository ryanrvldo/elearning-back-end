package com.lawencon.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

import com.lawencon.helper.InquiryTemplate;
import com.lawencon.util.Callback;

/**
 * <p>
 * Abstract DAO untuk handle basic CRUD
 * <p>
 * Setiap class child wajib set constructor untuk Entity yang digunakan
 * <p>
 * Sudah include method pre/post create, update, dan delete
 * <p>
 * Gunakan @override untuk mendefinisikan method pre/post sesuai kebutuhan
 * 
 * @author Agung Damas Saputra
 * 
 */
@Repository
public abstract class BaseDaoImpl<T extends Serializable> {

	public Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseDaoImpl.class);
	}

	protected String getClassName() {
		return clazz.getSimpleName();
	}

	protected String getTableName() {
		return clazz.getAnnotation(Table.class).name();
	}

	protected T getById(final String id) {
		return em().find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	protected List<T> getAll() {
		return em().createQuery("FROM " + clazz.getName()).getResultList();
	}

	/**
	 * Advance search providing filter for the entity.
	 * 
	 * @param inquiry       String that will be searched
	 * @param selectedField List of String that being selected
	 * @param conditions    Additional conditions with key being the field that will
	 *                      be searched and the value become the filter
	 * @param fromTable     Table view being used in FROM statement
	 * @param page          Integer value for offseting results set
	 * @param limit         Integer value for limiting results set
	 * @return List of Array-of-object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Object[]> getBySearch(String inquiry, List<String> selectedField,
			LinkedHashMap<String, Object> conditions, String fromTable, int page, int limit) throws Exception {
		String fields = selectedField.toString();
		fields = fields.replace("[", "");
		fields = fields.replace("]", "");

		Set<String> keys = conditions.keySet();

		StringBuilder sb = new StringBuilder("SELECT ").append(fields).append(" FROM ").append(fromTable)
				.append(" WHERE 1=1");

		for (String key : keys) {
			sb.append(" AND ").append(key).append(" = '").append(conditions.get(key)).append("'");
		}

		if (inquiry != null && !inquiry.isEmpty()) {
			sb.append(" AND POSITION(LOWER('").append(inquiry).append("') in LOWER(CONCAT(").append(fields)
					.append("))) > 0");
		}

		List<Object[]> listResult = new ArrayList<Object[]>();

		if (page > 0 && limit > 0) {
			listResult = ConnHandler.getManager().createNativeQuery(sb.toString()).setFirstResult(((page - 1) * limit))
					.setMaxResults(limit).getResultList();
		} else {
			listResult = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
		}

		return listResult;
	}

	// Experimental New Advance Search With custom operator for condition (Sample in
	// UserService)
	@SuppressWarnings("unchecked")
	protected List<Object[]> getBySearchV2(InquiryTemplate inquiry) {
		StringBuilder sb = new StringBuilder("SELECT ").append(inquiry.getSelect()).append(" FROM ")
				.append(inquiry.getViews()).append(" WHERE 1=1");

		if (inquiry.isAnyCondition()) {
			sb.append(" AND ");

			for (String field : inquiry.getConditionKeys()) {
				sb.append(field).append(inquiry.getCondition(field).get("operator")).append("'")
						.append(inquiry.getCondition(field).get("condition")).append("' ");
				if (inquiry.getCondition(field).get("connector") != null) {
					sb.append(inquiry.getCondition(field).get("connector")).append(" ");
				}
			}
		}

		if (inquiry.getInquiry() != null) {
			sb.append(" AND POSITION(LOWER('").append(inquiry.getInquiry()).append("') in LOWER(CONCAT(")
					.append(inquiry.getFields()).append("))) > 0");
		}

		List<Object[]> listResult = ConnHandler.getManager().createNativeQuery(sb.toString())
				.setFirstResult(((inquiry.getPage() - 1) * inquiry.getLimit())).setMaxResults(inquiry.getLimit())
				.getResultList();

		return listResult;
	}

	protected Integer getCountBySearch(String inquiry, List<String> selectedField,
			LinkedHashMap<String, Object> conditions, String tableView) throws Exception {
		String fields = selectedField.toString();
		fields = fields.replace("[", "");
		fields = fields.replace("]", "");

		Set<String> keys = conditions.keySet();

		StringBuilder sb = new StringBuilder("SELECT Count(*)").append(" FROM ").append(tableView)
				.append(" v WHERE 1=1");

		for (String key : keys) {
			sb.append(" AND v.").append(key).append(" = '").append(conditions.get(key)).append("'");
		}

		if (inquiry != null) {
			sb.append(" AND POSITION(LOWER('").append(inquiry).append("') in LOWER(CONCAT(").append(fields)
					.append("))) > 0");
		}

		BigInteger count = (BigInteger) ConnHandler.getManager().createNativeQuery(sb.toString()).getSingleResult();

		return count.intValue();
	}

	protected Integer getCountBySearchV2(InquiryTemplate inquiry) {
		StringBuilder sb = new StringBuilder("SELECT COUNT(*)").append(" FROM ").append(inquiry.getViews())
				.append(" WHERE 1=1");

		if (inquiry.isAnyCondition()) {
			sb.append(" AND ");

			for (String field : inquiry.getConditionKeys()) {
				sb.append(field).append(inquiry.getCondition(field).get("operator")).append("'")
						.append(inquiry.getCondition(field).get("condition")).append("' ");
				if (inquiry.getCondition(field).get("connector") != null) {
					sb.append(inquiry.getCondition(field).get("connector"));
				}
			}
		}

		if (inquiry.getInquiry() != null) {
			sb.append(" AND POSITION(LOWER('").append(inquiry.getInquiry()).append("') in LOWER(CONCAT(")
					.append(inquiry.getFields()).append("))) > 0");
		}

		BigInteger count = (BigInteger) ConnHandler.getManager().createNativeQuery(sb.toString()).getSingleResult();

		return count.intValue();
	}

	private Class<?> getBaseClass(final T entity) {
		Class<?> base = entity.getClass();
		try {
			base.getDeclaredField("id");
			return base;
		} catch (Exception e) {
			base = base.getSuperclass();
			while (true) {
				try {
					base.getDeclaredField("id");
					break;
				} catch (NoSuchFieldException x) {
					base = base.getSuperclass();
				}
			}
			return base;
		}
	}

//	public Integer getCount() {
//		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(clazz.getName())
//				.append(" WHERE company.id = :companyId");
//
//		Long countData = (Long) ConnHandler.getManager().createQuery(sb.toString())
//				.setParameter("companyId", SessionHelper.getSelectedCompanyId()).getSingleResult();
//
//		Integer count = countData.intValue();
//
//		return count;
//	}

	/* get count by company without session */
	protected Integer getCountWithoutSession(String companyId) {
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(clazz.getName())
				.append(" WHERE company.id = :companyId");

		Long countData = (Long) ConnHandler.getManager().createQuery(sb.toString()).setParameter("companyId", companyId)
				.getSingleResult();

		Integer count = countData.intValue();

		return count;
	}

	private List<Field> getAllBaseField(final T entity) {
		Class<?> base = entity.getClass().getSuperclass();
		List<Field> list = new ArrayList<Field>();
		list.addAll(Arrays.asList(base.getDeclaredFields()));

		while (true) {
			try {
				base = base.getSuperclass();
				list.addAll(Arrays.asList(base.getDeclaredFields()));
			} catch (Exception e) {
				break;
			}
		}
		return list;
	}

//	public T initAdminAdd(final T entity) throws Exception {
//		List<Field> listField = getAllBaseField(entity);
//		String user = "admin";
//		LocalDateTime localDateTime = LocalDateTime.now();
//		for (Field field : listField) {
//			field.setAccessible(true);
//			if (field.getName().equals("createdAt")) {
//				field.set(entity, localDateTime);
//			} else if (field.getName().equals("createdBy")) {
//				field.set(entity, user);
//			} else if (field.getName().equals("updatedAt")) {
//				field.set(entity, localDateTime);
//			} else if (field.getName().equals("updatedBy")) {
//				field.set(entity, user);
//			} else if (field.getName().equals("version")) {
//				field.set(entity, 0L);
//			} else if (field.getName().equals("isActive")) {
//				if (field.get(entity) == null) {
//					field.set(entity, true);
//				}
//			} else if (field.getName().equals("trxNumber")) {
//				if (field.get(entity) == null) {
//					try {
//						Field trxCode = entity.getClass().getSuperclass().getDeclaredField("trxCode");
//						trxCode.setAccessible(true);
//						Field trxDate = entity.getClass().getSuperclass().getDeclaredField("trxDate");
//						trxDate.setAccessible(true);
//						field.set(entity, TransactionHelper.generateTrxNumber(SessionHelper.getSelectedCompanyId(),
//								((TrxCode) trxCode.get(entity)).getId(), (LocalDate) trxDate.get(entity)));
//					} catch (Exception e) {
//						throw new Exception("Transaction Code doesn't have autonumber setted");
//					}
//				}
//			}
//		}
//		return entity;
//	}

//	public void adminAdd(final T entity) throws Exception {
//		ConnHandler.getManager().persist(initAdminAdd(entity));
//	}

	protected void save(final T entity, Callback before, Callback after) throws Exception {
		if (before != null)
			before.exec();

		Field id = getBaseClass(entity).getDeclaredField("id");
		id.setAccessible(true);

		Object objId = id.get(entity);

		if (objId != null) {
			T data = getById(id.get(entity).toString());
			setField(entity, data, false);
			em().merge(entity);
		} else {
			setField(entity, null, true);
			em().persist(entity);
		}
		if (after != null)
			after.exec();
	}

	protected void save(final T entity, Callback before, Callback after, boolean autoCommit) throws Exception {
		if (autoCommit) {
			begin();
			save(entity, before, after);
			commit();
		} else
			save(entity, before, after);
	}

	protected void save(final T entity, Callback before, Callback after, boolean autoCommit, boolean autoRollback)
			throws Exception {
		try {
			save(entity, before, after, autoCommit);
		} catch (Exception e) {
			if (autoRollback)
				rollback();

			throw new Exception(e);
		}
	}
	
	private void setField(final T entity, T data, boolean isAdd) throws Exception {
		List<Field> listField = getAllBaseField(entity);
		for (Field field : listField) {
			if (field.getName().equals("version")) {
				if (isAdd)
					field.set(entity, 0L);
				else {
					valVersion(entity, data);
					Object obj = field.get(data);
					field.set(entity, Long.parseLong(String.valueOf(obj)) + 1);
				}

				break;
			}
		}
	}

//	protected void beforeAdd(final T entity) throws Exception {
//	};

//	private void mainAdd(final T entity) throws Exception {
//		List<Field> listField = getAllBaseField(entity);
//		MobileUser user = SessionHelper.getLoginUser();
//		LocalDateTime localDateTime = LocalDateTime.now();
//		for (Field field : listField) {
//			field.setAccessible(true);
//			if (field.getName().equals("createdAt")) {
//				field.set(entity, localDateTime);
//			} else if (field.getName().equals("createdBy")) {
//				field.set(entity, user.getUsername());
//			} else if (field.getName().equals("updatedAt")) {
//				field.set(entity, localDateTime);
//			} else if (field.getName().equals("updatedBy")) {
//				field.set(entity, user.getUsername());
//			} else if (field.getName().equals("version")) {
//				field.set(entity, 0L);
//			} else if (field.getName().equals("isActive")) {
//				if (field.get(entity) == null) {
//					field.set(entity, true);
//				}
//			} else if (field.getName().equals("trxNumber")) {
//				if (field.get(entity) == null) {
//					try {
//						Field trxCode = entity.getClass().getSuperclass().getDeclaredField("trxCode");
//						trxCode.setAccessible(true);
//						Field trxDate = entity.getClass().getSuperclass().getDeclaredField("trxDate");
//						trxDate.setAccessible(true);
//						field.set(entity, TransactionHelper.generateTrxNumber(SessionHelper.getSelectedCompanyId(),
//								((TrxCode) trxCode.get(entity)).getId(), (LocalDate) trxDate.get(entity)));
//					} catch (Exception e) {
//						throw new Exception("Transaction Code doesn't have autonumber setted");
//					}
//				}
//			}
//		}
//		ConnHandler.getManager().persist(entity);
//	}

//	protected void afterAdd(final T entity) throws Exception {
//	}

//	public T edit(final T entity, Callback before, Callback after) throws Exception {
//		if (before != null) before.exec();
//		final T returnEntity = mainEdit(entity);
//		if (after != null) after.exec();
//		return returnEntity;
//	}

//	protected void beforeEdit(final T entity) throws Exception {
//	}

//	private T mainEdit(final T entity) throws Exception {
//		Field id = getBaseClass(entity).getDeclaredField("id");
//		id.setAccessible(true);
//		T data = getById(id.get(entity).toString());
//
//		List<Field> listField = getAllBaseField(entity);
//		
//		MobileUser user = SessionHelper.getLoginUser();
//		for (Field updateField : listField) {
//			updateField.setAccessible(true);
//			if (updateField.getName().equals("updatedAt")) {
//				updateField.set(entity, LocalDateTime.now());
//			} else if (updateField.getName().equals("updatedBy")) {
//				updateField.set(entity, user.getUsername());
//			} else if (updateField.getName().equals("version")) {
//				valVersion(entity, data);
//				Object o6 = updateField.get(data);
//				updateField.set(entity, Long.parseLong(String.valueOf(o6)) + 1);
//			} else if (updateField.getName().equals("isActive")) {
//				if (updateField.get(entity) == null) {
//					Field isActive = getBaseClass(data).getDeclaredField("isActive");
//					isActive.setAccessible(true);
//					updateField.set(entity, isActive.get(data));
//				}
//			} else if (updateField.getName().equals("createdAt")) {
//				Field createdAt = getBaseClass(data).getDeclaredField("createdAt");
//				createdAt.setAccessible(true);
//				updateField.set(entity, createdAt.get(data));
//			} else if (updateField.getName().equals("createdBy")) {
//				Field createdBy = getBaseClass(data).getDeclaredField("createdBy");
//				createdBy.setAccessible(true);
//				updateField.set(entity, createdBy.get(data));
//			}
//		}
//		return ConnHandler.getManager().merge(entity);
//	}

//	protected void afterEdit(final T entity) throws Exception {
//	}

//	public T adminEdit(final T entity) throws Exception {
//		Field id = getBaseClass(entity).getDeclaredField("id");
//		id.setAccessible(true);
//		T data = getById(id.get(entity).toString());
//
//		List<Field> listField = getAllBaseField(entity);
//
//		String user = "admin";
//		for (Field updateField : listField) {
//			updateField.setAccessible(true);
//			if (updateField.getName().equals("updatedAt")) {
//				updateField.set(entity, LocalDateTime.now());
//			} else if (updateField.getName().equals("updatedBy")) {
//				updateField.set(entity, user);
//			} else if (updateField.getName().equals("version")) {
//				valVersion(entity, data);
//				Object o6 = updateField.get(data);
//				updateField.set(entity, Long.parseLong(String.valueOf(o6)) + 1);
//			} else if (updateField.getName().equals("isActive")) {
//				if (updateField.get(entity) == null) {
//					Field isActive = getBaseClass(data).getDeclaredField("isActive");
//					isActive.setAccessible(true);
//					updateField.set(entity, isActive.get(data));
//				}
//			} else if (updateField.getName().equals("createdAt")) {
//				Field createdAt = getBaseClass(data).getDeclaredField("createdAt");
//				createdAt.setAccessible(true);
//				updateField.set(entity, createdAt.get(data));
//			} else if (updateField.getName().equals("createdBy")) {
//				Field createdBy = getBaseClass(data).getDeclaredField("createdBy");
//				createdBy.setAccessible(true);
//				updateField.set(entity, createdBy.get(data));
//			}
//		}
//		return ConnHandler.getManager().merge(entity);
//	}

	protected void delete(final T entity, Callback before, Callback after) throws Exception {
		if (before != null)
			before.exec();

		mainDelete(entity);

		if (after != null)
			after.exec();
	}

	protected void delete(final T entity, Callback before, Callback after, boolean autoCommit) throws Exception {
		if (autoCommit) {
			begin();
			delete(entity, before, after);
			commit();
		} else
			delete(entity, before, after);
	}

	protected void delete(final T entity, Callback before, Callback after, boolean autoCommit, boolean autoRollback)
			throws Exception {
		try {
			delete(entity, before, after, autoCommit);
		} catch (Exception e) {
			if (autoRollback)
				rollback();
		}
	}

	protected void deleteById(final Object entityId) throws Exception {
		T entity = null;
		if (entityId != null && entityId instanceof String) {
			entity = getById((String) entityId);
		}

		if (entity != null)
			delete(entity, null, null);
		else
			throw new Exception("ID Not Found");
	}

//	protected void deleteById(final String entityId) throws Exception {
//		final T entity = getById(entityId);
//		delete(entity, null, null);
//	}

	private void mainDelete(final T entity) throws Exception {
		em().remove(entity);
	}

//	protected void beforeDelete(final T entity) throws Exception {
//	}

//	protected void afterDelete(final T entity) throws Exception {
//	}

	protected boolean isIdExist(final String entityId) {
		if (getById(entityId) == null) {
			return false;
		} else {
			return true;
		}
	}

	protected boolean isIdExistAndActive(final String entityId) throws Exception {
		T data = getById(entityId);
		Field field;
		try {
			field = data.getClass().getSuperclass().getDeclaredField("isActive");
			field.setAccessible(true);
			if (field.get(data).equals(false)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	private void valVersion(T updatedEntity, T storedEntity) throws Exception {
		Field upedEntityVer = getBaseClass(updatedEntity).getDeclaredField("version");
		upedEntityVer.setAccessible(true);
		Field storEntityVer = getBaseClass(storedEntity).getDeclaredField("version");
		storEntityVer.setAccessible(true);
		if (upedEntityVer.get(updatedEntity) == null) {
			throw new Exception(" - Version is Required");
		} else if ((long) upedEntityVer.get(updatedEntity) != (long) storEntityVer.get(storedEntity)) {
			throw new Exception(" - Data Not Up-to-date");
		}
	}

//	public void versionUp(final T entity) throws Exception {
//		Field field = entity.getClass().getSuperclass().getDeclaredField("id");
//		field.setAccessible(true);
//		T originalEntity = getById((String) field.get(entity));
//
//		field = originalEntity.getClass().getSuperclass().getDeclaredField("version");
//		field.setAccessible(true);
//		Long version = (Long) field.get(originalEntity);
//		version++;
//		field.set(originalEntity, version);
//		ConnHandler.getManager().merge(originalEntity);
//	}

	/*
	 * ===================================================== tambahan fero
	 * ======================================================
	 */

//	public void confirm (final T entity, Employee requester, String notes) throws Exception{
//		ApprovalHelper.confirm( (BaseRequestOrder) entity,requester,notes);
//	}
//	
//	public void cancel (final T entity, Employee requester, String notes) throws Exception{
//		ApprovalHelper.cancel( (BaseRequestOrder) entity,requester,notes);
//	}
//	
//	public void reject (final T entity, Employee requester, String notes) throws Exception{
//		ApprovalHelper.refuse( (BaseRequestOrder) entity,requester,notes);
//	}
//	
//	public void correction (final T entity, Employee requester, String notes) throws Exception{
//		ApprovalHelper.correction( (BaseRequestOrder) entity,requester,notes);
//	}

//	public T editStatus(final T entity) throws Exception {
//		Field id = getBaseClass(entity).getDeclaredField("id");
//		id.setAccessible(true);
//		T data = getById(id.get(entity).toString());
//
//		List<Field> listField = getAllBaseField(entity);
//		MobileUser user = SessionHelper.getLoginUser();
//		for (Field updateField : listField) {
//			updateField.setAccessible(true);
//			if (updateField.getName().equals("updatedAt")) {
//				updateField.set(entity, LocalDateTime.now());
//			} else if (updateField.getName().equals("updatedBy")) {
//				updateField.set(entity, user.getUsername());
//			} else if (updateField.getName().equals("version")) {
//				valVersion(entity, data);
//				Object o6 = updateField.get(data);
//				updateField.set(entity, Long.parseLong(String.valueOf(o6)) + 1);
//			} else if (updateField.getName().equals("isActive")) {
//				if (updateField.get(entity) == null) {
//					Field isActive = getBaseClass(data).getDeclaredField("isActive");
//					isActive.setAccessible(true);
//					updateField.set(entity, isActive.get(data));
//				}
//			} else if (updateField.getName().equals("createdAt")) {
//				Field createdAt = getBaseClass(data).getDeclaredField("createdAt");
//				createdAt.setAccessible(true);
//				updateField.set(entity, createdAt.get(data));
//			} else if (updateField.getName().equals("createdBy")) {
//				Field createdBy = getBaseClass(data).getDeclaredField("createdBy");
//				createdBy.setAccessible(true);
//				updateField.set(entity, createdBy.get(data));
//			}
//		}
//		return ConnHandler.getManager().merge(entity);
//	}

	protected EntityManager em() {
		return ConnHandler.getManager();
	}

	protected void begin() {
		ConnHandler.begin();
	}

	protected void commit() {
		ConnHandler.commit();
	}

	protected void rollback() {
		ConnHandler.rollback();
	}

	protected void clear() {
		ConnHandler.clear();
	}

	protected <C> TypedQuery<C> createQuery(String sql, Class<C> clazz) {
		return em().createQuery(sql, clazz);
	}

	protected Query createNativeQuery(String sql) {
		return em().createNativeQuery(sql);
	}
}