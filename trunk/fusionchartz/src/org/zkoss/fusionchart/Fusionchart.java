/* Fusionchart.java

	Purpose:
		
	Description:
		
	History:
		Jan 8, 2011 2:13:47 AM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.zkoss.fusionchart.api.FusionchartEngine;
import org.zkoss.fusionchart.api.GanttTableRenderer;
import org.zkoss.fusionchart.config.ChartConfig;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.lang.Classes;
import org.zkoss.lang.Library;
import org.zkoss.lang.Objects;
import org.zkoss.lang.Strings;
import org.zkoss.util.TimeZones;
import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.AuRequests;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.XYModel;
import org.zkoss.zul.event.ChartDataEvent;
import org.zkoss.zul.event.ChartDataListener;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;


/**
 * The Fusionchart component. Developers set proper chart type, data model,
 * and the threeD (3D) attribute to draw proper chart.
 *
 * <p>The model and type must
 * match to each other; or the result is unpredictable. The 3D chart is not supported
 * on all chart type.
 *
 * <table>
 *   <tr><th>type</th><th>model</th><th>3D</th></tr>
 *   <tr><td>area</td><td>{@link CategoryModel} or {@link XYModel}</td><td>No</td></tr>
 *   <tr><td>bar</td><td>{@link CategoryModel}</td><td>Yes</td></tr>
 *   <tr><td>gantt</td><td>{@link GanttModel}</td><td>No</td></tr>
 *   <tr><td>line</td><td>{@link CategoryModel} or {@link XYModel}</td><td>Yes</td></tr>
 *   <tr><td>pie</td><td>{@link PieModel}</td><td>Yes</td></tr>
 *   <tr><td>stacked_bar</td><td>{@link CategoryModel}</td><td>Yes</td></tr>
 *   <tr><td>stacked_area</td><td>{@link CategoryModel} or {@link XYModel}</td><td>No</td></tr>
 * </table>
 *
 * @see ChartModel
 * @author jimmyshiau
 * @since 5.0.6
 */
public class Fusionchart extends HtmlBasedComponent implements org.zkoss.fusionchart.api.Fusionchart {
	private static final long serialVersionUID = 20110107232220L;

	// chart type
	public static final String COMBINATION = "combination";

	private static final Map DEFAULT_MODEL = new HashMap();
	static {
		DEFAULT_MODEL.put(Chart.PIE, "org.zkoss.zul.SimplePieModel");
		DEFAULT_MODEL.put(Chart.BAR, "org.zkoss.zul.SimpleCategoryModel");
		DEFAULT_MODEL.put(Chart.STACKED_BAR,
				"org.zkoss.zul.SimpleCategoryModel");
		DEFAULT_MODEL.put(COMBINATION, "org.zkoss.zul.SimpleCategoryModel");
		DEFAULT_MODEL.put(Chart.LINE, "org.zkoss.zul.SimpleXYModel");
		DEFAULT_MODEL.put(Chart.AREA, "org.zkoss.zul.SimpleXYModel");
		DEFAULT_MODEL.put(Chart.STACKED_AREA, "org.zkoss.zul.SimpleXYModel");
		DEFAULT_MODEL.put(Chart.GANTT, "org.zkoss.zul.GanttModel");
	}

	//control variable
	
	private boolean _smartDrawChart; // whether post the smartDraw event already?
	private transient EventListener _smartDrawChartListener; // the smartDrawListner
	private transient ChartDataListener _dataListener;
	private transient ChartDataListener _propertyListener;
	private transient ListDataListener _tableListener;

	private String _type = Chart.PIE; // chart type (pie, ring, bar, line, xy, etc)
	private boolean _threeD; // whether a 3D chart
	
	//chart related attributes
	private String _title; // chart title
	private String _subTitle; // chart sub title
	private int _intWidth = 400; //default to 400
	private int _intHeight = 200; //default to 200
	private String _xAxis;
	private String _yAxis;
	private boolean _showLegend = true; // wether show legend
	private boolean _showTooltiptext = true; //wether show tooltiptext
	private String _orient = "vertical"; // orient

