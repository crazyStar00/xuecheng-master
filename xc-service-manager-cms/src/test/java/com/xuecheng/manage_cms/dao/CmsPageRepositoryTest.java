package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void testFindpage() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageRequest);
        System.out.println(cmsPages);
    }

    @Test
    public void testUpdate() {
        Optional<CmsPage> cmsPageOptional = cmsPageRepository.findById("5a795ac7dd573c04508f3a56");
        if (cmsPageOptional.isPresent()) {
            CmsPage cmsPage = cmsPageOptional.get();
            cmsPage.setPageAliase("轮播图2");
            CmsPage cmsPage1 = cmsPageRepository.save(cmsPage);
            System.out.println(cmsPage1);
        }
    }

    @Test
    public void testFindByPageName() {
        CmsPage cmsPage = cmsPageRepository.findByPageName("index.html");
        System.out.println(cmsPage);
    }
}