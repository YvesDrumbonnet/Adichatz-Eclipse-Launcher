//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2016.05.12 à 08:41:12 AM CEST 
//


package org.adichatz.launcher.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour envTypeEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="envTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="java"/>
 *     &lt;enumeration value="ant"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "envTypeEnum")
@XmlEnum
public enum EnvTypeEnum {

    @XmlEnumValue("java")
    JAVA("java"),
    @XmlEnumValue("ant")
    ANT("ant");
    private final String value;

    EnvTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnvTypeEnum fromValue(String v) {
        for (EnvTypeEnum c: EnvTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
