package com.study.server.validator;

import com.study.server.validator.examples.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/**
 * @author jingfeng.yan
 */
@Slf4j
public class ValidationReference {

    public static void main(String[] args) {
        beanWrapper();
    }


    public static void beanWrapper() {
        Customer customer = new Customer();
        log.info("1:" + customer.getFirstName());
        BeanWrapper beanWrapper = new BeanWrapperImpl(customer);
        // setting the company name..
        beanWrapper.setPropertyValue("firstName", "jeff 2017");
        log.info("2:" + customer.getFirstName());
        // ... can also be done like this:
        PropertyValue value = new PropertyValue("firstName", "jeff 2019");
        beanWrapper.setPropertyValue(value);
        log.info("3:" + customer.getFirstName());
    }

}
