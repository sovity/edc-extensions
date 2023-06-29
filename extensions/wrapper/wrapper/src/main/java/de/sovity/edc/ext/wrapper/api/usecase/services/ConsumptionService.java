package de.sovity.edc.ext.wrapper.api.usecase.services;

import de.sovity.edc.ext.wrapper.api.usecase.model.ConsumeDto;
import de.sovity.edc.ext.wrapper.api.usecase.model.ConsumeInputDto;
import de.sovity.edc.ext.wrapper.api.usecase.model.ConsumeOutputDto;
import lombok.RequiredArgsConstructor;
import org.eclipse.edc.connector.contract.spi.negotiation.store.ContractNegotiationStore;
import org.eclipse.edc.connector.contract.spi.types.negotiation.ContractNegotiation;
import org.eclipse.edc.connector.contract.spi.types.negotiation.ContractRequest;
import org.eclipse.edc.connector.contract.spi.types.negotiation.ContractRequestData;
import org.eclipse.edc.connector.contract.spi.types.offer.ContractOffer;
import org.eclipse.edc.connector.spi.contractnegotiation.ContractNegotiationService;
import org.eclipse.edc.connector.spi.transferprocess.TransferProcessService;
import org.eclipse.edc.connector.transfer.spi.store.TransferProcessStore;
import org.eclipse.edc.connector.transfer.spi.types.DataRequest;
import org.eclipse.edc.connector.transfer.spi.types.TransferRequest;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ConsumptionService {

    private final Map<String, ConsumeDto> consume = new HashMap<>(); //TODO persist?

    private final ContractNegotiationService contractNegotiationService;
    private final TransferProcessService transferProcessService;
    private final ContractNegotiationStore contractNegotiationStore;
    private final TransferProcessStore transferProcessStore;

    private final String connectorId;

    public String startConsume(ConsumeInputDto consumeInputDto) {
        //TODO generate ID
        var id = "id";

        var consumeDto = new ConsumeDto(consumeInputDto);
        consume.put(id, consumeDto);

        //TODO transformer
        var contractOffer = ContractOffer.Builder.newInstance()
                .id(consumeInputDto.getOfferId())
                .assetId(consumeInputDto.getAsset().getId())
                .policy(consumeInputDto.getPolicy())
                .providerId("urn:connector:" + consumeInputDto.getConnectorId())
                .build();

        var contractRequestData = ContractRequestData.Builder.newInstance()
                .contractOffer(contractOffer)
                .dataSet(consumeInputDto.getAsset().getId())
                .protocol("dataspace-protocol-http")
                .counterPartyAddress(consumeInputDto.getConnectorAddress())
                .build();

        var contractOfferRequest = ContractRequest.Builder.newInstance()
                .requestData(contractRequestData)
                .build();

        var contractNegotiation = contractNegotiationService.initiateNegotiation(contractOfferRequest);
        consumeDto.setContractNegotiationId(contractNegotiation.getId());

        return id;
    }

    //TODO only specific negotiations (by id)
    public void negotiationConfirmed(ContractNegotiation contractNegotiation) {
        var process = findByNegotiation(contractNegotiation);

        if (process != null) {
            var agreementId = contractNegotiation.getContractAgreement().getId();

            //TODO transformer
            var dataRequest = DataRequest.Builder.newInstance()
                    .connectorId(process.getInput().getConnectorId())
                    .connectorAddress(process.getInput().getConnectorAddress())
                    .protocol("ids-multipart")
                    .dataDestination(process.getInput().getDataDestination())
                    .assetId(process.getInput().getAsset().getId())
                    .contractId(agreementId)
                    .build();

            var transferRequest = TransferRequest.Builder.newInstance()
                    .dataRequest(dataRequest)
                    .build();

            var result = transferProcessService.initiateTransfer(transferRequest);
            if (result.failed()) {
                process.getErrors().add(result.getFailureDetail());
            }
        }
    }

    public ConsumeOutputDto getConsume(String id) {
        var process = consume.get(id);
        if (process == null) {
            return null;
        }

        var negotiation = contractNegotiationStore.findById(process.getContractNegotiationId());
        var transferProcess = transferProcessStore.findById(process.getTransferProcessId());

        //TODO transform CN & TP to dto

        return new ConsumeOutputDto(id, process.getInput(), process.getErrors(), negotiation, transferProcess);
    }

    private ConsumeDto findByNegotiation(ContractNegotiation contractNegotiation) {
        var id = contractNegotiation.getId();
        return consume.entrySet().stream()
                .filter(entry -> entry.getValue().getContractNegotiationId().equals(id))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }
}
