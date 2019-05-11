package cn.haoxy.mongodb.dao;

import cn.haoxy.mongodb.domain.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Haoxy on 2019-05-10.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    /**
     * 这种写法类似于 Spring Data JPA
     * @param pageName
     * @return
     */

    //根据页面名称查询

    CmsPage findByPageName(String pageName);

    //根据页面名称和类型查询

    CmsPage findByPageNameAndPageType(String pageName, String pageType);//一定要注意顺序

    //根据站点和页面类型查询记录数

    int countBySiteIdAndPageType(String siteId, String pageType);

    //根据站点和页面类型分页查询

    Page<CmsPage> findBySiteIdAndPageType(String siteId, String pageType, Pageable pageable);

    Page<CmsPage> findAll(Pageable pageable);
}
