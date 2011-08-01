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

package org.apache.commons.configuration2.converter;

import org.apache.commons.configuration2.ConversionException;

/**
 * Double converter.
 * 
 * @author Emmanuel Bourg
 * @version $Revision$, $Date$
 * @since 2.0
 */
class DoubleConverter extends NumberConverter<Double>
{
    private static final TypeConverter instance = new DoubleConverter();

    public static TypeConverter getInstance()
    {
        return instance;
    }

    public Double convert(Object value, Object... params) throws ConversionException
    {
        Number n = toNumber(value, Double.class);
        if (n instanceof Double)
        {
            return (Double) n;
        }
        else
        {
            return n.doubleValue();
        }
    }
}
