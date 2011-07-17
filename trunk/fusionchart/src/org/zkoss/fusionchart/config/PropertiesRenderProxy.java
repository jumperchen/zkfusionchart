/* PropertiesRenderProxy.java

	Purpose:
		
	Description:
		
	History:
		Jul 4, 2011 10:43:59 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.Collection;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.config.GanttChartCategoriesConfig.GanttChartCategoriesProperties;
import org.zkoss.fusionchart.config.GanttTableConfig.GanttTableColumnConfig;
import org.zkoss.fusionchart.config.XYChartConfig.XAxisConfig;
import org.zkoss.zul.GanttModel.GanttTask;

/**
 * @author jimmy
 * 
 */
public class PropertiesRenderProxy {

	public final static ChartProperties getProperties(PropertiesMapHandler handler,
			int index, Object key) {
		ChartProperties properties = handler.getProperties(new Integer(index));
		if (properties == null)
			properties = handler.getProperties(key);

		return properties;
	}

	/* CategoryChartConfig */
	public final static SeriesConfig getSeriesConfig(CategoryChartConfig config) {
		return config.seriesConfig();
	}

	public final static CategoriesConfig getCategoryConfig(CategoryChartConfig config) {
		return config.categoryConfig();
	}

	public final static TrendLineConfig getTrendLineConfig(CategoryChartConfig config) {
		return config.trendLineConfig();
	}

	/* XAxisConfig */
	public final static XAxisConfig getXAxisConfig(XYChartConfig config) {
		return config.xAxisConfig();
	}

	/* PieChartConfig */
	public final static CategoriesConfig getCategoryConfig(PieChartConfig config) {
		return config.categoryConfig();
	}

	/* GanttChartConfig */
	public final static ProcessConfig getProcessConfig(GanttChartConfig config) {
		return config.processConfig();
	}

	public final static ChartProperties getTasksProperties(GanttChartConfig config) {
		return config.tasksProperties();
	}
	
	public final static GanttChartSeriesConfig getSeriesConfig(GanttChartConfig config) {
		return config.seriesConfig();
	}

	public final static GanttChartHeaderConfig getHeaderConfig(
			GanttChartConfig config) {
		return config.headerConfig();
	}
	
	public final static GanttChartCategoriesConfig getCategoriesConfig(
			GanttChartConfig config) {
		return config.categoriesConfig();
	}
	
	public final static Collection getCategoryKeys(
			GanttChartCategoriesProperties props) {
		return props.getKeys();
	}

	public final static TrendLineConfig getTrendLineConfig(GanttChartConfig config) {
		return config.trendLineConfig();
	}
	
	public final static MilestoneConfig getMilestoneConfig(GanttChartConfig config) {
		return config.milestoneConfig();
	}
	
	public final static GanttTask getTask(int index, MilestoneConfig congig) {
		return (GanttTask) congig.getTask(index);
	}
	
	public final static GanttTableColumnConfig getColumnConfig(GanttTableConfig config) {
		return config.columnConfig();
	}
}
