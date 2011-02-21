package org.zkoss.zkfusionchartdemo;

import java.util.*;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.GanttModel.GanttTask;
import org.zkoss.fusionchart.*;
import org.zkoss.fusionchart.FusionchartCategoryModel.*;
import org.zkoss.fusionchart.FusionchartGanttModel.*;



public class FusionchartDemoComposer extends GenericForwardComposer {
	private boolean threeD = false;
	private boolean vertical = true;
	private boolean stacked = false;

	public boolean isThreeD() {
		return threeD;
	}

	public void setThreeD(boolean threeD) {
		this.threeD = threeD;
	}

	public boolean isThreeD2() {
		return !threeD;
	}

	public void setThreeD2(boolean threeD2) {
		this.threeD = threeD2;
	}

	public boolean isVertical() {
		return vertical;
	}

	public String getOrient() {
		return vertical ? "vertical" : "horizontal";
	}

	public String getOrient2() {
		return !vertical ? "vertical" : "horizontal";
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public boolean isStacked() {
		return stacked;
	}

	public void setStacked(boolean stacked) {
		this.stacked = stacked;
	}

	public String getType() {
		return stacked ? "stacked_bar" : "bar";
	}

	public String getType2() {
		return stacked ? "stacked_bar" : "bar";
	}

	// @Override
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		comp.setAttribute("catmodel", initCategoryModel());
		comp.setAttribute("catmodel2", initCategoryModel2());
		comp.setAttribute("catmodel3", initCategoryModel3());
		comp.setAttribute("xymodel", initXYModel());
		comp.setAttribute("piemodel", initPieModel());
		comp.setAttribute("ganttmodel", initGanttModel());

		comp.setAttribute("fcatmodel", initFCategoryModel());
		comp.setAttribute("fcatmodel2", initFCategoryModel2());
		comp.setAttribute("fcatmodel3", initFCategoryModel3());
		comp.setAttribute("fxymodel", initFXYModel());
		comp.setAttribute("fpiemodel", initFPieModel());
		comp.setAttribute("fganttmodel", initFGanttModel());

	}

	private ChartModel initCategoryModel() {
		CategoryModel catmodel = new SimpleCategoryModel();
		catmodel.setValue("2001", "Q1", new Integer(20));
		catmodel.setValue("2001", "Q2", new Integer(35));
		catmodel.setValue("2001", "Q3", new Integer(40));
		catmodel.setValue("2001", "Q4", new Integer(55));
		catmodel.setValue("2002", "Q1", new Integer(40));
		catmodel.setValue("2002", "Q2", new Integer(60));
		catmodel.setValue("2002", "Q3", new Integer(70));
		catmodel.setValue("2002", "Q4", new Integer(90));
		return catmodel;
	}

	private ChartModel initCategoryModel2() {
		CategoryModel catmodel = new SimpleCategoryModel();
		catmodel.setValue("Product A", "08/01", new Integer(20));
		catmodel.setValue("Product A", "08/02", new Integer(35));
		catmodel.setValue("Product A", "08/03", new Integer(40));
		catmodel.setValue("Product A", "08/04", new Integer(55));
		catmodel.setValue("Product B", "08/01", new Integer(40));
		catmodel.setValue("Product B", "08/02", new Integer(60));
		catmodel.setValue("Product B", "08/03", new Integer(70));
		catmodel.setValue("Product B", "08/04", new Integer(90));
		return catmodel;
	}

	private ChartModel initCategoryModel3() {
		CategoryModel catmodel = new SimpleCategoryModel();
		FusionchartCombinSeries productA = new FusionchartCombinSeries(
				"Product A", 128, "P");
		FusionchartCombinSeries productB = new FusionchartCombinSeries(
				"Product B", 128, "P");
		FusionchartCombinSeries productC = new FusionchartCombinSeries(
				"Product C", 128, "S");
		catmodel.setValue(productA, "08/01", new Integer(20));
		catmodel.setValue(productA, "08/02", new Integer(35));
		catmodel.setValue(productA, "08/03", new Integer(40));
		catmodel.setValue(productA, "08/04", new Integer(55));
		catmodel.setValue(productB, "08/01", new Integer(40));
		catmodel.setValue(productB, "08/02", new Integer(60));
		catmodel.setValue(productB, "08/03", new Integer(70));
		catmodel.setValue(productB, "08/04", new Integer(90));
		catmodel.setValue(productC, "08/01", new Integer(90));
		catmodel.setValue(productC, "08/02", new Integer(30));
		catmodel.setValue(productC, "08/03", new Integer(60));
		catmodel.setValue(productC, "08/04", new Integer(10));
		return catmodel;
	}

