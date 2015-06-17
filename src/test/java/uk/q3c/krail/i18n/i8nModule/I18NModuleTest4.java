/*
 * Copyright (c) 2015. David Sowerby
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package uk.q3c.krail.i18n.i8nModule;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;
import com.mycila.testing.plugin.guice.ModuleProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.q3c.krail.core.eventbus.EventBusModule;
import uk.q3c.krail.core.guice.uiscope.UIScopeModule;
import uk.q3c.krail.core.guice.vsscope.VaadinSessionScopeModule;
import uk.q3c.krail.core.shiro.DefaultShiroModule;
import uk.q3c.krail.i18n.DefaultLocale;
import uk.q3c.krail.i18n.I18NModule;
import uk.q3c.krail.i18n.SupportedLocales;
import uk.q3c.krail.testutil.TestOptionModule;

import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Error in locale string, defaultLocale
 */
@RunWith(MycilaJunitRunner.class)
@GuiceContext({EventBusModule.class, VaadinSessionScopeModule.class, TestOptionModule.class, UIScopeModule.class, DefaultShiroModule.class})
public class I18NModuleTest4 {

    @Inject
    @DefaultLocale
    Locale locale;

    @Inject
    @SupportedLocales
    Set<Locale> supportedLocales;

    Exception thrownException;


    @Test
    public void invalid_locale_string() {
        //given

        //when

        //then
        assertThat(thrownException).isInstanceOf(IllegalArgumentException.class);
    }

    @ModuleProvider
    protected AbstractModule moduleProvider() {
        try {
            return new I18NModule().defaultLocale("rubbish");
        } catch (Exception e) {
            thrownException = e;
        }
        return new I18NModule();
    }

}