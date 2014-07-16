/*
 * Copyright 2013 the original author or authors.
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

package org.gradle.model.internal.registry;

import org.gradle.api.Nullable;
import org.gradle.model.internal.core.ModelElement;
import org.gradle.model.internal.core.ModelPath;
import org.gradle.model.internal.core.ModelReference;
import org.gradle.model.internal.core.ModelState;
import org.gradle.model.internal.core.ModelCreationListener;
import org.gradle.model.internal.core.ModelRuleRegistrar;

public interface ModelRegistry extends ModelRuleRegistrar {

    public <T> T get(ModelReference<T> reference);

    public ModelElement element(ModelPath path);

    @Nullable // if not registered/known
    public ModelState state(ModelPath path);

    public void registerListener(ModelCreationListener listener);

    void remove(ModelPath path);
}
