package com.opsec.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
@Data
@Document(collection = "User")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Transient
    public static final String SEQUENCE_NAME="user_sequence";
    @Id
    private long id;

    @NotBlank
    @Size(max=100)
    @Indexed(unique = true)
    private String firstName;
    private String surName;

    @Indexed(unique = true)
    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
    private String title;
}
