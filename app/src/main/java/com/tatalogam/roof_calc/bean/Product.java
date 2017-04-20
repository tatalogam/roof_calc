package com.tatalogam.roof_calc.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Product {

	private Integer id;
	private String name;

	public Integer getProduct_category_id() {
		return product_category_id;
	}

	public void setProduct_category_id(Integer product_category_id) {
		this.product_category_id = product_category_id;
	}

	private Integer product_category_id;
	private List<Attribute> attribute = new ArrayList<Attribute>();
	private List<Model> model =new ArrayList<Model>();
	private List<Model> task = new ArrayList<Model>();
	private List<Price> price = new ArrayList<Price>();
	private List<Model> color = new ArrayList<Model>();
	private List<Map<String, String>> image;
	private Integer batuan;
	private Integer is_package;
	private Double coeff;
	private Integer statusId;
	private String createdAt;
	private String updatedAt;
	private Category category;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Attribute> getAttribute() {
		return attribute;
	}
	public void setAttribute(List<Attribute> attribute) {
		this.attribute = attribute;
	}
	public List<Model> getTask() {
		return task;
	}
	public void setTask(List<Model> task) {
		this.task = task;
	}
	public List<Price> getPrice() {
		return price;
	}
	public void setPrice(List<Price> price) {
		this.price = price;
	}
	public List<Model> getColor() {
		return color;
	}
	public void setColor(List<Model> color) {
		this.color = color;
	}
	public List<Map<String, String>> getImage() {
		return image;
	}
	public void setImage(List<Map<String, String>> image) {
		this.image = image;
	}
	public Integer getBatuan() {
		return batuan;
	}
	public void setBatuan(Integer batuan) {
		this.batuan = batuan;
	}
	public Double getCoeff() {
		return coeff;
	}
	public void setCoeff(Double coeff) {
		this.coeff = coeff;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Model> getModel() {
		return model;
	}
	public void setModel(List<Model> model) {
		this.model = model;
	}

	public Integer getIs_package() {
		return is_package;
	}

	public void setIs_package(Integer is_package) {
		this.is_package = is_package;
	}
}