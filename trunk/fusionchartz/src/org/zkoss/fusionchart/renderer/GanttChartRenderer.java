/* GanttFusionchartRenderer.java

	Purpose:
		
	Description:
		
	History:
		Mar 21, 2011 5:34:48 PM, Created by jimmy

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

This program is distributed under GPL Version 3.0 in the hope that
it will be useful, but WITHOUT ANY WARRANTY.
 */
package org.zkoss.fusionchart.renderer;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.config.ChartConfig;
import org.zkoss.fusionchart.config.ChartInfoNotifier;
import org.zkoss.fusionchart.config.GanttChartCategoriesConfig;
import org.zkoss.fusionchart.config.GanttChartCategoriesConfig.GanttChartCategoriesProperties;
import org.zkoss.fusionchart.config.GanttChartConfig;
import org.zkoss.fusionchart.config.GanttChartHeaderConfig;
import org.zkoss.fusionchart.config.GanttChartSeriesConfig;
import org.zkoss.fusionchart.config.GanttChartSeriesConfig.GanttChartSeriesProperties;
import org.zkoss.fusionchart.config.MilestoneConfig;
import org.zkoss.fusionchart.config.ProcessConfig;
import org.zkoss.fusionchart.config.PropertiesRenderProxy;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.zul.GanttModel;


/**
 * The GanttFusionchartRenderer was used to render 
 * the Fusionchart XML String with {@link GanttModel}.
 * <p/>
 * All property of chart, please refer to the following reference document.
 * <br/><a href="http://www.fusioncharts.com/widgets/docs/Contents/GanttXML.html">Gantt Chart</a>.
 * @author jimmy
 * 
 */
public abstract class GanttChartRenderer extends AbstractFusionchartRenderer {

	private static final long serialVersionUID = 201103211705010L;
	
	private final GanttChartConfig _chartConfig;
	
	public GanttChartRenderer() {
		defineChartProperty(_chartConfig = new GanttChartConfig());
		addPropertyListener((ChartInfoNotifier)_chartConfig);
	}
	
	public ChartConfig getChartConfig() {
		return _chartConfig;
	}
	
	public abstract void defineChartProperty(final GanttChartConfig chartConfig);
	
	public void renderChildTages(StringBuffer sb) {
		CategoryChartRenderer.renderTrendLine(sb,
				PropertiesRenderProxy.getTrendLineConfig(_chartConfig));
	}
	
	public void renderProcessesProperty(StringBuffer sb) {
		Utils.renderChartProperties(sb, 
				PropertiesRenderProxy.getProcessConfig(_chartConfig));
	}
	
	public void renderProcessProperty(StringBuffer sb, int index, String taskName) {
		ProcessConfig config = PropertiesRenderProxy.getProcessConfig(_chartConfig);
		if (config != null)
			Utils.renderChartProperties(sb, 
					PropertiesRenderProxy.getProperties(config, index, taskName));
	}
	
	public void renderTasksProperty(StringBuffer sb) {
		Utils.renderChartProperties(sb,
				PropertiesRenderProxy.getTasksProperties(_chartConfig));
	}
	
	public void renderTaskProperty(StringBuffer sb, int seriesIndex, int taskIndex,
			Comparable series, String taskName) {
		
		GanttChartSeriesConfig config = 
			PropertiesRenderProxy.getSeriesConfig(_chartConfig);
		if (config == null) return;
		
		ChartProperties sProperties = 
			PropertiesRenderProxy.getProperties(config, seriesIndex, series);
		
		if (sProperties instanceof GanttChartSeriesProperties) {
			//render task properties
			Utils.renderChartProperties(sb, 
					PropertiesRenderProxy.getProperties(
							(GanttChartSeriesProperties) sProperties, taskIndex, taskName));
			
			//render series properties
			Utils.renderChartProperties(sb, sProperties);
		}
	}
	
	/*category*/
	public void renderHeadersProperty(StringBuffer sb) {
		Utils.renderChartProperties(sb, 
				PropertiesRenderProxy.getHeaderConfig(_chartConfig));
	}
	
	public void renderHeaderProperty(StringBuffer sb, int index, Date start,
			Date end, TimeZone tz) {
		GanttChartHeaderConfig config = 
			PropertiesRenderProxy.getHeaderConfig(_chartConfig);
		if (config != null)
			Utils.renderChartProperties(sb, PropertiesRenderProxy.getProperties(
					config, index, GanttChartHeaderConfig.getKey(start, end)));
	}
	
	public void renderCategories(StringBuffer sb) {
		GanttChartCategoriesConfig config = 
			PropertiesRenderProxy.getCategoriesConfig(_chartConfig);
		
		for (int i = 0, j = config.size(); i < j; i++) {
			GanttChartCategoriesProperties props = config.getCategoriesProperties(i);
			sb.append("<categories");
			Utils.renderChartProperties(sb, props).append(">");
			Collection keys = PropertiesRenderProxy.getCategoryKeys(props);
			
			for (Iterator it = keys.iterator(); it.hasNext();) {
				List key = (List) it.next();
				ChartProperties cProps = 
					props.getCategoryProperties((String) key.get(0), (Date) key.get(1), (Date) key.get(2));
				sb.append("<category");
				Utils.renderChartProperties(sb, cProps).append("/>");
			}
			
			sb.append("</categories>");
		}
	}
	
	public void renderMilestone(StringBuffer sb, Map taskIDMap) {
		MilestoneConfig config = 
			PropertiesRenderProxy.getMilestoneConfig(_chartConfig);
		
		if (config == null) return;
		
		int size = config.size();
		if (size == 0) return;
		
		sb.append("<milestones");
		Utils.renderChartProperties(sb, config).append(">");
		
		for (int i = 0; i < size; i++) {
			ChartProperties milestone = config.getMilestone(i);
			sb.append("<milestone taskId='")
				.append(taskIDMap.get(PropertiesRenderProxy.getTask(i, config)))
				.append("'");
			
			Utils.renderChartProperties(sb, milestone).append("/>");
		}
		sb.append("</milestones>");
	}	
	
	/*package*/ String getHeaderFormater() {
		GanttChartHeaderConfig config = 
			PropertiesRenderProxy.getHeaderConfig(_chartConfig);
		
		if (config != null)
			return config.getDateFormater();
		return GanttChartHeaderConfig.DEFAULT_MONTH_FORMAT;
	}
	
	/*package*/ int getHeaderPeriod() {
		GanttChartHeaderConfig config = 
			PropertiesRenderProxy.getHeaderConfig(_chartConfig);
		
		if (config != null)
			return config.getPeriod();
		return GanttChartHeaderConfig.DEFAULT_PERIOD;
	}
}
