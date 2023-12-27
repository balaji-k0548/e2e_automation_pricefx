package com.syscolab.qe.core.util.htmlReporting;

/**
 * This is the util class for the HTML Reporting
 * @author Sandeep Perera
 */
public class HTMLUtil {
    static String tr = "</tr>";
    static String fontdetails ="  font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;";
    static String bordercollapse ="  border-collapse: collapse;";
    static String width =  "width: 100%;";
    static String borderdetails ="  border: 1px solid #ddd;";
    static String  padding="  padding: 8px;";
    static String  paddingtop= "  padding-top: 12px;" ;
    static String  paddingbottom ="  padding-bottom: 12px;";
    static String  textalign = "  text-align: left;";
    static String  color ="  color: white;";
    static String  tabwith = "\t\t\twidth: 100%;\n";
    static String fontcontentattchment= "\t\t\tfont-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n";
    static String tabswithborder= "\t\t\tborder-collapse: collapse;\n";
    static String tboarder="\t\t\tborder: 1px solid #ddd;\n";
    static String  padding8px="\t\t\tpadding: 8px;\n";
    static String  paddingtop12px= "\t\t\tpadding-top: 12px;\n";
    static String  paddingbottom12px= "\t\t\tpadding-bottom: 12px;\n" ;
    static String  tabstextalign= "\t\t\ttext-align: left;\n";
    static String  tabscolorwhite="\t\t\tcolor: white;\n";
    static String  td = "</td>";
    static String  tdnewline="</td>\n";
    static String  tabstrnewline="\t\t</tr>\n";
    static String  tabsbracketnewline="\t\t}\n";
    static String tabs = "\t\t\t\t\t<tr>\n";
    private HTMLUtil(){}

    /**
     * This is the table block for project
     */
    public static String tableBlockForProject =" <table id=\"names\">" +
            "               <tr>"+
            "                 <th>Project</th>" +
            "                  <th>Environment</th>" +
            "                   <th>Release</th>" +
            "               </tr>" +
            "            PROJECTSTRBLOCK " +
            "            </table>";

    /**
     * This is the table row block for project
     */
    public static String trBlockForProject = " <tr>" +
            "        <td>PROJECTNAME</td>" +
            "        <td>ENVIRONMENTNAME</td>" +
            "        <td>BUILDNAME</td>" +
            tr;

    /**
     * This is the table block for count
     */
    public static String tableBlockForCount="<table id=\"summary\"> " +
            tr +
            "                  <th>Total</th>" +
            "                 <th>Passed</th>" +
            "                 <th>Failed</th>" +
            "               </tr>" +
            "               COUNTTRBLOCK" +
            "   </table> ";

    /**
     * This is the table row block for count
     */
    public static String trBlockForCount=" <tr>" +
            "        <td>TOTALTESTCASES</td>" +
            "        <td>PASSEDCOUNT</td>" +
            "        <td>FAILEDCOUNT</td>" +
            tr;

    /**
     * This is the table block for Test case
     */

