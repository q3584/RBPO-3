package com.javalab.lab1.Entities;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gardenbedID", nullable = false)
    private Gardenbed gardenbed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cropID", nullable = false)
    private Crop crop;

    @OneToMany(mappedBy = "planting")
    private List<Harvest> harvests = new ArrayList<>();

}

