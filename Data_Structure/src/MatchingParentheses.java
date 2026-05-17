public class MatchingParentheses {
    public static boolean doParenthesesMatch(String s) {
        StackWithArray leftp = new StackWithArray(100);
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '(': case '{':
                    leftp.push((int) c);
                    break;
                case ')':
                    if (leftp.isEmpty() || (leftp.pop() != '(')) return false;
                    break;
                case '}':
                    if (leftp.isEmpty() || (leftp.pop() != '{')) return false;
                    break;
            }
        }
        return leftp.isEmpty();
    }
}
