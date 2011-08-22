/* CategoryRender.java

	Purpose:
		
	Description:
		
	History:
		Jul 15, 2011 10:41:56 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.renderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.api.GanttTableRenderer;
import org.zkoss.fusionchart.config.CategoriesConfig;
import org.zkoss.fusionchart.config.GanttChartCategoriesConfig;
import org.zkoss.fusionchart.config.GanttChartCategoriesConfig.GanttChartCategoriesProperties;
import org.zkoss.fusionchart.config.GanttChartConfig;
import org.zkoss.fusionchart.config.GanttChartHeaderConfig;
import org.zkoss.fusionchart.config.GanttChartSeriesConfig;
import org.zkoss.fusionchart.config.GanttTableConfig;
import org.zkoss.fusionchart.config.GanttTableConfig.GanttRow;
import org.zkoss.fusionchart.config.GanttTableConfig.GanttTableColumnConfig;
import org.zkoss.fusionchart.config.GanttTableConfig.GanttTableColumnProperties;
import org.zkoss.fusionchart.config.MilestoneConfig;
import org.zkoss.fusionchart.config.ProcessConfig;
import org.zkoss.fusionchart.config.PropertiesMapHandler;
import org.zkoss.fusionchart.config.PropertiesMapProperties;
import org.zkoss.fusionchart.config.PropertiesProxy;
import org.zkoss.fusionchart.config.TrendLineConfig;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.lang.Exceptions;
import org.zkoss.util.Dates;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.GanttModel.GanttTask;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.XYModel;

/**
 * @author jimmy
 * 
 */
public class RenderUtils {
	
	/*Pie*/
	public final static void renderPieSet(StringBuffer sb, int index,
			Comparable category, Number value, CategoriesConfig config, String uuid) {

		sb.append("<set name='").append(category).append("'")
			.append(Utils.renderFusionchartAttr("value", value));
		
		if (uuid != null)
			RenderUtils.addClientEventInvok(sb, uuid, 0, index);
		
		if (config != null)
			Utils.renderChartProperties(sb, 
					PropertiesProxy.getProperties(config, index, category));
		
		sb.append(" color='").append(ChartColor.getColor(index)).append("'/>");
	}
	
	/*Categories*/
	public final static void renderCategory(StringBuffer sb, Collection c,
			PropertiesMapProperties config) {
		sb.append("<categories");
		
		
		if (config != null)
			Utils.renderChartProperties(sb, config);
		
		sb.append(">");

		int index = 0;
		for (final Iterator it = c.iterator(); it.hasNext(); index++) {
			Comparable category = (Comparable) it.next();
			sb.append("<category name='").append(Utils.escapeXML(category.toString())).append("'");
			
			if (config != null)
				Utils.renderChartProperties(sb, 
						PropertiesProxy.getProperties(
								config, index, category));
			
			sb.append("/>");
		}
		sb.append("</categories>");
	}
	
	/*Series*/
	private final static void renderSeriesBegin(StringBuffer sb, int index,
			Comparable series, PropertiesMapHandler config) {
		sb.append("<dataset seriesName='").append(Utils.escapeXML(series.toString())).append("'");
		
		if (config != null)
			Utils.renderChartProperties(sb, 
					PropertiesProxy.getProperties(config, index, series));
		
		sb.append(" color='").append(ChartColor.getColor(index)).append("'>");
	}
	
	public final static void renderSeries(StringBuffer sb, CategoryModel model,
			PropertiesMapHandler config, String uuid) {
		
		Collection cates = model.getCategories();
		
		int sIndex = 0;
		for (final Iterator it = model.getSeries().iterator(); it.hasNext(); sIndex++) {
			Comparable series = (Comparable) it.next();
			renderSeriesBegin(sb, sIndex, series, config);
			
			int cIndex = 0;
			for (final Iterator it2 = cates.iterator(); it2.hasNext(); cIndex++) {
				Comparable category = (Comparable) it2.next();
				
				renderDataset(sb, sIndex, cIndex, series, category,
						model.getValue(series, category), config, uuid);
				
			}
			sb.append("</dataset>");
		}
	}
	
	private static void renderDataset(StringBuffer sb,
			int seriesIndex, int categoryIndex, Comparable series,
			Comparable category, Number value, PropertiesMapHandler config, String uuid) {
		
		sb.append("<set")
			.append(Utils.renderFusionchartAttr("value", value));
	
		if (uuid != null)
			addClientEventInvok(sb, uuid, seriesIndex, categoryIndex);
		
		if (config != null)
			Utils.renderChartProperties(sb, 
					PropertiesProxy.getDatasetProperties(
							seriesIndex, categoryIndex, series, category, config));
	
		sb.append("/>");
	}
	
