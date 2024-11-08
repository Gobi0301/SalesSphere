/**
 * 
 */
package com.scube.crm.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author User
 * 
 */
public class MapSorting {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K extends Comparable, V extends Comparable> Map<K, V> sortByKey(
			Map<K, V> mapList) {
		if (null != mapList && mapList.size() > 0) {
			List<K> keys = new LinkedList<K>(mapList.keySet());
			Collections.sort(keys);
			// LinkedHashMap will keep the keys in the order they are inserted
			// which is currently sorted on natural ordering
			Map<K, V> sortedMap = new LinkedHashMap<K, V>();
			for (K key : keys) {
				sortedMap.put(key, mapList.get(key));
			}
			return sortedMap;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(
			Map<K, V> mapList) {

		if (null != mapList && mapList.size() > 0) {
			List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(
					mapList.entrySet());
			Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
				@SuppressWarnings("unchecked")
				@Override
				public int compare(Entry<K, V> o1, Entry<K, V> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});

			// LinkedHashMap will keep the keys in the order they are inserted
			// which is currently sorted on natural ordering
			Map<K, V> sortedMap = new LinkedHashMap<K, V>();

			for (Map.Entry<K, V> entry : entries) {
				sortedMap.put(entry.getKey(), entry.getValue());
			}
			return sortedMap;
		}
		return null;

	}
}