package com.javalab.lab1.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "gardenbeds")
@Getter 
@Setter 
@NoArgsConstructor 
public class Gardenbed 
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerID", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "gardenbed")
    private List<Planting> plantings = new ArrayList<>();

    @Column(nullable = false)
    private Boolean available = true;
}
