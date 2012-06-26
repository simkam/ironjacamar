/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008-2009, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.jca.core.workmanager.spec.chapter11.common;

import org.jboss.jca.core.workmanager.spec.chapter10.common.NestCharWork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextProvider;

/**
 * UniversalProviderWork allows to add contexts
 * 
 * @author <a href="mailto:vrastsel@redhat.com">Vladimir Rastseluev</a>
 * @version $Rev$ $Date$
 *
 */
public class NestProviderWork extends NestCharWork implements WorkContextProvider
{
   private static final long serialVersionUID = 374498650817259221L;

   private List<WorkContext> ctxs;

   /**
    * Constructor.
    * @param name this class name
    * @param start Latch when enter run method
    * @param done Latch when leave run method
    */
   public NestProviderWork(String name, CountDownLatch start, CountDownLatch done)
   {
      super(name, start, done);
   }

   /**
    * Gets an instance of <code>WorkContexts</code> that needs to be used
    * by the <code>WorkManager</code> to set up the execution context while
    * executing a <code>Work</code> instance.
    * 
    * @return an <code>List</code> of <code>WorkContext</code> instances.
    */
   public List<WorkContext> getWorkContexts()
   {
      return ctxs;
   }

   /**
    * Adds work context to the list
    * @param wc - added work context
    */
   public void addContext(WorkContext wc)
   {
      if (ctxs == null)
         ctxs = new ArrayList<WorkContext>();
      if (wc != null)
         ctxs.add(wc);
   }
}