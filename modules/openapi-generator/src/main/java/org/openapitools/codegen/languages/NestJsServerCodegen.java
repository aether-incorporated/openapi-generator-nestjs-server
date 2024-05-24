/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen.languages;

import io.swagger.v3.oas.models.media.Schema;
import org.openapitools.codegen.*;
import org.openapitools.codegen.meta.GeneratorMetadata;
import org.openapitools.codegen.meta.Stability;
import org.openapitools.codegen.meta.features.DocumentationFeature;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.ModelsMap;
import org.openapitools.codegen.model.OperationMap;
import org.openapitools.codegen.model.OperationsMap;
import org.openapitools.codegen.utils.ModelUtils;
import org.openapitools.codegen.utils.SemVer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.openapitools.codegen.utils.StringUtils.*;

public class NestJsServerCodegen extends AbstractTypeScriptClientCodegen {

    private final Logger LOGGER = LoggerFactory.getLogger(NestJsServerCodegen.class);

    private static String CLASS_NAME_PREFIX_PATTERN = "^[a-zA-Z0-9]*$";
    private static String CLASS_NAME_SUFFIX_PATTERN = "^[a-zA-Z0-9]*$";
    private static String FILE_NAME_SUFFIX_PATTERN = "^[a-zA-Z0-9.-]*$";

    private static final String DEFAULT_IMPORT_PREFIX = "./";
    private static final String DEFAULT_MODEL_IMPORT_DIRECTORY_PREFIX = "../";

    public static final String OPERATION_GUARDS = "operationGuards";

    public static final String NPM_REPOSITORY = "npmRepository";

    protected String apiModuleClassName = "ApiModule";
    protected String npmRepository = null;
    protected String serviceSuffix = "Service";
    protected String serviceFileSuffix = ".service";
    protected String modelSuffix = "Dto";
    protected String modelFileSuffix = ".dto";
    protected String sourceFolder = "src";

    protected boolean operationGuards = false;

    public NestJsServerCodegen() {
        super();

        modifyFeatureSet(features -> features.includeDocumentationFeatures(DocumentationFeature.Readme));

        this.outputFolder = "generated-code/nestjs-server";
        embeddedTemplateDir = templateDir = "nestjs-server";

        supportsMultipleInheritance = true;

        cliOptions.add(CliOption.newBoolean(OPERATION_GUARDS,
                "Whether to generate individual guards for each operation that can be configured", operationGuards));

        // Git files
        supportingFiles.add(new SupportingFile("gitignore.mustache", "", ".gitignore"));
        supportingFiles.add(new SupportingFile("git_push.sh.mustache", "", "git_push.sh"));

        // Configuration files
        supportingFiles.add(new SupportingFile("package.mustache", "", "package.json"));
        supportingFiles.add(new SupportingFile("tsconfig.mustache", "", "tsconfig.json"));
        supportingFiles.add(new SupportingFile("tsconfig.build.mustache", "", "tsconfig.build.json"));

        // API Files
        apiPackage = "api";
        apiTemplateFiles.put("api" + File.separator + "api.module.mustache", ".module.ts");
        apiTemplateFiles.put("api" + File.separator + "api.service.mustache", ".service.ts");
        apiTemplateFiles.put("api" + File.separator + "api.controller.mustache", ".controller.ts");
        apiTemplateFiles.put("api" + File.separator + "api.types.mustache", ".types.ts");
        apiTemplateFiles.put("api" + File.separator + "api-implementation.service.mustache", "-implementation.service.ts");
        apiTemplateFiles.put("api" + File.separator + "api-implementation.module.mustache", "-implementation.module.ts");
        supportingFiles.add(new SupportingFile("api" + File.separator + "api-root.module.mustache", getApiDirectory(), "api.module.ts"));
        supportingFiles.add(new SupportingFile("api" + File.separator + "api-root.types.ts", getApiDirectory(), "types.ts"));

        // Model Files
        modelPackage = "models" + File.separator + "dto";
        modelTemplateFiles.put("models" + File.separator + "model.mustache", ".ts");
        supportingFiles.add(new SupportingFile("models" + File.separator + "index.mustache", getModelDirectory(), "index.ts"));

        // Main files
        supportingFiles.add(new SupportingFile("main.mustache", sourceFolder, "main.ts"));
        supportingFiles.add(new SupportingFile("app.module.mustache", sourceFolder, "app.module.ts"));

        // Auth files
        supportingFiles.add(new SupportingFile("auth" + File.separator + "auth.module.mustache", getAuthDirectory(), "auth.module.ts"));
        supportingFiles.add(new SupportingFile("auth" + File.separator + "auth.decorator.mustache", getAuthDirectory(), "auth.decorator.ts"));
        supportingFiles.add(new SupportingFile("auth" + File.separator + "auth.guard.mustache", getAuthDirectory(), "auth.guard.ts"));
        supportingFiles.add(new SupportingFile("auth" + File.separator + "auth.strategy.mustache", getAuthDirectory(), "auth.strategy.ts"));
        supportingFiles.add(new SupportingFile("auth" + File.separator + "auth.types.mustache", getAuthDirectory(), "auth.types.ts"));

        generatorMetadata = GeneratorMetadata.newBuilder(generatorMetadata)
                .stability(Stability.BETA)
                .build();

        //
        classEnumSeparator = "";

        // languageSpecificPrimitives.add("Blob");
        // typeMapping.put("file", "Blob");

        // this.cliOptions.add(new CliOption(NPM_REPOSITORY,
        // "Use this property to set an url your private npmRepo in the package.json"));
    }

