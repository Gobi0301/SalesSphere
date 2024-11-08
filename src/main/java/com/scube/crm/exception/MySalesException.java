package com.scube.crm.exception;

import java.util.Locale;

import com.scube.crm.utils.BundleHelper;

public class MySalesException extends java.lang.Exception {

	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_RESOURCE_BUNDLE_NAME = "/com/jobtrolley/resources/jobtrolley-messages";

	private String bundleName = MySalesException.DEFAULT_RESOURCE_BUNDLE_NAME;
	private String key;
	private Object[] parameters;
	private String errorCode;
	private String errorMessage;

	private Class<?> bundleResolver;

	/**
	 * Constructs an instance of <code>MySalesException</code> with the
	 * specified detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 * @param resourcekey
	 *            the key to load the translated resource
	 * @param parameters
	 *            the array of objects, which are formatted into the translated
	 *            message, resolved from the resource key; the array can be
	 *            null, if no parameters are used
	 */
	public MySalesException(String msg, String resourcekey,
			Object[] parameters) {
		super(msg);
		this.key = resourcekey;
		this.parameters = parameters;
	}

	/**
	 * 
	 * @param ErrorCode
	 * @param ErrorMessage
	 */

	public MySalesException(String errorCode, String errorMessage) {

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * Constructs an instance of <code>MySalesException</code> with the
	 * specified original exception.
	 * 
	 * @param t
	 *            the original exception.
	 * @param resourcekey
	 *            the key to load the translated resource
	 * @param parameters
	 *            the array of objects, which are formatted into the translated
	 *            message, resolved from the resource key; the array can be
	 *            null, if no parameters are used
	 */
	public MySalesException(Throwable t, String resourcekey,
			Object[] parameters) {
		super(t);
		this.key = resourcekey;
		this.parameters = parameters;
	}

	/**
	 * Constructs an instance of <code>MySalesException</code> with the
	 * specified detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 * @param resourcekey
	 *            the key to load the translated resource
	 * @param parameters
	 *            the array of objects, which are formatted into the translated
	 *            message, resolved from the resource key; the array can be
	 *            null, if no parameters are used
	 * @param bundleName
	 */
	public MySalesException(String msg, String resourcekey,
			Object[] parameters, String bundleName) {
		super(msg);
		this.key = resourcekey;
		this.parameters = parameters;
		this.bundleName = bundleName;
	}

	/**
	 * Constructs an instance of <code>MySalesException</code> with the
	 * specified original exception.
	 * 
	 * @param t
	 *            the original exception.
	 * @param resourcekey
	 *            the key to load the translated resource
	 * @param parameters
	 *            the array of objects, which are formatted into the translated
	 *            message, resolved from the resource key; the array can be
	 *            null, if no parameters are used
	 * @param bundleName
	 */
	public MySalesException(Throwable t, String resourcekey,
			Object[] parameters, String bundleName) {
		super(t);
		this.key = resourcekey;
		this.parameters = parameters;
		this.bundleName = bundleName;
	}

	public MySalesException() {

	}

	public MySalesException(String message, String key, Object[] parameters,
			Class<?> bundleResolver) {
		this(message, key, parameters);
		this.setBundleResolver(bundleResolver);
	}

	public MySalesException(String string) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method resolves the key from a predefined ResourceBundle. The
	 * resolved String may contain additional formatter information. Sample: If
	 * the resolved String is <code>"%2$s %1$s"<code>
	 * and the parameters are <code>{"a", "b"}</code>, the String returned by
	 * <code>resolve</code> is <code>"a b"</code>.
	 * 
	 * If the resource bundle file cannot be found, the key is null or the key
	 * cannot be resolved message is returned directly.
	 * 
	 * @param key
	 *            the key to be resolved
	 * @param message
	 *            the message returned, if the key cannot be resolved
	 * @param parameters
	 *            the parameters to be formatted into the resolved key can be
	 *            null
	 * @return the resolved String
	 */
	protected String resolve(String message, String key, Object[] parameters) {
		return this.resolve(message, key, parameters, Locale.getDefault());
	}

	/**
	 * This method resolves the key from a predefined ResourceBundle. The
	 * resolved String may contain additional formatter information. Sample: If
	 * the resolved String is <code>"%2$s %1$s"<code>
	 * and the parameters are <code>{"a", "b"}</code>, the String returned by
	 * <code>resolve</code> is <code>"a b"</code>.
	 * 
	 * If the resource bundle file cannot be found, the key is null or the key
	 * cannot be resolved message is returned directly.
	 * 
	 * @param key
	 *            the key to be resolved
	 * @param message
	 *            the message returned, if the key cannot be resolved
	 * @param parameters
	 *            the parameters to be formatted into the resolved key can be
	 *            null
	 * @param locale
	 *            the locale to use for i18n
	 * @return the resolved String
	 */
	protected String resolve(String message, String key, Object[] parameters,
			Locale locale) {
		return BundleHelper.resolve(message, key, parameters,
				this.getBundleName(), this.getBundleResolver(), locale);
	}

	/**
	 * Creates a localized description of this throwable.
	 * 
	 * @see #resolve(String,String,Object[])
	 * @return The localized description of this throwable.
	 */
	@Override
	public String getLocalizedMessage() {
		return this.resolve(this.getMessage(), this.key, this.parameters);
	}

	/**
	 * Creates a description of this Exception.
	 * 
	 * @return The description of this throwable.
	 */
	@Override
	public String getMessage() {
		String msg = super.getMessage();
		if (msg == null || msg.length() == 0) {
			msg = this.key + ", " + this.resolve("", this.key, this.parameters);
		}
		return msg;
	}

	/**
	 * Creates a localized description of this throwable.
	 * 
	 * @see #resolve(String,String,Object[])
	 * @return The localized description of this throwable.
	 */
	public String getLocalizedMessage(Locale locale) {
		return this.resolve(this.getMessage(), this.key, this.parameters,
				locale);
	}

	public String getKey() {
		return this.key;
	}

	public Object[] getParameters() {
		return this.parameters;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getBundleName() {
		return this.bundleName;
	}

	/**
	 * Set to a class (normally within the package that contains the properties
	 * file and often an empty class called BundleResolver) which can be used
	 * for loading the property file
	 * 
	 * @param bundleResolver
	 */

	public void setBundleResolver(Class<?> bundleResolver) {
		this.bundleResolver = bundleResolver;
	}

	/**
	 * Get the class (normally within the package that contains the properties
	 * file and often an empty class called BundleResolver) which can be used
	 * for loading the property file
	 * 
	 * @return
	 */

	public Class<?> getBundleResolver() {
		return this.bundleResolver;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
