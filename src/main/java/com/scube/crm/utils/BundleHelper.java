package com.scube.crm.utils;

import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.scube.crm.exception.MySalesLogger;

public class BundleHelper {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(BundleHelper.class);

	public static final String resolve(Class<?> resolver, String key) {
		String bundleName = "Bundle";
		if (resolver == null) {
			return "";
		}
		final Package pack = resolver.getPackage();
		if (pack != null) {
			bundleName = pack.getName() + ".Bundle";
		}
		return BundleHelper.resolve(resolver, bundleName, key, new String[0],
				Locale.getDefault());
	}

	public static final String resolve(Class<?> resolver, String key,
			Locale locale) {
		String bundleName = "Bundle";
		if (resolver == null) {
			return "";
		}
		final Package pack = resolver.getPackage();
		if (pack != null) {
			bundleName = pack.getName() + ".Bundle";
		}
		return BundleHelper.resolve(resolver, bundleName, key, new String[0],
				locale);
	}

	public static final String resolve(Class<?> resolver, String bundleName,
			String key, Object[] params, Locale locale) {

		// assertion
		if (key == null) {
			return key;
		}

		if (resolver == null) {
			return "";
		}

		ResourceBundle bundle;
		String formatString;
		String s = "";

		try {
			bundle = ResourceBundle.getBundle(bundleName, locale,
					resolver.getClassLoader());
			// resolving key
			s = bundle.getString(key);
			formatString = String.format(s, params);
			return formatString;
		} catch (final MissingResourceException ex) {
			if (BundleHelper.LOGGER.isDebugEnabled()) {
				BundleHelper.LOGGER.error("MissingResourceException: Key="
						+ key + " not found in bundle=" + bundleName
						+ ", Resolver=" + resolver.getCanonicalName() + ", "
						+ ex.getMessage());
			}
			return key;
		} catch (final IllegalFormatException ex) {
			if (BundleHelper.LOGGER.isDebugEnabled()) {
				BundleHelper.LOGGER.error("IllegalFormatException: Value=" + s
						+ ", " + ex.getMessage());
			}
			return key;
		}
	}

	/**
	 * Parses and returns Locale by given String localString like
	 * <code>en_EN</code> or <code>de_DE</code>.
	 * 
	 * @return Locale
	 */
	public static Locale getLocale(String localString)
			throws IllegalArgumentException {
		Locale ret;

		final String[] locstr = localString.split("_");
		switch (locstr.length) {
		case 1:
			ret = new Locale(locstr[0]);
			break;
		case 2:
			ret = new Locale(locstr[0], locstr[1]);
			break;
		case 3:
			ret = new Locale(locstr[0], locstr[1], locstr[2]);
			break;
		default:
			throw new IllegalArgumentException(
					"Could not parse locale from string: " + localString);
		}

		return ret;

	}

	public static String resolve(String message, String key,
			Object[] parameters, String bundleName, Class<?> bundleResolver,
			Locale locale) {
		
		return null;
	}
}
