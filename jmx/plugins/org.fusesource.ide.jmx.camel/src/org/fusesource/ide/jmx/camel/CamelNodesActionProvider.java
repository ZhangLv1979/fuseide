/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.fusesource.ide.jmx.camel;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.fusesource.ide.foundation.ui.util.ContextMenuProvider;
import org.fusesource.ide.foundation.ui.util.Selections;

/**
 * @author lhein
 */
public class CamelNodesActionProvider extends CommonActionProvider {

	private ICommonActionExtensionSite site;

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		this.site = site;
		String viewId = site.getViewSite().getId();
		CamelJMXPlugin.getLogger().debug("============================= View ID: " + viewId);
	}

	public StructuredViewer getStructuredViewer() {
		return site.getStructuredViewer();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		Object o = Selections.getFirstSelection(site.getStructuredViewer().getSelection());
		if (o instanceof ContextMenuProvider) {
			ContextMenuProvider cmp = (ContextMenuProvider)o;
			cmp.provideContextMenu(menu);
		}
	}
}
