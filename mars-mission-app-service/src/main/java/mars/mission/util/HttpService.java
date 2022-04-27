package mars.mission.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mars.mission.CandidateResponse;
import mars.mission.models.Candidate;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
public class HttpService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configOverride(FileDescriptor.class).setIsIgnoredType(true);
    }

    @SneakyThrows
    public HttpService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(60 * 1000L))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public CandidateResponse getEntity(String url, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url));
        HttpRequest request = setHeaders(headers, builder).build();
        HttpResponse<String> response = send(request, HttpResponse.BodyHandlers.ofString());
        return buildResponseEntity(response);
    }

    public CandidateResponse postEntity(String url, Map<String, String> headers, Object requestBody) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(convertRequest(requestBody)))
                .uri(URI.create(url));
        HttpRequest request = setHeaders(headers, builder).build();
        HttpResponse<String> response = send(request, HttpResponse.BodyHandlers.ofString());
        return buildResponseEntity(response);
    }

    public Candidate postEntityCandidate(String url, Map<String, String> headers, Object requestBody) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(convertRequest(requestBody)))
                .uri(URI.create(url));
        HttpRequest request = setHeaders(headers, builder).build();
        HttpResponse<String> response = send(request, HttpResponse.BodyHandlers.ofString());
        return buildResponseEntityCandidate(response);
    }

    private String convertRequest(Object requestBody) {
        try {
            if (Objects.nonNull(requestBody)) {
                return objectMapper.writeValueAsString(requestBody);
            }
        } catch (JsonProcessingException e) {
            return "{}";
        }
        return "{}";
    }

    private Candidate buildResponseEntityCandidate(HttpResponse<String> response) {
        Candidate entity = new Candidate();
        try {
            if(response.body() != null && !response.body().isEmpty()) {
                entity = convertJsonToObject(response.body(),Candidate.class);
            }
        } catch (Exception e) {
            return entity;
        }
        return entity;
    }

    private CandidateResponse buildResponseEntity(HttpResponse<String> response) {
        CandidateResponse entity = new CandidateResponse();
        try {
            if(response.body() != null && !response.body().isEmpty()) {
                entity = convertJsonToObject(response.body(), CandidateResponse.class);
            }
        } catch (Exception e) {
            return entity;
        }
        return entity;
    }

    private HttpRequest.Builder setHeaders(Map<String, String> headers, HttpRequest.Builder builder) {
        if (Objects.nonNull(headers) && headers.size() > 0 && Objects.nonNull(builder)) {
            headers.entrySet()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(set -> Objects.nonNull(set.getKey()))
                    .forEach(set -> builder.setHeader(set.getKey(), set.getValue()));
        }
        return builder;
    }

    private <Y> HttpResponse<Y> send(HttpRequest httpRequest, HttpResponse.BodyHandler<Y> bodyHandler) {
        try {
            Map<String, List<String>> headers = httpRequest.headers().map().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return httpClient.send(httpRequest, bodyHandler);
        } catch (IOException | InterruptedException e) {
            log.debug(ExceptionUtils.getMessage(e));
            return null;
        }
    }

    private <T> List<T> readListValue(String responseBody, Class<T> responseType) {
        List<T> entityList = new ArrayList<>();
        try {
            entityList = isNotBlank(responseBody) ?
                    objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, responseType))
                    : Collections.emptyList();
        } catch (JsonProcessingException e) {
            return entityList;
        }
        return entityList;
    }

    private <T> T convertJsonToObject(String responseBody, Class<T> responseType) {
        try {
            return isNotBlank(responseBody) ?
                    objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructType(responseType))
                    : responseType.getDeclaredConstructor().newInstance();
        } catch (JsonProcessingException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