	private XYModel initXYModel() {
		XYModel xymodel = new SimpleXYModel();
		xymodel.addValue("2001", new Integer(20), new Integer(120));
		xymodel.addValue("2001", new Integer(40), new Integer(135));
		xymodel.addValue("2001", new Integer(60), new Integer(140));
		xymodel.addValue("2001", new Integer(80), new Integer(160));
		xymodel.addValue("2001", new Integer(25), new Integer(120));
		xymodel.addValue("2001", new Integer(75), new Integer(135));
		xymodel.addValue("2001", new Integer(65), new Integer(140));
		xymodel.addValue("2001", new Integer(85), new Integer(160));
		xymodel.addValue("2002", new Integer(30), new Integer(120));
		xymodel.addValue("2002", new Integer(31), new Integer(135));
		xymodel.addValue("2002", new Integer(32), new Integer(140));
		xymodel.addValue("2002", new Integer(90), new Integer(160));
		xymodel.addValue("2002", new Integer(35), new Integer(120));
		xymodel.addValue("2002", new Integer(55), new Integer(135));
		xymodel.addValue("2002", new Integer(75), new Integer(140));
		xymodel.addValue("2002", new Integer(80), new Integer(160));
		return xymodel;
	}

	private PieModel initPieModel() {
		PieModel piemodel = new SimplePieModel();
		piemodel.setValue("C/C++", new Double(12.5));
		piemodel.setValue("Java", new Double(50.2));
		piemodel.setValue("VB", new Double(20.5));
		piemodel.setValue("PHP", new Double(15.5));
		return piemodel;
	}

	private GanttModel initGanttModel() {
		GanttModel ganttmodel = new GanttModel();

		String scheduled = "Scheduled";
		String actual = "Actual";

		ganttmodel.addValue(
				scheduled,
				new GanttTask("Write Proposal", date(2008, 4, 1), date(2008, 4,
						5), 0.0));
		ganttmodel.addValue(scheduled, new GanttTask("Requirements Analysis",
				date(2008, 4, 10), date(2008, 5, 5), 0.0));
		ganttmodel.addValue(
				scheduled,
				new GanttTask("Design Phase", date(2008, 5, 6), date(2008, 5,
						30), 0.0));
		ganttmodel.addValue(scheduled, new GanttTask("Alpha Implementation",
				date(2008, 6, 3), date(2008, 7, 31), 0.0));

		ganttmodel.addValue(
				actual,
				new GanttTask("Write Proposal", date(2008, 4, 1), date(2008, 4,
						3), 0.0));
		ganttmodel.addValue(actual, new GanttTask("Requirements Analysis",
				date(2008, 4, 10), date(2008, 5, 15), 0.0));
		ganttmodel.addValue(
				actual,
				new GanttTask("Design Phase", date(2008, 5, 15), date(2008, 6,
						17), 0.0));
		ganttmodel.addValue(
				actual,
				new GanttTask("Alpha Implementation", date(2008, 7, 1), date(
						2008, 9, 12), 0.0));
		return ganttmodel;
	}

	public Date date(int year, int month, int day) {
		final java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}

	private ChartModel initFCategoryModel() {
		FusionchartCategoryModel catmodel = new FusionchartCategoryModel();

		FusionchartSeries y2001 = new FusionchartSeries("2001", null, 100, true);
		FusionchartSeries y2002 = new FusionchartSeries("2002", 50);

		FusionchartCategory Q1 = new FusionchartCategory("Q1");
		FusionchartCategory Q2 = new FusionchartCategory("Q2", "Q3 ....", false);
		FusionchartCategory Q3 = new FusionchartCategory("Q3", "Q3 ....");
		FusionchartCategory Q4 = new FusionchartCategory("Q4", "Q4 good");

		catmodel.setValue(y2001, Q1, new Integer(20), "#00FF00", null, 150);
		catmodel.setValue(y2001, Q2, new Integer(35));
		catmodel.setValue(y2001, Q3, new Integer(40));
		catmodel.setValue(y2001, Q4, new Integer(55), "#885022", null, 120);
		catmodel.setValue(y2002, Q1, new Integer(40));
		catmodel.setValue(y2002, Q2, new Integer(60));
		catmodel.setValue(y2002, Q3, new Integer(70), "#EDEF0E", null, 70);
		catmodel.setValue(y2002, Q4, new Integer(90));
		return catmodel;
	}

