/* CategoriesProperties.java

	Purpose:
		
	Description:
		
	History:
		Jun 12, 2011 10:55:43 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import org.zkoss.fusionchart.api.ChartProperties;

/**
 * @author jimmy
 * 
 */
public class CategoriesConfig extends PropertiesMapProperties {
	private static final long serialVersionUID = 20110612225640L;

	/**
	 * Create the category properties.
	 * 
	 * @param category
	 * @return ChartProperties
	 */
	public ChartProperties createCategoryProperties(Comparable category) {
		return super.createProperties(category, new ChartPropertiesImpl());
	}

	/**
	 * Create the category properties in the specified position.
	 * 
	 * @param index
	 * @return ChartProperties
	 */
	public ChartProperties createCategoryProperties(int index) {
		return createCategoryProperties(new Integer(index));
	}

	/**
	 * Returns the category properties.
	 * 
	 * @param category
	 * @return ChartProperties
	 */
	public ChartProperties getCategoryProperties(Comparable category) {
		return super.getProperties(category);
	}

	/**
	 * Returns the category properties in the specified position.
	 * 
	 * @param index
	 * @return ChartProperties
	 */
	public ChartProperties getCategoryProperties(int index) {
		return getCategoryProperties(new Integer(index));
	}

	/**
	 * Remove the category properties.
	 * 
	 * @param category
	 */
	public void removeCategoryProperties(Comparable category) {
		super.removeProperties(category);
	}

	/**
	 * Remove the category properties in the specified position.
	 * 
	 * @param index
	 */
	public void removeCategoryProperties(int index) {
		removeCategoryProperties(new Integer(index));
	}

	/**
	 * Remove all of category properties.
	 */
	public void clearAllCategoryProperties() {
		super.clearAllProperties();
	}
}
