package ro.ase.acs.main;

import ro.ase.acs.classes.Student;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   //read from keyboard
        System.out.print("Input the name: ");
        String name = scanner.nextLine();
        System.out.print("Input the age: ");
        int age = scanner.nextInt();
        System.out.print("Input the grade: ");
        float grade = scanner.nextFloat();

        Student s1 = new Student(name, age, grade);
        System.out.println(s1);


        //  WRITE IN A TEXT FILE
        try {
            FileOutputStream fos = new FileOutputStream("students.txt");       // FileOutputStream can be anythinkg ( one more param about append
            OutputStreamWriter osw = new OutputStreamWriter(fos);                   //make the file text
            BufferedWriter bw = new BufferedWriter(osw);

            // NAME
            // AGE
            // GRADE
            bw.write(s1.getName());
            bw.write(System.lineSeparator());   // \n is different based on the OS
            bw.write(Integer.toString(s1.getAge()));
            bw.write(System.lineSeparator());
            bw.write(Float.toString(s1.getGrade()));
            bw.close();
        } catch (FileNotFoundException e) {     //check exception
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  READ FROM A TEXT FILE
        BufferedReader br = null;
        try {
          FileInputStream fis = new FileInputStream("students.txt");
          InputStreamReader isr = new InputStreamReader(fis);
          br = new BufferedReader(isr);

          String n = br.readLine();
          int a = Integer.parseInt(br.readLine());
          float g = Float.parseFloat(br.readLine());

          br.close();

          Student s2 = new Student(n, a, g);
          System.out.println(s2);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {                                 // Use finally rather than a simple code - if you are inside the function and you throw an exeption before return
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //  WRITE IN A BINARY FILE
            // Buffers are just for text
        try(FileOutputStream fos = new FileOutputStream("students.bin");
            DataOutputStream dos = new DataOutputStream(fos)) {

            dos.writeUTF(s1.getName());
            dos.writeInt(s1.getAge());
            dos.writeFloat(s1.getGrade());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(FileInputStream fis = new FileInputStream("students.bin");
            DataInputStream dis = new DataInputStream(fis)) {

            Student s_bin = new Student();
            s_bin.setName(dis.readUTF());
            s_bin.setAge(dis.readInt());
            s_bin.setGrade(dis.readFloat());
            System.out.println(s_bin);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(FileOutputStream fos = new FileOutputStream("students.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(s1);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(FileInputStream fis = new FileInputStream("students.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Student s_bin2 = (Student) ois.readObject();
            System.out.println(s_bin2);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        scanner.close();    // can open the console just once per application
    }
}