	//only for Gantt chart
	private TimeZone _tzone = TimeZones.getCurrent();
	private String _dateFormat;
	
	//chart data model
	//chart data model
	private transient ChartModel _model; //chart data model
	private transient ListModel _tableModel;//chart table model
	private transient ChartConfig _chartConfig;
	
	
	
//	private transient FusionchartRenderer _chartRenderer; //chart render
	private transient GanttTableRenderer _tableRenderer; //chart table render
	
	//chart engine
	private FusionchartEngine _engine; //chart engine. model and engine is related
	private String _dataXML;
	private String _dataXMLPath;
	
	public Fusionchart() {
		init();
		setWidth("500");
		setHeight("250");
	}

	private ChartModel createDefaultModel() {
		final String klass = (String) DEFAULT_MODEL.get(getType());
		if (klass != null) {
			try {
				return (ChartModel) Classes.newInstanceByThread(klass);
			} catch (Exception e) {
				throw UiException.Aide.wrap(e);
			}
		} else {
			throw new UiException("unknown chart type: "+getType());
		}
	}
	
	private void init() {
		if (_smartDrawChartListener == null) {
			_smartDrawChartListener = new SmartDrawListener();
			addEventListener("onSmartDrawChart", _smartDrawChartListener);
		}
	}
	
	private class SmartDrawListener implements EventListener, Serializable {
		private static final long serialVersionUID = 20110612003710L;
		public void onEvent(Event event) throws Exception {
			doSmartDraw();
		}
	}
	
	private void doSmartDraw() {
		if (Strings.isBlank(getType()))
			throw new UiException("chart must specify type (pie, bar, line, ...)");

		if (_model == null)
			_model = createDefaultModel();

		if (Strings.isBlank(getWidth()))
			throw new UiException("chart must specify width");
			
		if (Strings.isBlank(getHeight()))
			throw new UiException("chart must specify height");
			
		try {
			smartUpdate("dataXML", 
					(_dataXML = getEngine().createChartXML(Fusionchart.this)));
		} finally {
			_smartDrawChart = false;
		}
	}
	
	/**
	 * Set the chart's type (Chart.PIE, Chart.BAR, Chart.LINE, etc.).
	 *
	 * <p>Default: pie.
	 *
	 */
	public void setType(String type) {
		if (!Objects.equals(_type, type)) {
			_type = type;
			smartUpdate("type", type);
		}
	}
	
	/**
	 * Get the chart's type.
	 */
	public String getType() {
		return _type;
	}

	/**
	 * Set true to show three dimensional graph (If a type of chart got no 3d peer, this is ignored).
	 */
	public void setThreeD(boolean threeD) {
		if (_threeD != threeD) {
			_threeD = threeD;
			smartUpdate("threeD", threeD);
		}
	}
	
	/**
	 * Whether a 3d chart.
	 */
	public boolean isThreeD() {
		return _threeD;
	}

	/**
	 * Set the chart's title.
	 * @param title the chart's title.
	 *
	 */
	public void setTitle(String title) {
		if (!Objects.equals(_title, title)) {
			_title = title;
			smartDrawChart();
		}
	}
	
	/**
	 * Get the chart's title.
	 */
	public String getTitle() {
		return _title;
	}
	
	/**
	 * Set the chart's subtitle.
	 * @param subTitle the chart's subtitle.
	 *
	 */
	public void setSubTitle(String subTitle) {
		if (!Objects.equals(_subTitle, subTitle)) {
			_subTitle = subTitle;
			smartDrawChart();
		}
	}
	
	/**
	 * Get the chart's subtitle.
	 */
	public String getSubTitle() {
		return _subTitle;
	}
	
	/**
	 * Override super class to prepare the int width.
	 */
	public void setWidth(String w) {
		if (!Objects.equals(w, getWidth())) {
			_intWidth = Utils.stringToInt(w);
			super.setWidth(w);
			smartUpdate("intWidth", _intWidth);
		}
	}
	
