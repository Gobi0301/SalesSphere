package com.scube.crm.dao;

import java.util.Date;
import java.util.List;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.BODTO;


/**
 * This class to be form an common function for this application
 * 
 * @author Scube Technologies
 * @param <E>
 * 
 */

public interface GenericDAO<E> {

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
	void create(List<BODTO<E>> list) throws MySalesException;

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
	void deleteAll(List<BODTO<E>> entityList) throws MySalesException;

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
	void update(List<BODTO<E>> list) throws MySalesException;

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

	E findById(Long id) throws MySalesException;

	/**
	 * Retrieve All entity from the database.
	 * 
	 * @param entity
	 * @throws MySalesException
	 *             ;
	 */
	List<E> retrieveAll() throws MySalesException;



}
