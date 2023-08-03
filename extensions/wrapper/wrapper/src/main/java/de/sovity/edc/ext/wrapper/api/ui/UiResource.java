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

package de.sovity.edc.ext.wrapper.api.ui;

import de.sovity.edc.ext.wrapper.api.common.model.AssetDto;
import de.sovity.edc.ext.wrapper.api.ui.model.*;
import de.sovity.edc.ext.wrapper.api.ui.pages.contracts.ContractAgreementPageApiService;
import de.sovity.edc.ext.wrapper.api.ui.pages.contracts.ContractAgreementTransferApiService;
import de.sovity.edc.ext.wrapper.api.ui.pages.contracts.ContractDefinitionApiService;
import de.sovity.edc.ext.wrapper.api.ui.pages.transferhistory.TransferHistoryPageApiService;
import de.sovity.edc.ext.wrapper.api.ui.pages.transferhistory.TransferHistoryPageAssetFetcherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.edc.api.model.IdResponse;

@Path("wrapper/ui")
@Tag(name = "UI", description = "EDC UI API Endpoints")
@RequiredArgsConstructor
public class UiResource {

    private final ContractAgreementPageApiService contractAgreementApiService;
    private final ContractAgreementTransferApiService contractAgreementTransferApiService;
    private final TransferHistoryPageApiService transferHistoryPageApiService;
    private final TransferHistoryPageAssetFetcherService transferHistoryPageAssetFetcherService;
    private final ContractDefinitionApiService contractDefinitionApiService;

    @GET
    @Path("pages/contract-agreement-page")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Collect all data for Contract Agreement Page")
    public ContractAgreementPage contractAgreementEndpoint() {
        return contractAgreementApiService.contractAgreementPage();
    }

    @POST
    @Path("pages/contract-agreement-page/transfers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Initiate a Transfer Process")
    public IdResponse initiateTransfer(
            ContractAgreementTransferRequest contractAgreementTransferRequest
    ) {
        return contractAgreementTransferApiService.initiateTransfer(
                contractAgreementTransferRequest
        );
    }

    @GET
    @Path("pages/transfer-history-page")
    @Produces(MediaType.APPLICATION_JSON)
    public TransferHistoryPage transferHistoryPageEndpoint() {
        return new TransferHistoryPage(transferHistoryPageApiService.getTransferHistoryEntries());
    }

    @GET
    @Path("pages/transfer-history-page/transfer-processes/{transferProcessId}/asset")
    @Produces(MediaType.APPLICATION_JSON)
    public AssetDto getTransferProcessAsset(@PathParam("transferProcessId") String transferProcessId) {
        return transferHistoryPageAssetFetcherService.getAssetForTransferHistoryPage(transferProcessId);
    }

    @GET
    @Path("pages/contract-definition-page")
    @Produces(MediaType.APPLICATION_JSON)
    public ContractDefinitionPage contractDefinitionPage() {
        return new ContractDefinitionPage(contractDefinitionApiService.getContractDefinitions());
    }

    @POST
    @Path("pages/contract-definition-page")
    @Produces(MediaType.APPLICATION_JSON)
    public IdResponse createContractDefinition(
            ContractDefinitionRequest contractDefinitionRequest
    ) {
        return contractDefinitionApiService.createContractDefinition(
                contractDefinitionRequest
        );
    }


}
