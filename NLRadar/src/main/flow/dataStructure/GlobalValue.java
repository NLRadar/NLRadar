package dataStructure;

import java.util.ArrayList;

import soot.SootClass;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.util.Chain;

public class GlobalValue {
    // android jar path
    public static String JAR_Path ="D:\\workdir\\android\\platforms";
    // logger path
    public static String OUT_Path = "Output";
   // apk path
    public static String APK_PATH = "testapk/";
    //ICCBotOutputPath
    public static String ICCBOT_OUTPUT_PATH = "Output/ICCBotOutput";

    public static CallGraph callgraph;

    public static Chain<SootClass> sootClass;

    public static String APK_REPACKAGE_PATH = "apkrepackaged";
 
    public static String SOOT_OUTPUT_PATH = "sootOutput";

    public static ArrayList<String> SourMethods = new ArrayList<String>(){
        {
            add("<android.service.notification.NotificationListenerService: void onNotificationPosted(android.service.notification.StatusBarNotification)>");
            add("<android.service.notification.NotificationListenerService: void onNotificationRemoved(android.service.notification.StatusBarNotification)>");
            add("onNotificationPosted");
            add("onNotificationRemoved");
            //add("<android.service.notification.StatusBarNotification: android.app.Notification getNotification()>");
            //add("getNotification");
            //add("<android.app.NotificationManager: android.service.notification.StatusBarNotification[] getActiveNotifications()>");
            //add("getActiveNotifications");
            add("<android.service.notification.NotificationListenerService: android.service.notification.StatusBarNotification[] getActiveNotifications()>");
        }
    };

    public static ArrayList<String> ICCmethods = new ArrayList<String>(){
        {
            add("sendBroadcast");
            add("sendBroadcastAsUser");
            add("sendOrderedBroadcast");
            add("startActivity");
            add("startService");
            add("bindService");
            add("startForegroundService");
            add("startActivityForResult");
        }
    };

