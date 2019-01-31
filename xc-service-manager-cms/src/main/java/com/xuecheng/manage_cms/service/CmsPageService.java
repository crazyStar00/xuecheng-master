package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageRequest);
        QueryResult<CmsPage> pageRequestQueryResult = new QueryResult<>();
        pageRequestQueryResult.setList(cmsPages.getContent());
        pageRequestQueryResult.setTotal(cmsPages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, pageRequestQueryResult);
    }
}
