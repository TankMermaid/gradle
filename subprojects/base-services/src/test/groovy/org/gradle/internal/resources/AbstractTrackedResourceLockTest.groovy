/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.internal.resources

import org.gradle.api.Action
import spock.lang.Specification


class AbstractTrackedResourceLockTest extends Specification {
    def resourceLockState = Mock(ResourceLockState)
    def coordinationService = Mock(ResourceLockCoordinationService)
    def lockAction = Mock(Action)
    def unlockAction = Mock(Action)
    def lock = new TestTrackedResourceLock("test", coordinationService, lockAction, unlockAction)

    def "tracks the lock in the current resource lock state and calls provided actions"()  {
        given:
        _ * coordinationService.current >> resourceLockState

        when:
        lock.tryLock()

        then:
        1 * lockAction.execute(lock)
        1 * resourceLockState.registerLocked(lock)

        when:
        lock.unlock()

        then:
        1 * unlockAction.execute(lock)
    }

    def "throws exception when methods are called without coordination service transform"() {
        given:
        _ * coordinationService.current >> null

        when:
        lock.tryLock()

        then:
        thrown(IllegalStateException)

        when:
        lock.unlock()

        then: thrown(IllegalStateException)

        when:
        lock.isLockedByCurrentThread()

        then:
        thrown(IllegalStateException)

        when:
        lock.isLocked()

        then:
        thrown(IllegalStateException)
    }

    def "tracks lock owner"() {
        when:
        lock.tryLock()

        then:
        lock.owner == Thread.currentThread()
        3 * coordinationService.current >> resourceLockState

        when:
        lock.unlock()

        then:
        lock.owner == null
        2 * coordinationService.current >> resourceLockState
    }
}