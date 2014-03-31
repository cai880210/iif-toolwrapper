/*
 * Copyright 2011 The IMPACT Project Consortium
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package eu.impact_project.iif.tw.gen;

import eu.impact_project.iif.tw.util.FileUtil;
import eu.impact_project.iif.tw.util.PropertyUtil;
import eu.impact_project.iif.tw.util.StringConverterUtil;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * PropertiesSubstitutor
 * @author shsdev https://github.com/shsdev
 * @version 0.7
 */
public class PropertiesSubstitutor extends Substitutor {

    private static Logger logger = Logger.getLogger(PropertiesSubstitutor.class.getName());
    private PropertyUtil pu;
    private String templateDir;
    private String generateDir;
    private ServiceDef serviceDef;

    /**
     * Default constructor
     */
    public PropertiesSubstitutor() {
    }

    /**
     * Reads the properties from the project configuration properties file
     * and creates the substitution variable map.
     * @param propertiesAbsPath the path to the properties file
     * @throws GeneratorException
     */
    public PropertiesSubstitutor(String propertiesAbsPath) throws GeneratorException {
        super();
        try {
            pu = new PropertyUtil(propertiesAbsPath);
        } catch (GeneratorException ex) {
            throw new GeneratorException("Unable to load properties.");
        }

        templateDir = pu.getProp("project.template.dir");
        generateDir = pu.getProp("project.generate.dir");

        // Substitution variables
        Map<String, String> map = pu.getKeyValuePairs();
        Set<Entry<String, String>> propertySet = map.entrySet();
        for (Entry<String, String> entry : propertySet) {
            String key = entry.getKey();
            String val = entry.getValue();
            this.putKeyValuePair(key, val);
        }
    }

    /**
     * Adds a key value pair to the subsitutor
     * @param key the key
     * @param val the value associated with the key
     */
    public void addVariable(String key, String val) {
        this.putKeyValuePair(key, val);
    }

    /**
     * Derives some variables from the service definition
     * @throws GeneratorException
     */
    public void deriveVariables() throws GeneratorException {
        if(serviceDef == null) {
            throw new GeneratorException("Service definition missing, unable "
                    + "to derive variables");
        }
        VelocityContext vc = getContext();
        Object[] keys = vc.getKeys();
        for (Object key : keys) {
            String keyStr = (String)key;
            String val =  (String) vc.get(keyStr);
            if (key.equals("project_title")) {
                this.putKeyValuePair("project_midfix", serviceDef.getMidfix());
                logger.debug("Note: Velocity variable \"project_midfix\" is derived from property \"project.title\"");
                this.putKeyValuePair("project_midfix_lc", serviceDef.getDirectory());
                logger.debug("Note: Velocity variable \"project_midfix_lc\" is derived from property \"project.title\"");
            } else if (key.equals("global_package_name")) {
                String projectPackagePath = StringConverterUtil.packageNameToPackagePath(val);
                this.putKeyValuePair("project_package_path", projectPackagePath);
                logger.debug("Note: Velocity variable \"project_package_path\" is derived from property \"global.package.name\"");
                String projectNamespace = StringConverterUtil.packageNameToNamespace(val);
                this.putKeyValuePair("project_namespace", projectNamespace);
                logger.debug("Note: Velocity variable \"project_namespace\" is derived from property \"global.package.name\"");
            } else if (key.equals("global_project_prefix")) {
                String globalProjectPrefixLc = val.toLowerCase();
                this.putKeyValuePair("global_project_prefix_lc", globalProjectPrefixLc);
                logger.debug("Note: Velocity variable \"global_project_prefix_lc\" is derived from property \"global.project.prefix\"");
            }
        }

    }

    @Override
    public void processFile(File path) throws GeneratorException {
        //String trgtFilePath = replaceVars(path.getPath());
        String trgtFilePath = path.getPath();
        trgtFilePath = trgtFilePath.replace(pu.getProp("project.template.dir"), pu.getProp("project.generate.dir") + "/" + serviceDef.getDirectory());
        String trgtDirStr = trgtFilePath.substring(0, trgtFilePath.lastIndexOf(File.separator));
        FileUtil.mkdirs(new File(trgtDirStr));
        //trgtFilePath = replaceVars(trgtFilePath);
        applySubstitution(path.getPath(), trgtFilePath);
    }

    /**
     * Getter for the global project prefix
     * @return global project prefix
     */
    public String getGlobalProjectPrefix() {
        return pu.getProp("global.project.prefix");
    }
    /**
     * Getter for the resources directory
     * @return resources directory
     */
    public String getProjectResourcesDir() {
        return pu.getProp("project.resources.dir");
    }
    /**
     * Getter for the library directory
     * @return library directory
     */
//    public String getProjectLibDir() {
//        return pu.getProp("project.lib.dir");
//    }
    /**
     * Getter for the template directory
     * @return template directory
     */
    public String getTemplateDir() {
        return templateDir;
    }
    /**
     * Getter for the generated directory
     * @return the generated directory
     */
    public String getGenerateDir() {
        return generateDir;
    }
    /**
     * Getter for the project midfix (e.g. SomeTool)
     * @return project midfix
     */
    public String getProjectMidfix() {
        return serviceDef.getMidfix();
    }
    /**
     * Getter for the project midfix (e.g. SomeTool)
     * @return project midfix
     */
//    public String getProjectVersion() {
//        return servicedef.getVersion();
//    }
    /**
     * Getter for the project midfix (e.g. SomeTool)
     * @return project midfix
     */
    public String getProjectDirectory() {
        return serviceDef.getDirectory();
    }
    /**
     * Getter for the property utils
     * @return property utils
     */
    public PropertyUtil getPropertyUtils() {
        return pu;
    }
    /**
     * Getter for the project midfix (e.g. SomeTool)
     * @return project midfix
     */
    public String getProjectPackagePath() {
        String ppp = (String) this.getContext().get("project_package_path");
        return ppp;
    }
    /**
     * Getter for the project midfix (e.g. SomeTool)
     * @return project midfix
     */
//    public String getProjectNamespace() {
//        String pn = (String) this.getContext().get("project_namespace");
//        return pn;
//    }
    /**
     * @return the servicedef
     */
//    public ServiceDef getServiceDef() {
//        return servicedef;
//    }
    /**
     * @param servicedef the servicedef to set
     */
//    public void setServiceDef(ServiceDef servicedef) {
//        this.servicedef = servicedef;
//    }
    /**
     * Note that the properties are dot separated like defined in the properties
     * file (e.g. some.variable=value).
     * @param string Variable
     * @return Value
     */
    public String getProp(String string) {
        return pu.getProp(string);
    }

    /**
     * Note that the velocity context variables are underscore (_) separated,
     * e.g. use some_variable=value if you have defined some.variable=value
     * in the properties file.
     * file (e.g. some.variable=value).
     * @param string Variable
     * @return Value
     */
    public String getContextProp(String string) {
        if (this.getContext() != null) {
            return (String) this.getContext().get(string);
        } else {
            return "NULL";
        }
    }

    /**
     * @return the serviceDef
     */
    public ServiceDef getServiceDef() {
        return serviceDef;
    }

    /**
     * @param serviceDef the serviceDef to set
     */
    public void setServiceDef(ServiceDef serviceDef) {
        this.serviceDef = serviceDef;
    }

}
