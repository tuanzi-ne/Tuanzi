package com.spring.common.utils;

import com.spring.common.exception.SysException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

import java.io.Writer;
import java.util.Map;

/**
 * FreeMarker模板工具类，配置模板路径
 *
 * @author  团子
 * @date 2018/4/12 19:05
 * @since V1.0
 */
public class FreeMarkerUtils {

    private static final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    static {
        //指定模板加载路径
        config.setTemplateLoader(new ClassTemplateLoader(FreeMarkerUtils.class, "/templates/gen/ftl"));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    public static void process(String templateName, Map<String, Object> map, Writer writer) {
        try {
            config.getTemplate(templateName).process(map, writer);
        } catch (TemplateNotFoundException e) {
            throw new SysException("模板" + templateName + "不存在!", e);
        } catch (Exception e) {
            throw new SysException("渲染模板" + templateName + "失败!", e);
        }
    }

    public static void clearCache() {
        config.clearTemplateCache();
    }
}
