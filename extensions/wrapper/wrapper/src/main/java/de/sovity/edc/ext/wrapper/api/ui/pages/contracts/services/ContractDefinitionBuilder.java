

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

package de.sovity.edc.ext.wrapper.api.ui.pages.contracts.services;


import de.sovity.edc.ext.wrapper.api.ui.model.ContractDefinitionRequest;
import de.sovity.edc.ext.wrapper.api.ui.pages.contracts.services.utils.ContractDefinitionUtils;
import lombok.RequiredArgsConstructor;
import org.eclipse.edc.connector.contract.spi.types.offer.ContractDefinition;
import java.util.UUID;

@RequiredArgsConstructor
public class ContractDefinitionBuilder {

    private final ContractDefinitionUtils contractDefinitionUtils;


    public ContractDefinition buildContractDefinition(
            ContractDefinitionRequest request
    ) {
        var contractPolicyId = request.getContractPolicyId();
        var accessPolicyId = request.getAccessPolicyId();
        var assetsSelector = request.getAssetsSelector();
        var assetsSelectorCriterion = request.getAssetsSelectorCriterion();


        return ContractDefinition.Builder.newInstance()
                .id(UUID.randomUUID().toString())
                .contractPolicyId(contractPolicyId)
                .accessPolicyId(accessPolicyId)
                .assetsSelector(contractDefinitionUtils.mapToCriteria(assetsSelector))
                .assetsSelectorCriterion(contractDefinitionUtils.mapToCriterion(assetsSelectorCriterion))
                .build();
    }
}
