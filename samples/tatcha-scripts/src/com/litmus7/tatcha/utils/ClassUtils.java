package com.litmus7.tatcha.utils;

/**
 * 
 * Contains Tatcha code utility functions
 * 
 * @author titus/Litmus7
 * 
 */
public class ClassUtils {

	/** 
	 * Singleton class
	 */
	private static ClassUtils instance = null;

	private ClassUtils() {
	}

	public static ClassUtils getInstance() {
		if (instance == null)
			instance = new ClassUtils();
		return instance;
	}

	/**
	 * primary Key
	 * 
	 * @return
	 */
	public int getPK() {
		int range = 10000;
		int min = 100001;

		int pk = (int) (Math.random() * range) + min;
		return pk;
	}
}
