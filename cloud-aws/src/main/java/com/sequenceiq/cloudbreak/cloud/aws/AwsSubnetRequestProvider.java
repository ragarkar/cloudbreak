package com.sequenceiq.cloudbreak.cloud.aws;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.sequenceiq.cloudbreak.cloud.model.network.SubnetRequest;

@Component
public class AwsSubnetRequestProvider {

    public List<SubnetRequest> provide(AmazonEC2Client ec2Client, List<String> publicSubnetCidrs,  List<String> privateSubnetCidrs) {
        List<String> az = getAvailabilityZones(ec2Client);
        List<SubnetRequest> subnets = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < publicSubnetCidrs.size(); i++) {
            SubnetRequest subnetRequest = new SubnetRequest();
            subnetRequest.setPublicSubnetCidr(publicSubnetCidrs.get(i));
            subnetRequest.setAvailabilityZone(az.get(i % az.size()));
            subnetRequest.setSubnetGroup(i % publicSubnetCidrs.size());
            subnetRequest.setIndex(index++);
            subnets.add(subnetRequest);
        }

        for (int i = 0; i < privateSubnetCidrs.size(); i++) {
            SubnetRequest subnetRequest = new SubnetRequest();
            subnetRequest.setPrivateSubnetCidr(privateSubnetCidrs.get(i));
            subnetRequest.setAvailabilityZone(az.get(i % az.size()));
            // we will create 3 public subnet for nat gateways so we need to loadbalance between the public subnets
            subnetRequest.setSubnetGroup(i % publicSubnetCidrs.size());
            subnetRequest.setIndex(index++);
            subnets.add(subnetRequest);
        }

        return subnets;
    }

    private List<String> getAvailabilityZones(AmazonEC2Client ec2Client) {
        return ec2Client.describeAvailabilityZones()
                        .getAvailabilityZones()
                        .stream()
                        .map(AvailabilityZone::getZoneName)
                        .collect(Collectors.toList());
    }
}
