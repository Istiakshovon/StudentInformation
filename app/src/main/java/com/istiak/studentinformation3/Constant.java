package com.istiak.studentinformation3;

public class Constant {
    public static final String MAIN_URL =  "http://10.0.2.2";
    //others url
    public static final String SIGNUP_URL = MAIN_URL+"/student/signup.php";
    public static final String LOGIN_URL = MAIN_URL+"/student/login.php";
    public static final String SAVE_URL = MAIN_URL+"/student/save_news.php";
    public static final String GET_PROFILE = MAIN_URL+"/student/get_profile.php";
    public static final String VIEW_NEWS = MAIN_URL+"/student/view_news.php";
    public static final String DELETE_NEWS_URL = MAIN_URL+"/student/delete_news.php";
    public static final String UPDATE_PROFILE_URL = MAIN_URL+"/student/update_profile.php";
    public static final String DELETE__POFILE_URL = MAIN_URL+"/student/delete_profile.php";

    //Keys for server communications
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ROLL = "roll";
    public static final String KEY_CLASS = "Class";
    public static final String KEY_FATHER_NAME = "fatherName";
    public static final String KEY_MOTHER_NAME = "motherName";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_SECTION= "section";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_USER_ROLL = "getUserRoll";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";;
    public static final String KEY_DATE = "date";

    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "com.istiak.studentinformation3.userLogin";
    //This would be used to store the cell of current logged in user
    public static final String ROLL_SHARED_PREF = "roll";


    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";
}