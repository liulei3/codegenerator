import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

public class CodeGenerator {

	// 输出表名称,如果要生成全部表则为空字符串
	public static final String[] TABLES = new String[]{};

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
		TableFill createField = new TableFill("strategyConfig", FieldFill.INSERT);
		tableFillList.add(createField);

		final ResourceBundle prop = ResourceBundle.getBundle("config");

		// 代码生成器
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setOutputDir(prop.getString("outputDir"));// 输出目录
		globalConfig.setFileOverride(true);// 是否覆盖文件
		globalConfig.setActiveRecord(true);// 开启 activeRecord 模式
		globalConfig.setEnableCache(false);// XML 二级缓存
		globalConfig.setBaseResultMap(false);// XML ResultMap
		globalConfig.setBaseColumnList(true);// XML columList
		globalConfig.setAuthor(prop.getString("author"));
		globalConfig.setSwagger2(false);
		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		globalConfig.setXmlName("%sMapper");
		globalConfig.setDateType(DateType.ONLY_DATE);
		globalConfig.setIdType(IdType.ID_WORKER);
//		globalConfig.setServiceName("MP%sService");
//		globalConfig.setServiceImplName("%sServiceDiy");
//		globalConfig.setControllerName("%sAction");

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
		// strategyConfig.setCapitalMode(true);// 全局大写命名
		// strategyConfig.setTablePrefix(new String[]{"unionpay_"});//
		// 此处可以修改为您的表前缀
		strategyConfig.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		if(ArrayUtils.isNotEmpty(TABLES)) {
			strategyConfig.setInclude(TABLES); // 需要生成的表
		}
		// .setExclude(new String[]{"test"}) // 排除生成的表
		// 自定义实体，公共字段
		strategyConfig.setSuperEntityColumns(new String[]{"create_time", "update_time"});
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
		strategyConfig.setRestControllerStyle(false);
		strategyConfig.setVersionFieldName("strategyConfig");
		// Boolean类型字段是否移除is前缀处理
		//strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
		//strategyConfig.setRestControllerStyle(true);
		//strategyConfig.setControllerMappingHyphenStyle(true);

		// 包配置
		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent(prop.getString("parent"));
		packageConfig.setModuleName(prop.getString("moduleName"));
//		packageConfig.setController("controller");// 这里是控制器包名，默认 web
//		packageConfig.setMapper("dao");
		packageConfig.setXml("mapper");

		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig customConfig = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
				this.setMap(map);
			}
		}.setFileOutConfigList(
				Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
					// 自定义输出文件目录
					@Override
					public String outputFile(TableInfo tableInfo) {
						return prop.getString("outputDir") + "/xml/" + tableInfo.getEntityName() + "Mapper.xml";
					}
				}));
		TemplateConfig template = getTemplateConfig();

		AutoGenerator generator = new AutoGenerator();
		generator.setGlobalConfig(globalConfig);
		generator.setDataSource(dataSourceConfig);
		generator.setStrategy(strategyConfig);
		generator.setPackageInfo(packageConfig);
		generator.setCfg(customConfig);
		generator.setTemplate(template);
		generator.execute();
	}

	/**
	 * 自定义模板配置，用于设置生成模板的文件,模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
	 * @return
	 */
	private static TemplateConfig getTemplateConfig() {
		//
		// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
		// 关闭默认 xml 生成，调整生成 至 根目录
		TemplateConfig template = new TemplateConfig();
		template.setXml(null);
//		template.setController(null);
//		template.setService(null);
//		template.setServiceImpl(null);
//		template.setMapper(null);
		// template.setEntity("...");
		return template;
	}

}