	/*XY Series*/
	public final static void renderXYSeries(StringBuffer sb, XYModel model,
			Set xSet, Map seriesMap, PropertiesMapHandler config, String uuid) {

		int sIndex = 0;
		for (final Iterator it = model.getSeries().iterator(); it.hasNext(); sIndex++) {
			final Comparable series = (Comparable) it.next();
			Map indexMap = (Map) seriesMap.get(series);
			renderSeriesBegin(sb, sIndex, series, config);

			int cIndex = 0;
			for (final Iterator it2 = xSet.iterator(); it2.hasNext();) {
				Number x = (Number) it2.next();
				Object o = indexMap.get(x);
				if (o == null) {
					sb.append("<set/>");
					continue;
				}
				cIndex = ((Integer)o).intValue();
				renderDataset(sb, sIndex, cIndex, series, (Comparable) x,
						model.getY(series, cIndex), config, uuid);
				
			}
			sb.append("</dataset>");
		}
	}

	/*Gantt*/
	public static void renderCategories(StringBuffer sb,
			GanttChartCategoriesConfig config) {
		for (int i = 0, j = config.size(); i < j; i++) {
			GanttChartCategoriesProperties props = config.getCategoriesProperties(i);
			sb.append("<categories");
			Utils.renderChartProperties(sb, props).append(">");
			Collection keys = PropertiesProxy.getCategoryKeys(props);
			
			for (Iterator it = keys.iterator(); it.hasNext();) {
				List key = (List) it.next();
				ChartProperties cProps = 
					props.getCategoryProperties((String) key.get(0), 
							(Date) key.get(1), (Date) key.get(2));
				sb.append("<category");
				Utils.renderChartProperties(sb, cProps).append("/>");
			}
			
			sb.append("</categories>");
		}
	}
	
