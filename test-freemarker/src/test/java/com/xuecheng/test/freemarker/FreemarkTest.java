package com.xuecheng.test.freemarker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkTest {

    @Autowired
    RestTemplate restTemplate;

    //基于ftl文件生成html文件
    @Test
    public void generatorHtmlByFtl() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        String path = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(path + "/templates/"));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //加载模块
        Template template = configuration.getTemplate("test1.ftl");
        //数据模块
        Map map = new HashMap();
        map.put("name", "star");
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(content);

        //输出文件
        FileUtils.writeByteArrayToFile(new File("/work/test1.html"), content.getBytes(StandardCharsets.UTF_8));

    }

    //基于ftl文件生成html文件
    @Test
    public void generatorHtmlByString() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        //String path = this.getClass().getResource("/").getPath();
        //configuration.setDirectoryForTemplateLoading(new File(path + "/templates/"));
        ////设置字符集
        //configuration.setDefaultEncoding("utf-8");
        String templateString = "" +
                "<html>\n" +
                "    <head></head>\n" +
                " <body>\n" +
                " 名称:${name}\n" +
                "    </body>\n" +
                "</html>";
        //加载模块
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        //得到模板
        Template template = configuration.getTemplate("template", "UTF-8");

        //数据模块
        Map map = new HashMap();
        map.put("name", "star");
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(content);

        //输出文件
        FileUtils.writeByteArrayToFile(new File("/work/test2.html"), content.getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void banber() throws IOException, TemplateException {
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/5a791725dd573c3574ee333f", Map.class);
        System.out.println(forEntity);

        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        String path = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(path + "/templates/"));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //加载模块
        Template template = configuration.getTemplate("index-banner.ftl");
        //数据模块
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, forEntity.getBody());
        System.out.println(content);

        //输出文件
        FileUtils.writeByteArrayToFile(new File("/work/index_banner.html"), content.getBytes(StandardCharsets.UTF_8));

    }

}
