/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sqoop.framework;

import org.apache.sqoop.common.ConnectorType;
import org.apache.sqoop.common.MutableMapContext;
import org.apache.sqoop.connector.idf.IntermediateDataFormat;
import org.apache.sqoop.connector.spi.SqoopConnector;
import org.apache.sqoop.job.etl.CallbackBase;
import org.apache.sqoop.model.MSubmission;
import org.apache.sqoop.utils.ClassUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Submission details class is used when creating new submission and contains
 * all information that we need to create a new submission (including mappers,
 * reducers, ...).
 */
public class SubmissionRequest {

  /**
   * Submission summary
   */
  MSubmission summary;

  /**
   * Original job name
   */
  String jobName;

  /**
   * Associated job (from metadata perspective) id
   */
  long jobId;

  /**
   * Connector instance associated with this submission request
   */
  Map<ConnectorType, SqoopConnector > connectors;

  /**
   * List of required local jars for the job
   */
  List<String> jars;

  /**
   * From connector callback
   */
  CallbackBase fromCallback;

  /**
   * To connector callback
   */
  CallbackBase toCallback;

  /**
   * All configuration objects
   */
  Map<ConnectorType, Object> connectorConnectionConfigs;
  Map<ConnectorType, Object> connectorJobConfigs;
  Map<ConnectorType, Object> frameworkConnectionConfigs;
  Object configFrameworkJob;

  /**
   * Connector context (submission specific configuration)
   */
  Map<ConnectorType, MutableMapContext> connectorContexts;

  /**
   * Framework context (submission specific configuration)
   */
  MutableMapContext frameworkContext;

  /**
   * HDFS output directory
   */
  String outputDirectory;

  /**
   * Optional notification URL for job progress
   */
  String notificationUrl;

  /**
   * Number of extractors
   */
  Integer extractors;

  /**
   * Number of loaders
   */
  Integer loaders;

  /**
   * The intermediate data format this submission should use.
   */
  Class<? extends IntermediateDataFormat> intermediateDataFormat;

  public SubmissionRequest() {
    this.jars = new LinkedList<String>();
    this.connectorContexts = new HashMap<ConnectorType, MutableMapContext>();

    this.connectorContexts.put(ConnectorType.FROM, new MutableMapContext());
    this.connectorContexts.put(ConnectorType.TO, new MutableMapContext());
    this.frameworkContext = new MutableMapContext();

    this.connectorConnectionConfigs = new HashMap<ConnectorType, Object>();
    this.connectorJobConfigs = new HashMap<ConnectorType, Object>();
    this.frameworkConnectionConfigs = new HashMap<ConnectorType, Object>();

    this.connectors = new HashMap<ConnectorType, SqoopConnector>();
  }

  public MSubmission getSummary() {
    return summary;
  }

  public void setSummary(MSubmission summary) {
    this.summary = summary;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  public SqoopConnector getConnector(ConnectorType type) {
    return connectors.get(type);
  }

  public void setConnector(ConnectorType type, SqoopConnector connector) {
    this.connectors.put(type, connector);
  }

  public List<String> getJars() {
    return jars;
  }

  public void addJar(String jar) {
    if(!jars.contains(jar)) {
      jars.add(jar);
    }
  }

  public void addJarForClass(Class klass) {
    addJar(ClassUtils.jarForClass(klass));
  }

  public void addJars(List<String> jars) {
    for(String j : jars) {
      addJar(j);
    }
  }

  public CallbackBase getFromCallback() {
    return fromCallback;
  }

  public void setFromCallback(CallbackBase fromCallback) {
    this.fromCallback = fromCallback;
  }

  public CallbackBase getToCallback() {
    return toCallback;
  }

  public void setToCallback(CallbackBase toCallback) {
    this.toCallback = toCallback;
  }

  public Object getConnectorConnectionConfig(ConnectorType type) {
    return connectorConnectionConfigs.get(type);
  }

  public void setConnectorConnectionConfig(ConnectorType type, Object config) {
    connectorConnectionConfigs.put(type, config);
  }

  public Object getConnectorJobConfig(ConnectorType type) {
    return connectorJobConfigs.get(type);
  }

  public void setConnectorJobConfig(ConnectorType type, Object config) {
    connectorJobConfigs.put(type, config);
  }

  public Object getFrameworkConnectionConfig(ConnectorType type) {
    return frameworkConnectionConfigs.get(type);
  }

  public void setFrameworkConnectionConfig(ConnectorType type, Object config) {
    frameworkConnectionConfigs.put(type, config);
  }

  public Object getConfigFrameworkJob() {
    return configFrameworkJob;
  }

  public void setConfigFrameworkJob(Object config) {
    configFrameworkJob = config;
  }

  public MutableMapContext getConnectorContext(ConnectorType type) {
    return connectorContexts.get(type);
  }

  public MutableMapContext getFrameworkContext() {
    return frameworkContext;
  }

  public String getNotificationUrl() {
    return notificationUrl;
  }

  public void setNotificationUrl(String url) {
    this.notificationUrl = url;
  }

  public Integer getExtractors() {
    return extractors;
  }

  public void setExtractors(Integer extractors) {
    this.extractors = extractors;
  }

  public Integer getLoaders() {
    return loaders;
  }

  public void setLoaders(Integer loaders) {
    this.loaders = loaders;
  }

  public Class<? extends IntermediateDataFormat> getIntermediateDataFormat() {
    return intermediateDataFormat;
  }

  public void setIntermediateDataFormat(Class<? extends IntermediateDataFormat> intermediateDataFormat) {
    this.intermediateDataFormat = intermediateDataFormat;
  }

}
