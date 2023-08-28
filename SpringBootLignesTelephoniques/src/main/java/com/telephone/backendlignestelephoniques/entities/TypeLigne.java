package com.telephone.backendlignestelephoniques.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeLigne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;

    @Column(unique = true, nullable = false)
    private String nomType;

    private String descriptionType;

    @CreatedDate
    private Date createdDate;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Attribut> attributs = new HashSet<>();

}
