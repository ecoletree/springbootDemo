/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : boadl
 * Create Date : 2022. 4. 14
 * File Name : CommonConst.java
 * DESC : 
*****************************************************************/
package com.example.demo.service.common;

public class CommonConst {

	public final class DataBaseConst {
		public static final String UPDATE = "U";
		public static final String INSERT = "I";
		public static final String DELETE = "D";
	}
	
	public final class RateTypeConst {
		public static final String NORMAL = "NORMAL"; // 일반 
		public static final String LONG = "LONG";  // 시외
		public static final String INTER = "INTER"; // 국제
	}
	
	public final class CodePrefixConst {
		public static final String RATE = "RATE";
		public static final String RATE_UUID = "RATEUUID";
		public static final String CERTIFICATE = "CERT";
	}
	
	public final class USER_TYPE {
		public static final String SYSTEM = "system";
		public static final String GROUP = "group";
		public static final String CENTER = "center";
		public static final String TEAM = "team";
	}
	
	
}

