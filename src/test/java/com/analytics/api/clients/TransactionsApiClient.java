package com.analytics.api.clients;

import com.analytics.api.enums.ApiEndpoints;
import com.analytics.api.models.TransactionResponse;
import com.microsoft.playwright.APIResponse;

public class TransactionsApiClient extends BaseApiClient {

  public APIResponse getTransactionsRaw(String queryParams) {
    return get(ApiEndpoints.TRANSACTIONS.getPath(), queryParams);
  }

  public TransactionResponse getTransactions(String queryParams) {
    APIResponse response = getTransactionsRaw(queryParams);
    return parseResponse(response, TransactionResponse.class);
  }
}