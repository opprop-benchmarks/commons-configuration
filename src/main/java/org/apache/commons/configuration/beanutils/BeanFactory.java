/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration.beanutils;

/**
 * <p>
 * Definition of an interface for bean factories.
 * </p>
 * <p>
 * Beans defined in configuration files are not directly created, but by so
 * called <em>bean factories</em>. This additional level of indirection
 * provides for high flexibility in the creation process. For instance one
 * implementation of this interface could be very simple and create a new
 * instance of the specified class for each invocation. A different
 * implementation could cache already created beans and ensure that always the
 * same bean of the given class will be returned - this would be an easy mean
 * for creating singleton objects.
 * </p>
 * <p>
 * The interface itself is quite simple. There is a single method for creating a
 * bean of a given class. All necessary parameters are obtained from an also
 * passed in {@link BeanDeclaration} object. It is also possible
 * (but optional) for a bean factory to declare the default class of the bean it
 * creates. Then it is not necessary to specify a bean class in the bean
 * declaration.
 * </p>
 *
 * @since 1.3
 * @author <a
 * href="http://commons.apache.org/configuration/team-list.html">Commons
 * Configuration team</a>
 * @version $Id$
 */
public interface BeanFactory
{
    /**
     * Returns a bean instance for the given class. The bean will be initialized
     * from the specified bean declaration object. It is up to a concrete
     * implementation how the bean will be created and initialized.
     *
     * @param beanClass the class for the bean
     * @param data the bean declaration object containing all data about the
     * bean to be created
     * @param param an additional parameter that may be passed by calling code;
     * it is up to a concrete implementation how this parameter is evaluated
     * @return the new bean instance (should not be <b>null</b>)
     * @throws Exception if an error occurs (the helper classes for creating
     * beans will catch this unspecific exception and wrap it in a configuration
     * exception)
     */
    Object createBean(Class<?> beanClass, BeanDeclaration data, Object param)
            throws Exception;

    /**
     * Returns the default bean class of this bean factory. If an implementation
     * here returns a non <b>null</b> value, bean declarations using this
     * factory do not need to provide the name of the bean class. In such a case
     * an instance of the default class will be created.
     *
     * @return the default class of this factory or <b>null</b> if there is
     * none
     */
    Class<?> getDefaultBeanClass();
}