    public static ArrayList<String> SinkMethods = new ArrayList<String>(){
        {
            //network
            add("<okhttp3.OkHttpClient: okhttp3.Call newCall(okhttp3.Request)>");
            add("<okhttp3.FormBody$Builder: okhttp3.FormBody$Builder add(java.lang.String,java.lang.String)>");
            add("<okhttp3.Request$Builder: okhttp3.Request$Builder post(okhttp3.RequestBody)>");
            
            add("<java.net.URLConnection: void <init>(java.net.URL)>");
            add("<java.net.URLConnection: void setRequestProperty(java.lang.String,java.lang.String)>");
            add("<java.net.URLConnection: void addRequestProperty(java.lang.String,java.lang.String)>");
            
            add("<java.net.HttpURLConnection: void <init>(java.net.URL)>");
            add("<java.net.HttpURLConnection: void setRequestProperty(java.lang.String,java.lang.String)>");
            add("<java.net.HttpURLConnection: void addRequestProperty(java.lang.String,java.lang.String)>");
            
            add("<java.net.HttpsURLConnection: void <init>(java.net.URL)>");
            add("<java.net.HttpsURLConnection: void setRequestProperty(java.lang.String,java.lang.String)>");
            add("<java.net.HttpsURLConnection: void addRequestProperty(java.lang.String,java.lang.String)>");
            
            add("<org.apache.http.client.methods.HttpPost: void <init>(java.net.URI)>");	
            add("<org.apache.http.client.methods.HttpPost: void <init>(java.lang.String)>");	
            
            add("<java.net.URI: void <init>(java.lang.String)>");	
            add("<java.net.URL: void <init>(java.lang.String)>");	
            //execute
            add("<okhttp3.Call: okhttp3.Response execute()>");
            add("<java.net.URLConnection: void connect()>");
            add("<java.net.HttpURLConnection: void connect()>");
            add("<java.net.HttpsURLConnection: void connect()>");
            add("<org.apache.http.client.HttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>");
            add("<org.apache.http.client.HttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest,org.apache.http.protocol.HttpContext)>");

            add("<java.net.URLConnection: java.io.OutputStream getOutputStream()>");
            add("<java.net.HttpURLConnection: java.io.OutputStream getOutputStream()>");
            add("<java.net.HttpsURLConnection: java.io.OutputStream getOutputStream()>");

            //network-Volley
            add("<com.android.volley.RequestQueue: com.android.volley.RequestQueue add(com.android.volley.Request)>");
            add("<com.android.volley.RequestQueue: com.android.volley.RequestQueue add(com.android.volley.Request,java.lang.Object)>");

            //socket 
            add("<java.net.Socket: java.io.OutputStream getOutputStream()>");

            
            //storage
            add("<java.io.FileOutputStream: void write(byte[])>");
            add("<java.io.FileOutputStream: void write(byte[],int,int)>");
            add("<java.io.FileOutputStream: void write(int)>");

            add("<java.io.OutputStream: void write(byte[])>");
            add("<java.io.OutputStream: void write(int)>");

            add("<java.io.ByteArrayOutputStream: void write(int)>");
            add("<java.io.ByteArrayOutputStream: void write(byte[])>");
            add("<java.io.ByteArrayOutputStream: void write(byte[],int,int)>");
            add("java.io.BufferedOutputStream: void write(byte[])");

            add("<java.io.PrintWriter: void write(java.lang.String)>");
            add("<java.io.PrintWriter: void write(char[])>");
            add("<java.io.PrintWriter: void println(java.lang.String)>");
            add("<java.io.BufferedWriter: void write(java.lang.String)>");
            add("<java.io.BufferedWriter: void write(char[])>");

            add("<java.io.FileWriter: void write(java.lang.String)>");
            add("<java.io.FileWriter: void write(char[])>");

            add("<java.io.Writer: void write(char[])>");
            add("<java.io.Writer: void write(char[],int,int)>");
            add("<java.io.Writer: void write(int)>");
            add("<java.io.Writer: void write(java.lang.String)>");
            add("<java.io.Writer: void write(java.lang.String,int,int)>");
            add("<java.io.Writer: java.io.Writer append(java.lang.CharSequence)>");
            
            add("<java.io.OutputStreamWriter: java.io.Writer append(java.lang.CharSequence)>");
            add("<java.io.OutputStreamWriter: void write(java.lang.String)>");
            add("<java.io.OutputStreamWriter: void write(char[])>");

            add("<java.io.DataOutputStream: void writeUTF(java.lang.String)>");
		    add("<java.io.DataOutputStream: void write(byte[],int,int)>");
		    add("<java.io.DataOutputStream: void write(java.lang.String)>");
		    add("<java.io.DataOutputStream: void writeBytes(java.lang.String)>");
		    add("<java.io.DataOutputStream: void writeChars(java.lang.String)>");

            add("<java.io.FilterOutputStream: void writeUTF(java.lang.String)>");
            add("<java.io.FilterOutputStream: void write(byte[],int,int)>");
            add("<java.io.FilterOutputStream: void write(java.lang.String)>");
            add("<java.io.FilterOutputStream: void writeBytes(java.lang.String)>");
            add("<java.io.FilterOutputStream: void writeChars(java.lang.String)>");
            add("<java.io.FilterOutputStream: void write(byte[])>");

            add("<java.util.zip.ZipOutputStream: void writeUTF(java.lang.String)>");
            add("<java.util.zip.ZipOutputStream: void write(byte[],int,int)>");
            add("<java.util.zip.ZipOutputStream: void write(java.lang.String)>");
            add("<java.util.zip.ZipOutputStream: void writeBytes(java.lang.String)>");
            add("<java.util.zip.ZipOutputStream: void writeChars(java.lang.String)>");
            add("<java.util.zip.ZipOutputStream: void write(byte[])>");
            add("<java.util.zip.ZipOutputStream: void setComment(java.lang.String)>");

            add("<java.util.zip.GZIPOutputStream: void write(byte[],int,int)>");
            add("<java.util.zip.GZIPOutputStream: void writeUTF(java.lang.String)>");
            add("<java.util.zip.GZIPOutputStream: void write(java.lang.String)>");
            add("<java.util.zip.GZIPOutputStream: void writeBytes(java.lang.String)>");
            add("<java.util.zip.GZIPOutputStream: void writeChars(java.lang.String)>");
            add("<java.util.zip.GZIPOutputStream: void write(byte[])>");

            //sharedpreferences
        
            //database
            add("<android.database.sqlite.SQLiteDatabase: long insert(java.lang.String,java.lang.String,android.content.ContentValues)>");
            add("<android.database.sqlite.SQLiteDatabase: long insertOrThrow(java.lang.String,java.lang.String,android.content.ContentValues)>");
            add("<android.database.sqlite.SQLiteDatabase: long insertWithOnConflict(java.lang.String,java.lang.String,android.content.ContentValues,int)>");
            add("<android.database.sqlite.SQLiteDatabase: int update(java.lang.String,android.content.ContentValues,java.lang.String,java.lang.String[])>");
            add("<android.database.sqlite.SQLiteDatabase: int updateWithOnConflict(java.lang.String,android.content.ContentValues,java.lang.String,java.lang.String[],int)>");
            //execSQL
            add("<android.database.sqlite.SQLiteDatabase: void execSQL(java.lang.String)>");
            add("<android.database.sqlite.SQLiteDatabase: void execSQL(java.lang.String,java.lang.Object[])>");
            add("<android.database.sqlite.SQLiteDatabase: void execSQL(java.lang.String,android.database.sqlite.SQLiteDatabase$CursorFactory)>");
            //query
            // add("<android.database.sqlite.SQLiteDatabase: android.database.Cursor query(java.lang.String,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String,java.lang.String,java.lang.String)>");

            // add("<android.content.ContentResolver: android.database.Cursor query(android.net.Uri,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String)> ");
            // add("<android.content.ContentResolver: android.database.Cursor query(android.net.Uri,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String,android.os.CancellationSignal)>");
            add("<org.owasp.webgoat.session.DatabaseUtilities: java.sql.Connection getHsqldbConnection(java.lang.String,org.owasp.webgoat.session.WebgoatContext)>");

            //com.google.firebase.database.DatabaseReference
            add("<com.google.firebase.database.DatabaseReference: com.google.android.gms.tasks.Task setValue(java.lang.Object)>");
            add("<com.google.firebase.database.DatabaseReference: com.google.android.gms.tasks.Task updateChildren(java.util.Map)>");



            //行为分析
            //cancelNotification
            add("<android.service.notification.NotificationListenerService: void cancelNotification(java.lang.String, java.lang.String, int)>");
            add("<android.service.notification.NotificationListenerService: void cancelNotification(java.lang.String)>");
            add("<android.service.notification.NotificationListenerService: void cancelNotifications(java.lang.String[])>");
            add("<android.service.notification.NotificationListenerService: void cancelAllNotifications()>");

            //createNotification
            add("<android.app.NotificationManager: void notify(java.lang.String,int,android.app.Notification)>");
            add("<android.app.NotificationManager: void notify(int,android.app.Notification)>");
            add("<android.app.NotificationManager: void notify(java.lang.String,int,android.app.Notification,android.app.NotificationManager$OnNotificationPostedListener)>");
            add("<android.app.NotificationManager: void notify(int,android.app.Notification,android.app.NotificationManager$OnNotificationPostedListener)>");

            //pendingIntent
            add("<android.app.PendingIntent: void send()>");
            add("<android.app.PendingIntent: void send(int)>");
            add("<android.app.PendingIntent: void send(android.os.Bundle)>");
            add("<android.app.PendingIntent: void send(int,android.app.PendingIntent$OnFinished,android.os.Handler)>");
            add("<android.app.PendingIntent: void send(android.content.Context,int,android.content.Intent)>");
            add("<android.app.PendingIntent: void send(android.content.Context,int,android.content.Intent,android.app.PendingIntent$OnFinished,android.os.Handler)>");
            add("<android.app.PendingIntent: void send(android.content.Context,int,android.content.Intent,android.app.PendingIntent$OnFinished,android.os.Handler,java.lang.String)>");
            add("android.app.PendingIntent: void send(");

            //remoteInput addReusltstoIntent
            add("<android.app.RemoteInput: void addResultsToIntent(android.app.RemoteInput$RemoteInput[],android.content.Intent,android.os.Bundle)>");
            //RemoteInput.Builder
            add("<android.app.RemoteInput$Builder: android.app.RemoteInput build()>");
            add("<android.app.RemoteInput$Builder: android.app.RemoteInput$Builder setAllowFreeFormInput(boolean)>");

            //Notification.Action.Builder
            add("<android.app.Notification$Action$Builder: android.app.Notification$Action$Builder addRemoteInput(android.app.RemoteInput)>");

            //sharedpreferences
            add("<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putString(java.lang.String,java.lang.String)>");
            add("<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putStringSet(java.lang.String,java.util.Set)>");
            add("<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putInt(java.lang.String,int)>");
            add("<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putLong(java.lang.String,long)>");
            add("<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putBoolean(java.lang.String,boolean)>");

            //invoke
            add("<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>");
            add("<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object)>");
            add("<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object)>");
            add("<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object,java.lang.Object)>");
            add("<javax.crypto.Cipher: byte[] doFinal(byte[])>");
            add("<javax.crypto.Cipher: int doFinal(byte[], int)>");
            add("<javax.crypto.KeyGenerator: SecretKey generateKey()>");
            add("<javax.crypto.SecretKeyFactory: SecretKey generateSecret(KeySpec)>");
            add("<java.security.MessageDigest: byte[] digest(byte[])>");
            add("<java.security.MessageDigest: int digest(byte[], int, int)>");
            add("<java.security.Signature: byte[] sign()>");
            add("<java.security.Signature: boolean verify(byte[])>");
            add("<android.security.keystore.KeyGenParameterSpec.Builder: KeyGenParameterSpec.Builder setKeySize(int)>");
            add("<android.security.keystore.KeyGenParameterSpec.Builder: KeyGenParameterSpec.Builder setBlockModes(String[])>");
            add("<android.security.keystore.KeyGenParameterSpec.Builder: KeyGenParameterSpec.Builder setEncryptionPaddings(String[])>");
            add("<android.security.keystore.KeyGenParameterSpec.Builder: KeyGenParameterSpec.Builder setUserAuthenticationRequired(boolean)>");
            add("<android.security.keystore.KeyGenParameterSpec.Builder: KeyGenParameterSpec build()>");
            add("<java.security.KeyPairGenerator: KeyPair generateKeyPair()>");
            add("<java.security.KeyStore: void load(InputStream, char[])>");
            add("<java.security.KeyStore: Key getKey(String, char[])>");
            add("<android.hardware.fingerprint.FingerprintManager: void authenticate(FingerprintManager.CryptoObject, CancellationSignal, int, FingerprintManager.AuthenticationCallback, Handler)>");
            add("<android.hardware.fingerprint.FingerprintManager.CryptoObject: FingerprintManager.CryptoObject(Signature)>");
            add("<android.hardware.fingerprint.FingerprintManager.CryptoObject: FingerprintManager.CryptoObject(Cipher)>");
            add("<android.hardware.fingerprint.FingerprintManager.CryptoObject: FingerprintManager.CryptoObject(Mac)>");
        }
    };

