package com.tatcha.jscripts.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriverException;

import com.tatcha.jscripts.TcConstants;
import com.tatcha.jscripts.dao.TestCase;

public class TatchaException extends Exception {

	/**
	 * Customized Exception class to hold all exception
	 */
	private final static Logger logger = Logger.getLogger(TatchaException.class);

	public TatchaException(Exception exp, List<TestCase> tcList) {
		String REMARKS = "";
		if (exp instanceof TimeoutException) {
			REMARKS = "Time Out";
		} else if (exp instanceof FileNotFoundException) {
			REMARKS = "File Not Found";

		} else if (exp instanceof IOException) {
			REMARKS = "IO Error";

		} else if (exp instanceof IllegalArgumentException) {
			REMARKS = "Illegal Argument";

		} else if (exp instanceof NoSuchElementException) {
			REMARKS = "Element Not Found";

		} else if (exp instanceof ElementNotVisibleException) {
			REMARKS = "Element Not Visible";

		} else if (exp instanceof StaleElementReferenceException) {
			REMARKS = "Stale Element Reference";

		} else if (exp instanceof WebDriverException) {
			REMARKS = "Web Driver Issue";

		} else if (exp instanceof IllegalArgumentException) {
			REMARKS = "Illegal Argument";

		} else if (exp instanceof NullPointerException) {
			REMARKS = "Null Value";

		}else {
			REMARKS = "Unknown Exception";
		}
		
		TestCase tc = new TestCase(TcConstants.TC_ERR, TcConstants.MOC_ERR, TcConstants.FUN_ERR, TcConstants.FAIL,
				REMARKS);
		tcList.add(tc);
		logger.error(exp.toString());

	}

}
