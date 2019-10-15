package com.java.xdd.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xdd
 * @date 2019/10/15
 */
@Service
public class TestService {

    private Logger logger = LoggerFactory.getLogger(TestService.class);

    public void test() {
        logger.info(this.getClass().getName());
    }
}
