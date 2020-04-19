### ROOM  
ROOM对Sqlite进行了封装，直接使用ROOM来进行数据库访问。  

**ROOM数据库查询可以直接返回LiveData对象，每次CRUD都会自动更新该LiveData，LiveData始终是数据库最新值**   

利用该特性可以直接将数据库的变化，通过LiveData作为观察者传递到ViewModel，保证拿到的数据都是**数据库中时刻最新的值**   
 
使用流程：
#### Entry类-->Dao-->EntryRoomDatabase(单例模式)-->Repository(从EntryRoomDatabase获得Dao)-->在Repository中实现异步-->AndroidViewModel通过Repository访问数据库CRUD  

***

资料：[https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..index#0](https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..index#0)


![框架](https://upload-images.jianshu.io/upload_images/19741117-bb17aec4494077b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
数据库操作不应该在MainThread中，所以我们使用AsyncTask来进行数据库操作。  
![详细设计](https://upload-images.jianshu.io/upload_images/19741117-ab02b949887b8a9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
UI层（Activity和Fragment）所有的数据应该都直接从ViewModel来获取，在ViewModel中我们使用AsyncTask来进行数据库操作。  
ROOM进行数据库操作依赖于Dao接口，在该接口中实现了很多数据库操作。  
***   
#### 主要角色  
* Entry  实体类  
* Dao  定义接口数据库操作  
* 继承RoomDatabase  通过ROOM实例化database返回Dao供调用  
* 异步类调用Dao中数据库操作如AsyncTask  
* Repository对操作进行封装  
* AndroidViewModel 聚合 Repository来执行数据库操作  

#### 使用方法  
![工程结构](https://upload-images.jianshu.io/upload_images/19741117-1018566d9ea351b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
**Entry类-->Dao-->EntryRoomDatabase(单例模式)-->Repository(从EntryRoomDatabase获得Dao)-->在Repository中实现异步-->AndroidViewModel通过Repository访问数据库CRUD**   
***  

#### 代码实现   
Entry  
```
@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "english")
    private String english;
    @ColumnInfo(name = "chinese")
    private String mean;
    public Word(String english, String mean) {
        this.english = english;
        this.mean = mean;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEnglish() {
        return english;
    }
    public void setEnglish(String english) {
        this.english = english;
    }
    public String getMean() {
        return mean;
    }
    public void setMean(String mean) {
        this.mean = mean;
    }
}
```  
Dao  
```

@Dao
public interface WordDao {
    @Insert
    void insertWord(Word... words);

    @Update
    void update(Word... words);
    @Delete
    void delete(Word... words);
    @Query("DELETE FROM WORD")
    void deleteAll();
    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAll();
}
```
WordRoomDatabase  
```

@Database(entities = {Word.class},version = 1,exportSchema = false)
public abstract  class WordRoomDatabase extends RoomDatabase {
    public abstract WordDao getWordDao();
    private static  WordRoomDatabase INSTANCE;
    public  static  WordRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
```  
Repository  
```

public class Repository {
    private LiveData<List<Word>> wordsLive;

    public LiveData<List<Word>> getWordsLive() {
        return wordsLive;
    }

    //用来调用数据库操作
    private WordDao wordDao;

    public Repository(Context context) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getInstance(context.getApplicationContext());
        wordDao = wordRoomDatabase.getWordDao();
        wordsLive = wordDao.getAll();
    }
   public   void insert(Word... words){
        new InsertTask(wordDao).execute(words);
    }
   public    void delete(Word... words){
        new DeleteTask(wordDao).execute(words);
    }
   public    void update(Word... words){
        new UpdateTask(wordDao).execute(words);
    }
   public   void deleteAll(){
        new DeleteAllTask(wordDao).execute();
    }
}
```
VeiwModel  
```  

public class MainViewModel extends AndroidViewModel {
    // ViewModel 没有很好的方法来获得一个Context 需要继承AndroidViewModel
    Repository repository;
    public MainViewModel(Application application) {
        super(application);
        this.repository = new Repository(application);
    }
  public   LiveData<List<Word>> getAll(){
        return repository.getWordsLive();
    }
   public void insert(Word... words){
        repository.insert(words);
    }
   public void update(Word... words){
        repository.update(words);
    }
   public void delete(Word... words){
        repository.delete(words);
    }
   public void deleteAll(){
        repository.deleteAll();
    }
}
```    
  

***  

### 总结  

![总结](https://upload-images.jianshu.io/upload_images/19741117-df3a4dc1c88a8602.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
