package com.hrm.entity;

import java.util.Date;

public class OrderMaterialStoreInfo implements java.io.Serializable {
	// Fields
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String id;
		private Date outdate;
		private String outuser;
		private String orderdesc;
		private String updtuser;
		private Date updttime;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Date getOutdate() {
			return outdate;
		}
		public void setOutdate(Date outdate) {
			this.outdate = outdate;
		}
		public String getOutuser() {
			return outuser;
		}
		public void setOutuser(String outuser) {
			this.outuser = outuser;
		}
		public String getOrderdesc() {
			return orderdesc;
		}
		public void setOrderdesc(String orderdesc) {
			this.orderdesc = orderdesc;
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
