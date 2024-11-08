package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.PlotBO;
import com.scube.crm.vo.PlotVO;

public interface PlotDao {

	PlotBO createPlot(PlotVO plotVo);

	long PlotCount(PlotVO plotVo);

	List<PlotVO> getlistPlot(PlotVO plotVO);

	PlotVO selectPlot(PlotVO plotvo);

	PlotBO PlotUpdate(PlotVO plotVO);

	Boolean deleteplot(PlotVO plotvo);

	boolean checkPlotNumbers(String plotNumbers, long companyId);

	

}
