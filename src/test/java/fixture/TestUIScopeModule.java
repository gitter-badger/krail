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
package fixture;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import uk.q3c.krail.core.guice.uiscope.UIScope;
import uk.q3c.krail.core.guice.uiscope.UIScoped;

/**
 * Mocks the UIScope
 *
 * @authors David Sowerby, Will Temperley
 */
public class TestUIScopeModule extends AbstractModule {

    private UIScope uiScope = new TestUIScope();

    public TestUIScopeModule() {
        super();
    }

    @Override
    public void configure() {
        // tell Guice about the scope
        bindScope(UIScoped.class, uiScope);

        // make our scope instance injectable
        bind(UIScope.class).annotatedWith(Names.named("UIScope"))
                           .toInstance(uiScope);
    }

    public UIScope getUiScope() {
        return uiScope;
    }

}