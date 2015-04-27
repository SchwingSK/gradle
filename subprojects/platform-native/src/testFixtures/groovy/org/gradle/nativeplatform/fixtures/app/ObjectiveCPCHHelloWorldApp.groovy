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

package org.gradle.nativeplatform.fixtures.app

import org.gradle.integtests.fixtures.SourceFile

class ObjectiveCPCHHelloWorldApp extends PCHHelloWorldApp {

    @Override
    SourceFile getMainSource() {
        return sourceFile("objc", "main.m", """
            // Simple hello world app
            #include "hello.h"

            int main(int argc, const char * argv[])
            {
                Greeter* greeter = [Greeter new];
                [greeter sayHello];
                [greeter release];
                printf("%d", sum(7, 5));
                return 0;
            }
        """);
    }

    @Override
    SourceFile getLibraryHeader() {
        return getLibraryHeader("")
    }

    @Override
    SourceFile getLibraryHeader(String path) {
        return sourceFile("headers/${path}", "hello.h", """
            #ifndef HELLO_H
            #define HELLO_H
            #import <Foundation/Foundation.h>

            @interface Greeter : NSObject
                - (void)sayHello;
            @end

            int sum(int a, int b);

            #ifdef FRENCH
            #pragma message("<==== compiling bonjour.h ====>")
            #else
            #pragma message("<==== compiling hello.h ====>")
            #endif
            #endif
        """);
    }

    @Override
    TestApp getAlternate() {
        return new TestApp() {
            @Override
            SourceFile getMainSource() {
                return getAlternateMainSource()
            }

            @Override
            SourceFile getLibraryHeader() {
                return sourceFile("headers", "hello.h", """
                #ifndef HELLO_H
                #define HELLO_H
                #import <Foundation/Foundation.h>

                @interface Greeter : NSObject
                    - (void)sayHello;
                @end

                int sum(int a, int b);

                #pragma message("<==== compiling althello.h ====>")
                #endif
            """);
            }

            @Override
            List<SourceFile> getLibrarySources() {
                return getAlternateLibrarySources()
            }
        }
    }

    @Override
    List<SourceFile> getLibrarySources() {
        return getLibrarySources("")
    }

    @Override
    List<SourceFile> getLibrarySources(String path) {
        return [
                sourceFile("objc", "hello.m", """
            #import "${path}hello.h"
            #import <stdio.h>

            @implementation Greeter
            - (void) sayHello {
                NSString *helloWorld = @"${HELLO_WORLD}";
                #ifdef FRENCH
                helloWorld = @"${HELLO_WORLD_FRENCH}";
                #endif
                fprintf(stdout, "%s\\n", [helloWorld UTF8String]);
            }
            @end
        """),
                sourceFile("objc", "sum.m", """
            #import "${path}hello.h"

            int sum (int a, int b)
            {
                return a + b;
            }
        """)]
    }

    @Override
    SourceFile getSystemHeader() {
        return getSystemHeader("")
    }

    @Override
    SourceFile getSystemHeader(String path) {
        sourceFile("headers/${path}", "systemHeader.h", """
            #ifndef SYSTEMHEADER_H
            #define SYSTEMHEADER_H
            void systemCall();
            #pragma message("<==== compiling systemHeader.h ====>")
            #endif
        """)
    }

    @Override
    String getIOHeader() {
        return "stdio.h"
    }

    @Override
    SourceFile getAlternateMainSource() {
        return getMainSource()
    }

    @Override
    String getAlternateOutput() {
        return null
    }

    @Override
    List<SourceFile> getAlternateLibrarySources() {
        return getAlternateLibrarySources()
    }

    @Override
    String getAlternateLibraryOutput() {
        return null
    }

    public String getExtraConfiguration(String binaryName = null) {
        return """
            binaries.matching { ${binaryName ? "it.name == '$binaryName'" : "true"} }.all {
                if (targetPlatform.operatingSystem.macOsX) {
                    linker.args "-framework", "Foundation"
                } else {
                    objcCompiler.args "-I/usr/include/GNUstep", "-I/usr/local/include/objc", "-fconstant-string-class=NSConstantString", "-D_NATIVE_OBJC_EXCEPTIONS"
                    linker.args "-lgnustep-base", "-lobjc"
                }
            }
        """
    }

    @Override
    List<String> getPluginList() {
        ['objective-c']
    }
}