    public static ArrayList<String> externalstorage  = new ArrayList<String>(){
        {
            add("<android.os.Environment: java.io.File getExternalStorageDirectory()>");
            add("<android.os.Environment: java.io.File getExternalStoragePublicDirectory(java.lang.String)>");
            add("<android.os.Environment: java.io.File getExternalFilesDir(java.lang.String)>");
            add("<android.os.Environment: java.io.File getExternalCacheDir()>");
            add("<android.os.Environment: java.io.File getDownloadCacheDirectory()>");
            add("/sdcard/Android/data");
        }
    };

    public static ArrayList<String> logMethods = new ArrayList<String>(){
        {
            //log
            add("<android.util.Log: int e(java.lang.String,java.lang.String)>");
            add("<android.util.Log: int e(java.lang.String,java.lang.String,java.lang.Throwable)>");
            add("<android.util.Log: int w(java.lang.String,java.lang.String)>");
            add("<android.util.Log: int w(java.lang.String,java.lang.String,java.lang.Throwable)>");
            add("<android.util.Log: int i(java.lang.String,java.lang.String)>");
            add("<android.util.Log: int i(java.lang.String,java.lang.String,java.lang.Throwable)>");
            add("<android.util.Log: int d(java.lang.String,java.lang.String)>");
            add("<android.util.Log: int d(java.lang.String,java.lang.String,java.lang.Throwable)>");
            add("<android.util.Log: int v(java.lang.String,java.lang.String)>");
            add("<android.util.Log: int v(java.lang.String,java.lang.String,java.lang.Throwable)>");
            add("<android.util.Log: int wtf(java.lang.String,java.lang.String)>");
            add("<android.util.Log: int wtf(java.lang.String,java.lang.String,java.lang.Throwable)>");
            add("<android.util.Log: int println(int,java.lang.String,java.lang.String)>");
            add("<android.util.Log: int println(int,java.lang.String,java.lang.String,java.lang.Throwable)>");
        }
    };

