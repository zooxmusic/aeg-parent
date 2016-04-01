package com.aeg.ims.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "BILL_STMT_DETAILS")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public final class BillingStatementItem extends LayerSupertype {

    @Id
    @Column(name = "BILL_STMT_ID")
    private String id;

    @ManyToOne
    private BillingStatement billingStatement;

    @Temporal(TemporalType.DATE)
    @Column(name = "INV_ST_DT")
    private Date invoiceStartsOn;

    @Temporal(TemporalType.DATE)
    @Column(name = "INV_END_DT")
    private Date invoiceEndOn;

    @Column(name = "AMOUNT")
    private Double amount;

}
