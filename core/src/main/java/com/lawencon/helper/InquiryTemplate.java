package com.lawencon.helper;

import java.util.LinkedHashMap;
import java.util.Set;

public class InquiryTemplate {
	private String select;
	private String inquiry;
	private String fields;
	private String views;
	private LinkedHashMap<String, LinkedHashMap<String, Object>> conditions;
	private int page;
	private int limit;

	public InquiryTemplate() {
	}

	public InquiryTemplate(String select, String inquiry, String fields, String views,
			LinkedHashMap<String, LinkedHashMap<String, Object>> conditions, int page, int limit) {
		this.select = select;
		this.inquiry = inquiry;
		this.fields = fields;
		this.views = views;
		this.conditions = conditions;
		setPage(page);
		setLimit(limit);
	}

	public InquiryTemplate(String select) {
		this.select = select;
	}

	public InquiryTemplate(String select, String views) {
		this.select = select;
		this.views = views;
	}

	public InquiryTemplate(String select, String inquiry, String fields, String views, int page, int limit) {
		this.select = select;
		this.inquiry = inquiry;
		this.fields = fields;
		this.views = views;
		setPage(page);
		setLimit(limit);
	}

	public void addCondition(String operator, String field, Object condition) {
		this.conditions = new LinkedHashMap<>();
		LinkedHashMap<String, Object> container = new LinkedHashMap<>();
		container.put("operator", operator);
		container.put("condition", condition);
		this.conditions.put(field, container);
	}

	public void addCondition(String operator, String field, Object condition, String connector) {
		this.conditions = new LinkedHashMap<>();
		LinkedHashMap<String, Object> container = new LinkedHashMap<>();
		container.put("operator", operator);
		container.put("condition", condition);
		container.put("connector", connector);
		this.conditions.put(field, container);
	}

	public boolean isAnyCondition() {
		if (conditions == null) {
			return false;
		} else {
			return true;
		}
	}

	public Set<String> getConditionKeys() {
		return conditions.keySet();
	}

	public LinkedHashMap<String, Object> getCondition(String key) {
		return conditions.get(key);
	}

	public String getInquiry() {
		return inquiry;
	}

	public void setInquiry(String inquiry) {
		this.inquiry = inquiry;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public LinkedHashMap<String, LinkedHashMap<String, Object>> getConditions() {
		return conditions;
	}

	public void setConditions(LinkedHashMap<String, LinkedHashMap<String, Object>> conditions) {
		this.conditions = conditions;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page < 0) {
			this.page = 0;
		} else {
			this.page = page;
		}
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if (limit < 0) {
			this.limit = 0;
		} else {
			this.limit = limit;
		}
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}
}