    public static ArrayList<String> CancelNotificationMethods = new ArrayList<String>(){
        {
            add("<android.service.notification.NotificationListenerService: void cancelNotification(java.lang.String, java.lang.String, int)>");
            add("<android.service.notification.NotificationListenerService: void cancelNotification(java.lang.String)>");
            add("<android.service.notification.NotificationListenerService: void cancelNotifications(java.lang.String[])>");
            add("<android.service.notification.NotificationListenerService: void cancelAllNotifications()>");
        }
    };

    public static ArrayList<String> createNotificationMethods = new ArrayList<String>(){
        {
            add("<android.app.NotificationManager: void notify(java.lang.String,int,android.app.Notification)>");
            add("<android.app.NotificationManager: void notify(int,android.app.Notification)>");
            add("<android.app.NotificationManager: void notify(java.lang.String,int,android.app.Notification,android.app.NotificationManager$OnNotificationPostedListener)>");
            add("<android.app.NotificationManager: void notify(int,android.app.Notification,android.app.NotificationManager$OnNotificationPostedListener)>");
        }
    };

    public static ArrayList<String> compareMethods = new ArrayList<String>(){
        {
            add("<java.lang.String: boolean equals(java.lang.Object)>");
            add("<java.lang.String: boolean equalsIgnoreCase(java.lang.String)>");
            add("<java.lang.String: boolean contentEquals(java.lang.StringBuffer)>");
            add("<java.lang.String: boolean startsWith(java.lang.String)>");
            add("<java.lang.String: boolean startsWith(java.lang.String,int)>");
            add("<java.lang.String: boolean endsWith(java.lang.String)>");
            
            add("<java.lang.String: boolean contains(java.lang.CharSequence)>");
            add("<java.lang.String: boolean contains(java.lang.CharSequence,int)>");
            add("<java.lang.String: boolean matches(java.lang.String)>");
            
            add("<java.lang.String: int indexOf(java.lang.String)>");
            add("<java.lang.String: int indexOf(java.lang.String,int)>");
            
            add("<java.lang.String: int lastIndexOf(java.lang.String)>");
            add("<java.lang.String: int lastIndexOf(java.lang.String,int)>");
            
            add("<java.lang.String: boolean isBlank()>");
            add("<java.lang.String: boolean isEmpty()>");
            add("<java.lang.String: boolean isBlank(java.lang.CharSequence)>");
            add("<java.lang.String: boolean isEmpty(java.lang.CharSequence)>");

            
            add("<android.os.Bundle: java.lang.String getString(java.lang.String)>");
            add("<android.os.Bundle: java.lang.String getString(java.lang.String,java.lang.String)>");
        }
    };