	/**
	 * Get the chart int width in pixel; to be used by the derived subclass.
	 */
	public int getIntWidth() {
		return _intWidth;
	}
	
	/**
	 * Override super class to prepare the int height.
	 */
	public void setHeight(String h) {
		if (!Objects.equals(h, getHeight())) {
			_intHeight = Utils.stringToInt(h);
			super.setHeight(h);
			smartUpdate("intHeight", _intHeight);
		}
	}
	
	/**
	 * Get the chart int width in pixel; to be used by the derived subclass.
	 */
	public int getIntHeight() {
		return _intHeight;
	}
	
	/**
	 * Set the label in xAxis.
	 * @param label label in xAxis.
	 */
	public void setXAxis(String label) {
		if (!Objects.equals(_xAxis, label)) {
			_xAxis = label;
			smartDrawChart();
		}
	}
	
	/**
	 * Get the label in xAxis.
	 */
	public String getXAxis() {
		return _xAxis;
	}
	
	/**
	 * Set the label in yAxis.
	 * @param label label in yAxis.
	 */
	public void setYAxis(String label) {
		if (!Objects.equals(_yAxis, label)) {
			_yAxis = label;
			smartDrawChart();
		}
	}
	
	/**
	 * Get the label in yAxis.
	 */
	public String getYAxis() {
		return _yAxis;
	}
	
	/**
	 * whether show the chart's legend.
	 * @param showLegend true if want to show the legend (default to true).
	 */
	public void setShowLegend(boolean showLegend) {
		if (_showLegend != showLegend) {
			_showLegend = showLegend;
			smartDrawChart();
		}
	}
	
	/**
	 * Check whether show the legend of the chart.
	 */
	public boolean isShowLegend() {
		return _showLegend;
	}
		
	/**
	 * whether show the chart's tooltip.
	 * @param showTooltiptext true if want to pop the tooltiptext (default to true).
	 */
	public void setShowTooltiptext(boolean showTooltiptext) {
		if (_showTooltiptext != showTooltiptext) {
			_showTooltiptext = showTooltiptext;
			smartDrawChart();
		}
	}
	
	/**
	 * Check whether show the tooltiptext.
	 */
	public boolean isShowTooltiptext() {
		return _showTooltiptext;
	}
	
	/**
	 * @deprecated Please config by chart renderer.
	 */
	public void setPaneAlpha(int alpha) {
	}
	
	/**
	 * @deprecated Please config by chart renderer.
	 */
	public int getPaneAlpha() {
		return 0;
	}

	/**
	 * @deprecated Please config by chart renderer.
	 */
	public void setPaneColor(String color) {
	}
	
	/**
	 * @deprecated Please config by chart renderer.
	 */
	public String getPaneColor() {
		return null;
	}
	
	/**
	 * @deprecated Please config by chart renderer.
	 */
	public int[] getPaneRGB() {
		return null;
	}

	/**
	 * @deprecated Please config by chart renderer.
	 */
	public void setFgAlpha(int alpha) {
	}

	/**
	 * @deprecated Please config by chart renderer.
	 */
	public int getFgAlpha() {
		return 0;
	}

	/**
	 * @deprecated Please config by chart renderer.
	 */
	public void setBgAlpha(int alpha) {
	}
	
	/**
	 * @deprecated Please config by chart renderer.
	 */
	public int getBgAlpha() {
		return 0;
	}

	/**
	 * @deprecated Please config by chart renderer.
	 */
	public void setBgColor(String color) {
	}
	
	/**
	 * @deprecated Please config by chart renderer.
	 */
	public String getBgColor() {
		return null;
	}
	
	/**
	 * @deprecated Please config by chart renderer.
	 */
	public int[] getBgRGB() {
		return null;
	}
	
	
	/**
	 * Set the chart orientation.
	 * @param orient vertical or horizontal (default to vertical)
	 */
	public void setOrient(String orient) {
		if (!Objects.equals(orient, _orient)) {
			_orient = orient;
			smartUpdate("orient", orient);
		}
	}
	
