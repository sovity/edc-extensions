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

package de.sovity.edc.client;

import org.eclipse.edc.connector.contract.spi.negotiation.store.ContractNegotiationStore;
import org.eclipse.edc.connector.spi.asset.AssetService;
import org.eclipse.edc.connector.transfer.spi.store.TransferProcessStore;
import org.eclipse.edc.junit.annotations.ApiTest;
import org.eclipse.edc.junit.extensions.EdcExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.Map;

import static de.sovity.edc.client.TransferProcessTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
@ExtendWith(EdcExtension.class)
class GetTransferProcessAssetApiServiceTest {

    @BeforeEach
    void setUp(EdcExtension extension) {
        extension.setConfiguration(TestUtils.createConfiguration(Map.of()));
    }

    @Test
    void testProviderTransferProcess(ContractNegotiationStore negotiationStore,
                                     TransferProcessStore transferProcessStore,
                                     AssetService assetStore) throws ParseException {

        EdcClient client = getEdcClient(negotiationStore, transferProcessStore, assetStore);

        var providerAssetResult = client.uiApi().getTransferProcessAsset(PROVIDING_TRANSFER_PROCESS_ID);

        assertThat(providerAssetResult.getAssetId()).isEqualTo(VALID_ASSET_ID);
        assertThat(providerAssetResult.getProperties().get("asset:prop:name")).isEqualTo(ASSET_NAME);

    }

    @Test
    void testConsumerTransferProcess(ContractNegotiationStore negotiationStore,
                                     TransferProcessStore transferProcessStore,
                                     AssetService assetStore) throws ParseException {

        EdcClient client = getEdcClient(negotiationStore, transferProcessStore, assetStore);

        var consumerAssetResult = client.uiApi().getTransferProcessAsset(CONSUMING_TRANSFER_PROCESS_ID);

        assertThat(consumerAssetResult.getAssetId()).isEqualTo(UNKNOWN_ASSET_ID);
        assertThat(consumerAssetResult.getProperties().get("asset:prop:name")).isNull();
    }

    private EdcClient getEdcClient(ContractNegotiationStore negotiationStore, TransferProcessStore transferProcessStore, AssetService assetStore) throws ParseException {
        var client = TestUtils.edcClient();

        createTransferProcesses(negotiationStore, transferProcessStore, assetStore);
        return client;
    }
}
