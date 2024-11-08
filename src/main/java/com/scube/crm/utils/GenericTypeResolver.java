package com.scube.crm.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.scube.crm.exception.MySalesException;

public final class GenericTypeResolver<T> {
	/**
	 * Resolves the generic parameterized type <code>Class<T></code> of the
	 * generic type <code>T</code>.
	 * 
	 * 
	 * @param clazz
	 *            a Class
	 * @return the generic parameterized typed class
	 * @throws MySalesException
	 */

	public Class<T> resolveGenericType(Class<?> clazz)
			throws MySalesException {
		Class<T> ret = null;
		Type[] types;
		Class<?>[] clazzes;

		// has this interface or class a Generic Type?
		types = clazz.getTypeParameters();
		ret = this.resolveTypes(types);
		if (ret != null) {
			return ret;
		}

		// No? Test extenedd interfaces
		types = clazz.getGenericInterfaces();
		clazzes = clazz.getInterfaces();
		ret = this.resolveTypes(types);
		if (ret != null) {
			return ret;
		}
		// Not found yet: start recursion
		for (final Class<?> clazze : clazzes) {
			try {
				ret = this.resolveGenericType(clazze);
				return ret;
			} catch (final RuntimeException rex) {
				//
			}
		}

		throw new MySalesException("Can't resolve Type");

	}

	/**
	 * Resolves the first matching generic parameterized type
	 * <code>Class<T></code> for a given array of <code>Type</code>.
	 * 
	 * Matching means:
	 * <p>
	 * <code>
	 * (types[i] instanceof ParameterizedType) && 
	 *    (Class<T>)types[i] // throws no ClassCastException
	 * </code>
	 * 
	 * @param types
	 *            array of Types
	 * @return the first castable class
	 */
	@SuppressWarnings("unchecked")
	public Class<T> resolveTypes(Type[] types) {
		Class<T> ret;

		for (final Type type : types) {
			if (type instanceof ParameterizedType) {
				final ParameterizedType pType = (ParameterizedType) type;
				final Type[] pTypes = pType.getActualTypeArguments();
				for (final Type pType2 : pTypes) {
					try {
						ret = (Class<T>) pType2;
						return ret;
					} catch (final ClassCastException ccex) {
						//
					}
				}
			}
		}
		return null;
	}
}
