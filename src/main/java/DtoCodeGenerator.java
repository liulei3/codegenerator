import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

public class DtoCodeGenerator {

    // 需要生成的表名称,如果要生成全部表则为空字符串
    private static final String TABLE_NAME = "temp_meta";//导出表名称

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
        final ResourceBundle prop = ResourceBundle.getBundle("config");

        GlobalConfig globalConfig = new GlobalConfig().setOutputDir(prop.getString("outputDir"))// 输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(false)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setAuthor(prop.getString("author"))
                .setSwagger2(false)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setXmlName("%sMapper")
                .setEntityName("%sDto")
                .setMapperName("%sDao")
                .setDateType(DateType.ONLY_DATE)
                .setIdType(IdType.ID_WORKER);
        // .setServiceName("MP%sService")
        // .setServiceImplName("%sServiceDiy")
        // .setControllerName("%sAction")

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert());
        dataSourceConfig.setDriverName(prop.getString("driver"));
        dataSourceConfig.setUrl(prop.getString("url"));
        dataSourceConfig.setUsername(prop.getString("userName"));
        dataSourceConfig.setPassword(prop.getString("password"));

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // strategyConfig.setCapitalMode(true)// 全局大写命名
        // strategyConfig.setTablePrefix(new String[]{"unionpay_"})//
        // 此处可以修改为您的表前缀
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        if(StringUtils.isNotEmpty(TABLE_NAME)){
            strategyConfig.setInclude(new String[] {TABLE_NAME}); // 需要生成的表
        }
        // .setExclude(new String[]{"test"}) // 排除生成的表
        // 自定义实体，公共字段
        strategyConfig.setSuperEntityColumns(new String[]{"gmt_create", "gmt_modified", "version"});
        strategyConfig.setTableFillList(tableFillList);
        // 自定义实体父类
        // strategyConfig.setSuperEntityClass("com.baomidou.demo.base.BsBaseEntity");
        // // 自定义 mapper 父类
        // strategyConfig.setSuperMapperClass("com.baomidou.demo.base.BsBaseMapper");
        // // 自定义 service 父类
        // strategyConfig.setSuperServiceClass("com.baomidou.demo.base.BsBaseService");
        // // 自定义 service 实现类父类
        // strategyConfig.setSuperServiceImplClass("com.baomidou.demo.base.BsBaseServiceImpl");
        // 自定义 controller 父类
        // strategyConfig.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        strategyConfig.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name =
        // name; return this;}
        strategyConfig.setEntityBuilderModel(true);
        // 【实体】是否为lombok模型（默认 false）<a
        // href="https://projectlombok.org/">document</a>
        strategyConfig.setEntityLombokModel(false);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setVersionFieldName("version");
        // Boolean类型字段是否移除is前缀处理
        // strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        // strategyConfig.setRestControllerStyle(true);
        // strategyConfig.setControllerMappingHyphenStyle(true);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(prop.getString("parent"));
        packageConfig.setModuleName(prop.getString("moduleName"));
//		packageConfig.setController("controller");// 这里是控制器包名，默认 web
//		packageConfig.setMapper("dao");
        packageConfig.setEntity("dto");

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig template = new TemplateConfig();
        template.setXml(null);
        template.setController(null);
        template.setService(null);
        template.setServiceImpl(null);
        template.setMapper(null);
        template.setEntity("/templates/entityVo.java");

        InjectionConfig customConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(globalConfig);
        generator.setDataSource(dataSourceConfig);
        generator.setStrategy(strategyConfig);
        generator.setPackageInfo(packageConfig);
        generator.setCfg(customConfig);
        generator.setTemplate(template);
        generator.execute();
    }

}
