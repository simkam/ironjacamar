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

package org.jboss.jca.deployers.test.unit.connector10;

import org.jboss.jca.embedded.Embedded;
import org.jboss.jca.embedded.EmbeddedFactory;

import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for deploying bad resource adapter archives 
 *
 * @author <a href="mailto:vrastsel@redhat.com">Vladimir Rastseluev</a>
 * @version $Revision: $
 */
public class BadDeploymentsTestCase extends Ra10TestBase
{

   /*
    * Embedded
    */
   private static Embedded embedded;

   /**
    * Lifecycle start, before the suite is executed
    * @throws Throwable throwable exception 
    */
   @BeforeClass
   public static void beforeClass() throws Throwable
   {
      // Create and set an embedded JCA instance
      embedded = EmbeddedFactory.create();

      // Startup
      embedded.startup();
   }

   /**
    * Lifecycle stop, after the suite is executed
    * @throws Throwable throwable exception 
    */
   @AfterClass
   public static void afterClass() throws Throwable
   {
      // Shutdown embedded
      embedded.shutdown();

      // Set embedded to null
      embedded = null;
   }

   /**
    * Deploys .rar archive and tries to look up for name of connection factory
    * @param raa - archive
    * @param d - activation descriptor
    * @throws Exception in case of error
    */
   public void testDeployment(ResourceAdapterArchive raa, Descriptor d) throws Exception
   {
      log.info("///////BeforeDeployment");

      try
      {

         embedded.deploy(raa);
         if (d != null)
            embedded.deploy(d);
         log.info("///////AfterDeployment");

      }
      catch (Throwable t)
      {
         t.printStackTrace();
         throw new Exception(t);
      }
      finally
      {
         if (d != null)
            try
            {
               embedded.undeploy(d);
            }
            catch (Throwable t1)
            {
               // Ignore
            }

         try
         {
            embedded.undeploy(raa);
         }
         catch (Throwable t2)
         {
            // Ignore
         }
      }

   }

   /**
    * 
    * test archive
    * 
    * @param name of archive
    * @throws Exception in case of error
    */
   public void testArchive(String name) throws Exception
   {
      testArchiveWithIjAndRa(name, null, null);
   }

   /**
    * 
    * test archive with ironjacamar and -ra.xml descriptor activation
    * 
    * @param name of archive
    * @param ijPath path to ironjacamar file
    * @param raPath path to -ra.xml file
    * @throws Exception in case of error
    */
   public void testArchiveWithIjAndRa(String name, String ijPath, String raPath) throws Exception
   {
      ResourceAdapterArchive raa = createDeployment(name + ".rar");
      Descriptor d = null;
      if (ijPath != null)
         raa.addAsManifestResource(ijPath, "ironjacamar.xml");
      if (raPath != null)
         d = createDescriptor(raPath);
      testDeployment(raa, d);
   }

   /**
    * 
    * testRaWithWrongProperty
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testRaWithWrongProperty() throws Exception
   {
      testArchive("ra10dtdoutwrongproperty");
   }

   /**
    * 
    * testRaWithWrongPropertyType
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testRaWithWrongPropertyType() throws Exception
   {
      testArchive("ra10dtdoutwrongpropertytype");
   }

   /**
    * 
    * testRaWithWrongPropertyValue
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testRaWithWrongPropertyValue() throws Exception
   {
      testArchive("ra10dtdoutwrongpropertyvalue");
   }

   /**
    * 
    * testCFWithoutDefConstructor
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testCFWithoutDefConstructor() throws Exception
   {
      testArchive("ra10dtdoutwrongmcf1");
   }

   /**
    * 
    * testMCFWithoutEqualsMethod
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testMCFWithoutEqualsMethod() throws Exception
   {
      testArchive("ra10dtdoutwrongmcf2");
   }

   /**
    * 
    * testMCFWithoutHashCodeMethod
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testMCFWithoutHashCodeMethod() throws Exception
   {
      testArchive("ra10dtdoutwrongmcf3");
   }

   /**
    * 
    * testRaWithWrongConnectionFactoryType
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   @Ignore("JBJCA-904")
   public void testRaWithWrongConnectionFactoryType() throws Exception
   {
      testArchive("ra10dtdoutwrongconnectionfactorytype");
   }

   /**
    * 
    * testRaWithWrongConnectionFactoryImpl
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   @Ignore("JBJCA-904")
   public void testRaWithWrongConnectionFactoryImpl() throws Exception
   {
      testArchive("ra10dtdoutwrongconnectionfactoryimpl");
   }

   /**
    * 
    * testRaWithWrongConnectionType
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   @Ignore("JBJCA-904")
   public void testRaWithWrongConnectionType() throws Exception
   {
      testArchive("ra10dtdoutwrongconnectiontype");
   }

   /**
    * 
    * testRaWithWrongConnectionImpl
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   @Ignore("JBJCA-904")
   public void testRaWithWrongConnectionImpl() throws Exception
   {
      testArchive("ra10dtdoutwrongconnectionimpl");
   }

   /**
    * 
    * testIjWithWrongProperty
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testIjWithWrongProperty() throws Exception
   {
      testArchiveWithIjAndRa("ra10dtdout", "ra10dtdoutwrongproperty.rar/META-INF/ironjacamar.xml", null);
   }

   /**
    * 
    * testIjWithWrongPropertyValue
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testIjWithWrongPropertyValue() throws Exception
   {
      testArchiveWithIjAndRa("ra10dtdoutoverwrite", "ra10dtdoutwrongpropertyvalue.rar/META-INF/ironjacamar.xml", null);
   }

   /**
    * 
    * testActivationWithWrongProperty
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testActivationWithWrongProperty() throws Exception
   {
      testArchiveWithIjAndRa("ra10dtdout", null, "ra10dtdoutwrongproperty.rar/rar-ra.xml");
   }

   /**
    * 
    * testActivationWithWrongPropertyValue
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testActivationWithWrongPropertyValue() throws Exception
   {
      testArchiveWithIjAndRa("ra10dtdoutoverwrite", null, "ra10dtdoutwrongpropertyvalue.rar/rar-ra.xml");
   }

   /**
    * 
    * testActivationIJWithWrongProperty
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testActivationIJWithWrongProperty() throws Exception
   {
      testArchiveWithIjAndRa("ra10dtdout", "ra10dtdout.rar/META-INF/ironjacamar.xml",
         "ra10dtdoutwrongproperty.rar/rar-ra.xml");
   }

   /**
    * 
    * testActivationIJWithWrongPropertyValue
    * 
    * @throws Exception  in case of error
    */
   @Test(expected = Exception.class)
   public void testActivationIJWithWrongPropertyValue() throws Exception
   {
      testArchiveWithIjAndRa("ra10dtdoutoverwrite", "ra10dtdoutoverwrite.rar/META-INF/ironjacamar.xml",
         "ra10dtdoutwrongpropertyvalue.rar/rar-ra.xml");
   }
}