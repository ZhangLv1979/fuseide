/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.fusesource.ide.camel.tests.util;

import java.io.File;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.fusesource.ide.branding.perspective.FusePerspective;

/**
 * @author lhein
 *
 */
public class CommonTestUtils {
	
	/**
	 * closes all editors, perspectives, the welcome page and then switches to fuse integration perspective
	 * and creating a screenshot folder
	 * 
	 * @param screenshotFolder	the folder to store screenshots
	 * @throws Exception
	 */
	public static void prepareIntegrationTestLaunch(String screenshotFolder) throws Exception {
		closeAllEditors();
		enablePerspectiveSwitchPreset();
		createScreenshotFolder(screenshotFolder);		
		closeWelcomePage();
		closeAllPerspectives();
		openFuseIntegrationPerspective();
		
	}

	/**
	 * opens the fuse integration perspective
	 * 
	 * @throws Exception
	 */
	public static void openFuseIntegrationPerspective() throws Exception {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			PlatformUI.getWorkbench().showPerspective(FusePerspective.ID, page.getWorkbenchWindow());
		}
	}
	
	/**
	 * closes all perspectives
	 */
	public static void closeAllPerspectives() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			page.closeAllPerspectives(false, false);
		}
	}
	
	/**
	 * closes the welcome page
	 */
	public static void closeWelcomePage() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			IWorkbenchPart welcomePage = page.getActivePart();
			if(welcomePage != null){
				welcomePage.dispose();
			}
		}
	}
	
	/**
	 * creates the screenshot folder in the given location 
	 * 
	 * @param folder
	 */
	public static void createScreenshotFolder(String folder) {
		File f = new File(folder);
		f.mkdirs();
	}
	
	/**
	 * sets preference to always switch to the right perspective	
	 */
	public static void enablePerspectiveSwitchPreset() {
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		store.setValue(IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE, IDEInternalPreferences.PSPM_ALWAYS);
		
	}
	
	/**
	 * closes all editors
	 */
	public static void closeAllEditors() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}
	
	/**
	 * @return
	 */
	public static IEditorPart getCurrentActiveEditor() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			return page.getActiveEditor();
		}
		return null;
	}
	
	/**
	 * process ui events
	 * 
	 * @param currentNumberOfTry
	 */
	public static void readAndDispatch(int currentNumberOfTry) {
		try{
			while (Display.getDefault().readAndDispatch()) {
				
			}
		} catch(SWTException swtException){
			//TODO: remove try catch when https://issues.jboss.org/browse/FUSETOOLS-1913 is done (CI with valid GUI)
			swtException.printStackTrace();
			if(currentNumberOfTry < 100){
				readAndDispatch(currentNumberOfTry ++);
			} else {
				System.out.println("Tried 100 times to wait for UI... Continue and see what happens.");
			}
		}
	}
}
