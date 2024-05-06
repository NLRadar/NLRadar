
# NLRadar

**Run Command**

To use NLRadar for static analysis, run the following command:


``` 
java -jar NLRadar.jar [apk_path] [android.jar_path]
```

Where:

- `[apk_path]` is the path to the APK file to be analyzed
- `[android.jar_path]` is the path to the `android.jar` file from the Android SDK

**Project Files**

- This repository contains the following files:
  - `NLRadar`: Source code of NLRadar
  - `NLRadar.jar`: Executable JAR file of NLRadar
  - `android.jar`: Dependency required for the analysis
  - `Effectiveness.html`: A refined version of the Effectiveness Assessment results
  - `ApkInfo`: The Package Name and SHA256 information of the test dataset

## Effectiveness Assess

<table width="2552" border="0" cellpadding="0" cellspacing="0" style="width:1914.00pt;border-collapse:collapse;table-layout:fixed;">
   <colgroup><col width="52.07" class="xl65" style="mso-width-source:userset;mso-width-alt:1666;">
   <col width="231.80" class="xl65" style="mso-width-source:userset;mso-width-alt:7417;">
   <col width="169.33" class="xl65" style="mso-width-source:userset;mso-width-alt:5418;">
   <col width="111.73" class="xl65" style="mso-width-source:userset;mso-width-alt:3575;">
   <col width="110.33" class="xl65" style="mso-width-source:userset;mso-width-alt:3530;">
   <col width="63.53" class="xl65" style="mso-width-source:userset;mso-width-alt:2033;">
   <col width="61.07" class="xl65" style="mso-width-source:userset;mso-width-alt:1954;">
   <col width="62.33" class="xl65" style="mso-width-source:userset;mso-width-alt:1994;">
   <col width="101.13" class="xl65" style="mso-width-source:userset;mso-width-alt:3236;">
   <col width="63.53" class="xl65" style="mso-width-source:userset;mso-width-alt:2033;">
   <col width="70.60" class="xl65" style="mso-width-source:userset;mso-width-alt:2259;">
   <col width="48.20" class="xl65" style="mso-width-source:userset;mso-width-alt:1542;">
   <col width="176.47" class="xl65" style="mso-width-source:userset;mso-width-alt:5646;">
   <col width="105.87" class="xl65" style="mso-width-source:userset;mso-width-alt:3387;">
   <col width="119.13" class="xl65" style="mso-width-source:userset;mso-width-alt:3812;">
   <col width="63.53" class="xl65" style="mso-width-source:userset;mso-width-alt:2033;">
   <col width="79.93" class="xl65" style="mso-width-source:userset;mso-width-alt:2557;">
   <col width="64.67" class="xl65" style="mso-width-source:userset;mso-width-alt:2069;">
   <col width="98.80" class="xl65" style="mso-width-source:userset;mso-width-alt:3161;">
   <col width="63.47" class="xl65" style="mso-width-source:userset;mso-width-alt:2030;">
   <col width="68.20" class="xl65" style="mso-width-source:userset;mso-width-alt:2182;">
   <col width="48.27" class="xl65" style="mso-width-source:userset;mso-width-alt:1544;">
   <col width="74" span="234" class="xl65" style="mso-width-source:userset;mso-width-alt:2368;">
   </colgroup><tbody><tr height="20" style="height:15.00pt;">
    <td class="xl66" height="80" width="52.07" rowspan="3" style="height:60.00pt;width:39.05pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Store</td>
    <td class="xl67" width="231.80" rowspan="3" style="width:173.85pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str=""><span style="mso-spacerun:yes;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><font class="font1">Label Item</font><font class="font1"><br><br></font><font class="font1">Package Name</font></td>
    <td class="xl68" width="861.80" colspan="10" style="width:646.35pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">NLRadar</td>
    <td class="xl87" width="888.33" colspan="10" style="width:666.25pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Manually Label</td>
    <td class="xl65" width="74" style="width:55.50pt;"></td>
    <td class="xl65" width="74" style="width:55.50pt;"></td>
    <td class="xl65" width="74" style="width:55.50pt;"></td>
    <td class="xl65" width="74" style="width:55.50pt;"></td>
    <td class="xl65" width="74" style="width:55.50pt;"></td>
    <td class="xl65" width="74" style="width:55.50pt;"></td>
    <td class="xl65" width="74" style="width:55.50pt;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl69" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Filtering</td>
    <td class="xl70" colspan="4" style="border-right:none;border-bottom:.5pt solid windowtext;" x:str="">Leakage</td>
    <td class="xl88" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Operation Triggering</td>
    <td class="xl69" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Filtering</td>
    <td class="xl89" colspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Leakage</td>
    <td class="xl88" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Operation Triggering</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="40" style="height:30.00pt;">
    <td class="xl69" x:str="">Originating App</td>
    <td class="xl69" x:str="">Content</td>
    <td class="xl69" x:str="">Attributes</td>
    <td class="xl71" x:str="">Network</td>
    <td class="xl71" x:str="">Local Storage</td>
    <td class="xl71" x:str="">Database</td>
    <td class="xl90" x:str="">Sharepreference</td>
    <td class="xl88" x:str="">Dismissal</td>
    <td class="xl88" x:str="">Interaction</td>
    <td class="xl88" x:str="">Auto Reply</td>
    <td class="xl69" x:str="">Originating App</td>
    <td class="xl69" x:str="">Content</td>
    <td class="xl69" x:str="">Attributes</td>
    <td class="xl71" x:str="">Network</td>
    <td class="xl71" x:str="">Local Storage</td>
    <td class="xl71" x:str="">Database</td>
    <td class="xl71" x:str="">Sharepreference</td>
    <td class="xl88" x:str="">Dismissal</td>
    <td class="xl88" x:str="">Interaction</td>
    <td class="xl88" x:str="">Auto Reply</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl72" height="848" rowspan="25" style="height:636.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">A<font class="font4"><br></font><font class="font4">n</font><font class="font4"><br></font><font class="font4">d</font><font class="font4"><br></font><font class="font4">r</font><font class="font4"><br></font><font class="font4">o</font><font class="font4"><br></font><font class="font4">z</font><font class="font4"><br></font><font class="font4">o</font><font class="font4"><br></font><font class="font4">o</font></td>
    <td class="xl73" x:str="">eu.toneiv.ubktouch</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.dongbao.ygzj</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="22" style="height:16.50pt;">
    <td class="xl73" x:str="">com.qihoo.appstore</td>
    <td class="xl74"></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl93" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl99" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="22" style="height:16.50pt;">
    <td class="xl73" x:str="">com.android.app.notificationbar</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl95" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl100" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.alibaba.icbu.app.seller</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.loc.tracker</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.viapalm.kidcares</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.qihoo.cloudisk</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="140" style="height:105.00pt;">
    <td class="xl73" x:str="">dominapp.number</td>
    <td class="xl74" x:str="">com.whatsapp<font class="font1"><br></font><font class="font1">com.google.android.gm</font></td>
    <td class="xl74" x:str="">" @ "<font class="font1"><br></font><font class="font1">"whatsapp"</font><font class="font1"><br></font><font class="font1">"telegram"</font><font class="font1"><br></font><font class="font1">"gmail"</font><font class="font1"><br></font><font class="font1">startsWith("+")</font><font class="font1"><br></font><font class="font1">"http"</font><font class="font1"><br></font><font class="font1">"www."</font></td>
    <td class="xl74"></td>
    <td class="xl77"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl77"></td>
    <td class="xl96" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">com.whatsapp<font class="font1"><br></font><font class="font1">com.google.android.gm</font></td>
    <td class="xl74" x:str="">" @ "<font class="font1"><br></font><font class="font1">"whatsapp"</font><font class="font1"><br></font><font class="font1">"telegram"</font><font class="font1"><br></font><font class="font1">"gmail"</font><font class="font1"><br></font><font class="font1">startsWith("+")</font><font class="font1"><br></font><font class="font1">"http"</font><font class="font1"><br></font><font class="font1">"www."</font></td>
    <td class="xl74"></td>
    <td class="xl77"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl77"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="22" style="height:16.50pt;">
    <td class="xl73" x:str="">com.mdnsoft.ussddualwidget</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl97" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl97" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="40" style="height:30.00pt;">
    <td class="xl73" x:str="">rss.serv.rete.accept</td>
    <td class="xl74"></td>
    <td class="xl78" x:str="">null,code,codice<font class="font3"><br></font><font class="font3">key...</font></td>
    <td class="xl74"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl93" x:str="">★</td>
    <td class="xl97" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl78" x:str="">null,code,codice<font class="font3"><br></font><font class="font3">key...</font></td>
    <td class="xl74"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl99" x:str="">◎</td>
    <td class="xl97" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">step.tracker.stepcounter.walking</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.bjhl.xiaozao</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.eztech.portal.crm.lpd</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="22" style="height:16.50pt;">
    <td class="xl73" x:str="">com.tencent.qqpimsecure</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl95" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl101" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.yc.pedometer</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.chenfankeji.cleanmaster</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="100" style="height:75.00pt;">
    <td class="xl79" x:str="">com.zhapp.infowear</td>
    <td class="xl80" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">id</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl80" x:str="">com.tencent.qqmusic<font class="font7"><br></font><font class="font7">com.netease.cloudmusic</font><font class="font7"><br></font><font class="font7">com.kugou.android</font><font class="font7"><br></font><font class="font7">com.android.bbkmusic</font><font class="font7"><br></font><font class="font7">cn.kuwo.player</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">id</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.junruy.callfake</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">ebr.com.bancobmg.bancodigital</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">com.app.cy.fireboltt</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="80" style="height:60.00pt;">
    <td class="xl73" x:str="">com.cyk.amcallerflicker</td>
    <td class="xl78" x:str="">com.android.mms<font class="font3"><br></font><font class="font3">com.cyk.amcallerflicker</font><font class="font3"><br></font><font class="font3">android</font></td>
    <td class="xl78" x:str="">"Running"<font class="font3"><br></font><font class="font3">"Download"</font><font class="font3"><br></font><font class="font3">"Complete"</font><font class="font3"><br></font><font class="font3">……</font></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl78" x:str="">com.android.mms<font class="font3"><br></font><font class="font3">com.cyk.amcallerflicker</font><font class="font3"><br></font><font class="font3">android</font></td>
    <td class="xl78" x:str="">"Running"<font class="font3"><br></font><font class="font3">"Download"</font><font class="font3"><br></font><font class="font3">"Complete"</font><font class="font3"><br></font><font class="font3">……</font></td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl73" x:str="">com.calleridannounce.callerid.caller<font class="font1"><br></font><font class="font1">name.caller.nameannouncer.names</font><font class="font1"><br></font><font class="font1">peaker</font></td>
    <td class="xl74" x:str="">com.google.android.dialer<font class="font1"><br></font><font class="font1">messaging, mms</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">com.google.android.dialer<font class="font1"><br></font><font class="font1">messaging, mms</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl73" x:str="">pl.tronik.progress_gps</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74" x:str="">id</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74"></td>
    <td class="xl74" x:str="">id<font class="font7">, group</font></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="40" style="height:30.00pt;">
    <td class="xl79" x:str="">com.psafe.msuite</td>
    <td class="xl74"></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">key, id, posttime, isOngoing</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl77"></td>
    <td class="xl98"></td>
    <td class="xl94" x:str="">★</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl74"></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">key, id, posttime, isOngoing</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl77"></td>
    <td class="xl77"></td>
    <td class="xl94" x:str="">★</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="40" style="height:30.00pt;">
    <td class="xl81" height="1142" rowspan="25" style="height:856.50pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">M<font class="font4"><br></font><font class="font4">a</font><font class="font4"><br></font><font class="font4">l</font><font class="font4"><br></font><font class="font4">r</font><font class="font4"><br></font><font class="font4">a</font><font class="font4"><br></font><font class="font4">d</font><font class="font4"><br></font><font class="font4">a</font><font class="font4"><br></font><font class="font4">r</font></td>
    <td class="xl82" x:str="">com.comeback.myside.sms</td>
    <td class="xl80" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl80" x:str="">Telephony.Sms.getDef<font class="font7"><br></font><font class="font7">aultSmsPackage(</font><font class="font30">this</font><font class="font31">)</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.antivirus.booster.smart.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.mobile.kavass</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.patronum.android</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.note.android.gnotes</td>
    <td class="xl83" x:str="">com.whatsapp<font class="font1"><br></font><font class="font1">com.facebook.katana</font><font class="font1"><br></font><font class="font1">org.telegram.messenger</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl83" x:str="">com.whatsapp<font class="font1"><br></font><font class="font1">com.facebook.katana</font><font class="font1"><br></font><font class="font1">org.telegram.messenger</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl82" x:str="">com.billowyapps.multiaccount</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.mobile.kavass</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="40" style="height:30.00pt;">
    <td class="xl82" x:str="">com.demo.testing</td>
    <td class="xl84" x:str="">com.skype.raider<font class="font3"><br></font><font class="font3">org.telegram.messenger...</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl84" x:str="">com.skype.raider<font class="font3"><br></font><font class="font3">org.telegram.messenger...</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.update.bbm</td>
    <td class="xl84" x:str="">securitylogagent<font class="font3"><br></font><font class="font3">samsung.android</font><font class="font3"><br></font><font class="font3">android</font></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl84" x:str="">securitylogagent<font class="font3"><br></font><font class="font3">samsung.android</font><font class="font3"><br></font><font class="font3">android</font></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl82" x:str="">com.powerful.phone.android.cleaner</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="40" style="height:30.00pt;">
    <td class="xl82" x:str="">com.hawkshawspy</td>
    <td class="xl84" x:str="">com.skype.raider<font class="font3"><br></font><font class="font3">org.telegram.messenger...</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl84" x:str="">com.skype.raider<font class="font3"><br></font><font class="font3">org.telegram.messenger...</font></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="22" style="height:16.50pt;">
    <td class="xl82" x:str="">com.quranmp3ramadan.readquran</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.antivirus.guardian.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.booster.antivirus.cleaner.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.supo.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="40" style="height:30.00pt;">
    <td class="xl82" x:str="">com.building.castle.bster</td>
    <td class="xl80" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl80" x:str="">Telephony.Sms.getDef<font class="font7"><br></font><font class="font7">aultSmsPackage(</font><font class="font30">this</font><font class="font31">)</font></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65"></td>
    <td class="xl65" colspan="5" style="mso-ignore:colspan;"></td>
    <td class="xl102"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.clean.antivirus.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.supo.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.supo.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl82" x:str="">com.windowspl.launcherwith</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">mobi.boostnow</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="60" style="height:45.00pt;">
    <td class="xl82" x:str="">com.supo.security</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83" x:str="">isClearable, isOngoing, id, key, tag</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl76" x:str="">★</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl94" x:str="">★</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl82" x:str="">com.freecard.danhbaionline2017</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83" x:str="">id</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl83" x:str="">id</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl82" x:str="">com.tvn.app.smartnote</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="20" style="height:15.00pt;">
    <td class="xl82" x:str="">com.Ignite.amino.clean</td>
    <td class="xl83"></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl91" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl83"></td>
    <td class="xl74" x:str="">●</td>
    <td class="xl83"></td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl75" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl92" x:str="">◎</td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="53.33" style="height:40.00pt;mso-height-source:userset;mso-height-alt:800;">
    <td class="xl85" height="53.33" colspan="22" style="height:40.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">Note: Detailed Result for Effectiveness Assess:<span style="mso-spacerun:yes;">&nbsp; </span>For Filtering Dimension,<span style="mso-spacerun:yes;">&nbsp; </span>● Indicates the existence of a comparison, but without a specific comparison object (please check if it is empty or from self-notification). For Leakage and Operation Triggering Dimension, <font class="font32">★</font><font class="font8"> stands for presence, and </font><font class="font32">◎</font><font class="font8"> stands for absense. For a clearer display of the relevant information, please refer to the "Effectiveness.xml" file.</font></td>
    <td class="xl65" colspan="7" style="mso-ignore:colspan;"></td>
   </tr>
   <!--[if supportMisalignedColumns]-->
    <tr width="0" style="display:none;">
     <td width="52" style="width:39;"></td>
     <td width="232" style="width:174;"></td>
     <td width="169" style="width:127;"></td>
     <td width="112" style="width:84;"></td>
     <td width="110" style="width:83;"></td>
     <td width="64" style="width:48;"></td>
     <td width="61" style="width:46;"></td>
     <td width="62" style="width:47;"></td>
     <td width="101" style="width:76;"></td>
     <td width="64" style="width:48;"></td>
     <td width="71" style="width:53;"></td>
     <td width="48" style="width:36;"></td>
     <td width="176" style="width:132;"></td>
     <td width="106" style="width:79;"></td>
     <td width="119" style="width:89;"></td>
     <td width="64" style="width:48;"></td>
     <td width="80" style="width:60;"></td>
     <td width="65" style="width:49;"></td>
     <td width="99" style="width:74;"></td>
     <td width="63" style="width:48;"></td>
     <td width="68" style="width:51;"></td>
     <td width="48" style="width:36;"></td>
     <td width="74" style="width:56;"></td>
    </tr>
   <!--[endif]-->
  </tbody></table>
