package com.study.server.validator.examples;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jingfeng.yan
 */
@Getter
@Setter
public class Address {
    private String province;

    private String city;

    private String district;

    private String town;

    private String detail;
}
