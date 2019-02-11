package com.xuecheng.manage_cms.service;

import com.google.common.base.Preconditions;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
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
        if (StringUtils.isNotEmpty(queryPageRequest.getPageType())) {
            cmsPage.setPageType(queryPageRequest.getPageType());
        }
        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsPage> example = Example.of(cmsPage, matching);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageRequest);
        QueryResult<CmsPage> pageRequestQueryResult = new QueryResult<>();
        pageRequestQueryResult.setList(cmsPages.getContent());
        pageRequestQueryResult.setTotal(cmsPages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, pageRequestQueryResult);
    }

    public CmsPage add(CmsPage cmsPage) {
        Preconditions.checkArgument(cmsPage != null
                        && StringUtils.isNoneBlank(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath())
                        && cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
                cmsPage.getSiteId(), cmsPage.getPageWebPath()) == null,
                "参数异常");

        return cmsPageRepository.save(cmsPage);
    }

    public Optional<CmsPage> eidt(String id, CmsPage cmsPage) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage1 = optional.get();
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
            cmsPage1.setSiteId(cmsPage.getSiteId());
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            cmsPage1.setPageName(cmsPage.getPageName());
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            cmsPage1.setPageType(cmsPage.getPageType());
            cmsPage1.setDataUrl(cmsPage.getDataUrl());
            CmsPage save = cmsPageRepository.save(cmsPage1);
            log.info("更新{} ：{}", save != null ? "成功" : "失败", id);
            return Optional.of(save);
        }

        return optional;
    }

    public Optional<CmsPage> get(String id) {
        return cmsPageRepository.findById(id);
    }

    public void deleteById(String id) {
        cmsPageRepository.deleteById(id);
    }
}
