package org.adichatz.launcher.editor;

import org.adichatz.launcher.LauncherTools;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart;

@SuppressWarnings("restriction")
public class AdiXMLMultiPageEditorPart extends XMLMultiPageEditorPart {
	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		LauncherTools.RECENT_LAUNCHER_TREE = null;
	}
}
