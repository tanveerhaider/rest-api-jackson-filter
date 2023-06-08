package com.simplejava.customer.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.simplejava.customer.dto.CustomerDto;
import com.simplejava.customer.entity.CustomerEntity;
import com.simplejava.customer.mapper.CustomerMapper;
import com.simplejava.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Description : CustomerController uses Jackson Mapping Filter to send only the requested fields on API response
 * User: Tanveer Haider
 * Date: 6/6/2023
 * Time: 7:37 PM
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("/{id}")
    public MappingJacksonValue findById(@PathVariable Long id, @RequestHeader("requested_fields") String fields) {
        log.info(" id {} and RequestFields {}", id, fields);
        String[] res = fields.split(",");
        Optional<CustomerEntity> customerById = customerService.findById(id);
        CustomerDto customerDto = CustomerMapper.MAPPER.mapToDto(customerById.get());
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerDto);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(res);
        FilterProvider filters = new SimpleFilterProvider().addFilter("customerFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping
    public MappingJacksonValue findById(@RequestParam("id") long id, @RequestParam("requested_fields") String fields) {
        log.info(" id {} and RequestFields {}", id, fields);
        String[] res = fields.split(",");
        Optional<CustomerEntity> customerById = customerService.findById(id);
        CustomerDto customerDto = CustomerMapper.MAPPER.mapToDto(customerById.get());
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerDto);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(res);
        FilterProvider filters = new SimpleFilterProvider().addFilter("customerFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    //TODO apply on getALL

}
