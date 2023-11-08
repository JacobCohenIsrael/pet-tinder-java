package com.gamefather.pettinder.vendors.petfinder;

import com.gamefather.pettinder.vendors.petfinder.models.AnimalModel;
import com.gamefather.pettinder.vendors.petfinder.models.GetAnimalsResponse;
import com.gamefather.pettinder.vendors.petfinder.models.PetfinderConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
@Slf4j
public class PetfinderService {

    private final RestTemplate restTemplate;

    public PetfinderService() {
        this.restTemplate = new RestTemplate();
    }
    @Value("${petfinder.baseUrl}")
    private String baseUrl;

    @Value("${petfinder.key}")

    private String key;

    @Value("${petfinder.secret}")

    private String secret;

    public AnimalModel[] GetAnimals(String petType, String petGender, int limit) throws Exception {
        String url = baseUrl + "animals";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.GetConnector().access_token);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("type", petType);
        queryParams.add("gender", petGender);
        queryParams.add("limit", String.valueOf(limit));

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(queryParams);

        String finalUrl = builder.toUriString();
        ResponseEntity<GetAnimalsResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, GetAnimalsResponse.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody()).animals;
        } else {
            throw new Exception("Failed to fetch animals. Status code: " + response.getStatusCode());
        }
    }

    public AnimalModel GetPetById(String petId) throws Exception {
        String url = baseUrl + "animals/" + petId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.GetConnector().access_token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<AnimalModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, AnimalModel.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("Failed to retrieve pet information. Status code: " + response.getStatusCode());
        }
    }

    private PetfinderConnector GetConnector() throws Exception {
        String url = baseUrl + "oauth2/token";

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", key);
        requestBody.add("client_secret", secret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);


        try {
            ResponseEntity<PetfinderConnector> response = restTemplate.postForEntity(url, request, PetfinderConnector.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody(); // Return the access token
            } else {
                throw new Exception("Failed to obtain access token. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("Failed to obtain access token: " + e.getMessage());
        }
    }
}
