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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Operation code that can be evaluated by the Velocity template
 * engine. Evaluation is performed by applying the Velocity context to the
 * source code content.
 * @author shsdev https://github.com/shsdev
 * @version 0.7
 */
public class OperationCode extends Code {

    /** Logger */
    private static Logger logger = Logger.getLogger(OperationCode.class.getName());
    
    private int opid;
    private String operationName;
    private ArrayList<String> parameters;
    private StringBuilder inputSection;
    private StringBuilder outputSection;

    private ArrayList<String> outFileItems;

    private ArrayList<String> resultElements;

    /**
     * @param filePath
     * @param opid
     * @throws IOException
     */
    public OperationCode(String filePath, int opid) throws IOException {
        super(filePath);
        parameters = new ArrayList<String>();
        this.opid = opid;
        inputSection = new StringBuilder();
        outputSection = new StringBuilder();
        outFileItems = new ArrayList<String>();
        resultElements = new ArrayList<String>();
    }


    /**
     * @param resultElement the resultElement to add
     */
    public void addResultElement(String resultElement) {
        resultElements.add(resultElement);
    }

    /**
     * @return the parameters
     */
    public ArrayList<String> getResultElements() {
        return resultElements;
    }

    /**
     * @param outFileItem the outFileItem to add
     */
    public void addOutFileItem(String outFileItem) {
        outFileItems.add(outFileItem);
    }

    /**
     * @return the parameters
     */
    public ArrayList<String> getOutFileItems() {
        return outFileItems;
    }

    /**
     * Add a list of operations to the Velocity context
     * @param string Key
     * @param listitem List of operations
     */
    public void put(String string, List<String> listitem) {
        getCtx().put(string, listitem);
    }

    /**
     * @param parameter the parameter to add
     */
    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    /**
     * @return the operationName
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * @param operationName the operationName to set
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
     * @return the parameters
     */
    public ArrayList<String> getParameters() {
        return parameters;
    }

    /**
     * @return the parameter list
     */
    public String getParametersCsList() {
        logger.debug(parameters.size() + " parameters in parameter list of "
                + "operation \""+this.operationName + "\"");
        String result = "";
        if (parameters == null || parameters.isEmpty()) {
            result = "";
        } else {
            for (String parameter : parameters) {
                result += parameter + ", ";
            }
            result = result.substring(0, result.length() - 2);

        }
        return result;
    }

    /**
     * @return the opid
     */
    public int getOpid() {
        return opid;
    }

    /**
     * @param opid the opid to set
     */
    public void setOpid(int opid) {
        this.opid = opid;
    }

    /**
     * Append code to the input section
     * @param code
     */
    public void appendInputSection(String code) {
        inputSection.append(code);
    }

    /**
     * Append code to the output section
     * @param code
     */
    public void appendOutputSection(String code) {
        outputSection.append(code);
    }

    /**
     * @return the inputSection
     */
    public String getInputSection() {
        return inputSection.toString();
    }

    /**
     * @return the outputSection
     */
    public String getOutputSection() {
        return outputSection.toString();
    }
}
