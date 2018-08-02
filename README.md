# GreenDAO-Demo
This is a demo for GreenDAO use and update. 

* 添加plugin到项目 build.gradle

      classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' // add plugin

* 添加依赖到模块 build.gradle

      implementation 'org.greenrobot:greendao:3.2.2'
     
* 添加插件到模块 build.gradle 头
      
      apply plugin: 'com.android.application'
      apply plugin: 'org.greenrobot.greendao'
      android {
   
* 添加自定义路径：
      schemaVersion--> 指定数据库schema版本号，迁移等操作会用到;
      daoPackage --> dao的包名，包名默认是entity所在的包；
      targetGenDir --> 生成数据库文件的目录;
      
      greendao {
        schemaVersion 1
        targetGenDir 'src/main/java'
      }

      dependencies {
          implementation fileTree(dir: 'libs', include: ['*.jar'])
          implementation 'com.android.support:appcompat-v7:28.0.0-alpha3'
          
* 创建自定义bean类（**使用注解，创建后make project 后会自动生成 DaoMaster DaoSession beanDao 类**）：

      @Entity
      public class UserBean {

          @Property(nameInDb = "USERNAME")
          private String userName;

          @Property(nameInDb = "USERBALANCE")
          private String userBalance;

          @Id
          @Property(nameInDb = "USERID")
          private String userId;

          @Generated
          public UserBean(String userName, String userBalance, String userId) {
              this.userName = userName;
              this.userBalance = userBalance;
              this.userId = userId;
          }

          public UserBean(){
          }

          public String getUserName() {
              return userName;
          }

          public void setUserName(String userName) {
              this.userName = userName;
          }

          public String getUserBalance() {
              return userBalance;
          }

          public void setUserBalance(String userBalance) {
              this.userBalance = userBalance;
          }

          public String getUserId() {
              return userId;
          }

          public void setUserId(String userId) {
              this.userId = userId;
          }
        }
        
* 在Application 中定义一个daosession，方便调用，并在onCreate方法中初始化：

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper key_db = new DaoMaster.DevOpenHelper(this, "key_db", null);
        SQLiteDatabase writableDatabase = key_db.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();
        
* 增 删 改 查：

      DaoSession daoSession = MyApplication.getInstance().getDaoSession();
      daoSession.insert(new UserBean("william", "110", "12"));
      
      daoSession.delete(new UserBean("william", "110", "12"));
      
      daoSession.update(new UserBean("william111", "110", "12"));
      
      UserBean load = daoSession.load(UserBean.class, "12");
      
## 数据库升级（个人理解，自己可以研究一下代码） ##

* 需要自定义一个OpenHelper类，继承DaoMaster.OpenHelper，重写onUpgrade方法。升级数据库的时候回在这个方法中进行操作。

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //MigrationHelper.migrate(db,UserKeyBeanDao.class);
        if (newVersion > oldVersion) {
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    DaoMaster.createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    DaoMaster.dropAllTables(db, ifExists);
                }
            }, UserBeanDao.class, KeyBeanDao.class);
        }
      }
      
##### 此方法中传入一个数据库对象db 和两个版本号，版本号在上面的配置文件中更改。先比较版本号，如果有更新再操作。这里用到一个MigrationHelper类，主要是用来更新操作的。 #####

* MigrationHelper 类操作更新数据库（这里只贴核心代码，具体可以自己看看）：

      public static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        printLog("【Generate temp table】start");
        generateTempTables(database, daoClasses);
        printLog("【Generate temp table】complete");

        ReCreateAllTableListener listener = weakListener.get();
        if (listener != null) {
            listener.onDropAllTables(database, true);
            printLog("【Drop all table by listener】");
            listener.onCreateAllTables(database, false);
            printLog("【Create all table by listener】");
        } else {
            dropAllTables(database, true, daoClasses);
            createAllTables(database, false, daoClasses);
        }
        printLog("【Restore data】start");
        restoreData(database, daoClasses);
        printLog("【Restore data】complete");
      }
      
##### 1、这里的weakListener是构造传入的回调。
##### 2、generateTemp方法会根据传入的daoClass生产对应的缓存数据库。
##### 3、然后判断是否有回调，如果有回调说明用户外层调用者自己操作删表和重新建表。
##### 4、方法restoreData恢复原有数据
