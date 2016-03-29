package com.aeg.ims.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BUDGET_GROUPS")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class BudgetGroup extends LayerSupertype {

    @Id
    @Column(name = "BUDG_GROUP_ID")
    private Long id;

    @Column(name = "")
    private String name;
}
