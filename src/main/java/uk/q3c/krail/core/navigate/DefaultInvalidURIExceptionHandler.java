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
package uk.q3c.krail.core.navigate;

import com.google.inject.Inject;
import uk.q3c.krail.core.user.notify.UserNotifier;
import uk.q3c.krail.i18n.MessageKey;

public class DefaultInvalidURIExceptionHandler implements InvalidURIExceptionHandler {

    private final UserNotifier notifier;

    @Inject
    protected DefaultInvalidURIExceptionHandler(UserNotifier notifier) {
        super();
        this.notifier = notifier;
    }

    @Override
    public void invoke(InvalidURIException exception) {
        notifier.notifyInformation(MessageKey.Invalid_URI, exception.getTargetURI());
    }

}