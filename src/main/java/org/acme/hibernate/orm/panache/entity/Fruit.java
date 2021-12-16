package org.acme.hibernate.orm.panache.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @Autor Jairo Nascimento
 * @Created 16/12/2021 - 08:39
 */
@Entity
@Cacheable
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;
}
