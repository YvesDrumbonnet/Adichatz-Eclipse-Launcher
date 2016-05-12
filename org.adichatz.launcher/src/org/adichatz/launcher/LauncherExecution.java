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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.launcher.xjc.LauncherTree;
import org.adichatz.launcher.xjc.LauncherType;
import org.adichatz.launcher.xjc.NodeType;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.internal.core.LaunchConfiguration;
import org.eclipse.debug.internal.core.StreamsProxy;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.launching.StandardVM;
import org.eclipse.jdt.internal.launching.StandardVMType;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

// TODO: Auto-generated Javadoc
/**
 * The Class LauncherExecution.
 *
 * @author Yves Arpheuil
 */
@SuppressWarnings("restriction")
public class LauncherExecution {

	/** The logger. */
	private static Logger logger = Logger.getInstance();

	/** The launcher. */
	private LauncherType launcher;

	/**
	 * Instantiates a new launcher execution.
	 *
	 * @param launcher
	 *            the launcher
	 */
	public LauncherExecution(LauncherType launcher) {
		this.launcher = launcher;
	}

	/**
	 * Open file.
	 */
	public void openFile() {
		switch (launcher.getEnvType()) {
		case JAVA:
			openJavaFile();
			break;
		case ANT:
			openAntFile();
			break;
		default:
			break;
		}
	}

