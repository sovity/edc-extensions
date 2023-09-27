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

package de.sovity.edc.ext.wrapper.api.ui.pages.dashboard;

import de.sovity.edc.ext.wrapper.api.common.mappers.AssetMapper;
import de.sovity.edc.ext.wrapper.api.common.mappers.PolicyMapper;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractAgreementCard;
import de.sovity.edc.ext.wrapper.api.ui.model.DashboardPage;
import de.sovity.edc.ext.wrapper.api.ui.pages.contracts.services.ContractAgreementPageCardBuilder;
import de.sovity.edc.ext.wrapper.api.ui.pages.dashboard.services.DashboardDataFetcher;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

import static org.eclipse.edc.connector.contract.spi.types.negotiation.ContractNegotiation.Type.CONSUMER;
import static org.eclipse.edc.connector.contract.spi.types.negotiation.ContractNegotiation.Type.PROVIDER;

@RequiredArgsConstructor
public class DashboardPageApiService {
    private final DashboardDataFetcher dashboardDataFetcher;
    private final ContractAgreementPageCardBuilder contractAgreementPageCardBuilder;
    private final AssetMapper assetMapper;
    private final PolicyMapper policyMapper;

    @NotNull
    public DashboardPage dashboardPage(String connectorEndpoint) {
        var agreements = dashboardDataFetcher.getContractAgreements();

        var cards = agreements.stream()
                .map(agreement -> contractAgreementPageCardBuilder.buildContractAgreementCard(
                        agreement.agreement(), agreement.negotiation(), agreement.asset(), agreement.transfers()))
                .sorted(Comparator.comparing(ContractAgreementCard::getContractSigningDate).reversed())
                .toList();

        var numberOfProvidingAgreements = cards.stream()
                .filter(card -> card.getDirection().getType().equals(PROVIDER))
                .count();

        var numberOfConsumingAgreements = cards.stream()
                .filter(card -> card.getDirection().getType().equals(CONSUMER))
                .count();

        var numberOfAssets = dashboardDataFetcher.getAllAssets().stream().map(assetMapper::buildUiAsset).toList().size();
        var numberOfPolicies = dashboardDataFetcher.getAllPolicies().stream().map(policyMapper::buildUiPolicy).toList().size();

        return new DashboardPage((Integer) dashboardDataFetcher.getTransferProcessesAmount(), numberOfAssets, numberOfPolicies, numberOfConsumingAgreements, numberOfProvidingAgreements, connectorEndpoint);
    }
}