    public static String tableBlockForTestCase = "<hr style=\"border: 2px solid white; background-color: white; margin-bottom: 3%;\">" +
            "<table align=\"center\" id=\"chart\" width=\"100%\" cellpadding=\"5\" , cellspacing=\"0\" style=\"border: 3px solid white; padding: 2%; border-radius: 10px\">" +
            tabs +
            "\t\t\t\t\t\t<td width=\"40%\"><h5  align=\"right\" style=\"margin:1%;\">Passed (PASSEDCOUNT) - <font color=\"green\">PASSEDPERC%</font></h5></td>\n" +
            "\t\t\t\t\t\t<td><div class=\"bar bar1\"></div></td>\n" +
            tabs +
            tabs+
            "\t\t\t\t\t\t<td width=\"40%\"><h5  align=\"right\" style=\"margin:1%;\">Uncategorized (UNCATEGORIZEDCOUNT) - <font color=\"maroon\">UNCATEGORIZEDPERC%</font></h5></td>\n" +
            "\t\t\t\t\t\t<td><div class=\"bar bar2\"></div></td>\n" +
            tabs+
            tabs +
            "\t\t\t\t\t\t<td width=\"40%\"><h5  align=\"right\" style=\"margin:1%;\">Defect (DEFECTCOUNT) - <font color=\"red\">DEFECTPERC%</font></h5></td>\n" +
            "\t\t\t\t\t\t<td><div class=\"bar bar3\"></div></td>\n" +
            tabs +
            tabs +
            "\t\t\t\t\t\t<td width=\"40%\"><h5  align=\"right\" style=\"margin:1%;\">Environment (ENVIRONMENTCOUNT) - <font color=\"orange\">ENVIRONMENTPERC%</font></h5></td>\n" +
            "\t\t\t\t\t\t<td><div class=\"bar bar4\"></div></td>\n" +
            tabs+
            tabs+
            "\t\t\t\t\t\t<td width=\"40%\"><h5  align=\"right\" style=\"margin:1%;\">Data (DATACOUNT) - <font color=\"light blue\">DATAPERC%</font></h5></td>\n" +
            "\t\t\t\t\t\t<td><div class=\"bar bar5\"></div></td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td width=\"40%\"><h5 align=\"right\" style=\"margin:1%;\">Script (SCRIPTCOUNT) - <font color=\"purple\">SCRIPTPERC%</font></h5></td>\n" +
            "\t\t\t\t\t\t<td><div class=\"bar bar6\"></div></td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t</table>"+
            "<hr style=\"border: 2px solid white; background-color: white; margin-bottom: 2%; margin-top: 2%;\">\n" +
            "\n" +
            "\t\t\t\t<table id=\"projects\">"+
            tr +
            "                   <th>Module</th> " +
            "                   <th>Feature</th> " +
            "                <th>Test Case</th>" +
            "                   <th>Status</th>" +
            "              </tr>" +
            "            TESTCASETRBLOCK" +
            "            </table>";

    /**
     * This is the table row block for Test case
     */
    public static String trBlockForTestCase = " <tr bgcolor = \"BACKGROUNDCOLORTR\">" +
            "        <td>MODULE</td>" +
            "        <td>FEATURE</td>" +
            "        <td>TESTCASE</td>" +
            "        <td bgcolor=\"BGCOLOUR\">STATUS</td>" +
            tr;


    /**
     * This is the table row block for Failed test cases
     */
    public static String failedTRBlock = "";
    /**
     * This is the table row block for Passed test cases
     */
    public static String passedTRBlock = "";

    /**
     * This is the table row block for Final Projects
     */
    public static String finalProjectsTRBlock = "";
    /**
     * This is the table row block for Final Count
     */
    public static String finalCountTRBlock ="";
    /**
     * This is the table row block for Final Test cases
     */
    public static String finalTestCaseTRBlock = "";

