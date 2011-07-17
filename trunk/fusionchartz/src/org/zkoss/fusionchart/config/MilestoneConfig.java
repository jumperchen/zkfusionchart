/* MilestoneConfig.java

	Purpose:
		
	Description:
		
	History:
		Jul 3, 2011 11:02:22 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.zul.GanttModel.GanttTask;

/**
 * @author jimmy
 *
 */
public class MilestoneConfig extends PropertiesListProperties {
	private static final long serialVersionUID = 20110703234710L;
	private Map taskPropMap = new HashMap(20);

	/**
	 * Add a trend lines on the chart.
	 * 
	 * @return Trend
	 */
	public ChartProperties createMilestone(Date date, GanttTask task, String color) {
		ChartPropertiesImpl milestone = new ChartPropertiesImpl();
		addProperty("date", Utils.toFusionchartDate(date));
		milestone.addProperty("color", Utils.toFusionchartColor(color));
		taskPropMap.put(milestone, task);
		return super.createProperties(milestone);
	}

	/**
	 * Return the trend line.
	 * 
	 * @param index
	 * @return Trend
	 */
	public ChartProperties getMilestone(int index) {
		return super.getProperties(index);
	}
	
	/*package*/ GanttTask getTask(int index) {
		return (GanttTask) taskPropMap.get(getMilestone(index));
	}
	
	/**
	 * Remove the trend line on the chart.
	 * 
	 * @param milestone
	 */
	public void removeMilestone(ChartProperties milestone) {
		taskPropMap.remove(milestone);
		super.removeProperties(milestone);
	}

	/**
	 * Remove the trend line on the chart in the specified position.
	 * 
	 * @param index
	 */
	public void removeMilestone(int index) {
		removeMilestone(getMilestone(index));
	}

	/**
	 * Clear all of trend lines.
	 */
	public void clearAllMilestone() {
		taskPropMap.clear();
		super.clearAllProperties();
	}
}
