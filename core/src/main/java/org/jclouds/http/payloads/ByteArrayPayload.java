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
package org.jclouds.http.payloads;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Adrian Cole
 */
public class ByteArrayPayload extends BasePayload<byte[]> {
   public ByteArrayPayload(byte[] content) {
      this(content, null);
   }

   public ByteArrayPayload(byte[] content, byte[] md5) {
      super(content, null, new Long(checkNotNull(content, "content").length), md5);
      checkArgument(content.length >= 0, "length cannot me negative");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public InputStream getInput() {
      return new ByteArrayInputStream(content);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isRepeatable() {
      return true;
   }

}