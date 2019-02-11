package com.xuecheng.manage_cms.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author star
 */
@RestController
@RequestMapping("/cms/page")
@CrossOrigin
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    CmsPageService cmsPageService;

    @Override
    @RequestMapping(value = "/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(page - 1, size, queryPageRequest);
    }

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return new CmsPageResult(CommonCode.SUCCESS, cmsPageService.add(cmsPage));
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        Optional<CmsPage> eidt = cmsPageService.eidt(id, cmsPage);
        return new CmsPageResult(eidt.isPresent() ? CommonCode.SUCCESS : CommonCode.FAIL, eidt.orElseGet(null));
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public CmsPageResult get(@PathVariable String id) {
        return new CmsPageResult(CommonCode.SUCCESS, cmsPageService.get(id).orElseGet(null));
    }

    @RequestMapping(value = "del/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") String id){
        cmsPageService.deleteById(id);
        return   ResponseResult.SUCCESS();
    }
}