	/**
	 * Get the chart orientation (vertical or horizontal)
	 */
	public String getOrient() {
		return _orient;
	}
	
	/** Returns the time zone that this Time Series Chart belongs to, or null if
	 * the default time zone is used.
	 * <p>The default time zone is determined by {@link org.zkoss.util.TimeZones#getCurrent}.
	 */
	public TimeZone getTimeZone() {
		return _tzone;
	}
	/** Sets the time zone that this Time Series Chart belongs to, or null if
	 * the default time zone is used.
	 * <p>The default time zone is determined by {@link org.zkoss.util.TimeZones#getCurrent}.
	 */
	public void setTimeZone(TimeZone tzone) {
		if (!Objects.equals(tzone, _tzone)) {
			_tzone = tzone;
			smartDrawChart();
		}
	}
	
	/**
	 * Returns the date format used by date related Chart.
	 * @return the date format used by date related Chart..
	 */
	public String getDateFormat() {
		return _dateFormat;
	}
	
	/**
	 * Sets the date format used by date related Chart.
	 * This is the most important attribute, 
	 * which you'll need to specify for all the Gantt charts that you build. 
	 * With the help of this attribute, you're basically specifying the format 
	 * in which you'll be providing your dates to FusionCharts.
	 * @param format  MM/dd/yyyy or dd/MM/yyyy or yyyy/MM/dd (default to MM/dd/yyyy)
	 */
	public void setDateFormat(String format) {
		if (!Objects.equals(format, _dateFormat)) {
			_dateFormat = format;
			smartDrawChart();
		}
	}
	
	/**
	 * Set the url of the chart data .xml file. See Fusionchart Free's 
	 * <a href="http://www.fusioncharts.com/free/docs/">
	 * FusionCharts Free Documentation</a> for details.
	 * @param url the url path for the data xml path
	 */
	public void setDataXMLPath(String url) {
		if (!Objects.equals(_dataXMLPath, url)) {
			_dataXMLPath = url;
			smartUpdate("dataXMLPath", new EncodedURL(_dataXMLPath));	
		}
	}

	/**
	 * Get the url of the chart data .xml file. See Fusionchart Free's 
	 * <a href="http://www.fusioncharts.com/free/docs/">
	 * FusionCharts Free Documentation</a> for details.
	 */
	public String getDataXMLPath() {
		return _dataXMLPath;
	}
	
	public ChartConfig getChartConfig() {
		return _chartConfig;
	}

	public void setChartConfig(ChartConfig chartConfig) {
		if (chartConfig != null) {
			if (_chartConfig != chartConfig) {
				if (_chartConfig != null) {
					_chartConfig.removeChartDataListener(_propertyListener);
				}
				_chartConfig = chartConfig;
				initPropertyListener();
			}
		} else if (_chartConfig != null) {
			_chartConfig.removeChartDataListener(_propertyListener);
			_chartConfig = null;
		}
		
		smartDrawChart();
	}
	
	public GanttTableRenderer getTableRenderer() {
		return _tableRenderer;
	}

	public void setTableRenderer(GanttTableRenderer renderer) {
		if (renderer != _tableRenderer) {
			_tableRenderer = renderer;
			smartDrawChart();
		}
	}
	
	/** Returns the chart model associated with this chart, or null
	 * if this chart is not associated with any chart data model.
	 */
	public ChartModel getModel() {
		return _model;
	}

	/** Sets the chart model associated with this chart.
	 * If a non-null model is assigned, no matter whether it is the same as
	 * the previous, it will always cause re-render.
	 *
	 * @param model the chart model to associate, or null to dis-associate
	 * any previous model.
	 * @exception UiException if failed to initialize with the model
	 */
	public void setModel(ChartModel model) {
		if (model != null) {
			if (_model != model) {
				if (_model != null) {
					_model.removeChartDataListener(_dataListener);
				}
				_model = model;
				initDataListener();
			}
		} else if (_model != null) {
			_model.removeChartDataListener(_dataListener);
			_model = null;
		}
		smartDrawChart();
	}
	