    public static  ArrayList<String> BehaviorMethods = new ArrayList<String>(){
        {
            //comparision
            add("<java.lang.String: boolean equals(java.lang.Object)>");
            add("<java.lang.String: boolean equalsIgnoreCase(java.lang.String)>");
            add("<java.lang.String: boolean contentEquals(java.lang.StringBuffer)>");
            add("<java.lang.String: boolean startsWith(java.lang.String)>");
            add("<java.lang.String: boolean startsWith(java.lang.String,int)>");
            add("<java.lang.String: boolean endsWith(java.lang.String)>");
            
            add("<java.lang.String: boolean contains(java.lang.CharSequence)>");
            add("<java.lang.String: boolean contains(java.lang.CharSequence,int)>");
            add("<java.util.List: boolean contains(java.lang.Object)>");
            add("<java.util.Collection: boolean contains(java.lang.Object)>");
            

            add("<java.lang.String: boolean matches(java.lang.String)>");

            add("<java.lang.String: int indexOf(java.lang.String)>");
            add("<java.lang.String: int indexOf(java.lang.String,int)>");
            
            add("<java.lang.String: int lastIndexOf(java.lang.String)>");
            add("<java.lang.String: int lastIndexOf(java.lang.String,int)>");

            add("<java.lang.String: boolean isBlank()>");
            add("<java.lang.String: boolean isEmpty()>");
            add("<java.lang.String: boolean isBlank(java.lang.CharSequence)>");
            add("<java.lang.String: boolean isEmpty(java.lang.CharSequence)>");
            
            add("<android.os.Bundle: java.lang.String getString(java.lang.String)>");
            add("<android.os.Bundle: java.lang.String getString(java.lang.String,java.lang.String)>");

            //modify
            add("<java.lang.String: java.lang.String replace(char,char)>");
            add("<java.lang.String: java.lang.String replace(java.lang.CharSequence,java.lang.CharSequence)>");
            add("<java.lang.String: java.lang.String replaceAll(java.lang.String,java.lang.String)>");
            add("<java.lang.String: java.lang.String replaceFirst(java.lang.String,java.lang.String)>");
            add("<java.lang.String: java.lang.String toLowerCase()>");
            add("<java.lang.String: java.lang.String toUpperCase()>");
            add("<org.json.JSONObject: java.lang.String optString(java.lang.String)>");
            add("<org.json.JSONObject: java.lang.String optString(java.lang.String,java.lang.String)>");
            add("<org.json.JSONObject: java.lang.Object opt(java.lang.String)>");
            add("<java.util.ArrayList: boolean add(java.lang.Object)>");
            add("<java.util.List: boolean add(java.lang.Object)>");
            //substring
            add("<java.lang.String: java.lang.String substring(int)>");
            add("<java.lang.String: java.lang.String substring(int,int)>");
            add("<java.lang.String: java.lang.String subSequence(int,int)>");
            //trim
            add("<java.lang.String: java.lang.String trim()>");
            //append
            add("<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)>");
            add("<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.StringBuffer)>");

        }
    };

