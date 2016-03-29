package com.aeg.ims.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PROGRAM_MANAGERS")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class ProgramManager extends LayerSupertype {
    @Id
    @Column(name = "PROGRAM_MANAGER_ID")
    private Long id;

    private String name;
}
