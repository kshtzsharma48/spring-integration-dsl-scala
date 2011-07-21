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
private[dsl] class AbstractEndpoint extends IntegrationComponent {

  private[dsl] var inputChannel: AbstractChannel = null

  private[dsl] var outputChannel: AbstractChannel = null;
  
  this.configMap.put(IntegrationComponent.name, "generatedEndpoint_" + this.hashCode)
}

/**
 * Common Traits
 */
trait using extends AbstractEndpoint {
  
  def using(spel: String): ComposableEndpoint = { 
    require(StringUtils.hasText(spel))
    this.configMap.put(IntegrationComponent.using, spel)
    new ComposableEndpoint(this)
  }
  
  def using(function: _ => _): ComposableEndpoint = { 
    this.configMap.put(IntegrationComponent.using, function)
     new ComposableEndpoint(this)
  }
}
/**
 * 
 */
trait andPoller extends AbstractEndpoint with using with andName{
  def andPoller(maxMessagesPerPoll: Int, fixedRate: Int): AbstractEndpoint with using with andName = {
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.maxMessagesPerPoll -> maxMessagesPerPoll, IntegrationComponent.fixedRate -> fixedRate))
    this
  }
  def andPoller(maxMessagesPerPoll: Int, cron: String):AbstractEndpoint with using with andName = {
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.maxMessagesPerPoll -> maxMessagesPerPoll, IntegrationComponent.cron -> cron))
    this
  }
  def andPoller(cron: String): AbstractEndpoint with using with andName =  {
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.cron -> cron))
    this
  }
  def andPoller(fixedRate: Int):AbstractEndpoint with using with andName = {
    this.configMap.put(IntegrationComponent.poller, Map(IntegrationComponent.fixedRate -> fixedRate))
    this
  }
}