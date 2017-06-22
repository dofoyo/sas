package com.rhb.sas.stockholder.bean;

import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;
import com.rhb.af.util.DateTools;
import com.rhb.af.bean.BaseBean;

public class Stockholder extends BaseBean {
	private String pk = "";
	private String stockNo = "";
	private String stockName = "";
	private String holderNo = "";
	private String holderName = "";
	private Date reportedDate = new Date();
	private Date endDate = new Date();
	private Long holdingNumber = new Long(0);
	private Double holdingRatio = 0.0;
	private String holdingType = "";

	public Stockholder() {
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPk() {
		return this.pk;
	}

	public String getPkCaption() {
		return "编码";
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockNo() {
		return this.stockNo;
	}

	public String getStockNoCaption() {
		return "股票代码";
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockName() {
		return this.stockName;
	}

	public String getStockNameCaption() {
		return "股票名称";
	}

	public void setHolderNo(String holderNo) {
		this.holderNo = holderNo;
	}

	public String getHolderNo() {
		return this.holderNo;
	}

	public String getHolderNoCaption() {
		return "股东代码";
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getHolderName() {
		return this.holderName;
	}

	public String getHolderNameCaption() {
		return "股东名称";
	}

	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}

	public Date getReportedDate() {
		return this.reportedDate;
	}

	public String getReportedDateCaption() {
		return "公告日期";
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getEndDateCaption() {
		return "截止日期";
	}

	public void setHoldingNumber(long holdingNumber) {
		this.holdingNumber = holdingNumber;
	}

	public long getHoldingNumber() {
		return this.holdingNumber;
	}

	public String getHoldingNumberCaption() {
		return "持股数量";
	}

	public void setHoldingRatio(Double holdingRatio) {
		this.holdingRatio = holdingRatio;
	}

	public Double getHoldingRatio() {
		return this.holdingRatio;
	}

	public String getHoldingRatioCaption() {
		return "持股比例";
	}

	public void setHoldingType(String holdingType) {
		this.holdingType = holdingType;
	}

	public String getHoldingType() {
		return this.holdingType;
	}

	public String getHoldingTypeCaption() {
		return "持股性质";
	}

	public String requiredValidate() {
		StringBuffer errors = new StringBuffer();
		return errors.toString();
	}

	public String outOfRangeValidate() {
		StringBuffer errors = new StringBuffer();
		if (this.pk.length() > 24) {
			errors.append(this.getPkCaption() + "字符数为" + this.pk.length()
					+ ",超过了24个字符的最大限制！");
		}
		if (this.stockNo.length() > 24) {
			errors.append(this.getStockNoCaption() + "字符数为"
					+ this.stockNo.length() + ",超过了24个字符的最大限制！");
		}
		if (this.stockName.length() > 24) {
			errors.append(this.getStockNameCaption() + "字符数为"
					+ this.stockName.length() + ",超过了24个字符的最大限制！");
		}
		if (this.holderNo.length() > 250) {
			errors.append(this.getHolderNoCaption() + "字符数为"
					+ this.holderNo.length() + ",超过了250个字符的最大限制！");
		}
		if (this.holderName.length() > 250) {
			errors.append(this.getHolderNameCaption() + "字符数为"
					+ this.holderName.length() + ",超过了250个字符的最大限制！");
		}
		if (this.holdingType.length() > 60) {
			errors.append(this.getHoldingTypeCaption() + "字符数为"
					+ this.holdingType.length() + ",超过了60个字符的最大限制！");
		}
		return errors.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result + ((stockNo == null) ? 0 : stockNo.hashCode());
		result = prime * result
				+ ((stockName == null) ? 0 : stockName.hashCode());
		result = prime * result
				+ ((holderNo == null) ? 0 : holderNo.hashCode());
		result = prime * result
				+ ((holderName == null) ? 0 : holderName.hashCode());
		result = prime * result
				+ ((reportedDate == null) ? 0 : reportedDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((holdingNumber == null) ? 0 : holdingNumber.hashCode());
		result = prime * result
				+ ((holdingRatio == null) ? 0 : holdingRatio.hashCode());
		result = prime * result
				+ ((holdingType == null) ? 0 : holdingType.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		final Stockholder other = (Stockholder) obj;

		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		if (stockNo == null) {
			if (other.stockNo != null)
				return false;
		} else if (!stockNo.equals(other.stockNo))
			return false;
		if (stockName == null) {
			if (other.stockName != null)
				return false;
		} else if (!stockName.equals(other.stockName))
			return false;
		if (holderNo == null) {
			if (other.holderNo != null)
				return false;
		} else if (!holderNo.equals(other.holderNo))
			return false;
		if (holderName == null) {
			if (other.holderName != null)
				return false;
		} else if (!holderName.equals(other.holderName))
			return false;
		if (reportedDate == null) {
			if (other.reportedDate != null)
				return false;
		} else if (!reportedDate.equals(other.reportedDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (holdingNumber == null) {
			if (other.holdingNumber != null)
				return false;
		} else if (!holdingNumber.equals(other.holdingNumber))
			return false;
		if (holdingRatio == null) {
			if (other.holdingRatio != null)
				return false;
		} else if (!holdingRatio.equals(other.holdingRatio))
			return false;
		if (holdingType == null) {
			if (other.holdingType != null)
				return false;
		} else if (!holdingType.equals(other.holdingType))
			return false;

		return true;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("pk = " + pk);
		sb.append(",stockNo = " + stockNo);
		sb.append(",stockName = " + stockName);
		sb.append(",holderNo = " + holderNo);
		sb.append(",holderName = " + holderName);
		sb.append(",holdingNumber = " + holdingNumber);
		sb.append(",holdingRatio = " + holdingRatio);
		sb.append(",holdingType = " + holdingType);
		//sb.append(",reportedDate=" + reportedDate.toString());
		sb.append(",endDate=" + endDate.toString());
		return sb.toString();
	}

}
