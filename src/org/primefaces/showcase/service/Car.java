package org.primefaces.showcase.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Car {

	String RandomId;
	int getRandomYear ;
	String randomColor ;
	String randomBrand ;
	int randomPrice; 
	boolean randomSoldState;
	List<String> colors;
	List<String> brands;
	String brand;
	String color;
	String id;
	String year;

	
	 
	public Car(String RandomId, String randomColor ,int getRandomYear ,String randomBrand ,int randomPrice, boolean randomSoldState)
			
	{
		
	}



	public String getRandomId() {
		return RandomId;
	}



	public void setRandomId(String randomId) {
		RandomId = randomId;
	}



	public int getGetRandomYear() {
		return getRandomYear;
	}



	public void setGetRandomYear(int getRandomYear) {
		this.getRandomYear = getRandomYear;
	}



	public String getRandomColor() {
		return randomColor;
	}



	public void setRandomColor(String randomColor) {
		this.randomColor = randomColor;
	}



	public String getRandomBrand() {
		return randomBrand;
	}



	public void setRandomBrand(String randomBrand) {
		this.randomBrand = randomBrand;
	}



	public int getRandomPrice() {
		return randomPrice;
	}



	public void setRandomPrice(int randomPrice) {
		this.randomPrice = randomPrice;
	}



	public boolean isRandomSoldState() {
		return randomSoldState;
	}



	public void setRandomSoldState(boolean randomSoldState) {
		this.randomSoldState = randomSoldState;
	}



	public List<String> getColors() {
		return colors;
	}



	public void setColors(List<String> colors) {
		this.colors = colors;
	}



	public List<String> getBrands() {
		return brands;
	}



	public void setBrands(List<String> brands) {
		this.brands = brands;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}
}
