package com.xuecheng.manage_cms.service;

import com.google.common.base.Preconditions;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Cleanup;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    CmsPageService cmsPageService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    public GridFSBucket gridFSBucket;

    @Autowired
    public GridFsTemplate gridFsTemplate;

    @Autowired
    public Configuration configuration;

    /**
     * 获取页面的html
     *
     * @param pageId
     *
     * @return
     */
    public String getPageHtml(String pageId) {
        String template = getTemplateByPageId(pageId);
        Map model = getModelByPageId(pageId);
        return generatorHtmlByFtl(template, model);
    }

    /**
     * 根据freemarker的ftl模板和模型数据，生成静态化的文件的字符串
     *
     * @param template freemarker的ftl模板
     * @param model 模型数据
     *
     * @return 静态化的文件的字符串
     */
    public String generatorHtmlByFtl(String template, Map model) {
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("轮播图", template);
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template1;
        String content = "";
        try {
            template1 = configuration.getTemplate("轮播图");
            content = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 根据页面id获取模板文件字符串
     *
     * @param pageId
     *
     * @return
     */
    public String getTemplateByPageId(String pageId) {
        Optional<CmsPage> cmsPageOptional = cmsPageService.get(pageId);
        Preconditions.checkArgument(cmsPageOptional.isPresent(), "未找到id为：" + pageId + "对应的页面信息");
        CmsPage cmsPage = cmsPageOptional.get();
        String templateId = cmsPage.getTemplateId();
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        Preconditions.checkArgument(optional.isPresent(), "未找到id为：" + "5a795ac7dd573c04508f3a56" + "对应的模板信息");
        CmsTemplate cmsTemplate = optional.get();
        String templateFileId = cmsTemplate.getTemplateFileId();
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        ObjectId objectId = gridFSFile.getObjectId();
        @Cleanup GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(objectId);
        String content = "";
        try {
            content = IOUtils.toString(gridFSDownloadStream, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public Map getModelByPageId(String pageId) {
        Optional<CmsPage> cmsPageOptional = cmsPageService.get(pageId);
        Preconditions.checkArgument(cmsPageOptional.isPresent(), "未找到id为：" + pageId + "对应的页面信息");
        String dataUrl = cmsPageOptional.get().getDataUrl();
        Preconditions.checkArgument(StringUtils.isNotEmpty(dataUrl), "id为：" + pageId + "的页面为配置dataUrl");
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return responseEntity.getBody();
    }
}
