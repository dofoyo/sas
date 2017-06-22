package com.rhb.sas.interfaces.downloadreport.sina;

import java.util.ArrayList;
import java.util.List;

import com.rhb.sas.application.console.Sensor;
import com.rhb.sas.interfaces.downloadreport.DownloadReport;
import com.rhb.sas.interfaces.downloadreport.dto.BalanceSheetDTO;
import com.rhb.sas.interfaces.downloadreport.dto.CashFlowDTO;
import com.rhb.sas.interfaces.downloadreport.dto.ReportInfoDTO;
import com.rhb.sas.util.FileUtil;
import com.rhb.sas.util.HttpBrowser;
import com.rhb.sas.util.Tools;

public class DownloadCashFlowFromSina implements DownloadReport {
	private Sensor sensor;
	private String path = "d:\\stocks\\stockNo_CashFlow.html";
	
	@Override
	public List doIt(String stockNo, java.util.Date reportDate) {
		//System.out.println("*** DownloadCashFlowFromSina ***");
		return downloadCashFlowByFile(stockNo, reportDate);
	}

	private List<CashFlowDTO> downloadCashFlowByFile(String stockNo, java.util.Date reportDate){
		List<CashFlowDTO> list = new ArrayList();
		String url = path.replace("stockNo", stockNo);
		try {
			String result = FileUtil.readTextFile(url);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result,reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				CashFlowDTO dto = parseCashFlow(result,ri); 
				if(dto != null){
					list.add(dto);					
				}
			}
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
			//e.printStackTrace();
		}

		return list;
	}
	
	private List<CashFlowDTO> downloadCashFlowByWeb(String stockNo, java.util.Date reportDate){
		List<CashFlowDTO> list = new ArrayList();
		String url = "http://money.finance.sina.com.cn/corp/go.php/vFD_CashFlow/stockid/stockNo/ctrl/part.phtml"; // �ֽ�������
		url = url.replace("stockNo", stockNo);
		HttpBrowser hb = new HttpBrowser();
		try {
			String result = hb.Browser(url);
			List<ReportInfoDTO> ris = SinaTools.getReportInfo(result,reportDate);
			for(ReportInfoDTO ri : ris){
				ri.setStockNo(stockNo);
				CashFlowDTO dto = parseCashFlow(result,ri); 
				if(dto != null){
					list.add(dto);					
				}
			}
		} catch (Exception e) {
			sensor.setMessage("********** error **********");
			sensor.setMessage(e.toString());
			//e.printStackTrace();
		}

		return list;
	}

	
	// �����ֽ�������
	private CashFlowDTO parseCashFlow(String str,ReportInfoDTO report) {			
		CashFlowDTO cf = new CashFlowDTO(report);

		String findPeriodRegexp = "������</strong></td>|</tr>";  //��������  //������
		if(str.indexOf("��������") != -1){
			findPeriodRegexp = "��������</strong></td>|</tr>";			
		}
		List<String> period = Tools.subStrings(str, findPeriodRegexp);
		int i = SinaTools.findTable(period,Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		if(i == -1){
			sensor.setMessage("******* parseCashFlow,  " + Tools.getDate(report.getEndDate(), "yyyy-MM-dd") + " dose NOT exist! ****");
			return null;
		}
		int j = SinaTools.findTd(period.get(i),Tools.getDate(report.getEndDate(), "yyyy-MM-dd"));
		
		//System.out.println("******* parseCashFlow, i=" + i + ", j=" + j);
		
		String findPurchaseAssetsRegexp = "�����̶��ʲ��������ʲ������������ʲ���֧�����ֽ�</a></td>|</tr>";
		String strOfPurchaseAssets = "�����̶��ʲ��������ʲ������������ʲ�֧�����ֽ�";
		if(str.indexOf(strOfPurchaseAssets) != -1){
			findPurchaseAssetsRegexp = strOfPurchaseAssets + "</a></td>|</tr>";			
		}
		
		
		String findDepreciationAssetsRegexp = "�̶��ʲ��۾ɡ������ʲ��ۺġ������������۾�</a></td>|</tr>";  
		String strOfDepreciationAssets = "�̶��ʲ��۾ɡ������ʲ��ۺġ������������ʲ��۾�";
		if(str.indexOf(strOfDepreciationAssets) != -1){
			findDepreciationAssetsRegexp = strOfDepreciationAssets + "</a></td>|</tr>";			
		}

		//��Ӫ��������ֽ���������
		String findNetCashFlowRegexp = "��Ӫ��������ֽ���������</a></td>|</tr>";  
		String strOfNetCashFlow = "��Ӫ��������ֽ���������";
		if(str.indexOf(strOfNetCashFlow) != -1){
			findNetCashFlowRegexp = strOfNetCashFlow + "</a></td>|</tr>";			
		}
		
		String findDepreciationAssets1Regexp = "�����ʲ�̯��</a></td>|</tr>";
		//String findDepreciationAssets2Regexp = "�����ʲ�̯��</a></td>|</tr>";
		String findDepreciationAssets3Regexp = "���ڴ�̯����̯��</a></td>|</tr>";
			
		List<String> purchaseAssets = Tools.subStrings(str, findPurchaseAssetsRegexp);
		List<String> depreciationAssets = Tools.subStrings(str, findDepreciationAssetsRegexp);
		List<String> depreciationAssets1 = Tools.subStrings(str, findDepreciationAssets1Regexp);
		//List<String> depreciationAssets2 = Tools.subStrings(str, findDepreciationAssets2Regexp);
		List<String> depreciationAssets3 = Tools.subStrings(str, findDepreciationAssets3Regexp);
		List<String> netCashFlows = Tools.subStrings(str, findNetCashFlowRegexp);
		
		if (purchaseAssets!=null && purchaseAssets.size()>0) {
			cf.setPurchaseAssets(Tools.getDouble(SinaTools.findTdValue(
					purchaseAssets.get(i), j)));
		}
		cf.setDepreciationAssets(Tools.getDouble(SinaTools.findTdValue(depreciationAssets.get(i),j)) 
									+ Tools.getDouble(SinaTools.findTdValue(depreciationAssets1.get(i),j))
									//+ Tools.getDouble(SinaTools.findTdValue(depreciationAssets2.get(i),j))
									+ Tools.getDouble(SinaTools.findTdValue(depreciationAssets3.get(i),j)));

		
		if (netCashFlows!=null && netCashFlows.size()>0) {
			cf.setNetCashFlow(Tools.getDouble(SinaTools.findTdValue(
					netCashFlows.get(i), j)));
		}
		
		//System.out.println(report.getCashFlow());
		
		//System.out.println(cf);
		
		return cf;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	
}
