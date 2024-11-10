package com.raslen.StackOverflow.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title ;

    @Lob /*contain much data */
    @Column(name = "body", length = 512)
    private String body;

    private Date createDate;
    private List<String> tags;

    @ManyToOne(fetch =FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = false )
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;





}
