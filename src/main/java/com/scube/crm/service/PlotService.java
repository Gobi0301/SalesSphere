package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.PlotBO;

public interface PlotService {

	PlotBO createPlot(PlotBO plotBO);

	long PlotCount(PlotBO plotBO);

	List<PlotBO> listPlot(PlotBO plotBO);

	PlotBO selectPlot(PlotBO plotBo);

	PlotBO PlotUpdate(PlotBO plotBO);

	boolean deletePlot(PlotBO plotBo);

	boolean checkPlotNumbers(String plotNumbers, long companyId);

	

	

}
