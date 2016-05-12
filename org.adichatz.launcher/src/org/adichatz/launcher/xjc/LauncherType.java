//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2016.05.12 à 08:41:12 AM CEST 
//


package org.adichatz.launcher.xjc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour launcherType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="launcherType">
 *   &lt;complexContent>
 *     &lt;extension base="{}nodeType">
 *       &lt;sequence>
 *         &lt;element name="arg" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="antAddedEntries" type="{}antAddedEntriesType"/>
 *         &lt;element name="refresh" type="{}refreshType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="confirmMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="launcherURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="encoding" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="envType" type="{}envTypeEnum" default="java" />
 *       &lt;attribute name="lastLaunched" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="returnCode" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "launcherType", propOrder = {
    "arg",
    "antAddedEntries",
    "refresh"
})
public class LauncherType
    extends NodeType
{

    protected List<String> arg;
    @XmlElement(required = true)
    protected AntAddedEntriesType antAddedEntries;
    @XmlElement(required = true)
    protected RefreshType refresh;
    @XmlAttribute(name = "confirmMessage")
    protected String confirmMessage;
    @XmlAttribute(name = "launcherURI")
    protected String launcherURI;
    @XmlAttribute(name = "encoding")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String encoding;
    @XmlAttribute(name = "envType")
    protected EnvTypeEnum envType;
    @XmlAttribute(name = "lastLaunched")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastLaunched;
    @XmlAttribute(name = "returnCode")
    protected Integer returnCode;

    /**
     * Gets the value of the arg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getArg() {
        if (arg == null) {
            arg = new ArrayList<String>();
        }
        return this.arg;
    }

    /**
     * Obtient la valeur de la propriété antAddedEntries.
     * 
     * @return
     *     possible object is
     *     {@link AntAddedEntriesType }
     *     
     */
    public AntAddedEntriesType getAntAddedEntries() {
        return antAddedEntries;
    }

    /**
     * Définit la valeur de la propriété antAddedEntries.
     * 
     * @param value
     *     allowed object is
     *     {@link AntAddedEntriesType }
     *     
     */
    public void setAntAddedEntries(AntAddedEntriesType value) {
        this.antAddedEntries = value;
    }

    /**
     * Obtient la valeur de la propriété refresh.
     * 
     * @return
     *     possible object is
     *     {@link RefreshType }
     *     
     */
    public RefreshType getRefresh() {
        return refresh;
    }

    /**
     * Définit la valeur de la propriété refresh.
     * 
     * @param value
     *     allowed object is
     *     {@link RefreshType }
     *     
     */
    public void setRefresh(RefreshType value) {
        this.refresh = value;
    }

    /**
     * Obtient la valeur de la propriété confirmMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmMessage() {
        return confirmMessage;
    }

    /**
     * Définit la valeur de la propriété confirmMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmMessage(String value) {
        this.confirmMessage = value;
    }

    /**
     * Obtient la valeur de la propriété launcherURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLauncherURI() {
        return launcherURI;
    }

    /**
     * Définit la valeur de la propriété launcherURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLauncherURI(String value) {
        this.launcherURI = value;
    }

    /**
     * Obtient la valeur de la propriété encoding.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Définit la valeur de la propriété encoding.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncoding(String value) {
        this.encoding = value;
    }

    /**
     * Obtient la valeur de la propriété envType.
     * 
     * @return
     *     possible object is
     *     {@link EnvTypeEnum }
     *     
     */
    public EnvTypeEnum getEnvType() {
        if (envType == null) {
            return EnvTypeEnum.JAVA;
        } else {
            return envType;
        }
    }

    /**
     * Définit la valeur de la propriété envType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvTypeEnum }
     *     
     */
    public void setEnvType(EnvTypeEnum value) {
        this.envType = value;
    }

    /**
     * Obtient la valeur de la propriété lastLaunched.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastLaunched() {
        return lastLaunched;
    }

    /**
     * Définit la valeur de la propriété lastLaunched.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastLaunched(XMLGregorianCalendar value) {
        this.lastLaunched = value;
    }

    /**
     * Obtient la valeur de la propriété returnCode.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReturnCode() {
        return returnCode;
    }

    /**
     * Définit la valeur de la propriété returnCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReturnCode(Integer value) {
        this.returnCode = value;
    }

}
