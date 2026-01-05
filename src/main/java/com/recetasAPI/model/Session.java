package com.recetasAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sessions")
public class Session {

    @Id
    private String id;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String ip;
    private String browser;
    private String token;
    private boolean enabled;
}
