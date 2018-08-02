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
