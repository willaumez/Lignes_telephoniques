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
public class NatureLigne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNature;
    @Column(unique = true)
    private String nomNature;

    @CreatedDate
    private Date createdDate;

    @OneToMany(mappedBy = "natureLigne", fetch = FetchType.LAZY)
    private List<LigneTelephonique> lignesTelephoniques;
}
