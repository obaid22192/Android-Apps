package com.golfapp.vistex;

import java.io.Serializable;

public class GolfCourse implements Serializable{
	
   private int  idcourse;
   private String name;
   private String country;
   private String city;
   private String zipcode;
   private String address;
   private String provience;
   private String website;
   private Double lat;
   private Double lng;
   private int holecount;
   
   //contructor
   public GolfCourse(  int  idcourse1,
    String name1,
    String country1,
    String city1,
    String zipcode1,
    String address1,
    String provience1,
    String website1,
    Double lat1,
    Double lng1,
    int holecount1)
   {
	idcourse = idcourse1;
	name = name1;
	country = country1;
	city = city1;
	zipcode = zipcode1;
	address= address1;
	provience = provience1;
	website = website1;
	lat= lat1;
	lng = lng1;
	holecount = holecount1;
			
	   
   }

public int getIdcourse() {
	return idcourse;
}

public void setIdcourse(int idcourse) {
	this.idcourse = idcourse;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getZipcode() {
	return zipcode;
}

public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getProvience() {
	return provience;
}

public void setProvience(String provience) {
	this.provience = provience;
}

public String getWebsite() {
	return website;
}

public void setWebsite(String website) {
	this.website = website;
}

public Double getLat() {
	return lat;
}

public void setLat(Double lat) {
	this.lat = lat;
}

public Double getLng() {
	return lng;
}

public void setLng(Double lng) {
	this.lng = lng;
}

public int getHolecount() {
	return holecount;
}

public void setHolecount(int holecount) {
	this.holecount = holecount;
}
public void setdistence(float distance)
{
	city = city +"  "+ distance +" KM";
}
}

