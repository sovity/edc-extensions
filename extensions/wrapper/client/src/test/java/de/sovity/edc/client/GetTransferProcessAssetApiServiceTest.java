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
    void startTransferProcessForAgreement(
            ContractNegotiationStore negotiationStore,
            TransferProcessStore transferProcessStore,
            AssetService assetStore
    ) throws ParseException {
        var client = TestUtils.edcClient();

        // arrange
        createTransferProcesses(negotiationStore
                , transferProcessStore
                , assetStore
        );

        // act
        var providerAssetResult = client.uiApi().getTransferProcessAsset(PROVIDING_TRANSFER_PROCESS_ID);
        var consumerAssetResult = client.uiApi().getTransferProcessAsset(CONSUMING_TRANSFER_PROCESS_ID);

        // assert for provider type transfer process
        assertThat(providerAssetResult.getId()).isEqualTo(VALID_ASSET_ID);
        assert providerAssetResult.getProperties() != null;
        assertThat(providerAssetResult.getProperties().get("asset:prop:name")).isEqualTo(ASSET_NAME);

        // assert for consumer type transfer process
        assertThat(consumerAssetResult.getId()).isEqualTo(RANDOM_DELETED_ASSET_ID);
        assert consumerAssetResult.getProperties() != null;
        assertThat(consumerAssetResult.getProperties().get("asset:prop:name")).isNull();
    }
}
