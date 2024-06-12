
/*
 *  Copyright (c) 2022 sovity GmbH
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

package de.sovity.edc.ext.wrapper.api.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Data Offer Data Source Model. Supports certain Data Address types but also leaves a backdoor for custom Data Address Properties.")
public class UiDataSource {
    @Schema(
        description = "Data Address Type. " +
            "Supported types are HTTP_DATA and CUSTOM.",
        defaultValue = "CUSTOM",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UiDataSourceType type;

    @Schema(
        description = "Only for type HTTP_DATA",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private UiDataSourceHttpData httpData;

    @Schema(
        description = "Only for type ON_REQUEST",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private UiDataSourceOnRequest onRequest;

    @Schema(
        description = "For all types. Custom Data Address Properties.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Map<String, String> customProperties;
}