package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.core.gateways.outbound.DerogatoryConditionExportGateway;
import com.socgen.unibank.services.core.model.DerogatoryConditionRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DerogatoryConditionExportGatewayImpl implements DerogatoryConditionExportGateway {

    @Override
    public String exportRequests(List<DerogatoryConditionRequest> requests) {
        String header = "requestId,customerReference,requestType,status,currentActor,createdAt,updatedAt,message";
        String body = requests.stream()
            .map(request -> String.join(",",
                csv(request.getRequestId()),
                csv(request.getCustomerReference()),
                csv(request.getRequestType()),
                csv(request.getStatus() == null ? null : request.getStatus().name()),
                csv(request.getCurrentActor()),
                csv(request.getCreatedAt() == null ? null : DateTimeFormatter.ISO_INSTANT.format(request.getCreatedAt())),
                csv(request.getUpdatedAt() == null ? null : DateTimeFormatter.ISO_INSTANT.format(request.getUpdatedAt())),
                csv(request.getMessage())))
            .collect(Collectors.joining("\n"));
        return body.isBlank() ? header : header + "\n" + body;
    }

    private String csv(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        return '"' + escaped + '"';
    }
}
