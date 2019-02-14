package com.xuecheng.manage_cms.service;

import com.google.common.base.Preconditions;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Cleanup;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsPageService cmsPageService;

    @Autowired
    PageService pageService;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    public GridFsTemplate gridFsTemplate;


    @Autowired
    public GridFSBucket gridFSBucket;

    @Autowired
    public Configuration Configuration;

    public StringTemplateLoader stringTemplateLoader=new StringTemplateLoader();

    public String pageId = "5a795ac7dd573c04508f3a56";

    @Test
    public void getPageHtml() throws IOException, TemplateException {
        String pageHtml = pageService.getPageHtml(pageId);
        System.out.println(pageHtml);
    }

    @Test
    public void generatorHtmlByFtl() throws IOException, TemplateException {

        String content = pageService.generatorHtmlByFtl(pageService.getTemplateByPageId(pageId), pageService.getModelByPageId(pageId));
        System.out.println(content);
    }

    @Test
    public void getTemplateByPageId() throws IOException {
        String template = pageService.getTemplateByPageId(pageId);
        System.out.println(template);
    }

    @Test
    public void getModelByPageId() {
        Map model = pageService.getModelByPageId(pageId);
        System.out.println(model);

    }
}