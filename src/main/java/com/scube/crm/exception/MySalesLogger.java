package com.scube.crm.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 
 * @author Scube Technologies
 * 
 */
public class MySalesLogger {

	/**
	 * Logger Variable declaration
	 */
	private transient Logger logger;
	/**
	 * Entry Message Variable Declaration
	 */
	private static final String ENTRY_MESSAGE = "Entering method {}()";
	/**
	 * Exit Message Variable Declaration
	 */
	private static final String EXIT_MESSAGE = "Exiting method {}()";
	/**
	 * Name key variable declaration
	 */
	private static final String NAME_KEY = "method";

	/**
	 * @param loggerName
	 *            Name of the logger (as a String) for which the logging needs
	 *            to be performed.
	 */

	protected MySalesLogger(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	/**
	 * @param clazzName
	 *            Class object for the class for which the logging needs to be
	 *            performed.
	 */

	protected MySalesLogger(Class<?> clazzName) {
		this.logger = LoggerFactory.getLogger(clazzName);
	}

	// getLogger methods
	/**
	 * Description :Access method to retrieve the initialized instance of the
	 * logging component for a particular logger.
	 * 
	 * @param loggerName
	 *            Name of the logger (as a String) for which the logging needs
	 *            to be performed.
	 */

	public static MySalesLogger getLogger(String loggerName) {
		return new MySalesLogger(loggerName);
	}

	/**
	 * Description :Access method to retrieve the initialized instance of the
	 * logging component for a particular logger.
	 * 
	 * @param clazzName
	 *            Class object for the class for which the logging needs to be
	 *            performed.
	 */

	public static MySalesLogger getLogger(Class<?> clazzName) {
		return new MySalesLogger(clazzName);
	}

	/**
	 * Description :Purpose of this Function is to check whether Debug Level
	 * logging is enabled or not
	 * 
	 * @return true if Debug level logging is enabled for this logger, false
	 *         otherwise
	 * 
	 */
	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	/**
	 * Description :Purpose of this Function is to check whether info Level
	 * logging is enabled or not
	 * 
	 * @return true if info level logging is enabled for this logger, false
	 *         otherwise
	 * 
	 */
	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 * 
	 */
	public void debug(Object logMsg, Object... placeholders) {
		if (this.logger.isDebugEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.debug(String.valueOf(logMsg), placeholders);
			} else {
				this.logger.debug(String.valueOf(logMsg));
			}
		}
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 * @param logCause
	 *            An instance of Throwable that needs to be logged along with
	 *            the corresponding stacke trace.
	 */
	public void debug(Object logMsg, Throwable logCause, Object... placeholders) {
		if (this.logger.isDebugEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.debug(String.valueOf(logMsg), placeholders,
						logCause);
			} else {
				this.logger.debug(String.valueOf(logMsg), logCause);
			}
		}
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 */
	public void info(Object logMsg, Object... placeholders) {
		if (this.logger.isInfoEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.info(String.valueOf(logMsg), placeholders);
			} else {
				this.logger.info(String.valueOf(logMsg));
			}
		}
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 * @param logCause
	 *            An instance of Throwable that needs to be logged along with
	 *            the corresponding stacke trace.
	 */
	public void info(Object logMsg, Throwable logCause, Object... placeholders) {
		if (this.logger.isInfoEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger
						.info(String.valueOf(logMsg), logCause, placeholders);
			} else {
				this.logger.info(String.valueOf(logMsg), logCause);
			}
		}
	}

