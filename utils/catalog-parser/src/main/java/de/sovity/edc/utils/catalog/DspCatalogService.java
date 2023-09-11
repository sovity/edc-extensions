package de.sovity.edc.utils.catalog;

import de.sovity.edc.utils.catalog.model.DataOffer;
import de.sovity.edc.utils.catalog.utils.JsonUtils;
import jakarta.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.edc.connector.spi.catalog.CatalogService;
import org.eclipse.edc.spi.query.QuerySpec;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class DspCatalogService {
    private final CatalogService catalogService;
    private final DataOfferBuilder dataOfferBuilder;

    public List<DataOffer> fetchDataOffers(String endpoint) {
        var catalogJson = fetchDcatResponse(endpoint);
        return dataOfferBuilder.buildDataOffers(endpoint, catalogJson);
    }

    private JsonObject fetchDcatResponse(String connectorEndpoint) {
        var raw = fetchDcatRaw(connectorEndpoint);
        var string = new String(raw, StandardCharsets.UTF_8);
        return JsonUtils.parseJson(string);
    }

    @SneakyThrows
    private byte[] fetchDcatRaw(String connectorEndpoint) {
        return catalogService.requestCatalog(connectorEndpoint, "dataspace-protocol-http",
                QuerySpec.max()).get().getContent();
    }
}
