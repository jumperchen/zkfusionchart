/* FusionchartEngine.java

	Purpose:
		
	Description:
		
	History:
		Mar 9, 2011 2:13:47 AM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;

import org.zkoss.fusionchart.Fusionchart;
import org.zkoss.fusionchart.api.FusionchartEngine;
import org.zkoss.fusionchart.config.CategoriesConfig;
import org.zkoss.fusionchart.config.CategoryChartConfig;
import org.zkoss.fusionchart.config.ChartConfig;
import org.zkoss.fusionchart.config.GanttChartConfig;
import org.zkoss.fusionchart.config.GanttChartHeaderConfig;
import org.zkoss.fusionchart.config.GanttTableConfig;
import org.zkoss.fusionchart.config.PieChartConfig;
import org.zkoss.fusionchart.config.ProcessConfig;
import org.zkoss.fusionchart.config.PropertiesMapHandler;
import org.zkoss.fusionchart.config.PropertiesMapProperties;
import org.zkoss.fusionchart.config.PropertiesProxy;
import org.zkoss.fusionchart.config.SeriesConfig;
import org.zkoss.fusionchart.config.XYChartConfig;
import org.zkoss.fusionchart.renderer.RenderUtils;
import org.zkoss.lang.Objects;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.GanttModel.GanttTask;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.XYModel;

public class FusionChartEngine implements FusionchartEngine,
		java.io.Serializable {

	private static final long serialVersionUID = 20110319195510L;

	// caching chartImpl if type and 3d are the same
//	private transient Fusionchart _chart;
	private transient boolean _threeD;
	private transient String _type;
//	private transient ChartModel _model;
//	private transient FusionchartRenderer _renderer;
//
	private transient ChartImpl _chartImpl; // chart implementaion

	public String createChartXML(Object data) {
		Fusionchart chart = (Fusionchart) data;
		return getChartImpl(chart).createChartXML(chart);
	}

	/**
	 * create specific type of chart drawing engine. This implementation use
	 * JFreeChart engine.
	 */
	private ChartImpl getChartImpl(Fusionchart chart) {

//		if (Objects.equals(chart.getType(), _type)
//				&& _threeD == chart.isThreeD()
//				&& Objects.equals(chart.getModel(), _model)
//				&& Objects.equals(chart.getChartRenderer(), _renderer)) {
//			return _chartImpl;
//		}
//		_chart = chart;
		_type = chart.getType();
		_threeD = chart.isThreeD();
//		_model = chart.getModel();
//		_renderer = chart.getChartRenderer();

		String errMsg = "";
		String orient = chart.getOrient();

		if (Chart.PIE.equals(_type)) {
			_chartImpl = new Pie();
		} else if (Chart.BAR.equals(_type) || Chart.STACKED_BAR.equals(_type)
				|| Fusionchart.COMBINATION.equals(_type)) {
			if (_threeD && "horizontal".equals(orient))
				errMsg = orient + " " + _type;
			_chartImpl = new Bar();
		} else if (Chart.LINE.equals(_type)) {
			if (_threeD)
				errMsg = _type;
			_chartImpl = new Line();
		} else if (Chart.AREA.equals(_type)) {
			if (_threeD)
				errMsg = _type;
			_chartImpl = new AreaImpl();
		} else if (Chart.STACKED_AREA.equals(_type)) {
			if (_threeD)
				errMsg = _type;
			_chartImpl = new StackedArea();
		} else if (Chart.GANTT.equals(_type)) {
			if (_threeD)
				errMsg = _type;
			_chartImpl = new Gantt();
		} else
			throw new UiException("Unsupported chart type yet: " + _type);

		if (!Strings.isBlank(errMsg))
			throw new UiException("Unsupported chart type yet: " + errMsg
					+ " in threeD.");

		return _chartImpl;
	}

	/** Model transfer **/
	/**
	 * transfer a PieModel into Fusionchart PieDataset.
	 */
	private StringBuffer PieModelToPieDataset(StringBuffer sb, String uuid,
			PieModel model, PieChartConfig config) {
		CategoriesConfig cateConfig = config == null ?  null:
			PropertiesProxy.getCategoryConfig(config);

		int index = 0;
		for (final Iterator it = model.getCategories().iterator(); it.hasNext(); index++) {
			Comparable category = (Comparable) it.next();

			RenderUtils.renderPieSet(sb, index, category,
					model.getValue(category), cateConfig, uuid);
		}

		return sb;
	}

	/**
	 * transfer a CategoryModel into Fusionchart PieDataset.
	 */
	private StringBuffer CategoryModelToPieDataset(StringBuffer sb, String uuid,
			CategoryModel model, PieChartConfig config) {
		
		CategoriesConfig cateConfig = config == null ?  null:
			PropertiesProxy.getCategoryConfig(config);
		
		Collection cates = model.getCategories();
		Comparable defaultSeries = null;
		int max = 0;
		for (final Iterator it = model.getKeys().iterator(); it.hasNext();) {
			final List key = (List) it.next();
			Comparable series = (Comparable) key.get(0);
			if (defaultSeries == null) {
				defaultSeries = series;
				max = cates.size();
			}
			if (!Objects.equals(defaultSeries, series)) {
				continue;
			}
			Comparable category = (Comparable) key.get(1);
			int cIndex = ((List) cates).indexOf(category);
			
			
			if (uuid != null)
				RenderUtils.addClientEventInvok(sb, uuid, 0, cIndex);
			
			RenderUtils.renderPieSet(sb, cIndex, category,
					model.getValue(series, category), cateConfig, uuid);
			
			if (--max == 0)
				break; // no more in this series
		}
		return sb;
	}

	/**
	 * transfer a CategoryModel into JFreeChart CategoryDataset.
	 */
	private StringBuffer CategoryModelToCategoryDataset(StringBuffer sb, String uuid,
			CategoryModel model, CategoryChartConfig config) {
		
		CategoriesConfig cateConfig = null;
		SeriesConfig serConfig = null;
				
		if (config != null) {
			cateConfig = PropertiesProxy.getCategoryConfig(config);
			serConfig = PropertiesProxy.getSeriesConfig(config);
		}
				
		RenderUtils.renderCategory(sb, model.getCategories(), cateConfig);
		RenderUtils.renderSeries(sb, model, serConfig, uuid);

		if (config != null)
			RenderUtils.renderTrendLine(sb,
					PropertiesProxy.getTrendLineConfig(config));

		return sb;
	}

	/**
	 * transfer a XYModel into JFreeChart XYSeriesCollection.
	 */
	private StringBuffer XYModelToXYDataset(StringBuffer sb, String uuid,
			XYModel model, CategoryChartConfig config) {
		
		PropertiesMapHandler serConfig = null;
		PropertiesMapProperties cateConfig = null;
				
		if (config != null) {
			serConfig = PropertiesProxy.getSeriesConfig(config);
			
			if (config instanceof XYChartConfig) 
				cateConfig = PropertiesProxy.getXAxisConfig((XYChartConfig) config);
			else 
				cateConfig = PropertiesProxy.getCategoryConfig(config);
		}
		
		Map seriesMap = new HashMap();
		Set xSet = null;

		if (model.isAutoSort())
			xSet = new TreeSet();
		else
			xSet = new LinkedHashSet(13);

		// collect all x
		Collection serieses = model.getSeries();
		for (final Iterator it = serieses.iterator(); it.hasNext();) {
			final Comparable series = (Comparable) it.next();
			final int size = model.getDataCount(series);
			Map indexMap = new TreeMap();
			for (int j = 0; j < size; ++j) {
				Number x = model.getX(series, j);
				xSet.add(x);
				indexMap.put(x, new Integer(j));
			}
			seriesMap.put(series, indexMap);
		}
		
		RenderUtils.renderCategory(sb, xSet, cateConfig);
		RenderUtils.renderXYSeries(sb, model, xSet, seriesMap, serConfig, uuid);
		
		if (config != null)
			RenderUtils.renderTrendLine(sb,
					PropertiesProxy.getTrendLineConfig(config));

		return sb;
	}

	/**
	 * transfer a GanttModel into JFreeChart GanttDataset.
	 */
	private StringBuffer GanttModelToGanttDataset(StringBuffer sb, String uuid, 
			GanttModel model, GanttChartConfig config, TimeZone tz) {
		
//		GanttChartRenderer renderer = null;
//		if (_renderer instanceof GanttChartRenderer)
//			renderer = (GanttChartRenderer) _renderer;
		
		
		
		
		
		Comparable[] allseries = model.getAllSeries();
		Set processSet = new LinkedHashSet(13);
		Map processIDMap = new HashMap(13);
		Map taskIDMap = new HashMap(30);
		
		long startLong = Long.MAX_VALUE, endLong = Long.MIN_VALUE;
		for (int i = 0, j = allseries.length, processId = 0; i < j; i++) {
			final Comparable series = allseries[i];
			final GanttTask[] tasks = model.getTasks(series);
			for (int k = 0, l = tasks.length; k < l; k++) {
				GanttTask task = tasks[k];
				String taskName = task.getDescription();
				
				startLong = Math.min(startLong, task.getStart().getTime());
				endLong = Math.max(endLong, task.getEnd().getTime());
				
				if (!processSet.contains(taskName)) {
					processIDMap.put(taskName, new Integer(processId++));
					processSet.add(taskName);
				}
				String taskID = processIDMap.get(taskName) + "_" + k;
				taskIDMap.put(task, taskID);
			}
		}
		
		GanttChartHeaderConfig hConfig = null;
		ProcessConfig pConfig = null;
		
		if (config != null) {
			hConfig = PropertiesProxy.getHeaderConfig(config);
			pConfig = PropertiesProxy.getProcessConfig(config);
			
			RenderUtils.renderCategories(sb, 
					PropertiesProxy.getCategoriesConfig(config));
		}
		
		RenderUtils.renderMonthHeaders(sb, tz, 
				new Date(startLong), new Date(endLong), hConfig);
		RenderUtils.renderProcess(sb, processSet, processIDMap, pConfig);
		RenderUtils.renderTasks(sb, model, processIDMap, config, uuid);
		
		if (config != null) {
			RenderUtils.renderTrendLine(sb,
					PropertiesProxy.getTrendLineConfig(config));
			
			RenderUtils.renderMilestone(sb, taskIDMap, 
					PropertiesProxy.getMilestoneConfig(config));
		}

		return sb;
	}

	// -- Chart specific implementation --//
	/** base chart */
	abstract private class ChartImpl {
		protected String createChartXML(Fusionchart chart) {
			String tag = getChartTag();
			StringBuffer sb = new StringBuffer();
			renderProperties(sb.append("<").append(tag), chart).append(">");
			
			String uuid = null;
			if (Events.isListened(chart, Events.ON_CLICK, false))
				uuid = chart.getUuid();
			
			renderDataset(sb, uuid, chart.getModel(), chart.getChartConfig(), chart.getTimeZone());
			
			renderMoreData(sb, chart);
			
			sb.append("</").append(tag).append(">");
			
			return sb.toString();
		}

		protected abstract StringBuffer renderDataset(StringBuffer sb,
				String uuid, ChartModel model, ChartConfig config, TimeZone tz);

		protected String getChartTag() {
			return "graph";
		}

		protected StringBuffer renderProperties(StringBuffer sb, Fusionchart chart) {
			ChartConfig config = chart.getChartConfig();
			Utils.renderChartProperties(sb, config);

			sb.append(Utils.renderFusionchartAttr("caption", chart.getTitle()))
					.append(Utils.renderFusionchartAttr("subCaption",
							chart.getSubTitle()))
					.append(Utils.renderFusionchartBoolean("showLegend",
							chart.isShowLegend()));
			sb.append(" showValues='0'");
			return sb;
		}
		
		public void renderMoreData(StringBuffer sb, Fusionchart chart) {
		}
	}

	abstract private class AxisLableChart extends ChartImpl {
		protected StringBuffer renderProperties(StringBuffer sb,
				Fusionchart chart) {
			return super.renderProperties(sb, chart)
				.append(Utils.renderFusionchartAttr("xaxisname", chart.getXAxis()))
				.append(Utils.renderFusionchartAttr("yaxisname", chart.getYAxis()));
		}
	}

	/** pie chart */
	private class Pie extends ChartImpl {
		
		protected StringBuffer renderDataset(StringBuffer sb, String uuid,
				ChartModel model, ChartConfig config, TimeZone tz) {
			
			PieChartConfig pConfig = config instanceof PieChartConfig ? 
					(PieChartConfig) config: null;

			if (model instanceof CategoryModel)
				return CategoryModelToPieDataset(sb, uuid, (CategoryModel) model, pConfig);
			if (model instanceof PieModel)
				return PieModelToPieDataset(sb, uuid, (PieModel) model, pConfig);

			throw new UiException(
					"The model of pie chart must be a org.zkoss.zul.PieModel or a org.zkoss.zul.CategoryModel");
		}

		protected StringBuffer renderProperties(StringBuffer sb,
				Fusionchart chart) {
			return super.renderProperties(sb, chart).append(" shownames='1'");
		}
	}

	/** bar chart */
	private class Bar extends AxisLableChart {
		protected StringBuffer renderDataset(StringBuffer sb, String uuid,
				ChartModel model, ChartConfig config, TimeZone tz) {
			
			CategoryChartConfig cConfig = config instanceof CategoryChartConfig ? 
					(CategoryChartConfig) config: null;
			
			if (model instanceof CategoryModel)
				return CategoryModelToCategoryDataset(sb, uuid, (CategoryModel) model, cConfig);
			if (model instanceof XYModel)
				return XYModelToXYDataset(sb, uuid, (XYModel) model, cConfig);

			throw new UiException(
					"The model of bar chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/** area chart */
	private class AreaImpl extends AxisLableChart {
		protected StringBuffer renderDataset(StringBuffer sb, String uuid,
				ChartModel model, ChartConfig config, TimeZone tz) {
			
			CategoryChartConfig cConfig = config instanceof CategoryChartConfig ? 
					(CategoryChartConfig) config: null;
			
			if (model instanceof CategoryModel)
				return CategoryModelToCategoryDataset(sb, uuid, (CategoryModel) model, cConfig);
			if (model instanceof XYModel)
				return XYModelToXYDataset(sb, uuid, (XYModel) model, cConfig);

			throw new UiException(
					"The model of area chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/** line chart */
	private class Line extends AxisLableChart {
		protected StringBuffer renderDataset(StringBuffer sb, String uuid,
				ChartModel model, ChartConfig config, TimeZone tz) {
			
			CategoryChartConfig cConfig = config instanceof CategoryChartConfig ? 
					(CategoryChartConfig) config: null;
			
			if (model instanceof CategoryModel)
				return CategoryModelToCategoryDataset(sb, uuid, (CategoryModel) model, cConfig);
			if (model instanceof XYModel)
				return XYModelToXYDataset(sb, uuid, (XYModel) model, cConfig);

			throw new UiException(
					"The model of line chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/** stackedarea chart */
	private class StackedArea extends AxisLableChart {
		protected StringBuffer renderDataset(StringBuffer sb, String uuid,
				ChartModel model, ChartConfig config, TimeZone tz) {
			
			CategoryChartConfig cConfig = config instanceof CategoryChartConfig ? 
					(CategoryChartConfig) config: null;
			
			if (model instanceof CategoryModel)
				return CategoryModelToCategoryDataset(sb, uuid, (CategoryModel) model, cConfig);
			if (model instanceof XYModel)
				return XYModelToXYDataset(sb, uuid, (XYModel) model, cConfig);

			throw new UiException(
					"The model of stacked_area chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/**
	 * gantt chart
	 */
	private class Gantt extends ChartImpl {
		protected StringBuffer renderDataset(StringBuffer sb, String uuid,
				ChartModel model, ChartConfig config, TimeZone tz) {
			
			GanttChartConfig gConfig = config instanceof GanttChartConfig ? 
					(GanttChartConfig) config: null;
			
			if (model instanceof GanttModel)
				return GanttModelToGanttDataset(sb, uuid, (GanttModel) model, gConfig, tz);

			throw new UiException(
					"The model of gantt chart must be a org.zkoss.zul.GanttModel");
		}
		
		public void renderMoreData(StringBuffer sb, Fusionchart chart) {
			ListModel tModel = chart.getTableModel();
			if (tModel != null) {
				ChartConfig config = chart.getChartConfig();
				GanttTableConfig tConfig = null;
				
				if (config instanceof GanttChartConfig)
					tConfig = PropertiesProxy.getTableConfig(
							(GanttChartConfig) config);
				
				RenderUtils.renderGenttTable(sb, tModel, 
						chart.getTableRenderer(), tConfig);
			}
		}

		protected String getChartTag() {
			return "chart";
		}

		protected StringBuffer renderProperties(StringBuffer sb,
				Fusionchart chart) {
			return super.renderProperties(sb, chart).append(" dateFormat='MM/dd/yyyy'");
		}
	}

	


}
