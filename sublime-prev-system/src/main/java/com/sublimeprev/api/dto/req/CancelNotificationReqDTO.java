package com.sublimeprev.api.dto.req;


import lombok.Data;

@Data
public class CancelNotificationReqDTO {

    private String title;
    private String content;
    private boolean sendNotify;
}
