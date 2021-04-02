import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Main {
    public static void main(String[] args) {
        System.out.println(new MyMatcher().matches("dasdasdsadsadsa", "/foo([/")); /// Syntax error example
        System.out.println(new MyMatcher().matches("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "(\\w+)+\\.")); /// Hang example
        System.out.println(new MyMatcher().matches("a", "a")); /// Property regex example
    }
}

class MyMatcher {
    public boolean matches(String text, String regex) {
        Boolean result = false;
        MyThread thread = new MyThread(result, text, regex);
        thread.start();
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 1000 && !thread.isInterrupted()) {}
        if (thread.isInterrupted())
            result = thread.result;
        thread.stop();
        return result;
    }
}

class MyThread extends Thread {
    public Boolean result;
    private String text, regex;

    MyThread(Boolean result, String text, String regex) {
        this.result = result;
        this.text = text;
        this.regex = regex;
    }

    public void run() {
        try {
            result = Pattern.compile(regex).matcher(text).matches();
        }
            catch(PatternSyntaxException e) {
            result = false;
        }
        this.interrupt();
    }
}
