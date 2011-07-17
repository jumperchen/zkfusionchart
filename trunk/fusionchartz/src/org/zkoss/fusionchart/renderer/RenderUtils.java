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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.api.FusionchartRenderer;
import org.zkoss.fusionchart.config.GanttChartHeaderConfig;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.lang.Objects;
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
	public final static PieChartRenderer getPieChartRenderer(
			FusionchartRenderer renderer) {
		if (renderer instanceof PieChartRenderer)
			return (PieChartRenderer) renderer;
		return null;
	}
	
	public final static void renderPieSet(StringBuffer sb, int index,
			Comparable category, Number value, PieChartRenderer renderer,
			String uuid) {

		sb.append("<set name='").append(category).append("'")
			.append(Utils.renderFusionchartAttr("value", value));

		if (uuid != null)
			addClientEventInvok(uuid, sb, 0, index);
		if (renderer != null)
			renderer.renderCategoryProperty(sb, index, category);
		sb.append(" color='").append(ChartColor.getColor(index)).append("'/>");
	}
	
	/*Categories*/
	public final static CategoryChartRenderer getCategoryChartRenderer(
			FusionchartRenderer renderer) {
		if (renderer instanceof CategoryChartRenderer)
			return (CategoryChartRenderer) renderer;
		return null;
	}
	
	public final static void renderCategory(StringBuffer sb, Collection c,
			CategoryChartRenderer renderer) {
		sb.append("<categories");
		if (renderer != null)
			renderer.renderCategoriesProperty(sb);
		sb.append(">");

		int index = 0;
		for (final Iterator it = c.iterator(); it.hasNext(); index++) {
			Comparable category = (Comparable) it.next();
			sb.append("<category name='").append(category).append("'");
			if (renderer != null)
				renderer.renderCategoryProperty(sb, index, category);
			sb.append("/>");
		}
		sb.append("</categories>");
	}

	
	/*Series*/
	public final static void renderSeries(StringBuffer sb, CategoryModel model,
			CategoryChartRenderer renderer, String uuid) {
		Collection cates = model.getCategories();

		int sIndex = 0;
		for (final Iterator it = model.getSeries().iterator(); it.hasNext(); sIndex++) {
			Comparable series = (Comparable) it.next();
			renderSeriesBegin(sb, sIndex, series, renderer);
			
			int cIndex = 0;
			for (final Iterator it2 = cates.iterator(); it2.hasNext(); cIndex++) {
				Comparable category = (Comparable) it2.next();
				sb.append("<set")
					.append(Utils.renderFusionchartAttr("value", model.getValue(series, category)));

				if (uuid != null)
					addClientEventInvok(uuid, sb, sIndex, cIndex);
				if (renderer != null)
					renderer.renderDatasetProperty(sb, sIndex, cIndex, series, category);

				sb.append("/>");
			}
			sb.append("</dataset>");
		}
	}
	
	public final static void renderSeriesBegin(StringBuffer sb, int index,
			Comparable series, CategoryChartRenderer renderer) {
		sb.append("<dataset seriesName='").append(series).append("'");
		if (renderer != null)
			renderer.renderSeriesProperty(sb, index, series);
		sb.append(" color='").append(ChartColor.getColor(index)).append("'>");
	}
	
	/*XY Series*/
	public final static void renderXYSeries(StringBuffer sb, XYModel model,
			Set xSet, Map seriesMap, CategoryChartRenderer renderer, String uuid) {

		int sIndex = 0;
		for (final Iterator it = model.getSeries().iterator(); it.hasNext(); sIndex++) {
			final Comparable series = (Comparable) it.next();
			Map indexMap = (Map) seriesMap.get(series);
			renderSeriesBegin(sb, sIndex, series, renderer);

			int cIndex = 0;
			for (final Iterator it2 = xSet.iterator(); it2.hasNext();) {
				Number x = (Number) it2.next();
				Object o = indexMap.get(x);
				if (o == null) {
					sb.append("<set/>");
					continue;
				}
				
				cIndex = ((Integer)o).intValue();
				sb.append("<set")
					.append(Utils.renderFusionchartAttr("value", model.getY(series, cIndex)));
				
				if (uuid != null)
					addClientEventInvok(uuid, sb, sIndex, cIndex);
				if (renderer != null)
					renderer.renderDatasetProperty(sb, sIndex, cIndex, series, (Comparable) x);
		
				sb.append("/>");
			}
			sb.append("</dataset>");
		}
	}

	/*Gantt*/
	public final static void renderCategory(StringBuffer sb, TimeZone tz,
			Date startDate, Date endDate, GanttChartRenderer renderer) {
		String mFmt = GanttChartHeaderConfig.DEFAULT_MONTH_FORMAT;
		int period = GanttChartHeaderConfig.DEFAULT_PERIOD;
		if (renderer != null) {
			mFmt = renderer.getHeaderFormater();
			period = renderer.getHeaderPeriod();
			renderer.renderCategories(sb);
		}
		DateFormat df = new SimpleDateFormat(mFmt);
		
		//render header
		sb.append("<categories");
		if (renderer != null)
			renderer.renderHeadersProperty(sb);
		sb.append(">");
		
		if (period != Calendar.DAY_OF_MONTH) {
			startDate = Dates.beginOfMonth(startDate, tz);
			endDate = Dates.endOfMonth(endDate, tz);
		}
		
		Calendar cal = Calendar.getInstance(tz);
		cal.setTime(startDate);
		int i = 0;
		do {
			sb.append("<category name='").append(df.format(startDate)).append("'")
				.append(Utils.renderFusionchartDate("start", startDate));
			
			cal.add(period, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			sb.append(Utils.renderFusionchartDate("end", cal.getTime()));
			
			if (renderer != null)
				renderer.renderHeaderProperty(sb, i, startDate, cal.getTime(), tz);
			sb.append("/>");
			cal.add(Calendar.DAY_OF_MONTH, 1);
			startDate = cal.getTime();
			i++;
		} while (startDate.before(endDate));
		sb.append("</categories>");
	}
	
	public final static void renderProcess(StringBuffer sb, Set processSet,
			Map processIDMap, GanttChartRenderer renderer) {
		sb.append("<processes");
		if (renderer != null)
			renderer.renderProcessesProperty(sb);
		sb.append(">");
		
		int index = 0;
		for (Iterator it = processSet.iterator(); it.hasNext(); index++) {
			String taskName = (String) it.next();
			
			sb.append("<process name='").append(taskName)
				.append("' id='").append(processIDMap.get(taskName)).append("'");
		
			if (renderer != null)
				renderer.renderProcessProperty(sb, index, taskName);
			sb.append("/>");
			
		}
		sb.append("</processes>");
	}
	
	public final static void renderTasks(StringBuffer sb, GanttModel model,
			Map processIDMap, GanttChartRenderer renderer, String uuid) {
		Comparable[] allseries = model.getAllSeries();
		int seriesSize = allseries.length;
		int height = 0;
		int padding = 0;
		
		if (seriesSize > 0) {
			// 10 for spaces, 40 for bars.
			height = 40 / seriesSize;
			padding = 10 / (seriesSize + 1);
		}
		
		sb.append("<tasks");
		if (renderer != null)
			renderer.renderTasksProperty(sb);
		sb.append(">");
		
		for (int i = 0, j = allseries.length; i < j; i++) {
			final Comparable series = allseries[i];
			final GanttTask[] tasks = model.getTasks(series);
			for (int k = 0, l = tasks.length; k < l; k++) {
				GanttTask task = tasks[k];
				String taskName = task.getDescription();
				Integer processId = (Integer) processIDMap.get(taskName);
				
				sb.append("<task name='").append(series)
					.append("' id='").append(processId).append("_").append(k)
					.append("' processId='").append(processId).append("'")
					.append(Utils.renderFusionchartDate("start", task.getStart()))
					.append(Utils.renderFusionchartDate("end", task.getEnd()));
				
				if (uuid != null)
					addClientEventInvok(uuid, sb, i, k);
				if (renderer != null)
					renderer.renderTaskProperty(sb, i, k, series, taskName);
				
				sb.append(" height='").append(height).append("' topPadding='")
					.append(padding + (padding + height) * i)
					.append("' color='").append(ChartColor.getColor(i)).append("'/>");
				
			}
		}
		sb.append("</tasks>");
	}
	
	public final static void renderGenttTable(StringBuffer sb, ListModel model,
			GanttTableRenderer renderer) {
		
		if (renderer == null)
			renderer = GanttTableRenderer.getDefalutRenderer();
		
		List columnList = renderer.createColumnList(model);
		
		renderer.renderTableProperties(sb.append("<dataTable"));
		sb.append(">");
		
		int cIndex = 0;
		for (Iterator it = columnList.iterator(); it.hasNext(); cIndex++) {
			List labels = (List) it.next();
			
			renderer.renderColumnProperties(sb.append("<dataColumn"), cIndex);
			sb.append(">");
			
			int tIndex = 0;
			for (Iterator it2 = labels.iterator(); it2.hasNext(); tIndex++) {
				sb.append("<text");
				Utils.renderChartProperties(sb, 
						(ChartProperties) it2.next()).append("/>");
			}
			sb.append("</dataColumn>");
		}
		sb.append("</dataTable>");
	}
	
	/*event*/
	public static final void addClientEventInvok(String uuid, StringBuffer sb,
			int sIndex, int cIndex) {
		sb.append(" link=\"JavaScript:zk.Widget.$('").append(uuid)
				.append("').clickChart('").append(sIndex).append("','")
				.append(cIndex).append("');\"");
	}
	
}
