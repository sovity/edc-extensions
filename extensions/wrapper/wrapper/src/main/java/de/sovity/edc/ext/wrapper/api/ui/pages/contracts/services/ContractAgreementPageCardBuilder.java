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

package de.sovity.edc.ext.wrapper.api.ui.pages.contracts.services;

import static de.sovity.edc.ext.wrapper.utils.EdcDateUtils.utcMillisToOffsetDateTime;
import static de.sovity.edc.ext.wrapper.utils.EdcDateUtils.utcSecondsToOffsetDateTime;
import static de.sovity.edc.ext.wrapper.utils.MapUtils.mapValues;

import de.sovity.edc.ext.wrapper.api.common.mappers.PolicyMapper;
import de.sovity.edc.ext.wrapper.api.common.model.AssetDto;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractAgreementCard;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractAgreementDirection;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractAgreementTransferProcess;
import java.util.Comparator;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.edc.connector.contract.spi.types.agreement.ContractAgreement;
import org.eclipse.edc.connector.contract.spi.types.negotiation.ContractNegotiation;
import org.eclipse.edc.connector.transfer.spi.types.TransferProcess;
import org.eclipse.edc.spi.types.domain.asset.Asset;
import org.jetbrains.annotations.NotNull;

@Slf4j
@RequiredArgsConstructor
public class ContractAgreementPageCardBuilder {
    private final PolicyMapper policyMapper;
    private final TransferProcessStateService transferProcessStateService;

    @NotNull
    public ContractAgreementCard buildContractAgreementCard(
            @NonNull ContractAgreement agreement,
            @NonNull ContractNegotiation negotiation,
            @NonNull Asset asset,
            @NonNull List<TransferProcess> transferProcesses
    ) {
        ContractAgreementCard card = new ContractAgreementCard();
        card.setContractAgreementId(agreement.getId());
        card.setContractNegotiationId(negotiation.getId());
        card.setDirection(ContractAgreementDirection.fromType(negotiation.getType()));
        card.setCounterPartyAddress(negotiation.getCounterPartyAddress());
        card.setCounterPartyId(negotiation.getCounterPartyId());
        card.setContractSigningDate(utcSecondsToOffsetDateTime(agreement.getContractSigningDate()));
        card.setAsset(buildAssetDto(asset));
        card.setContractPolicy(policyMapper.buildUiPolicy(agreement.getPolicy()));
        card.setTransferProcesses(buildTransferProcesses(transferProcesses));
        return card;
    }

    @NotNull
    private List<ContractAgreementTransferProcess> buildTransferProcesses(
            @NonNull List<TransferProcess> transferProcessEntities
    ) {
        return transferProcessEntities.stream()
                .map(this::buildContractAgreementTransfer)
                .sorted(Comparator.comparing(ContractAgreementTransferProcess::getLastUpdatedDate)
                        .reversed())
                .toList();
    }

    @NotNull
    private ContractAgreementTransferProcess buildContractAgreementTransfer(
            TransferProcess transferProcessEntity) {
        var transferProcess = new ContractAgreementTransferProcess();
        transferProcess.setTransferProcessId(transferProcessEntity.getId());
        transferProcess.setLastUpdatedDate(
                utcMillisToOffsetDateTime(transferProcessEntity.getUpdatedAt()));
        transferProcess.setState(transferProcessStateService.buildTransferProcessState(
                transferProcessEntity.getState()));
        transferProcess.setErrorMessage(transferProcessEntity.getErrorDetail());
        return transferProcess;
    }

    @NotNull
    private AssetDto buildAssetDto(@NonNull Asset asset) {
        var createdAt = utcMillisToOffsetDateTime(asset.getCreatedAt());
        var properties = mapValues(asset.getProperties(), Object::toString);
        return new AssetDto(asset.getId(), createdAt, properties);
    }
}
