package com.aeg.ims.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class LayerSupertype implements Serializable{

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DT")
    private Date createdOn;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DT")
    private Date updatedOn;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

}
