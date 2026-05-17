import java.io.*;

public class JavaBasics3 {
    public static void main(String[] args) {
        String s;
        int i, x;

        // 자바는 파일 읽기와 같이 위험한 작업을 할 때 반드시 try-catch문을 쓰도록 강제한다
            // 만일 try 없이 파일 입출력 코드를 쓴다면 컴파일 자체가 되지 않음 (파일 읽기를 실패했을 때 어떻게 처리할지 프로그래밍이 되어있어야 함)
        try {
            BufferedReader rd =
                    new BufferedReader(new FileReader("input.txt"));
            BufferedWriter wr =
                    new BufferedWriter(new FileWriter("output.txt"));

            for (i = 0; i<3; i++) {
                s = rd.readLine();
                    // parseInt, toString 메서드는 Integer class안에 있는 static method라서 클래스 이름 붙여 호출
                x = Integer.parseInt(s);
                s = Integer.toString(x + 1);

                // 위에서 input.txt 읽어서 계산한 결과값을 output.txt에 쓰는 과정
                // (한줄씩 쓰고 newLine으로 줄바꿔줌)
                wr.write(s);
                wr.newLine();
            }
            // must be closed
                // (파일을 열면 메모리에 임시공간-buffer이 생기는데, 안 닫으면 임시공간에 있는 내용이 파일에 완전히 입력이 안되거나, 다른 프로그램이 그 파일을 못 쓰게 될 수도 있기 때문)
            rd.close();
            wr.close();
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
    }
}