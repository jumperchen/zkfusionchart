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
import java.util.TreeMap;
import java.util.TreeSet;

import org.zkoss.fusionchart.Fusionchart;
import org.zkoss.fusionchart.api.FusionchartEngine;
import org.zkoss.fusionchart.api.FusionchartRenderer;
import org.zkoss.fusionchart.renderer.CategoryChartRenderer;
import org.zkoss.fusionchart.renderer.GanttChartRenderer;
import org.zkoss.fusionchart.renderer.PieChartRenderer;
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
	private transient Fusionchart _chart;
	private transient boolean _threeD;
	private transient String _type;
	private transient ChartModel _model;
	private transient FusionchartRenderer _renderer;

	private transient ChartImpl _chartImpl; // chart implementaion

	public String createChartXML(Object data) {
		return getChartImpl((Fusionchart) data).createChartXML();
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
		_chart = chart;
		_type = chart.getType();
		_threeD = chart.isThreeD();
		_model = chart.getModel();
		_renderer = chart.getChartRenderer();

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
	private String PieModelToPieDataset(PieModel model) {
		StringBuffer sb = new StringBuffer();
		String uuid = null;
		if (Events.isListened(_chart, Events.ON_CLICK, false))
			uuid = _chart.getUuid();

		PieChartRenderer renderer = RenderUtils.getPieChartRenderer(_renderer);
		
		int index = 0;
		for (final Iterator it = model.getCategories().iterator(); it.hasNext(); index++) {
			Comparable category = (Comparable) it.next();
			RenderUtils.renderPieSet(sb, index, category, model.getValue(category),
					renderer, uuid);
		}

		return sb.toString();
	}

	/**
	 * transfer a CategoryModel into Fusionchart PieDataset.
	 */
	private String CategoryModelToPieDataset(CategoryModel model) {
		StringBuffer sb = new StringBuffer();
		String uuid = null;
		if (Events.isListened(_chart, Events.ON_CLICK, false))
			uuid = _chart.getUuid();

		PieChartRenderer renderer = RenderUtils.getPieChartRenderer(_renderer);
		
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
			RenderUtils.renderPieSet(sb, cIndex, category,
					model.getValue(series, category), renderer, uuid);
			if (--max == 0)
				break; // no more in this series
		}
		return sb.toString();
	}

	/**
	 * transfer a CategoryModel into JFreeChart CategoryDataset.
	 */
	private String CategoryModelToCategoryDataset(CategoryModel model) {
		StringBuffer sb = new StringBuffer();
		CategoryChartRenderer renderer = 
			RenderUtils.getCategoryChartRenderer(_renderer);
		
		RenderUtils.renderCategory(sb, model.getCategories(), renderer);
		
		String uuid = null;
		if (Events.isListened(_chart, Events.ON_CLICK, false))
			uuid = _chart.getUuid();
		
		RenderUtils.renderSeries(sb, model, renderer, uuid);

		if (_renderer != null)
			_renderer.renderChildTages(sb);

		return sb.toString();
	}

	/**
	 * transfer a XYModel into JFreeChart XYSeriesCollection.
	 */
	private String XYModelToXYDataset(XYModel model) {
		StringBuffer sb = new StringBuffer();
		
		CategoryChartRenderer renderer = 
			RenderUtils.getCategoryChartRenderer(_renderer);
		
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
		
		RenderUtils.renderCategory(sb, xSet, renderer);
		
		String uuid = null;
		if (Events.isListened(_chart, Events.ON_CLICK, false))
			uuid = _chart.getUuid();
		
		RenderUtils.renderXYSeries(sb, model, xSet, seriesMap, renderer, uuid);
		
		if (_renderer != null)
			_renderer.renderChildTages(sb);

		return sb.toString();
	}

	/**
	 * transfer a XYModel into JFreeChart DefaultTableXYDataset.
	 */
	private String XYModelToTableXYDataset(XYModel model) {
		StringBuffer sb = new StringBuffer();
		for (final Iterator it = model.getSeries().iterator(); it.hasNext();) {
			final Comparable series = (Comparable) it.next();
			// XYSeries xyser = new XYSeries(series, false, false);
			final int size = model.getDataCount(series);
			for (int j = 0; j < size; ++j) {

				// xyser.add(model.getX(series, j), model.getY(series, j),
				// false);
			}
			// dataset.addSeries(xyser);
		}
		return sb.toString();
	}

	/**
	 * transfer a GanttModel into JFreeChart GanttDataset.
	 */
	private String GanttModelToGanttDataset(GanttModel model) {
		StringBuffer sb = new StringBuffer();
		
		GanttChartRenderer renderer = null;
		if (_renderer instanceof GanttChartRenderer)
			renderer = (GanttChartRenderer) _renderer;
		
		
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
		
		RenderUtils.renderCategory(sb, _chart.getTimeZone(), 
				new Date(startLong), new Date(endLong), renderer);
		
		RenderUtils.renderProcess(sb, processSet, processIDMap, renderer);
		
		ListModel tModel = _chart.getTableModel();
		if (tModel != null)
			RenderUtils.renderGenttTable(sb, tModel, _chart.getTableRenderer());
		
		
		String uuid = null;
		if (Events.isListened(_chart, Events.ON_CLICK, false))
			uuid = _chart.getUuid();
		
		RenderUtils.renderTasks(sb, model, processIDMap, renderer, uuid);
		
		
		
		if (renderer != null) {
			renderer.renderChildTages(sb);
			renderer.renderMilestone(sb, taskIDMap);
		}

		return sb.toString();
	}

	// -- Chart specific implementation --//
	/** base chart */
	abstract private class ChartImpl {
		abstract String createChartXML();

		protected String createChart(String dataset) {
			String tag = getChartTag();
			StringBuffer sb = new StringBuffer("<").append(tag);

			renderProperties(sb);

			sb.append(">").append(dataset).append("</").append(tag).append(">");
			return sb.toString();
		}

		protected String getChartTag() {
			return "graph";
		}

		protected void renderProperties(StringBuffer sb) {
			if (_renderer != null)
				_renderer.renderChartProperty(sb);

			sb.append(Utils.renderFusionchartAttr("caption", _chart.getTitle()))
					.append(Utils.renderFusionchartAttr("subCaption",
							_chart.getSubTitle()))
					.append(Utils.renderFusionchartBoolean("showLegend",
							_chart.isShowLegend()));
			sb.append(" showValues='0'");
		}
	}

	abstract private class AxisLableChart extends ChartImpl {
		protected void renderProperties(StringBuffer sb) {
			super.renderProperties(sb);
			sb.append(Utils.renderFusionchartAttr("xaxisname", _chart.getXAxis()))
					.append(Utils.renderFusionchartAttr("yaxisname",
							_chart.getYAxis()));
		}
	}

	/** pie chart */
	private class Pie extends ChartImpl {
		public String createChartXML() {
			if (_model instanceof CategoryModel)
				return createChart(CategoryModelToPieDataset((CategoryModel) _model));
			if (_model instanceof PieModel)
				return createChart(PieModelToPieDataset((PieModel) _model));

			throw new UiException(
					"The model of pie chart must be a org.zkoss.zul.PieModel or a org.zkoss.zul.CategoryModel");
		}

		protected void renderProperties(StringBuffer sb) {
			super.renderProperties(sb);
			sb.append(" shownames='1'");
		}
	}

	/** bar chart */
	private class Bar extends AxisLableChart {
		public String createChartXML() {
			if (_model instanceof CategoryModel)
				return createChart(CategoryModelToCategoryDataset((CategoryModel) _model));
			if (_model instanceof XYModel)
				return createChart(XYModelToXYDataset((XYModel) _model));

			throw new UiException(
					"The model of bar chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/** area chart */
	private class AreaImpl extends AxisLableChart {
		public String createChartXML() {
			if (_model instanceof CategoryModel)
				return createChart(CategoryModelToCategoryDataset((CategoryModel) _model));
			if (_model instanceof XYModel)
				return createChart(XYModelToXYDataset((XYModel) _model));

			throw new UiException(
					"The model of area chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/** line chart */
	private class Line extends AxisLableChart {
		public String createChartXML() {
			if (_model instanceof CategoryModel)
				return createChart(CategoryModelToCategoryDataset((CategoryModel) _model));
			if (_model instanceof XYModel)
				return createChart(XYModelToXYDataset((XYModel) _model));

			throw new UiException(
					"The model of line chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/** stackedarea chart */
	private class StackedArea extends AxisLableChart {
		public String createChartXML() {
			if (_model instanceof CategoryModel)
				return createChart(CategoryModelToCategoryDataset((CategoryModel) _model));
			else if (_model instanceof XYModel)
				return createChart(XYModelToXYDataset((XYModel) _model));

			throw new UiException(
					"The model of stacked_area chart must be a org.zkoss.zul.CategoryModel or a org.zkoss.zul.XYModel");
		}
	}

	/**
	 * gantt chart
	 */
	private class Gantt extends ChartImpl {
		public String createChartXML() {
			if (_model instanceof GanttModel)
				return createChart(GanttModelToGanttDataset((GanttModel) _model));

			throw new UiException(
					"The model of gantt chart must be a org.zkoss.zul.GanttModel");
		}

		protected String getChartTag() {
			return "chart";
		}

		protected void renderProperties(StringBuffer sb) {
			super.renderProperties(sb);
			sb.append(" dateFormat='MM/dd/yyyy'");
		}
	}


}
