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

import static org.adichatz.launcher.LauncherTools.getValueFromBundle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.adichatz.launcher.xjc.LauncherType;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class AntLaunchBuilder.
 *
 * @author Yves Arpheuil
 */
public class AntLaunchBuilder {

	/** The logger. */
	private static Logger logger = Logger.getInstance();

	/** The entry prefix. */
	private static String ENTRY_PREFIX = "<listEntry value=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; standalone=&quot;no&quot;?&gt;&#13;&#10;&lt;runtimeClasspathEntry ";

	/** The entry suffix. */
	private static String ENTRY_SUFFIX = "&quot; path=&quot;3&quot; type=&quot;2&quot;/&gt;&#13;&#10;\"/>";

	/** The launcher. */
	private LauncherType launcher;

	/** The project name. */
	private String projectName;

	/**
	 * Instantiates a new ant launch builder.
	 *
	 * @param launcher
	 *            the launcher
	 * @param projectName
	 *            the project name
	 */
	public AntLaunchBuilder(LauncherType launcher, String projectName) {
		this.launcher = launcher;
		this.projectName = projectName;
	}

	/**
	 * Gets the input stream.
	 *
	 * @return the input stream
	 */
	public InputStream getInputStream() {
		StringBuffer launchSB = new StringBuffer();
		launchSB.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		launchSB.append("<launchConfiguration type=\"org.eclipse.ant.AntLaunchConfigurationType\">\n");
		launchSB.append("<booleanAttribute key=\"org.eclipse.ant.ui.DEFAULT_VM_INSTALL\" value=\"true\"/>\n");
		if (null != launcher.getRefresh() && !launcher.getRefresh().getProjectName().isEmpty()) {
			List<String> validProjectNames = new ArrayList<>();
			for (String projectName : launcher.getRefresh().getProjectName()) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if (project.exists() && project.isOpen())
					validProjectNames.add(projectName);
				else
					logger.logError(getValueFromBundle("project.not.exist.or.closed", projectName));
			}
			if (!validProjectNames.isEmpty()) {
				launchSB.append(
						"<stringAttribute key=\"org.eclipse.debug.core.ATTR_REFRESH_SCOPE\" value=\"${working_set:&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;&#13;&#10;&lt;resources&gt;");
				for (String projectName : validProjectNames)
					launchSB.append("&#13;&#10;&lt;item path=&quot;/").append(projectName).append("&quot; type=&quot;4&quot;/&gt;");
				launchSB.append("&#13;&#10;&lt;/resources&gt;}\"/>\n");
			}
		}
		launchSB.append("<listAttribute key=\"org.eclipse.debug.core.MAPPED_RESOURCE_PATHS\">\n");
		launchSB.append("<listEntry value=\"" + launcher.getLauncherURI().substring(5) + "\"/>\n");
		launchSB.append("</listAttribute>\n");
		launchSB.append("<listAttribute key=\"org.eclipse.debug.core.MAPPED_RESOURCE_TYPES\">\n");
		launchSB.append("<listEntry value=\"1\"/>\n");
		launchSB.append("</listAttribute>\n");
		launchSB.append("<listAttribute key=\"org.eclipse.jdt.launching.CLASSPATH\">\n");
		launchSB.append(ENTRY_PREFIX);
		launchSB.append(
				"id=&quot;org.eclipse.ant.ui.classpathentry.antHome&quot;&gt;&#13;&#10;&lt;memento default=&quot;true&quot;/&gt;&#13;&#10;&lt;/runtimeClasspathEntry&gt;&#13;&#10;\"/>\n");
		if (null != launcher.getAntAddedEntries() && !launcher.getAntAddedEntries().getPath().isEmpty())
			for (String path : launcher.getAntAddedEntries().getPath()) {
				Object resource = getEntry(projectName, path);
				if (null != resource) {
					launchSB.append(ENTRY_PREFIX);
					if (resource instanceof File) {
						launchSB.append("externalArchive=&quot;");
						launchSB.append(((File) resource).getAbsolutePath());
					} else {
						launchSB.append("internalArchive=&quot;");
						launchSB.append(((IResource) resource).getFullPath().toString());
					}
					launchSB.append(ENTRY_SUFFIX).append("\n");

				}
			}
		launchSB.append(ENTRY_PREFIX);
		launchSB.append(
				"id=&quot;org.eclipse.ant.ui.classpathentry.extraClasspathEntries&quot;&gt;&#13;&#10;&lt;memento/&gt;&#13;&#10;&lt;/runtimeClasspathEntry&gt;&#13;&#10;\"/>\n");
		launchSB.append("</listAttribute>\n");
		launchSB.append(
				"<stringAttribute key=\"org.eclipse.jdt.launching.CLASSPATH_PROVIDER\" value=\"org.eclipse.ant.ui.AntClasspathProvider\"/>\n");
		launchSB.append("<booleanAttribute key=\"org.eclipse.jdt.launching.DEFAULT_CLASSPATH\" value=\"false\"/>\n");
		launchSB.append(
				"<stringAttribute key=\"org.eclipse.jdt.launching.JRE_CONTAINER\" value=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/jre1.8.0_92\"/>\n");
		launchSB.append(
				"<stringAttribute key=\"org.eclipse.jdt.launching.MAIN_TYPE\" value=\"org.eclipse.ant.internal.launching.remote.InternalAntRunner\"/>\n");
		launchSB.append("<stringAttribute key=\"org.eclipse.jdt.launching.PROJECT_ATTR\" value=\"").append(projectName)
				.append("\"/>\n");
		launchSB.append(
				"<stringAttribute key=\"org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER\" value=\"org.eclipse.ant.ui.AntClasspathProvider\"/>\n");
		launchSB.append("<stringAttribute key=\"org.eclipse.ui.externaltools.ATTR_LOCATION\" value=\"${workspace_loc:/");
		launchSB.append(launcher.getLauncherURI().substring(17) + "}\"/>\n");
		launchSB.append("<stringAttribute key=\"process_factory_id\" value=\"org.eclipse.ant.ui.remoteAntProcessFactory\"/>\n");
		launchSB.append("</launchConfiguration>\n");
		try {
			return new ByteArrayInputStream(launchSB.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the entry.
	 *
	 * @param parentProjectName
	 *            the parent project name
	 * @param path
	 *            the path
	 * @return the entry
	 */
	private Object getEntry(String parentProjectName, String path) {
		Object result = null;
		if (path.startsWith("platform:/plugin/")) {
			String resourcePath = path.substring(17);
			int index = resourcePath.indexOf('/');
			if (-1 == index) {
				logger.logError(getValueFromBundle("invalid.resource.path", path));
				return null;
			}
			String projectName = 0 == index ? parentProjectName : resourcePath.substring(0, index);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (!project.exists() || !project.isOpen()) {
				logger.logError(getValueFromBundle("project.not.exist", projectName, path));
			} else {
				String pathInProject = resourcePath.substring(index + 1);
				StringTokenizer tokenizer = new StringTokenizer(pathInProject, "/");
				IResource resource = project;
				IContainer container = project;
				while (tokenizer.hasMoreTokens() && null != container) {
					String resourceName = tokenizer.nextToken();
					resource = container.findMember(resourceName);
					container = (resource instanceof IContainer) ? (IContainer) resource : null;
				}
				if (null == resource || !resource.exists() || tokenizer.hasMoreTokens())
					logger.logError(getValueFromBundle("invalid.resource.path", path));
				else
					result = resource;
			}
		} else if (path.startsWith("file://")) {
			String uriPath = "file:////".concat(path.substring(7));
			try {
				File file = new File(new URL(uriPath).toURI());
				if (!file.exists()) {
					logger.logError(getValueFromBundle("invalid.url", path));
				} else
					result = file;
			} catch (MalformedURLException | URISyntaxException e) {
				logger.logError(e);
			}
		} else
			logger.logError(getValueFromBundle("invalid.ant.path.type", path));
		return result;
	}
}
