package com.sd.onlinebankingsystemassignment.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mas_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;

    public Permission(Long id) {
        this.id = id;
    }
}
