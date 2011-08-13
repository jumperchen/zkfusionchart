/* GanttTableRowRenderer.java

	Purpose:
		
	Description:
		
	History:
		Mar 23, 2011 5:38:33 PM, Created by jimmy

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

This program is distributed under GPL Version 3.0 in the hope that
it will be useful, but WITHOUT ANY WARRANTY.
*/
package org.zkoss.fusionchart.api;

import java.io.Serializable;

import org.zkoss.fusionchart.config.GanttTableConfig.GanttRow;
import org.zkoss.zul.ListModel;

/**
 * The GanttTableRenderer was used to render 
 * the Fusionchart XML String with GanttTableModel ({@link ListModel}).
 * @author jimmy
 *
 */
public interface GanttTableRenderer extends Serializable {
	public void render(GanttRow row, Object data) throws Exception;
	
}
