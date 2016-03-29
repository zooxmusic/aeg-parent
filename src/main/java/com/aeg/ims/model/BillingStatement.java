package com.aeg.ims.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "BILLABLE_STMT")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@GenericGenerator(name = "billingStatementGenerator", strategy = "com.aeg.ims.data.BillingStatementIdGenerator")
public final class BillingStatement extends LayerSupertype {

    @Id
    @GeneratedValue(generator = "billingStatementGenerator")
    @Column(name = "BILL_STMT_ID")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRG_ID", referencedColumnName = "PRG_ID")
    private Program program;

    //private String type;
    //private FundingSource fundingSource;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROGRAM_MANAGER_ID", referencedColumnName = "PROGRAM_MANAGER_ID")
    private ProgramManager programManager;

    @Temporal(TemporalType.DATE)
    @Column(name = "INV_ST_DT")
    private Date startsOn;

    @Temporal(TemporalType.DATE)
    @Column(name = "INV_END_DT")
    private Date endOn;

    @Column(name = "AMOUNT")
    private Double amount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billingStatement")
    private Set<BillingStatementItem> details;

}
