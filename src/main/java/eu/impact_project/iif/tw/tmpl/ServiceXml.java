/*
 *  Copyright 2011 The IMPACT Project Consortium.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package eu.impact_project.iif.tw.tmpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Template-based service code generator.
 * The service code can be evaluated by the Velocity template
 * engine. Evaluation is performed by applying the Velocity context to the
 * source code content.
 * @author shsdev https://github.com/shsdev
 * @version 0.5
 */
public class ServiceXml extends Code {
    private ArrayList<ServiceXmlOp> operations;

    /**
     * Constructor for a service code instance
     * @param filePath Path to template file
     * @throws IOException Exception while reading the template file
     */
    @SuppressWarnings("serial")
	public ServiceXml(String filePath) throws IOException {
        super(filePath);
        operations = new ArrayList<ServiceXmlOp>() {};
    }

    /**
     * @return the operationSnippets
     */
    public List<ServiceXmlOp> getOperations() {
        return operations;
    }

    /**
     * @param servxmlop the operationSnippets to add
     */
    public void addOperation(ServiceXmlOp servxmlop) {
        this.operations.add(servxmlop);
    }



    /**
     * Add a list of operations to the Velocity context
     * @param string Key
     * @param operations List of operations
     */
    public void put(String string, List<ServiceXmlOp> operations) {
        getCtx().put(string, operations);
    }

    /**
     * @param targetFilePath
     * @throws IOException
     */
    public void create(String targetFilePath) throws IOException {
        this.evaluate();
        File targetFile = new File(targetFilePath);
        org.apache.commons.io.FileUtils.writeStringToFile(targetFile, this.getCode());
    }
}
