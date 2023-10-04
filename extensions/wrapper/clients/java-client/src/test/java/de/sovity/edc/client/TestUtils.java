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

import org.eclipse.edc.junit.extensions.EdcExtension;
import org.eclipse.edc.spi.protocol.ProtocolWebhook;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static de.sovity.edc.ext.wrapper.api.ui.pages.dashboard.services.ConfigPropertyUtils.configKey;
import static org.eclipse.edc.junit.testfixtures.TestUtils.getFreePort;
import static org.mockito.Mockito.mock;

public class TestUtils {
    private static final int DATA_PORT = getFreePort();
    private static final int PROTOCOL_PORT = getFreePort();
    private static final String DATA_PATH = "/api/management";
    private static final String PROTOCOL_PATH = "/api/dsp";
    public static final String MANAGEMENT_API_KEY = "123456";
    public static final String MANAGEMENT_ENDPOINT = "http://localhost:" + DATA_PORT + DATA_PATH;


    public static final String PROTOCOL_HOST = "http://localhost:" + PROTOCOL_PORT;
    public static final String PROTOCOL_ENDPOINT = PROTOCOL_HOST + PROTOCOL_PATH;

    @NotNull
    public static Map<String, String> createConfiguration(
            Map<String, String> additionalConfigProperties
    ) {
        Map<String, String> config = new HashMap<>();
        config.put("web.http.port", String.valueOf(getFreePort()));
        config.put("web.http.path", "/api");
        config.put("web.http.management.port", String.valueOf(DATA_PORT));
        config.put("web.http.management.path", DATA_PATH);
        config.put("web.http.protocol.port", String.valueOf(PROTOCOL_PORT));
        config.put("web.http.protocol.path", PROTOCOL_PATH);
        config.put("edc.api.auth.key", MANAGEMENT_API_KEY);
        config.put("edc.dsp.callback.address", PROTOCOL_ENDPOINT);
        config.put("edc.oauth.provider.audience", "idsc:IDS_CONNECTORS_ALL");

        config.put("edc.participant.id", "my-edc-participant-id");
        config.put("my.edc.title", "My Connector");
        config.put("my.edc.description", "My Connector Description");
        config.put("my.edc.curator.url", "https://connector.my-org");
        config.put("my.edc.curator.name", "My Org");
        config.put("my.edc.maintainer.url", "https://maintainer-org");
        config.put("my.edc.maintainer.name", "Maintainer Org");

        config.put("edc.oauth.token.url", "https://token-url.daps");
        config.put("edc.oauth.provider.jwks.url", "https://jwks-url.daps");
        config.put("tx.ssi.miw.authority.id", "my-authority-id");
        config.put("tx.ssi.miw.url", "https://miw");
        config.put("tx.ssi.oauth.token.url", "https://token.miw");
        config.putAll(additionalConfigProperties);
        return config;
    }

    public static void setupExtension(EdcExtension extension) {
        setupExtension(extension, Map.of());
    }

    public static void setupExtension(EdcExtension extension, Map<String, String> configProperties) {
        extension.registerServiceMock(ProtocolWebhook.class, mock(ProtocolWebhook.class));
        extension.setConfiguration(createConfiguration(configProperties));
    }

    public static EdcClient edcClient() {
        return EdcClient.builder()
                .managementApiUrl(TestUtils.MANAGEMENT_ENDPOINT)
                .managementApiKey(TestUtils.MANAGEMENT_API_KEY)
                .build();
    }
}