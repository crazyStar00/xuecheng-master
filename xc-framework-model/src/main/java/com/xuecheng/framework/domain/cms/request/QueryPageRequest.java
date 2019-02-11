package com.xuecheng.framework.domain.cms.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * 接受页面查询的条件
 *
 * @author star
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryPageRequest {

    /**
     * 站点id
     */
    String siteId;
    /**
     * 页面id
     */
    String pageId;
    /**
     * 页面名称
     */
    String pageName;
    /**
     * 别名
     */
    String pageAliase;
    /**
     * 模板id
     */
    String temolateId;
    /**
     * 页面类型
     */
    String pageType;
}
