package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import lombok.Cleanup;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {

    @Autowired
    public GridFsTemplate gridFsTemplate;

    @Autowired
    public GridFSBucket gridFSBucket;

    @Test
    public void testStore() throws IOException {
        @Cleanup FileInputStream fileInputStream = FileUtils.openInputStream(new File("/work/linsong.txt"));
        gridFsTemplate.store(fileInputStream, "test.txt");
    }

    @Test
    public void testDownload() throws IOException {
        //fs.files中文件对应的id
        ObjectId objectId = new ObjectId("5c63cb788ca277bc00d5a5f9");
        @Cleanup GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(objectId);
        FileUtils.copyInputStreamToFile(gridFSDownloadStream, new File("/work/text.txt"));
    }

    @Test
    public void testDelete(){//fs.files中文件对应的id
        ObjectId objectId = new ObjectId("5c63cb788ca277bc00d5a5f9");
        gridFSBucket.delete(objectId);

    }
}
