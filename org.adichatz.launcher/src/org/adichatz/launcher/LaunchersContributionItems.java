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

import java.util.List;

import org.adichatz.launcher.editor.AdiXMLMultiPageEditorPart;
import org.adichatz.launcher.xjc.EnvTypeEnum;
import org.adichatz.launcher.xjc.LauncherTree;
import org.adichatz.launcher.xjc.LauncherType;
import org.adichatz.launcher.xjc.MenuType;
import org.adichatz.launcher.xjc.NodeType;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

// TODO: Auto-generated Javadoc
/**
 * The Class LaunchersContributionItems.
 *
 * @author Yves Arpheuil
 */
@SuppressWarnings("restriction")
public class LaunchersContributionItems extends CompoundContributionItem implements IWorkbenchContribution {

	/** The logger. */
	/*
	 * S T A T I C
	 */
	protected static Logger logger = Logger.getInstance();

	/** The index. */
	private int index;

	/*
	 * end S T A T I C
	 */

	/** The contribution items. */
	private IContributionItem[] contributionItems;

	/**
	 * The Enum LAUNCHER_TYPE.
	 *
	 * @author Yves Arpheuil
	 */
	public enum LAUNCHER_TYPE {

		/** The run. */
		RUN,
		/** The editor. */
		EDITOR
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
	 */
	@Override
	protected IContributionItem[] getContributionItems() {
		index = 0;
		int maxRecentListSize = Activator.getDefault().getPreferenceStore().getInt(LauncherTools.PREFS_RECENT_LIST_SIZE);
		LauncherTools.LAUNCHER_TREE = LauncherTools.unmarshal(LauncherTools.getLauncherFile());
		int nbItems = LauncherTools.LAUNCHER_TREE.getMenuOrLauncher().size() + 3;
		LauncherTree recentLauncherTree = LauncherTools.getRecentLauncherTree();
		if (null != recentLauncherTree && !recentLauncherTree.getMenuOrLauncher().isEmpty()) {
			int currentListSize = recentLauncherTree.getMenuOrLauncher().size();
			if (currentListSize > maxRecentListSize)
				currentListSize = maxRecentListSize;
			nbItems += currentListSize + 1;
		}
		if (null != LauncherTools.LAUNCHER_TREE) {
			contributionItems = new IContributionItem[nbItems];
			for (NodeType node : LauncherTools.LAUNCHER_TREE.getMenuOrLauncher()) {
				contributionItems[index++] = new ContributionItem("run") {
					public void fill(Menu parent, int index) {
						if (node instanceof LauncherType) {
							addRunMenuItem(parent, (LauncherType) node);
						} else {
							try {
								Menu subMenu = new Menu(parent);
								MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
								menuItem.setText(LauncherTools.getText(node));
								menuItem.setToolTipText(LauncherTools.getToolTipText(node));
								menuItem.setMenu(subMenu);
								buildMenu(subMenu, ((MenuType) node).getMenuOrLauncher(), true);
							} catch (Exception e) {
								logger.logError(e);
							}

						}
					}
				};
			}
		}
		contributionItems[index++] = new ContributionItem("editor") {
			public void fill(Menu parent, int index) {
				MenuItem menuItem = new MenuItem(parent, SWT.PUSH);
				menuItem.setImage(LauncherTools.getRegisteredImage(LauncherTools.IMG_EDIT_XML));
				menuItem.setText(getValueFromBundle("open.launcher.file"));
				menuItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						LauncherTools.openLauncherFile();
					}
				});
			}
		};
		contributionItems[index++] = new Separator();
		contributionItems[index++] = new ContributionItem("open") {
			public void fill(Menu parent, int index) {
				try {
					Menu subMenu = new Menu(parent);
					MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
					menuItem.setText(getValueFromBundle("open"));
					menuItem.setImage(LauncherTools.getRegisteredImage(LauncherTools.IMG_EDITOR));
					menuItem.setMenu(subMenu);
					buildMenu(subMenu, LauncherTools.LAUNCHER_TREE.getMenuOrLauncher(), false);
					if (!LauncherTools.LAUNCHER_TREE.getMenuOrLauncher().isEmpty())
						new Separator();
					MenuItem openLastsItem = new MenuItem(subMenu, SWT.PUSH);
					openLastsItem.setImage(LauncherTools.getRegisteredImage(LauncherTools.IMG_EDIT_XML));
					openLastsItem.setText(getValueFromBundle("open.recent.launches.file"));
					openLastsItem.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							IFileStore fileStore = EFS.getLocalFileSystem()
									.getStore(new Path(LauncherTools.getLastLauncherXMLFile().getAbsolutePath()));
							if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
								FileStoreEditorInput input = new FileStoreEditorInput(fileStore);
								try {
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input,
											AdiXMLMultiPageEditorPart.class.getName());
								} catch (PartInitException exception) {
									logger.logError(exception);
								}
							}
						}
					});

				} catch (Exception e) {
					logger.logError(e);
				}
			}
		};
		if (null != recentLauncherTree && !recentLauncherTree.getMenuOrLauncher().isEmpty()) {
			contributionItems[index++] = new Separator();
			int i = 0;
			for (NodeType node : recentLauncherTree.getMenuOrLauncher()) {
				contributionItems[index++] = new ContributionItem(LauncherTools.getText(node)) {
					public void fill(Menu parent, int index) {
						addRunMenuItem(parent, (LauncherType) node);
					}
				};
				i++;
				if (i == maxRecentListSize)
					break;
			}
		}
		return contributionItems;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.menus.IWorkbenchContribution#initialize(org.eclipse.ui.services.IServiceLocator)
	 */
	@Override
	public void initialize(IServiceLocator serviceLocator) {
	}

	/**
	 * Gets the image uri.
	 *
	 * @param node
	 *            the node
	 * @return the image uri
	 */
	private String getImageURI(NodeType node) {
		String imageURI = node.getIconURI();
		if (null == imageURI) {
			if (node instanceof LauncherType) {
				if (((LauncherType) node).getEnvType() == EnvTypeEnum.ANT)
					imageURI = LauncherTools.IMG_RUN_ANT;
				else
					imageURI = LauncherTools.IMG_RUN_JAVA;
			}
		} else
			imageURI = LauncherTools.IMG_MENU;
		return imageURI;
	}

	private SelectionAdapter createSelectionAdapter(NodeType node, boolean run) {
		if (run)
			return new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					new LauncherExecution((LauncherType) node).exec();
				}
			};
		else
			return new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					new LauncherExecution((LauncherType) node).openFile();
				}
			};
	}

	/**
	 * Adds the run menu item.
	 *
	 * @param menu
	 *            the menu
	 * @param node
	 *            the node
	 */
	private void addRunMenuItem(Menu menu, LauncherType node) {
		addLauncherMenuItem(menu, node, createSelectionAdapter(node, true));
	}

	/**
	 * Adds the launcher menu item.
	 *
	 * @param menu
	 *            the menu
	 * @param node
	 *            the node
	 * @param selectionAdapter
	 *            the selection adapter
	 */
	private void addLauncherMenuItem(Menu menu, LauncherType node, SelectionAdapter selectionAdapter) {
		MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
		menuItem.setText(LauncherTools.getText(node));
		menuItem.setToolTipText(LauncherTools.getToolTipText(node));
		menuItem.setImage(LauncherTools.getRegisteredImage(getImageURI(node)));
		menuItem.addSelectionListener(selectionAdapter);
	}

	/**
	 * Builds the menu.
	 *
	 * @param menu
	 *            the menu
	 * @param nodes
	 *            the nodes
	 */
	private void buildMenu(Menu menu, List<NodeType> nodes, boolean run) {
		for (NodeType node : nodes) {
			String imageURI = node.getIconURI();
			if (node instanceof MenuType) {
				Menu subMenu = new Menu(menu);
				MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
				menuItem.setText(LauncherTools.getText(node));
				menuItem.setToolTipText(LauncherTools.getToolTipText(node));
				menuItem.setMenu(subMenu);
				menuItem.setImage(LauncherTools.getRegisteredImage(null != imageURI ? imageURI : LauncherTools.IMG_MENU));
				buildMenu(subMenu, ((MenuType) node).getMenuOrLauncher(), run);
			} else {
				addLauncherMenuItem(menu, (LauncherType) node, createSelectionAdapter(node, run));
			}
		}
	}
}
