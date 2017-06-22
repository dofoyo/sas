package com.rhb.sas.stock.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.*;

import com.rhb.af.bean.Page;

import com.rhb.af.web.struts.action.BaseAction;

public class StockReportListAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String stockNo = "";		
		try {
			Page page = (Page)PropertyUtils.getProperty(form,"page");
			Object object = page.getObject();
			stockNo = BeanUtils.getSimpleProperty(object, "stockNo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ActionForward("/reportViewListAction.do?query.stockNo="+ stockNo + "&query.orderBy=theYear");
	}
}
