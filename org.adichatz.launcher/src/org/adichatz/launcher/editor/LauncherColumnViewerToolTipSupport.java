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

import static org.adichatz.launcher.LauncherTools.getValueFromBundle;

import java.text.DateFormat;

import org.adichatz.launcher.LauncherTools;
import org.adichatz.launcher.xjc.LauncherType;
import org.adichatz.launcher.xjc.MenuType;
import org.adichatz.launcher.xjc.NodeType;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

// TODO: Auto-generated Javadoc
/**
 * The Class LauncherColumnViewerToolTipSupport.
 *
 * @author Yves Arpheuil
 */
public class LauncherColumnViewerToolTipSupport extends ColumnViewerToolTipSupport {

	/** The toolkit. */
	private FormToolkit toolkit;

	/** The label provider. */
	private LabelProvider labelProvider;

	/**
	 * Instantiates a new view column viewer tool tip support.
	 *
	 * @param viewer
	 *            the viewer
	 * @param toolkit
	 *            the toolkit
	 */
	public LauncherColumnViewerToolTipSupport(ColumnViewer viewer, FormToolkit toolkit) {
		super(viewer, ToolTip.NO_RECREATE, false);
		this.labelProvider = (LabelProvider) viewer.getLabelProvider();
		viewer.setLabelProvider(new RecentDecoratingStyledCellLabelProvider(new RecentLabelProvider()));
		this.toolkit = toolkit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ColumnViewerToolTipSupport#createViewerToolTipContentArea(org.eclipse.swt.widgets.Event,
	 * org.eclipse.jface.viewers.ViewerCell, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createViewerToolTipContentArea(Event event, ViewerCell cell, Composite parent) {
		Composite content = toolkit.createComposite(parent);
		content.setLayout(new GridLayout(1, false));

		Group editorGroup = new Group(content, SWT.SMOOTH);
		toolkit.adapt(editorGroup);
		NodeType node = (NodeType) cell.getElement();
		editorGroup.setText(getValueFromBundle(node instanceof MenuType ? "menu" : "launcher"));
		editorGroup.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData();
		gridData.minimumWidth = 200;
		gridData.grabExcessHorizontalSpace = true;
		editorGroup.setLayoutData(gridData);
		addLabel(node, editorGroup, "id", node.getId());
		addLabel(node, editorGroup, "label", node.getLabel());
		if (node instanceof LauncherType) {
			addLabel(node, editorGroup, "launcherURI", ((LauncherType) node).getLauncherURI());
			addLabel(node, editorGroup, "env", ((LauncherType) node).getEnvType().name());
			addLabel(node, editorGroup, "encoding", ((LauncherType) node).getEncoding());
			LauncherType recentLaunch = LauncherTools.getRecentLaunch((LauncherType) node);
			if (null != recentLaunch) {
				if (null != recentLaunch.getLastLaunched()) {
					String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
							.format(recentLaunch.getLastLaunched().toGregorianCalendar().getTime());
					addLabel(node, editorGroup, "lastLaunch", date);
				}
				if (null != recentLaunch.getReturnCode())
					addLabel(node, editorGroup, "returnCode", recentLaunch.getReturnCode().toString());
			}
		}
		return content;
	}

	/**
	 * Adds the label.
	 *
	 * @param node
	 *            the node
	 * @param composite
	 *            the composite
	 * @param labelKey
	 *            the label key
	 * @param value
	 *            the value
	 */
	private void addLabel(NodeType node, Composite composite, String labelKey, String value) {
		if (null != value && !value.isEmpty()) {
			Label wording = toolkit.createLabel(composite, getValueFromBundle(labelKey));
			wording.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			Label label = toolkit.createLabel(composite, value);
			label.setFont(LauncherTools.getModifiedFont(label.getFont(), SWT.BOLD));
		}
	}

	/**
	 * The Class RecentDecoratingStyledCellLabelProvider.
	 *
	 * @author Yves Arpheuil
	 */
	// ============================================================================================================================================================
	class RecentDecoratingStyledCellLabelProvider extends DecoratingStyledCellLabelProvider {

		/** The label provider. */
		private final RecentLabelProvider labelProvider;

		/**
		 * Instantiates a new view decorating styled cell label provider.
		 *
		 * @param labelProvider
		 *            the label provider
		 */
		private RecentDecoratingStyledCellLabelProvider(RecentLabelProvider labelProvider) {
			super(labelProvider, null, null);
			this.labelProvider = labelProvider;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipDisplayDelayTime(java.lang.Object)
		 */
		@Override
		public int getToolTipDisplayDelayTime(Object object) {
			return 500;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipFont(java.lang.Object)
		 */
		@Override
		public Font getToolTipFont(Object object) {
			if (labelProvider instanceof CellLabelProvider) {
				return ((CellLabelProvider) labelProvider).getToolTipFont(object);
			}
			return super.getToolTipFont(object);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipImage(java.lang.Object)
		 */
		public Image getToolTipImage(Object element) {
			return labelProvider.getImage(element);
		}

	}

	// ============================================================================================================================================================

	/**
	 * The Class RecentLabelProvider.
	 *
	 * @author Yves Arpheuil
	 */
	class RecentLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof LauncherType)
				return LauncherTools.getText((NodeType) element).concat(" - ").concat(((LauncherType) element).getLauncherURI());
			return LauncherTools.getText((NodeType) element);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			return labelProvider.getImage(element);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
		 */
		@Override
		public StyledString getStyledText(Object element) {
			return new StyledString(getText(element));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
		 */
		@Override
		public String getToolTipText(Object element) {
			return getText(element);
		}

	}
}
