/* Fusionchart.js
 Purpose:
 
 Description:
 
 History:
 Sun Jan 9 18:15:21 TST 2011, Created by jimmyshiau
 Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 This program is distributed under GPL Version 3.0 in the hope that
 it will be useful, but WITHOUT ANY WARRANTY.
 */
(function(){
	
	var Chart = {
			PIE: 'pie',
			BAR: 'bar',
			LINE: 'line',
			AREA: 'area',
			STACKED_BAR: 'stacked_bar',
			STACKED_AREA: 'stacked_area',
			GANTT: 'gantt',
			COMBINATION: 'combination'
		},
		_swfPath = zk.ajaxURI('/web/js/fusionchartz/ext/Charts/', {au: true});
	_swfPath = _swfPath.substr(0, _swfPath.lastIndexOf("/")+1);
    
	function _getSwf(wgt) {
		var type = wgt._type,
			threeD = wgt._threeD,
			orient = wgt._orient;
			
		switch(type) {
			case Chart.PIE:
				return threeD ? 'FCF_Pie3D.swf': '	FCF_Pie2D.swf';
				break;
			case Chart.BAR:
				if (orient == 'vertical')
					return threeD ? 'FCF_MSColumn3D.swf': 'FCF_MSColumn2D.swf';
				else if (orient == 'horizontal' && !_validateThreeD(orient + ' ' +type, threeD))
			    	return 'FCF_MSBar2D.swf';
				break;
			case Chart.LINE:
				if (!_validateThreeD(type, threeD))
					return 'FCF_MSLine.swf';
				break;
			case Chart.AREA:
				if (!_validateThreeD(type, threeD))
					return 'FCF_MSArea2D.swf';
				break;
			case Chart.STACKED_BAR:
				if (orient == 'vertical')
					return threeD ? 'FCF_StackedColumn3D.swf': 'FCF_StackedColumn2D.swf';
				else if (orient == 'horizontal' && !_validateThreeD(type, threeD))
			    	return 'FCF_StackedBar2D.swf';
				break;
			case Chart.STACKED_AREA:
				if (!_validateThreeD(type, threeD))
					return 'FCF_StackedArea2D.swf';
				break;
			case Chart.COMBINATION:
				if (orient == 'vertical')
					return threeD ? 'FCF_MSColumn3DLineDY.swf': 'FCF_MSColumn2DLineDY.swf';
				else if (orient == 'horizontal')
			    	jq.alert('Unsupported chart type yet: ' + type + ' in horizontal.',
						{icon: 'ERROR'});
				break;
			case Chart.GANTT:
				if (!_validateThreeD(type, threeD))
					return 'FCF_Gantt.swf';
				break;
			default:
				jq.alert('Unsupported chart type yet: ' + type, {icon: 'ERROR'});
		}
	}
	
	function _validateThreeD(type, threeD) {
		if (threeD) {
			jq.alert('Unsupported chart type yet: ' + type + ' in threeD.',
				{icon: 'ERROR'});
			return true;
		}
	}
	
	function _updateChart(wgt) {
		var dataXML, chart;
		if (!(dataXML = wgt._dataXML) || !wgt.isRealVisible())
			return;
		if (chart = wgt._fusionchart) {
//			chart.setDataXML(dataXML); 
			updateChartXML('Chart_' + wgt.uuid, dataXML);
			wgt._shallUpdate = false;
		}
	}
	
	function _createChart(wgt) {
		var dataXML, dataXMLPath;
		if (!((dataXML = wgt._dataXML) || (dataXMLPath = wgt._dataXMLPath)) 
			|| !wgt.isRealVisible())
			return;
		
		var n = wgt.$n(),
			width = n.style.width || wgt._intWidth,
			height = n.style.height || wgt._intHeight,
			chart = new FusionCharts(_swfPath + _getSwf(wgt), 
				'Chart_' + wgt.uuid, zk.parseInt(width), zk.parseInt(height));
		
		if (dataXML)
			chart.setDataXML(dataXML);
		else if (dataXMLPath) chart.setDataURL(dataXMLPath);
		
		chart.setTransparent(true);
		chart.render(wgt.$n());
		wgt._fusionchart = chart;
		wgt._shallRedraw = wgt._shallUpdate = false;
	}
	
	function _initChart(wgt) {
		if (wgt.desktop) {
			if (wgt._fusionchart)
				_updateChart(wgt);
			else
				_createChart(wgt);
		}
	}
	
	function _redraw(wgt) {
		if (wgt._shallRedraw) {
			if (wgt._shallUpdate)
				_initChart(wgt);
			else _createChart(wgt);
		}
	}
	
	function _syncChart(wgt) {
		if (!wgt.desktop) return;
		
		if (wgt._shallRedraw)
			_createChart(wgt);
		else _updateChart(wgt);
	}
	
	
var Fusionchart =
/**
 * The Fusionchart component. Developers set proper chart type, data model,
 * and the threeD (3D) attribute to draw proper chart.
 *
 * @author jimmyshiau
 */
fusionchartz.Fusionchart = zk.$extends(zk.Widget, {
	_intWidth: 400,
	_intHeight: 200,
	_type: Chart.PIE,
	_orient: 'vertical',
	$define: {
		intWidth: zkf = function () {
			this._shallUpdate = true;
			this._shallRedraw = true;
		},
		intHeight: zkf,
		/**
		 * Get the chart's type.
		 * @return String
		 */
		/**
		 * Set the chart's type (Chart.PIE, Chart.BAR, Chart.LINE, etc.).
		 * <p>Default: pie.
		 * @param String type
		 */
	    type: zkf,
		/**
		 * Whether a 3d chart.
		 * @return boolean
		 */
		/**
		 * Set true to show three dimensional graph (If a type of chart got no 3d peer, this is ignored).
		 * @param boolean isThreeD
		 */
		threeD: zkf,
		/**
		 * Get the chart orientation (vertical or horizontal)
		 * @return String
		 */
		/**
		 * Set the chart orientation.
		 * @param String orient vertical or horizontal (default to vertical)
		 */
		orient: zkf,
		/**
		 * Get the XML string of the chart data.
		 * @return String
		 */
		/**
		 * Sets the XML string for render the chart data.
		 * @param String XMLString
		 */
		dataXML: function () {
			this._shallUpdate = true;
		},
		/**
		 * Get the url of the chart data .xml file. See Fusionchart Free's 
		 * <a href="http://www.fusioncharts.com/free/docs/">
		 * FusionCharts Free Documentation</a> for details.
		 */
		/**
		 * Set the url of the chart data .xml file. See Fusionchart Free's 
		 * <a href="http://www.fusioncharts.com/free/docs/">
		 * FusionCharts Free Documentation</a> for details.
		 * @param url the url path for the data xml path
		 */
		dataXMLPath: function () {
//			this._shallRedraw = true;
//			this._shallUpdate = true;
//			_redraw(this);
		}
	},
	
	bind_: function() {
		this.$supers(Fusionchart, 'bind_', arguments);
		zWatch.listen({onShow: this, onResponse: this, onSize: this});
		_createChart(this);
	},
	
	unbind_: function () {
		this._fusionchart = this._shallRedraw = this._shallUpdate = null;
		zWatch.unlisten({onShow: this, onResponse: this, onSize: this});
		this.$supers(Fusionchart, 'unbind_', arguments);
	},
	
	onSize: _zkf = function () {
		_createChart(this);
	},
	onShow: _zkf,
	
	onResponse: function () {
		if (this._shallUpdate) {
			_syncChart(this);
			this._shallUpdate = false;
		}
	},
	
	getZclass: function () {
		var zcls = this._zclass;
		return zcls != null ? zcls: "z-fusionchart";
	},
	
	doClick_: function () {
		//do not thing
	},
	
	clickChart: function (series, category, index) {
		this.fire('onClick', {
			series: zk.parseInt(series), 
			category: zk.parseInt(category),
			index: zk.parseInt(index)});
	}
});		
})();