/*
 * Copyright (C) 2013 David Sowerby
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.q3c.krail.demo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import uk.q3c.krail.base.guice.BaseServlet;
import uk.q3c.krail.base.ui.ScopedUIProvider;

@Singleton
public class DemoServlet extends BaseServlet {

    @Inject
    public DemoServlet(ScopedUIProvider uiProvider) {
        super(uiProvider);
    }

    @Override
    protected String widgetset() {
        return "uk.q3c.krail.demo.widgetset.demoWidgetset";
    }

}