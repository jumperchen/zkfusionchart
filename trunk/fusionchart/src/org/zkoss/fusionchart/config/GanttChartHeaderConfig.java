/* GanttChartCategoryConfig.java

	Purpose:
		
	Description:
		
	History:
		Jun 20, 2011 10:26:59 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.*;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.lang.Objects;
import org.zkoss.zul.event.ChartDataEvent;

/**
 * @author jimmy
 *
 */
public class GanttChartHeaderConfig extends PropertiesMapProperties {
	private static final long serialVersionUID = 20110620222710L;
	
	private static final Map DEFAULT_FORMAT = new HashMap();
	static {
		DEFAULT_FORMAT.put(new Integer(Calendar.DAY_OF_MONTH), "MMM-dd-yyyy");
		DEFAULT_FORMAT.put(new Integer(Calendar.MONTH), "MMM-yyyy");
		DEFAULT_FORMAT.put(new Integer(Calendar.YEAR), "yyyy");
	}
	
	public static final String DEFAULT_MONTH_FORMAT = 
		(String) DEFAULT_FORMAT.get(new Integer(Calendar.MONTH));
	public static final int DEFAULT_PERIOD = Calendar.MONTH;
	
	private String dateFormater = (String) DEFAULT_FORMAT.get(new Integer(Calendar.MONTH));
	private boolean _showDateHeader = true;// render date column by ZK.
	private int _period = Calendar.MONTH;
	
	/**
	 * Returns the dateformat for all of header.
	 * @return String
	 */
	public String getDateFormater() {
		return dateFormater;
	}

	/**
	 * Sets the dateformat for all of header.
	 * @param dateFormater
	 */
	public void setDateFormater(String dateFormater) {
		if (!Objects.equals(this.dateFormater, dateFormater)) {
			this.dateFormater = dateFormater;
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}

	/**
	 * Sets whether to show the date column.
	 * <p><b>Gantt chart only.</b>
	 * @param showDateHeader
	 */
	public void setShowDateHeader(boolean showDateHeader) {
		if (this._showDateHeader != showDateHeader) {
			this._showDateHeader = showDateHeader;
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
	
	/**
	 * Returns whether to show the date column.
	 * <p><b>Gantt chart only.</b>
	 * @return boolean
	 */
	public boolean isShowDateHeader() {
		return _showDateHeader;
	}
	
	/** Returns the period used in header. The value can be
	 * Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR.
	 * default is Calendar.MONTH if not specified.
	 */
	public int getPeriod() {
		return _period;
	}
	
	/** Sets the period used in header. The value can be
	 * Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR.
	 */
	public void setPeriod(int period) {
		if (period != _period) {
			_period = period;
			dateFormater = (String) DEFAULT_FORMAT.get(new Integer(period));
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
	
	/**
	 * Create the header properties.
	 * 
	 * @return GanttChartCategoryProperties
	 */
	public ChartProperties createHeaderProperties(Date start, Date end) {
		return super.createProperties(getKey(start, end), new ChartPropertiesImpl());
	}
	
	/**
	 * Create the header properties.
	 * 
	 * @return GanttChartCategoryProperties
	 */
	public ChartProperties createHeaderProperties(int index) {
		return super.createProperties(new Integer(index), new ChartPropertiesImpl());
	}
	
	/**
	 * Returns the header properties.
	 * @param start
	 * @param end
	 * @return ChartProperties
	 */
	public ChartProperties getHeaderProperties(Date start, Date end) {
		return super.getProperties(getKey(start, end));
	}

	/**
	 * Returns the header properties at the specified position.
	 * 
	 * @param index
	 * @return ChartProperties
	 */
	public ChartProperties getHeaderProperties(int index) {
		return getProperties(new Integer(index));
	}

	/**
	 * Remove the header properties properties.
	 * @param start
	 * @param end
	 */
	public void removeHeaderProperties(Date start, Date end) {
		super.removeProperties(getKey(start, end));
	}

	/**
	 * Remove the header properties at the specified position.
	 * 
	 * @param index
	 */
	public void removeHeaderProperties(int index) {
		super.removeProperties(new Integer(index));
	}

	/**
	 * Remove all of header property.
	 */
	public void clearAllHeaderProperties() {
		super.clearAllProperties();
	}
	
	public final static List getKey(Date start, Date end) {
		return Arrays.asList(new Comparable[] { Utils.toFusionchartDate(start),
				Utils.toFusionchartDate(end) });
	}
}