	private ChartModel initFCategoryModel2() {
		FusionchartCategoryModel catmodel = new FusionchartCategoryModel();

		FusionchartAreaSeries productA = new FusionchartAreaSeries("Product A",
				"#0EEF2C", 100, true, true, new Integer(10), "#EF0E6C");
		FusionchartAreaSeries productB = new FusionchartAreaSeries("Product B",
				50);

		FusionchartCategory D1 = new FusionchartCategory("08/01");
		FusionchartCategory D2 = new FusionchartCategory("08/02", "08/02 ....",
				false);
		FusionchartCategory D3 = new FusionchartCategory("08/03", "08/03 ....");
		FusionchartCategory D4 = new FusionchartCategory("08/04", "08/04 good");

		catmodel.setValue(productA, D1, new Integer(20), "#00FF00", null, 150);
		catmodel.setValue(productA, D2, new Integer(40));
		catmodel.setValue(productA, D3, new Integer(35));
		catmodel.setValue(productA, D4, new Integer(55), "#885022", null, 120);
		catmodel.setValue(productB, D1, new Integer(40));
		catmodel.setValue(productB, D2, new Integer(60));
		catmodel.setValue(productB, D3, new Integer(70));
		catmodel.setValue(productB, D4, new Integer(90), "#EDEF0E", null, 70);
		return catmodel;
	}

	private ChartModel initFCategoryModel3() {
		FusionchartCategoryModel catmodel = new FusionchartCategoryModel();
		FusionchartCombinSeries productA = new FusionchartCombinSeries(
				"Product A", 128, "P");

		FusionchartCombinSeries productB = new FusionchartCombinSeries(
				"Product B", 128, "P");

		AnchorProperty anchor = new AnchorProperty(true, new Integer(15),
				new Integer(10), "#00FFFA", new Integer(10), "#C300FF", 150,
				180);

		FusionchartCombinSeries productC = new FusionchartCombinSeries(
				"Product B", "#FF7200", 200, true, new Integer(5),
				anchor, "S", "$", " US");

		FusionchartCategory D1 = new FusionchartCategory("Q1");
		FusionchartCategory D2 = new FusionchartCategory("Q2", "Q3 ....", false);
		FusionchartCategory D3 = new FusionchartCategory("Q3", "Q3 ....");
		FusionchartCategory D4 = new FusionchartCategory("Q4", "Q4 good");

		catmodel.setValue(productA, D1, new Integer(20));
		catmodel.setValue(productA, D2, new Integer(35), "#00FF00", null, 150);
		catmodel.setValue(productA, D3, new Integer(40), "#885022", null, 120);
		catmodel.setValue(productA, D4, new Integer(55));
		catmodel.setValue(productB, D1, new Integer(40));
		catmodel.setValue(productB, D2, new Integer(60));
		catmodel.setValue(productB, D3, new Integer(70), "#EDEF0E", null, 70);
		catmodel.setValue(productB, D4, new Integer(90));
		catmodel.setValue(productC, D1, new Integer(90));
		catmodel.setValue(productC, D2, new Integer(30));
		catmodel.setValue(productC, D3, new Integer(60));
		catmodel.setValue(productC, D4, new Integer(10));
		return catmodel;
	}

	private XYModel initFXYModel() {
		FusionchartXYModel xymodel = new FusionchartXYModel();

		AnchorProperty anchor = new AnchorProperty(true, new Integer(8), new Integer(3), "#00FFFA", new Integer(3),
				"#C300FF", 150, 180);

		FusionchartLineSeries y2001 = new FusionchartLineSeries("2001",
				"#FF00FA", 60, false, new Integer(2), null);
		FusionchartLineSeries y2002 = new FusionchartLineSeries("2002",
				"#00FF3A", 60, true, new Integer(4), anchor);

		xymodel.addValue(y2001, new Integer(20), new Integer(120), null, 50, -1);
		xymodel.addValue(y2001, new Integer(40), new Integer(135));
		xymodel.addValue(y2001, new Integer(60), new Integer(140));
		xymodel.addValue(y2001, new Integer(80), new Integer(160));
		xymodel.addValue(y2001, new Integer(25), new Integer(120));
		xymodel.addValue(y2001, new Integer(75), new Integer(135), null, 100,
				-1);
		xymodel.addValue(y2001, new Integer(65), new Integer(140));
		xymodel.addValue(y2001, new Integer(85), new Integer(160));
		xymodel.addValue(y2002, new Integer(30), new Integer(120));
		xymodel.addValue(y2002, new Integer(31), new Integer(135));
		xymodel.addValue(y2002, new Integer(32), new Integer(140));
		xymodel.addValue(y2002, new Integer(90), new Integer(160));
		xymodel.addValue(y2002, new Integer(35), new Integer(120), null, 200,
				-1);
		xymodel.addValue(y2002, new Integer(55), new Integer(135));
		xymodel.addValue(y2002, new Integer(75), new Integer(140));
		xymodel.addValue(y2002, new Integer(80), new Integer(160));
		return xymodel;
	}

