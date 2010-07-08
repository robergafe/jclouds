/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.vcloud.binders;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.jclouds.vcloud.reference.VCloudConstants.PROPERTY_VCLOUD_DEFAULT_NETWORK;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Properties;

import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.util.Utils;
import org.jclouds.vcloud.VCloudPropertiesBuilder;
import org.jclouds.vcloud.domain.FenceMode;
import org.jclouds.vcloud.options.InstantiateVAppTemplateOptions;
import org.testng.annotations.Test;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
 * Tests behavior of {@code BindInstantiateVAppTemplateParamsToXmlPayload}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "vcloud.BindInstantiateVAppTemplateParamsToXmlPayloadTest")
public class BindInstantiateVAppTemplateParamsToXmlPayloadTest {
   Injector injector = Guice.createInjector(new AbstractModule() {

      @Override
      protected void configure() {
         Properties props = new Properties();
         props
                  .put(PROPERTY_VCLOUD_DEFAULT_NETWORK,
                           "https://vcloud.safesecureweb.com/network/1990");
         Names.bindProperties(binder(), checkNotNull(new VCloudPropertiesBuilder(props).build(),
                  "properties"));
      }
   });

   public void testDefault() throws IOException {
      String expected = Utils.toStringAndClose(getClass().getResourceAsStream(
               "/newvapp-hosting.xml"));
      GeneratedHttpRequest<?> request = createMock(GeneratedHttpRequest.class);
      expect(request.getEndpoint()).andReturn(URI.create("http://localhost/key")).anyTimes();
      expect(request.getArgs()).andReturn(new Object[] {}).atLeastOnce();
      request.setPayload(expected);
      replay(request);

      BindInstantiateVAppTemplateParamsToXmlPayload binder = injector
               .getInstance(BindInstantiateVAppTemplateParamsToXmlPayload.class);

      Map<String, String> map = Maps.newHashMap();
      map.put("name", "my-vapp");
      map.put("template", "https://vcloud.safesecureweb.com/api/v0.8/vAppTemplate/3");
      binder.bindToRequest(request, map);
      verify(request);

   }

   public void testWithProcessorMemoryDisk() throws IOException {
      InstantiateVAppTemplateOptions options = new InstantiateVAppTemplateOptions();
      options.processorCount(1).memory(512).disk(1024);

      String expected = Utils.toStringAndClose(getClass().getResourceAsStream(
               "/newvapp-hostingcpumemdisk.xml"));
      GeneratedHttpRequest<?> request = createMock(GeneratedHttpRequest.class);
      expect(request.getEndpoint()).andReturn(URI.create("http://localhost/key")).anyTimes();
      expect(request.getArgs()).andReturn(new Object[] { options }).atLeastOnce();
      request.setPayload(expected);
      replay(request);

      BindInstantiateVAppTemplateParamsToXmlPayload binder = injector
               .getInstance(BindInstantiateVAppTemplateParamsToXmlPayload.class);

      Map<String, String> map = Maps.newHashMap();
      map.put("name", "my-vapp");
      map.put("template", "https://vcloud.safesecureweb.com/api/v0.8/vAppTemplate/3");
      map.put("network", "https://vcloud.safesecureweb.com/network/1990");

      binder.bindToRequest(request, map);
      verify(request);

   }

   public void testWithNetworkNameDhcpFenceMode() throws IOException {
      String expected = Utils.toStringAndClose(getClass().getResourceAsStream(
               "/newvapp-hostingnetworknamedhcpfencemode.xml"));

      GeneratedHttpRequest<?> request = createMock(GeneratedHttpRequest.class);
      expect(request.getEndpoint()).andReturn(URI.create("http://localhost/key")).anyTimes();
      expect(request.getArgs()).andReturn(new Object[] {}).atLeastOnce();
      request.setPayload(expected);
      replay(request);

      BindInstantiateVAppTemplateParamsToXmlPayload binder = injector
               .getInstance(BindInstantiateVAppTemplateParamsToXmlPayload.class);

      Map<String, String> map = Maps.newHashMap();
      map.put("name", "my-vapp");
      map.put("template", "https://vcloud.safesecureweb.com/api/v0.8/vAppTemplate/3");
      map.put("network", "https://vcloud.safesecureweb.com/network/1990");
      map.put("networkName", "aloha");
      map.put("fenceMode", FenceMode.BRIDGED);

      binder.bindToRequest(request, map);
      verify(request);

   }
}
