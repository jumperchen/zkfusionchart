/* Fusionchart.java

	Purpose:
		
	Description:
		
	History:
		Jan 21, 2011 11:47:47 AM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.api;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.XYModel;
import org.zkoss.zul.impl.ChartEngine;
import org.zkoss.zul.event.ChartAreaListener;
import java.util.TimeZone;

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
public interface Fusionchart {
	/**
	 * Set the chart's type (Chart.PIE, Chart.BAR, Chart.LINE, etc.).
	 * 
	 * <p>
	 * Default: pie.
	 */
	public void setType(String type);

	/**
	 * Get the chart's type.
	 */
	public String getType();

	/**
	 * Set true to show three dimensional graph (If a type of chart got no 3d
	 * peer, this is ignored).
	 */
	public void setThreeD(boolean b);

	/**
	 * Whether a 3d chart.
	 */
	public boolean isThreeD();

	/**
	 * Set the chart's title.
	 * 
	 * @param title
	 *            the chart's title.
	 * 
	 */
	public void setTitle(String title);

	/**
	 * Get the chart's title.
	 */
	public String getTitle();

	/**
	 * Set the label in xAxis.
	 * 
	 * @param label
	 *            label in xAxis.
	 */
	public void setXAxis(String label);

	/**
	 * Get the label in xAxis.
	 */
	public String getXAxis();

	/**
	 * Set the label in yAxis.
	 * 
	 * @param label
	 *            label in yAxis.
	 */
	public void setYAxis(String label);

	/**
	 * Get the label in yAxis.
	 */
	public String getYAxis();
	public void setOrient(String orient);

	/**
	 * Get the chart orientation (vertical or horizontal)
	 */
	public String getOrient();

	/**
	 * Returns the time zone that this Time Series Chart belongs to, or null if
	 * the default time zone is used.
	 * <p>
	 * The default time zone is determined by
	 * {@link org.zkoss.util.TimeZones#getCurrent}.
	 */
	public TimeZone getTimeZone();

	/**
	 * Sets the time zone that this Time Series Chart belongs to, or null if the
	 * default time zone is used.
	 * <p>
	 * The default time zone is determined by
	 * {@link org.zkoss.util.TimeZones#getCurrent}.
	 */
	public void setTimeZone(TimeZone tzone);

	/**
	 * Returns the date format used by date related Chart.
	 * 
	 * @return the date format used by date related Chart..
	 */
	public String getDateFormat();

	/**
	 * Sets the date format used by date related Chart.
	 * 
	 * @param format
	 */
	public void setDateFormat(String format);

	/**
	 * Returns the chart model associated with this chart, or null if this chart
	 * is not associated with any chart data model.
	 */
	public ChartModel getModel();

	/**
	 * Sets the chart model associated with this chart. If a non-null model is
	 * assigned, no matter whether it is the same as the previous, it will
	 * always cause re-render.
	 * 
	 * @param model
	 *            the chart model to associate, or null to dis-associate any
	 *            previous model.
	 * @exception UiException
	 *                if failed to initialize with the model
	 */
	public void setModel(ChartModel model);

	/**
	 * Sets the model by use of a class name. It creates an instance
	 * automatically.
	 */
	public void setModel(String clsnm) throws ClassNotFoundException,
			NoSuchMethodException, IllegalAccessException,
			InstantiationException, java.lang.reflect.InvocationTargetException;
}
