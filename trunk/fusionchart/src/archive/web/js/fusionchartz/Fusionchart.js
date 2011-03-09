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
		_swfPath = zk.ajaxURI('/web/js/fchart/ext/Charts/', {au: true});
		_swfPath = _swfPath.substr(0, _swfPath.lastIndexOf("/")+1);
    
	function _updateChart(wgt) {
		var fusionChartXML;
		if (!(fusionChartXML = wgt._fusionChartXML) || !wgt.isRealVisible())
			return;
		updateChartXML('Chart_' + wgt.uuid, fusionChartXML); 
		wgt._shallUpdate = false;
	}
	
	function _createChart(wgt) {
		var fusionChartXML;
		if (!(fusionChartXML = wgt._fusionChartXML) || !wgt.isRealVisible())
			return;
			
		var chart = new FusionCharts(_swfPath + _getSwf(wgt), 
						'Chart_' + wgt.uuid, zk.parseInt(wgt._width), zk.parseInt(wgt._height));
		chart.setDataXML(fusionChartXML);
		chart.setTransparent(true);
		chart.render(wgt.$n());
		wgt._fusionchart = chart;
		wgt._shallRedraw = false;
	}
	
	function _initChart(wgt) {
		if (wgt.desktop) {
			if (wgt._fusionchart)
				_updateChart(wgt)
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
	
	function _getSwf(wgt) {
		var type = wgt._type,
			threeD = wgt._threeD,
			orient = wgt._orient,
			isChartTypeNoMatch;
			
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
					return wgt._threeD ? 'FCF_StackedColumn3D.swf': 'FCF_StackedColumn2D.swf';
				else if (orient == 'horizontal' && !_validateThreeD(type, threeD))
			    	return 'FCF_StackedBar2D.swf';
				break;
			case Chart.STACKED_AREA:
				if (!_validateThreeD(type, threeD))
					return 'FCF_StackedArea2D.swf';
				break;
			case Chart.COMBINATION:
				if (orient == 'vertical')
					return wgt._threeD ? 'FCF_MSColumn3DLineDY.swf': 'FCF_MSColumn2DLineDY.swf';
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
	
	
var Fusionchart =
/**
 * The Fusionchart component. Developers set proper chart type, data model,
 * and the threeD (3D) attribute to draw proper chart.
 *
 * @author jimmyshiau
 */
fusionchartz.Fusionchart = zk.$extends(zk.Widget, {
	_type: Chart.PIE,
	_orient: 'vertical',
	$define: {
		/**
		 * Get the chart's type.
		 * @return String
		 */
		/**
		 * Set the chart's type (Chart.PIE, Chart.BAR, Chart.LINE, etc.).
		 * <p>Default: pie.
		 * @param String type
		 */
	    type: zkf = function () {
			this._shallRedraw = true;
		},
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
		fusionChartXML: function () {
			this._shallRedraw = true;
			this._shallUpdate = true;
			_redraw(this);
		}
	},
	
	bind_: function() {
		this.$supers(Fusionchart, 'bind_', arguments);
		zWatch.listen({onShow: this, onResponse: this});
		this._shallRedraw = true
		_redraw(this);
	},
	
	unbind_: function () {
		this._fusionchart = null;
		zWatch.unlisten({onShow: this, onResponse: this});
		this.$supers(Fusionchart, 'unbind_', arguments);
	},
	
	onShow: function () {
		if (this.desktop)
			_createChart(this);
	},
	
	onResponse: function () {
		_redraw(this);
	},
	
	getZclass: function () {
		var zcls = this._zclass;
		return zcls != null ? zcls: "z-fusionchart";
	},
	
	clickChart: function (series, category) {
		this.fire('onClick', {series: zk.parseInt(series), category: zk.parseInt(category)});
	}
});		
})();