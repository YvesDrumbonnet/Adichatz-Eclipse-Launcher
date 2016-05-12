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
package org.adichatz.launcher.editor;

import static org.adichatz.launcher.LauncherTools.getRegisteredImage;
import static org.adichatz.launcher.LauncherTools.getRegisteredImageDescriptor;
import static org.adichatz.launcher.LauncherTools.getValueFromBundle;

import java.io.File;

import org.adichatz.launcher.LauncherExecution;
import org.adichatz.launcher.LauncherTools;
import org.adichatz.launcher.xjc.EnvTypeEnum;
import org.adichatz.launcher.xjc.LauncherType;
import org.adichatz.launcher.xjc.MenuType;
import org.adichatz.launcher.xjc.NodeType;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

// TODO: Auto-generated Javadoc
/**
 * The Class LauncherTreeFormPage.
 *
 * @author Yves Arpheuil
 */
public class LauncherTreeFormPage extends FormPage {

	/** The id. */
	private static String ID = "org.adichatz.launcher.tree.page";

	/** The java button. */
	private Button javaButton;

	/** The ant button. */
	private Button antButton;

	/** The filtered tree. */
	private FilteredTree filteredTree;

	/** The label provider. */
	private LabelProvider labelProvider;

	/**
	 * Instantiates a new launcher tree form page.
	 *
	 * @param editor
	 *            the editor
	 */
	public LauncherTreeFormPage(FormEditor editor) {
		super(editor, ID, getValueFromBundle("launcher.editor.tree.page"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.pde.internal.ui.editor.PDEFormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(getValueFromBundle("launcher.tree.page"));
		form.setImage(getRegisteredImage(LauncherTools.IMG_TREE));
		toolkit.decorateFormHeading(form.getForm());
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new FillLayout());

		PatternFilter patternFilter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(Viewer viewer, Object element) {
				String labelText = labelProvider.getText(element);
				boolean visible = null == labelText ? false : wordMatches(labelText);
				return visible && isCompatible(element);
			}

			private boolean isCompatible(Object element) {
				if (element instanceof LauncherType) {
					LauncherType launcher = (LauncherType) element;
					if (!javaButton.getSelection() && EnvTypeEnum.JAVA == launcher.getEnvType())
						return false;
					if (!antButton.getSelection() && EnvTypeEnum.ANT == launcher.getEnvType())
						return false;
				}
				return true;
			}
		};
		patternFilter.setPattern("org.eclipse.ui.keys.optimization.false");
		filteredTree = new FilteredTree(body, SWT.SINGLE, patternFilter, true) {
			@Override
			protected void updateToolbar(boolean visible) {
				super.updateToolbar(visible);
				if (filterText.getText().isEmpty())
					treeViewer.expandAll();
			}

			@Override
			protected void createFilterText(Composite parent) {
				super.createFilterText(parent);
				GridLayout gridLayout = new GridLayout(4, false);
				parent.setLayout(gridLayout);
				javaButton = toolkit.createButton(parent, getValueFromBundle("java"), SWT.CHECK);
				javaButton.setSelection(true);
				javaButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						textChanged();
					}
				});
				antButton = toolkit.createButton(parent, getValueFromBundle("ant"), SWT.CHECK);
				antButton.setSelection(true);
				antButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						textChanged();
					}
				});
			}

		};

		TreeViewer treeViewer = filteredTree.getViewer();
		labelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof LauncherType)
					return LauncherTools.getText((NodeType) element).concat(" - ")
							.concat(((LauncherType) element).getLauncherURI());
				return LauncherTools.getText((NodeType) element);
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof NodeType) {
					String imageURI = ((NodeType) element).getIconURI();
					if (null == imageURI) {
						if (element instanceof LauncherType) {
							LauncherType launcher = (LauncherType) element;
							switch (launcher.getEnvType()) {
							case JAVA:
								imageURI = LauncherTools.IMG_RUN_JAVA;
								break;
							default:
								imageURI = LauncherTools.IMG_RUN_ANT;
								break;
							}
						} else
							imageURI = LauncherTools.IMG_MENU;
					}
					return getRegisteredImage(imageURI);
				}
				return null;
			}
		};
		treeViewer.setLabelProvider(labelProvider);

		ITreeContentProvider treeContentProvider = new ITreeContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean hasChildren(Object element) {
				return (element instanceof MenuType && !((MenuType) element).getMenuOrLauncher().isEmpty());
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return (Object[]) inputElement;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof MenuType)
					return ((MenuType) parentElement).getMenuOrLauncher().toArray();
				return null;
			}
		};
		treeViewer.setContentProvider(treeContentProvider);
		refresh();
		createActions(treeViewer);
		new LauncherColumnViewerToolTipSupport(treeViewer, toolkit);
	}

	/**
	 * Creates the actions.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	private void createActions(TreeViewer treeViewer) {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object selectedObject = ((StructuredSelection) event.getSelection()).getFirstElement();
				if (selectedObject instanceof LauncherType) {
					new LauncherExecution((LauncherType) selectedObject).exec();
				}
			}
		});

		MenuManager menuManager = new MenuManager();
		// Menu manager is attached to a control
		menuManager.setRemoveAllWhenShown(true);
		Menu menu = menuManager.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				LauncherType launcher = (LauncherType) ((StructuredSelection) treeViewer.getSelection()).getFirstElement();
				mgr.add(new Action(getValueFromBundle("run"), getRegisteredImageDescriptor(LauncherTools.IMG_RUN)) {
					@Override
					public void run() {
						new LauncherExecution(launcher).exec();
					}
				});
				mgr.add(new Action(getValueFromBundle("open"), getRegisteredImageDescriptor(LauncherTools.IMG_EDITOR)) {
					@Override
					public void run() {
						new LauncherExecution(launcher).openFile();
					}
				});
			}
		});
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		File launcherFile = LauncherTools.getLauncherFile();
		LauncherTools.LAUNCHER_TREE = LauncherTools.unmarshal(launcherFile);
		filteredTree.getViewer().setInput(LauncherTools.LAUNCHER_TREE.getMenuOrLauncher().toArray());
		filteredTree.getViewer().expandAll();
	}
}
