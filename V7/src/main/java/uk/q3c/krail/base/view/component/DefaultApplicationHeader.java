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
package uk.q3c.krail.base.view.component;

import com.google.inject.Inject;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class DefaultApplicationHeader extends Panel implements ApplicationHeader {
    private Label label;
    private HorizontalLayout layout;

    // private ComboBox comboBox;

    @Inject
    protected DefaultApplicationHeader() {
        super();
        build();
        setIds();
    }

    private void build() {
        layout = new HorizontalLayout();
        label = new Label("Application Header");
        layout.addComponent(label);

        // comboBox = new ComboBox();
        // comboBox.addItem("one");
        // comboBox.addItem("two");
        // layout.addComponent(comboBox);

        this.setContent(layout);

    }

    private void setIds() {
        // comboBox.setId(ID.getId(this, comboBox));
    }

}