    /**
     * This is the field for content
     */
    public static String content = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "    <style>" +
            "body {\n" +
            "    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
            "  }" +
            "#projects {" +
            fontdetails +
            bordercollapse+
            width +
            "}" +
            "#projects td, #projects th {" +
            borderdetails+
            padding +
            "}" +
            "#projects tr:nth-child(even){background-color: #f2f2f2;}" +
            "projects.col:last-child{background: #ff9999;}" +
            "#projects tr:hover {background-color: yellow;}" +
            "#projects th {" +
            paddingtop +
            paddingbottom +
            textalign +
            "  background-color:#1EA6EC;" +
            color +
            "}" +
            "#summary {" +
            fontdetails +
            bordercollapse +
            width+
            "}" +
            "#summary td, #summary th {" +
            borderdetails +
            padding +
            "}" +
            "#summary tr:nth-child(even){background-color: #f2f2f2;}" +
            "#summary tr:hover {background-color: orange;}" +
            "#summary th {" +
            paddingtop +
            paddingbottom +
            textalign +
            "  background-color:#1EA6EC;" +
            color +
            "}" +
            "#names {" +
            fontdetails+
            bordercollapse +
            width +
            "}" +
            "#names td, #names th {" +
            borderdetails +
            padding +
            "}" +
            "#names tr:nth-child(even){background-color: #f2f2f2;}" +
            "#names tr:hover {background-color: orangered;}" +
            "#names th {" +
            paddingtop +
            paddingbottom +
            textalign +
            "  background-color:#22AF47;" +
            color +
            "}" +
            "</style>" +
            "BARCHARTCSSBLOCK" +
            "</head>" +
            "<body>" +
            "<table width=\"100%\" style=\"font-family:\"Helvetica Neue, Helvetica, Arial, sans-serif>" +
            "<tr style=\"background-color: #006099;\"><td style=\"padding: 30px;  \">" +
            "<a href=\"https://syscoqcenter.sysco.com/\"> " +
            "<img auto src=\"https://assets.jibecdn.com/prod/sysco/0.2.169/assets/logo-header.png\"  style=\"padding-top: 30px;padding-bottom: 30px; display:block; margin-left:auto;margin-right: auto\"> </a> </td> </tr>" +
            "<tr style=\"background-color: #ddd; border: 2px solid #ddd;\"><td style=\"padding: 30px;\">" +
            "   PROJECTSUMMARYTABLE" +
            "<br>" +
            "   TESTCASECOUNTSUMMARYTABLE" +
            "<br>" +
            "   TESTCASEDETAILSUMMARYTABLE" +
            "    <br/>" +
            "    <br/> </td> </tr> " +
            "<tr style=\"background-color: #979797; border: 2px solid #ddd;\"><td style=\"padding: 20px;color: #ffffff;font-size: medium; text-align: center;\"> Powered By SYSCO QE Team </td> </tr>" +
            "</table>" +
            "</body>" +
            "</html>";
    /**
     * This is the block for Css Bar Chart
     */
    public static String cssBarChartBlock = "<style>\n" +
            ".bar {\n" +
            "\t\t\theight: 20px;\n" +
            "\t\t\tmax-width: 100%;\n" +
            "\t\t\tline-height: 20px;\n" +
            "\t\t\tdisplay: block;\n" +
            tabwith +
            "\t\t\tfloat: left;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t.bar1 {\n" +
            "\t\t\tmax-width: DONUTPASSED%;\n" +
            "\t\t\tbackground-color: green;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t.bar2 {\n" +
            "\t\t\tmax-width: DONUTUNCATEGORIZED%;\n" +
            "\t\t\tbackground-color: maroon;\n" +
            tabsbracketnewline+
            "\n" +
            "\t\t.bar3 {\n" +
            "\t\t\tmax-width: DONUTDEFECT%;\n" +
            "\t\t\tbackground-color: red;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t.bar4 {\n" +
            "\t\t\tmax-width: DONUTENVIRONMENT%;\n" +
            "\t\t\tbackground-color: orange;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t.bar5 {\n" +
            "\t\t\tmax-width: DONUTDATA%;\n" +
            "\t\t\tbackground-color: blue;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t.bar6 {\n" +
            "\t\t\tmax-width: DONUTSCRIPT%;\n" +
            "\t\t\tbackground-color: purple;\n" +
            "\t\t}"+
            "#chart td{\n" +
            "\t\t\tborder:none;\n" +
            "\t\t}"+
            " </style>";