	private PieModel initFPieModel() {
		FusionchartPieModel piemodel = new FusionchartPieModel();
		piemodel.setValue("C/C++", new Double(12.5), "#EFDB00", "C/C++....",
				60, null, false);
		piemodel.setValue("Java", new Double(50.2), "#DF0D3D", "Java  good!!!",
				80, null, true);
		piemodel.setValue("VB", new Double(20.5), "#5F7F0F", "VB....", 100,
				null, false);
		piemodel.setValue("PHP", new Double(15.5), "#EF6B00", "PHP....", 120,
				null, false);
		return piemodel;
	}

	private GanttModel initFGanttModel() {
		FusionchartGanttModel ganttmodel = new FusionchartGanttModel();

		FusionchartSeries scheduled = new FusionchartGanttSeries("Scheduled",
				"#885022", 100, true, null, new Border("#FF0000", 5, 200));
		FusionchartSeries actual = new FusionchartGanttSeries("Actual", true);

		ganttmodel.addValue(scheduled, new FusionchartGanttTask(
				"Write Proposal", date(2008, 4, 1), date(2008, 4, 5), 0.0));
		ganttmodel.addValue(scheduled, new FusionchartGanttTask(
				"Requirements Analysis", date(2008, 4, 10), date(2008, 5, 5),
				0.0));
		ganttmodel.addValue(scheduled, new FusionchartGanttTask("Design Phase",
				date(2008, 5, 6), date(2008, 5, 30), 0.0));
		ganttmodel.addValue(scheduled, new FusionchartGanttTask(
				"Alpha Implementation", date(2008, 6, 3), date(2008, 7, 31),
				0.0));

		ganttmodel.addValue(actual, new FusionchartGanttTask("Write Proposal",
				date(2008, 4, 1), date(2008, 4, 3), "#00FF00", 150,
				"Write Proposal.....", null, true, new Integer(10), null, new Border(
						"#00FF00", 2, 180)));
		ganttmodel.addValue(actual, new FusionchartGanttTask(
				"Requirements Analysis", date(2008, 4, 10), date(2008, 5, 15),
				"#DF0D3D"));
		ganttmodel.addValue(actual, new FusionchartGanttTask("Design Phase",
				date(2008, 5, 15), date(2008, 6, 17)));
		ganttmodel.addValue(actual, new FusionchartGanttTask(
				"Alpha Implementation", date(2008, 7, 1), date(2008, 9, 12),
				"#EDEF0E", 90, "Alpha Implementation.....", null, true, new Integer(15),
				null, new Border("#0000FF", 3, 120), true, false, false));
		return ganttmodel;
	}

	public void onChartClick(ForwardEvent evt) {
		doCategoryChartClick(evt);
	}

	private void doCategoryChartClick(ForwardEvent evt) {
		Event event = (Event) evt.getOrigin();
		if (event instanceof MouseEvent) {
			Component obj = ((MouseEvent) event).getAreaComponent();

			if (obj != null) {
				Area area = (Area) obj;
				System.out.print(area.getAttribute("series") + " = "
						+ area.getTooltiptext());
			}
		} else {
			Map data = (Map) event.getData();
			ChartModel model = ((Fusionchart) event.getTarget()).getModel();
			if (model instanceof CategoryModel) {
				System.out.print(data.get("series") + ", "
						+ data.get("category") + " = " + data.get("value"));
			} else if (model instanceof PieModel) {
				System.out.print(data.get("category") + " = "
						+ data.get("value"));
			} else if (model instanceof XYModel) {
				System.out.print(data.get("series") + " = " + data.get("x")
						+ ", " + data.get("y"));
			} else if (model instanceof GanttModel) {
				GanttModel.GanttTask task = (GanttTask) data.get("task");
				System.out.print(data.get("series") + ", "
						+ task.getDescription() + " = " + task.getStart()
						+ " ~ " + task.getEnd());
			}
		}

		System.out.println();
	}
}