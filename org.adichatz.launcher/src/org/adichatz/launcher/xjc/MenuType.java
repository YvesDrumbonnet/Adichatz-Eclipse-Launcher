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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour menuType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="menuType">
 *   &lt;complexContent>
 *     &lt;extension base="{}nodeType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="menu" type="{}menuType"/>
 *         &lt;element name="launcher" type="{}launcherType"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menuType", propOrder = {
    "menuOrLauncher"
})
public class MenuType
    extends NodeType
{

    @XmlElements({
        @XmlElement(name = "menu", type = MenuType.class),
        @XmlElement(name = "launcher", type = LauncherType.class)
    })
    protected List<NodeType> menuOrLauncher;

    /**
     * Gets the value of the menuOrLauncher property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuOrLauncher property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuOrLauncher().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuType }
     * {@link LauncherType }
     * 
     * 
     */
    public List<NodeType> getMenuOrLauncher() {
        if (menuOrLauncher == null) {
            menuOrLauncher = new ArrayList<NodeType>();
        }
        return this.menuOrLauncher;
    }

}
