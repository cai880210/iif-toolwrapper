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

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Test class for the template-based service code generator.
 * 
 * @author shsdev https://github.com/shsdev
 * @version 0.7
 */
public class TestServiceCodeTemplate {

    private String serviceTmpl = "tmpl/service_test.vm";
    private String operationTmpl = "tmpl/operation_test.vm";
    /** Logger */
    private static Logger logger = Logger.getLogger(TestServiceCodeTemplate.class.getName());


    /**
     * Simple test for required template files.
     * @throws FileNotFoundException if either of the template files doesn't exits
     */
    @Test
    public void testTemplateFilesAvailable() throws FileNotFoundException {
        if (!(new File(serviceTmpl)).exists() || !(new File(operationTmpl)).exists() )
            throw new FileNotFoundException();
    }

    /**
     * @throws IOException
     */
    @Test
    public void testTemplateEvaluation() throws IOException  {

        ServiceCode sc = new ServiceCode(serviceTmpl);

        OperationCode operation1 = new OperationCode(operationTmpl, 1);
        operation1.setOperationName("doSomething");
        operation1.put("operationname", operation1.getOperationName() );
        operation1.put("inputsection", "String input = \"test\";");
        operation1.put("outputsection", "return input;");
        operation1.addParameter("String infile");
        operation1.addParameter("Integer reversible");
        operation1.put("parameters", operation1.getParametersCsList());
        operation1.evaluate();
        sc.addOperation(operation1);

        OperationCode operation2 = new OperationCode(operationTmpl, 2);
        operation2.setOperationName("doSomethingElse");
        operation2.put("operationname", operation2.getOperationName());
        operation2.put("inputsection", "String input = \"Another test\";");
        operation2.put("outputsection", "return input;");
        operation2.addParameter("String infile");
        operation2.put("parameters", operation2.getParametersCsList());
        operation2.evaluate();
        sc.addOperation(operation2);

        sc.put("operations", sc.getOperations());

        sc.evaluate();
        logger.debug("Template evaluation result:\n" + sc.getCode());

    }
}
