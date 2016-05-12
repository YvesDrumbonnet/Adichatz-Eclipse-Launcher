/*******************************************************************************
 * Copyright © Adichatz (2007-2016) - www.adichatz.org
 *
 * arpheuil@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2016) - www.adichatz.org
 *
 * arpheuil@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.launcher;

import static org.adichatz.launcher.LauncherTools.getValueFromBundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 *
 * @author Yves Arpheuil
 */
@SuppressWarnings("restriction")
public class Logger {

	/** The excluded classes. */
	private static Set<String> EXCLUDED_CLASSES = new HashSet<String>();
	static {
		EXCLUDED_CLASSES.add(Thread.class.getName());
		EXCLUDED_CLASSES.add(Logger.class.getName());
		EXCLUDED_CLASSES.add(MessageConsole.class.getName());
	}

	/** The this. */
	private static Logger THIS;

	/** The out console stream. */
	private MessageConsoleStream outConsoleStream;

	/** The err console stream. */
	private MessageConsoleStream errConsoleStream;

	/** The launcher console. */
	private MessageConsole launcherConsole;

	/**
	 * Gets the single instance of Logger.
	 *
	 * @return single instance of Logger
	 */
	public static Logger getInstance() {
		if (null == THIS) {
			THIS = new Logger();
		}
		return THIS;
	}

	/**
	 * Log error.
	 *
	 * @param throwable
	 *            the throwable
	 */
	public void logError(Throwable throwable) {
		logError(getValueFromBundle("error"), throwable);
	}

	/**
	 * Log error.
	 *
	 * @param message
	 *            the message
	 */
	public void logError(String message) {
		logError(message, null);
	}

	/**
	 * Log error.
	 *
	 * @param message
	 *            the message
	 * @param throwable
	 *            the throwable
	 */
	public void logError(String message, Throwable throwable) {
		if (InternalPolicy.OSGI_AVAILABLE) {
			if (null != message && !message.isEmpty())
				getErrConsoleStream().println(prefix().append(message).toString());
			if (null != throwable)
				getErrConsoleStream().println(getErrorString(throwable));
		} else {
			getErrConsoleStream().println(message);
			if (null != throwable)
				throwable.printStackTrace();
		}
	}

	/**
	 * Clear console.
	 */
	public void clearConsole() {
		if (null != launcherConsole) {
			launcherConsole.clearConsole();
		}
	}

	/**
	 * Gets the error string.
	 *
	 * @param throwable
	 *            the throwable
	 * @return the error string
	 */
	private String getErrorString(Throwable throwable) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		throwable.printStackTrace(ps);
		ps.flush();
		try {
			baos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return baos.toString();
	}

	/**
	 * Inits the console.
	 */
	private void initConsole() {
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		for (IConsole console : consoleManager.getConsoles())
			if (console instanceof MessageConsole && console.getName().contains("Adichatz")) {
				launcherConsole = (MessageConsole) console;
				break;
			}
		if (null == launcherConsole)
			launcherConsole = new MessageConsole(getValueFromBundle("launcher.console"), null);
		outConsoleStream = launcherConsole.newMessageStream();
		outConsoleStream.setActivateOnWrite(true);
		errConsoleStream = launcherConsole.newMessageStream();
		errConsoleStream.setActivateOnWrite(true);
		if (null != Display.getDefault()) {
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					outConsoleStream.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
					errConsoleStream.setColor(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				}
			});
		}
		consoleManager.addConsoles(new IConsole[] { launcherConsole });
		launcherConsole.initialize();
	}

	/**
	 * Gets the err console stream.
	 *
	 * @return the err console stream
	 */
	public MessageConsoleStream getErrConsoleStream() {
		if (null == errConsoleStream)
			initConsole();
		return errConsoleStream;
	}

	/**
	 * Gets the out console stream.
	 *
	 * @return the out console stream
	 */
	public MessageConsoleStream getOutConsoleStream() {
		if (null == outConsoleStream)
			initConsole();
		return outConsoleStream;
	}

	/**
	 * Prefix.
	 *
	 * @return the string buffer
	 */
	private StringBuffer prefix() {
		StringBuffer stringBuffer = new StringBuffer("ERROR ").append(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()))
				.append(" - ");
		StackTraceLocation stackTraceLocation = getStackTraceLocation();
		return stringBuffer.append(stackTraceLocation.className).append("[").append(stackTraceLocation.lineNumber).append("]");
	}

	/**
	 * Gets the stack trace location.
	 *
	 * @return the stack trace location
	 */
	private StackTraceLocation getStackTraceLocation() {
		StackTraceLocation stackTraceLocation = new StackTraceLocation();
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements)
			if (!EXCLUDED_CLASSES.contains(stackTraceElement.getClassName())) {
				stackTraceLocation.className = stackTraceElement.getFileName().substring(0,
						stackTraceElement.getFileName().indexOf('.'));
				stackTraceLocation.lineNumber = stackTraceElement.getLineNumber();
				break;
			}
		return stackTraceLocation;

	}

	/**
	 * The Class StackTraceLocation.
	 *
	 * @author Yves Arpheuil
	 */
	class StackTraceLocation {

		/** The class name. */
		String className = "";

		/** The line number. */
		int lineNumber = -1;
	}
}
