/*******************************************************************************
* Copyright © Adichatz (2007-2016) - www.adichatz.org
*
* arpheuil@adichatz.org
*
* This software is a computer program whose purpose is to build easily
* Eclipse RCP applications using JPA in a JEE or JSE context.
*
* This software is governed by the CeCILL-C license under French law and
* abiding by the rules of distribution of free software.  You can  use,
* modify and/ or redistribute the software under the terms of the CeCILL
* license as circulated by CEA, CNRS and INRIA at the following URL
* "http://www.cecill.info".
*
* As a counterpart to the access to the source code and  rights to copy,
* modify and redistribute granted by the license, users are provided only
* with a limited warranty  and the software's author,  the holder of the
* economic rights,  and the successive licensors  have only  limited
* liability.
*
* In this respect, the user's attention is drawn to the risks associated
* with loading,  using,  modifying and/or developing or reproducing the
* software by the user in light of its specific status of free software,
* that may mean  that it is complicated to manipulate,  and  that  also
* therefore means  that it is reserved for developers  and  experienced
* professionals having in-depth computer knowledge. Users are therefore
* encouraged to load and test the software's suitability as regards their
* requirements in conditions enabling the security of their systems and/or
* data to be ensured and,  more generally, to use and operate it in the
* same conditions as regards security.
*
* The fact that you are presently reading this means that you have had
* knowledge of the CeCILL license and that you accept its terms.
*
*
********************************************************************************
*
* Copyright © Adichatz (2007-2016) - www.adichatz.org
*
* arpheuil@adichatz.org
*
* Ce logiciel est un programme informatique servant à construire rapidement des
* applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
*
* Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
* respectant les principes de diffusion des logiciels libres. Vous pouvez
* utiliser, modifier et/ou redistribuer ce programme sous les conditions
* de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
* sur le site "http://www.cecill.info".
*
* En contrepartie de l'accessibilité au code source et des droits de copie,
* de modification et de redistribution accordés par cette licence, il n'est
* offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
* seule une responsabilité restreinte pèse sur l'auteur du programme,  le
* titulaire des droits patrimoniaux et les concédants successifs.
*
* A cet égard  l'attention de l'utilisateur est attirée sur les risques
* associés au chargement,  à l'utilisation,  à la modification et/ou au
* développement et à la reproduction du logiciel par l'utilisateur étant
* donné sa spécificité de logiciel libre, qui peut le rendre complexe à
* manipuler et qui le réserve donc à des développeurs et des professionnels
* avertis possédant  des  connaissances  informatiques approfondies.  Les
* utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
* logiciel à leurs besoins dans des conditions permettant d'assurer la
* sécurité de leurs systèmes et ou de leurs données et, plus généralement,
* à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
*
* Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
* pris connaissance de la licence CeCILL, et que vous en avez accepté les
* termes.
*******************************************************************************/
package org.adichatz.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adichatz.launcher.editor.LauncherFormEditor;
import org.adichatz.launcher.xjc.LauncherTree;
import org.adichatz.launcher.xjc.LauncherType;
import org.adichatz.launcher.xjc.NodeType;
import org.adichatz.launcher.xjc.ObjectFactory;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.osgi.framework.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class LauncherTools.
 *
 * @author Yves Arpheuil
 */
public class LauncherTools {

	/** The logger. */
	private static Logger logger = Logger.getInstance();

	/** The resource bundle. */
	private static ResourceBundle RESOURCE_BUNDLE = Platform.getResourceBundle(Activator.getDefault().getBundle());;

	/** The registered images. */
	private static Map<String, Image> REGISTERED_IMAGES = new HashMap<>();

	/** The registered image descriptors. */
	private static Map<String, ImageDescriptor> REGISTERED_IMAGE_DESCRIPTORS = new HashMap<>();

	/** The launcher tree. */
	public static LauncherTree LAUNCHER_TREE;

	/** The launcher tree for recent launches. */
	public static LauncherTree RECENT_LAUNCHER_TREE;

	/** The launcher dir name. */
	private static String LAUNCHER_DIR_NAME = Platform.getInstallLocation().getURL().getFile()
			.concat("/configuration/org.adichatz.launcher/");

	/** The recent launcher xml. */
	public static String RECENT_LAUNCHER_XML = "recentLaunches.xml";

	/** The menu image key. */
	public static String IMG_MENU = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_MENU.png";

	/** The editor image key. */
	public static String IMG_EDITOR = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_EDITOR.png";

	/** The edit xml image key. */
	public static String IMG_EDIT_XML = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_EDIT_XML.png";

	/** The run image key. */
	public static String IMG_RUN = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_RUN.png";

	/** The run java image key. */
	public static String IMG_RUN_JAVA = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_RUN_JAVA.png";

	/** The run ant image key. */
	public static String IMG_RUN_ANT = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_RUN_ANT.png";

	/** The tree image key. */
	public static String IMG_TREE = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_TREE.png";

