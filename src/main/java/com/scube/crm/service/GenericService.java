package com.scube.crm.service;

import java.util.Date;
import java.util.List;

import com.scube.crm.exception.MySalesException;


public interface GenericService<E> {

	/**
	 * Creates a new entity and copies properties of given entity.
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */
	E create(E entity) throws MySalesException;

	/**
	 * Create list of new entity and copies properties of given entity.
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */
	/*void create(List<BODTO<E>> list) throws MySalesException;*/

	/**
	 * Deletes given entity from database
	 * 
	 * @param entity
	 * @throws MySalesException
	 */
	void delete(Long id) throws MySalesException;

	/**
	 * Deletes given list of entity from database
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */
	void deleteAll(List<E> entityList) throws MySalesException;

	/**
	 * Updates given entity to the database
	 * 
	 * @param entity
	 * @throws MySalesException
	 */
	void update(E entity) throws MySalesException;

	/**
	 * Updates given list of entity to the database
	 * 
	 * @param entity
	 * @throws MySalesException
	 */
	/*void update(List<BODTO<E>> list) throws MySalesException;*/

	/**
	 * Retrieve entity with given criteria.
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */

	E findByCriteria(E entity) throws MySalesException;

	/**
	 * Retrieve entity with given name String.
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */

	E findByParam(String entityParam, String entityParamValue)
			throws MySalesException;

	/**
	 * Retrieve entity with given date.
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */

	List<E> findByDate(Date fDate, Date tDate) throws MySalesException;

	/**
	 * Retrieve entity with given id.
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */

	E findById(Long long1) throws MySalesException;

}
