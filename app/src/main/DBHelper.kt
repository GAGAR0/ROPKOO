class DBHelper {
    package com.release.gfg1
     
    import android.content.ContentValues
    import android.content.Context
    import android.database.Cursor
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper
     
    class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
            SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
         
            // below is the method for creating a database by a sqlite query
            override fun onCreate(db: SQLiteDatabase) {
            // below is a sqlite query, where column names
                    // along with their data types is given
                val query = ("CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY, " +
                        NAME_COL + " TEXT," +
                        EMAIL_COL + " TEXT," +
                        PASSWORD_COL + " TEXT," +
                        AGE_COL + " INTEGER," +
                        STARTWEIGHT_COL + " FLOAT," +
                        HEIGHT_COL + " INTEGER," +
                        GENDER_COL + " TEXT," +
                        GOALSID_COL + " integer references " + TABLE_NAME2 + "(" + ID_COL + ")," +
                        PROGRESSID_COL + " integer references " + TABLE_NAME3 + "(" + ID_COL + ")" + ")")

                val query2 = ("CREATE TABLE " + TABLE_NAME2 + " (" + ID_COL + " INTEGER PRIMARY KEY, " +
                        STARTBODY_COL + " TEXT," +
                        GOALBODY_COL + " TEXT"  + ")")

                val query3 = ("CREATE TABLE " + TABLE_NAME3 + " (" + ID_COL + " INTEGER PRIMARY KEY, " +
                        DAILYCALORIES_COL + " INTEGER," +
                        STEPCOUNT_COL + " INTEGER," +
                        WATERINTAKE_COL + " INTEGER," +
                        GOALPROGRESS_COL + " INTEGER," +
                        DAILYWEIGHT_COL + " FLOAT," +
                        WEEKLYWEIGHT_COL + " FLOAT" + ")")

            // we are calling sqlite
           // method for executing our query
                db.execSQL(query)
                db.execSQL(query2)
                db.execSQL(query3)
            }
        
        override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
           // this method is to check if table already exists
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3)
            onCreate(db)
            }
        
        // This method is for adding data in our database
            fun addUser(name : String, email : String, password : String, startWeight : Float, height : Int, age : Int, gender : String){
                    // below we are creating
                    // a content values variable
                    val values = ContentValues()
             
                    // we are inserting our values
                    // in the form of key-value pair

                    values.put(NAME_COL, name)
                    values.put(EMAIL_COL, email)
                    values.put(PASSWORD_COL, password)
                    values.put(AGE_COL, age)
                    values.put(STARTWEIGHT_COL, startWeight)
                    values.put(HEIGHT_COL, height)
                    values.put(GENDER_COL, gender)

                    // here we are creating a
                    // writable variable of
                    // our database as we want to
                    // insert value in our database
                    val db = this.writableDatabase
             
                    // all values are inserted into database
                    db.insert(TABLE_NAME, null, values)
             
                    // at last we are
                    // closing our database
                    db.close()
                }

        fun addGoals(startBody : String, goalBody : String){
            // below we are creating
            // a content values variable
            val values = ContentValues()

            // we are inserting our values
            // in the form of key-value pair

            values.put(STARTBODY_COL, startBody)
            values.put(GOALBODY_COL, goalBody)

            // here we are creating a
            // writable variable of
            // our database as we want to
            // insert value in our database
            val db = this.writableDatabase

            // all values are inserted into database
            db.insert(TABLE_NAME2, null, values)

            // at last we are
            // closing our database
            db.close()
        }

        fun addProgress(dailyCalories : Int, stepCount : Int, waterIntake : Int, goalProgress : Int, dailyWeight : Float, weeklyWeight: Float){
            // below we are creating
            // a content values variable
            val values = ContentValues()

            // we are inserting our values
            // in the form of key-value pair

            values.put(DAILYCALORIES_COL, dailyCalories)
            values.put(STEPCOUNT_COL, stepCount)
            values.put(WATERINTAKE_COL, waterIntake)
            values.put(GOALPROGRESS_COL, goalProgress)
            values.put(DAILYWEIGHT_COL, dailyWeight)
            values.put(WEEKLYWEIGHT_COL, weeklyWeight)

            // here we are creating a
            // writable variable of
            // our database as we want to
            // insert value in our database
            val db = this.writableDatabase

            // all values are inserted into database
            db.insert(TABLE_NAME3, null, values)

            // at last we are
            // closing our database
            db.close()
        }

            // below method is to get
            // all data from our database
            fun getUser(): Cursor? {
                    // here we are creating a readable
                    // variable of our database
                    // as we want to read value from it
                    val db = this.readableDatabase
             
                    // below code returns a cursor to
                    // read data from the database
                    return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
                }
         
            companion object{
                    private val DATABASE_NAME = "ropDB"
                    private val DATABASE_VERSION = 1

                    val TABLE_NAME = "user"
                    val ID_COL = "id"
                    val NAME_COL = "name"
                    val EMAIL_COL = "email"
                    val PASSWORD_COL = "password"
                    val AGE_COL = "age"
                    val STARTWEIGHT_COL = "startWeight"
                    val HEIGHT_COL = "height"
                    val GENDER_COL = "gender"
                    val GOALSID_COL = "goalsId"
                    val PROGRESSID_COL = "progressId"

                    val TABLE_NAME2 = "goals"
                    val STARTBODY_COL = "startBody"
                    val GOALBODY_COL = "goalBody"

                    val TABLE_NAME3 = "progress"
                    val DAILYCALORIES_COL = "dailyCalories"
                    val STEPCOUNT_COL = "stepCount"
                    val WATERINTAKE_COL = "waterIntake"
                    val GOALPROGRESS_COL = "goalProgress"
                    val DAILYWEIGHT_COL = "dailyWeight"
                    val WEEKLYWEIGHT_COL = "weeklyWeight"
                }
    }
}