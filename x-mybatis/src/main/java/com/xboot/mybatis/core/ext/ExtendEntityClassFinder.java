/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xboot.mybatis.core.ext;

import com.xboot.mybatis.core.annotation.Table;
import io.mybatis.provider.defaults.DefaultEntityClassFinder;


public class ExtendEntityClassFinder extends DefaultEntityClassFinder {

  @Override
  public boolean isEntityClass(Class<?> clazz) {
    return clazz.isAnnotationPresent(Table.class) || super.isEntityClass(clazz);
  }

}
