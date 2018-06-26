/*
 * Copyright (c) 2018, 2025, Yue Chang and/or its affiliates. All rights reserved.
 * Yue Chang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.yc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yue Chang
 * @version 1.0
 * @className: MyController
 * @description: 控制器类
 * @date 2018年06月26日 15:16
 */
@RestController
public class MyController {


    @RequestMapping(name = "/helloWorld")
    @ResponseBody
    public String helloWorld() {

        return "helloWorld ！~";
    }

}