	/**
	 * Parameters :It takes only one parameter "logging Message" of type Object
	 * 
	 * Modification Log Date Author Description
	 */
	public void warn(Object logMsg, Object... placeholders) {
		if (this.logger.isWarnEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.warn(String.valueOf(logMsg), placeholders);
			} else {
				this.logger.warn(String.valueOf(logMsg));
			}
		}
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 * @param logCause
	 *            An instance of Throwable that needs to be logged along with
	 *            the corresponding stacke trace.
	 */
	public void warn(Object logMsg, Throwable logCause, Object... placeholders) {
		if (this.logger.isWarnEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger
						.warn(String.valueOf(logMsg), logCause, placeholders);
			} else {
				this.logger.warn(String.valueOf(logMsg), logCause);
			}
		}
	}

	/**
	 * Parameters :It takes only one parameter "logging Message" of type Object
	 * 
	 * Return Value :NIL Modification Log Date Author Description
	 */

	public void error(Object logMsg, Object... placeholders) {
		if (this.logger.isErrorEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.error(String.valueOf(logMsg), placeholders);
			} else {
				this.logger.error(String.valueOf(logMsg));
			}
		}
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 * @param logCause
	 *            An instance of Throwable that needs to be logged along with
	 *            the corresponding stacke trace.
	 * 
	 */
	public void error(Object logMsg, Throwable logCause, Object... placeholders) {
		if (this.logger.isErrorEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.error(String.valueOf(logMsg), logCause,
						placeholders);
			} else {
				this.logger.error(String.valueOf(logMsg), logCause);
			}
		}
	}

	/**
	 * Parameters :It takes only one parameter "logging Message" of type Object
	 * 
	 * Return Value :NIL
	 * 
	 * Modification Log Date Author Description
	 */

	public void fatal(Object logMsg, Object... placeholders) {
		if (this.logger.isErrorEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.error(String.valueOf(logMsg), placeholders);
			} else {
				this.logger.error(String.valueOf(logMsg));
			}
		}
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 * @param logCause
	 *            An instance of Throwable that needs to be logged along with
	 *            the corresponding stacke trace.
	 * 
	 */
	public void fatal(Object logMsg, Throwable logCause, Object... placeholders) {
		if (this.logger.isErrorEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.error(String.valueOf(logMsg), logCause,
						placeholders);
			} else {
				this.logger.error(String.valueOf(logMsg), logCause);
			}
		}
	}

	/**
	 * Parameters :It takes only one parameter "logging Message" of type Object
	 * 
	 * Return Value :NIL
	 * 
	 * Modification Log Date Author Description
	 * 
	 */

	public void trace(Object logMsg, Object... placeholders) {
		if (this.logger.isTraceEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.trace(String.valueOf(logMsg), placeholders);
			} else {
				this.logger.trace(String.valueOf(logMsg));
			}
		}
	}

	/**
	 * @param logMsg
	 *            Message to be logged. Usually passed as a String, but accepts
	 *            any object.
	 * @param logCause
	 *            An instance of Throwable that needs to be logged along with
	 *            the corresponding stacke trace.
	 */
	public void trace(Object logMsg, Throwable logCause, Object... placeholders) {
		if (this.logger.isTraceEnabled()) {
			this.addMethodNameToMDC();
			if (placeholders != null && placeholders.length > 0) {
				this.logger.trace(String.valueOf(logMsg), logCause,
						placeholders);
			} else {
				this.logger.trace(String.valueOf(logMsg), logCause);
			}
		}
	}

	/* Application useful methods */

	/***************************************************************************
	 * Description : Method to log the entry point in any method without passing
	 * any inputs. Method name is automatically deduced using reflection. Note:
	 * This method should not be used in interceptors.
	 **************************************************************************/
	public void entry() {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(MySalesLogger.ENTRY_MESSAGE,
					this.getCallingMethodName());
		}
	}

	/***************************************************************************
	 * Description : Method to log the exit point in any method without passing
	 * any inputs. Method name is automatically deduced using reflection. Note:
	 * This method should not be used in interceptors.
	 **************************************************************************/
	public void exit() {
		if (this.logger.isDebugEnabled()) {
			this.logger
					.debug(MySalesLogger.EXIT_MESSAGE, this.getCallingMethodName());
		}

	}

	/**
	 * Adds the caller method's name on the MDC for the current thread. This is
	 * used in the logging pattern to ensure that the method name is printed out
	 * in the logs.
	 */
	private void addMethodNameToMDC() {
		MDC.put(MySalesLogger.NAME_KEY, this.getCallingMethodName());
	}

	/**
	 * Deduces the method name from the logger's class that appears first on the
	 * stack trace.
	 * 
	 * @return String Name of the method calling this logging method.
	 */
	private String getCallingMethodName() {
		String methodName = "<unknown>";
		boolean foundClass = false;
		final StackTraceElement[] elements = new MySalesException()
				.getStackTrace();
		for (final StackTraceElement element : elements) {
			if (element.getClassName().contains(this.logger.getName())) {
				methodName = element.getMethodName();
				foundClass = true;
				break;
			}
		}
		if (foundClass) {
			return methodName;
		} else {
			return "";
		}
	}
}