package com.study.server.validator.examples;


import lombok.Getter;
import lombok.Setter;

/**
 * @author jingfeng.yan
 */
@Getter
@Setter
public class Customer {

    private String firstName;

    private String lastName;

    private Address address;
}
