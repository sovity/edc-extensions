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

package de.sovity.edc.client;

import de.sovity.edc.ext.wrapper.api.example.ExampleResource;
import de.sovity.edc.ext.wrapper.api.example.model.ExampleQuery;

import java.util.List;

/**
 * API Client for our EDC API Wrapper.
 *
 * @param exampleClient Example API
 */
public record EdcClient(
        ExampleResource exampleClient
) {
    public static EdcClientBuilder builder() {
        return new EdcClientBuilder();
    }

    public void testConnection() {
        // TODO implement better connection test
        exampleClient.exampleEndpoint(new ExampleQuery("", List.of()));
    }
}
