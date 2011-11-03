/**
 * Test.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test.vo;

public class Test  implements java.io.Serializable {
    private java.lang.String a1;

    private java.lang.String a2;

    public Test() {
    }

    public Test(
           java.lang.String a1,
           java.lang.String a2) {
           this.a1 = a1;
           this.a2 = a2;
    }


    /**
     * Gets the a1 value for this Test.
     * 
     * @return a1
     */
    public java.lang.String getA1() {
        return a1;
    }


    /**
     * Sets the a1 value for this Test.
     * 
     * @param a1
     */
    public void setA1(java.lang.String a1) {
        this.a1 = a1;
    }


    /**
     * Gets the a2 value for this Test.
     * 
     * @return a2
     */
    public java.lang.String getA2() {
        return a2;
    }


    /**
     * Sets the a2 value for this Test.
     * 
     * @param a2
     */
    public void setA2(java.lang.String a2) {
        this.a2 = a2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Test)) return false;
        Test other = (Test) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.a1==null && other.getA1()==null) || 
             (this.a1!=null &&
              this.a1.equals(other.getA1()))) &&
            ((this.a2==null && other.getA2()==null) || 
             (this.a2!=null &&
              this.a2.equals(other.getA2())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getA1() != null) {
            _hashCode += getA1().hashCode();
        }
        if (getA2() != null) {
            _hashCode += getA2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Test.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsdemo", "Test"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("a1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "a1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("a2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "a2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
