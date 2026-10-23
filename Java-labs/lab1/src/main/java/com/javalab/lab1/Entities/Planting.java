package com.javalab.lab1.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "plantings")
@Getter 
@Setter 
@NoArgsConstructor 
public class Planting 
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer plantnum;
    @Column(nullable = false)
    private String status;
    @ManyToOne
    @JoinColumn(name = "gardenbed", nullable = false)
    private Gardenbed gardenbed;
    @ManyToOne
    @JoinColumn(name = "crop", nullable = false)
    private Crop crop;

}

