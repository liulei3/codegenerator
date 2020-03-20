import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DtoCodeGenerator {
    /**
     * 包名
     */
    private static final String PACKAGE_NAME = "com";
    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "demo";
    /**
     * 输出文件的路径
     */
    private static final String OUT_PATH = "E:\\code";
    /**
     * 代码生成者
     */
    private static final String AUTHOR = "liulei";

    /**
     * JDBC相关配置
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3336/demo?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<TableFill>();
        // 如 每张表都有一个创建时间、修改时间
        // 而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        // 修改时，修改时间会修改，
        // 虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        // 使用公共字段填充功能，就可以实现，自动按场景更新了。
        // 如下是配置
        TableFill createField = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill modifiedField = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        tableFillList.add(createField);
        tableFillList.add(modifiedField);

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig().setOutputDir(OUT_PATH)// 输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(true)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(false)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        .setAuthor(AUTHOR)
                        .setSwagger2(true)
                        // 自定义文件命名，注意 %s 会自动填充表实体属性！
                        .setXmlName("%sMapper").setEntityName("%sDto").setMapperName("%sDao").setDateType(DateType.ONLY_DATE).setIdType(IdType.ID_WORKER)
                // .setServiceName("MP%sService")
                // .setServiceImplName("%sServiceDiy")
                // .setControllerName("%sAction")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig().setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert()).setDriverName(DRIVER).setUsername(USER_NAME)
                        .setPassword(PASSWORD).setUrl(URL))
                .setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                // .setCapitalMode(true)// 全局大写命名
                                // .setTablePrefix(new String[]{"unionpay_"})//
                                // 此处可以修改为您的表前缀
                                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
//								.setInclude(new String[] {"us_user"}) // 需要生成的表
                                // .setExclude(new String[]{"test"}) // 排除生成的表
                                // 自定义实体，公共字段
                                .setSuperEntityColumns(new String[]{"gmt_create","gmt_modified","version"})
                                .setTableFillList(tableFillList)
                                // 自定义实体父类
                                // .setSuperEntityClass("com.baomidou.demo.base.BsBaseEntity")
                                // // 自定义 mapper 父类
                                // .setSuperMapperClass("com.baomidou.demo.base.BsBaseMapper")
                                // // 自定义 service 父类
                                // .setSuperServiceClass("com.baomidou.demo.base.BsBaseService")
                                // // 自定义 service 实现类父类
                                // .setSuperServiceImplClass("com.baomidou.demo.base.BsBaseServiceImpl")
                                // 自定义 controller 父类
                                // .setSuperControllerClass("com.baomidou.demo.TestController")
                                // 【实体】是否生成字段常量（默认 false）
                                // public static final String ID = "test_id";
                                .setEntityColumnConstant(true)
                                // 【实体】是否为构建者模型（默认 false）
                                // public User setName(String name) {this.name =
                                // name; return this;}
                                .setEntityBuilderModel(true)
                                // 【实体】是否为lombok模型（默认 false）<a
                                // href="https://projectlombok.org/">document</a>
                                .setEntityLombokModel(false)
                                .setRestControllerStyle(true)
                                .setVersionFieldName("version")
                        // Boolean类型字段是否移除is前缀处理
                        // .setEntityBooleanColumnRemoveIsPrefix(true)
                        // .setRestControllerStyle(true)
                        // .setControllerMappingHyphenStyle(true)
                )
                .setPackageInfo(
                        // 包配置
                        new PackageConfig().setModuleName(MODULE_NAME).setParent(PACKAGE_NAME)// 自定义包路径
                                //.setController("controller")// 这里是控制器包名，默认 web
                                //.setXml("mapper").setMapper("dao")
                                .setEntity("dto")

                ).setCfg(
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        new InjectionConfig() {
                            @Override
                            public void initMap() {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                                this.setMap(map);
                            }
                        })
                .setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig().setXml(null)
                                .setEntity("/templates/entityVo.java")
                                .setController(null)
                                .setService(null)
                                .setServiceImpl(null)
                                .setEntityKt(null)
                                .setMapper(null)
                        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                        // .setController("...");
                        // .setEntity("...");
                        // .setMapper("...");
                        // .setXml("...");
                        // .setService("...");
                        // .setServiceImpl("...");
                );

        // 执行生成
        mpg.execute();
    }

}