package com.analytics.api.models;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponse {

  private List<Transaction> data;
  private Meta meta;

  @Data
  @NoArgsConstructor
  public static class Transaction {

    private String id;
    private String date;
    private String region;
    private String category;
    private double revenue;
  }

  @Data
  @NoArgsConstructor
  public static class Meta {

    private int total;
    private int returned;
    private Map<String, String> filters;
  }
}