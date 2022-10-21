package cc.openhome.gossip.constant;

import java.util.regex.Pattern;

public class Regex {
    public static final Pattern passwdRegex = Pattern.compile("^\\w{8,16}$");
    public final static Pattern emailRegex = Pattern.compile(
            "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");

    public final static Pattern usernameRegex = Pattern.compile("^\\w{1,16}$");
}
