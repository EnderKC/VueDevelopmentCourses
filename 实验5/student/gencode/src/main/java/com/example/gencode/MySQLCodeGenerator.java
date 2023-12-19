package com.example.gencode;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.example.common.core.BaseController;
import com.example.common.core.BaseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成工具
 * <br>
 *
 * @author lihuanzhe
 * @version 1.0
 */
public class MySQLCodeGenerator {
    /**
     * 服务父项目的名称，没有填空
     */
    private static final String PARENT_SERVICE_NAME = "";
    /**
     * 服务名称，没有填空
     */
    private static final String SERVICE_NAME = "score";
    /**
     * 项目的统一包名
     */
    private static final String PARENT_PACKET_NAME = "com.example.score";
    /**
     * 项目划分包名，用于划分不同的模块
     */
    private static final String PACKAGE_NAME = "";

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8",
            "root", "mysql")
            .build();

    /**
     * 全局配置
     *
     * @return GlobalConfig
     */
    private static GlobalConfig globalConfig() {

        //获取当前项目根目录mybatisplus-master
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath;
        if (!PARENT_SERVICE_NAME.isEmpty()) {
            outputDir = outputDir + "/" + PARENT_SERVICE_NAME;
        }
        if (!SERVICE_NAME.isEmpty()) {
            outputDir = outputDir + "/" + SERVICE_NAME;
        }
        outputDir = outputDir + "/src/main/java";
        return new GlobalConfig.Builder()
                .disableOpenDir()
                .outputDir(outputDir)
                .author("lihuanzhe")
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd")
                .build();
    }

    /**
     * 包配置
     *
     * @return PackageConfig
     */
    private static PackageConfig packageConfig() {
        //获取当前项目根目录
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath;
        if (!PARENT_SERVICE_NAME.isEmpty()) {
            outputDir = outputDir + "/" + PARENT_SERVICE_NAME;
            projectPath = projectPath + "/" + PARENT_SERVICE_NAME;
        }
        if (!SERVICE_NAME.isEmpty()) {
            outputDir = outputDir + "/" + SERVICE_NAME;
            projectPath = projectPath + "/" + SERVICE_NAME;
        }
        outputDir = outputDir + "/src/main/java/"+PARENT_PACKET_NAME.replace(".","/");
        if(!outputDir.endsWith("/")){
            outputDir = outputDir +"/";
        }

        // 设置自定义路径
        Map<OutputFile, String> pathInfo = new HashMap<>(5);
        if(PACKAGE_NAME.isEmpty()){
            pathInfo.put(OutputFile.xml, projectPath + "/src/main/resources/mapper");
            pathInfo.put(OutputFile.entity, outputDir + "entity");
            return new PackageConfig.Builder()
                    .parent(PARENT_PACKET_NAME)
                    .entity("entity")
                    .service("service")
                    .serviceImpl("service" + ".impl")
                    .mapper("mapper")
                    .xml("mapper")
                    .controller("controller")
                    .pathInfo(pathInfo)
                    .build();
        }else{
            pathInfo.put(OutputFile.xml, outputDir + "/src/main/resources/mapper/" + PACKAGE_NAME);
            pathInfo.put(OutputFile.entity, outputDir + "entity/" + PACKAGE_NAME);
            return new PackageConfig.Builder()
                    .parent(PARENT_PACKET_NAME)
                    .entity("entity" + "." + PACKAGE_NAME)
                    .service("service" + "." + PACKAGE_NAME)
                    .serviceImpl("service" + "." + PACKAGE_NAME + ".impl")
                    .mapper("mapper" + "." + PACKAGE_NAME)
                    .xml("mapper")
                    .controller("controller" + "." + PACKAGE_NAME)
                    .pathInfo(pathInfo)
                    //.pathInfo(Collections.singletonMap(OutputFile.xml, "src/main/resources/mapper"))
                    //.pathInfo(Collections.singletonMap(OutputFile.entity, outputDir+moduleName+"api/entity"))
                    .build();
        }

    }

    /**
     * 模板配置
     *
     * @return TemplateConfig
     */
    private static TemplateConfig templateConfig() {
        return new TemplateConfig.Builder()
                //.disable(TemplateType.ENTITY).disable(TemplateType.XML)
                .entity("/templates/entity.java.vm")
                .service("/templates/service.java.vm")
                .serviceImpl("/templates/serviceImpl.java.vm")
                .mapper("/templates/mapper.java.vm")
                .xml("/templates/mapper.xml.vm")
                .controller("/templates/controller.java.vm")
                .build();
    }

    /**
     * 注入配置，打印配置信息
     *
     * @return InjectionConfig
     */
    private static InjectionConfig injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        Map<String, String> map = new HashMap<>(10);
        String parentPackageName = PARENT_PACKET_NAME;
        if(PACKAGE_NAME.isEmpty()){
            map.put("Entity", parentPackageName + ".entity");
            map.put("Mapper", parentPackageName + ".mapper");
            map.put("Parent", parentPackageName);
            map.put("Xml", parentPackageName + ".mapper");
            map.put("ServiceImpl", parentPackageName + ".service" + ".impl");
            map.put("Service", parentPackageName + ".service");
            map.put("Controller", parentPackageName + ".controller");
            map.put("package","");
        }else{
            map.put("Entity", parentPackageName + ".entity" + "." + PACKAGE_NAME);
            map.put("Mapper", parentPackageName + ".mapper" + "." + PACKAGE_NAME);
            map.put("Parent", parentPackageName);
            map.put("Xml", parentPackageName + ".mapper" + "." + PACKAGE_NAME);
            map.put("ServiceImpl", parentPackageName + ".service" + "." + PACKAGE_NAME + ".impl");
            map.put("Service", parentPackageName + ".service" + "." + PACKAGE_NAME);
            map.put("Controller", parentPackageName + ".controller" + "." + PACKAGE_NAME);
            map.put("package",PACKAGE_NAME);
        }


        return new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.toString());
                })
                .customMap(Collections.singletonMap("package", map))
                //.customFile(Collections.singletonMap("test.txt", "/templates/test.vm"))
                .build();
    }

    /**
     * 策略配置
     *
     * @return StrategyConfig
     */
    private static StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude("StuInfo","User","course","score","course_choosing")
                .addTablePrefix("")
                //controller配置
                .controllerBuilder()
                .enableFileOverride()
                .superClass(BaseController.class)
                .enableRestStyle()
                .build()
                //service配置
                .serviceBuilder()
                .enableFileOverride()
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImpl")
                .build()
                //entity配置
                .entityBuilder()
                .enableFileOverride()
                //.superClass(BaseEntity.class)
                .enableLombok()
                .enableChainModel()
                .enableTableFieldAnnotation()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                //.addSuperEntityColumns("create_by", "create_time", "update_by", "update_time", "remark")
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .idType(IdType.AUTO)
                .build()
                //mapper配置
                .mapperBuilder()
                .enableBaseResultMap()
                .enableBaseColumnList()
                .enableFileOverride()
                .build();

    }

    /**
     * 生成代码
     */
    public static void generateCode() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.global(globalConfig());
        generator.packageInfo(packageConfig());
        generator.template(templateConfig());
        generator.injection(injectionConfig());
        generator.strategy(strategyConfig());
        generator.execute();
    }

    /**
     * 主函数
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        MySQLCodeGenerator.generateCode();
    }
}