    //////////////////////////Summary Report With Errors/////////////////////////////////////////////////////////////////////
    /**
     * This is the content attachment if the summary report contains errors
     */
    public static String contentAttachment = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "\t<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
            "\n" +
            "\t<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>\n" +
            "\t<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n" +
            "\n" +
            "\t<link href=\"https://gitcdn.github.io/bootstrap-toggle/2.2.0/css/bootstrap-toggle.min.css\" rel=\"stylesheet\">\n" +
            "\t<script src=\"https://gitcdn.github.io/bootstrap-toggle/2.2.0/js/bootstrap-toggle.min.js\"></script>\n" +
            "<script src=\"https://www.amcharts.com/lib/4/core.js\"></script>\n" +
            "    <script src=\"https://www.amcharts.com/lib/4/charts.js\"></script>\n" +
            "    <script src=\"https://www.amcharts.com/lib/4/themes/animated.js\"></script>"+
            "\t<style>\n" +
            "\t\tbody {\n" +
            fontcontentattchment +
            "\t\t\tcolor: black;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#projects {\n" +
            fontcontentattchment +
            tabswithborder +
            tabwith +
            tabsbracketnewline +
            "\n" +
            "\t\t#projects td,\n" +
            "\t\t#projects th {\n" +
            tboarder +
            padding8px+
            tabsbracketnewline +
            "\n" +
            "\t\tprojects.col:last-child {\n" +
            "\t\t\tbackground: #ff9999;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#projects tr:hover {\n" +
            "\t\t\tbackground-color: yellow;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#projects th {\n" +
            paddingtop12px +
            paddingbottom12px+
            tabstextalign +
            "\t\t\tbackground-color: #1EA6EC;\n" +
            tabscolorwhite+
            tabsbracketnewline +
            "\n" +
            "\t\t#summary {\n" +
            fontcontentattchment +
            tabswithborder +
            tabwith +
            tabsbracketnewline +
            "\n" +
            "\t\t#summary td,\n" +
            "\t\t#summary th {\n" +
            tboarder +
            padding8px +
            tabsbracketnewline +
            "\n" +
            "\t\t#summary tr:nth-child(even) {\n" +
            "\t\t\tbackground-color: #f2f2f2;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#summary tr:hover {\n" +
            "\t\t\tbackground-color: orange;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#summary th {\n" +
            paddingtop12px+
            paddingbottom12px +
            tabstextalign +
            "\t\t\tbackground-color: #1EA6EC;\n" +
            tabscolorwhite +
            tabsbracketnewline +
            "\n" +
            "\t\t#names {\n" +
            "\t\t\tfont-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
            "\t\t\tborder-collapse: collapse;\n" +
            "\t\t\twidth: 100%;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#names td,\n" +
            "\t\t#names th {\n" +
            "\t\t\tborder: 1px solid #ddd;\n" +
            "\t\t\tpadding: 8px;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#names tr:nth-child(even) {\n" +
            "\t\t\tbackground-color: #f2f2f2;\n" +
            tabsbracketnewline +
            "\n" +
            "\t\t#names tr:hover {\n" +
            "\t\t\tbackground-color: orangered;\n" +
            tabsbracketnewline+
            "\n" +
            "\t\t#names th {\n" +
            "\t\t\tpadding-top: 12px;\n" +
            "\t\t\tpadding-bottom: 12px;\n" +
            "\t\t\ttext-align: left;\n" +
            "\t\t\tbackground-color: #22AF47;\n" +
            "\t\t\tcolor: white;\n" +
            tabsbracketnewline +
            "\n" +
            "#searchBar {\n" +
            "\t\t\tmargin:2%;\n" +
            "\t\t}\n" +
            "\n" +
            "        #testCaseController{\n" +
            "            margin-bottom: 1%;\n" +
            "        }"+
            "#chartdiv {\n" +
            "            width: 100%;\n" +
            "            height: 600px;\n" +
            "        }"+
            "\t</style>\n" +
            "</head>\n" +
            "\n" +
            "<body onload=\"javascript:showFailedTests()\">\n" +
            "\t<table width=\"100%\" style=\"font-family: Helvetica Neue, Helvetica, Arial, sans-serif\">\n" +
            "\t\t<tr style=\"background-color: #006099;\">\n" +
            "\t\t\t<td style=\"padding: 30px;  \"><a href=\"https://syscoqcenter.sysco.com/\"> <img auto\n" +
            "\t\t\t\t\t\tsrc=\"https://assets.jibecdn.com/prod/sysco/0.2.169/assets/logo-header.png\"\n"+
            "\t\t\t\t\t\tstyle=\"padding-top: 30px;padding-bottom: 30px; display:block; margin-left:auto;margin-right: auto\">\n" +
            "\t\t\t\t</a> </td>\n" +
            tabstrnewline +
            "\t\t<tr style=\"background-color: #e2e1d9; border: 2px solid #ddd;\">\n" +
            "\t\t\t<td style=\"padding: 30px;\">" +
            " PROJECTSUMMARYTABLE<br> TESTCASECOUNTSUMMARYTABLE<br>TESTCASEDETAILSUMMARYTABLE " +
            " <br />\n" +
            "\t\t\t\t<br />\n" +
            "\t\t\t</td>\n" +
            tabstrnewline +
            "\t\t<tr style=\"background-color: #979797; border: 2px solid #ddd;\">\n" +
            "\t\t\t<td style=\"padding: 20px;color: #ffffff;font-size: medium; text-align: center;\"> Powered By SYSCO QE Team\n" +
            "\t\t\t</td>\n" +
            tabstrnewline  +
            "\t</table>" +
            " JAVASCRIPTCODE " +
            "</body>\n" +
            "\n" +
            "</html>";

