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
package org.apache.sqoop.json;

import org.apache.sqoop.model.MConnectionForms;
import org.apache.sqoop.model.MConnector;
import org.apache.sqoop.model.MForm;
import org.apache.sqoop.model.MFramework;
import org.apache.sqoop.model.MJob;
import org.apache.sqoop.model.MJobForms;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static org.apache.sqoop.json.util.FormSerialization.*;
import static org.apache.sqoop.json.util.ResourceBundleSerialization.*;

/**
 *
 */
public class FrameworkBean implements JsonBean {


  private MFramework framework;

  private ResourceBundle bundle;

  // for "extract"
  public FrameworkBean(MFramework framework, ResourceBundle bundle) {
    this.framework = framework;
    this.bundle = bundle;
  }

  // for "restore"
  public FrameworkBean() {
  }

  public MFramework getFramework() {
    return framework;
  }

  public ResourceBundle getResourceBundle() {
    return bundle;
  }

  @SuppressWarnings("unchecked")
  @Override
  public JSONObject extract(boolean skipSensitive) {
    // @TODO(Abe): Add From/To connection forms.
    JSONArray conForms =
      extractForms(framework.getConnectionForms().getForms(), skipSensitive);
    JSONArray jobForms = extractForms(framework.getJobForms().getForms(), skipSensitive);

    JSONObject result = new JSONObject();
    result.put(ID, framework.getPersistenceId());
    result.put(FRAMEWORK_VERSION, framework.getVersion());
    result.put(CON_FORMS, conForms);
    result.put(JOB_FORMS, jobForms);
    result.put(RESOURCES, extractResourceBundle(bundle));
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void restore(JSONObject jsonObject) {
    long id = (Long) jsonObject.get(ID);
    String frameworkVersion = (String) jsonObject.get(FRAMEWORK_VERSION);

    List<MForm> connForms = restoreForms((JSONArray) jsonObject.get(CON_FORMS));
    List<MForm> jobForms = restoreForms((JSONArray) jsonObject.get(JOB_FORMS));

    // @TODO(Abe): Get From/To connection forms.
    framework = new MFramework(
        new MConnectionForms(connForms),
        new MJobForms(jobForms),
        frameworkVersion);
    framework.setPersistenceId(id);

    bundle = restoreResourceBundle((JSONObject) jsonObject.get(RESOURCES));
  }

}
