/**
 * WebServiceManangerImplService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test.vo;

public interface WebServiceManangerImplService extends javax.xml.rpc.Service {
    public java.lang.String getWebServiceAddress();

    public com.test.vo.WebServiceManangerImpl getWebService() throws javax.xml.rpc.ServiceException;

    public com.test.vo.WebServiceManangerImpl getWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
