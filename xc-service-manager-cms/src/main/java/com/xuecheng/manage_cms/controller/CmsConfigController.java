package com.xuecheng.manage_cms.controller;

import com.google.common.base.Preconditions;
import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    CmsConfigService cmsConfigService;

    @Override
    @GetMapping("/{id}")
    public CmsConfig getModel(@PathVariable("id") String id) {
        Optional<CmsConfig> optional = cmsConfigService.findById(id);
        Preconditions.checkArgument(optional.isPresent(),"id:"+id+"未找到对应的数据");
        return optional.get();
    }
}
