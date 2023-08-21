package com.telephone.backendlignestelephoniques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeLigne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;
    @Column(unique = true)
    private String nomType;
    private String descriptionType;

    @CreatedDate
    private Date createdDate;

    @OneToMany(mappedBy = "typeLigne", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AttributLigne> attributs = new ArrayList<>();
}
