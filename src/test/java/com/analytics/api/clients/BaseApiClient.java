package com.analytics.api.clients;

import com.analytics.core.ApiContextManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;

public abstract class BaseApiClient {

  protected final ObjectMapper objectMapper = new ObjectMapper();

  protected APIResponse get(String endpoint, String queryParams) {
    String url = queryParams != null && !queryParams.isEmpty()
        ? endpoint + "?" + queryParams
        : endpoint;
    return ApiContextManager.getApiContext().get(url);
  }

  protected <T> T parseResponse(APIResponse response, Class<T> clazz) {
    try {
      return objectMapper.readValue(response.text(), clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to parse response to " + clazz.getName(), e);
    }
  }
}