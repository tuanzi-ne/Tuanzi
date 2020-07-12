package com.spring.common.utils;

/**
 * 常量工具类
 *
 * @author  团子
 * @date 2018/3/23 16:39
 * @since V1.0
 */
public class Constants {
    /**
     * 超级管理员ID
     */
    public static final Integer ADMIN_ID = 1;
    /**
     * 一级资源ID
     */
    public static final Integer RES_TOP_ID = 0;
    /**
     * 一级部门ID
     */
    public static final Integer DPT_TOP_ID = 0;
    /**
     * 文件名最大长度
     */
    public static final Integer FILENAME_MAX_LEN = 50;
    /**
     * 头像默认保存路径
     */
    public static final String DEFAULT_HEAD_PATH = "/static/img/profile.jpg";
    /**
     * 用户重置默认密码
     */
    public static final String USER_RESET_PWD  = "abcd1234";

    /**
     * 资源类型
     *
     * @param
     * @return
     * @throws
     */
    public enum ResType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private final int value;

        private ResType(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时Job状态
     *
     * @author  团子
     * @date 2018/4/18 14:52
     * @since V1.0
     */
    public enum JobStatus {
        /**
         * 暂停
         */
        PAUSE(0),
        /**
         * 失败
         */
        FAILED(0),
        /**
         * 正常
         */
        NORMAL(1);

        private final int value;

        private JobStatus(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * shiro缓存类型类型
     *
     * @param
     * @return
     * @throws
     */
    public enum ShiroCacheType {
        /**
         * ehcache
         */
        EHCACHE(0),
        /**
         * redis
         */
        REDIS(1);

        private final int value;

        private ShiroCacheType(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * 皮肤主题，共三种
     *
     * @author  团子
     * @date 2018/4/18 14:52
     * @since V1.0
     */
    public enum SkinTheme {
        /**
         * 默认皮肤
         */
        DEFAULT(0, "defaultSkin"),
        /**
         * 蓝色主题
         */
        BLUE(1, "skin-1"),
        /**
         * 黄色主题
         */
        YELLOW(2, "skin-3");

        private final int key;
        private final String value;

        SkinTheme(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}
