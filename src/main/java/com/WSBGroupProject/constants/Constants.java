package com.WSBGroupProject.constants;

public class Constants {
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String FIRSTNAME_PATTERN = "^[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]{3,}$";
	public static final String LASTNAME_PATTERN = "^[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]{3,}$";
	public static final String STREET_PATTERN = "^[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż '\"\\.]{3,}$";
	public static final String HOUSENUMBER_PATTERN = "^[A-Za-z\\d\\/\\\\]{1,15}$";
	public static final String POSTCODE_PATTERN = "^\\d{2}-\\d{3}$";
	public static final String CITY_PATTERN = "^[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]{3,}$";
	public static final String DATEOFBIRTH_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String PHONE_PATTERN = "^\\d{9}$";
	public static final String PASSWORD_PATTERN = "^(?=\\S*[0-9])(?=\\S*[a-z])(?=\\S*[A-Z])"
			+ "(?=\\S*[`~!@#$%^&*\\-_=+,<.>?;:'\"()\\[\\]{}\\|\\/\\\\])\\S{8,16}$";
	public static final String COMPANYNAME_PATTERN = "^[^;]{3,}$";
	public static final String NIP_PATTERN = "^\\d{10}$";
	
	public static final String FLOAT_PATTERN = "^\\d+(\\.\\d{1,2})?$";
	
	public static final Integer TYPE_PRIVATE = 1;
	public static final Integer TYPE_COMPANY = 2;
	public static final Integer TYPE_ADMIN = 3;

	public static final Integer SERVICE_PROVIDER = 1;
	public static final Integer SERVICE_RECIPIENT = 2;
	
	public static final Integer STATUS_AVAILABLE = 0;
	public static final Integer STATUS_ORDERED = 1;
	public static final Integer STATUS_DONE = 2;
	public static final Integer STATUS_CANCELED = 3;
}
