package cc.openhome.gossip.constant;

import java.util.regex.Pattern;

public class Regex {

    public static final String passwdRegexString = "^\\w{8,16}$";
    public static final String emailRegexString = "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
    public static final String usernameRegexString = "^\\w{1,16}$";

    public static final Pattern passwdRegex = Pattern.compile(passwdRegexString);
    public static final Pattern emailRegex = Pattern.compile(emailRegexString);
    public static final Pattern usernameRegex = Pattern.compile(usernameRegexString);
}
