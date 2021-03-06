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

package uk.q3c.krail.core.option;

import org.apache.shiro.authz.UnauthorizedException;
import uk.q3c.krail.core.persist.cache.option.DefaultOptionCacheLoader;
import uk.q3c.krail.core.persist.cache.option.OptionCache;
import uk.q3c.krail.core.persist.cache.option.OptionCacheKey;
import uk.q3c.krail.core.persist.common.option.OptionDaoDelegate;
import uk.q3c.krail.core.shiro.SubjectIdentifier;
import uk.q3c.krail.core.shiro.SubjectProvider;
import uk.q3c.krail.core.user.profile.RankOption;
import uk.q3c.krail.core.user.profile.UserHierarchy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static uk.q3c.krail.core.user.profile.RankOption.*;

/**
 * Base implementation for {@link Option}. Uses {@link OptionCache}, which is configured to use some form of
 * persistence. All calls reference an implementation of {@link UserHierarchy}, either directly as a method
 * parameter, or by defaulting to {@link #hierarchy}.  The get() and set() default to using the highest rank
 * from {@link UserHierarchy}.  For getting or setting values at a specific hierarchyRank use the getSpecific() and
 * setSpecific() methods. The delete() method is always specific
 * <br>
 * To create a hierarchy specific implementation, simply sub-class with the alternative hierarchy injected into it.
 * <br>
 * Permission is required to execute {@link #set(OptionKey, int, Object)}, {@link #set(OptionKey, Object)} or {@link #delete(OptionKey, int)}.  Permission
 * required is represented by an instance of {@link OptionPermission}.  If permissions are required to view, these would need to be applied at the user
 * interface.<br>
 * <b>NOTE:</b> All values to and from {@link Option} are natively typed.  All values to and from {@link OptionCache}, {@link DefaultOptionCacheLoader} and
 * {@link OptionDaoDelegate} are wrapped in Optional.
 * </p>
 * Created by David Sowerby on 03/12/14.
 */

public abstract class OptionBase implements Option {

    private UserHierarchy hierarchy;
    private OptionCache optionCache;
    private SubjectIdentifier subjectIdentifier;
    private SubjectProvider subjectProvider;

    protected OptionBase(OptionCache optionCache, UserHierarchy hierarchy, SubjectProvider subjectProvider, SubjectIdentifier subjectIdentifier) {
        this.hierarchy = hierarchy;
        this.optionCache = optionCache;
        this.subjectProvider = subjectProvider;
        this.subjectIdentifier = subjectIdentifier;
    }

    @Override
    public UserHierarchy getHierarchy() {
        return hierarchy;
    }

    @Override
    public <T> void set(@Nonnull OptionKey<T> optionKey, T value) {
        set(optionKey, 0, value);
    }

    @Override
    public synchronized <T> void set(@Nonnull OptionKey<T> optionKey, int hierarchyRank, @Nonnull T value) {
        checkArgument(hierarchyRank >= 0);
        checkNotNull(optionKey);
        OptionPermission permission = new OptionPermission(OptionPermission.Action.EDIT, hierarchy, hierarchyRank, optionKey, subjectIdentifier.userId());
        if (subjectProvider.get()
                           .isPermitted(permission)) {
            optionCache.write(new OptionCacheKey<>(hierarchy, SPECIFIC_RANK, hierarchyRank, optionKey), Optional.of(value));
        } else {
            throw new UnauthorizedException();
        }
    }


    @Override
    @Nonnull
    public synchronized <T> T get(@Nonnull OptionKey<T> optionKey) {
        checkNotNull(optionKey);
        return getRankedValue(optionKey, HIGHEST_RANK);
    }

    private <T> T getRankedValue(@Nonnull OptionKey<T> optionKey, RankOption rank) {
        T defaultValue = optionKey.getDefaultValue();
        Optional<T> optionalValue = optionCache.get(Optional.of(defaultValue), new OptionCacheKey<>(hierarchy, rank, 0, optionKey));
        if (optionalValue == null) {
            return defaultValue;
        }
        if (optionalValue.isPresent()) {
            return optionalValue.get();
        } else {
            return defaultValue;
        }
    }

    @Nonnull
    @Override
    public synchronized <T> T getLowestRanked(@Nonnull OptionKey<T> optionKey) {
        checkNotNull(optionKey);
        return getRankedValue(optionKey, LOWEST_RANK);
    }


    @Nonnull
    @Override
    public synchronized <T> T getSpecificRanked(int hierarchyRank, @Nonnull OptionKey<T> optionKey) {
        checkNotNull(optionKey);
        T defaultValue = optionKey.getDefaultValue();
        Optional<T> optionalValue = optionCache.get(Optional.of(defaultValue), new OptionCacheKey(hierarchy, SPECIFIC_RANK, hierarchyRank, optionKey));

        if (optionalValue == null) {
            return defaultValue;
        }
        if (optionalValue.isPresent()) {
            return optionalValue.get();
        } else {
            return defaultValue;
        }
    }


    @Override
    @Nullable
    public <T> T delete(@Nonnull OptionKey<T> optionKey, int hierarchyRank) {
        checkArgument(hierarchyRank >= 0);
        checkNotNull(optionKey);
        OptionPermission permission = new OptionPermission(OptionPermission.Action.EDIT, hierarchy, hierarchyRank, optionKey, subjectIdentifier.userId());
        if (subjectProvider.get()
                           .isPermitted(permission)) {
            //noinspection unchecked
            return (T) optionCache.delete(new OptionCacheKey(hierarchy, SPECIFIC_RANK, hierarchyRank, optionKey));
        } else {
            throw new UnauthorizedException();
        }
    }

}