	/** The xml image key. */
	public static String IMG_XML = "platform:/plugin/org.adichatz.launcher/resources/icons/IMG_XML.png";

	/** The prefs recent list size. */
	public static String PREFS_RECENT_LIST_SIZE = "org.adichatz.launcher.recent.list.size";

	/**
	 * Gets the value from bundle.
	 *
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * @return the value from bundle
	 */
	public static String getValueFromBundle(String key, Object... variables) {
		try {
			return replaceVariables(RESOURCE_BUNDLE.getString(key), variables);
		} catch (MissingResourceException e) {
			return "???" + key + "???";
		}
	}

	/**
	 * Replace variables.
	 *
	 * @param value
	 *            the value
	 * @param variables
	 *            the variables
	 * @return the string
	 */
	private static String replaceVariables(String value, Object... variables) {
		if (null != value)
			for (int i = 0; i < variables.length; i++)
				value = value.replace("{" + i + "}", String.valueOf(variables[i]));
		return value;
	}

	/**
	 * Unmarshal.
	 *
	 * @param file
	 *            the file
	 * @return the launcher tree
	 */
	public static LauncherTree unmarshal(File file) {
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			InputStream inputStream = file.toURI().toURL().openStream();
			LauncherTree launcherTree = (LauncherTree) unmarshaller.unmarshal(inputStream);
			inputStream.close();
			return launcherTree;
		} catch (JAXBException | IOException e) {
			logger.logError(getValueFromBundle("error.cannot.unmarshall"), e);
			return null;
		}
	}

	/**
	 * Marshal.
	 *
	 * @param launcherTree
	 *            the launcher tree
	 * @param file
	 *            the file
	 */
	public static void marshal(LauncherTree launcherTree, File file) {
		String schemaLocation = "http://www.adichatz.org/xsd/org.adichatz.launcher/launcherTree.xsd";
		try {
			FileOutputStream xmlFileFOS = new FileOutputStream(file);
			JAXBContext context = JAXBContext.newInstance(LauncherTree.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (null != schemaLocation)
				m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, schemaLocation);
			m.marshal(launcherTree, xmlFileFOS);
			xmlFileFOS.close();
		} catch (JAXBException | IOException e) {
			logger.logError(e);
		}
	}

	/**
	 * Gets the registered image.
	 *
	 * @param imageURI
	 *            the image uri
	 * @return the registered image
	 */
	public static Image getRegisteredImage(String imageURI) {
		String imageKey = imageURI.startsWith("platform:/plugin/") ? imageURI.substring(17) : imageURI;
		Image image = REGISTERED_IMAGES.get(imageKey);
		if (null == image) {
			ImageDescriptor imageDescriptor = getRegisteredImageDescriptor(imageKey);
			if (null != imageDescriptor) {
				image = imageDescriptor.createImage();
				REGISTERED_IMAGES.put(imageKey, image);
			}
		}
		return image;
	}

	/**
	 * Gets the registered image descriptor.
	 *
	 * @param imageURI
	 *            the image uri
	 * @return the registered image descriptor
	 */
	public static ImageDescriptor getRegisteredImageDescriptor(String imageURI) {
		String imageKey = imageURI.startsWith("platform:/plugin/") ? imageURI.substring(17) : imageURI;
		ImageDescriptor imageDescriptor = REGISTERED_IMAGE_DESCRIPTORS.get(imageKey);
		if (null == imageDescriptor) {
			int index = imageKey.indexOf('/');
			String bundleName = imageKey.substring(0, index);
			URL urlImage = null;
			Bundle bundle = Platform.getBundle(bundleName);
			if (null != bundle)
				urlImage = bundle.getEntry(imageKey.substring(index + 1));
			else {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);
				if (project.exists()) {
					IFile iconFile = project.getFile(imageKey.substring(index + 1));
					if (iconFile.exists())
						try {
							urlImage = iconFile.getLocationURI().toURL();
						} catch (MalformedURLException e) {
							logger.logError(e);
						}
				}
			}
			if (null != urlImage) {
				imageDescriptor = ImageDescriptor.createFromURL(urlImage);
				REGISTERED_IMAGE_DESCRIPTORS.put(imageKey, imageDescriptor);
			}
		}
		return imageDescriptor;
	}

	/**
	 * Gets the recent launcher tree.
	 *
	 * @return the recent launcher tree
	 */
	public static LauncherTree getRecentLauncherTree() {
		if (null == RECENT_LAUNCHER_TREE) {
			File lastLaunchFile = LauncherTools.getLastLauncherXMLFile();
			if (lastLaunchFile.exists())
				RECENT_LAUNCHER_TREE = LauncherTools.unmarshal(lastLaunchFile);
		}
		return RECENT_LAUNCHER_TREE;
	}

	/**
	 * Gets the last launcher xml file.
	 *
	 * @return the last launcher xml file
	 */
	public static File getLastLauncherXMLFile() {
		String launcherFileName = LAUNCHER_DIR_NAME.concat(RECENT_LAUNCHER_XML);
		return new File(launcherFileName);
	}

	/**
	 * Gets the launcher file.
	 *
	 * @return the launcher file
	 */
	public static File getLauncherFile() {
		String launcherFileName = LAUNCHER_DIR_NAME.concat("Launcher.xml");
		File file = new File(launcherFileName);
		if (!file.exists()) {
			new File(LAUNCHER_DIR_NAME).mkdirs();
			LauncherTools.marshal(new LauncherTree(), file);
		}
		return file;
	}

	/**
	 * Gets the text.
	 *
	 * @param node
	 *            the node
	 * @return the text
	 */
	public static String getText(NodeType node) {
		String text = node.getId();
		if (null == text || text.isEmpty())
			text = node.getLabel();
		if (null == text || text.isEmpty() && node instanceof LauncherType)
			text = ((LauncherType) node).getLauncherURI();
		if (null == text || text.isEmpty())
			return "";
		return text;
	}

	public static String getToolTipText(NodeType node) {
		String toolTipText = node.getLabel();
		if (null == toolTipText || toolTipText.isEmpty())
			toolTipText = node.getId();
		if (null == toolTipText || toolTipText.isEmpty() && node instanceof LauncherType)
			toolTipText = ((LauncherType) node).getLauncherURI();
		if (null == toolTipText || toolTipText.isEmpty())
			return "";
		return toolTipText;
	}

	/**
	 * Gets the modified font.
	 *
	 * @param font
	 *            the font
	 * @param style
	 *            the style
	 * @return the modified font
	 */
	public static Font getModifiedFont(Font font, int style) {
		FontData[] fontData = font.getFontData();
		FontData[] styleData = new FontData[fontData.length];
		for (int i = 0; i < styleData.length; i++) {
			FontData base = fontData[i];
			styleData[i] = new FontData(base.getName(), base.getHeight(), base.getStyle() | style);
		}
		return new Font(font.getDevice(), styleData);
	}

	/**
	 * Gets the index.
	 *
	 * @param resourceURI
	 *            the resource uri
	 * @return the index
	 */
	private static int getIndex(String resourceURI) {
		if (resourceURI.startsWith("bundleclass://"))
			return 14;
		else if (resourceURI.startsWith("platform:/plugin/"))
			return 17;
		logger.logError(getValueFromBundle("error.invalid.resource.uri", resourceURI));
		return -1;

	}

	/**
	 * Gets the bundle.
	 *
	 * @param resourceURI
	 *            the resource uri
	 * @return the bundle
	 */
	public static Bundle getBundle(String resourceURI) {
		int index = getIndex(resourceURI);
		if (-1 == index)
			return null;
		String bundleName = resourceURI.substring(index, resourceURI.indexOf('/', index + 1));
		return Platform.getBundle(bundleName);
	}

	/**
	 * Gets the project.
	 *
	 * @param resourceURI
	 *            the resource uri
	 * @return the project
	 */
	public static IProject getProject(String resourceURI) {
		int index = getIndex(resourceURI);
		if (-1 == index)
			return null;
		String projectName = resourceURI.substring(index, resourceURI.indexOf('/', index + 1));
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}

	/**
	 * Open launcher file.
	 */
	public static void openLauncherFile() {
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(LauncherTools.getLauncherFile().getAbsolutePath()));
		if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
			FileStoreEditorInput input = new FileStoreEditorInput(fileStore);
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, LauncherFormEditor.ID);
			} catch (PartInitException exception) {
				logger.logError(exception);
			}
		}
	}

	/**
	 * Gets the identifier.
	 *
	 * @param launcher
	 *            the launcher
	 * @return the identifier
	 */
	public static String getIdentifier(LauncherType launcher) {
		StringBuffer identifierSB = new StringBuffer();
		if (null != launcher.getId() && !launcher.getId().isEmpty())
			identifierSB.append("#id#").append(launcher.getId());
		else if (null != launcher.getLabel() && !launcher.getLabel().isEmpty())
			identifierSB.append("#label#").append(launcher.getLabel());
		identifierSB.append("#URI#").append(launcher.getLauncherURI());
		return identifierSB.toString();
	}

	/**
	 * Gets the XML gregorian calendar.
	 *
	 * @param date
	 *            the date
	 * @return the XML gregorian calendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		try {
			XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
			return xmlGregorianCalendar;
		} catch (DatatypeConfigurationException e) {
			logger.logError("Invalid gregorian calendar:" + gregorianCalendar);
		}
		return null;
	}

	/**
	 * Gets the recent launch.
	 *
	 * @param launcher
	 *            the launcher
	 * @return the recent launch
	 */
	public static LauncherType getRecentLaunch(LauncherType launcher) {
		LauncherTree recentLauncherTree = getRecentLauncherTree();
		String indentifier = getIdentifier(launcher);
		for (NodeType node : recentLauncherTree.getMenuOrLauncher())
			if (indentifier.equals(getIdentifier((LauncherType) node)))
				return (LauncherType) node;
		return null;
	}
}
