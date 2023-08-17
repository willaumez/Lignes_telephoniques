package com.telephone.backendlignestelephoniques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDirection;
    @Column(unique = true)
    private String nomDirection;

    @CreatedDate
    private Date createdDate;

    @OneToMany(fetch = FetchType.LAZY)
    private List<LigneTelephonique> lignesTelephoniques;
}
