/* SeriesConfig.java

	Purpose:
		
	Description:
		
	History:
		Jun 12, 2011 11:16:33 PM, Created by jimmyshiau

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
public class SeriesConfig extends PropertiesMapHandler {
	private static final long serialVersionUID = 20110612231740L;

	/**
	 * Create the series properties.
	 * 
	 * @param series
	 * @return SeriesProperties
	 */
	public SeriesProperties createSeriesProperties(Comparable series) {
		return (SeriesProperties) super.createProperties(series,
				new SeriesProperties());
	}

	/**
	 * Create the series properties at the specified position.
	 * 
	 * @param index
	 * @return SeriesProperties
	 */
	public SeriesProperties createSeriesProperties(int index) {
		return createSeriesProperties(new Integer(index));
	}

	/**
	 * Returns the series properties.
	 * 
	 * @param series
	 * @return SeriesProperties
	 */
	public SeriesProperties getSeriesProperties(Comparable series) {
		return (SeriesProperties) super.getProperties(series);
	}

	/**
	 * Returns the series properties at the specified position.
	 * 
	 * @param index
	 * @return SeriesProperties
	 */
	public SeriesProperties getSeriesProperties(int index) {
		return getSeriesProperties(new Integer(index));
	}

	/**
	 * Remove the series properties.
	 * 
	 * @param series
	 */
	public void removeSeriesProperties(Comparable series) {
		super.removeProperties(series);
	}

	/**
	 * Remove the series properties at the specified position.
	 * 
	 * @param index
	 */
	public void removeSeriesProperties(int index) {
		removeSeriesProperties(new Integer(index));
	}

	/**
	 * Remove all of series property.
	 */
	public void clearAllSeriesProperties() {
		super.clearAllProperties();
	}

	/**
	 * @author jimmy
	 * 
	 */
	public static class SeriesProperties extends PropertiesMapProperties {
		private static final long serialVersionUID = 20110624003210L;

		/**
		 * Create the data properties.
		 * 
		 * @param category
		 * @return ChartProperties
		 */
		public ChartProperties createDataProperties(Comparable category) {
			return super.createProperties(category, new ChartPropertiesImpl());
		}

		/**
		 * Create the data properties at the specified position.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties createDataProperties(int index) {
			return createDataProperties(new Integer(index));
		}

		/**
		 * Returns the data properties.
		 * 
		 * @param category
		 * @return ChartProperties
		 */
		public ChartProperties getDataProperties(Comparable category) {
			return super.getProperties(category);
		}

		/**
		 * Returns the data properties at the specified position.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties getDataProperties(int index) {
			return getDataProperties(new Integer(index));
		}

		/**
		 * Remove the data properties.
		 * 
		 * @param category
		 */
		public void removeDataProperties(Comparable category) {
			super.removeProperties(category);
		}

		/**
		 * Remove the data properties at the specified position.
		 * 
		 * @param index
		 */
		public void removeDataProperties(int index) {
			removeDataProperties(new Integer(index));
		}

		/**
		 * Remove all of data property.
		 */
		public void clearAllDataProperties() {
			super.clearAllProperties();
		}

	}
}
