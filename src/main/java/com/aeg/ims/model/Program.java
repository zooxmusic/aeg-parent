package com.aeg.ims.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PROGRAMS")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Program extends LayerSupertype {

    @Id
    @Column(name = "PRG_ID")
    private Long id;

    @Column(name = "PRG_NAME")
    private String name;


}
