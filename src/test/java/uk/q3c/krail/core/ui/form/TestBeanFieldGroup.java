/*
 *
 *  * Copyright (c) 2016. David Sowerby
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations under the License.
 *
 */
package uk.q3c.krail.core.ui.form;

import com.google.inject.Provider;
import com.vaadin.data.Property;
import com.vaadin.ui.TextField;
import uk.q3c.krail.core.data.TestEntity;
import uk.q3c.krail.core.i18n.Caption;
import uk.q3c.krail.core.i18n.DescriptionKey;
import uk.q3c.krail.core.i18n.I18NProcessor;
import uk.q3c.krail.core.i18n.LabelKey;
import uk.q3c.krail.core.option.Option;
import uk.q3c.krail.core.validation.BeanValidator;

public class TestBeanFieldGroup extends BeanFieldGroupBase<TestEntity> {

    @Caption(caption = LabelKey.First_Name, description = DescriptionKey.Field)
    private TextField firstName;

    @Caption(caption = LabelKey.Last_Name, description = DescriptionKey.Last_Name)
    private TextField lastName;


    protected TestBeanFieldGroup(I18NProcessor i18NProcessor, Provider<BeanValidator> beanValidatorProvider, Option
            option) {
        super(i18NProcessor, beanValidatorProvider, option);
    }

    public TextField getFirstName() {
        return firstName;
    }

    public TextField getLastName() {
        return lastName;
    }

    @Override
    public void optionValueChanged(Property.ValueChangeEvent event) {

    }
}
