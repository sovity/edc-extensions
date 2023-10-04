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

import de.sovity.edc.ext.wrapper.api.common.model.PolicyDefinitionCreateRequest;
import de.sovity.edc.ext.wrapper.api.common.model.UiAsset;
import de.sovity.edc.ext.wrapper.api.common.model.UiAssetCreateRequest;
import de.sovity.edc.ext.wrapper.api.ui.model.AssetPage;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractAgreementPage;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractAgreementTransferRequest;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractDefinitionPage;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractDefinitionRequest;
import de.sovity.edc.ext.wrapper.api.ui.model.ContractNegotiationRequest;
import de.sovity.edc.ext.wrapper.api.ui.model.DashboardPage;
import de.sovity.edc.ext.wrapper.api.ui.model.IdResponseDto;
import de.sovity.edc.ext.wrapper.api.ui.model.PolicyDefinitionPage;
import de.sovity.edc.ext.wrapper.api.ui.model.TransferHistoryPage;
import de.sovity.edc.ext.wrapper.api.ui.model.UiContractNegotiation;
import de.sovity.edc.ext.wrapper.api.ui.model.UiDataOffer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("wrapper/ui")
@Tag(name = "UI", description = "EDC UI API Endpoints")
interface UiResource {

    @GET
    @Path("pages/contract-agreement-page")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Collect all data for Contract Agreement Page")
    ContractAgreementPage getContractAgreementPage();

    @POST
    @Path("pages/contract-agreement-page/transfers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Initiate a Transfer Process")
    IdResponseDto initiateTransfer(ContractAgreementTransferRequest contractAgreementTransferRequest);

    @GET
    @Path("pages/transfer-history-page")
    @Produces(MediaType.APPLICATION_JSON)
    TransferHistoryPage getTransferHistoryPage();

    @GET
    @Path("pages/transfer-history-page/transfer-processes/{transferProcessId}/asset")
    @Produces(MediaType.APPLICATION_JSON)
    UiAsset getTransferProcessAsset(@PathParam("transferProcessId") String transferProcessId);

    @GET
    @Path("pages/asset-page")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Collect all data for Asset Page")
    AssetPage getAssetPage();

    @POST
    @Path("pages/asset-page/assets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Create a new Asset")
    IdResponseDto createAsset(UiAssetCreateRequest uiAssetCreateRequest);

    @DELETE
    @Path("pages/asset-page/assets/{assetId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Delete an Asset")
    IdResponseDto deleteAsset(@PathParam("assetId") String assetId);

    @GET
    @Path("pages/catalog-page/data-offers")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Fetch a connector's data offers")
    List<UiDataOffer> getCatalogPageDataOffers(@QueryParam("connectorEndpoint") String connectorEndpoint);

    @GET
    @Path("pages/contract-definition-page")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Collect all data for Contract Definition Page")
    ContractDefinitionPage getContractDefinitionPage();

    @POST
    @Path("pages/contract-definition-page/contract-definitions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Create a new Contract Definition")
    IdResponseDto createContractDefinition(ContractDefinitionRequest contractDefinitionRequest);

    @DELETE
    @Path("pages/contract-definition-page/contract-definitions/{contractDefinitionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Delete a Contract Definition")
    IdResponseDto deleteContractDefinition(@PathParam("contractDefinitionId") String contractDefinitionId);

    @GET
    @Path("pages/policy-page")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Collect all data for Policy Definition Page")
    PolicyDefinitionPage getPolicyDefinitionPage();

    @POST
    @Path("pages/policy-page/policy-definitions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Create a new Policy Definition")
    IdResponseDto createPolicyDefinition(PolicyDefinitionCreateRequest policyDefinitionDtoDto);

    @DELETE
    @Path("pages/policy-page/policy-definitions/{policyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Delete a Policy Definition")
    IdResponseDto deletePolicyDefinition(@PathParam("policyId") String policyId);

    @POST
    @Path("pages/catalog-page/contract-negotiations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Initiate a new Contract Negotiation")
    UiContractNegotiation initiateContractNegotiation(ContractNegotiationRequest contractNegotiationRequest);

    @GET
    @Path("pages/catalog-page/contract-negotiations/{contractNegotiationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Get Contract Negotiation Information")
    UiContractNegotiation getContractNegotiation(@PathParam("contractNegotiationId") String contractNegotiationId);

    @GET
    @Path("pages/dashboard-page")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Collect all data for the Dashboard Page")
    DashboardPage getDashboardPage();
}