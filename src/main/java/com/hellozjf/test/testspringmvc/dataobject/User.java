package com.hellozjf.test.testspringmvc.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author hellozjf
 */
@Data
@Entity
public class User extends BaseEntity {
    private String username;
    private String password;
    private Integer age;
    @Lob
    @Column(length = 1 * 1024 * 1024)
    private byte[] picture;
}
