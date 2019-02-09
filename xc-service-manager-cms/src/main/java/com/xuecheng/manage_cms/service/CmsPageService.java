package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CmsPageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    public CmsPage findByPageName(String pageName) {
        return cmsPageRepository.findByPageName(pageName);
    }

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, size <= 0 ? 10 : size);
        CmsPage cmsPage = new CmsPage();

        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getTemolateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemolateId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }

        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsPage> example = Example.of(cmsPage, matching);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageRequest);
        QueryResult<CmsPage> pageRequestQueryResult = new QueryResult<>();
        pageRequestQueryResult.setList(cmsPages.getContent());
        pageRequestQueryResult.setTotal(cmsPages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, pageRequestQueryResult);
    }
}
