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

package uk.q3c.krail.core.ui;

import com.google.inject.Inject;
import com.vaadin.data.util.converter.ConverterFactory;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.VerticalLayout;
import net.engio.mbassy.bus.MBassador;
import uk.q3c.krail.core.eventbus.BusMessage;
import uk.q3c.krail.core.eventbus.SessionBus;
import uk.q3c.krail.core.navigate.Navigator;
import uk.q3c.krail.core.push.Broadcaster;
import uk.q3c.krail.core.push.PushMessageRouter;
import uk.q3c.krail.core.view.component.DefaultUserStatusPanel;
import uk.q3c.krail.i18n.CurrentLocale;
import uk.q3c.krail.i18n.I18NProcessor;
import uk.q3c.krail.i18n.Translate;

public class TestUI extends ScopedUI {

    @Inject
    private DefaultUserStatusPanel panel1;

    @Inject
    private DefaultUserStatusPanel panel2;

    @Inject
    public TestUI(Navigator navigator, ErrorHandler errorHandler, ConverterFactory converterFactory,
                  Broadcaster broadcaster, PushMessageRouter pushMessageRouter, ApplicationTitle applicationTitle, Translate translate, CurrentLocale
            currentLocale, I18NProcessor translator, @SessionBus MBassador<BusMessage> eventBus) {
        super(navigator, errorHandler, converterFactory, broadcaster, pushMessageRouter, applicationTitle, translate, currentLocale, translator, eventBus);

    }

    public DefaultUserStatusPanel getPanel2() {
        return panel2;
    }

    @Override
    protected AbstractOrderedLayout screenLayout() {
        return new VerticalLayout(getViewDisplayPanel());
    }

    @Override
    protected String pageTitle() {
        return "TestUI";
    }

    public DefaultUserStatusPanel getPanel1() {
        return panel1;
    }

    @Override
    protected void processBroadcastMessage(String group, String message) {

    }

}
