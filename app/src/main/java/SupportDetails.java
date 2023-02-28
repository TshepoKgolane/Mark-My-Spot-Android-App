public class SupportDetails {
    private static String UserName;
    private static String Password;
    public static void setUsername(String username){
        UserName = username;
    }public static void setPassword(String pass){
        Password = pass;
    }
    public static String getPassword(){
        return Password;
    }
    public static String getUsername(){
        return UserName;
    }
}
