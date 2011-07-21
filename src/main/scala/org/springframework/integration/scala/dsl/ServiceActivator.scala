/*
 * Copyright 2002-2011 the original author or authors.
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
package org.springframework.integration.scala.dsl
import org.springframework.util._
/**
 * @author Oleg Zhurakousky
 *
 */
class service(name:String) extends AbstractEndpoint {
  if (!StringUtils.hasText(name)){
    this.configMap.put(IntegrationComponent.name, "service-activator_" + this.hashCode)
  }
  
  
  override def toString = {
    this.configMap.get(IntegrationComponent.name).asInstanceOf[String]
  }
}
/**
 * 
 */
object service {
  
  def apply() = new service(null) with using with andPoller
  
  def withName(componentName: String) = new service(null) with using with andPoller {
    require(StringUtils.hasText(componentName))
    this.configMap.put(IntegrationComponent.name, componentName)
  }

  def using(spel: String): Composable = {
    require(StringUtils.hasText(spel))
    val s = new service(null)
    s.configMap.put(IntegrationComponent.using, spel)
    ComposableEndpoint(s)
  }
 
  def using(function: _ => _): Composable = {
    val s = new service(null)
    s.configMap.put(IntegrationComponent.using, function)
    ComposableEndpoint(s)
  }

  def withPoller(maxMessagesPerPoll: Int, fixedRate: Int) = new service(null) with using with andName {
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.maxMessagesPerPoll -> maxMessagesPerPoll, IntegrationComponent.fixedRate -> fixedRate))
  }
  def withPoller(maxMessagesPerPoll: Int, cron: String) = new service(null) with using with andName {
    require(StringUtils.hasText(cron))
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.maxMessagesPerPoll -> maxMessagesPerPoll, IntegrationComponent.cron -> cron))
  }
  def withPoller(cron: String) = new service(null) with using with andName {
    require(StringUtils.hasText(cron))
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.cron -> cron))
  }
  def withPoller(fixedRate: Int) = new service(null) with using with andName {
    require(fixedRate > 0)
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.fixedRate -> fixedRate))
  }
}