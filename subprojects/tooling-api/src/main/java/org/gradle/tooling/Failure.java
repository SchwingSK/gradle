/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.tooling;

import org.gradle.api.Incubating;
import org.gradle.api.Nullable;

/**
 * Represents a failure. Failures are similar to exceptions but carry less information (only a message, a description and a cause) so
 * they can be used in a wider scope than just Java compilation.
 *
 * @since 2.4
 */
@Incubating
public interface Failure {

    /**
     * Returns a short message (typically one line) for the failure.
     *
     * @return the failure message
     */
    @Nullable
    String getMessage();

    /**
     * Returns a long description of the failure. For example, a stack trace.
     *
     * @return a long description of the failure
     */
    @Nullable
    String getDescription();

    /**
     * Returns the underlying cause for this failure, if any.
     *
     * @return the cause for this failure, or {@code null} if there's no underlying failure or the cause is unknown
     */
    @Nullable
    Failure getCause();

}
