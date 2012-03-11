package com.hrm.entity;

import java.util.Date;

public class OrderMaterialStoreList implements java.io.Serializable {
	// Fields

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String id;
		private String outid;
		private String materialid;
		//进货数量
		private Float sum;
		//应付金额
		private Float shouldpay;
		//实付金额
		private Float actuallypay;
		private String updtuser;
		private Date updttime;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getOutid() {
			return outid;
		}
		public void setOutid(String outid) {
			this.outid = outid;
		}
		public String getMaterialid() {
			return materialid;
		}
		public void setMaterialid(String materialid) {
			this.materialid = materialid;
		}
		public Float getSum() {
			return sum;
		}
		public void setSum(Float sum) {
			this.sum = sum;
		}
		public Float getShouldpay() {
			return shouldpay;
		}
		public void setShouldpay(Float shouldpay) {
			this.shouldpay = shouldpay;
		}
		public Float getActuallypay() {
			return actuallypay;
		}
		public void setActuallypay(Float actuallypay) {
			this.actuallypay = actuallypay;
		}
		public String getUpdtuser() {
			return updtuser;
		}
		public void setUpdtuser(String updtuser) {
			this.updtuser = updtuser;
		}
		public Date getUpdttime() {
			return updttime;
		}
		public void setUpdttime(Date updttime) {
			this.updttime = updttime;
		}
}
