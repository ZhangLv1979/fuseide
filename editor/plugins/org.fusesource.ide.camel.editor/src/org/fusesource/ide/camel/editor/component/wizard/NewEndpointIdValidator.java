/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.fusesource.ide.camel.editor.component.wizard;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.fusesource.ide.camel.editor.internal.UIMessages;
import org.fusesource.ide.camel.model.service.core.model.AbstractCamelModelElement;

final class NewEndpointIdValidator implements IValidator {
	
	private AbstractCamelModelElement parent;

	public NewEndpointIdValidator(AbstractCamelModelElement parent) {
		this.parent = parent;
	}

	@Override
	public IStatus validate(Object value) {
		String id = (String) value;
		if (id == null || id.isEmpty()) {
			return ValidationStatus.error(UIMessages.GlobalEndpointWizardPage_idMandatoryMessage);
		}
		if(!parent.findAllNodesWithId(id).isEmpty()){
			return ValidationStatus.error(UIMessages.GlobalEndpointWizardPage_idExistingMessage);
		}
		return ValidationStatus.ok();
	}
}