	/**
	 * Open ant file.
	 */
	private void openAntFile() {
		String launcherAntURI = launcher.getLauncherURI();
		IProject project = LauncherTools.getProject(launcherAntURI);
		if (!project.exists()) {
			logger.logError(getValueFromBundle("error.invalid.project", project.getName()));
			return;
		}
		IFile antFile = project.getFile(getResourceName(launcherAntURI, 17));
		if (!antFile.exists()) {
			logger.logError(getValueFromBundle("error.invalid.antFile", antFile.getName(), project.getName()));
			return;
		}
		FileEditorInput input = new FileEditorInput(antFile);
		try {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(antFile.getName(),
					antFile.getContentDescription().getContentType());
			if (desc == null) {
				desc = workbench.getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
			}
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, desc.getId());
		} catch (CoreException e) {
			logger.logError(e);
		}
	}

	/**
	 * Open java file.
	 */
	private void openJavaFile() {
		String launcherClassURI = launcher.getLauncherURI();
		String classsName = getResourceName(launcherClassURI, 14);
		IProject project = LauncherTools.getProject(launcherClassURI);
		if (!project.exists()) {
			logger.logError(getValueFromBundle("error.invalid.project", project.getName()));
			return;
		}
		try {
			IJavaProject javaProject = (IJavaProject) project.getNature(JavaCore.NATURE_ID);
			if (null == javaProject || !javaProject.exists()) {
				logger.logError(getValueFromBundle("error.invalid.java.project", project.getName()));
				return;
			}
			IType classType = javaProject.findType(classsName);
			IResource resource = classType.getResource();
			if (null != resource && resource instanceof IFile)
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), (IFile) resource, true);
			else
				logger.logError(getValueFromBundle("cannot.open.file", launcherClassURI));
		} catch (CoreException e) {
			logger.logError(e);
		}
	}

	/**
	 * Exec.
	 */
	public void exec() {
		String confirmMessage = launcher.getConfirmMessage();
		if (null != confirmMessage && !confirmMessage.isEmpty()) {
			if (!MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),
					getValueFromBundle("confirm.launch", launcher.getLabel()), confirmMessage))
				return;
		}
		logger.clearConsole();
		try {
			switch (launcher.getEnvType()) {
			case JAVA:
				execJava();
				refreshProject(launcher);
				break;
			case ANT:
				execAnt();
				break;
			default:
				break;
			}
			LauncherTree lastLauncherTree = LauncherTools.getRecentLauncherTree();
			if (null == lastLauncherTree)
				lastLauncherTree = new LauncherTree();
			launcher.setLastLaunched(LauncherTools.getXMLGregorianCalendar(new Date()));
			String identifier = LauncherTools.getIdentifier(launcher);
			for (NodeType node : lastLauncherTree.getMenuOrLauncher()
					.toArray(new NodeType[lastLauncherTree.getMenuOrLauncher().size()])) {
				LauncherType launcher = (LauncherType) node;
				if (LauncherTools.getIdentifier(launcher).equals(identifier)) {
					lastLauncherTree.getMenuOrLauncher().remove(launcher);
				}
			}
			lastLauncherTree.getMenuOrLauncher().add(0, launcher);
			LauncherTools.marshal(lastLauncherTree, LauncherTools.getLastLauncherXMLFile());
			LauncherTools.RECENT_LAUNCHER_TREE = null; // force new unmarshalling when using getLastLauncherTree method.
		} catch (Exception e) {
			logger.logError(e);
		}
	}

	/**
	 * Exec ant.
	 */
	public void execAnt() {
		String launcherAntURI = launcher.getLauncherURI();
		if (!launcherAntURI.startsWith("platform:/plugin/"))
			logger.logError(getValueFromBundle("error.invalid.ant.xml.uri", launcherAntURI));
		IProject project = LauncherTools.getProject(launcherAntURI);
		if (null == project || !project.exists()) {
			logger.logError(getValueFromBundle("error.invalid.project", project.getName()));
			return;
		}
		IFile antFile = project.getFile(getResourceName(launcherAntURI, 17));
		if (!antFile.exists()) {
			logger.logError(getValueFromBundle("error.invalid.antFile", antFile.getName(), project.getName()));
			return;
		}

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.ant.AntLaunchConfigurationType");
		String name = "org.adichatz.launcher - ant";
		try {
			ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, name);
			AdiLaunchConfigurationWorkingCopy launchConfiguration = new AdiLaunchConfigurationWorkingCopy(
					(LaunchConfiguration) workingCopy);
			AntLaunchBuilder antLaunchBuilder = new AntLaunchBuilder(launcher, project.getName());
			launchConfiguration.setInfo(new AdiLaunchManager().createInfoFromInputStream(antLaunchBuilder.getInputStream()));
			launchConfiguration.launch("run", new NullProgressMonitor(), true, true);
		} catch (CoreException e) {
			logger.logError(e);
		}
	}

	/**
	 * Exec java.
	 */
	public void execJava() {
		String launcherClassURI = launcher.getLauncherURI();
		checkLauncherClassURI(launcherClassURI);
		IProject project = LauncherTools.getProject(launcherClassURI);
		if (null == project || !project.exists()) {
			logger.logError(getValueFromBundle("error.invalid.project", project.getName()));
			return;
		}
		String className = getResourceName(launcherClassURI, 14);
		if (null == launcher.getEncoding())
			launcher.setEncoding("Cp1252");

		try {
			IJavaProject javaProject = (IJavaProject) project.getNature(JavaCore.NATURE_ID);
			if (null == javaProject || !javaProject.exists()) {
				logger.logError(getValueFromBundle("error.invalid.java.project", project.getName()));
				return;
			}
			IRuntimeClasspathEntry runtimeClasspathEntry = JavaRuntime.computeJREEntry(javaProject);
			IRuntimeClasspathEntry[] entries = JavaRuntime.computeUnresolvedRuntimeClasspath(javaProject);
			// replace project JRE with config's JRE
			IRuntimeClasspathEntry projEntry = JavaRuntime.computeJREEntry(javaProject);
			if (runtimeClasspathEntry != null && projEntry != null) {
				if (!runtimeClasspathEntry.equals(projEntry)) {
					for (int i = 0; i < entries.length; i++) {
						IRuntimeClasspathEntry entry = entries[i];
						if (entry.equals(projEntry)) {
							entries[i] = runtimeClasspathEntry;
							break;
						}
					}
				}
			}
			Set<IRuntimeClasspathEntry> all = new LinkedHashSet<IRuntimeClasspathEntry>(entries.length);
			for (IRuntimeClasspathEntry entry : entries) {
				IRuntimeClasspathEntry[] resolved = JavaRuntime.resolveRuntimeClasspathEntry(entry, javaProject);
				for (int j = 0; j < resolved.length; j++) {
					all.add(resolved[j]);
				}
			}

			List<String> userEntries = new ArrayList<String>(entries.length);
			Set<String> set = new HashSet<String>(entries.length);
			for (IRuntimeClasspathEntry entry : all) {
				if (entry.getClasspathProperty() == IRuntimeClasspathEntry.USER_CLASSES) {
					String location = entry.getLocation();
					if (location != null) {
						if (!set.contains(location)) {
							userEntries.add(location);
							set.add(location);
						}
					}
				}
			}
			String[] classpaths = userEntries.toArray(new String[userEntries.size()]);
			String workingDirectory = project.getLocation().toOSString();
			StandardVM standardVM = (StandardVM) JavaRuntime.getVMInstall(javaProject);

			String program = StandardVMType.findJavaExecutable(standardVM.getInstallLocation()).getAbsolutePath();
			List<String> arguments = new ArrayList<String>();
			arguments.add(program);
			arguments.add("-Dfile.encoding=" + launcher.getEncoding());
			arguments.add("-classpath"); //$NON-NLS-1$
			StringBuffer buf = new StringBuffer();
			boolean first = true;
			for (String classpath : classpaths) {
				if (first) {
					first = false;
				} else
					buf.append(File.pathSeparator);
				buf.append(classpath);
			}
			arguments.add(buf.toString());
			arguments.add(className);
			for (String arg : launcher.getArg())
				if (null != arg && !arg.isEmpty())
					arguments.add(arg);

			String[] cmdLine = new String[arguments.size()];
			arguments.toArray(cmdLine);

			if (Platform.getOS().equals(Constants.OS_WIN32)) {
				String[] winCmdLine = new String[cmdLine.length];
				if (cmdLine.length > 0) {
					winCmdLine[0] = cmdLine[0];
				}
				for (int i = 1; i < cmdLine.length; i++) {
					winCmdLine[i] = winQuote(cmdLine[i]);
				}
				cmdLine = winCmdLine;
			}

			Process p = DebugPlugin.exec(cmdLine, new File(workingDirectory), null);
			IStreamsProxy streamsProxy = new StreamsProxy(p, launcher.getEncoding());
			streamsProxy.getOutputStreamMonitor().addListener(new IStreamListener() {
				@Override
				public void streamAppended(String text, IStreamMonitor monitor) {
					logger.getOutConsoleStream().print(text);
				}
			});
			streamsProxy.getErrorStreamMonitor().addListener(new IStreamListener() {
				@Override
				public void streamAppended(String text, IStreamMonitor monitor) {
					logger.logError(text);
				}
			});
			streamsProxy.write("\n");
		} catch (CoreException | IOException e) {
			logger.logError(e);
		}
	}

	/**
	 * Check launcher class uri.
	 *
	 * @param launcherClassURI
	 *            the launcher class uri
	 */
	private void checkLauncherClassURI(String launcherClassURI) {
		boolean invalid = false;
		if (!launcherClassURI.startsWith("bundleclass://"))
			invalid = true;
		else {
			String suffix = launcherClassURI.substring(14);
			int index = suffix.indexOf('/');
			if (index < 1 || index != suffix.lastIndexOf('/'))
				invalid = true;
		}
		if (invalid)
			logger.logError(getValueFromBundle("error.invalid.launcher.class.uri", launcherClassURI));
	}

	/**
	 * Refresh project.
	 *
	 * @param launcher
	 *            the launcher
	 */
	private void refreshProject(LauncherType launcher) {
		if (null != launcher.getRefresh())
			for (String projectName : launcher.getRefresh().getProjectName()) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if (project.exists())
					try {
						project.refreshLocal(IResource.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						logger.logError(e);
					}
				else
					logger.logError(getValueFromBundle("error.invalid.project", projectName));
			}
	}

	/**
	 * Needs quoting.
	 *
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	private boolean needsQuoting(String s) {
		int len = s.length();
		if (len == 0) // empty string has to be quoted
			return true;
		if ("\"\"".equals(s)) //$NON-NLS-1$
			return false; // empty quotes must not be quoted again
		for (int i = 0; i < len; i++) {
			switch (s.charAt(i)) {
			case ' ':
			case '\t':
			case '\\':
			case '"':
				return true;
			}
		}
		return false;
	}

	/**
	 * Win quote.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 */
	private String winQuote(String s) {
		if (!needsQuoting(s))
			return s;
		s = s.replaceAll("([\\\\]*)\"", "$1$1\\\\\""); //$NON-NLS-1$ //$NON-NLS-2$
		s = s.replaceAll("([\\\\]*)\\z", "$1$1"); //$NON-NLS-1$ //$NON-NLS-2$
		return "\"" + s + "\""; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the resource name.
	 *
	 * @param launcherResourceURI
	 *            the launcher resource uri
	 * @param index
	 *            the index
	 * @return the resource name
	 */
	private String getResourceName(String launcherResourceURI, int index) {
		String className = launcherResourceURI.substring(index);
		className = className.substring(className.indexOf('/') + 1);
		return className;
	}
}