	/** Sets the model by use of a class name.
	 * It creates an instance automatically.
	 */
	public void setModel(String clsnm)
	throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
	InstantiationException, java.lang.reflect.InvocationTargetException {
		if (clsnm != null) {
			setModel((ChartModel)Classes.newInstanceByThread(clsnm));
		}
	}
	
	public ListModel getTableModel() {
		return _tableModel;
	}
	
	public void setTableModel(ListModel model) {
		if (model != null) {
			if (_tableModel != model) {
				if (_tableModel != null)
					_tableModel.removeListDataListener(_tableListener);
				_tableModel = model;
				initTableDataListener();
			}
		} else if (_tableModel != null) {
			_tableModel.removeListDataListener(_tableListener);
			_tableModel = null;
		}
		smartDrawChart();
	}
	
	public FusionchartEngine getEngine() throws UiException {
		if (_engine == null)
			_engine = newChartEngine();
		return _engine;
	}
	/** Instantiates the default chart engine.
	 * It is called, if {@link #setEngine} is not called with non-null
	 * engine.
	 *
	 * <p>By default, it looks up the library property called
	 * org.zkoss.zul.chart.engine.class.
	 * If found, the value is assumed to be
	 * the class name of the chart engine (it must implement
	 * {@link FusionchartEngine}).
	 * If not found, {@link UiException} is thrown.
	 *
	 * <p>Derived class might override this method to provide your
	 * own default class.
	 *
	 * @exception UiException if failed to instantiate the engine
	 */
	protected FusionchartEngine newChartEngine() throws UiException {
		final String PROP = "org.zkoss.fusionchart.engine.class";
		final String klass = Library.getProperty(PROP);
		if (klass == null)
			throw new UiException("Library property,  "+PROP+", required");

		final Object v;
		try {
			v = Classes.newInstanceByThread(klass);
		} catch (Exception ex) {
			throw UiException.Aide.wrap(ex);
		}
		if (!(v instanceof FusionchartEngine))
			throw new UiException(FusionchartEngine.class + " must be implemented by "+v);
		return (FusionchartEngine)v;
	}
	/** Sets the chart engine.
	 */
	public void setEngine(FusionchartEngine engine) {
		if (_engine != engine) {
			_engine = engine;
		}
		
		//Always redraw
		smartDrawChart();
	}

	/** Sets the chart engine by use of a class name.
	 * It creates an instance automatically.
	 */
	public void setEngine(String clsnm)
	throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
	InstantiationException, java.lang.reflect.InvocationTargetException {
		if (clsnm != null) {
			setEngine((FusionchartEngine)Classes.newInstanceByThread(clsnm));
		}
	}
	
	private void initDataListener() {
		if (_dataListener == null) {
			_dataListener = new MyChartDataListener();
			_model.addChartDataListener(_dataListener);
		}
	}
	
	private void initTableDataListener() {
		if (_tableListener == null)
			_tableListener = new MyChartTableDataListener();

		_tableModel.addListDataListener(_tableListener);
	}
	
	private void initPropertyListener() {
		if (_propertyListener == null) {
			_propertyListener = new MyChartDataListener();
			_chartConfig.addChartDataListener(_propertyListener);
		}
	}
	
	private class MyChartDataListener implements ChartDataListener, Serializable {
		private static final long serialVersionUID = 20091008183622L;

		public void onChange(ChartDataEvent event) {
			smartDrawChart();
		}
	}
	
	private class MyChartTableDataListener implements ListDataListener, Serializable {
		private static final long serialVersionUID = 20110612013922L;

		public void onChange(ListDataEvent event) {
			smartDrawChart();
		}
	}

