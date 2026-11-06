package com.javalab.lab1.Entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "harvests")
@Getter 
@Setter 
@NoArgsConstructor 
public class  Harvest
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Double mass;
    @ManyToOne
    @JoinColumn(name = "planting", nullable = false)
    private Planting planting;

}
