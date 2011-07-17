/* GanttTableRowRenderer.java

	Purpose:
		
	Description:
		
	History:
		Mar 23, 2011 5:38:33 PM, Created by jimmy

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

This program is distributed under GPL Version 3.0 in the hope that
it will be useful, but WITHOUT ANY WARRANTY.
*/
package org.zkoss.fusionchart.renderer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.fusionchart.config.ChartInfoNotifier;
import org.zkoss.fusionchart.config.GanttTableConfig;
import org.zkoss.fusionchart.config.GanttTableConfig.GanttRow;
import org.zkoss.fusionchart.config.GanttTableConfig.GanttTableColumnConfig;
import org.zkoss.fusionchart.config.GanttTableConfig.GanttTableColumnProperties;
import org.zkoss.fusionchart.config.PropertiesRenderProxy;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.lang.Exceptions;
import org.zkoss.zul.ListModel;

/**
 * The GanttTableRenderer was used to render 
 * the Fusionchart XML String with GanttTableModel ({@link ListModel}).
 * @author jimmy
 *
 */
public abstract class GanttTableRenderer extends ChartInfoNotifier {
	private static final long serialVersionUID = 20110323182910L;
	
	private GanttTableConfig _tableConfig;
	
	public GanttTableRenderer() {
		defineTableProperty(_tableConfig = new GanttTableConfig());
		addPropertyListener((ChartInfoNotifier)_tableConfig);
	}
	
	public abstract void defineTableProperty(final GanttTableConfig tableConfig);
	
	public abstract void render(GanttRow row, Object data) throws Exception;
	
	public void renderTableProperties(StringBuffer sb) {
		Utils.renderChartProperties(sb, _tableConfig);
	}
	
	public void renderColumnProperties(StringBuffer sb, int index) {
		GanttTableColumnConfig config = 
			PropertiesRenderProxy.getColumnConfig(_tableConfig); 
		if (config != null)
			Utils.renderChartProperties(sb, config.getColumnProperties(index));
	}
	
	public void renderTextProperties(StringBuffer sb, int cIndex, int tIndex) {
		GanttTableColumnConfig config = 
			PropertiesRenderProxy.getColumnConfig(_tableConfig); 
		if (config != null) {
			GanttTableColumnProperties cprops = config.getColumnProperties(cIndex);
			if (cprops != null)
				Utils.renderChartProperties(sb, cprops.getTextProperties(tIndex));
		}
	}
	
	/*package*/ List createColumnList(ListModel model) {
		List columnList = new ArrayList(4);
		for (int i = 0, j = model.getSize(); i < j; i++) {
			GanttRow row = new GanttRow();
			addPropertyListener(row);
			try {
				render(row, model.getElementAt(i));
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
				labels.add(row.getLabel(k));
			}
		}
		return columnList;
	}
	
	public static final GanttTableRenderer getDefalutRenderer () {
		return new GanttTableRenderer () {
			private static final long serialVersionUID = 20110717110710L;
			public void defineTableProperty(GanttTableConfig property) {}
			public void render(GanttRow row, Object data) throws Exception {
				row.createLabel(String.valueOf(data));
			}
		};
	}
}
