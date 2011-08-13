/* GanttFusionchartProperty.java

	Purpose:
		
	Description:
		
	History:
		Mar 21, 2011 2:58:19 PM, Created by jimmy

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

This program is distributed under GPL Version 3.0 in the hope that
it will be useful, but WITHOUT ANY WARRANTY.
 */
package org.zkoss.fusionchart.config;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.impl.Utils;

/**
 * The GanttFusionchartProperty lets you control a variety of functional elements on the chart. 
 * For example, you can opt to show/hide data labels, data values, 
 * y-axis values. You can also set chart limits and extended properties.
 * <p/>
 * All property of chart, please refer to the following reference document.
 * <br/><a href="http://www.fusioncharts.com/widgets/docs/Contents/GanttXML.html">Gantt Chart</a>.
 * 
 * 
 * @author jimmy
 *
 */
public class GanttChartConfig extends ChartConfig {

	private static final long serialVersionUID = 20110321153310L;

	public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

	private GanttChartHeaderConfig _headerConfig;
	private GanttChartCategoriesConfig _catesConfig;
	
	private ProcessConfig _procConfig;
	private GanttChartSeriesConfig _sersConfig;
	private ChartProperties _taskProps;
	private TrendLineConfig _trendLineConfig;
	private MilestoneConfig _mstoneConfig;
	
	private GanttTableConfig _tableConfig;
	
	/**
	 * Returns the canvas background color. 
	 * @return String
	 */
	public String getCanvasBgColor() {
		return getProperty("canvasBgColor");
	}

	/**
	 * Sets the canvas background color. 
	 * For Gradient effect, enter colors separated by comma.
	 * @param canvasBgColor
	 */
	public void setCanvasBgColor(String canvasBgColor) {
		addProperty("canvasBgColor", Utils.toFusionchartColor(canvasBgColor));
	}

	/**
	 * Returns the alpha for Canvas Background. 
	 * @return Number
	 */
	public int getCanvasBgAlpha() {
		return Utils.getInt(getProperty("canvasBgAlpha"));
	}

	/**
	 * Sets the alpha for Canvas Background. 
	 * For gradient, enter alpha list separated by commas.
	 * @param canvasBgAlpha
	 */
	public void setCanvasBgAlpha(Number canvasBgAlpha) {
		addProperty("canvasBgAlpha", String.valueOf(canvasBgAlpha));
	}

	/*config*/
	/**
	 * Return the process config.
	 */
	public ProcessConfig getProcessConfig() {
		if (_procConfig == null) {
			_procConfig = new ProcessConfig();
			addPropertyListener((ChartInfoNotifier)_procConfig);
		}
		return _procConfig;
	}
	
	/**
	 * Return the tasks properties.
	 */
	public ChartProperties getTasksProperties() {
		if (_taskProps == null) {
			_taskProps = new ChartPropertiesImpl();
			addPropertyListener(_taskProps);
		}
		return _taskProps;
	}
	
	/**
	 * Return the series config.
	 */
	public GanttChartSeriesConfig getSeriesConfig() {
		if (_sersConfig == null) {
			_sersConfig = new GanttChartSeriesConfig();
			addPropertyListener(_sersConfig);
		}
		return _sersConfig;
	}
	
	/**
	 * Return the header config.
	 */
	public GanttChartHeaderConfig getHeaderConfig() {
		if (_headerConfig == null) {
			_headerConfig = new GanttChartHeaderConfig();
			addPropertyListener((ChartInfoNotifier)_headerConfig);
		}
		return _headerConfig;
	}
	
	/**
	 * Return the categories config.
	 */
	public GanttChartCategoriesConfig getCategoriesConfig() {
		if (_catesConfig == null) {
			_catesConfig = new GanttChartCategoriesConfig();
			addPropertyListener(_catesConfig);
		}
		return _catesConfig;
	}

	/**
	 * Return the trend line config.
	 */
	public TrendLineConfig getTrendLineConfig() {
		if (_trendLineConfig == null) {
			_trendLineConfig = new TrendLineConfig();
			addPropertyListener((ChartInfoNotifier)_trendLineConfig);
		}
		return _trendLineConfig;
	}
	
	public MilestoneConfig getMilestoneConfig() {
		if (_mstoneConfig == null) {
			_mstoneConfig = new MilestoneConfig();
			addPropertyListener((ChartInfoNotifier)_mstoneConfig);
		}
		return _mstoneConfig;
	}
	
	public GanttTableConfig getTableConfig() {
		if (_tableConfig == null) {
			_tableConfig = new GanttTableConfig();
			addPropertyListener((ChartInfoNotifier)_tableConfig);
		}
		return _tableConfig;
	}
	
	/*package*/ GanttChartHeaderConfig headerConfig() {
		return _headerConfig;
	}
	
	/*package*/ GanttChartCategoriesConfig categoriesConfig() {
		return _catesConfig;
	}
	
	/*package*/ ProcessConfig processConfig() {
		return _procConfig;
	}
	
	/*package*/ ChartProperties tasksProperties() {
		return _taskProps;
	}
	
	/*package*/ GanttChartSeriesConfig seriesConfig() {
		return _sersConfig;
	}
	
	/*package*/ TrendLineConfig trendLineConfig() {
		return _trendLineConfig;
	}
	
	/*package*/ MilestoneConfig milestoneConfig() {
		return _mstoneConfig;
	}
	
	/*package*/ GanttTableConfig tableConfig() {
		return _tableConfig;
	}
}