    // @Override
    // protected void addAdditionPropertiesToCodeGenModel(CodegenModel codegenModel, Schema schema) {
    //     codegenModel.additionalPropertiesType = getTypeDeclaration(getAdditionalProperties(schema));
    //     addImport(codegenModel, codegenModel.additionalPropertiesType);
    // }

    @Override
    public String getName() {
        return "nestjs-server";
    }

    @Override
    public String getHelp() {
        return "Generates a NestJs server package.";
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    @Override
    public void processOpts() {
        super.processOpts();

        // if (additionalProperties.containsKey(OPERATION_GUARDS)) {
        //     this.setOperationGuards(Boolean.parseBoolean(additionalProperties.get(CodegenConstants.DELEGATE_PATTERN).toString()));
        // }

        // if (operationGuards) {
        //     additionalProperties.put("operationGuards", "true");
        //     supportingFiles.add(
        //             new SupportingFile("auth/operations.guard.mustache", getAuthDirectory(), "operations.guard.ts"));
        // }
    }

    /**
     * Return the source directory containing authentication files
     */
    private String getAuthDirectory() {
        // TODO -- allow configuring?
        return sourceFolder + File.separator + "auth";
    }

    /**
     * Return the RELATIVE path to the api files (from generated folder root)
     */
    private String getApiDirectory() {
        return sourceFolder + File.separator + apiPackage();
    }

    /**
     * Return the RELATIVE path to model files
     */
    private String getModelDirectory() {
        return sourceFolder + File.separator + modelPackage() + File.separator;
    }

    @Override
    public boolean isDataTypeFile(final String dataType) {
        return "Blob".equals(dataType);
    }

    @Override
    public String getTypeDeclaration(Schema p) {
        if (ModelUtils.isFileSchema(p)) {
            return "Blob";
        } else {
            return super.getTypeDeclaration(p);
        }
    }

    public void setOperationGuards(boolean operationGuards) {
        this.operationGuards = operationGuards;
    }

    public boolean getOperationGuards() {
        return this.operationGuards;
    }

    /**
     * Attempt to assign a type based on the Typescript type mappings.
     * If the type is not found, set to unknown
     */
    private String applyLocalTypeMapping(String type) {
        if (typeMapping.containsKey(type)) {
            return typeMapping.get(type);
        }
        return type;
    }

    /**
     * Override the implementation of this, since we do NOT want unset defaults
     * to have an undefined default value, they should just not be set
     */
    @Override
    public String toDefaultValue(Schema p) {
        if (p.getDefault() == null) {
            return null;
        }
        return super.toDefaultValue(p);
    }

    @Override
    public void postProcessParameter(CodegenParameter parameter) {
        super.postProcessParameter(parameter);
        parameter.dataType = applyLocalTypeMapping(parameter.dataType);
    }

    /**
     * Post process all the operations with the models for additional info.
     * In this case, we need to replace the operation path params e.g. {id}
     * with the NestJS equivalent, e.g. :id
     */
    @Override
    public OperationsMap postProcessOperationsWithModels(OperationsMap operations, List<ModelMap> allModels) {
        OperationMap objs = operations.getOperations();

        // Add filename information for api imports
        operations.put("apiFilename", getApiFilenameFromClassname(objs.getClassname()));

        List<CodegenOperation> ops = objs.getOperation();
        boolean hasSomeFormParams = false;
        for (CodegenOperation op : ops) {
            if (op.getHasFormParams()) {
                hasSomeFormParams = true;
            }
            op.httpMethod = op.httpMethod.toLowerCase(Locale.ENGLISH);

            // Prep a string buffer where we'll set up the new version of the path
            StringBuilder pathBuffer = new StringBuilder();
            StringBuilder parameterName = new StringBuilder();
            int insideCurly = 0;

            // Iterate through existing string, one character at a time.
            for (int i = 0; i < op.path.length(); i++) {
                switch (op.path.charAt(i)) {
                    case '{':
                        // We entered curly braces, so track that.
                        insideCurly++;

                        // Instead of braces we add a colon for the express-style routing
                        pathBuffer.append(":");
                        break;
                    case '}':
                        // We exited curly braces, so track that.
                        insideCurly--;

                        pathBuffer.append(toParamName(parameterName.toString()));
                        parameterName.setLength(0);
                        break;
                    default:
                        char nextChar = op.path.charAt(i);
                        if (insideCurly > 0) {
                            parameterName.append(nextChar);
                        } else {
                            pathBuffer.append(nextChar);
                        }
                        break;
                }
            }

            // Overwrite path after applying everything we just did
            op.path = pathBuffer.toString();
        }

        operations.put("hasSomeFormParams", hasSomeFormParams);

        // Add additional filename information for model imports in the services
        List<Map<String, String>> imports = operations.getImports();
        for (Map<String, String> im : imports) {
            // This property is not used in the templates any more, subject for removal
            im.put("filename", im.get("import"));
            im.put("classname", im.get("classname"));
        }

        return operations;
    }

    @Override
    public ModelsMap postProcessModels(ModelsMap objs) {
        ModelsMap result = super.postProcessModels(objs);
        return postProcessModelsEnum(result);
    }

    @Override
    public Map<String, ModelsMap> postProcessAllModels(Map<String, ModelsMap> objs) {
        Map<String, ModelsMap> result = super.postProcessAllModels(objs);
        for (ModelsMap entry : result.values()) {
            for (ModelMap mo : entry.getModels()) {
                CodegenModel cm = mo.getModel();

                // Add additional filename information for imports
                Set<String> parsedImports = parseImports(cm);
                mo.put("tsImports", toTsImports(cm, parsedImports));
            }
        }
        return result;
    }

    /**
     * Parse imports
     */
    private Set<String> parseImports(CodegenModel cm) {
        Set<String> newImports = new HashSet<>();
        if (cm.imports.size() > 0) {
            for (String name : cm.imports) {
                if (name.indexOf(" | ") >= 0) {
                    String[] parts = name.split(" \\| ");
                    Collections.addAll(newImports, parts);
                } else {
                    newImports.add(name);
                }
            }
        }
        return newImports;
    }

    private List<Map<String, String>> toTsImports(CodegenModel cm, Set<String> imports) {
        List<Map<String, String>> tsImports = new ArrayList<>();
        for (String im : imports) {
            if (!im.equals(cm.classname)) {
                HashMap<String, String> tsImport = new HashMap<>();
                // TVG: This is used as class name in the import statements of the model file
                tsImport.put("classname", im);
                // tsImport.put("filename", toModelFilename(removeModelPrefixSuffix(im)));
                tsImport.put("filename", toModelFilename(im.substring(0, im.length() - modelSuffix.length())));
                tsImports.add(tsImport);
            }
        }
        return tsImports;
    }

    /**
     * Returns the ABSOLUTE path to the api files within the src directory
     */
    @Override
    public String apiFileFolder() {
        return (outputFolder + File.separator + getApiDirectory()).replace('/', File.separatorChar);
    }

    // OG apiFilename
    // @Override
    // public String apiFilename(String templateName, String tag) {
    // String suffix = apiTemplateFiles().get(templateName);
    // return apiFileFolder() + File.separator + toApiFilename(tag) + suffix;
    // }

    /**
     * Converts the api filenames to include the tag as a directory.
     * This helps to separate the api logic for cleaner browsing of the files
     */
    @Override
    public String apiFilename(String templateName, String tag) {
        String result = super.apiFilename(templateName, tag);

        // Each api file should be separated into its own directory
        // If no directory is provided, set to 'default'
        String directoryName = convertUsingFileNamingConvention(tag);
        if (directoryName == null || directoryName.length() == 0) {
            directoryName = "default";
        }

        int index = result.lastIndexOf(File.separator);
        result = result.substring(0, index) + File.separator + directoryName + result.substring(index, result.length());

        // For implementation files, add them to a specific sub-directory for easier
        // navigation
        if (templateName.contains("implementation")) {
            index = result.lastIndexOf(File.separator);
            result = result.substring(0, index) + File.separator + "implementation"
                    + result.substring(index, result.length());
        }

        return result;
    }

    @Override
    public String toApiName(String name) {
        if (name.length() == 0) {
            return "Default";
        }
        return camelize(name);
    }

    @Override
    public String toApiFilename(String name) {
        if (name.length() == 0) {
            return "default";
        }
        return this.convertUsingFileNamingConvention(name);
    }

    @Override
    public String toApiImport(String name) {
        if (importMapping.containsKey(name)) {
            return importMapping.get(name);
        }
        return apiPackage() + "/" + toApiFilename(name);
    }

    /**
     * Return the ABSOLUTE path to model files
     */
    @Override
    public String modelFileFolder() {
        return (outputFolder + File.separator + getModelDirectory()).replace('/', File.separatorChar);
    }

    @Override
    public String toModelFilename(String name) {
        if (importMapping.containsKey(name)) {
            return importMapping.get(name);
        }
        return DEFAULT_IMPORT_PREFIX + this.convertUsingFileNamingConvention(this.sanitizeName(name)) + modelFileSuffix;
    }

    @Override
    public String toModelImport(String name) {
        if (importMapping.containsKey(name)) {
            return importMapping.get(name);
        }
        return DEFAULT_MODEL_IMPORT_DIRECTORY_PREFIX + modelPackage() + "/"
                + toModelFilename(name.substring(0, name.length() - modelSuffix.length()))
                        .substring(DEFAULT_IMPORT_PREFIX.length());
    }

    public String getNpmRepository() {
        return npmRepository;
    }

    public void setNpmRepository(String npmRepository) {
        this.npmRepository = npmRepository;
    }

    private String getApiFilenameFromClassname(String classname) {
        // String name = classname.substring(0, classname.length() -
        // serviceSuffix.length());
        return toApiFilename(classname);
    }

    @Override
    public String toModelName(String name) {
        name = addSuffix(name, modelSuffix);
        return super.toModelName(name);
    }

    /**
     * Converts the original name according to the file naming standard for NestJS
     * (kebab case)
     *
     * @param originalName the original name to transform
     * @return the transformed name
     */
    private String convertUsingFileNamingConvention(String originalName) {
        String name = originalName; // this.removeModelPrefixSuffix(originalName);
        return dashize(underscore(name));
    }
}
