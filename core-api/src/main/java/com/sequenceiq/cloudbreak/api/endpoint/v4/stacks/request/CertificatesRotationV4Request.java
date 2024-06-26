package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request;

import java.util.Objects;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.CertificateRotationType;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.common.model.JsonEntity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificatesRotationV4Request implements JsonEntity {
    @Schema(description = ClusterModelDescription.CERTIFICATE_ROTATION_TYPE, required = true)
    private CertificateRotationType certificateRotationType = CertificateRotationType.HOST_CERTS;

    @Schema(description = ClusterModelDescription.FLAG_SKIP_SALT_UPDATE)
    private Boolean skipSaltUpdate = Boolean.FALSE;

    public CertificateRotationType getCertificateRotationType() {
        return certificateRotationType;
    }

    public void setCertificateRotationType(CertificateRotationType certificateRotationType) {
        this.certificateRotationType = certificateRotationType;
    }

    public Boolean getSkipSaltUpdate() {
        return skipSaltUpdate;
    }

    public void setSkipSaltUpdate(Boolean skipSaltUpdate) {
        this.skipSaltUpdate = skipSaltUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CertificatesRotationV4Request that = (CertificatesRotationV4Request) o;
        return certificateRotationType == that.certificateRotationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateRotationType);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CertificatesRotationV4Request.class.getSimpleName() + "[", "]")
                .add("certificateRotationType=" + certificateRotationType)
                .toString();
    }
}
