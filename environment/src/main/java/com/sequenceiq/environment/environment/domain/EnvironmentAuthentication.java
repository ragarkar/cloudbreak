package com.sequenceiq.environment.environment.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "environment_authentication")
public class EnvironmentAuthentication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "environment_authentication_generator")
    @SequenceGenerator(name = "environment_authentication_generator", sequenceName = "environment_authentication_id_seq", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String publicKey;

    private String publicKeyId;

    private String loginUserName;

    private boolean managedKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKeyId() {
        return publicKeyId;
    }

    public void setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public boolean isManagedKey() {
        return managedKey;
    }

    public void setManagedKey(boolean managedKey) {
        this.managedKey = managedKey;
    }
}