	public final static void renderMonthHeaders(StringBuffer sb, TimeZone tz,
			Date startDate, Date endDate, GanttChartHeaderConfig config) {
		
		String mFmt = GanttChartHeaderConfig.DEFAULT_MONTH_FORMAT;
		int period = GanttChartHeaderConfig.DEFAULT_PERIOD;
		
		if (config != null) {
			mFmt = config.getDateFormater();
			period = config.getPeriod();
		}
		
		DateFormat df = new SimpleDateFormat(mFmt);
		
		//render header
		sb.append("<categories");
		
		if (config != null)
			Utils.renderChartProperties(sb, config);
		
		sb.append(">");
		
		if (period != Calendar.DAY_OF_MONTH) {
			startDate = Dates.beginOfMonth(startDate, tz);
			endDate = Dates.endOfMonth(endDate, tz);
		}
		
		Calendar cal = Calendar.getInstance(tz);
		cal.setTime(startDate);
		int i = 0;
		do {
			
			sb.append("<category name='").append(Utils.escapeXML(df.format(startDate))).append("'")
				.append(Utils.renderFusionchartDate("start", startDate));
			
			cal.add(period, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			sb.append(Utils.renderFusionchartDate("end", cal.getTime()));
			
			if (config != null)
				Utils.renderChartProperties(sb, PropertiesProxy.getProperties(
						config, i, GanttChartHeaderConfig.getKey(startDate, cal.getTime())));
			
			sb.append("/>");
			cal.add(Calendar.DAY_OF_MONTH, 1);
			startDate = cal.getTime();
			i++;
		} while (startDate.before(endDate));
		sb.append("</categories>");
	}

	public final static void renderProcess(StringBuffer sb, Set processSet,
			Map processIDMap, ProcessConfig config) {
		
		sb.append("<processes");
		
		if (config != null)
			Utils.renderChartProperties(sb, config);
		
		sb.append(">");
		
		int index = 0;
		for (Iterator it = processSet.iterator(); it.hasNext(); index++) {
			String taskName = (String) it.next();
			
			sb.append("<process name='").append(Utils.escapeXML(taskName))
				.append("' id='").append(processIDMap.get(taskName)).append("'");
		
			if (config != null)
				Utils.renderChartProperties(sb, 
						PropertiesProxy.getProperties(config, index, taskName));
			
			sb.append("/>");
			
		}
		sb.append("</processes>");
	}
	
	public final static void renderTasksBegin(StringBuffer sb,
			GanttChartConfig config) {
		sb.append("<tasks");
		if (config != null) {
			Utils.renderChartProperties(sb,
					PropertiesProxy.getTasksProperties(config));
		}
		sb.append(">");
	}
	
	public final static void renderTasks(StringBuffer sb, GanttModel model,
			Map processIDMap, GanttChartConfig config, String uuid) {
		
		Comparable[] allseries = model.getAllSeries();
		int seriesSize = allseries.length;
		int height = 0;
		int padding = 0;
		
		if (seriesSize > 0) {
			// 10 for spaces, 40 for bars.
			height = 40 / seriesSize;
			padding = 10 / (seriesSize + 1);
		}
		
		
		GanttChartSeriesConfig sConfig =  null;
		if (config != null) 
			sConfig = PropertiesProxy.getSeriesConfig(config);
		
		
		renderTasksBegin(sb, config);
		
		for (int i = 0, j = allseries.length; i < j; i++) {
			final Comparable series = allseries[i];
			final GanttTask[] tasks = model.getTasks(series);
			for (int k = 0, l = tasks.length; k < l; k++) {
				GanttTask task = tasks[k];
				String taskName = task.getDescription();
				Integer processId = (Integer) processIDMap.get(taskName);
				
				sb.append("<task name='").append(Utils.escapeXML(series.toString()))
					.append("' id='").append(processId).append("_").append(k)
					.append("' processId='").append(processId).append("'")
					.append(Utils.renderFusionchartDate("start", task.getStart()))
					.append(Utils.renderFusionchartDate("end", task.getEnd()));
				
				if (uuid != null)
					addClientEventInvok(sb, uuid, i, k);
				
				if (sConfig != null) {
					Utils.renderChartProperties(sb, 
							PropertiesProxy.getTaskProperties(
									i, k, series, taskName, sConfig));
					Utils.renderChartProperties(sb, 
							PropertiesProxy.getProperties(sConfig, i, series));
				}
				
				sb.append(" height='").append(height).append("' topPadding='")
					.append(padding + (padding + height) * i)
					.append("' color='").append(ChartColor.getColor(i)).append("'/>");
				
			}
		}
		sb.append("</tasks>");
	}
	
	public final static void renderGenttTable(StringBuffer sb, ListModel model,
			GanttTableRenderer renderer, GanttTableConfig config) {
		
		if (renderer == null)
			renderer = getDefalutRenderer();
		
		
		GanttTableColumnConfig cConfig = null;
		if (config != null)
			cConfig = PropertiesProxy.getColumnConfig(config); 
		
		
		List columnList = createColumnList(model, renderer);
		
		Utils.renderChartProperties(sb.append("<dataTable"), config).append(">");
		
		int cIndex = 0;
		for (Iterator it = columnList.iterator(); it.hasNext(); cIndex++) {
			List labels = (List) it.next();
			
			sb.append("<dataColumn");
			
			GanttTableColumnProperties cprops = null;
			if (cConfig != null) {
				cprops = cConfig.getColumnProperties(cIndex);
				Utils.renderChartProperties(sb, cprops);
			}
			
			sb.append(">");
			
			int tIndex = 0;
			for (Iterator it2 = labels.iterator(); it2.hasNext(); tIndex++) {
				sb.append("<text");
				
				if (cprops != null)
					Utils.renderChartProperties(sb, 
							cprops.getTextProperties(tIndex));
				
				Utils.renderChartProperties(sb, 
						(ChartProperties) it2.next()).append("/>");
			}
			sb.append("</dataColumn>");
		}
		sb.append("</dataTable>");
	}

	private static GanttTableRenderer getDefalutRenderer() {
		return new DefalutRenderer();
	}
	
	public static class DefalutRenderer implements GanttTableRenderer {
		private static final long serialVersionUID = 20110717110710L;
		public void render(GanttRow row, Object data) throws Exception {
			row.createLabel(String.valueOf(data));
		}
		
	}
	
	private static List createColumnList(ListModel model, GanttTableRenderer renderer) {
		List columnList = new ArrayList(4);
		for (int i = 0, j = model.getSize(); i < j; i++) {
			GanttRow row = new GanttRow();
//			addPropertyListener(row);
			try {
				renderer.render(row, model.getElementAt(i));
			} catch (Exception e) {
				row.createLabel(Exceptions.getMessage(e));
			}
			//create column list
			for (int k = 0, l = row.size(); k < l; k++) {
				List labels = null;
				try {
					labels = (List) columnList.get(k);
				} catch (IndexOutOfBoundsException e) {
					columnList.add(labels = new ArrayList(13));
				}
				labels.add(row.getCellProperties(k));
			}
		}
		return columnList;
	}

	/*event*/
	public static final StringBuffer addClientEventInvok(StringBuffer sb, String uuid,
			int sIndex, int cIndex) {
		return sb.append(" link=\"JavaScript:zk.Widget.$('").append(uuid)
				.append("').clickChart('").append(sIndex).append("','")
				.append(cIndex).append("');\"");
		
	}
	
	/*TrendLine*/
	public static final void renderTrendLine(StringBuffer sb,
			TrendLineConfig config) {
		if (config == null) return;
		
		int size = config.size();
		if (size == 0) return;
		
		Utils.renderChartProperties(
				sb.append("<trendlines"), config).append(">");
		for (int i = 0; i < size; i++)
			Utils.renderChartProperties(
				sb.append("<line"), config.getTrendLine(i))
				.append("/>");
		sb.append("</trendlines>");
	}
	
	/*Milestone*/
	public static final void renderMilestone(StringBuffer sb, Map taskIDMap,
			MilestoneConfig config) {

		if (config == null)
			return;

		int size = config.size();
		if (size == 0)
			return;

		sb.append("<milestones");
		Utils.renderChartProperties(sb, config).append(">");

		for (int i = 0; i < size; i++) {
			ChartProperties milestone = config.getMilestone(i);
			sb.append("<milestone taskId='")
					.append(taskIDMap.get(PropertiesProxy.getTask(i, config)))
					.append("'");

			Utils.renderChartProperties(sb, milestone).append("/>");
		}
		sb.append("</milestones>");
	}
}
