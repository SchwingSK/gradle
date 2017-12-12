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

package org.gradle.integtests.tooling.r43

import org.gradle.integtests.tooling.fixture.ToolingApiVersion
import org.gradle.util.GradleVersion

@ToolingApiVersion("current")
class ToolingApiUnsupportedConsumerCrossVersionSpec extends ToolingApiVersionSpecification {
    @ToolingApiVersion("<1.2")
    def "provider rejects build request from a tooling API client older than 1.2"() {
        when:
        build()

        then:
        caughtGradleConnectionException = thrown()
        caughtGradleConnectionException.cause.message.contains('Support for clients using a tooling API version older than 2.0 was removed in Gradle 3.0. You should upgrade your tooling API client to version 3.0 or later.')
    }

    @ToolingApiVersion(">=1.2 <2.0")
    def "provider rejects build request from a tooling API client older than 2.0"() {
        when:
        build()

        then:
        caughtGradleConnectionException = thrown()
        caughtGradleConnectionException.cause.message.contains("Support for clients using a tooling API version older than 2.0 was removed in Gradle 3.0. You are currently using tooling API version ${GradleVersion.current().version}. You should upgrade your tooling API client to version 3.0 or later.")
    }

    @ToolingApiVersion("<1.2")
    def "provider rejects model request from a tooling API client older than 1.2"() {
        when:
        getModel()

        then:
        caughtGradleConnectionException = thrown()
        caughtGradleConnectionException.cause.message.contains('Support for clients using a tooling API version older than 2.0 was removed in Gradle 3.0. You should upgrade your tooling API client to version 3.0 or later.')
    }

    @ToolingApiVersion(">=1.2 <2.0")
    def "provider rejects model request from a tooling API client older than 2.0"() {
        when:
        getModel()

        then:
        caughtGradleConnectionException = thrown()
        caughtGradleConnectionException.cause.message.contains("Support for clients using a tooling API version older than 2.0 was removed in Gradle 3.0. You are currently using tooling API version ${GradleVersion.current().version}. You should upgrade your tooling API client to version 3.0 or later.")
    }

    @ToolingApiVersion(">=1.8 <2.0")
    def "provider rejects build action request from a tooling API client older than 2.0"() {
        when:
        buildAction()

        then:
        caughtGradleConnectionException = thrown()
        caughtGradleConnectionException.cause.message.contains("Support for clients using a tooling API version older than 2.0 was removed in Gradle 3.0. You are currently using tooling API version ${GradleVersion.current().version}. You should upgrade your tooling API client to version 3.0 or later.")
    }
}
