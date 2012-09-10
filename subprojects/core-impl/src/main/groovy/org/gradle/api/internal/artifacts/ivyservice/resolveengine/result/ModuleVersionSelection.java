/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.internal.artifacts.ivyservice.resolveengine.result;

import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.result.ModuleVersionSelectionReason;

/**
* by Szczepan Faber, created at: 8/31/12
*/
public class ModuleVersionSelection {

    private ModuleVersionIdentifier id;
    private ModuleVersionSelectionReason selectionReason;

    public ModuleVersionSelection(ModuleVersionIdentifier id, ModuleVersionSelectionReason selectionReason) {
        assert id != null;
        assert selectionReason != null;

        this.id = id;
        this.selectionReason = selectionReason;
    }

    public ModuleVersionIdentifier getId() {
        return id;
    }

    public ModuleVersionSelectionReason getSelectionReason() {
        return selectionReason;
    }
}
