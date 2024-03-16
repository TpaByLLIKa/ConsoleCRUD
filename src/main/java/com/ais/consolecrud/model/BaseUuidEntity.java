package com.ais.consolecrud.model;

import com.ais.consolecrud.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseUuidEntity extends AbstractPersistable<UUID> {

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    public abstract String getInfo();
}