    public static ArrayList<String> modifyMethods = new ArrayList<String>(){
        {
            add("<java.lang.String: java.lang.String replace(char,char)>");
            add("<java.lang.String: java.lang.String replace(java.lang.CharSequence,java.lang.CharSequence)>");
            add("<java.lang.String: java.lang.String replaceAll(java.lang.String,java.lang.String)>");
            add("<java.lang.String: java.lang.String replaceFirst(java.lang.String,java.lang.String)>");
            add("<org.json.JSONObject: java.lang.String optString(java.lang.String)>");
            add("<org.json.JSONObject: java.lang.String optString(java.lang.String,java.lang.String)>");
            add("<org.json.JSONObject: java.lang.Object opt(java.lang.String)>");
            add("<java.util.ArrayList: boolean add(java.lang.Object)>");
            add("<java.util.List: boolean add(java.lang.Object)>");
            //substring
            add("<java.lang.String: java.lang.String substring(int)>");
            add("<java.lang.String: java.lang.String substring(int,int)>");
            add("<java.lang.String: java.lang.String subSequence(int,int)>");
            //trim
            add("<java.lang.String: java.lang.String trim()>");
            //append
            add("<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)>");
            add("<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.StringBuffer)>");

        }
    };

    public static ArrayList<String> StatusBarNotificationMethods = new ArrayList<String>(){
        {
            add("<android.service.notification.StatusBarNotification: java.lang.String getPackageName()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getTag()>");
            add("<android.service.notification.StatusBarNotification: int getId()>");
            add("<android.service.notification.StatusBarNotification: android.app.Notification getNotification()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getGroupKey()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getKey()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getGroup()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getChannelId()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getOpPkg()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getOverrideGroupKey()>");
            add("<android.service.notification.StatusBarNotification: java.lang.String getPackageName()>");
            add("<android.service.notification.StatusBarNotification: long getPostTime()>");
            add("<android.service.notification.StatusBarNotification: int getUid()>");
            add("<android.service.notification.StatusBarNotification: android.os.UserHandle getUser()>");
            add("<android.service.notification.StatusBarNotification: int getUserId()>");
            add("<android.service.notification.StatusBarNotification: boolean isAppGroup()>");
            add("<android.service.notification.StatusBarNotification: boolean isClearable()>");
            add("<android.service.notification.StatusBarNotification: boolean isGroup()>");
            add("<android.service.notification.StatusBarNotification: boolean isOngoing()>");
            //extra
            add("<android.app.Notification: android.os.Bundle extras>");
            add("<android.app.Notification: android.os.Bundle getExtras()>");
            add("<android.app.Notification: java.lang.CharSequence tickerText>");
            add("<android.app.Notification: java.lang.CharSequence getTickerText()>");
            add("<android.app.Notification: int icon>");
            add("<android.app.Notification: int getIcon()>");
            //contentIntent
            add("<android.app.Notification$Action: android.app.PendingIntent actionIntent>");
            add("<android.app.Notification: android.app.PendingIntent contentIntent>");
            add("<android.app.Notification$Action: android.app.PendingIntent getActionIntent()>");
        }
    };

    public static ArrayList<String> tracebackmethods = new ArrayList<String>(){
        {
            add("<android.service.notification.NotificationListenerService: android.service.notification.StatusBarNotification[] getActiveNotifications()>");
            add("<android.database.sqlite.SQLiteDatabase: android.database.Cursor query(java.lang.String,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String,java.lang.String,java.lang.String)>");
            add("<java.io.BufferedReader: java.lang.String readLine()>");
            add("<java.io.BufferedReader: int read()>");
            add("<java.io.FileInputStream: int read()>");
            add("<java.io.FileInputStream: int read(byte[])>");
            add("<java.io.FileInputStream: int read(byte[],int,int)>");
        }
    };

    public ArrayList<String> getSourMethods() {
        return SourMethods;
    }

    public ArrayList<String> getSinkMethods() {
        return SinkMethods;
    }
}