	/**
	 * mark a draw flag to inform that this Chart needs update.
	 */
	protected void smartDrawChart() {
		if (!_smartDrawChart) { 
			_smartDrawChart = true;
			Events.postEvent("onSmartDrawChart", this, null);
		}
	}

	public String getZclass() {
		return _zclass != null ? _zclass: "z-fusionchart";
	}
	
	//Cloneable//
	public Object clone() {
		final Fusionchart clone = (Fusionchart)super.clone();

		clone._smartDrawChartListener = null;
		clone._smartDrawChart = false;
		clone.init();
		clone.doSmartDraw();
		if (clone._model != null) {
			clone._dataListener = null;
			clone.initDataListener();
		}
		
		if (clone._chartConfig != null) {
			clone._propertyListener = null;
			clone.initPropertyListener();
		}
		
		if (clone._tableModel != null) {
			clone._tableListener = null;
			clone.initTableDataListener();
		}
		
		if (clone._tableRenderer != null) {
			clone._propertyListener = null;
			clone.initTableDataListener();
		}
		
		return clone;
	}
	
	public void service(AuRequest request, boolean everError) {
		final String cmd = request.getCommand();
		if (cmd.equals(Events.ON_CLICK)) {
			final Map data = request.getData();
			ChartModel model = getModel();
			int cateIndex = AuRequests.getInt(data, "category", 0, true);
			
			if (model instanceof CategoryModel) {
				
				int seriIndex = AuRequests.getInt(data, "series", 0, true);
				CategoryModel cateModel = (CategoryModel) model;
				Comparable series = cateModel.getSeries(seriIndex);
				Comparable category	= cateModel.getCategory(cateIndex);
				
				data.put("series", series);
				data.put("category", category);
				data.put("value", ((CategoryModel) model).getValue(series, category));
				
			} else if (model instanceof PieModel) {
				PieModel pieModel = (PieModel) model;
				Comparable category	= pieModel.getCategory(cateIndex);
				
				data.put("category", category);
				data.put("value", pieModel.getValue(category));
				
			} else if (model instanceof XYModel) {
				XYModel xyModel = (XYModel) model;
				int seriIndex = AuRequests.getInt(data, "series", 0, true);
				Comparable series = xyModel.getSeries(seriIndex);
				
				data.put("series", series);
				data.put("x", xyModel.getX(series, cateIndex));
				data.put("y", xyModel.getY(series, cateIndex));
				
			} else if (model instanceof GanttModel) {
				GanttModel ganttModel = (GanttModel) model;
				int seriIndex = AuRequests.getInt(data, "series", 0, true);
				Comparable series = ganttModel.getAllSeries()[seriIndex];
				
				data.put("series", series);
				data.put("task", ganttModel.getTasks(series)[cateIndex]);
				
			}
			Events.postEvent(new Event(cmd, this, data));
		} else
			super.service(request, everError);
	}
	
	protected void renderProperties(ContentRenderer renderer)
			throws IOException {
		super.renderProperties(renderer);
		render(renderer, "type", _type);
		render(renderer, "threeD", _threeD);
		render(renderer, "orient", _orient);
		if (_intWidth != 400)
			renderer.render("intWidth", _intWidth);
		if (_intHeight != 200)
			renderer.render("intHeight", _intHeight);
		render(renderer, "dataXML", _dataXML);
		render(renderer, "dataXMLPath", getEncodedURL(_dataXMLPath));
	}
	
	private String getEncodedURL(String path) {
		  final Desktop dt = getDesktop(); //it might not belong to any desktop
		  return dt != null ? dt.getExecution().encodeURL(path): "";			 
	}
	
	private class EncodedURL implements org.zkoss.zk.ui.util.DeferredValue {
		private String path;
		public EncodedURL(String path) {
			this.path = path;
		}
		public Object getValue() {
			return getEncodedURL(path);
		}
	}
	// -- Super --//
	/** Not childable. */
	public boolean isChildable() {
		return false;
	}
}
