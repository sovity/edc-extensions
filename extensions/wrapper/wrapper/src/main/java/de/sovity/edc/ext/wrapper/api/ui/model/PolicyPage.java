/*
 *  Copyright (c) 2023 sovity GmbH
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       sovity GmbH - initial API and implementation
 *
 */

package de.sovity.edc.ext.wrapper.api.ui.model;
import de.sovity.edc.ext.wrapper.api.common.model.PolicyDto;
import de.sovity.edc.ext.wrapper.api.common.model.UiPolicyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PolicyPage {
    @Schema(description = "Policy Entries", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<UiPolicyDto> policies;
}
