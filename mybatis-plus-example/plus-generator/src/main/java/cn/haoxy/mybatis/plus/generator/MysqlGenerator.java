package cn.haoxy.mybatis.plus.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MysqlGenerator {

    /**
     * 1. JDBC 相关配置
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mybatis-plus?useUnicode=true&characterEncoding=utf8";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "yong1014";

    /**
     * 2. 需要生成的表名
     */
    private static final String[] TABLE_NAME = {"sys_user", "sys_role", "sys_permission", "sys_role_permission", "sys_email", "sys_operation_log", "sys_login_log"};

    /**
     * 3. 包名
     */
    private static final String PACKAGE_NAME = "cn.haoxy.mybatis.plus.generator";
    private static final String MAPPER_NAME = "mapper";

    /**
     * 4. 文件生成目录
     */
    private static final String JAVA_HOME = "/Users/haoxiaoyong/development/mybatis-plus";
    private static final String RESOURCES_HOME = "/Users/haoxiaoyong/development/mybatis-plus/resources/mapper/";

    /**
     * 5. 开发人员
     */
    private static final String AUTHOR = "haoxy";

    /**
     * 6. 代码生成
     */
    public static void main(String[] args) {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill createField = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateField = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        tableFillList.add(createField);
        tableFillList.add(updateField);

        // 自定义需要逻辑删除的字段
        String logicDeleteFieldName = "del_flag";

        AutoGenerator mpg = new AutoGenerator()
                .setGlobalConfig(
                        // 全局配置
                        new GlobalConfig()
                                .setActiveRecord(false) // 需要ActiveRecord特性 改为true
                                .setAuthor(AUTHOR) // 开发人员
                                .setOutputDir(JAVA_HOME) // 文件生成的目录
                                .setFileOverride(true) // 是否覆盖文件
                                .setEnableCache(false) // XML 二级缓存
                                .setBaseColumnList(true) // XML ColumnList
                                .setBaseResultMap(true) // XML ResultMap
                                .setServiceName("%sService") // 自定义文件名 %s 会自动填充表名
                ).setDataSource(
                        // 数据源配置
                        new DataSourceConfig()
                                .setDbType(DbType.MYSQL) // 数据库类型
                                .setUrl(URL)
                                .setDriverName(DRIVER)
                                .setUsername(USER_NAME)
                                .setPassword(PASSWORD)
                ).setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                .setCapitalMode(true) // 全局大写命名
                                .setEntityLombokModel(true) // 简化代码
                                .setNaming(NamingStrategy.underline_to_camel) // 表名生成策略
                                .setInclude(TABLE_NAME) // 表名
                                .setTableFillList(tableFillList) // 自动填充字段
                                .setLogicDeleteFieldName(logicDeleteFieldName) // 逻辑删除字段
                ).setPackageInfo(
                        // 包设置
                        new PackageConfig()
                                .setParent(PACKAGE_NAME) // 自定义包路径
                                .setMapper(MAPPER_NAME) // 自定义数据层包名
                ).setCfg(
                        // 注入自定义配置
                        new InjectionConfig() {
                            @Override
                            public void initMap() {

                            }
                        }.setFileOutConfigList(
                                Collections.singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                                    @Override
                                    public String outputFile(TableInfo tableInfo) {
                                        return RESOURCES_HOME + tableInfo.getEntityName() + "Mapper.xml";
                                    }
                                }))
                ).setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig().setXml(null)
                );

        // 执行生成
        mpg.execute();
    }
}
