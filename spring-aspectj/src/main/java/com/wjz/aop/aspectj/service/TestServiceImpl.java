/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wjz.aop.aspectj.service;

import com.wjz.aop.aspectj.core.BlockException;
import com.wjz.aop.aspectj.core.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @author Eric Zhao
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        System.out.println("Test");
    }

    @Override
    @SentinelResource(value = "hello", blockHandler = "helloBlockHandler")
    public String hello(long s) {
        if (s < 0) {
            throw new BlockException("invalid arg");
        }
        return String.format("Hello at %d", s);
    }

    @Override
    @SentinelResource(value = "helloAnother", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public String helloAnother(String name) {
        if (name == null || "bad".equals(name)) {
            throw new BlockException("oops");
        }
        return "Hello, " + name;
    }

    public String helloBlockHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }
}
