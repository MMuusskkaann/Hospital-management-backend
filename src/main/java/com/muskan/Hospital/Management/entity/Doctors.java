package com.muskan.Hospital.Management.entity;
import jakarta.persistence.*;
import lombok.*;
//import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
//import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import java.util.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @ManyToOne   // or @OneToOne (depends on your design)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments = new HashSet<>();


    //  MUST for OneToOne reverse mapping
    @OneToOne(mappedBy = "headDoctor")
    private Department headOfDepartment;

    @OneToMany(mappedBy = "doctor")  // must match Appointment.doctor
    private List<Appointment> appointments = new ArrayList<>();
}
