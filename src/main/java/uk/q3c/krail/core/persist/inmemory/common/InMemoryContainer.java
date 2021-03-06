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

package uk.q3c.krail.core.persist.inmemory.common;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.util.BeanItemContainer;
import uk.q3c.krail.core.persist.cache.i18n.PatternCacheKey;
import uk.q3c.krail.core.persist.common.option.OptionEntity;
import uk.q3c.krail.core.persist.inmemory.i18n.InMemoryPatternStore;
import uk.q3c.krail.core.persist.inmemory.i18n.PatternEntity;
import uk.q3c.krail.core.persist.inmemory.option.InMemoryOptionStore;

import java.util.List;

/**
 * An extension of {@link BeanItemContainer} to handle in memory option and pattern entities only.
 * <p>
 * Created by David Sowerby on 30/06/15.
 */
public class InMemoryContainer<E> extends BeanItemContainer<E> {


    private final InMemoryOptionStore optionStore;
    private final InMemoryPatternStore patternStore;

    /**
     * {@inheritDoc}
     */
    public InMemoryContainer(Class<? super E> type, InMemoryOptionStore optionStore, InMemoryPatternStore patternStore) {
        super(type);
        this.optionStore = optionStore;
        this.patternStore = patternStore;
        if (!type.equals(PatternEntity.class) && (!type.equals(OptionEntity.class))) {
            throw new UnsupportedOperationException("Only OptionEntity or PatternEntity may be use with " + this.getClass()
                                                                                                                .getSimpleName());
        }
        refresh();
    }

    public final void refresh() {
        if (this.getBeanType()
                .equals(PatternEntity.class)) {
            refreshPatterns();
        } else {
            refreshOptions();
        }
    }

    private void refreshPatterns() {
        ImmutableMap<PatternCacheKey, String> store = patternStore.getAsMap();
        store.forEach((k, v) -> {
            PatternEntity entity = new PatternEntity(k, v);
            this.addItem(entity);
        });
    }

    private void refreshOptions() {
        this.addAll((List<E>) optionStore.asEntities());
    }
}
