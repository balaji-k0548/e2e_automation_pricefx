package com.syscolab.qe.core.api.tests.admin;

import io.restassured.response.Response;
import com.syscolab.qe.core.api.URI;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.api.util.RequestMethods;
import com.syscolab.qe.core.reporting.SyscoLabListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by yoosufm on 6/13/17.
 */
@Listeners(SyscoLabListener.class)
public class GetProjectTest extends APITestBase {

    @BeforeClass
    /**
     * This initializes the test variables
     */
    public void initFeature(ITestContext iTestContext){
        iTestContext.setAttribute("feature", "Projects - Add Projects");

    }
    @Test
    /**
     * This gets the details of the tests
     * @throws IOException
     */
    public void testGetAllProjects() throws IOException {

        Response response = RestUtil.send(Headers.getHeader(), "", URI.retrieveProjectsURI(), RequestMethods.GET.toString());
        //System.out.println(response.asString());

    }

    @Test
    /**
     * This checks whether the test is found on not
     * @throws IOException
     */
    public void testNotFountErrorCode() throws IOException {

        Response response = RestUtil.send(Headers.getHeader(), "", URI.retrieveProjectsURI() + "invalid", RequestMethods.GET.toString());
        Assert.assertEquals(response.getStatusCode(), 404);

    }
}
