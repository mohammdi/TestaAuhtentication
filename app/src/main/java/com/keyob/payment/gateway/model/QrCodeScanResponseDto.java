package com.keyob.payment.gateway.model;

import java.io.Serializable;

public class QrCodeScanResponseDto implements Serializable {

   private String Id;
   private String Type;
   private String TargetId;

    public QrCodeScanResponseDto() {
    }

    public QrCodeScanResponseDto(String id, String type, String targetId) {
        Id = id;
        Type = type;
        TargetId = targetId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTargetId() {
        return TargetId;
    }

    public void setTargetId(String targetId) {
        TargetId = targetId;
    }
}
