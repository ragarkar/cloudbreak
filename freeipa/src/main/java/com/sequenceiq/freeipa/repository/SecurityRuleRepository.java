package com.sequenceiq.freeipa.repository;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

import org.springframework.data.repository.CrudRepository;

import com.sequenceiq.freeipa.entity.SecurityGroup;
import com.sequenceiq.freeipa.entity.SecurityRule;

@Transactional(TxType.REQUIRED)
public interface SecurityRuleRepository extends CrudRepository<SecurityRule, Long> {

    List<SecurityRule> findAllBySecurityGroup(SecurityGroup securityGroup);

}
