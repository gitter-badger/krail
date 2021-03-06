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

package uk.q3c.krail.core.services;

import javax.annotation.concurrent.Immutable;

/**
 * Object that captures service identity and state
 * <p>
 * Created by David Sowerby on 31/10/15.
 */
@Immutable
public class ServiceStatus {

    private final Service service;
    private final Service.State state;
    private final Service.Cause cause;

    public ServiceStatus(Service service, Service.State state, Service.Cause cause) {
        this.service = service;
        this.state = state;
        this.cause = cause;
    }

    public Service.Cause getCause() {
        return cause;
    }

    public Service getService() {
        return service;
    }

    public Service.State getState() {
        return state;
    }
}