    /**
     * This is the table row block for Test Case attachment if the summary report contains errors
     */
    public static String tableBlockForTestCaseAttachment = "<table id=\"projects\"> " +
            tr +
            "                   <th>Module</th> " +
            "                   <th>Feature</th> " +
            "                <th>Test Case</th>" +
            "                   <th>Status</th>" +
            "<tbody id=\"myTable\">" +
            "              </tr>" +
            "            TESTCASETRBLOCK" +
            "</tbody>\n" +
            "\t\t\t\t</table>";

    /**
     * This is the block for JavaScript
     */
    public static String blockForJavaScript = "<script>\n" +
            "\n" +
            "        function search() {\n" +
            "            $(document).ready(function () {\n" +
            "                $(\"#searchBar\").on(\"keyup\", function () {\n" +
            "                    var value = $(this).val().toLowerCase();\n" +
            "                    $(\"#myTable tr.srchRes\").filter(function () {\n" +
            "                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)\n" +
            "                    });\n" +
            "                });\n" +
            "            });\n" +
            "        }\n" +
            "\n" +
            "function showFailedTests() {\n" +
            "            var table, tr, td, i;\n" +
            "            var errorRow = document.getElementsByClassName(\"collapse\");\n" +
            "\n" +
            "            table = document.getElementById(\"myTable\");\n" +
            "            tr = table.getElementsByClassName(\"srchRes\");\n" +
            "\n" +
            "            var checkBoxFailed = document.getElementById(\"testCasesFailed\");\n" +
            "            var checkBoxPassed = document.getElementById(\"testCasesPassed\");\n" +
            "\n" +
            "            if (checkBoxFailed.checked == true) {\n" +
            "                for (i = 0; i < tr.length; i++) {\n" +
            "                    td = tr[i].getElementsByTagName(\"td\")[3];\n" +
            "\n" +
            "                    if (td.innerHTML.toUpperCase() == \"FAILED\" || td.innerHTML.toUpperCase() == \"FAILED - (SKIPPED)\") {\n" +
            "                        tr[i].style.display = \"\";\n" +
            "                        errorRow[i].style.display = \"\";\n" +
            "                    }\n" +
            "                    else {\n" +
            "                        if (checkBoxPassed.checked == true) {\n" +
            "                            tr[i].style.display = \"\";\n" +
            "                        }\n" +
            "                        else {\n" +
            "                            tr[i].style.display = \"none\";\n" +
            "                        }\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "            else {\n" +
            "                for (i = 0; i < tr.length; i++) {\n" +
            "                    td = tr[i].getElementsByTagName(\"td\")[3];\n" +
            "\n" +
            "                    if (td.innerHTML.toUpperCase() == \"FAILED\" || td.innerHTML.toUpperCase() == \"FAILED - (SKIPPED)\") {\n" +
            "                        tr[i].style.display = \"none\";\n" +
            "                        errorRow[i].style.display = \"none\";\n" +
            "                    }\n" +
            "                    else {\n" +
            "                        if (checkBoxPassed.checked == true) {\n" +
            "                            tr[i].style.display = \"\";\n" +
            "                        }\n" +
            "                        else {\n" +
            "                            tr[i].style.display = \"none\";\n" +
            "                        }\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }" +
            "        function showPassedTests() {\n" +
            "            var table, tr, td, i;\n" +
            "\n" +
            "            table = document.getElementById(\"myTable\");\n" +
            "            tr = table.getElementsByClassName(\"srchRes\");\n" +
            "\n" +
            "            var checkBoxFailed = document.getElementById(\"testCasesFailed\");\n" +
            "            var checkBoxPassed = document.getElementById(\"testCasesPassed\");\n" +
            "\n" +
            "            if (checkBoxPassed.checked == true) {\n" +
            "                for (i = 0; i < tr.length; i++) {\n" +
            "                    td = tr[i].getElementsByTagName(\"td\")[3];\n" +
            "\n" +
            "                    if (td.innerHTML.toUpperCase() == \"FAILED\" || td.innerHTML.toUpperCase() == \"FAILED - (SKIPPED)\") {\n" +
            "                        if (checkBoxFailed.checked == true) {\n" +
            "                            tr[i].style.display = \"\";\n" +
            "                        }\n" +
            "                        else {\n" +
            "                            tr[i].style.display = \"none\";\n" +
            "                        }\n" +
            "                    }\n" +
            "                    else {\n" +
            "                        tr[i].style.display = \"\";\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "            else {\n" +
            "                for (i = 0; i < tr.length; i++) {\n" +
            "                    td = tr[i].getElementsByTagName(\"td\")[3];\n" +
            "\n" +
            "                    if (td.innerHTML.toUpperCase() == \"FAILED\" || td.innerHTML.toUpperCase() == \"FAILED - (SKIPPED)\") {\n" +
            "                        if (checkBoxFailed.checked == true) {\n" +
            "                            tr[i].style.display = \"\";\n" +
            "                        }\n" +
            "                        else {\n" +
            "                            tr[i].style.display = \"none\";\n" +
            "                        }\n" +
            "                    }\n" +
            "                    else {\n" +
            "                        tr[i].style.display = \"none\";\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        function turnOnFailed() {\n" +
            "            $('#testCasesFailed').bootstrapToggle('on');\n" +
            "        }\n" +
            "\n" +
            "        function turnOnPassed() {\n" +
            "            $('#testCasesPassed').bootstrapToggle('on');\n" +
            "        }\n" +
            "\n" +
            "        function clearText() {\n" +
            "            document.getElementById(\"searchBar\").value = '';\n" +
            "            turnOnFailed();\n" +
            "            turnOnPassed();\n" +
            "        }\n" +
            "\n" +
            "    </script>" +
            "DONUTSCRIPTBLOCK";
    /**
     * This is the block of donut script
     */

    public static String donutScriptBlock = "<script>\n" +
            "        am4core.ready(function () {\n" +
            "\n" +
            "            // Themes begin\n" +
            "            am4core.useTheme(am4themes_animated);\n" +
            "            // Themes end\n" +
            "\n" +
            "            // Create chart instance\n" +
            "            var chart = am4core.create(\"chartdiv\", am4charts.PieChart);\n" +
            "\n" +
            "            // Add data\n" +
            "            chart.data = [{\n" +
            "                \"category\": \"DONUTPASSED (DONUTPASSEDCOUNT)\",\n" +
            "                \"percentage\": DONUTPASSEDCOUNT\n" +
            "            }, {\n" +
            "                \"category\": \"DONUTUNCATEGORIZED (DONUTUNCATEGORIZEDCOUNT)\",\n" +
            "                \"percentage\": DONUTUNCATEGORIZEDCOUNT\n" +
            "            }, {\n" +
            "                \"category\": \"DONUTDEFECT (DONUTDEFECTCOUNT)\",\n" +
            "                \"percentage\": DONUTDEFECTCOUNT\n" +
            "            }, {\n" +
            "                \"category\": \"DONUTENVIRONMENT (DONUTENVIRONMENTCOUNT)\",\n" +
            "                \"percentage\": DONUTENVIRONMENTCOUNT\n" +
            "            }, {\n" +
            "                \"category\": \"DONUTDATA (DONUTDATACOUNT)\",\n" +
            "                \"percentage\": DONUTDATACOUNT\n" +
            "            }, {\n" +
            "                \"category\": \"DONUTSCRIPT (DONUTSCRIPTCOUNT)\",\n" +
            "                \"percentage\": DONUTSCRIPTCOUNT\n" +
            "            }];\n" +
            "\n" +
            "            // Add and configure Series\n" +
            "            var pieSeries = chart.series.push(new am4charts.PieSeries());\n" +
            "            pieSeries.dataFields.value = \"percentage\";\n" +
            "            pieSeries.dataFields.category = \"category\";\n" +
            "            pieSeries.innerRadius = am4core.percent(50);\n" +
            "            pieSeries.ticks.template.disabled = true;\n" +
            "            pieSeries.labels.template.disabled = true;\n" +
            "\n" +
            "            var rgm = new am4core.RadialGradientModifier();\n" +
            "            rgm.brightnesses.push(-0.8, -0.8, -0.5, 0, - 0.5);\n" +
            "            pieSeries.slices.template.fillModifier = rgm;\n" +
            "            pieSeries.slices.template.strokeModifier = rgm;\n" +
            "            pieSeries.slices.template.strokeOpacity = 0.4;\n" +
            "            pieSeries.slices.template.strokeWidth = 0;\n" +
            "\n" +
            "            var colorSet = new am4core.ColorSet();\n" +
            "            colorSet.list = [\"#32CD32\", \"#800000\", \"#ff0000\", \"#ffa500\", \"#cccc00\", \"#ffff00\"].map(function (color) {\n" +
            "                return new am4core.color(color);\n" +
            "            });\n" +
            "            pieSeries.colors = colorSet;\n" +
            "\n" +
            "            chart.legend = new am4charts.Legend();\n" +
            "            chart.legend.position = \"center\";\n" +
            "            chart.legend.size = 20;\n" +
            "\n" +
            "        }); // end am4core.ready()\n" +
            "    </script>";

    /**
     * This is the table row block fot test case attachment
     */
    public static String trBlockForTestCaseAttachment = " <tr BGCOLORTR class=\"srchRes\">        <td>MODULE</td>        <td>FEATURE</td>        <td>TESTCASE</td>        <td bgcolor=\"BGCOLOUR\">STATUS</td>    </tr>";

    /**
     * This is the table data for Toggle Add Icon Attachment
     */
    public static String tdDataToggleAddIconAttachment = "<a data-toggle=\"collapse\" data-target=\"#DATATARGETNAME\"> <span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\" style=\"float:right; margin-right: 2%;\"></span> </a>";

    /**
     * This is the table data for Toggle add icon row attachment
     */
    public static String trDataToggleAddIconRowAttachment = "<tr id=\"TARGETID\" class=\"collapse\">  <td colspan=\"4\" style=\"background-color: white;\">   <code> ERRORCODE  </code>  FAILURESNAPSHOT </td>   </tr>";

    /**
     * This is the field for controller attachment
     */
    public static String controllerAttachment = "<div align=\"center\">\n" +
            "                    <table id=\"testCaseController\" style=\"border: solid 3px white;\" width=\"100%\" align=\"center\">\n" +
            "                        <tr>\n" +
            "                            <td width=\"50%\">\n" +
            "                                <center>\n" +
            "                                    <input class=\"form-control\" id=\"searchBar\" onkeypress=\"search();\"\n" +
            "                                        onclick=\"clearText();\" type=\"text\" placeholder=\"Search Here....\"\n" +
            "                                        style=\"width: 70%;\">\n" +
            "                                </center>\n" +
            tdnewline+
            "\n" +
            "\n" +
            "                            <td width=\"10%\">\n" +
            "                                <center>\n" +
            "                                    <button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\"\n" +
            "                                        data-target=\".bd-example-modal-lg\">View Results as a Chart</button>\n" +
            "\n" +
            "                                    <div class=\"modal fade bd-example-modal-lg\" tabindex=\"-1\" role=\"dialog\"\n" +
            "                                        aria-labelledby=\"myLargeModalLabel\" aria-hidden=\"true\">\n" +
            "                                        <div class=\"modal-dialog modal-lg\">\n" +
            "                                            <div class=\"modal-content\">\n" +
            "                                                <div class=\"modal-header\">\n" +
            "                                                    <h3 class=\"modal-title\"><b>PROJECTNAMECHART</b>\n" +
            "                                                    </h3>\n" +
            "                                                </div>\n" +
            "                                                <div id=\"chartdiv\"></div>\n" +
            "                                                <div>\n" +
            "                                                    <h3 style=\"margin-bottom: 3%;\">Total Test Cases : <b style=\"color:blue\">TOTALTESTSCHART</b> | Failed Test\n" +
            "                                                        Cases : <b style=\"color: red;\">TOTALTESTSFAILED</b></h3>\n" +
            "                                                </div>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                </center>\n" +
            tdnewline +
            "                            <td width=\"40%\">\n" +
            "                                <div align=\"center\">\n" +
            "                                    <span style=\"font-size: 18px;\">Show : &nbsp&nbsp</span>\n" +
            "                                    <input type=\"checkbox\" id=\"testCasesFailed\" checked data-toggle=\"toggle\"\n" +
            "                                        data-on=\"Failed\" data-off=\"View Failed\" data-onstyle=\"danger\"\n" +
            "                                        onchange=\"showFailedTests()\">\n" +
            "\n" +
            "                                    &nbsp&nbsp&nbsp&nbsp\n" +
            "                                    <input type=\"checkbox\" id=\"testCasesPassed\" data-toggle=\"toggle\" data-on=\"Passed\"\n" +
            "                                        data-off=\"View Passed\" data-onstyle=\"success\" onchange=\"showPassedTests()\"\n" +
            "                                        </div>\n" +
            "                            </td>\n" +
            "                        </tr>\n" +
            "                    </table>\n" +
            "                </div>";

    /**
     * This is for the block of snapshot
     */
    public static String snapshotBlock = "<div align=\"center\" style=\"margin-top: 3%; margin-bottom: 3%;\">\n" +
            "                    <h2 align=\"center\"><b>SNAPSHOT</b></h2>\n" +
            "                    <img src=\"https://syscoqcenter.sysco.com:3000/images/SNAPSHOTID\" class=\"img-fluid\" width=\"60%\" height=\"40%\">\n" +
            "                </div>";

    /**
     * This is for the Table row Block for Failed test case attachment
     */
    public static String failedTestCaseTRBBlockAttachment = "";
    /**
     * This is for the Table row Block for Passed test case attachment
     */
    public static String passedTestCaseTRBBlockAttachment = "";
    /**
     * This is for the Table row Block for Final test case attachment
     */
    public static String finalTestCaseTRBlockAttachment = "";

}